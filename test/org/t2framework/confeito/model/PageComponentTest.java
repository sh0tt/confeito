package org.t2framework.confeito.model;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.t2framework.confeito.annotation.ActionParam;
import org.t2framework.confeito.annotation.ActionPath;
import org.t2framework.confeito.annotation.Ajax;
import org.t2framework.confeito.annotation.Default;
import org.t2framework.confeito.annotation.GET;
import org.t2framework.confeito.annotation.POST;
import org.t2framework.confeito.annotation.Page;
import org.t2framework.confeito.spi.Navigation;
import org.t2framework.confeito.urltemplate.UrlTemplate;


public class PageComponentTest extends TestCase{

	public static Set<Class<? extends Annotation>> actionAnnotationSet = new HashSet<Class<? extends Annotation>>();

	static {
		actionAnnotationSet.add(ActionParam.class);
		actionAnnotationSet.add(ActionPath.class);
		actionAnnotationSet.add(GET.class);
		actionAnnotationSet.add(POST.class);
		actionAnnotationSet.add(Ajax.class);
		actionAnnotationSet.add(Default.class);
	}

	public void test1() throws Exception {
		PageComponent component = new PageComponent(HogePage.class, new UrlTemplate("/hoge"), actionAnnotationSet);
		assertNotNull(component.getDefaultMethod());
		
		ActionMethod actionMethodDesc = component.getActionMethod();
		assertNotNull(actionMethodDesc);
	}
	
	@Page
	public static class HogePage {
		
		@POST
		@ActionPath
		public Navigation foo(){
			return null;
		}
		
		@Default
		public Navigation hoge(){
			return null;
		}
	}
}
