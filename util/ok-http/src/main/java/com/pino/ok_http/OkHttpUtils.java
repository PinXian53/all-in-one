package com.pino.ok_http;

import com.pino.exception.InternalServerErrorException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class OkHttpUtils {

    private static final Headers defaultHeader = Headers.of("Content-Type", "application/json");

    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static final OkHttpClient okHttpclient = new OkHttpClient.Builder()
        .cookieJar(new CookieJar() {
            public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                cookieStore.put(httpUrl.host(), list);
            }

            @NotNull @Override
            public List<Cookie> loadForRequest(@NotNull HttpUrl httpurl) {
                var cookies = cookieStore.get(httpurl.host());
                return cookies != null ? cookies : new ArrayList<>();
            }
        }).build();

    private OkHttpUtils() {}

    public static String getRequest(String uri) throws IOException {
        return getRequest(uri, null);
    }

    public static String getRequest(String uri, Map<String, String> params) throws IOException {
        return getRequest(uri, params, null);
    }

    public static String getRequest(String uri, Map<String, String> params, Map<String, String> headers)
        throws IOException {
        var httpBuilder = Objects.requireNonNull(HttpUrl.parse(uri)).newBuilder();
        if (params != null) {
            for (var param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        var request = new Request.Builder()
            .url(httpBuilder.build())
            .headers(getHeaders(headers))
            .get()
            .build();
        return sendRequest(request);
    }

    public static String postRequest(String uri, String queryJson) throws IOException {
        return postRequest(uri, queryJson, null);
    }

    public static String postRequest(String uri, String queryJson, Map<String, String> headers)
        throws IOException {
        var body = RequestBody.create(queryJson, MediaTypes.APPLICATION_JSON);
        var request = new Request.Builder()
            .url(uri)
            .headers(getHeaders(headers))
            .post(body)
            .build();
        return sendRequest(request);
    }

    public static String postRequest(
        String uri, Map<String, String> formDataParams,
        Map<String, String> headers)
        throws IOException {
        var request = new Request.Builder()
            .url(uri)
            .headers(getHeaders(headers))
            .post(getFormBody(formDataParams))
            .build();
        return sendRequest(request);
    }

    public static String deleteRequest(String uri) throws IOException {
        var request = new Request.Builder()
            .url(uri)
            .headers(defaultHeader)
            .delete()
            .build();
        return sendRequest(request);
    }

    private static Headers getHeaders(Map<String, String> headerMap) {
        Map<String, String> currentHeaderMap = new HashMap<>();
        // 加上預設 Header
        for (int i = 0; i < defaultHeader.size(); i++) {
            currentHeaderMap.put(defaultHeader.name(i), defaultHeader.value(i));
        }

        if (headerMap != null) {
            currentHeaderMap.putAll(headerMap);
        }
        return Headers.of(currentHeaderMap);
    }

    private static FormBody getFormBody(Map<String, String> formDataParams) {
        var formBody = new FormBody.Builder();
        if (formDataParams != null) {
            for (Map.Entry<String, String> header : formDataParams.entrySet()) {
                formBody.add(header.getKey(), header.getValue());
            }
        }
        return formBody.build();
    }

    private static String sendRequest(Request request) throws IOException {
        int statusCode;
        String responseBody;
        try (Response response = okHttpclient.newCall(request).execute()) {
            statusCode = response.code();
            responseBody = null;
            if (response.body() != null) {
                responseBody = Objects.requireNonNull(response.body()).string();
            }
        }
        var msg = String.format("Url: %s, Status Code: %s, Response Body: %s", request.url(), statusCode, responseBody);
        if (statusCode != 200 && statusCode != 204) {
            throw new InternalServerErrorException(msg);
        }
        return responseBody;
    }
}
