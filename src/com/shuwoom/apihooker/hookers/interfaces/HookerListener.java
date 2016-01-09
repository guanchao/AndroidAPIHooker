package com.shuwoom.apihooker.hookers.interfaces;

import java.lang.reflect.GenericDeclaration;

import com.shuwoom.apihooker.common.InterceptEvent;

public interface HookerListener {
	
	public void before(String className, GenericDeclaration method, Object resources, InterceptEvent event);
	
	public void after(String className, GenericDeclaration method, Object resources, InterceptEvent event);
}
