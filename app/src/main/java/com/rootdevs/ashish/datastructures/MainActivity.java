package com.rootdevs.ashish.datastructures;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView question, answer;
    ImageView imageView;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question = (TextView)findViewById(R.id.qts1);
        answer = (TextView)findViewById(R.id.ans1);
        imageView = (ImageView)findViewById(R.id.imgur);
        cardView = (CardView)findViewById(R.id.card);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        fade.excludeTarget(R.id.mainactlay,true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        Intent intent = getIntent();
        String qts = intent.getStringExtra("Question");
        String ans = intent.getStringExtra("Answer");
        question.setText(qts);
        answer.setText(ans);

        if(qts.contains("Binary trees"))
        {
            cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1b5e20")));
            imageView.setImageResource(R.drawable.binary_tree);
        }
        else if(qts.contains("Stack")){
            cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#263238")));
            imageView.setImageResource(R.drawable.stack);
        }
        else if(qts.contains("queue")){
            cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c51162")));
            imageView.setImageResource(R.drawable.queue);
        }
        else if(qts.contains("binary search")){
            cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffab00")));
            imageView.setImageResource(R.drawable.bsearch);
        }
        else{
            cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            imageView.setImageResource(R.drawable.nullvoid1);
        }
    }

    public void open_link(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://courses.learncodeonline.in"));
        startActivity(browserIntent);
    }
}
