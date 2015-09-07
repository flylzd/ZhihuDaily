package com.lizeda.library.http;


import com.lizeda.library.core.utils.IOUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class XRequest {

    protected final HttpMethod method;
    protected final HttpUrl httpUrl;
    protected RequestParams params;
    protected byte[] body;
    protected Object tag;

//    public static XRequest head(final String url) {
//        return new XRequest(HttpMethod.HEAD, url);
//    }
//
//    public static XRequest get(final String url) {
//        return new XRequest(HttpMethod.GET, url);
//    }
//
//    public static XRequest delete(final String url) {
//        return new XRequest(HttpMethod.DELETE, url);
//    }
//
//    public static XRequest post(final String url) {
//        return new XRequest(HttpMethod.POST, url);
//    }
//
//    public static XRequest put(final String url) {
//        return new XRequest(HttpMethod.PUT, url);
//    }

    public XRequest(final XRequest source) {
        this.method = source.method;
        this.httpUrl = source.httpUrl;
        this.params = source.params;
        this.body = source.body;
        this.tag = source.tag;
//        this.listener = source.listener;
//        this.debug = source.debug;
    }

    public XRequest(final HttpMethod method, String url, Object tag) {
        this(method, url, new RequestParams(), tag);
    }

    public XRequest(final HttpMethod method, String url, final RequestParams params, Object tag) {
//        AssertUtils.notNull(method, "http method can not be null");
//        AssertUtils.notEmpty(url, "http url can not be null or empty");
//        AssertUtils.notNull(params, "http params can not be null");
        final HttpUrl hUrl = HttpUrl.parse(url);
//        AssertUtils.notNull(hUrl, "invalid url:" + url);
        this.method = method;
        this.httpUrl = HttpUrl.parse(url);
        this.params = new RequestParams(params);
        this.tag = tag;
    }


//    public XRequest progress(final ProgressListener listener) {
//        this.listener = listener;
//        return this;
//    }

    public XRequest userAgent(final String userAgent) {
        return header(HttpConsts.USER_AGENT, userAgent);
    }

    public XRequest authorization(final String authorization) {
        return header(HttpConsts.AUTHORIZATION, authorization);
    }

    public XRequest referer(final String referer) {
        return header(HttpConsts.REFERER, referer);
    }

    public XRequest header(String name, String value) {
        this.params.header(name, value);
        return this;
    }

    public XRequest headers(Map<String, String> headers) {
        if (headers != null) {
            this.params.headers(headers);
        }
        return this;
    }

    public XRequest query(String key, String value) {
//        AssertUtils.notEmpty(key, "key must not be null or empty.");
        this.params.query(key, value);
        return this;
    }

    public XRequest queries(Map<String, String> queries) {
        this.params.queries(queries);
        return this;
    }

    protected void throwIfNotSupportBody() {
        if (!supportBody()) {
            throw new IllegalStateException("HTTP " + method.name() + " not support http body");
        }
    }

    public XRequest form(String key, String value) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.params.form(key, value);
        }
        return this;
    }

    public XRequest forms(Map<String, String> forms) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.params.forms(forms);
        }
        return this;
    }

    public XRequest parts(Collection<BodyPart> parts) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            for (final BodyPart part : parts) {
                part(part);
            }
        }
        return this;
    }

    public XRequest file(String key, File file) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.params.file(key, file);
        }
        return this;
    }

    public XRequest file(String key, File file, String contentType) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.params.file(key, file, contentType);
        }
        return this;
    }

    public XRequest file(String key, File file, String contentType, String fileName) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.params.file(key, file, contentType, fileName);
        }
        return this;
    }

    public XRequest file(String key, byte[] bytes) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.params.file(key, bytes);
        }
        return this;
    }

    public XRequest file(String key, byte[] bytes, String contentType) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.params.file(key, bytes, contentType);
        }
        return this;
    }

    public XRequest body(final byte[] body) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.body = body;
        }
        return this;
    }

    public XRequest body(final String content, final Charset charset) {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.body = content.getBytes(charset);
        }
        return this;
    }

    public XRequest body(final File file) throws IOException {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.body = IOUtils.readBytes(file);
        }
        return this;
    }

    public XRequest body(final Reader reader) throws IOException {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.body = IOUtils.readBytes(reader);
        }
        return this;
    }

    public XRequest body(final InputStream stream) throws IOException {
//        throwIfNotSupportBody();
        if (supportBody()) {
            this.body = IOUtils.readBytes(stream);
        }
        return this;
    }

    public XRequest params(final RequestParams params) {
        if (params != null) {
            queries(params.queries);
            if (supportBody()) {
                forms(params.forms);
                parts(params.parts);
            }
        }
        return this;
    }

    public HttpUrl url() {
        return buildUrlWithQueries();
    }

    public HttpMethod method() {
        return method;
    }

    public String originalUrl() {
        return httpUrl.toString();
    }

