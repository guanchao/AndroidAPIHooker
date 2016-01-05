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

public class NetworkHooker extends Hooker{
	

	@Override
	public void hook() {
		hookIOBridgeClass();
	}
	
	/**
	 * IoBridge.java 
	 * https://android.googlesource.com/platform/libcore/+/android-4.1.1_r1/luni/src/main/java/libcore/io/IoBridge.java
	 */
	private void hookIOBridgeClass(){
		final String className = "libcore.io.IoBridge";
		
		final Map<String, Integer> methodsToHook = new HashMap<String, Integer>();
		// methodsToHook.put("open", 1); This is in common with filesystem operation.
	    methodsToHook.put("recvfrom", 1);
	    // methodsToHook.put("read", 1);

	    // methodsToHook.put("write", 2);
	    methodsToHook.put("sendto", 2);

	    methodsToHook.put("getSocketLocalAddress", 1);
	    methodsToHook.put("getSocketLocalPort", 1);

	    methodsToHook.put("closeSocket", 1);
	    methodsToHook.put("connectErrno", 1);
	    methodsToHook.put("connect", 2);
	    methodsToHook.put("bind", 1);
	    
	    hookMethods(new HookerListener(){

			@Override
			public void before(String className, GenericDeclaration method,
					Object resources, Object... args) {
				Date now = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Log.v(Config.DEBUG_TAG, "before:{'time':'"+df.format(now)+"', 'method':'"+className + "." + ((Method)method).getName()+"'}");
			
				
			}

			@Override
			public void after(String className, GenericDeclaration method,
					Object resources, Object... args) {
				Date now = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Log.v(Config.DEBUG_TAG, "after:{'time':'"+df.format(now)+"', 'method':'"+className + "." + ((Method)method).getName()+"'}");
			}

	    	
	    }, className, methodsToHook);
	}
	
}
