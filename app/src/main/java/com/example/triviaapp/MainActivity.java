package com.example.triviaapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaapp.data.AnswerListAsyncResponse;
import com.example.triviaapp.data.QuestionBank;
import com.example.triviaapp.model.Question;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView question_tv;
    private TextView counter;
    private Button true_btn;
    private Button false_btn;
    private ImageButton next;
    private ImageButton prev;
    private int index = 0;
    private List<Question>questionList;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        question_tv = findViewById(R.id.question_tv);
        counter = findViewById(R.id.counter);
        true_btn = findViewById(R.id.true_button);
        false_btn = findViewById(R.id.false_button);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);


        false_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!questionList.get(index).isCorrect())
                {
                    fadeView();
                    updateQuestion();
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    shakeAnimation();
                    updateQuestion();
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        true_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = (index +1) % questionList.size(); 
                updateQuestion();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0)
                {
                    index = (index-1) % questionList.size();
                    updateQuestion();
                }
            }
        });


        QuestionBank bank = new QuestionBank();

        questionList = bank.getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                    question_tv.setText(questionArrayList.get(0).getQuestion());
                    counter.setText(index + "/"+ questionArrayList.size());

                    Log.d("DONE", "" + questionArrayList);
            }
        });
    }

    private void checkAnswer() {
        if (questionList.get(index).isCorrect())
        {
            fadeView();
            updateQuestion();
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            shakeAnimation();
            updateQuestion();
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateQuestion() {
        String question = questionList.get(index).getQuestion();
        Log.d("QUESTION", ""+question);
        question_tv.setText(question);
        counter.setText(index + "/"+ questionList.size());

    }

    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        final CardView card = findViewById(R.id.cardView);
        card.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                card.setCardBackgroundColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                card.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }
        });
    }

    private void fadeView()
    {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation =  new AlphaAnimation(1.0f, 0.5f);
        alphaAnimation.setDuration(350);

        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
