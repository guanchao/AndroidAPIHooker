package com.shuwoom.apihooker;

import java.util.ArrayList;
import java.util.List;

import com.shuwoom.apihooker.hookers.NetworkHooker;

public class SubstrateMain {
	//the packages below are ingonred  
	public static final List<String> FILTERED_PACKAGE_NAMES = new ArrayList<String>();

	static {
		FILTERED_PACKAGE_NAMES.add("android");
		FILTERED_PACKAGE_NAMES.add("com.android.phone");
		FILTERED_PACKAGE_NAMES.add("com.android.systemui");
		FILTERED_PACKAGE_NAMES.add("com.android.settings");
		FILTERED_PACKAGE_NAMES.add("com.android.inputmethod.latin");
		FILTERED_PACKAGE_NAMES.add("com.android.launcher");
		FILTERED_PACKAGE_NAMES.add("com.android.email");
		FILTERED_PACKAGE_NAMES.add("com.android.deskclock");
		FILTERED_PACKAGE_NAMES.add("com.android.mms");
		FILTERED_PACKAGE_NAMES.add("com.android.calendar");
		FILTERED_PACKAGE_NAMES.add("com.android.providers.downloads");
		FILTERED_PACKAGE_NAMES.add("com.noshufou.android.su");
		FILTERED_PACKAGE_NAMES.add("com.android.providers.calendar");
		FILTERED_PACKAGE_NAMES.add("com.android.providers.settings");
		FILTERED_PACKAGE_NAMES.add("com.android.contacts");
		FILTERED_PACKAGE_NAMES.add("com.android.providers.contacts");
		FILTERED_PACKAGE_NAMES.add("com.android.providers.userdictionary");
		FILTERED_PACKAGE_NAMES.add("com.android.exchange");
		FILTERED_PACKAGE_NAMES.add("com.android.providers.media");
		FILTERED_PACKAGE_NAMES.add("com.android.browser");
		FILTERED_PACKAGE_NAMES.add("com.android.quicksearchbox");
		FILTERED_PACKAGE_NAMES.add("com.saurik.substrate");
		FILTERED_PACKAGE_NAMES.add("com.shuwoom.apihooker");
	}

	
	public static void initialize(){
		new NetworkHooker().hook();
		new GlobalContextHooker().hook(FILTERED_PACKAGE_NAMES);
	}

}
