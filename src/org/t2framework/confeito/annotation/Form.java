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

import org.t2framework.confeito.spi.FormResolver;
import org.t2framework.confeito.spi.impl.FormResolverImpl;

/**
 * <#if locale="en">
 * <p>
 * &#064;Form is a parameter annotation to inject object as POJO from submitted
 * request.This annotation is usually used with &#064;POST.
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
public @interface Form {

	/**
	 * 
	 * <#if locale="en">
	 * <p>
	 * Set resolver class for resolve mapping request parameters to object.
	 * </p>
	 * <#else>
	 * <p>
	 * 
	 * </p>
	 * </#if>
	 * 
	 * @return resolver class
	 */
	Class<? extends FormResolver> resolverClass() default FormResolverImpl.class;
}
