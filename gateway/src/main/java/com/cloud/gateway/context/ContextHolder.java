package com.cloud.gateway.context;

import java.util.Objects;

/**
 */
public class ContextHolder {
    private final static ThreadLocal<Context> data = new ThreadLocal<>();

    static public Context getContext() {
        Context context = data.get();
        if (Objects.isNull(context)) {
            context = new Context();
            data.set(context);
        }
        return context;
    }

    public static void clearContext() {
        data.remove();
    }
}
