package com.example.theprogrammer.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    TextView questionLabel, questionCountLabel, scoreLabel;
    EditText answerEdt;
    Button submitButton;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArraylist;


    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionCountLabel = findViewById(R.id.noQuestion);
        questionLabel = findViewById(R.id.question);
        scoreLabel = findViewById(R.id.score);

        answerEdt = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        questionModelArraylist = new ArrayList<>();

        setUpQuestion();

        setData();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkForTheAnswer();
            }
        });

        answerEdt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                Log.e("event.getAction()",event.getAction()+"");
                Log.e("event.keyCode()",keyCode+"");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    checkForTheAnswer();
                    return true;
                }
                return false;
            }
        });

    }
    public void checkForTheAnswer(){

        String answerString  = answerEdt.getText().toString().trim();
        String correctAnswer = questionModelArraylist.get(currentPosition).getAnswer();

        if (answerString.equals("")){
            Toast.makeText(this, "No answer provided", Toast.LENGTH_LONG).show();
        }

        else if (answerString.equalsIgnoreCase(correctAnswer)) {
            numberOfCorrectAnswer ++;

            Toast.makeText(this, "Right Answer: " + answerString+ "\n" + "Congs!", Toast.LENGTH_SHORT).show();
            currentPosition ++;
            setData();
            answerEdt.setText("");

        }else {

            Toast.makeText(this, "Wrong Answer: " + answerString + "\n" + "Right Answer is: "
                    + correctAnswer, Toast.LENGTH_SHORT).show();
            currentPosition ++;
            setData();
            answerEdt.setText("");

        }


    }




    public void setUpQuestion(){

        questionModelArraylist.add(new QuestionModel("When did Uganda get Independence ? ","October 9, 1962"));
        questionModelArraylist.add(new QuestionModel("When did GDPR law come into force in Europe ? ","May 25, 2018"));
        questionModelArraylist.add(new QuestionModel("Who is the CEO of Microsoft ? ","Satya Narayana Nadella"));
        questionModelArraylist.add(new QuestionModel("When was Andela found ? ","May 21, 2013"));
        questionModelArraylist.add(new QuestionModel("Who are the founders of Google ? ","Larry Page and Sergey Brin"));
        questionModelArraylist.add(new QuestionModel("Who is the current richest person in the world 2018 ? ","Jeff Bezos"));
        questionModelArraylist.add(new QuestionModel("Who is the President of the Republic of Uganda ? ","Yoweri Museveni"));
        questionModelArraylist.add(new QuestionModel("Who is the President of SpaceX ? ","Gwynne Shotwell"));
        questionModelArraylist.add(new QuestionModel("Who is the founder of Facebook ? ","Mark Zuckerberg"));
        questionModelArraylist.add(new QuestionModel("What Programming Language is Android most written In ? ","Java"));


    }

    public void setData(){


        if(questionModelArraylist.size()>currentPosition) {

            questionLabel.setText(questionModelArraylist.get(currentPosition).getQuestionString());

            scoreLabel.setText("Score :" + numberOfCorrectAnswer + "/" + questionModelArraylist.size());
            questionCountLabel.setText("Question No : " + (currentPosition + 1));


        }else{


            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("You have successfully completed the quiz")
                    .setContentText("Your score is : "+ numberOfCorrectAnswer + "/" + questionModelArraylist.size())
                    .setConfirmText("Restart")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                    })
                    .setCancelText("Close")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();

        }

    }



}
