package com.example.electiva.zimbabue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class Seleccionar_Mundo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_seleccionar_mundo);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            startActivity(new Intent(Seleccionar_Mundo.this, Cant_Jugadores.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
