package org.t2framework.confeito.model;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

public class ComponentTest extends TestCase {

	public void test1() throws Exception {
		Component<Hoge> hoge = new Component<ComponentTest.Hoge>(Hoge.class);
		assertNotNull(hoge.getComponentClass());
		Collection<Property> properties = hoge.getProperties();
		assertTrue(properties.size() == 2);
		List<Method> methods = hoge.getMethods();
		System.out.println(methods);
		assertTrue(methods.size() == 1);
	}
	
	public static class Hoge {
		
		protected String name;
		
		protected int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
		public String invoke(){
			return "";
		}
	}
}
