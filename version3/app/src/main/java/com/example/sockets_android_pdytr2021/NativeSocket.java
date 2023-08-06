package com.example.sockets_android_pdytr2021;

import java.util.concurrent.Executor;

public abstract class NativeSocket {

    protected final Executor executor;

    public NativeSocket(Executor executor){
        this.executor = executor;
    }

    public abstract void runNativeSocket(ResponseCallback<Response> callback);

}
