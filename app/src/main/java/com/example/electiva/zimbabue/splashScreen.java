package com.example.electiva.zimbabue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by ariel on 21/3/2016.
 */
public class splashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        final ImageView iv = (ImageView) findViewById(R.id.logoTec);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO: 21/3/2016  poner la musica a sonar aqui
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                    finish();
                startActivity(new Intent(splashScreen.this, Pantalla_Inicio.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
