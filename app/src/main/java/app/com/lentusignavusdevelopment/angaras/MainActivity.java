package app.com.lentusignavusdevelopment.angaras;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pusher.client.Pusher;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText messageInput;
    FloatingActionButton fab;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageAdapter = new MessageAdapter(this, new ArrayList<app.com.lentusignavusdevelopment.angaras.Message>());
        final ListView messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        //get input fields by id
         messageInput = (EditText) findViewById(R.id.message_input);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        //initialize Pusher
        Pusher pusher = new Pusher("913987a1757ec97a7259");

        //subscribe to "messages channel
        com.pusher.client.channel.Channel channel = pusher.subscribe("messages");

        channel.bind("new_message", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Message message = gson.fromJson(data, Message.class);

                    messageAdapter.add(message);

                    //have the ListView scroll down to the new message
                    messagesView.setSelection(messageAdapter.getCount() - 1);
                }
            });
            }
        });

        //connect to Pusher API
        pusher.connect();



//                setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                    }
//                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        postMessage();
    }

    public void postMessage(){
    String text = messageInput.getText().toString();

        // return if blank
        if (text.equals("")){
            return;
        }


        RequestParams params = new RequestParams();

        //set our JSON object
        params.put("text", text);
        params.put("name", "tupac_shareem");
        params.put("time", new Date().getTime());

        //Create Async Client
        AsyncHttpClient client = new AsyncHttpClient();

        client.post("http://192.168.1.16:3000", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageInput.setText("");
                        Toast.makeText(
                                getApplicationContext(),
                                "Something went right :)",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(
                        getApplicationContext(),
                        "Something went wrong :(",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        }


    }

