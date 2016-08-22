package com.sun.stock.core.util.http;

import com.google.gson.Gson;
import com.sun.stock.core.informations.InformationHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zksun on 16-2-12.
 */
public class HttpGet {

	private final static int DEFAULT_RETRY_TIMES = 3;

	private final static int DEFAULT_HTTP_PORT = 80;

	private HttpClient client;

	private GetMethod getMethod;

	private List<NameValuePair> parameters;

	private volatile boolean active = true;

	public static HttpGet getHttpGetInstance(String url) {
		return getHttpGetInstance(url, DEFAULT_HTTP_PORT, DEFAULT_RETRY_TIMES, null);
	}

	private String createGetMethodPath(String url, int port, String path) {
		if (!url.startsWith("http://")) {
			url = "http://" + url + ":" + port;
		}

		if (StringUtils.isNoneBlank(path)) {
			url = url + path;
		}

		return url;
	}

	public static HttpGet getHttpGetInstance(String url, int port, int retry, String path) {
		if (StringUtils.isBlank(url)) {
			throw new NullPointerException("no url");
		}
		HttpGet httpGet = new HttpGet();
		httpGet.client = new HttpClient();
		httpGet.getMethod = new GetMethod(httpGet.createGetMethodPath(url, port, path));
		httpGet.getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retry, false));
		return httpGet;
	}

	public HttpGet setParameters(String parameterName, String value) {
		if (StringUtils.isBlank(parameterName) || StringUtils.isBlank(value)) {
			throw new NullPointerException();
		}
		synchronized (this) {
			if (CollectionUtils.isEmpty(parameters)) {
				parameters = new ArrayList<>();
			}
		}
		parameters.add(new NameValuePair(parameterName, value));
		return this;
	}

	public HttpGet setPath(String path) {
		if (StringUtils.isBlank(path)) {
			throw new NullPointerException("no path");
		}
		this.getMethod.setPath(this.getMethod.getPath() + path);
		return this;
	}

	public byte[] getHttpResponse() throws IOException {
		if (!active) {
			throw new UnsupportedOperationException("connection released, please get new HttpGet instance");
		}
		if (CollectionUtils.isNotEmpty(parameters)) {
			this.getMethod.setQueryString(parameters.toArray(new NameValuePair[parameters.size()]));
		}

		try {
			int code = this.client.executeMethod(this.getMethod);
			if (code != HttpStatus.SC_OK) {
				throw new RuntimeException("http error");
			}
			return this.getMethod.getResponseBody();
		} finally {
			this.getMethod.releaseConnection();
			this.active = false;
		}
	}

	public <T> T getHttpResponse(InformationHandler<T, byte[]> informationHandler) throws IOException {
		if (null == informationHandler) {
			throw new NullPointerException("no information handler");
		}
		if (!active) {
			throw new UnsupportedOperationException("connection released, please get new HttpGet instance");
		}
		if (CollectionUtils.isNotEmpty(parameters)) {
			this.getMethod.setQueryString(parameters.toArray(new NameValuePair[parameters.size()]));
		}

		try {
			int code = this.client.executeMethod(this.getMethod);
			if (code != HttpStatus.SC_OK) {
				throw new RuntimeException("http error");
			}
			return informationHandler.getInformation(this.getMethod.getResponseBody());
		} finally {
			this.getMethod.releaseConnection();
			this.active = false;
		}
	}

	public <T> T getHttpStringResponse(InformationHandler<T, String> informationHandler) throws IOException {
		if (null == informationHandler) {
			throw new NullPointerException("no information handler");
		}
		byte[] responseBytes = getHttpResponse();
		if (null == responseBytes) {
			return null;
		}
		return informationHandler.getInformation(new String(responseBytes));
	}

	public String getHttpStringResponse() throws IOException {
		byte[] responseBytes = getHttpResponse();
		if (null == responseBytes) {
			return null;
		}
		return new String(responseBytes);
	}


	public <T> T getHttpObjectResponse(Class<T> clazz) throws IOException {
		return new Gson().fromJson(getHttpStringResponse(), clazz);
	}
}
