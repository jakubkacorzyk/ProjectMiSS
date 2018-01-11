package miss.missproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by lazar81ba on 15.12.2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void outdoor(View view){
        Intent aboutScreen = new Intent(this, MapsActivity.class);
        this.startActivity(aboutScreen);
    }
    public void indoor(View viev){
        Intent aboutScreen = new Intent(this,MapsIndoorActivity.class);
        this.startActivity(aboutScreen);
    }
}
