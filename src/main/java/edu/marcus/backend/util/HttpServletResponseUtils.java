package edu.marcus.backend.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Formats the content of a {@link HttpServletResponse} into a readble string.
 */
public final class HttpServletResponseUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpServletResponseUtils.class);

	private HttpServletResponseUtils() {
	}

	public static String toString(HttpServletResponse response, byte[] payload) {
		if (response != null) {
			String key;
			Object value;
			StringBuilder sb = new StringBuilder();
			sb.append("\nRESPONSE:\n");
			sb.append(String.format("status-code: %s%n", response.getStatus()));
			sb.append(String.format("character-encoding: %s%n", response.getCharacterEncoding()));
			sb.append(String.format("content-type: %s%n", response.getContentType()));
			sb.append(String.format("buffer-size: %s%n", response.getBufferSize()));
			sb.append(String.format("content-length: %s%n", payload.length));
			Collection<String> headerNames = response.getHeaderNames();
			for (String headerName : headerNames) {
				key = headerName;
				value = response.getHeaders(key);
				sb.append(String.format("header[%s->%s]%n", key, value));
			}
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(payload)))) {
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
