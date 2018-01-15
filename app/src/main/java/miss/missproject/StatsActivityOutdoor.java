package miss.missproject;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by komputer on 13.01.2018.
 */

public class StatsActivityOutdoor extends AppCompatActivity {

    private List<LatLng> coordinates = new LinkedList<>();
    private List<Location> locations = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar);
        //Back button on toolbar
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        Intent i = getIntent();
        coordinates = (List<LatLng>) i.getSerializableExtra("ListCoordinates");
        locations = (List<Location>) i.getSerializableExtra("ListLocation");
        distance();
        altitude();
        speed();
        time();
    }

    private void distance(){
        TextView t=(TextView)findViewById(R.id.textView2);
        StringBuilder message = new StringBuilder();
        message.append("Przebyty dystans: ");
        message.append(calculateDistance());
        message.append(" m");
        t.setText(message.toString());
    }
    private float calculateDistance(){
        Location tmp = locations.get(0);
        float sum = 0;
        for(Location location : locations ){
            sum += tmp.distanceTo(location);
            tmp = location;
        }
        return sum;
    }


    private void altitude(){
        TextView t=(TextView)findViewById(R.id.textView4);
        StringBuilder message = new StringBuilder();
        message.append("Różnica wysokości: ");
        message.append(calculateAltitude());
        message.append(" m");
        t.setText(message.toString());
    }

    private double calculateAltitude(){
        Location firstLocation = findFirstLocationAltitude();
        Location lastLocation = getLastKnownLocation();
        return lastLocation.getAltitude()-firstLocation.getAltitude();
    }

    private Location getLastKnownLocation(){
        Location result = locations.get(locations.size()-1);
        for (int i=locations.size()-1;i>=0;i--){
            Location tmp = locations.get(i);
            if(tmp.hasAltitude()){
                result = tmp;
                break;
            }
        }
        return result;
    }

    private Location findFirstLocationAltitude() {
        Location result = locations.get(0);
        for(Location location : locations){
            if (location.hasAltitude()){
                result = location;
                break;
            }
        }
        return result;
    }

    private void speed(){
        TextView t=(TextView)findViewById(R.id.textView5);
        StringBuilder message = new StringBuilder();
        message.append("Srednia prędkość: ");
        message.append(calculateAvarageSpeed());
        message.append(" m/s");
        t.setText(message.toString());
    }

    private float calculateAvarageSpeed(){
        float sumSpeed = 0;
        int count = 0;
        for(Location location : locations){
            if (location.hasSpeed()){
                sumSpeed = location.getSpeed();
                count++;
                break;
            }
        }
        return sumSpeed/count;
    }
    private void time(){
        TextView t=(TextView)findViewById(R.id.textView6);
        StringBuilder message = new StringBuilder();
        message.append("Czas pomiaru: ");
        message.append(calculateTimeInSecond());
        message.append(" s");
        t.setText(message.toString());
    }
    private int calculateTimeInSecond(){
        long timeStart = locations.get(0).getTime();
        long timeEnd = locations.get(locations.size()-1).getTime();
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(timeEnd-timeStart);
        return seconds;
    }
}