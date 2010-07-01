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

import org.t2framework.confeito.contexts.HttpMethod;
import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.StringUtil;

/**
 * Utility for request parameter.
 * 
 * @author shot
 * 
 */
public class HttpMethodUtil {

	/**
	 * REST overload method is a technique which simulates PUT/DELETE on
	 * browser.
	 * 
	 * This method gives you whether this request is REST overload method. The
	 * specifications are:
	 * <ul>
	 * <li>If POST method request and parameter "_method" sets PUT, it is REST
	 * overload method.</li>
	 * <li>If POST method request and parameter "_method" sets DELETE, it is
	 * REST overload method.</li>
	 * <li>If POST method request and parameter "_method" sets HEAD, it is REST
	 * overload method.</li>
	 * <li>If GET method request and parameter "_method" sets HEAD, it is REST
	 * overload method.</li>
	 * <li>Others, it is not REST overload method.</li>
	 * </ul>
	 * 
	 * @param request
	 * @return
	 */
	public static HttpMethod getHttpOverloadMethodExistByParameter(
			final Request request) {
		Assertion.notNull(request);
		final HttpMethod method = request.getMethod();
		final String param = request.getParameter("_method");
		if (StringUtil.isEmpty(param)) {
			return null;
		}
		final HttpMethod specifiedMethod = HttpMethod.getMethodType(param);
		if (method == HttpMethod.POST
				&& (specifiedMethod == HttpMethod.DELETE
						|| specifiedMethod == HttpMethod.PUT || specifiedMethod == HttpMethod.HEAD)) {
			return specifiedMethod;
		} else if (method == HttpMethod.GET
				&& specifiedMethod == HttpMethod.HEAD) {
			return specifiedMethod;
		}
		return null;
	}
}
