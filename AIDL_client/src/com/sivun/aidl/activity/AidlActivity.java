package com.sivun.aidl.activity;

import java.util.Random;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sivun.aidl.aidl.IMyAIDL;
import com.sivun.aidl.client.R;

public class AidlActivity extends Activity implements OnClickListener {
	private BookConnection bookConn = new BookConnection();
	private IMyAIDL query;
	private TextView text;
	private Button button1, button2;
	private Random random;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.buttom1);
		button2 = (Button) findViewById(R.id.buttom2);
		text = (TextView) findViewById(R.id.text);
		random = new Random();
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		Log.e("AIDL", "onCreate");
	}

	@Override
	public void onClick(View v) {
		Log.e("AIDL", "onClick");
		if (v == button1) {
			if (query != null) {
				int k = random.nextInt(15);
				String re = "";
				try {
					re = query.getSomeThing(k);
					showLog4TextView("输入：" + k + "，结果：" + re);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			} else
				Log.e("AIDL", "query：" + (query == null));
		} else if (v == button2) {
			Intent service = new Intent("com.xxw.aidl_service.aidlService");
			bindService(service, bookConn, BIND_AUTO_CREATE);
		}
	}

	private final class BookConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("AIDL", "成功连接onServiceConnected()");
			showLog4TextView("成功连接服务");
			query = IMyAIDL.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e("AIDL", "失去连接onServiceDisconnected()");
			showLog4TextView("丢失服务连接");
			query = null;
		}

	}

	private void showLog4TextView(CharSequence log) {
		String s = text.getText().toString();
		text.setText(s + "\r\n" + log);
	}

}
