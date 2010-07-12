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
package org.t2framework.confeito.parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.t2framework.confeito.action.ActionContext;
import org.t2framework.confeito.util.Assertion;

/**
 * <#if locale="en">
 * <p>
 * Abstract base {@link ParameterResolver}.
 * </p>
 * <#else>
 * <p>
 * {@link ParameterResolver} の抽象ベースクラスです.
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
public abstract class AbstractParameterResolver implements ParameterResolver {

	/**
	 * <#if locale="en">
	 * <p>
	 * Target class of this resolver.
	 * </p>
	 * <#else>
	 * <p>
	 * このリゾルバーが対象とするクラス.
	 * </p>
	 * </#if>
	 */
	protected Class<?> targetClass;

	/**
	 * <#if locale="en">
	 * <p>
	 * Target annotation class for this resolver.
	 * </p>
	 * <#else>
	 * <p>
	 * このリゾルバーが対象とするアノテーションのクラス.
	 * 
	 * </p>
	 * </#if>
	 */
	protected Class<? extends Annotation> targetAnnotationClass;

	/**
	 * <#if locale="en">
	 * <p>
	 * If parameter annotation has annotation that is
	 * targetAnnotationClass,return true.And if parameter class is assignable
	 * from targetClass,return true.
	 * </p>
	 * <#else>
	 * <p>
	 * パラメーターのアノテーション中にtargetAnnotationClassのアノテーションがある場合、trueを返します.
	 * またパラメータのクラスがtargetClassもしくはtargetClassのサブクラスの場合、trueを返します.
	 * </p>
	 * </#if>
	 */
	@Override
	public boolean isTargetParameter(ActionContext actionContext, Method md,
			int paramIndex, Annotation[] paramAnnotations, Class<?> paramClass) {
		Assertion.notNulls(paramAnnotations, paramClass);
		if (isTargetAnnotationClass(targetAnnotationClass, paramAnnotations)) {
			return true;
		}
		if (isTargetClass(targetClass, paramClass)) {
			return true;
		}
		return false;
	}

	protected boolean isTargetClass(Class<?> targetClass, Class<?> paramClass) {
		return targetClass != null && paramClass.isAssignableFrom(targetClass);
	}

	protected boolean isTargetAnnotationClass(
			Class<? extends Annotation> targetAnnotationClass,
			Annotation[] paramAnnotations) {
		Annotation annotation = findTargetAnnotation(paramAnnotations,
				targetAnnotationClass);
		if (annotation != null) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	protected <A> A findTargetAnnotation(Annotation[] annotations,
			Class<?> targetAnnotationClass) {
		if (annotations == null || targetAnnotationClass == null) {
			return null;
		}
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == targetAnnotationClass) {
				return (A) annotation;
			}
		}
		return null;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set target class for this resolver.
	 * </p>
	 * <#else>
	 * <p>
	 * このリゾルバーの処理対象のクラスをセットします.
	 * </p>
	 * </#if>
	 * 
	 * @param targetClass
	 */
	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Set target annotation class for this resolver.
	 * </p>
	 * <#else>
	 * <p>
	 * このリソルバーの処理対象のアノテーションのクラスをセットします.
	 * </p>
	 * </#if>
	 * 
	 * @param targetAnnotationClass
	 */
	public void setTargetAnnotationClass(
			Class<? extends Annotation> targetAnnotationClass) {
		this.targetAnnotationClass = targetAnnotationClass;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get target class.
	 * </p>
	 * <#else>
	 * <p>
	 * 処理対象のクラスを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return target class
	 */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * <#if locale="en">
	 * <p>
	 * Get target annotation class.
	 * </p>
	 * <#else>
	 * <p>
	 * 処理対象のアノテーションのクラスを返します.
	 * </p>
	 * </#if>
	 * 
	 * @return target annotation class
	 */
	public Class<?> getTargetAnnotationClass() {
		return targetAnnotationClass;
	}

}