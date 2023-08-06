package com.example.sockets_android_pdytr2021;

import java.util.concurrent.Executor;

public class Server extends NativeSocket {

    private int port = -1;

    public Server(Executor executor, int port) {
        super(executor);
        setPort(port);
    }

    public void runNativeSocket(ResponseCallback<Response> callback){
        executor.execute(() -> {
            Response value = runServer(port);
            callback.onComplete(value);
        });
    }

    public void setPort(int port) {
        this.port = port;
    }

    // Metodo nativo que esta implementado en la libreria nativa 'sockets_android_pdytr2021'
    public native Response runServer(int port);

}
