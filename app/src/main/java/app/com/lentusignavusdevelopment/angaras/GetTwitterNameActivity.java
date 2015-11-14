package app.com.lentusignavusdevelopment.angaras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetTwitterNameActivity extends AppCompatActivity implements View.OnClickListener {

    String twitterName;
//    Button name;
    EditText twitterNameText;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_twitter_name);

        twitterNameText = (EditText) findViewById(R.id.editTwitterName);

        Button name = (Button) findViewById(R.id.button_submit_name);
        name.setOnClickListener(this);

        i = new Intent(GetTwitterNameActivity.this, MainActivity.class);


    }








    @Override
    public void onClick(View v) {
    i.putExtra("twitterName", twitterNameText.getText().toString());
        Log.e("Changing", "changed");
        startActivity(i);


    }
}
