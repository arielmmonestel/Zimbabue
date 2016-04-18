package com.example.electiva.zimbabue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Pantalla_Preguntas extends AppCompatActivity {

    public static ArrayList<Pregunta> listaPreguntas;
    public TextView textoPregunta1;
    public TextView textoPregunta2;
    public TextView textoPregunta3;
    public Button btnOpcionA;
    public Button btnOpcionB;
    public Button btnOpcionC;
    public static Pregunta preguntaRandom;
    public static ArrayList<Integer> listaRespuestas;
    public static boolean yaEligioPregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla__preguntas);

        textoPregunta1 = (TextView) findViewById(R.id.txtViewPregunta1);
        textoPregunta2 = (TextView) findViewById(R.id.txtViewPregunta2);
        textoPregunta3 = (TextView) findViewById(R.id.txtViewPregunta3);
        btnOpcionA = (Button) findViewById(R.id.buttonOpcionA);
        btnOpcionB = (Button) findViewById(R.id.buttonOpcionB);
        btnOpcionC = (Button) findViewById(R.id.buttonOpcionC);

        Intent intent = getIntent();
        listaPreguntas =(ArrayList<Pregunta>)intent.getSerializableExtra("preguntas");
        int j = 0;
        for(Pregunta pregunta:listaPreguntas){
            System.out.println(j+") "+"Pregunta LLEGADA: Cuanto es "+pregunta.getOperandoUno()+pregunta.getOperacion()+pregunta.getOperandoDos()+"? R/"+pregunta.getResultado());
            j++;
        }

        preguntaRandom = getPreguntaRandom(listaPreguntas);
        listaRespuestas = getRespuestasRandom(listaPreguntas, preguntaRandom);
        yaEligioPregunta = false;

    }

    public void seleccionarPreguntaA(View v){
        if(!yaEligioPregunta){
            textoPregunta1.setText(construirPregunta(preguntaRandom));
            textoPregunta1.setTextSize(30);

            btnOpcionA.setText(String.valueOf(listaRespuestas.get(0)));
            btnOpcionB.setText(String.valueOf(listaRespuestas.get(1)));
            btnOpcionC.setText(String.valueOf(listaRespuestas.get(2)));
            yaEligioPregunta = true;
        }
    }

    public void seleccionarPreguntaB(View v){
        if(!yaEligioPregunta){
            textoPregunta2.setText(construirPregunta(preguntaRandom));
            textoPregunta2.setTextSize(30);

            btnOpcionA.setText(String.valueOf(listaRespuestas.get(0)));
            btnOpcionB.setText(String.valueOf(listaRespuestas.get(1)));
            btnOpcionC.setText(String.valueOf(listaRespuestas.get(2)));
            yaEligioPregunta = true;
        }
    }

    public void seleccionarPreguntaC(View v){
        if(!yaEligioPregunta) {
            textoPregunta3.setText(construirPregunta(preguntaRandom));
            textoPregunta3.setTextSize(30);

            btnOpcionA.setText(String.valueOf(listaRespuestas.get(0)));
            btnOpcionB.setText(String.valueOf(listaRespuestas.get(1)));
            btnOpcionC.setText(String.valueOf(listaRespuestas.get(2)));
            yaEligioPregunta = true;
        }
    }


    public ArrayList<Integer> getRespuestasRandom(ArrayList<Pregunta> listaDePreguntas, Pregunta preguntaElegida){
        int respuestaCorrecta = preguntaElegida.getResultado();
        int primeraRespuestaErronea = 0;
        int segundaRespuestaErronea = 0;

        ArrayList<Integer> listaDeRespuestas = new ArrayList<Integer>();
        listaDeRespuestas.add(respuestaCorrecta);

        Random random = new Random();
        int indiceAleatorio = random.nextInt(listaDePreguntas.size());
        primeraRespuestaErronea = listaDePreguntas.get(indiceAleatorio).getResultado();

        while(primeraRespuestaErronea == respuestaCorrecta){
            Random random2 = new Random();
            int nuevoIndiceAleatorio = random2.nextInt(listaDePreguntas.size());
            primeraRespuestaErronea = listaDePreguntas.get(nuevoIndiceAleatorio).getResultado();
            System.out.println("***********RESPUESTA RANDOM = " + String.valueOf(primeraRespuestaErronea));
        }

        listaDeRespuestas.add(primeraRespuestaErronea);

        int siguienteIndiceAleatorio = random.nextInt(listaDePreguntas.size());
        segundaRespuestaErronea = listaDePreguntas.get(siguienteIndiceAleatorio).getResultado();
        System.out.println("RESPUESTA CORRECTA: " + String.valueOf(respuestaCorrecta));
        System.out.println("PRIMERA ERRONEA: " + String.valueOf(primeraRespuestaErronea));

        Random random2 = new Random();
        int a = 0;
        while((segundaRespuestaErronea == respuestaCorrecta) || (segundaRespuestaErronea == primeraRespuestaErronea)){
            int ultimoIndiceAleatorio = random2.nextInt(listaDePreguntas.size());
            segundaRespuestaErronea = listaDePreguntas.get(ultimoIndiceAleatorio).getResultado();
            if(a<10){
                System.out.println("INDICE: " + String.valueOf(segundaRespuestaErronea));
            }
            a++;
        }

        listaDeRespuestas.add(segundaRespuestaErronea);

        long semilla = System.nanoTime();
        Collections.shuffle(listaDeRespuestas, new Random(semilla));

        return listaDeRespuestas;
    }

    public String construirPregunta(Pregunta pregunta){
        String preguntaEnTexto = String.valueOf(pregunta.getOperandoUno());

        if(pregunta.getOperacion().equals("+")){
            preguntaEnTexto += "+";
        }else if(pregunta.getOperacion().equals("-")){
            preguntaEnTexto += "-";
        }else if(pregunta.getOperacion().equals("x")){
            preguntaEnTexto += "x";
        }else{
            preguntaEnTexto += "/";
        }
        preguntaEnTexto += String.valueOf(pregunta.getOperandoDos());
        return preguntaEnTexto;
    }

    public Pregunta getPreguntaRandom(ArrayList<Pregunta> listaDePreguntas){
        Random random = new Random();
        int indiceAleatorio = random.nextInt(listaDePreguntas.size());
        Pregunta preguntaElegida = listaDePreguntas.get(indiceAleatorio);
        return preguntaElegida;
    }
}
