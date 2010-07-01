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
package org.t2framework.confeito.navigation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.t2framework.confeito.contexts.Response;
import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.spi.Navigation;

/**
 * 
 * <#if locale="en">
 * <p>
 * HTML5 Cache Manifest navigation.
 * </p>
 * <#else>
 * <p>
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public class CacheManifest implements Navigation {

	protected static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyyMMddhhss");

	protected Date version;

	protected Map<String, String> fallbacks;

	protected String[] networks;

	protected String[] caches;

	public static CacheManifest write(String[] caches) {
		return new CacheManifest(null, null, null, caches);
	}

	public static CacheManifest write(Date version, String[] caches) {
		return new CacheManifest(version, null, null, caches);
	}

	public static CacheManifest write(Date version, String[] networks,
			String[] caches) {
		return new CacheManifest(version, null, networks, caches);
	}

	public static CacheManifest write(Date version,
			Map<String, String> fallbacks, String[] networks, String[] caches) {
		return new CacheManifest(version, fallbacks, networks, caches);
	}

	public CacheManifest(Date version, Map<String, String> fallbacks,
			String[] networks, String[] caches) {
		this.version = version;
		this.fallbacks = fallbacks;
		this.networks = networks;
		this.caches = caches;
	}

	@Override
	public void execute(WebContext context) throws Exception {
		Response response = context.getResponse();
		response.setContentType("text/cache-manifest; charset=utf-8");
		final String sep = System.getProperty("line.separator");

		StringBuilder builder = new StringBuilder();
		builder.append("CACHE MANIFEST");
		builder.append(sep);
		if (this.version != null) {
			builder.append("Version:");
			builder.append(sep);
			builder.append(formatter.format(version));
			builder.append(sep);
		}
		builder.append(sep);
		if (networks != null && 0 < networks.length) {
			builder.append("NETWORK:");
			builder.append(sep);
			for (String network : networks) {
				builder.append(network);
				builder.append(sep);
			}
			builder.append(sep);
		}
		if (fallbacks != null && fallbacks.isEmpty() == false) {
			builder.append("FALLBACK:");
			builder.append(sep);
			for (Map.Entry<String, String> entry : fallbacks.entrySet()) {
				builder.append(entry.getKey() + " " + entry.getValue());
				builder.append(sep);
			}
			builder.append(sep);
		}
		if (caches != null && 0 < caches.length) {
			builder.append("CACHE:");
			builder.append(sep);
			for (String cache : caches) {
				builder.append(cache);
				builder.append(sep);
			}
		}
		response.writeAndFlush(new String(builder));
	}
}
