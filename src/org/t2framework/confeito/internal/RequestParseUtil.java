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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.t2framework.confeito.contexts.Request;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.PatternUtil;
import org.t2framework.confeito.util.StringUtil;

/**
 * 
 * <#if locale="en">
 * <p>
 * Request parsing and converting utility for request like hoge[0][1].
 * </p>
 * <#else>
 * <p>
 * 
 * </p>
 * </#if>
 * 
 * TODO convert as array.
 * 
 * @author shot
 * 
 */
public class RequestParseUtil {

	public static final String REGEX = "([^\\]]+)(?:\\[(\\d+)\\])?(?:\\[(\\d+)\\])?";

	public static final int MAX = 1000;

	public static Map<String, Map<Integer, Object>> createParamMap(
			Request request) {
		Map<String, Map<Integer, Object>> map = new HashMap<String, Map<Integer, Object>>();
		for (String key : request.getParameterNames()) {
			String value = request.getParameter(key);
			parse(key, value, map);
		}
		return map;
	}

	protected static Map<Integer, Object> createMap() {
		return new TreeMap<Integer, Object>();
	}

	@SuppressWarnings("unchecked")
	public static void parse(String key, String value,
			Map<String, Map<Integer, Object>> context) {
		Assertion.notNull(key);
		Assertion.notNull(value);
		int head = key.indexOf("[");
		int tail = key.indexOf("]");
		// fast check
		if (0 < head && head < tail) {
			final Pattern pattern = PatternUtil.getPattern(REGEX);
			Matcher matcher = pattern.matcher(key);
			if (matcher.matches() == false) {
				throw new IllegalStateException("unmatch");
			}
			int groupCount = matcher.groupCount();
			String propertyName = matcher.group(1);
			Map<Integer, Object> root = null;
			if (context.containsKey(propertyName)) {
				root = context.get(propertyName);
			} else {
				root = createMap();
				context.put(propertyName, root);
			}
			Map<Integer, Object> map = root;
			for (int i = 2; i < groupCount + 1; i++) {
				String s = matcher.group(i);
				if (StringUtil.isEmpty(s)) {
					break;
				}
				Integer mapKey = Integer.valueOf(s);
				if (MAX < mapKey.intValue()) {
					throw new IllegalStateException(
							"Can not keep more than 1000 entry.");
				}
				boolean nextNull = (i == groupCount) ? true : StringUtil
						.isEmpty(matcher.group(i + 1));
				if (nextNull) {
					if (map.containsKey(mapKey)) {
						throw new IllegalStateException("dimension error.");
					} else {
						map.put(mapKey, value);
					}
				} else {
					Map<Integer, Object> m = null;
					if (map.containsKey(mapKey)) {
						Object o = map.get(mapKey);
						if (o instanceof Map) {
							m = (Map<Integer, Object>) o;
						}
					} else {
						m = createMap();
					}
					if (m != null) {
						map.put(mapKey, m);
						map = m;
					}
				}
			}
		}
	}

	public static List<Object> convertAsList(Map<Integer, Object> map) {
		List<Object> ret = new ArrayList<Object>();
		walk(ret, map);
		return ret;
	}

	@SuppressWarnings("unchecked")
	protected static void walk(List<Object> root, Map<Integer, Object> map) {
		for (Entry<Integer, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof Map) {
				Map<Integer, Object> m = (Map<Integer, Object>) value;
				List<Object> list = new ArrayList<Object>();
				root.add(list);
				walk(list, m);
			} else {
				root.add(value);
			}
		}
	}

}
