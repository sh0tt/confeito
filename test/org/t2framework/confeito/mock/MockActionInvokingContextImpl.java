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
package org.t2framework.confeito.mock;

import org.t2framework.confeito.action.impl.ActionInvokingContextImpl;
import org.t2framework.confeito.adapter.ContainerAdapter;
import org.t2framework.confeito.adapter.SimpleContainerAdapter;
import org.t2framework.confeito.contexts.WebConfiguration;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.plugin.PluginProcessor;
import org.t2framework.confeito.spi.AnnotationResolverCreator;

public class MockActionInvokingContextImpl extends ActionInvokingContextImpl {

	public MockActionInvokingContextImpl(String requestPath) {
		this(MockWebContext.createMock(requestPath).containerAdapter(
				new SimpleContainerAdapter()));
	}

	public MockActionInvokingContextImpl(WebContext context) {
		this(context, new MockWebConfigurationImpl(), new PluginProcessor(
				context.getContainerAdapter()));
	}

	public MockActionInvokingContextImpl(WebContext context,
			WebConfiguration webConfig, PluginProcessor pluginProcessor) {
		super(context, webConfig, pluginProcessor);
	}

	public void setResolverCreator(AnnotationResolverCreator creator) {
		super.resolverCreator = creator;
	}

	public void setContainerAdapter(ContainerAdapter<?> containerAdapter) {
		super.containerAdapter = containerAdapter;
	}

	public void setPluginProcessor(PluginProcessor pluginProcessor) {
		super.pluginProcessor = pluginProcessor;
	}
}
