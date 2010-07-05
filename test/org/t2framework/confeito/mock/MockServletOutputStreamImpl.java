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

import java.io.IOException;
import java.io.OutputStream;

/**
 * <#if locale="en">
 * <p>
 * An implementation class of {@link MockServletOutputStream}.
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
public class MockServletOutputStreamImpl extends MockServletOutputStream {

	protected OutputStream outputStream;

	public MockServletOutputStreamImpl(final OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void write(final int b) throws IOException {
		outputStream.write(b);
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

}
