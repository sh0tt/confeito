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
package org.t2framework.confeito;

import java.util.Collections;
import java.util.Set;

public interface Constants {

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty object array.
	 * </p>
	 * <#else>
	 * <p>
	 * 空のオブジェクト配列です.
	 * </p>
	 * </#if>
	 */
	Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty string array.
	 * </p>
	 * <#else>
	 * <p>
	 * 空のString配列です.
	 * </p>
	 * </#if>
	 */
	String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty class array.
	 * </p>
	 * <#else>
	 * <p>
	 * 空のClass配列です.
	 * </p>
	 * </#if>
	 */
	Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty int array.
	 * </p>
	 * <#else>
	 * <p>
	 * 空のint配列です.
	 * </p>
	 * </#if>
	 */
	int[] EMPTY_PRIMITIVE_INT_ARRAY = new int[0];

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty Integer array.
	 * </p>
	 * <#else>
	 * <p>
	 * 空のInteger配列です.
	 * </p>
	 * </#if>
	 */
	Integer[] EMPTY_INT_ARRAY = new Integer[0];

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty double array.
	 * </p>
	 * <#else>
	 * <p>
	 * 空のdouble配列です.
	 * </p>
	 * </#if>
	 */
	double[] EMPTY_PRIMITIVE_DOUBLE_ARRAY = new double[0];

	/**
	 * <#if locale="en">
	 * <p>
	 * Empty Double array.
	 * </p>
	 * <#else>
	 * <p>
	 * 空のDouble配列です.
	 * </p>
	 * </#if>
	 */
	Double[] EMPTY_DOUBLE_ARRAY = new Double[0];

	Set<String> EMPTY_STRING_SET = Collections.emptySet();

	Integer INT_DEFAULT_VALUE = new Integer(0);

	Double DOUBLE_DEFAULT_VALUE = new Double(0);

	Long LONG_DEFAULT_VALUE = new Long(0);

	Float FLOAT_DEFAULT_VALUE = new Float(0);

	Short SHORT_DEFAULT_VALUE = new Short((short) 0);

	Boolean BOOLEAN_DEFAULT_VALUE = Boolean.FALSE;

	Byte BYTE_DEFAULT_VALUE = Byte.valueOf((byte) 0);

	Character CHAR_DEFAULT_VALUE = Character.valueOf((char) 0);

	String TEXT_CONTENT_TYPE = "text/plain";

	String HTML_CONTENT_TYPE = "text/html";

	String ANY_CONTENT_TYPE = "*/*";

	String XHTML_CONTENT_TYPE = "application/xhtml+xml";

	String APPLICATION_XML_CONTENT_TYPE = "application/xml";

	String TEXT_XML_CONTENT_TYPE = "text/xml";

	String JAVASCRIPT_CONTENT_TYPE = "text/javascript";

	String CACHE_MANIFEST_CONTENT_TYPE = "text/cache-manifest";

	String PREFIX_CHARSET = "charset=";

	String MULTIPART = "multipart/";

	String MULTIPART_FORM_DATA = "multipart/form-data";

	String MULTIPART_MIXED = "multipart/mixed";

	/**
	 * Content type for AMF, Acrion Message Format from Flex/AIR.
	 */
	String AMF_CONTENT_TYPE = "application/x-amf";

	/**
	 * Content type for Avro, a message serialization system.
	 */
	String AVRO_CONTENT_TYPE = "avro/binary";

	String CONTENT_TYPE = "Content-type";

	String CONTENT_DISPOSITION = "Content-disposition";

	String CONTENT_LENGTH = "Content-length";

	String FORM_DATA = "form-data";

	String ATTACHMENT = "attachment";

	String CLASS_SUFFIX = ".class";

	String WEB_INF_CLASSES_PATH = "WEB-INF/classes/";

	int WEB_INF_CLASSES_PATH_LENGTH = WEB_INF_CLASSES_PATH.length();

	String WAR_SUFFIX = ".war";

	/** Pseudo URL prefix for loading from the class path: "classpath:" */
	String CLASSPATH_URL_PREFIX = "classpath:";

	/** URL prefix for loading from the file system: "file:" */
	String FILE_URL_PREFIX = "file:";

	/** URL prefix length for loading from the file system: "file:" */
	int FILE_URL_PREFIX_LENGTH = FILE_URL_PREFIX.length();

	/** URL protocol for a file in the file system: "file" */
	String URL_PROTOCOL_FILE = "file";

	/** URL protocol for an entry from a jar file: "jar" */
	String URL_PROTOCOL_JAR = "jar";

	/** URL protocol for an entry from a zip file: "zip" */
	String URL_PROTOCOL_ZIP = "zip";

	/** URL protocol for an entry from a JBoss zip file: "vfszip" */
	String URL_PROTOCOL_VFSZIP = "vfszip";

	/** URL protocol for an entry from a WebSphere jar file: "wsjar" */
	String URL_PROTOCOL_WSJAR = "wsjar";

	/** URL protocol for an entry from an OC4J jar file: "code-source" */
	String URL_PROTOCOL_CODE_SOURCE = "code-source";

	/** Separator between JAR URL and file path within the JAR */
	String JAR_URL_SEPARATOR = "!/";

	/**
	 * <#if locale="en">
	 * <p>
	 * package-info class name.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String PACKAGE_INFO = "package-info";

	int KB = 1024;

	int MB = KB * KB;

	int GB = MB * KB;

	/**
	 * <#if locale="en">
	 * <p>
	 * JavaBeans get property.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String GET = "get";

	/**
	 * <#if locale="en">
	 * <p>
	 * JavaBeans set property.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String SET = "set";

	/**
	 * <#if locale="en">
	 * <p>
	 * JavaBeans is property.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 */
	String IS = "is";

	int GET_LENGTH = GET.length();

	int SET_LENGTH = SET.length();

	int IS_LENGTH = IS.length();

	/**
	 * <#if locale="en">
	 * <p>
	 * UTF8 encoding
	 * </p>
	 * <#else>
	 * <p>
	 * UTF8の文字エンコーディング
	 * </p>
	 * </#if>
	 */
	String UTF8 = "UTF-8";

}
