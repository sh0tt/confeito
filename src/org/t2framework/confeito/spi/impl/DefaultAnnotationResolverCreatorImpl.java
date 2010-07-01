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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.Ajax;
import org.t2framework.confeito.annotation.DELETE;
import org.t2framework.confeito.annotation.GET;
import org.t2framework.confeito.annotation.HEAD;
import org.t2framework.confeito.annotation.OPTIONS;
import org.t2framework.confeito.annotation.POST;
import org.t2framework.confeito.annotation.PUT;
import org.t2framework.confeito.annotation.TRACE;
import org.t2framework.confeito.contexts.HttpMethod;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.method.ActionMethodResolver;
import org.t2framework.confeito.method.impl.ActionParamResolver;
import org.t2framework.confeito.method.impl.ActionPathResolver;
import org.t2framework.confeito.method.impl.AjaxResolver;
import org.t2framework.confeito.method.impl.DefaultResolver;
import org.t2framework.confeito.method.impl.HttpMethodActionMethodResolver;
import org.t2framework.confeito.parameter.ParameterResolver;
import org.t2framework.confeito.parameter.impl.CookieParameterResolver;
import org.t2framework.confeito.parameter.impl.ErrorInfoParameterResolver;
import org.t2framework.confeito.parameter.impl.FormParameterResolver;
import org.t2framework.confeito.parameter.impl.InParameterResolver;
import org.t2framework.confeito.parameter.impl.IndexParameterResolver;
import org.t2framework.confeito.parameter.impl.JsonParameterResolverImpl;
import org.t2framework.confeito.parameter.impl.RequestHeaderResolver;
import org.t2framework.confeito.parameter.impl.RequestParamParameterResolver;
import org.t2framework.confeito.parameter.impl.ServletParameterResolver;
import org.t2framework.confeito.parameter.impl.SessionAttributeResolver;
import org.t2framework.confeito.parameter.impl.UploadFileParameterResolver;
import org.t2framework.confeito.parameter.impl.VarParameterResolver;
import org.t2framework.confeito.parameter.impl.WebContextParameterResolver;
import org.t2framework.confeito.spi.AnnotationResolverCreator;

/**
 * <#if locale="en">
 * <p>
 * Default implementation of {@code AnnotationResolverCreator}.
 * 
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class DefaultAnnotationResolverCreatorImpl implements
		AnnotationResolverCreator {

	protected List<ParameterResolver> parameterResolvers = new ArrayList<ParameterResolver>();

	protected Map<Class<? extends Annotation>, ActionMethodResolver> actionAnnotationResolverMap = new HashMap<Class<? extends Annotation>, ActionMethodResolver>();

	protected ActionMethodResolver defaultAnnotationResolver = new DefaultResolver();
	{
		// parameter resolvers
		parameterResolvers.add(new WebContextParameterResolver());
		parameterResolvers.add(new ServletParameterResolver());

		parameterResolvers.add(new CookieParameterResolver());
		parameterResolvers.add(new RequestParamParameterResolver());
		parameterResolvers.add(new SessionAttributeResolver());
		parameterResolvers.add(new FormParameterResolver());
		parameterResolvers.add(new UploadFileParameterResolver());
		parameterResolvers.add(new ErrorInfoParameterResolver());
		parameterResolvers.add(new IndexParameterResolver());
		parameterResolvers.add(new VarParameterResolver());
		parameterResolvers.add(new RequestHeaderResolver());
		parameterResolvers.add(new JsonParameterResolverImpl());
		parameterResolvers.add(new InParameterResolver());

		// method resolvers
		actionAnnotationResolverMap.put(GET.class,
				new HttpMethodActionMethodResolver(HttpMethod.GET));
		actionAnnotationResolverMap.put(POST.class,
				new HttpMethodActionMethodResolver(HttpMethod.POST));
		actionAnnotationResolverMap.put(PUT.class,
				new HttpMethodActionMethodResolver(HttpMethod.PUT));
		actionAnnotationResolverMap.put(DELETE.class,
				new HttpMethodActionMethodResolver(HttpMethod.DELETE));
		actionAnnotationResolverMap.put(HEAD.class,
				new HttpMethodActionMethodResolver(HttpMethod.HEAD));
		actionAnnotationResolverMap.put(OPTIONS.class,
				new HttpMethodActionMethodResolver(HttpMethod.OPTIONS));
		actionAnnotationResolverMap.put(TRACE.class,
				new HttpMethodActionMethodResolver(HttpMethod.TRACE));

		actionAnnotationResolverMap.put(ActionPath.class,
				new ActionPathResolver());
		actionAnnotationResolverMap.put(ActionParam.class,
				new ActionParamResolver());
		actionAnnotationResolverMap.put(Ajax.class, new AjaxResolver());
	};

	@Override
	public Map<Class<? extends Annotation>, ActionMethodResolver> createActionMethodResolvers(
			ContainerAdapter<?> containerAdapter) {
		return actionAnnotationResolverMap;
	}

	@Override
	public ActionMethodResolver createDefaultActionMethodResolver(
			ContainerAdapter<?> containerAdapter) {
		return defaultAnnotationResolver;
	}

	@Override
	public List<ParameterResolver> createParameterResolvers(
			ContainerAdapter<?> containerAdapter, WebContext context) {
		return parameterResolvers;
	}

	@Override
	public Set<Class<? extends Annotation>> getActionAnnotationSet(
			ContainerAdapter<?> containerAdapter) {
		return actionAnnotationResolverMap.keySet();
	}

}
