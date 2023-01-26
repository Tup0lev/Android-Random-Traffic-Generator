package gay.pilipili.trafficgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.io.InputStream;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    TextView textView;
    ImageView imageView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.title);


        EditText URL = (EditText) findViewById(R.id.editTextURL);



        Button startYouPieceOfShit = (Button) findViewById(R.id.buttonStart);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }






        startYouPieceOfShit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("im pressed");


                if (!Isdaservicerunnin()) {
                    System.out.println("there's your service alright, big sir");
                    Intent startForegroundSir = new Intent(getApplicationContext(), ForegroundSir.class);
                    String daurl = String.valueOf(URL.getText());
                    startForegroundSir.putExtra("passMeMyPorn", daurl);
                    startService(startForegroundSir);

                    Toast letsroll = Toast.makeText(getApplicationContext(), "Let's roll",
                            Toast.LENGTH_SHORT);
                    letsroll.show();
                } else{
                  //  System.out.println("I ain givin another service, you greedy ass grease ball bastard");

                    Toast youaretoast = Toast.makeText(getApplicationContext(), "I ain givin another service, you greedy ass grease ball bastard",
                            Toast.LENGTH_SHORT);
                    youaretoast.show();
                }

            }
        });

//        if (!Isdaservicerunnin()) {
//            System.out.println("there's your service alright, big sir");
//            Intent startForegroundSir = new Intent(this, ForegroundSir.class);
//           // startForegroundSir.putExtra(daurl);
//            startService(startForegroundSir);
//        } else{
//            System.out.println("I ain givin another service, you greedy ass grease ball bastard");
//        }


    }

    public Boolean Isdaservicerunnin(){

        ActivityManager IWannaSeeYourManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo sirvice: IWannaSeeYourManager.getRunningServices(Integer.MAX_VALUE)){
            if (ForegroundSir.class.getName().equals(sirvice.service.getClassName())){
                return true;
            }
        }

return false;
    }

}

