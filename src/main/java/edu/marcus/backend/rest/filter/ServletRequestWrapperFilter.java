package edu.marcus.backend.rest.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.marcus.backend.util.HttpServletRequestUtils;
import edu.marcus.backend.util.HttpServletResponseUtils;

/**
 * Filter class responsible for intercept HTTP requests and decides if
 * request content and response content should be logged. Works because
 * of the inner classes {@link BufferedInputstreamHttpServletRequestWrapper} and
 * {@link BufferedOutputstreamHttpServletResponseWrapper}.
 */
@WebFilter("/api/*")
public class ServletRequestWrapperFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServletRequestWrapperFilter.class);
	private static final int LAST_POSSIBLE_SUCCESS_CODE = 299;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		BufferedInputstreamHttpServletRequestWrapper wrappedRequest =
				new BufferedInputstreamHttpServletRequestWrapper(httpServletRequest);
		BufferedOutputstreamHttpServletResponseWrapper wrappedResponse =
				new BufferedOutputstreamHttpServletResponseWrapper(httpServletResponse);
		try {
			chain.doFilter(wrappedRequest, wrappedResponse);
			wrappedResponse.flushBuffer();
		} finally {
			int statusCode = wrappedResponse.getStatus();
			/*
			 * If log level is info, then always log the request
			 * If log level is not info, then log the request only if HTTP status code if greater than 299
			 * Customize this when to log logic according to your needs
			 */
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(HttpServletRequestUtils.toString(wrappedRequest));
				byte[] payload = wrappedResponse.getCopy();
				LOGGER.info(HttpServletResponseUtils.toString(wrappedResponse, payload));
			} else if (statusCode > LAST_POSSIBLE_SUCCESS_CODE) {
				LOGGER.error(HttpServletRequestUtils.toString(wrappedRequest));
				byte[] payload = wrappedResponse.getCopy();
				LOGGER.error(HttpServletResponseUtils.toString(wrappedResponse, payload));
			}
		}
	}

	@Override
	public void destroy() {
	}

	/**
	 * Responsible for buffering the input stream of a HttpServletRequest so the stream can be read more than one time.
	 */
	private class BufferedInputstreamHttpServletRequestWrapper extends HttpServletRequestWrapper {

		private byte[] buffer;

		public BufferedInputstreamHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			if (buffer == null) {
				bufferInputStream();
			}
			return new CustomServletInputStream();
		}

		private void bufferInputStream() throws IOException {
			ServletInputStream is = super.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];
			int readBytes;
			while ((readBytes = is.read(temp)) > 0) {
				baos.write(temp, 0, readBytes);
			}
			buffer = baos.toByteArray();
		}

		@Override
		public BufferedReader getReader() throws IOException{
			return new BufferedReader(new InputStreamReader(getInputStream()));
		}
		
		private class CustomServletInputStream extends ServletInputStream {

			private ByteArrayInputStream is;

			public CustomServletInputStream() {
				is = new ByteArrayInputStream(buffer);
			}

			@Override
			public int available() throws IOException {
				return is.available();
			}

			@Override
			public void close() throws IOException {
				is.close();
			}

			@Override
			public synchronized void mark(int readlimit) {
				is.mark(readlimit);
			}

			@Override
			public boolean markSupported() {
				return is.markSupported();
			}

			@Override
			public int read(byte[] b) throws IOException {
				return is.read(b);
			}

			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				return is.read(b, off, len);
			}

			@Override
			public int read() throws IOException {
				return is.read();
			}

			@Override
			public synchronized void reset() throws IOException {
				is.reset();
			}

			@Override
			public long skip(long n) throws IOException {
				return is.skip(n);
			}
		}
	}
	
	/**
	 * Responsible for buffering the output stream of a HttpServletResponse so the stream can be read more than one time.
	 */
	private class BufferedOutputstreamHttpServletResponseWrapper extends HttpServletResponseWrapper {

		private ServletOutputStream os;
	    private PrintWriter writer;
	    private CustomServletOutputStream copier;
		
		public BufferedOutputstreamHttpServletResponseWrapper(HttpServletResponse response) {
			super(response);
		}
		
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
	        if (os == null) {
	            os = getResponse().getOutputStream();
	            copier = new CustomServletOutputStream(os);
	        }
	        return copier;
		}

		@Override
	    public PrintWriter getWriter() throws IOException {
	        if (writer == null) {
	            copier = new CustomServletOutputStream(getOutputStream());
	            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
	        }
	        return writer;
	    }
		
		@Override
	    public void flushBuffer() throws IOException {
	        if (writer != null) {
	            writer.flush();
	        } else if (os != null) {
	            copier.flush();
	        }
	    }

	    public byte[] getCopy() {
	        if (copier != null) {
	            return copier.getCopy();
	        } else {
	            return new byte[0];
	        }
	    }

		private class CustomServletOutputStream extends ServletOutputStream {

			private OutputStream os;
			private ByteArrayOutputStream copy;
			
			public CustomServletOutputStream(OutputStream os) {
				this.os = os;
				this.copy = new ByteArrayOutputStream(1024);
			}

			@Override
			public void write(int b) throws IOException {
				os.write(b);
				copy.write(b);
			}

			public byte[] getCopy() {
		        return copy.toByteArray();
		    }
			
		}
	}

}
