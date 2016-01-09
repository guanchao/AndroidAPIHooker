package com.shuwoom.apihooker;

import android.app.Activity;
import android.os.Bundle;
import android.os.Messenger;
import android.widget.TextView;

import com.shuwoom.apihooker.common.DataChanger;
import com.shuwoom.apihooker.common.DataWatcher;
import com.shuwoom.apihooker.common.InterceptEvent;

public class TestActivity extends Activity {

	private TextView eventText;
	private Messenger mService = null;
	private StringBuilder msg = new StringBuilder();

	private DataWatcher dataWatcher = new DataWatcher() {

		@Override
		public void onDataChanged(InterceptEvent data) {
			if(eventText != null && data != null){
				StringBuilder str = new StringBuilder();
				str.append("HookerName : " + data.getHookerName() + "\n");
				str.append("PackageName : " + data.getPackageName() + "\n");
				str.append("MethodName : " + data.getMethodName() + "\n");
				str.append("Timestamp : " + data.getTimestamp() + "\n");
				str.append("Data : " + data.getData() + "\n\n");
				msg.insert(0, str);
				eventText.setText(msg.toString());
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		eventText = (TextView) findViewById(R.id.event_text);

	}

	@Override
	protected void onResume() {
		super.onResume();
		DataChanger.getInstance(this).addObserver(dataWatcher);
	}

	@Override
	protected void onPause() {
		super.onPause();
		DataChanger.getInstance(this).deleteObserver(dataWatcher);
	}
}
