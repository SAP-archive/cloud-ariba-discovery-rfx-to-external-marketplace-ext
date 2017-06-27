package com.sap.cloud.samples.ariba.discovery.rfx.api;

import org.apache.http.HttpException;

/**
 * HTTP response exception.
 *
 */
public class HttpResponseException extends HttpException {

	private static final long serialVersionUID = 6801244928635636544L;

	public HttpResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpResponseException(String message) {
		super(message);
	}

}
