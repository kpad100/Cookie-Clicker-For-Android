package com.example.kedar.cookieclicker;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    ImageView cookie;
    ConstraintLayout layout;
    TextView cookieCountTxt;
    AtomicInteger cookieCount;
    ImageView grandmaSitting;
    TextView upgrades;
    AtomicInteger grandmaCount;
    ImageView bankImage;
    AtomicInteger bankCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookie = (ImageView)findViewById(R.id.cookie);
        layout = (ConstraintLayout)findViewById(R.id.layout);
        cookieCountTxt = (TextView)findViewById(R.id.cookieCountTxt);
        grandmaSitting = (ImageView)findViewById(R.id.grandmasitting);
        upgrades = (TextView)findViewById(R.id.upgrades);
        bankImage = (ImageView)findViewById(R.id.bankImage);
        cookieCount = new AtomicInteger(0);
        grandmaCount = new AtomicInteger(0);
        bankCount = new AtomicInteger(0);

        final ScaleAnimation cookieAnimation = new ScaleAnimation(0.5f,1.0f,0.5f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        cookieAnimation.setDuration(200);
        cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(cookieAnimation);
                cookieCount.getAndAdd(1);
                cookieCountTxt.setText(cookieCount+" cookies");

                final TextView plusCookie;
                plusCookie = new TextView(MainActivity.this);
                plusCookie.setId(View.generateViewId());

                final ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                plusCookie.setText("+1");
                plusCookie.setLayoutParams(params);
                layout.addView(plusCookie);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);

                constraintSet.connect(plusCookie.getId(),ConstraintSet.TOP,cookieCountTxt.getId(),ConstraintSet.BOTTOM);
                constraintSet.connect(plusCookie.getId(),ConstraintSet.BOTTOM,cookie.getId(),ConstraintSet.TOP);
                constraintSet.connect(plusCookie.getId(),ConstraintSet.RIGHT,layout.getId(),ConstraintSet.RIGHT);
                constraintSet.connect(plusCookie.getId(),ConstraintSet.LEFT,layout.getId(),ConstraintSet.LEFT);

                constraintSet.setHorizontalBias(plusCookie.getId(),(float)(Math.random()*.8f)+.1f);
                constraintSet.setVerticalBias(plusCookie.getId(),(float)(Math.random())+.8f);
                constraintSet.applyTo(layout);

                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -5f);
                translateAnimation.setDuration(1000);
                plusCookie.startAnimation(translateAnimation);


               plusCookie.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       Animation disappear = new AlphaAnimation(1,0);
                       disappear.setInterpolator(new AccelerateInterpolator());
                       disappear.setDuration(500);
                       disappear.setAnimationListener(new Animation.AnimationListener() {
                           @Override
                           public void onAnimationStart(Animation animation) {

                           }

                           @Override
                           public void onAnimationEnd(Animation animation) {
                               plusCookie.setVisibility(View.INVISIBLE);
                           }

                           @Override
                           public void onAnimationRepeat(Animation animation) {

                           }
                       });
                       plusCookie.startAnimation(disappear);
                   }
               },1000);
                if(cookieCount.get()>=50)
                    grandma();
                else {
                    grandmaSitting.setImageResource(R.drawable.grandmasittinggray);
                    grandmaSitting.setClickable(false);
                }
                if(cookieCount.get()>=100)
                    bank();
                else {
                    bankImage.setImageResource(R.drawable.bankgray);
                    bankImage.setClickable(false);
                }
            }
        });
    }

    public class grandmaThread extends Thread{
        public void run(){
            while (true){
                cookieCount.getAndAdd(grandmaCount.get());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cookieCountTxt.setText(cookieCount+" cookies");
                    }
                });
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class bankThread extends Thread{
        public void run(){
            while (true){
                cookieCount.getAndAdd(2*bankCount.get());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cookieCountTxt.setText(cookieCount+" cookies");
                    }
                });
                try{
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void grandma(){
        grandmaSitting.setImageResource(R.drawable.grandmasitting);
        grandmaSitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView grandma;
                grandma = new ImageView(MainActivity.this);
                grandma.setId(View.generateViewId());
                grandma.setImageResource(R.drawable.grandma);

                final ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                grandma.setLayoutParams(params);
                layout.addView(grandma);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);

                constraintSet.connect(grandma.getId(),ConstraintSet.TOP,cookie.getId(),ConstraintSet.BOTTOM);
                constraintSet.connect(grandma.getId(),ConstraintSet.BOTTOM,upgrades.getId(),ConstraintSet.TOP);
                constraintSet.connect(grandma.getId(),ConstraintSet.RIGHT,layout.getId(),ConstraintSet.RIGHT);
                constraintSet.connect(grandma.getId(),ConstraintSet.LEFT,layout.getId(),ConstraintSet.LEFT);

                constraintSet.setHorizontalBias(grandma.getId(),(float)(Math.random()*.4f));
                constraintSet.setVerticalBias(grandma.getId(),(float)(Math.random()*.5f));
                constraintSet.applyTo(layout);

                grandmaCount.getAndAdd(1);
                cookieCount.getAndAdd(-50);
                if(cookieCount.get()<50) {
                    grandmaSitting.setClickable(false);
                    grandmaSitting.setImageResource(R.drawable.grandmasittinggray);
                }
                new grandmaThread().start();
            }
        });

    }

    public void bank(){
        bankImage.setImageResource(R.drawable.bank);
        bankImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView smallBank;
                smallBank = new ImageView(MainActivity.this);
                smallBank.setId(View.generateViewId());
                smallBank.setImageResource(R.drawable.smallbank);

                final ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                smallBank.setLayoutParams(params);
                layout.addView(smallBank);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);

                constraintSet.connect(smallBank.getId(),ConstraintSet.TOP,cookie.getId(),ConstraintSet.BOTTOM);
                constraintSet.connect(smallBank.getId(),ConstraintSet.BOTTOM,upgrades.getId(),ConstraintSet.TOP);
                constraintSet.connect(smallBank.getId(),ConstraintSet.RIGHT,layout.getId(),ConstraintSet.RIGHT);
                constraintSet.connect(smallBank.getId(),ConstraintSet.LEFT,layout.getId(),ConstraintSet.LEFT);

                constraintSet.setHorizontalBias(smallBank.getId(),(float)(Math.random()*.4f)+.6f);
                constraintSet.setVerticalBias(smallBank.getId(),(float)(Math.random()*.5f));
                constraintSet.applyTo(layout);

                bankCount.getAndAdd(1);
                cookieCount.getAndAdd(-100);
                if(cookieCount.get()<50) {
                    grandmaSitting.setClickable(false);
                    grandmaSitting.setImageResource(R.drawable.grandmasittinggray);
                }
                if(cookieCount.get()<100) {
                    bankImage.setClickable(false);
                    bankImage.setImageResource(R.drawable.bankgray);
                }
                new bankThread().start();
            }
        });
    }
}

