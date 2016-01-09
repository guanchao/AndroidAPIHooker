package com.shuwoom.apihooker.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class ReceiverService extends Service{
	
	private static final int MSG_EVENT = 0x01;
	
	private Messenger messenger = new Messenger(new Handler(){

		@Override
		public void handleMessage(Message msgFromClient) {
			switch(msgFromClient.what){
			case MSG_EVENT:
				Log.v("wgc", "receive msg from client 11111111111111111");
				break;
			case 3:
				Log.v("wgc", "receive msg from client 333333333333");
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
