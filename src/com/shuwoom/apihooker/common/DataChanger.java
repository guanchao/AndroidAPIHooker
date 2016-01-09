package com.shuwoom.apihooker.common;

import java.util.Observable;

import android.content.Context;

public class DataChanger extends Observable {
	private static DataChanger mInstance;
	private final Context context;

	private DataChanger(Context context) {
		this.context = context;
	}

	public synchronized static DataChanger getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DataChanger(context);
		}
		return mInstance;
	}

	public void update(InterceptEvent event) {
		setChanged();
		notifyObservers(event);
	}
}
