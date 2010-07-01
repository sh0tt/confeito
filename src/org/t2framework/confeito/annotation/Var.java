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
package org.t2framework.confeito.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <#if locale="en">
 * <p>
 * &#064;Var is a parameter annotation to notify the annotated parameter is part
 * of url.If requested url is like http://www.example.com/conext-root/var/123,
 * and you would like to get '123' part.The code is like below:
 * 
 * <pre>
 * &#064;RequestScope
 * &#064;Page(&quot;var/{aaa}&quot;)
 * public class VarPage {
 * 
 *  &#064;Default
 *  public Navigation index(@Var(&quot;aaa&quot;) String str) {//
 * 
 * </pre>
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
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Inherited
public @interface Var {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Variable key.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return the variable key
	 */
	String value();

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Accept null or not.Default is null is acceptable.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return true if the variable is accepted as null, otherwise false
	 */
	boolean nullable() default true;
}
