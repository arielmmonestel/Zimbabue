package com.example.electiva.zimbabue;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;


public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Typeface texto = Typeface.createFromAsset(getAssets(),"zoo.otf");
        TextView myTexto = (TextView) findViewById(R.id.textViewPop);
        TextView myTexto2 = (TextView) findViewById(R.id.textViewPop2);
        myTexto.setTypeface(texto);
        myTexto2.setTypeface(texto);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height* 0.6));
    }
}
