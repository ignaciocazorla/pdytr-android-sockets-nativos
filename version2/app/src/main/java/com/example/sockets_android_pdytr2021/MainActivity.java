package com.example.sockets_android_pdytr2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sockets_android_pdytr2021.ViewModels.ClientViewModel;
import com.example.sockets_android_pdytr2021.ViewModels.ResponseViewModel;
import com.example.sockets_android_pdytr2021.databinding.ActivityMainBinding;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'sockets_android_pdytr2021' library on application startup.
    static {
        System.loadLibrary("sockets_android_pdytr2021");
    }

    private ActivityMainBinding binding;

    // Componentes de IU
    private Button startServerButton;
    private Button startClientButton;

    private ClientViewModel clientViewModel;
    private ResponseViewModel responseViewModel;
    private Executor executor;

    private int destPort;
    private int serverPort;

    private CardView serverInfoCardView;
    private CardView serverResponseCardView;
    private TextView serverPortInput;
    private TextView serverResponseStatusTextView;
    private TextView serverResponseContentTextView;
    private Button serverInfoCardViewCloseButton;
    private Button serverResponseCardViewCloseButton;

    private CardView clientInfoCardView;
    private CardView clientResponseCardView;
    private TextView clientPortInput;
    private TextView clientResponseStatusTextView;
    private TextView clientResponseContentTextView;
    private Button clientInfoCardViewCloseButton;
    private Button clientResponseCardViewCloseButton;
    private Button clientSendMsgButton;

    private TextView editClientMsgInput;
    private CardView promptEnterMsgCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executor = ((MyApplication) getApplication()).executorService;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeUIComponents();

        responseViewModel = new ViewModelProvider(this).get(ResponseViewModel.class);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        // Boton para interactuar con el servidor
        startServerOnClickListener();

        // Boton para interactuar con el cliente
        startClientOnClickListener();

    }

    public void initializeUIComponents(){
        startServerButton = binding.startServerButton;
        startClientButton = binding.startClientButton;

        promptEnterMsgCardView = binding.promptEnterMsgCardView;
        clientSendMsgButton = binding.sendClientMsgButton;

        clientInfoCardView = binding.clientInfoCardView;
        clientInfoCardViewCloseButton = binding.clientInfoCardViewButton;
        clientPortInput = binding.clientDestPortInput;
        clientResponseCardView = binding.clientResponseCardView;
        clientResponseStatusTextView = binding.clientResponseStatusTextView;
        clientResponseContentTextView  = binding.clientResponseContentTextView;
        clientResponseCardViewCloseButton = binding.clientResponseCardViewButton;
        editClientMsgInput = binding.editClientMsgTextView;

        serverInfoCardView = binding.serverInfoCardView;
        serverInfoCardViewCloseButton = binding.serverInfoCardViewCloseButton;
        serverPortInput = binding.serverPortInput;
        serverResponseCardView = binding.serverResponseCardView;
        serverResponseStatusTextView = binding.serverResponseStatusTextView;
        serverResponseContentTextView = binding.serverResponseContentTextView;
        serverResponseCardViewCloseButton = binding.serverResponseCardViewButton;
    }

    public void startServerOnClickListener(){
        startServerButton.setOnClickListener(v -> {
            serverPort = getPortInput(serverPortInput);
            if(serverPort > 0) {
                changeButtonEnabledState(startServerButton);
                showInfoCardView(serverInfoCardView,serverInfoCardViewCloseButton);

                Server server = new Server(executor, serverPort);

                responseViewModel.getServerResponse(server).observe(this, response -> {
                    closeInfoCardView(serverInfoCardView);
                    setResponseCardView(serverResponseCardView, serverResponseStatusTextView, serverResponseContentTextView, response, serverResponseCardViewCloseButton, startServerButton);
                });
            }
        });
    }

    public void startClientOnClickListener(){
        startClientButton.setOnClickListener(v -> {
            destPort = getPortInput(clientPortInput);
            if(destPort > 0){
                changeButtonEnabledState(startClientButton);
                getClientMsg();

                clientViewModel.getMsg().observe(this, s -> {
                    showInfoCardView(clientInfoCardView, clientInfoCardViewCloseButton);
                    sendClientMsg(s);
                    clientViewModel.clearMsg();
                });
            }
        });
    }

    public int getPortInput(TextView in){
        String portString = in.getText().toString();
        int port = -1;

        if(portString.length() == 0){
            Toast.makeText(this, "Ingrese el puerto!", Toast.LENGTH_LONG).show();
        }else {
            port = Integer.parseInt(portString);
        }
        return port;
    }

    public void getClientMsg(){
        promptEnterMsgCardView.setVisibility(View.VISIBLE);
        clientSendMsgButton.setOnClickListener(w -> {
            String msg = editClientMsgInput.getText().toString();
            if(checkInput(msg)){
                promptEnterMsgCardView.setVisibility(View.INVISIBLE);
                editClientMsgInput.setText("");
                clientViewModel.getMsg().setValue(msg);
            }
        });
    }

    private Boolean checkInput(String msg){
        if(msg.length() == 0){
            Toast.makeText(this, "Primero ingrese un mensaje!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(msg.length() > 255) {
            Toast.makeText(this, "Máximo de caracteres permitidos es 255! Caracteres ingresados: " + String.valueOf(msg.length()), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void sendClientMsg(String msg){
        Client client = new Client(executor, msg, destPort);

        responseViewModel.getClientResponse(client).observe(this, response -> {
            closeInfoCardView(clientInfoCardView);
            setResponseCardView(clientResponseCardView, clientResponseStatusTextView, clientResponseContentTextView, response, clientResponseCardViewCloseButton, startClientButton);
            //Log.d("Valor de retorno (CLI)", String.valueOf(response.getPayload()));
        });
    }


    private void setResponseCardView(CardView responseCardView, TextView responseStatusTextView, TextView responseMsgTextView, Response response, Button closeButton, Button processTypeButton){
        responseCardView.setVisibility(View.VISIBLE);
        responseStatusTextView.setText(String.valueOf(response.getStatus()));
        responseMsgTextView.setText(response.getPayload());
        setCardViewBackgroundColor(response.getStatus(), responseCardView);
        closeCardViewOnClickListener(closeButton, responseCardView, processTypeButton);
    }

    public void showInfoCardView(CardView infoCardView, Button infoCloseButton){
        infoCardView.setVisibility(View.VISIBLE);
        closeCardViewOnClickListener(infoCloseButton,infoCardView,null);

    }

    private  void closeInfoCardView(CardView infoCardView){
        if (infoCardView.getVisibility() == View.VISIBLE) {
            infoCardView.setVisibility(View.INVISIBLE);
        }
    }

    public void setCardViewBackgroundColor(int status,View view){
        if(status == 0){
            view.setBackgroundColor(getResources().getColor(R.color.ok_color));
        }else{
            view.setBackgroundColor(getResources().getColor(R.color.error_color));
        }
    }

    public void closeCardViewOnClickListener(Button button, CardView cardView, Button processTypeButton){
        button.setOnClickListener(v -> {
            cardView.setVisibility(View.INVISIBLE);
            if (processTypeButton != null) {
                changeButtonEnabledState(processTypeButton);
            }
        });
    }

    public void changeButtonEnabledState(Button button){
        button.setEnabled(!button.isEnabled());
    }

}