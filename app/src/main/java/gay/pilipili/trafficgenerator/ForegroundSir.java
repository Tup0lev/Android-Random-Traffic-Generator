package gay.pilipili.trafficgenerator;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ForegroundSir extends Service{



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //get url from da intent
                    String inwebsite= VisitWebsite(intent.getStringExtra("passMeMyPorn"));
                    String website2visit = inwebsite;
                    while (true){
                        System.out.println("got " + website2visit);



                        if (website2visit == null){
                            website2visit = inwebsite;
                        }
                        website2visit = VisitWebsite(website2visit);
//                        Toast visitin = Toast.makeText(getApplicationContext(), "Visiting" + website2visit,
//                                Toast.LENGTH_SHORT);
//                        visitin.show();


                        try {

                        Thread.sleep(1000);} catch (InterruptedException e) {
                            System.out.println("Bing Chilling!");
                        }
                    }
                }



            }    ).start();

            final String CHANNELID = "Foreground big sir";
            NotificationChannel channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_HIGH);

            getSystemService(NotificationManager.class).createNotificationChannel(channel);
                    Notification.Builder notification = new Notification.Builder(this,CHANNELID)
                            .setContentText("yay")
                           .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("Foreground");
            startForeground(1001, notification.build());



        }


        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String VisitWebsite(String strInURL){
        if (strInURL == null){
            System.out.println("dude wadafa");
            return null;
        }
        System.out.println("received " + strInURL);
        int doubleslash = strInURL.lastIndexOf("//");
        String firstpart = strInURL.substring(0,doubleslash+2);
        String secondpart = strInURL.substring(doubleslash+2);
      //  System.out.println("firstpart " + firstpart);
      //  System.out.println("lastpart "+ secondpart);
        String beforeslash = "";
        if (secondpart.contains("/")) {
            int slash = secondpart.indexOf("/");
             beforeslash = secondpart.substring(0, slash);
        } else {
             beforeslash = secondpart;
        }
     //   System.out.println("beforeslash " + beforeslash);

        String rootURL = firstpart +  beforeslash ;
        Document document;
        try {
            //Connect to the website
            document = Jsoup.connect(strInURL).get();


            Elements links = document.select("a[href]");

            //Get the link text
            //Iterate links and print link attributes.
            ArrayList<String> lstRealLinks = new ArrayList<String>();


            for (Element link : links) {
               // System.out.println("LINK FOUND!!!: " + link.attr("href"));

            //    System.out.println(link);
              //  System.out.println(link.text().charAt(0));
        //        System.out.println(link.attr("href"));
//                System.out.println("trying to visit lolol");


                if (link.attr("href").toString() != ""
                        && link.attr("href").toString() != "j"
                        && link.attr("href").toString() != null 
                        && link.attr("href").toString().length() > 0
                ) {
                    if (link.attr("href").toString().charAt(0) == 'h'){

                    lstRealLinks.add(link.attr("href"));
                    //    System.out.println("adding " + link.attr("href"));
                } else if (link.attr("href").toString().charAt(0) == '/') {
                       // System.out.println(link.attr("href"));
               //         System.out.println("halfurl");
               //         System.out.println(rootURL +  link.attr("href"));
                        lstRealLinks.add( rootURL +  link.attr("href") );
              //          System.out.println(rootURL +  link.attr("href"));
                    }


                }
            }
            System.out.println("process linkz");
            if (lstRealLinks.isEmpty()){
                //do something where no real link on da page
                System.out.println("no link on page");
                return null;
            } else {
            //    System.out.println("yes link on page");
                Random randomizer = new Random();
                String strNextLink = lstRealLinks.get(randomizer.nextInt(lstRealLinks.size()));
            //    System.out.println("chosen link is " + strNextLink);
                return strNextLink;
            }
        } catch (IOException e) {

        }
        return null;



    }


}
