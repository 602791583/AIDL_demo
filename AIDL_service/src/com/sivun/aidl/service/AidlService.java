package com.sivun.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.sivun.aidl.aidl.IMyAIDL;

public class AidlService extends Service {

	private IBinder mIBinder = new MyBinder();
	

	@Override
	public IBinder onBind(Intent intent) {
		return mIBinder;
	}
	
	private class MyBinder extends IMyAIDL.Stub {

		@Override
		public IBinder asBinder() {
			return mIBinder;
		}

		@Override
		public String getSomeThing(int k) throws RemoteException {
			return "服务A--->"+k * k;
		}
	}
}
