package com.sap.cloud.samples.ariba.discovery.rfx.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Utils class for HTTP responses.
 *
 */
public class HttpResponseUtils {

	private static final String DEBUG_CONVERTING_HTTP_ENTITY = "Converting HTTP entity...";
	private static final String DEBUG_CONVERTED_HTTP_ENTITY = "Converted HTTP entity.";

	private static final String ERROR_ENTITY_CANNOT_BE_NULL = "Entity cannot be null.";
	private static final String ERROR_CALL_RETURNED_MESSAGE = "Call returned [{0}] {1}";
	private static final String ERROR_PROBLEM_OCCURED_WHILE_CONVERTING_RESPONSE_ENTITY_TO_CLASS_MESSAGE = "Problem occured while converting response entity to class [{0}].";

	private static Gson gson = new Gson();

	private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtils.class);

	/**
	 * Validates HTTP response against an array of target HTTP statuses. If the
	 * response's status is not among the target statuses, the response is not
	 * valid.
	 * 
	 * @param httpResponse
	 *            the HTTP response to be validated.
	 * @param targetStatus
	 *            array of target HTTP status codes.
	 * @return the actual HTTP status code.
	 * @throws UnsuccessfulOperationException
	 *             when the actual HTTP status code is not among the target
	 *             status codes.
	 */
	public static int validateHttpStatusResponse(CloseableHttpResponse httpResponse, Integer... targetStatus)
			throws HttpResponseException {
		Integer responseStatusCode = httpResponse.getStatusLine().getStatusCode();
		String responseMessage = httpResponse.getStatusLine().getReasonPhrase();

		if (!Arrays.asList(targetStatus).contains(responseStatusCode)) {
			String errorMessage = MessageFormat.format(ERROR_CALL_RETURNED_MESSAGE, responseStatusCode,
					responseMessage);
			logger.error(errorMessage);
			throw new HttpResponseException(errorMessage);
		}

		return responseStatusCode;
	}

	/**
	 * Converts an HTTP response entity to a given class.
	 * 
	 * @param entity
	 *            the HTTP response entity to be converted.
	 * @param cls
	 *            the target class.
	 * @return the response converted to the target class.
	 * @throws HttpResponseException
	 *             when the response cannot be converted to the given entity
	 *             class.
	 */
	public static <T> T convertHttpResponse(HttpEntity entity, Class<T> cls) throws HttpResponseException {
		if (entity == null) {
			logger.error(ERROR_ENTITY_CANNOT_BE_NULL);

			throw new IllegalArgumentException(ERROR_ENTITY_CANNOT_BE_NULL);
		}

		T result = null;
		try {
			logger.debug(DEBUG_CONVERTING_HTTP_ENTITY);
			result = gson.fromJson(EntityUtils.toString(entity, StandardCharsets.UTF_8), cls);
		} catch (ParseException | IOException e) {
			String errorMessage = MessageFormat
					.format(ERROR_PROBLEM_OCCURED_WHILE_CONVERTING_RESPONSE_ENTITY_TO_CLASS_MESSAGE, cls.getName());
			logger.error(errorMessage);
			throw new HttpResponseException(errorMessage, e);
		}

		logger.debug(DEBUG_CONVERTED_HTTP_ENTITY);
		return result;
	}
}
