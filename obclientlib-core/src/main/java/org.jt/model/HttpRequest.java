package org.jt.model;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private Map<String,String> headers;
    private String body;
    private HttpMethod method;
    private String url;

    public HttpMethod getMethod() {
        return method;
    }
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public HttpRequest withHeaders(final Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequest withBody(final String body) {
        this.body = body;
        return this;
    }

    public HttpRequest withMethod(final HttpMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequest withUrl(final String url) {
        this.url = url;
        return this;
    }
}
