package mobiili.ohjelmointi.lab4tehtava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.enums.ReadyState;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, ChatClientInterface{

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    ChatClient chatClient;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView =(ListView) findViewById(R.id.listView);

        String[] items = {};
        arrayList = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<>(this,R.layout.lista,R.id.texlist,arrayList);
        listView.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(this);

        findViewById(R.id.connection).setOnClickListener(this);







    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button) {

            if (chatClient != null && chatClient.getReadyState() == ReadyState.OPEN) {

                EditText messagebox = findViewById(R.id.messageBox);

                chatClient.send(messagebox.getText().toString());
            }
            else{
                Toast.makeText(this,"There is no connection! Please,make the connection first!",Toast.LENGTH_LONG).show();
            }
        }

        if(v.getId() == R.id.connection) {
            try {
                chatClient = new ChatClient(new URI("ws://obscure-waters-98157.herokuapp.com"), this);
                chatClient.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                adapter.add(message);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onStatusChange(final String newStatus) {


        runOnUiThread(new Runnable() {  //Metodikutsu, jolla varmistetaan,että käyttöliittymän päivitys toimii oikein
            @Override
            public void run() {
                TextView tvStatus = findViewById(R.id.tvStatus);
                tvStatus.setText(newStatus);
            }
        });



    }
}
