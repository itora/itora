package com.github.itora.account;

import com.github.itora.request.Request;

public interface RequestHandler {

    void accept(Request request);

    default void accept(Request... requests) {
        for (Request request: requests) {
            accept(request);
        }
    }
}
