package com.shuwoom.apihooker.hookers.interfaces;

import java.lang.reflect.GenericDeclaration;

public interface HookerListener {
	
	public void before(String className, GenericDeclaration method, Object resources, Object...args);
	
	
	public void after(String className, GenericDeclaration method, Object resources, Object...args);
}
