package com.shuwoom.apihooker.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.shuwoom.apihooker.common.Config;
import com.shuwoom.apihooker.common.DataChanger;
import com.shuwoom.apihooker.common.InterceptEvent;

public class ReceiverService extends Service{
	
	private static final int MSG_EVENT = 0x01;
	
	private Messenger messenger = new Messenger(new Handler(){

		@Override
		public void handleMessage(Message msgFromClient) {
			switch(msgFromClient.what){
			case MSG_EVENT:
				msgFromClient.getData().setClassLoader(InterceptEvent.class.getClassLoader());
				InterceptEvent event = (InterceptEvent)msgFromClient.getData().getParcelable("eventKey");
				if(event != null){
					if(event.getPackageName().equals("com.kugou.android")){
						Log.v(Config.DEBUG_CONNECT_TAG, "receive event msg from client=>" + event.toJson());
//						DataChanger.getInstance(getApplication()).update(event);
					}
				}
				break;
			case 3:
				Log.v(Config.DEBUG_CONNECT_TAG, "receive connect msg from client");
				break;
			}
			super.handleMessage(msgFromClient);
		}
	});

	@Override
	public IBinder onBind(Intent intent) {
		return messenger.getBinder();
	}
}
