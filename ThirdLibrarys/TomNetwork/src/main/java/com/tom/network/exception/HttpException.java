package com.tom.network.exception;

import com.tom.network.model.Response;
import com.tom.network.utils.HttpUtils;

public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 8773734741709178425L;

    //HTTP status code

    private int code;

    //HTTP status message

    private String message;

    //The full HTTP response. This may be null if the exception was serialized

    private transient Response<?> response;

    public HttpException(String message) {
        super(message);
    }

    public HttpException(Response<?> response) {
        super(getMessage(response));
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    private static String getMessage(Response<?> response) {
        HttpUtils.checkNotNull(response, "response == null");
        return "HTTP " + response.code() + " " + response.message();
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public Response<?> response() {
        return response;
    }

    public static HttpException NET_ERROR() {
        return new HttpException("network error! http response code is 404 or 5xx!");
    }

    public static HttpException COMMON(String message) {
        return new HttpException(message);
    }
}
