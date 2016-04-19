package com.example.electiva.zimbabue;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Pantalla_Inicio extends Activity {
    public static MediaPlayer musicaFondo;
    public static boolean musicaEstaCorriendo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pantalla_inicio);
        musicaFondo = MediaPlayer.create(this, R.raw.fondo);
        musicaFondo.setVolume(1,1);
        final MediaPlayer opc = MediaPlayer.create(this, R.raw.elige);
        final Button botonJugar= (Button) findViewById(R.id.buttonJugar);
        final Button botonAyuda = (Button) findViewById(R.id.buttonAyuda);
        //if (!musicaEstaCorriendo) {

            musicaFondo.setLooping(true);
            musicaFondo.start();
            musicaEstaCorriendo = true;

        //}
        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation anrTl = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_from_right_to_left);
                opc.start();
                botonJugar.startAnimation(anrTl);
                anrTl.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                        startActivity(new Intent(Pantalla_Inicio.this, Seleccionar_Mundo.class));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        botonAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation anlTr = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_from_left_to_right);
                opc.start();
                botonAyuda.startAnimation(anlTr);
                anlTr.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                        String url = "https://docs.google.com/document/d/1DPUn_RA08ynQx5lqpod7afcivpOAeBaWCNKg9Mpmqp0/edit?usp=sharing";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //musicaFondo.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
      if (!musicaFondo.isPlaying()){
        musicaFondo.start();
      }
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  musicaFondo.pause();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            musicaFondo.stop();
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }
        return super.onKeyDown(keyCode, event);
    }
}

