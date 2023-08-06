package com.example.sockets_android_pdytr2021;

import java.util.concurrent.Executor;

public class Server extends NativeSocket {

    private int port = -1;
    private String ipAddr = null;

    public Server(Executor executor, String ipAddr, int port) {
        super(executor);
        setPort(port);
        this.ipAddr = ipAddr;
    }

    public void runNativeSocket(ResponseCallback<Response> callback){
        executor.execute(() -> {
            Response value = runServer(ipAddr, port);
            callback.onComplete(value);
        });
    }

    public void setPort(int port) {
        this.port = port;
    }

    // Metodo nativo que esta implementado en la libreria nativa 'sockets_android_pdytr2021'
    public native Response runServer(String ipAddr, int port);

}
