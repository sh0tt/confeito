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
 * 
 * <pre>
 * &#064;ActionParam is an method annotation for action method which is invoked by T2 
 * if submitted the name attribute is same as method name.
 * At page class(it's just POJO), only you need is add ActionParam to the method 
 * which you would like to call from user action(usually it goes with @POST).
 * Here is how to use:
 * &#064;POST
 * &#064;ActionParam
 * public Navigation add(WebContext context) {
 *     //do something
 * }
 *  
 * Then, you just use submit button named &quot;add&quot;:
 * &lt;input type=&quot;submit&quot; name=&quot;add&quot; value=&quot;go&quot;/&gt;
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
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface ActionParam {

	String value() default "";

}
