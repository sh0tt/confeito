/*
 * Copyright 2008-2010 the T2 Project ant the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.t2framework.confeito.spi.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Properties;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.exception.IORuntimeException;
import org.t2framework.confeito.internal.PageUtil;
import org.t2framework.confeito.spi.PageRegistrationCommand;
import org.t2framework.confeito.spi.PageRegistrationHandler;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.ArrayUtil;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.ResourceUtil;
import org.t2framework.confeito.util.StringUtil;
import org.t2framework.confeito.util.Reflections.ClassUtil;

/**
 * <p>
 * Faster page registration handler.
 * </p>
 * <p>
 * This implementation of {@link PageRegistrationHandler} provides faster
 * registration process than usual directory traversal approach.However,
 * developer must provide page.properties which is configuration file to let T2
 * framework know where and how many page classes exists.If not provide
 * page.properties, then T2 framework does fallback automatically to the default
 * {@link PageRegistrationHandler}, directory traversal approach.
 * </p>
 * <p>
 * To use this, developer must register this class to {@link ContainerAdapter}.
 * For example, with {@link SimpleContainerAdapter}, the configuration is
 * something like this:
 * </p>
 * <p>
 * &lt;init-param&gt;<br />
 * &nbsp;&nbsp;&lt;param-name&gt;t2.components&lt;/param-name&gt;<br />
 * &nbsp;&nbsp;&lt;param-value&gt;org.t2framework.t2.spi.impl.
 * FastPageRegistrationHandlerImpl&lt;/param-value&gt;<br/>
 * &lt;/init-param&gt;
 * </p>
 * 
 * @author shot
 * @since 0.6.3
 */
public class FastPageRegistrationHandlerImpl implements PageRegistrationHandler {

	/**
	 * <#if locale="en">
	 * <p>
	 * The class name as logging key.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static final String CLASS_NAME = FastPageRegistrationHandlerImpl.class
			.getName();

	/**
	 * <#if locale="en">
	 * <p>
	 * The logger for registration succeed.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static Logger success = Logger.getLogger(CLASS_NAME + ".success");

	/**
	 * <#if locale="en">
	 * <p>
	 * The logger for ignoring classes.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static Logger ignored = Logger.getLogger(CLASS_NAME + ".ignored");

	/**
	 * The logger for this class.
	 */
	protected static Logger logger = Logger.getLogger(CLASS_NAME);

	/**
	 * <#if locale="en">
	 * <p>
	 * Page class registration properties file.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static final String PROPERTIES_NAME = "page.properties";

	/**
	 * Execute registration process with page.properties.
	 */
	@Override
	public PageRegistrationCommand handle(String packageName,
			Map<Class<?>, UrlTemplate> classCache) {
		final Properties props = loadProperties(PROPERTIES_NAME);
		if (props == null) {
			logger.log("ITDT0056");
			return PageRegistrationCommand.CONTINUE;
		}
		final String[] pageCandidates = getPageCandidates(packageName, props);
		if (ArrayUtil.isEmpty(pageCandidates)) {
			logger.log("ITDT0038");
			return PageRegistrationCommand.CONTINUE;
		} else {
			return processPage(packageName, pageCandidates, classCache);
		}
	}

	protected PageRegistrationCommand processPage(String packageName,
			String[] pageCandidates, Map<Class<?>, UrlTemplate> classCache) {
		for (String shortClassName : pageCandidates) {
			final String classname = packageName + "." + shortClassName;
			Class<Object> c = ClassUtil.forNameNoException(classname);
			if (c == null) {
				ignored.log("ITDT0037", new Object[] { packageName,
						shortClassName });
				continue;
			} else if (isAnnotationOrEnum(c)) {
				ignored.log("ITDT0030", new Object[] { packageName,
						shortClassName });
				continue;
			} else if (isNotPublicConcreteClass(c)) {
				ignored.log("ITDT0031", new Object[] { packageName,
						shortClassName });
				continue;
			}
			processPage(c, classCache);
		}
		return PageRegistrationCommand.STOP;
	}

	protected String[] getPageCandidates(String packageName, Properties props) {
		String s = props.getProperty(packageName);
		return StringUtil.split(s, ", ");
	}

	protected Properties loadProperties(String propertiesName) {
		final InputStream is = ResourceUtil
				.getResourceAsStreamNoException(PROPERTIES_NAME);
		if (is == null) {
			return null;
		}
		Properties props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		return props;
	}

	private boolean isNotPublicConcreteClass(Class<Object> c) {
		final int mod = c.getModifiers();
		return Modifier.isInterface(mod) || Modifier.isAbstract(mod)
				|| Modifier.isPublic(mod) == false;
	}

	private boolean isAnnotationOrEnum(Class<Object> c) {
		return c.isAnnotation() || c.isEnum();
	}

	protected void processPage(Class<?> cd,
			Map<Class<?>, UrlTemplate> classCache) {
		if (PageUtil.isPageClass(cd) == false) {
			return;
		}
		final UrlTemplate template = PageUtil.toUrlTemplate(cd);
		final String className = cd.getName();
		if (template != null) {
			success.log("ITDT0034", new Object[] { className });
			classCache.put(cd, template);
		} else {
			ignored.log("ITDT0032", new Object[] { className });
		}
	}

}
