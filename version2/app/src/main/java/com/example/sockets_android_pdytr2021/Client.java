package com.example.sockets_android_pdytr2021;

import java.util.concurrent.Executor;

public class Client extends NativeSocket{

    private String msg;
    private int destPort = -1;

    public Client(Executor executor, String msg, int port) {
        super(executor);
        setMsg(msg);
        setDestPort(port);
    }

    public void runNativeSocket(ResponseCallback<Response> callback) {
        executor.execute(() -> {
            Response value = runClient(destPort, msg);
            callback.onComplete(value);
        });
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // Metodo nativo que esta implementado en la libreria nativa 'sockets_android_pdytr2021'
    public native Response runClient(int port, String msg);
}

