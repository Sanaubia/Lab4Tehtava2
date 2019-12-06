package mobiili.ohjelmointi.lab4tehtava2;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface ChatClientInterface {
    void onMessage(String message);
    void onStatusChange(String newStatus);
}


public class ChatClient extends WebSocketClient {

    ChatClientInterface observer;

    public ChatClient(URI serverUri, ChatClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        observer.onStatusChange("Connection Open");
    }

    @Override
    public void onMessage(String message) {

        observer.onStatusChange("New Message" +message);
        observer.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {


        observer.onStatusChange("Connection Closed");
    }

    @Override
    public void onError(Exception ex) {

        observer.onStatusChange("ERROR! Something went Wrong, check Log! " +ex.toString());

    }
}
