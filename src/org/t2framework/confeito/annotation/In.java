package org.t2framework.confeito.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.t2framework.confeito.adapter.ContainerAdapter;

/**
 * 
 * <#if locale="en">
 * <p>
 * Inject parameter annotation using {@link ContainerAdapter} for T2.
 * </p>
 * <#else>
 * <p>
 * </p>
 * </#if>
 * 
 * @author shot
 * 
 */
@Target( { PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface In {
}
