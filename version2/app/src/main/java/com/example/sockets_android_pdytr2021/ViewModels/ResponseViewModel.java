package com.example.sockets_android_pdytr2021.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sockets_android_pdytr2021.NativeSocket;
import com.example.sockets_android_pdytr2021.Response;

public class ResponseViewModel extends ViewModel implements ViewModelProvider.Factory {

    private MutableLiveData<Response> clientResponse;
    private MutableLiveData<Response> serverResponse;


    public LiveData<Response> getServerResponse(NativeSocket socket){
        serverResponse = new MutableLiveData<>();
        socket.runNativeSocket(resp -> {
            serverResponse.postValue(resp);
        });

        return serverResponse;
    }

    public LiveData<Response> getClientResponse(NativeSocket socket){
        clientResponse = new MutableLiveData<>();
        socket.runNativeSocket(resp -> {
            clientResponse.postValue(resp);
        });

        return clientResponse;
    }

}

/*class ResponseViewModelFactory implements ViewModelProvider.Factory {
    private NativeSocket mParam;

    public ResponseViewModelFactory(NativeSocket param) {
        mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ResponseViewModel(mParam);
    }
}*/
