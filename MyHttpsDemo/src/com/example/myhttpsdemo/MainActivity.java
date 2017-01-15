package com.example.myhttpsdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 安卓使用httpsdemo
 * 
 * @author lin
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread() {
			public void run() {
				try {
					getConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

	}

	@SuppressWarnings("static-access")
	private void getConnection() throws IOException, KeyManagementException, NoSuchAlgorithmException,
			UnrecoverableKeyException, CertificateException, KeyStoreException {
		// TODO Auto-generated method stub
		// https://github.com/shenglintang?tab=repositories
		// http://blog.csdn.net/lin_t_s
		URL url = new URL("https://github.com/shenglintang?tab=repositories");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5 * 1000);
		connection.setReadTimeout(5 * 1000);
		connection.setRequestMethod("GET");

		// 得到sslContext对象，有两种情况：1.需要安全证书，2.不需要安全证书
		Log.e("geek", "url==" + url);
		Log.e("geek", "是否为https请求==" + (connection instanceof HttpsURLConnection));
		if (connection instanceof HttpsURLConnection) {// 判断是否为https请求
			SSLContext sslContext = HttpsUtil.getSSLContextWithCer();
//			 SSLContext sslContext = HttpsUtil.getSSLContextWithoutCer();
			if (sslContext != null) {
				SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
				((HttpsURLConnection) connection).setDefaultSSLSocketFactory(sslSocketFactory);
//				((HttpsURLConnection) connection).setHostnameVerifier(HttpsUtil.hostnameVerifier);
			}
		}

		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			Log.e("geek", "responseCode==" + responseCode);
			InputStream is = connection.getInputStream();
			Log.e("geek", "is==" + is);
			is.close();
		}
		connection.disconnect();
	}
}
