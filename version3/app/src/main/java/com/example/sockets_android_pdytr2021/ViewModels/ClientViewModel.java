package com.example.sockets_android_pdytr2021.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientViewModel extends ViewModel {

    private MutableLiveData<String> clientMsg;

    public MutableLiveData<String> getMsg() {
        if (clientMsg == null) {
            clientMsg = new MutableLiveData<>();
        }
        return clientMsg;
    }

    public void clearMsg(){
        clientMsg = null;
    }

}
