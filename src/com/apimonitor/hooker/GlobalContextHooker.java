package com.apimonitor.hooker;

import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;

import com.apimonitor.common.ApplicationConfig;
import com.saurik.substrate.MS;

public class GlobalContextHooker {

	public void hook(final List<String> filteredPackageNames) {
		MS.hookClassLoad("android.app.ContextImpl", new MS.ClassLoadHook() {

			@SuppressWarnings("unchecked")
			@Override
			public void classLoaded(Class<?> resources) {
				loadContext(resources, filteredPackageNames);
			}
		});
	}

	@SuppressWarnings("unchecked")
	protected void loadContext(Class<?> resources,
			final List<String> filteredPackageNames) {
		Method getPackageNameMethod = null;
		try {
			getPackageNameMethod = resources.getMethod("getPackageName",
					new Class<?>[] {});
		} catch (NoSuchMethodException e1) {
		}

		if (getPackageNameMethod == null) {
			return;
		}

		final MS.MethodPointer<Object, Object> oldPointer = new MS.MethodPointer<Object, Object>();
		MS.hookMethod(resources, getPackageNameMethod, new MS.MethodHook() {

			@Override
			public Object invoked(Object arg0, Object... arg1) throws Throwable {
				String packageName = (String) oldPointer.invoke(arg0, arg1);
				ApplicationConfig.setPackageName(packageName);

				if (filteredPackageNames.contains(packageName)) {
					return oldPointer.invoke(arg0, arg1);
				}
				try {
					Class<?> contextImplClass = Class
							.forName("android.app.ContextImpl");
					Class<?> noparams[] = {};
					Method getApplicationContextMethod = contextImplClass
							.getDeclaredMethod("getApplicationContext",
									noparams);

					Context context = (Context) getApplicationContextMethod
							.invoke(arg0);
					if (context != null) {
						ApplicationConfig.setContext(context);
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
				}
				return oldPointer.invoke(arg0, arg1);
			}
		}, oldPointer);

	}
}
