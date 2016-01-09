package com.shuwoom.apihooker;

import android.content.Context;

public class ApplicationConfig {
	private static String packageName;
	private static Context context;

	public static String getPackageName() {
		return packageName;
	}

	public static void setPackageName(String packageName) {
		ApplicationConfig.packageName = packageName;
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		ApplicationConfig.context = context;
	}

}
