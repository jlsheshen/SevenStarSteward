package com.sevenstar.steward2016.manager;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.HttpTask;
import org.xutils.http.RequestParams;

import com.sevenstar.steward2016.callback.BaseSimpleHttpCallback;

/**
 * 网络请求管理类
 */
public class HttpSendMananger {

	public static void sendPost(String url, BaseSimpleHttpCallback callback, String... paramsStr) {
		RequestParams params = new RequestParams(url);
		if (paramsStr != null && paramsStr.length > 0) {
			for (int i = 0, size = paramsStr.length; i < size; i++) {
				params.addQueryStringParameter(paramsStr[i], paramsStr[++i]);
			}
		}
		x.http().post(params, callback);
	}

	public static void getFile(String url, Callback.ProgressCallback<File> callback) {
		RequestParams params = new RequestParams(url);
		x.http().get(params, callback);
	}

	public static void sendPostFile(String url, BaseSimpleHttpCallback callback, String fileName, File file, String... paramsStr) {
		RequestParams params = new RequestParams(url);
		params.addBodyParameter(fileName, file);
		if (paramsStr != null && paramsStr.length > 0) {
			for (int i = 0, size = paramsStr.length; i < size; i++) {
				params.addQueryStringParameter(paramsStr[i], paramsStr[++i]);
			}
		}
		x.http().post(params, callback);
	}

	public static void sendPost(String url, int page, Callback.CommonCallback<String> callback, String... paramsStr) {
		RequestParams params = new RequestParams(url);
		params.addQueryStringParameter("page", String.valueOf(page));
		if (paramsStr != null && paramsStr.length > 0) {
			for (int i = 0, size = paramsStr.length; i < size; i++) {
				params.addQueryStringParameter(paramsStr[i], paramsStr[++i]);
			}
		}
		x.http().post(params, callback);
	}

}
