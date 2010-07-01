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
package org.t2framework.confeito.internal;

import org.t2framework.confeito.contexts.Application;
import org.t2framework.confeito.contexts.Chain;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.Session;
import org.t2framework.confeito.contexts.WebContext;

/**
 * {@link WebContext} utility.
 * 
 * @author shot
 * 
 */
public class WebContextUtil {

	/**
	 * Get {@link Request}.The method may return null.
	 * 
	 * @return
	 */
	public static Request getRequest() {
		return getWebContext().getRequest();
	}

	/**
	 * Get {@link Response}.The method may return null.
	 * 
	 * @return
	 */
	public static Response getResponse() {
		return getWebContext().getResponse();
	}

	/**
	 * Get {@link Session}.The method may return null.
	 * 
	 * @return
	 */
	public static Session getSession() {
		return getWebContext().getSession();
	}

	/**
	 * Get {@link Application}.The method may return null.
	 * 
	 * @return
	 */
	public static Application getApplication() {
		return getWebContext().getApplication();
	}

	private static WebContext getWebContext() {
		WebContext context = WebContext.get();
		return (context != null) ? context : NULL_CONTEXT;
	}

	/**
	 * Represents null object of {@link WebContext}.
	 */
	private static final WebContext NULL_CONTEXT = new WebContext() {

		@Override
		public Session getSession() {
			return null;
		}

		@Override
		public Response getResponse() {
			return null;
		}

		@Override
		public Request getRequest() {
			return null;
		}

		@Override
		public Chain getChain() {
			return null;
		}

		@Override
		public Application getApplication() {
			return null;
		}
	};
}
