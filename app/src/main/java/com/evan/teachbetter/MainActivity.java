package com.evan.teachbetter;

import android.app.Notification;
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
import android.widget.TextView;
import 	android.app.AlarmManager;
import android.os.SystemClock;
import android.widget.EditText;
import 	android.support.v4.app.TaskStackBuilder;
public class MainActivity extends AppCompatActivity {
    int qCounter;
    TextView tView;
    EditText question;
    boolean debugging=  true;
    EditText answer;
    NotificationView nv;
    @Override
/*
-List
-ML-show similar questions
-HLR
-Difficulty
 */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button)findViewById(R.id.Add);
        question = (EditText)findViewById(R.id.editText);
        answer = (EditText)findViewById(R.id.editText2);
        tView = (TextView)findViewById(R.id.textView);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qCounter=initializeQ();
                tView.setText("Added question! ");
               // System.out.println(1);
                makeQuestion(question.getText().toString(),answer.getText().toString(),1+"",qCounter);

                sendNotification(answer.getText().toString(),question.getText().toString());
            }
        });
    }
    public int initializeQ(){
        try {
            FileInputStream fin = openFileInput(("qCounter"));
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            if (temp.equals(""))throw new Exception();
            //string temp contains all the data of the file.
            fin.close();
            System.out.println("No write needed " + temp);
            try {
                FileOutputStream fOut = openFileOutput("qCounter", MODE_PRIVATE);
                fOut.write(((Integer.parseInt(temp)+1)+"").getBytes());
                System.out.println("Successful write");

            }
            catch (Exception eee){
                System.out.println("failed write...");

            }
            return Integer.parseInt(temp);
        }
        catch (Exception e){
            try {
                FileOutputStream fOut = openFileOutput("qCounter", MODE_PRIVATE);
                fOut.write((2+"").getBytes());
                System.out.println("Successful write");
                return 1;
            }
            catch (Exception eee){
                System.out.println("failed write...");
                return 1;
            }
        }
    }
    public void makeQuestion(String q, String a,String n,int qCounter){
        try {

            FileOutputStream fOut = openFileOutput("q"+ qCounter, MODE_PRIVATE);
            FileOutputStream fOut2 = openFileOutput("a"+qCounter, MODE_PRIVATE);
            FileOutputStream fOut3 = openFileOutput("n"+qCounter, MODE_PRIVATE);
            fOut.write(q.getBytes());
            fOut2.write(a.getBytes());
            fOut3.write(n.getBytes());
            fOut.close();
            fOut2.close();
            fOut3.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public String readQuestion(String fileName){
        try {
            FileInputStream fin = openFileInput((fileName));
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            //string temp contains all the data of the file.
            fin.close();
            return temp;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public void sendNotification(String answer, String question){
        //System.out.println(2);



        //System.out.println(4);
        int delay;

       // System.out.println("qCounter is " + qCounter);
        delay=Integer.parseInt(readQuestion("n"+qCounter));

        if (!debugging) delay*=24*60;
     //   System.out.println("delay is " + delay);

    /*    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);
        mNotificationManager.notify(0, mBuilder.build());*/
        //System.out.println(5);
       // System.out.println("qCounter is " + qCounter);

        Intent intent = new Intent(this, BootReceiver.class);
       // intent.setAction("Question Ready");
        intent.putExtra("Number",qCounter);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, qCounter,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    //    System.out.println("A");
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        AlarmManager alarmManager = (AlarmManager)
                this.getSystemService(this.ALARM_SERVICE);
     //   System.out.println("B");
        alarmManager.setExact(alarmType,   SystemClock.elapsedRealtime()+delay*5000

                ,pendingIntent);
//System.out.println("C");





    }
}
