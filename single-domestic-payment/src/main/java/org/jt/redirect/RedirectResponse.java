package org.jt.redirect;

public class RedirectResponse {
    private int code;
    private String message;

    public RedirectResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
