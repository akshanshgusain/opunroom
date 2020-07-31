package com.factor8.opUndoor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashActivity extends AppCompatActivity {
    ImageView label;
    int[] location = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        label = findViewById(R.id.imageViewex);
        label.getLocationOnScreen(location);
        animateLabel();

    }

    private void animateLabel() {
        SpringAnimation springAnimation = new SpringAnimation(label, DynamicAnimation.Y);
        int y = location[1];
        SpringForce springForce = new SpringForce();
        springForce.setFinalPosition(y);
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);

        springAnimation.setSpring(springForce);
        springAnimation.setStartVelocity(4000f);
        springAnimation.start();
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                 TextView appName = findViewById(R.id.textView_appName);
                 appName.setVisibility(View.VISIBLE);
                Delay d=new Delay();
                d.start();
            }
        });
    }

    public class Delay extends Thread{
        public void run(){
            try {
                sleep(1000);
                Intent p = new Intent(SplashActivity.this, MainActivity.class);
                p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(p);
                finish();
            }catch(Exception e){

            }
        }
    }
}
