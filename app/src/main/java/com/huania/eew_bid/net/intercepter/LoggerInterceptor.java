package com.huania.eew_bid.net.intercepter;


import com.huania.eew_bid.utils.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Formatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志拦截器
 */
@SuppressWarnings("ALL")
public class LoggerInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    public LoggerInterceptor() {
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final int id = ID_GENERATOR.incrementAndGet();
        {
            final String LOG_PREFIX = "[" + id + " request]";
            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;

            Connection connection = chain.connection();
            Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            Logger.INSTANCE.v(LOG_PREFIX + requestStartMessage);

            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
//				if (requestBody.contentType() != null) {
//					Logger.INSTANCE.v(LOG_PREFIX + "Content-Type: " + requestBody.contentType());
//				}
//				if (requestBody.contentLength() != -1) {
//					Logger.INSTANCE.v(LOG_PREFIX + "Content-Length: " + requestBody.contentLength());
//				}
            }

//			Headers headers = request.headers();
//			for (int i = 0, count = headers.size(); i < count; i++) {
//				String name = headers.name(i);
//				// Skip headers from the request body as they are explicitly iged above.
//				if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
//					Logger.INSTANCE.v(LOG_PREFIX + name + ": " + headers.value(i));
//				}
//			}

//			if (!hasRequestBody || requestBody instanceof MultipartBody) {
//				Logger.INSTANCE.v(LOG_PREFIX + "--> END " + request.method());
//			} else if (bodyEncoded(request.headers())) {
//				Logger.INSTANCE.v(LOG_PREFIX + "--> END " + request.method() + " (encoded body omitted)");
//			} else {
//				Buffer buffer = new Buffer();
//				requestBody.writeTo(buffer);
//
//				Charset charset = UTF8;
//				MediaType contentType = requestBody.contentType();
//				if (contentType != null) {
//					charset = contentType.charset(UTF8);
//				}
//
//				Logger.INSTANCE.v(LOG_PREFIX + "--> END " + request.method() + " (binary "
//						+ requestBody.contentLength() + "-byte body omitted)");
//			}
        }

        {
            final String LOG_PREFIX = "[" + id + " response]";
            long startNs = System.nanoTime();
            Response response = null;
            try {
                response = chain.proceed(request);
                long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

                ResponseBody responseBody = response.body();
                long contentLength = responseBody.contentLength();
                Logger.INSTANCE.v(LOG_PREFIX + "<-- " + response.code() + ' ' + response.message() + ' ' + response.request().url() + " (" + tookMs + "ms" + "" + ')');

                Headers headers = response.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    Logger.INSTANCE.v(LOG_PREFIX + headers.name(i) + ": " + headers.value(i));
                }

                if (!HttpHeaders.hasBody(response)) {
                    Logger.INSTANCE.v(LOG_PREFIX + "<-- END HTTP");
                } else if (bodyEncoded(response.headers())) {
                    Logger.INSTANCE.v(LOG_PREFIX + "<-- END HTTP (encoded body omitted)");
                } else {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();

                    Charset charset = UTF8;
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        try {
                            charset = contentType.charset(UTF8);
                        } catch (UnsupportedCharsetException e) {
                            Logger.INSTANCE.v(LOG_PREFIX + "");
                            Logger.INSTANCE.v(LOG_PREFIX + "Couldn't decode the response body; charset is likely malformed.");
                            Logger.INSTANCE.v(LOG_PREFIX + "<-- END HTTP");
                            return response;
                        }
                    }

                    if (contentLength != 0) {

                        final String bufferString = buffer.clone().readString(charset);
                        Logger.INSTANCE.v(LOG_PREFIX + bufferString);
                        if (contentType != null && "json".equals(contentType.subtype())) {
                            Logger.INSTANCE.v(LOG_PREFIX + "\n" + new Formatter().format(bufferString));
                        }
                    }

                    Logger.INSTANCE.v(LOG_PREFIX + "<-- END HTTP (" + buffer.size() + "-byte body)");
                }
            } catch (Exception e) {
                Logger.INSTANCE.v(LOG_PREFIX + "<-- HTTP FAILED: " + e);
                e.printStackTrace();
            }
            return response;
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

}
