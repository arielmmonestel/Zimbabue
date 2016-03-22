package com.example.electiva.zimbabue;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Cant_Jugadores extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cant_jugador);
        final Button Jugador2 = (Button) findViewById(R.id.Jugador2);
        final Button Jugador3 = (Button) findViewById(R.id.Jugador3);
        final Button Jugador4 = (Button) findViewById(R.id.Jugador4);

        Jugador2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation anrTl = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_from_right_to_left);
                Jugador2.startAnimation(anrTl);
                anrTl.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(Cant_Jugadores.this, Pantalla_Tablero.class));
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        Jugador3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation anlTr = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_from_left_to_right);
                Jugador3.startAnimation(anlTr);
                anlTr.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(Cant_Jugadores.this, Pantalla_Tablero.class));
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        Jugador4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation anlTr = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_from_left_to_right);
                Jugador4.startAnimation(anlTr);
                anlTr.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(Cant_Jugadores.this, Pantalla_Tablero.class));
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            startActivity(new Intent(Cant_Jugadores.this, Pantalla_Inicio.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
