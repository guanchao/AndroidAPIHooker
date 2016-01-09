package com.shuwoom.apihooker.common;

import java.util.Observable;
import java.util.Observer;

public abstract class DataWatcher implements Observer{

	@Override
	public void update(Observable observable, Object data) {
		if(data instanceof InterceptEvent){
			onDataChanged((InterceptEvent) data);
		}
	}
	
	public abstract void onDataChanged(InterceptEvent downloadEntry);

}
