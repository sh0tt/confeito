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

import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.t2framework.confeito.contexts.WebContext;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * Direct output returns as response like returning binary response.
 * 
 * <pre>
 * Direct can take three parameter to write to ServletOutputStream :
 *  -File
 *  -InputStream
 *  -bytes[]
 * </pre>
 * 
 * </p>
 * <#else>
 * <p>
 * バイナリなどを直接レスポンスに書き込む{@link Navigation}です.
 * </p>
 * 
 * <pre>
 * Directは以下の3つのパラメータを入力として選択できます:
 *  -File
 *  -InputStream
 *  -bytes[]
 * </pre>
 * 
 * メモリ消費を考え、出来るだけbyte[]は使わないようにしてください.
 * 
 * </#if>
 * 
 * @author shot
 */
public class Direct extends StreamNavigation<Direct> {

	/**
	 * <#if locale="en">
	 * <p>
	 * Direct response from {@link File}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link File}を直接書き出す{@link Direct}を生成します.
	 * </p>
	 * </#if>
	 * 
	 * @param file
	 * @return
	 */
	public static Direct from(File file) {
		return new Direct(Assertion.notNull(file));
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Direct response from {@link File} with specific content type.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param file
	 * @param contentType
	 * @return
	 * @since 0.6.3
	 */
	public static Direct from(File file, String contentType) {
		return new Direct(Assertion.notNull(file)).setContentType(contentType);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Direct response from {@link InputStream}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link InputStream}を直接書き出す{@link Direct}を生成します.
	 * </p>
	 * </#if>
	 * 
	 * @param is
	 * @return
	 */
	public static Direct from(InputStream is) {
		return new Direct(Assertion.notNull(is));
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Direct response from {@link InputStream} with specific content type.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param is
	 * @param contentType
	 * @return
	 */
	public static Direct from(InputStream is, String contentType) {
		return new Direct(Assertion.notNull(is)).setContentType(contentType);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Direct response from {@link byte[]}.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link byte[]}を直接書き出す{@link Direct}を生成します.
	 * </p>
	 * </#if>
	 * 
	 * @param bytes
	 * @return
	 */
	public static Direct from(byte[] bytes) {
		return new Direct(Assertion.notNull(bytes));
	}

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Direct response from bytes with specific content type.
	 * </p>
	 * <#else>
	 * <p>
	 * </p>
	 * </#if>
	 * 
	 * @param bytes
	 * @param contentType
	 * @return
	 */
	public static Direct from(byte[] bytes, String contentType) {
		return new Direct(Assertion.notNull(bytes)).setContentType(contentType);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Constructor for file.
	 * </p>
	 * <#else>
	 * <p>
	 * ファイルを書き出すDirectのコンストラクタです.
	 * </p>
	 * </#if>
	 * 
	 * @param file
	 */
	public Direct(File file) {
		super(file);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Constructor for input stream.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link InputStream}を書き出すDirectのコンストラクタです.
	 * </p>
	 * </#if>
	 * 
	 * @param is
	 */
	public Direct(InputStream is) {
		super(is);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Constructor for bytes.
	 * </p>
	 * <#else>
	 * <p>
	 * byte[]を書き出すDirectのコンストラクタです.
	 * </p>
	 * </#if>
	 * 
	 * @param bytes
	 */
	public Direct(byte[] bytes) {
		super(bytes);
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Write resource to {@link HttpServletResponse} and immeatelly flush.
	 * </p>
	 * <#else>
	 * <p>
	 * {@link HttpServletResponse}に渡されたリソースを書き出します.
	 * </p>
	 * </#if>
	 */
	@Override
	public void execute(WebContext context) throws Exception {
		final HttpServletResponse res = context.getResponse()
				.getNativeResource();
		// TODO : if user cancel download, then should we log it instead of
		// print stack trace?
		ServletOutputStream sos = res.getOutputStream();
		super.writeTo(res, sos);
		sos.flush();
	}

}
