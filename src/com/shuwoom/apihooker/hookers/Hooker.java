package com.shuwoom.apihooker.hookers;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.saurik.substrate.MS;
import com.saurik.substrate.MS.MethodPointer;
import com.shuwoom.apihooker.ApplicationConfig;
import com.shuwoom.apihooker.common.Config;
import com.shuwoom.apihooker.common.InterceptEvent;
import com.shuwoom.apihooker.hookers.interfaces.HookerListener;

public abstract class Hooker {
	
	private String hookerName = "";
	private String packageName;
	private final long startTimestamp = Calendar.getInstance().getTime().getTime();
	private Handler handler;
	
	private static final int MSG_EVENT = 0x01;
	private Messenger mService = null;
	boolean isConn = false;
	
	public Hooker(String hookerName) {
	    this.hookerName = hookerName;
	    
	 }
	
	private ServiceConnection conn = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = new Messenger(service);
			isConn = true;
			try {
				Message msg = Message.obtain(null, 3);
				msg.arg1 = 999;
				mService.send(msg);
			} catch (RemoteException e) {
				Log.v(Config.DEBUG_CONNECT_TAG, e.toString());
				e.printStackTrace();
			}
			Log.v(Config.DEBUG_CONNECT_TAG, "conncted!!!!!!!!!!!");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			isConn = false;
			Log.v(Config.DEBUG_CONNECT_TAG, "disconncted!!!!!!!!!!!");
		}
		
	};
	
	public abstract void hook();
	
//	private HookerListener hookListener = new HookerListener(){
//
//		@Override
//		public void before(String className, GenericDeclaration method,
//				Object resources, InterceptEvent event) {
//			Log.v(Config.DEBUG_TAG, "before:{'time':'"+df.format(now)+"', 'method':'"+className + "." + ((Method)method).getName()+"'}");
//		}
//
//		@Override
//		public void after(String className, GenericDeclaration method,
//				 Object resources, InterceptEvent event) {
//			Log.v(Config.DEBUG_TAG, "after:{'time':'"+df.format(now)+"', 'method':'"+className + "." + ((Method)method).getName()+"'}");
//		
//			
//		}
//
//    	
//    };

	protected void hookMethods(final HookerListener listener, final String className, final Map<String, Integer> methodsToHook){
		
		MS.hookClassLoad(className, new MS.ClassLoadHook() {
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void classLoaded(final Class<?> classToHook) {
				Map<GenericDeclaration, String> allMethods = new HashMap<GenericDeclaration, String>();
				try {
					Class clz = Class.forName(className);
					ApplicationConfig.setPackageName(clz.getPackage().getName() + "." + clz.getSimpleName());
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
					
					final int intrusiveLevel = methodsToHook.get(methodName);
					
					final MS.MethodPointer<Object, Object> old = new MethodPointer<Object, Object>();
					MS.hookMethod(classToHook, method, new MS.MethodHook() {

						@Override
						public Object invoked(Object resources, Object... args)
								throws Throwable {
							
							InterceptEvent event = 
									new InterceptEvent(hookerName, intrusiveLevel, System.identityHashCode(resources), "unknown", className, methodName);
							
							if( args != null){
								for(Object arg : args){
									if(arg != null){
										String argValue = arg.toString();
										event.addParameter(arg.getClass().getName(), argValue);
									}else{
										event.addParameter(null, null);
									}
								}
							}
							
							if(ApplicationConfig.getPackageName() != null){
								event.setPackageName(ApplicationConfig.getPackageName());
							}
							
							final Context appContext = ApplicationConfig.getContext();
							if(appContext != null){
								doBindService(appContext);
								Log.v(Config.DEBUG_TAG, "hook context from****************>" + appContext.getPackageName());
							}
							
							if(listener != null){
								listener.before(className, method, resources, event);
							}
							
							Object result = old.invoke(resources, args);
							
							if(result != null){
								event.setReturns(result.getClass().getName(), result.toString());
							}else{
								event.setReturns(null, null);
							}
							Log.v(Config.DEBUG_TAG, event.toJson());
							insertEvent(event);
							
							if(listener != null){
								listener.after(className, method, resources, event);
							}
							
							return result;
						}
						
					}, old);
				}
			}
		});
	}
	
	private void doBindService(Context appContext) {
		Intent intent = new Intent();
        intent.setAction("com.shuwoom.aidl.hooker");
        appContext.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	private void insertEvent(InterceptEvent event) {
		long relativeTimestamp = event.getTimestamp() - this.startTimestamp;
	    event.setRelativeTimestamp(relativeTimestamp);
	    
	    if(isConn){
	    	try {
	    		Message msg = Message.obtain(null, MSG_EVENT);
	    		msg.replyTo = mService;
	    		Bundle eventToSend = new Bundle();
	    		
	    		eventToSend.putParcelable("eventKey", event);
	    		msg.setData(eventToSend);
				mService.send(msg);
			} catch (RemoteException e) {
				Log.v(Config.DEBUG_TAG, e.toString());
				e.printStackTrace();
			}
	    }
	}
}
