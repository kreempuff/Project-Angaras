package app.com.lentusignavusdevelopment.angaras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetTwitterNameActivity extends AppCompatActivity implements View.OnClickListener {

    String twitterName;
    Button name;
    EditText twitterNameText;
    EditText channelNameText;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_twitter_name);

        twitterNameText = (EditText) findViewById(R.id.editTwitterName);
        channelNameText = (EditText) findViewById(R.id.editChannelName);

        Button name = (Button) findViewById(R.id.submit_button);
        name.setOnClickListener(this);




    }








    @Override
    public void onClick(View v) {
        if(twitterNameText.getText().toString() == "" || channelNameText.getText().toString() == ""){
            return;
        }
        i = new Intent(GetTwitterNameActivity.this, MainActivity.class);
        i.putExtra("twitterName", twitterNameText.getText().toString());
        i.putExtra("channelName", channelNameText.getText().toString());

        startActivity(i);


    }
}
