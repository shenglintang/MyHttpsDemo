package com.example.myhttpsdemo;

import android.app.Application;

public class MyApplication extends Application {
	public static Application mApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;

	}

	public static Application getApplication() {
		return mApplication;
	}
}
