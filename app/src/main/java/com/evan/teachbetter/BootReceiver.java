package com.evan.teachbetter;
import android.app.NotificationChannel;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import 	android.app.AlarmManager;
import android.os.SystemClock;
import android.widget.EditText;
import 	android.support.v4.app.TaskStackBuilder;
import 	android.support.v4.content.WakefulBroadcastReceiver;
import java.util.Date;
import java.util.Calendar;
import 	android.content.ComponentName;
import 	android.content.pm.PackageManager;
import 	android.content.BroadcastReceiver;
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    //    System.out.println("5555");
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeatingIntent= new Intent(context,NotificationView.class);

        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int c = intent.getExtras().getInt("Number");
        //System.out.println("C is " +c);
        repeatingIntent.putExtra("Number",c);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,c
                ,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,Integer.toString(c));
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Question Ready");
        mBuilder.setContentText("You have a question ready to be reviewed.");
        mBuilder.setContentIntent(pendingIntent);
      //  System.out.println("233");
        mBuilder.setAutoCancel(true);
        NotificationChannel mChannel = new NotificationChannel(Integer.toString(c),"Question",NotificationManager.IMPORTANCE_DEFAULT);
       // mChannel.setDescription("test");
        mNotificationManager.createNotificationChannel(mChannel);
        if (true) {
          /*  ComponentName receiver = new ComponentName(context, BootReceiver.class);
            PackageManager pm = context.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);*/
            mNotificationManager.notify(c, mBuilder.build());
        }
    }
}