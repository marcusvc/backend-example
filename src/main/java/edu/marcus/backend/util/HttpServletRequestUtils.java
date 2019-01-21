package edu.marcus.backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Formats the content of a {@link HttpServletRequest} into a readble string.
 */
public final class HttpServletRequestUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpServletRequestUtils.class);

	private HttpServletRequestUtils() {
	}

	public static String toString(HttpServletRequest request) {
		if (request != null) {
			String key;
			Object value;
			StringBuilder sb = new StringBuilder();
			sb.append("\nREQUEST:\n");
			sb.append(String.format("http-method: %s%n", request.getMethod()));
			sb.append(String.format("url: %s%n", request.getRequestURL()));
			sb.append(String.format("query-string: %s%n", request.getQueryString()));
			Enumeration<String> headersEnum = request.getHeaderNames();
			while (headersEnum.hasMoreElements()) {
				key = headersEnum.nextElement();
				value = request.getHeader(key);
				sb.append(String.format("header[%s->%s]%n", key, value));
			}
			Enumeration<String> attributesEnum = request.getAttributeNames();
			while (attributesEnum.hasMoreElements()) {
				key = attributesEnum.nextElement();
				value = request.getAttribute(key);
				sb.append(String.format("attrib[%s->%s]%n", key, value));
			}
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
				if (bufferedReader.ready()) {
					sb.append("Payload:\n");
					Stream<String> lines = bufferedReader.lines();
					lines.forEach(sb::append);
				} else {
					sb.append("Payload: n\\a");
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			return sb.toString();
		}
		return "";
	}

}
