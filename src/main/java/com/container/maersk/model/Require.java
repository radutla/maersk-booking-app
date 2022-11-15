package com.container.maersk.model;

import javax.annotation.Nullable;

public final class Require {

    private Require() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T requireNonNull(@Nullable final T t, final String msg) {
        if (t == null) {
            throw new IllegalArgumentException(msg);
        }
        return t;
    }
}
