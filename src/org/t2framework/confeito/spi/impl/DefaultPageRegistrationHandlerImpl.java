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

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

import org.t2framework.confeito.Constants;
import org.t2framework.confeito.spi.PageRegistrationCommand;
import org.t2framework.confeito.spi.PageRegistrationHandler;
import org.t2framework.confeito.urltemplate.UrlTemplate;
import org.t2framework.confeito.util.Assertion;
import org.t2framework.confeito.util.JarFileUtil;
import org.t2framework.confeito.util.Logger;
import org.t2framework.confeito.util.ResourceUtil;
import org.t2framework.confeito.util.Traversal;
import org.t2framework.confeito.util.URLUtil;
import org.t2framework.confeito.util.ZipFileUtil;
import org.t2framework.confeito.util.Traversal.ClassHandler;

/**
 * Default implementation of {@link PageRegistrationHandler}. This
 * implementation provides several {@link Strategy} inside, and each of these is
 * provided by URL prefix such as file, jar, zip.
 * 
 * @author shot
 * 
 */
public class DefaultPageRegistrationHandlerImpl implements
		PageRegistrationHandler {

	/**
	 * The logger for this class.
	 */
	protected static Logger logger = Logger
			.getLogger(DefaultPageRegistrationHandlerImpl.class);

	/**
	 * Strategies map.Key is url prefix, value is {@link Strategy} itself.
	 */
	protected Map<String, Strategy> strategies = new HashMap<String, Strategy>();

	/**
	 * {@link ClassHandler} for processing page class registration.
	 */
	protected ClassHandler classHandler;

	/**
	 * Construct this class.Given {@link ClassHandler} must not be null.
	 * 
	 * @param classHandler
	 */
	public DefaultPageRegistrationHandlerImpl(ClassHandler classHandler) {
		this.classHandler = Assertion.notNull(classHandler);
		setupStrategies();
	}

	/**
	 * Setting up basic strategies.Developer can add later using
	 * {@link DefaultPageRegistrationHandlerImpl#addStrategy(String, Strategy)}.
	 */
	protected void setupStrategies() {
		addStrategy(Constants.URL_PROTOCOL_FILE, new FileSystemStrategy());
		addStrategy(Constants.URL_PROTOCOL_JAR, new JarFileStrategy());
		addStrategy(Constants.URL_PROTOCOL_WSJAR, new JarFileStrategy());
		addStrategy(Constants.URL_PROTOCOL_ZIP, new ZipFileStrategy());
		addStrategy(Constants.URL_PROTOCOL_VFSZIP, new VfsZipFileStrategy());
		addStrategy(Constants.URL_PROTOCOL_CODE_SOURCE,
				new CodeSourceFileStrategy());
	}

	/**
	 * Add customize {@link Strategy}.
	 * 
	 * @param protocol
	 * @param strategy
	 */
	public void addStrategy(String protocol, Strategy strategy) {
		strategies.put(protocol, strategy);
	}

	@Override
	public PageRegistrationCommand handle(String rootPackage,
			Map<Class<?>, UrlTemplate> classCache) {
		final String path = rootPackage.replace('.', '/');
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> resources = ResourceUtil.getResources(loader, path);
		while (resources.hasMoreElements()) {
			final URL u = resources.nextElement();
			final String externalForm = u.toExternalForm();
			String filepath = u.getFile();
			filepath = URLUtil.decode(filepath, "UTF8");
			logger.log("ITDT0035", new Object[] { rootPackage, externalForm });
			if (filepath.startsWith(Constants.FILE_URL_PREFIX)) {
				filepath = filepath.substring(Constants.FILE_URL_PREFIX_LENGTH);
			}
			if (0 < filepath.indexOf("!")) {
				filepath = filepath.substring(0, filepath.indexOf("!"));
			}
			final File file = new File(filepath);
			if (file.isDirectory()) {
				loadFromFilesystem(path, file, u);
			} else {
				loadFromJar(path, file, u);
			}
		}
		return PageRegistrationCommand.STOP;
	}

	protected void loadFromFilesystem(String packagename, File file, URL url) {
		strategies.get("file")
				.handle(packagename, file, url, this.classHandler);
	}

	protected void loadFromJar(String packagename, File file, URL url) {
		String protocol = url.getProtocol();
		Strategy strategy = strategies.get(protocol);
		if (strategy == null) {
			throw new NullPointerException();
		}
		strategy.handle(packagename, file, url, this.classHandler);
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Internal strategy for page registration.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @author shot
	 * 
	 */
	public static interface Strategy {
		void handle(String packagename, File root, URL url, ClassHandler handler);
	}

	/**
	 * {@link Strategy} for file.
	 * 
	 * @author shot
	 * 
	 */
	protected static class FileSystemStrategy implements Strategy {

		@Override
		public void handle(String packagename, File root, URL url,
				ClassHandler handler) {
			File[] files = root.listFiles();
			StringBuilder builder = null;
			if (files == null) {
				return;
			}
			for (File f : files) {
				builder = new StringBuilder(100);
				final String name = f.getName();
				builder.append(packagename).append("/").append(name);
				final String packageOrClass = (root == null ? name : builder
						.toString());
				if (f.isDirectory()) {
					handle(packageOrClass, f, url, handler);
				} else if (name.endsWith(Constants.CLASS_SUFFIX)) {
					final String className = packageOrClass.substring(
							0,
							packageOrClass.length()
									- Constants.CLASS_SUFFIX.length()).replace(
							'/', '.');
					final int pos = className.lastIndexOf('.');
					final String shortClassName = (pos == -1) ? className
							: className.substring(pos + 1);
					handler.processClass(packagename, shortClassName);
				}
			}
		}

	}

	/**
	 * Strategy for jar.
	 * 
	 * @author shot
	 * 
	 */
	protected static class JarFileStrategy implements Strategy {

		@Override
		public void handle(String packagename, File root, URL url,
				ClassHandler handler) {
			final JarFile jarFile = createJarFile(url);
			Traversal.traverseFromJarFile(jarFile, handler);
		}

		protected JarFile createJarFile(final URL url) {
			return JarFileUtil.toJarFile(url);
		}
	}

	/**
	 * Strategy for zip.
	 * 
	 * @author shot
	 * 
	 */
	protected static class ZipFileStrategy implements Strategy {

		@Override
		public void handle(String packagename, File root, URL url,
				ClassHandler handler) {
			final JarFile jarFile = createJarFile(url);
			Traversal.traverseFromJarFile(jarFile, handler);
		}

		protected JarFile createJarFile(final URL url) {
			final String jarFileName = ZipFileUtil.toZipFilePath(url);
			return JarFileUtil.create(new File(jarFileName));
		}
	}

	/**
	 * Strategy for code-source.
	 * 
	 * @author shot
	 * 
	 */
	protected static class CodeSourceFileStrategy implements Strategy {

		@Override
		public void handle(String packagename, File root, URL url,
				ClassHandler handler) {
			final JarFile jarFile = createJarFile(url);
			Traversal.traverseFromJarFile(jarFile, handler);
		}

		protected JarFile createJarFile(final URL url) {
			final URL jarUrl = URLUtil.create("jar:file:" + url.getPath());
			return JarFileUtil.toJarFile(jarUrl);
		}
	}

	/**
	 * Strategy for vfs-zip.
	 * 
	 * @author shot
	 * 
	 */
	protected static class VfsZipFileStrategy implements Strategy {
		@Override
		public void handle(String packagename, File root, URL url,
				ClassHandler handler) {
			final JarFile jarFile = createJarFile(url);
			Traversal.traverseFromJarFile(jarFile, handler);
		}

		protected JarFile createJarFile(final URL url) {
			final String path = url.getPath();
			String pathUp = path.toUpperCase();
			String warUp = Constants.WAR_SUFFIX.toUpperCase();
			String warPath = path.substring(0, pathUp.indexOf(warUp)
					+ Constants.WAR_SUFFIX.length());
			return JarFileUtil.create(warPath);
		}
	}

}
