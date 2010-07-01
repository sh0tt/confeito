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
package org.t2framework.confeito.contexts.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.contexts.Chain;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.contexts.WebApplication;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.exception.NoSuchComponentException;
import org.t2framework.confeito.internal.PageUtil;
import org.t2framework.confeito.model.Component;
import org.t2framework.confeito.model.PageComponent;
import org.t2framework.confeito.spi.AnnotationResolverCreator;
import org.t2framework.confeito.spi.PageRegistrationCommand;
import org.t2framework.confeito.spi.PageRegistrationHandler;
import org.t2framework.confeito.spi.impl.DefaultPageRegistrationHandlerImpl;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.Traversal;
import org.t2framework.confeito.util.Reflections.ClassUtil;
import org.t2framework.confeito.util.Reflections.PackageUtil;

/**
 * <#if locale="en">
 * <p>
 * An implementation of {@link WebApplication} and Traversal.ClassHandler.
 * {@link WebApplicationImpl} takes care of registration page classes at
 * initalizing time.Basically, {@link WebApplicationImpl} traverses file system,
 * jar file, and zip file to collect all of page classes.To customize behavior,
 * you can create your own {@link PageRegistrationHandler}.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * @author yone098
 * @see org.t2framework.confeito.contexts.WebApplication
 */
