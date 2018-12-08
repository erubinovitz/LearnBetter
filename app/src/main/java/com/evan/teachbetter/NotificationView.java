package com.evan.teachbetter;

import android.app.Notification;
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
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class NotificationView extends MainActivity{
    TextView tView;
    EditText answer;
    int id;
    Button b1;
    int time;
    String question;
    String correctAnswer;
    @Override
    public void onCreate(Bundle savedInstanceState){
        Intent intent= getIntent();
       // System.out.println("Is intent null? " + (intent==null));
        int a=intent.getExtras().getInt("Number");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        b1 = (Button)findViewById(R.id.Confirm);
        answer = (EditText)findViewById(R.id.editText5);
        tView = (TextView)findViewById(R.id.question);

        id=a;
        qCounter=a;
        question = readQuestion("q"+id);
        if (!question.endsWith("?"))
            question+="?";
        correctAnswer=readQuestion("a"+id);
        System.out.println("a = " +a);
        time = Integer.parseInt(readQuestion("n"+id));
        tView.setText("Question: "+question);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(10);
                String answerText = answer.getText().toString();

                if (readQuestion("a"+id).toLowerCase().equals(answerText.toLowerCase())){
                    correct(question,answer.getText().toString(),id);
                }
                else open(v);

            }
        });



    }

    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("The correct answer was " + correctAnswer+"." +
                " This doesn't match our answer. Was your answer actually correct?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                correct(question,answer.getText().toString(),id);
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                wrong(question,answer.getText().toString(), id);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void exit(boolean correct){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if (correct) {
            time*=2;
            String response="";
            if (time>16)
                response="Congrats! You've got this question correct enough to remove it from your list.";
            else response = "Good job! You got the question right, so you won't be asked again for "+
            time +" days.";
            alertDialogBuilder.setMessage(response);
            alertDialogBuilder.setPositiveButton("Nice!",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                           System.exit(0);
                        }
                    });
        }
        else {
            if (time!=1)
                time/=2;

            alertDialogBuilder.setMessage("Unfortunately, you got the question wrong. You will be asked again in " +
            time + " days.");
            alertDialogBuilder.setPositiveButton("Dang!",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            System.exit(0);
                        }
                    });
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void correct(String q, String a, int id){
        Toast.makeText(this,"Correct!",Toast.LENGTH_LONG).show();
        exit(true);
        if (time<=16){
            makeQuestion(q,correctAnswer,time+"",id);
            sendNotification(q,correctAnswer);
        }

    }
    public void wrong(String q, String a, int id){
        System.out.println("wrong");

        exit(false);
        makeQuestion(q,correctAnswer,time+"",id);
        sendNotification(q,correctAnswer);
    }
}