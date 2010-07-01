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
 * &#064;Index is a parameter annotation to inject submitted index of button in
 * the foreach kind of situation. The view code is like this:
 * 
 * TODO : expression for hogeList, s, e.
 * 
 * <pre>
 * 	&lt;c:forEach var=&quot;e&quot; items=&quot; {hogeList}&quot; varStatus=&quot;s&quot;&gt;
 * 		&lt;input type=&quot;submit&quot; name=&quot;hoge[ {s.index}]&quot; value=&quot; {e}&quot;/&gt;
 * 		&lt;br /&gt;
 * 	&lt;/c:forEach&gt;
 * </pre>
 * 
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
@Target( { ElementType.PARAMETER })
@Inherited
public @interface Index {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Index variable.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return index key
	 */
	String value() default "index";
}
