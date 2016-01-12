package com.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil {
	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(15000); // 超时时间，默认为10s
	}

	public static void get(String url, AsyncHttpResponseHandler res) {
		client.get(url, res);
	}

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler res) {
		client.get(url, params, res);
	}

	public static void get(String url, JsonHttpResponseHandler res) {
		client.get(url, res);
	}

	public static void get(String url, RequestParams params, JsonHttpResponseHandler res) {
		client.get(url, params, res);
	}

	public static void get(String url, BinaryHttpResponseHandler bHandler) {
		client.get(url, bHandler);
	}

	public static void post(String url, AsyncHttpResponseHandler res) {
		client.post(url, res);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler res) {
		client.post(url, params, res);
	}

	public static void post(String url, JsonHttpResponseHandler res) {
		client.get(url, res);
	}

	public static void post(String url, RequestParams params, JsonHttpResponseHandler res) {
		client.post(url, params, res);
	}

	public static AsyncHttpClient getClient() {
		return client;
	}
}