//    public ProgressListener listener() {
//        return listener;
//    }

    protected boolean supportBody() {
        return HttpMethod.supportBody(method);
    }

    protected XRequest part(final BodyPart part) {
        this.params.parts.add(part);
        return this;
    }

    protected XRequest removeHeader(String key) {
        this.params.headers.remove(key);
        return this;
    }

    protected XRequest removeQuery(String key) {
        this.params.queries.remove(key);
        return this;
    }

    protected XRequest removeForm(String key) {
        this.params.forms.remove(key);
        return this;
    }

    protected XRequest removePart(BodyPart part) {
        this.params.parts.remove(part);
        return this;
    }

    protected Object getTag() {
        return this.tag;
    }

    protected String getHeader(String key) {
        return this.params.getHeader(key);
    }

    protected String getQuery(String key) {
        return this.params.getQuery(key);
    }

    protected String getForm(String key) {
        return this.params.getForm(key);
    }

    protected BodyPart getPart(String key) {
        return this.params.getPart(key);
    }

    protected boolean hasHeader(String key) {
        return getHeader(key) != null;
    }

    protected boolean hasQuery(String key) {
        return getQuery(key) != null;
    }

    protected boolean hasForm(String key) {
        return getForm(key) != null;
    }

    protected boolean hasPart(String key) {
        return getPart(key) != null;
    }

    protected int queriesSize() {
        return queries().size();
    }

    protected int formsSize() {
        return form().size();
    }

    protected int headersSize() {
        return headers().size();
    }

    protected int partsSize() {
        return parts().size();
    }

    protected Map<String, String> headers() {
        return this.params.headers;
    }

    protected Map<String, String> queries() {
        return this.params.queries;
    }

    protected Map<String, String> form() {
        return this.params.forms;
    }

    protected List<BodyPart> parts() {
        return this.params.parts;
    }

    protected boolean hasParts() {
        return this.params.parts.size() > 0;
    }

    protected boolean hasForms() {
        return this.params.forms.size() > 0;
    }


    HttpUrl buildUrlWithQueries() {
        final HttpUrl.Builder builder = httpUrl.newBuilder();
        for (final Map.Entry<String, String> entry : params.queries().entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    protected void copy(final XRequest source) {
        this.params = source.params;
        this.body = source.body;
        this.tag = source.tag;
//        this.listener = source.listener;
//        this.debug = source.debug;
    }

    protected RequestBody getRequestBody() throws IOException {
        if (!supportBody()) {
            return null;
        }
        if (body != null) {
            return RequestBody.create(HttpConsts.MEDIA_TYPE_OCTET_STREAM, body);
        }
        RequestBody requestBody;
        if (hasParts()) {
            final MultipartBuilder multipart = new MultipartBuilder();
            for (final BodyPart part : parts()) {
                if (part.getBody() != null) {
                    multipart.addFormDataPart(part.getName(), part.getFileName(), part.getBody());
                }
            }
            for (Map.Entry<String, String> entry : form().entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                multipart.addFormDataPart(key, value == null ? "" : value);
            }
            requestBody = multipart.type(MultipartBuilder.FORM).build();
        } else if (hasForms()) {
            final FormEncodingBuilder bodyBuilder = new FormEncodingBuilder();
            for (Map.Entry<String, String> entry : form().entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                bodyBuilder.add(key, value == null ? "" : value);
            }
            requestBody = bodyBuilder.build();
        } else {
            //FIXME workaround for null body, waiting OkHttp release
            requestBody = RequestBody.create(null, HttpConsts.NO_BODY);
        }
        return requestBody;
    }


    @Override
    public String toString() {
        return "Request{HTTP " + method + " " + httpUrl + '}';
    }
}