public class WebApplicationImpl implements WebApplication,
		Traversal.ClassHandler {

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
	protected static final String CLASS_NAME = WebApplicationImpl.class
			.getName();

	/**
	 * <#if locale="en">
	 * <p>
	 * The logger for debugging.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected static Logger logger = Logger.getLogger(CLASS_NAME);

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
	 * <#if locale="en">
	 * <p>
	 * The {@link ContainerAdapter} for creating {@link PageComponent}
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected ContainerAdapter<?> adapter;

	/**
	 * <#if locale="en">
	 * <p>
	 * The root packages.It must not be null.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected String[] rootPackages;

	/**
	 * <#if locale="en">
	 * <p>
	 * Eagerload option.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected boolean eagerload;

	/**
	 * <#if locale="en">
	 * <p>
	 * Concurrent map of {@link PageComponent}(key: class name of page, value :
	 * {@link PageComponent} instance).
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected ConcurrentHashMap<String, PageComponent> pageDescMap = new ConcurrentHashMap<String, PageComponent>();

	/**
	 * <#if locale="en">
	 * <p>
	 * Temporal class cache for creating page instance.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected Map<Class<?>, UrlTemplate> classCache = new ConcurrentHashMap<Class<?>, UrlTemplate>();

	/**
	 * <#if locale="en">
	 * <p>
	 * Annotation resolver creator.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected final AnnotationResolverCreator resolverCreator;

	/**
	 * <#if locale="en">
	 * <p>
	 * list of {@link PageRegistrationHandler}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	protected List<PageRegistrationHandler> handlers = new ArrayList<PageRegistrationHandler>();

	public WebApplicationImpl(WebConfiguration config) {
		this(config.getContainerAdapter(), config.getRootPackages(), config
				.getEagerLoad(), config.getResolverCreator());
	}

	public WebApplicationImpl(ContainerAdapter<?> adapter,
			final String[] rootPackages, final boolean eagerload,
			final AnnotationResolverCreator resolverCreator) {
		this.adapter = Assertion.notNull(adapter, "adapter");
		this.rootPackages = Assertion.notNull(rootPackages);
		this.eagerload = eagerload;
		this.resolverCreator = Assertion.notNull(resolverCreator);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set up all strategies that it has, and begin traversing by root packages.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void initialize() {
		setupPageRegistrationHandler();
		traverse();
	}

	protected void setupPageRegistrationHandler() {
		List<PageRegistrationHandler> customHandlers = this.adapter
				.getComponents(PageRegistrationHandler.class);
		if (customHandlers != null && customHandlers.isEmpty() == false) {
			this.handlers.addAll(customHandlers);
		}
		this.handlers.add(new DefaultPageRegistrationHandlerImpl(this));
	}

	protected synchronized void traverse() {
		logger.log("ITDT0026");
		long start = System.currentTimeMillis();
		for (String rootPackage : rootPackages) {
			for (PageRegistrationHandler handler : this.handlers) {
				PageRegistrationCommand ret = handler.handle(rootPackage,
						classCache);
				if (ret != PageRegistrationCommand.CONTINUE) {
					break;
				}
			}
		}
		registerIfEagerload();
		final long registerationTime = System.currentTimeMillis() - start;
		logger.log("ITDT0027", new Object[] { new Long(registerationTime) });
	}

	protected void registerIfEagerload() {
		if (eagerload) {
			final ContainerAdapter<?> adapter = getContainerAdapter();
			for (Class<?> c : this.classCache.keySet()) {
				logger.log("ITDT0033", new Object[] { c.getName() });
				adapter.register(c);
			}
		}
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create {@link WebContext}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public WebContext createContext(final HttpServletRequest req,
			final HttpServletResponse res, final FilterChain filterChain,
			final FilterConfig config) {
		final Request request = createRequest(req, res);
		final Response response = createResponse(res);
		final Session session = createSession(request);
		final Application application = createApplication(config);
		final Chain chain = createChain(filterChain);
		ContextImpl context = createContext(request, response, application,
				session, chain);
		context.setContainerAdapter(getContainerAdapter());
		WebContext.set(context);
		return context;
	}

	protected ContextImpl createContext(Request request, Response response,
			Application application, Session session, Chain chain) {
		return new ContextImpl(request, response, application, session, chain);
	}

	protected Chain createChain(FilterChain filterChain) {
		return new ChainImpl(filterChain);
	}

	protected Application createApplication(FilterConfig config) {
		return new ApplicationImpl(config.getServletContext(), config);
	}

	protected Session createSession(final Request request) {
		return new SessionImpl(request);
	}

	protected Request createRequest(final HttpServletRequest req,
			final HttpServletResponse res) {
		return new RequestImpl(req, res);
	}

	protected Response createResponse(final HttpServletResponse res) {
		return new ResponseImpl(res);
	}

	@Override
	public void processClass(String packageName, String shortClassName) {
		logger.log("ITDT0028", new Object[] { packageName, shortClassName });
		if (packageName == null || shortClassName == null) {
			ignored.log("ITDT0029",
					new Object[] { packageName, shortClassName });
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(packageName.replace('/', '.'));
		builder.append(".");
		builder.append(shortClassName);
		final String classname = new String(builder);
		Class<Object> c = ClassUtil.forNameNoException(classname);
		if (c == null) {
			ignored.log("ITDT0037",
					new Object[] { packageName, shortClassName });
			return;
		}
		if (c.isAnnotation() || c.isEnum()) {
			ignored.log("ITDT0030",
					new Object[] { packageName, shortClassName });
			return;
		}
		final int mod = c.getModifiers();
		if (Modifier.isInterface(mod) || Modifier.isAbstract(mod)
				|| Modifier.isPublic(mod) == false) {
			ignored.log("ITDT0031",
					new Object[] { packageName, shortClassName });
			return;
		}
		processPage(c);
	}

	protected void processPage(Class<?> cd) {
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

	protected ContainerAdapter<?> getContainerAdapter() {
		return adapter;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Create map of url path to {@link PageComponent}.If the map is empty, it
	 * will try to load all {@link PageComponent}.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public Map<String, PageComponent> createPageDescMap() {
		if (pageDescMap.isEmpty()) {
			loadPageDescMap();
		}
		return pageDescMap;
	}

	@SuppressWarnings("unchecked")
	protected void loadPageDescMap() {
		for (Iterator<Class<?>> itr = classCache.keySet().iterator(); itr
				.hasNext();) {
			Class key = itr.next();
			UrlTemplate template = classCache.get(key);
			final Component<Object> component = adapter.getBeanDesc(key);
			if (component == null) {
				throw new NoSuchComponentException(key.getName());
			}
			final String packageName = PackageUtil.getPackageName(key);
			final String simpleClassName = ClassUtil.getSimpleClassName(key);
			final Set<Class<? extends Annotation>> actionAnnotationSet = resolverCreator
					.getActionAnnotationSet(adapter);
			final PageComponent pd = new PageComponent(packageName,
					simpleClassName, component, template, actionAnnotationSet);
			pageDescMap.putIfAbsent(key.getName(), pd);
		}
	}

	@Override
	public String[] getRootPackages() {
		return rootPackages;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * {@inheritDoc}
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	@Override
	public void destroy() {
		this.adapter = null;
		this.rootPackages = null;
		this.eagerload = false;
		this.pageDescMap.clear();
		this.classCache.clear();
		this.handlers.clear();
	}

	@Override
	public Map<Class<?>, UrlTemplate> getClassCache() {
		return this.classCache;
	}

}
