package com.netforceinfotech.vrmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.netforceinfotech.vrmarket.dashboard.Dashboard;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final View l = findViewById(R.id.main);
        Animation a = AnimationUtils.loadAnimation(
                this, R.anim.fade_out);
        a.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationEnd(Animation animation) {
                // Do what ever you need, if not remove it.
                Intent i = new Intent(Splash.this, Dashboard.class);
                startActivity(i);
                finish();
            }

            public void onAnimationRepeat(Animation animation) {
                // Do what ever you need, if not remove it.
            }

            public void onAnimationStart(Animation animation) {
                // Do what ever you need, if not remove it.
            }

        });
        l.startAnimation(a);


    }
}
