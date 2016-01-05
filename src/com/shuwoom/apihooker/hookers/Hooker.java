package com.shuwoom.apihooker.hookers;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

import com.saurik.substrate.MS;
import com.saurik.substrate.MS.MethodPointer;
import com.shuwoom.apihooker.common.Config;
import com.shuwoom.apihooker.hookers.interfaces.HookerListener;

public abstract class Hooker {

	public abstract void hook();

	protected void hookMethods(final HookerListener listener, final String className, final Map<String, Integer> methodsToHook){
		MS.hookClassLoad(className, new MS.ClassLoadHook() {
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void classLoaded(Class<?> classToHook) {
				Map<GenericDeclaration, String> allMethods = new HashMap<GenericDeclaration, String>();
				
				try {
					Class clz = Class.forName(className);
					
					for(Method method : clz.getMethods()){
						allMethods.put(method, method.getName());
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
				Iterator iterator = allMethods.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry entry = (Map.Entry) iterator.next(); 
					final Method method = (Method) entry.getKey();
					final String methodName = (String)entry.getValue();
					
					if(!methodsToHook.containsKey(methodName))
						continue;
					
					Log.v(Config.DEBUG_TAG, "start to hook " + className + "." + methodName);
					
					final MS.MethodPointer<Object, Object> old = new MethodPointer<Object, Object>();
					MS.hookMethod(classToHook, method, new MS.MethodHook() {

						@Override
						public Object invoked(Object resources, Object... args)
								throws Throwable {
							
							if(listener != null){
								listener.before(className, method, resources, args);
							}
							
							Object result = old.invoke(resources, args);
							
							if(listener != null){
								listener.after(className, method, resources, args);
							}
							return result;
						}

					}, old);
				}
			}
		});
	}
}
