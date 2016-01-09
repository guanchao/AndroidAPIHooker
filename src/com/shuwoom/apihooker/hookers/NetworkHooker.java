package com.shuwoom.apihooker.hookers;

import java.util.HashMap;
import java.util.Map;

public class NetworkHooker extends Hooker{
	
	public static final String NAME = "Network";

	public NetworkHooker() {
		super(NetworkHooker.NAME);
	}

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
	    
	    hookMethods(null, className, methodsToHook);
	}
	
}
