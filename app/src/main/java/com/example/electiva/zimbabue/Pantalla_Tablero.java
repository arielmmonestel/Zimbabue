package com.example.electiva.zimbabue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Pantalla_Tablero extends AppCompatActivity {


    public static ArrayList<Integer> listaJugador1 ;
    public static ArrayList<Integer>  listaJugador2 ;
    public static ArrayList<Integer>  listaJugador3 ;
    public static ArrayList<Integer>  listaJugador4 ;
    public static ArrayList<Pregunta> listaPreguntas  = new ArrayList<Pregunta>();
    public static ArrayList<Button> listaBotones = new ArrayList<Button>();
    public static ArrayList<Button> listaBotonesAux = new ArrayList<Button>();
    public static int cantidadDeJugadores=0;
    public static String simboloOperacion ;
    public static int jugadorEnTurno;
    public Context context ;
    Button botonJugador1;
    Button botonJugador2;
    Button botonJugador3;
    Button botonJugador4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MediaPlayer cuadro = MediaPlayer.create(this,R.raw.pop);
        Intent intent = getIntent();
        Bundle otrasVariables = intent.getExtras();
        cantidadDeJugadores = otrasVariables.getInt("cantidadDeJugadores", 0);
        simboloOperacion = otrasVariables.getString("SimboloOperacion", "-1");
        System.out.println("CANTIDA DE JUGADORES --------------------------------" + cantidadDeJugadores);
        System.out.println("Simbolo--------------------------------" + simboloOperacion);
        crearListasDeJugadores();
        crearPreguntas();
        jugadorEnTurno = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_tablero);
        cargarBotones();
        hacerVisibleBotonesJugadorEnTurno(cantidadDeJugadores);
        Intent intentAPreguntas = new Intent(Pantalla_Tablero.this, Pantalla_Preguntas.class);
        intentAPreguntas.putExtra("preguntas", listaPreguntas);
        intentAPreguntas.putExtra("jugadorEnTurno", jugadorEnTurno);
        startActivityForResult(intentAPreguntas, 1);

        //habilitarAlgunosBotones(Integer.parseInt(listaBotones.get(2).getText().toString()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        cambiarDeJugadorEnInterfaz();
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("result",-1);
                if(result ==-1){
                    cambiarDeJugador();
                }else{
                    habilitarAlgunosBotones(result);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    public  static  void habilitarTodosLosBotones(){
        for (Button boton:listaBotones) {
            if(boton.isEnabled()==true){
                boton.setTextSize(23);



            }
            if(boton.isEnabled()==false) {
                boton.setEnabled(true);
                boton.setTextSize(23);
                boton.setAlpha(1.0f);

            }

        }
    }

    public static void habilitarAlgunosBotones(int numeroBoton){
         ;
        for (Button boton:listaBotones) {
            if(!boton.getText().equals(String.valueOf(numeroBoton))){
                boton.setEnabled(false);
                boton.setTextSize(18);
                boton.setAlpha(0.5f);

            }if(boton.getText().equals(String.valueOf(numeroBoton))){
                boton.setTextSize(27);

            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            startActivity(new Intent(Pantalla_Tablero.this, Pantalla_Inicio.class));
           // Pantalla_Inicio.musicaFondo.release();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public  void colocarEnLista(View Boton){
        habilitarTodosLosBotones();
        context = getApplicationContext();
        int etiquetaDelBoton = Integer.parseInt(Boton.getTag().toString());
        colocarBotonSegunJugador(Boton, jugadorEnTurno);
        if(!botonEstaEnAlgunaLista(Boton)){
            if (jugadorEnTurno==1){
                listaJugador1.add(etiquetaDelBoton);

            }else if (jugadorEnTurno==2){
                listaJugador2.add(etiquetaDelBoton);

            }else if (jugadorEnTurno==3){
                listaJugador3.add(etiquetaDelBoton);

            }else{
                listaJugador4.add(etiquetaDelBoton);

            }
        }else{
            quitarBotonDeLaLista(Boton);
            if (jugadorEnTurno==1){
                listaJugador1.add(etiquetaDelBoton);

            }else if (jugadorEnTurno==2){
                listaJugador2.add(etiquetaDelBoton);

            }else if (jugadorEnTurno==3){
                listaJugador3.add(etiquetaDelBoton);

            }else{
                listaJugador4.add(etiquetaDelBoton);

            }
        }

        Collections.sort(getListaJugadorEnTurno());
        if(verificarSiHayGanador(getListaJugadorEnTurno())){
            Toast.makeText(context,"El ganador es: Jugador "+jugadorEnTurno,Toast.LENGTH_LONG).show();
        }if(!verificarSiHayGanador(getListaJugadorEnTurno())){
                cambiarDeJugador();
        }

    }

    public static void colocarBotonSegunJugador(View Boton,int jugadorEnTurno){

        if(jugadorEnTurno==1){
            Boton.setBackgroundResource(R.drawable.btn_jugador_uno);
        }else if(jugadorEnTurno==2){
            Boton.setBackgroundResource(R.drawable.btn_jugador_dos);
        }else if(jugadorEnTurno==3){
            Boton.setBackgroundResource(R.drawable.btn_jugador_tres);
        }else if(jugadorEnTurno ==4){
            Boton.setBackgroundResource(R.drawable.btn_jugador_cuatro);
        }
    }
    public static boolean verificarSiHayGanador(ArrayList<Integer> listaJugadorEnTurno){
        boolean hayGanador = false;

        if(hayCuatroEnLineaHorizontal(listaJugadorEnTurno)){
            hayGanador=true;
            System.out.println("HAY GANADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
        }else if(hayCuatroEnLineaVertical(listaJugadorEnTurno)){
            hayGanador=true;
            System.out.println("HAY GANADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
        }else if(hayCuatroEnLineaDiagonalBackSlash(listaJugadorEnTurno)){
           hayGanador=true;
            System.out.println("HAY GANADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
        }

        else if(hayCuatroEnLineaDiagonalSlash(listaJugadorEnTurno)){
            hayGanador=true;
            System.out.println("HAY GANADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
        }

        return hayGanador;
    }
    public static boolean hayCuatroEnLineaDiagonalSlash(ArrayList<Integer> listaJugadorEnTurno){
        boolean hayCuatroEnLineaSlash = false;
        int i = 0;
        int cantEnLinea=1;
        ArrayList<Integer> listaDiagonalSlash = new ArrayList<>();
        if(listaJugadorEnTurno.size()>3){
            while(i<listaJugadorEnTurno.size()-1){
                listaDiagonalSlash.clear();
                listaDiagonalSlash.add(listaJugadorEnTurno.get(i));
                int indiceListaDiagonalSlash =0;
                int j = i+1;

                while (j<listaJugadorEnTurno.size()) {

                    if (listaDiagonalSlash.get(indiceListaDiagonalSlash) == listaJugadorEnTurno.get(j)-7){
                        listaDiagonalSlash.add(listaJugadorEnTurno.get(j));
                        indiceListaDiagonalSlash++;
                        j++;
                    }
                    else if (listaDiagonalSlash.get(indiceListaDiagonalSlash) != listaJugadorEnTurno.get(j)-7){
                        j++;
                    }
                    if(listaDiagonalSlash.size()==4){
                        hayCuatroEnLineaSlash=true;
                        break;
                    }
                }
                if(listaDiagonalSlash.size()==4){
                    hayCuatroEnLineaSlash=true;

                    break;
                }
                i++;
            }
        }
        return hayCuatroEnLineaSlash;

    }
    public static boolean hayCuatroEnLineaDiagonalBackSlash(ArrayList<Integer> listaJugadorEnTurno){

        boolean hayCuatroEnLineaBackSlash = false;
        int i = 0;
        int cantEnLinea=1;
        ArrayList<Integer> listaDiagonalBackSlash= new ArrayList<>();
        if(listaJugadorEnTurno.size()>3){
            while(i<listaJugadorEnTurno.size()-1){
                listaDiagonalBackSlash.clear();
                listaDiagonalBackSlash.add(listaJugadorEnTurno.get(i));
                int indiceListaDiagonalBackSlash =0;
                int j = i+1;

                while (j<listaJugadorEnTurno.size()) {

                    if (listaDiagonalBackSlash.get(indiceListaDiagonalBackSlash) == listaJugadorEnTurno.get(j)-9){
                        listaDiagonalBackSlash.add(listaJugadorEnTurno.get(j));
                        indiceListaDiagonalBackSlash++;
                        j++;
                    }
                    else if (listaDiagonalBackSlash.get(indiceListaDiagonalBackSlash) != listaJugadorEnTurno.get(j)-9){
                        j++;
                    }
                    if(listaDiagonalBackSlash.size()==4){
                        hayCuatroEnLineaBackSlash =true;
                        break;
                    }
                }
                if(listaDiagonalBackSlash.size()==4){
                    hayCuatroEnLineaBackSlash=true;
                    break;
                }
                i++;
            }
        }
        return hayCuatroEnLineaBackSlash;
    }
    public static boolean hayCuatroEnLineaVertical(ArrayList<Integer> listaJugadorEnTurno){
        boolean hayCuatroEnLineaVertical = false;
        int i = 0;
        int cantEnLinea=1;
        ArrayList<Integer> listaVertical = new ArrayList<>();
        if(listaJugadorEnTurno.size()>3){
            while(i<listaJugadorEnTurno.size()-1){
                listaVertical.clear();
                listaVertical.add(listaJugadorEnTurno.get(i));
                int indiceListaVertical =0;
                int j = i+1;

                while (j<listaJugadorEnTurno.size()) {

                    if (listaVertical.get(indiceListaVertical) == listaJugadorEnTurno.get(j)-8){
                        listaVertical.add(listaJugadorEnTurno.get(j));
                        indiceListaVertical++;
                        j++;
                    }
                    else if (listaVertical.get(indiceListaVertical) != listaJugadorEnTurno.get(j)-8){
                        j++;
                    }
                    if(listaVertical.size()==4){
                        hayCuatroEnLineaVertical =true;
                        break;
                    }
                }
                if(listaVertical.size()==4){
                    hayCuatroEnLineaVertical =true;
                    break;
                }
                i++;
            }
        }
        return hayCuatroEnLineaVertical;
    }
    public static boolean hayCuatroEnLineaHorizontal(ArrayList<Integer> listaJugadorEnTurno){
        boolean hayCuatroEnLineaHorizontal = false;
            int i = 0;
            int cantEnLinea=1;
        if(listaJugadorEnTurno.size()>3){
            while(i<listaJugadorEnTurno.size()-1){
                if (listaJugadorEnTurno.get(i)-listaJugadorEnTurno.get(i+1) ==-1){

                        cantEnLinea++;
                }else if(listaJugadorEnTurno.get(i)-listaJugadorEnTurno.get(i+1) !=-1){

                    cantEnLinea=1;
                }
                if (cantEnLinea==4){

                    hayCuatroEnLineaHorizontal =true;
                    break;
                }
                i++;
            }
        }
        return hayCuatroEnLineaHorizontal;
    }
    public static Boolean botonEstaEnAlgunaLista(View boton){
        Boolean botonEstaEnLista = false;
        int idDelBoton = Integer.parseInt(boton.getTag().toString());
        if(listaJugador1.contains(idDelBoton)){botonEstaEnLista=true;}
        if(listaJugador2.contains(idDelBoton)){botonEstaEnLista=true;}
        if(listaJugador3.contains(idDelBoton)){botonEstaEnLista=true;}
        if(listaJugador4.contains(idDelBoton)){botonEstaEnLista=true;}
        return botonEstaEnLista;
    }
    public void cambiarDeJugador(){
        if(jugadorEnTurno ==cantidadDeJugadores){
            jugadorEnTurno=1;
        }else{
            jugadorEnTurno++;
        }

        System.out.println("EL JUGADOR EN TURNO ES: " + jugadorEnTurno);
        Intent intentAPreguntas = new Intent(Pantalla_Tablero.this, Pantalla_Preguntas.class);
        intentAPreguntas.putExtra("preguntas", listaPreguntas);
        intentAPreguntas.putExtra("jugadorEnTurno", jugadorEnTurno);
        startActivityForResult(intentAPreguntas, 1);
    }
    public static ArrayList<Integer> getListaJugadorEnTurno(){
        ArrayList<Integer> listaJugadorEnTurno = new ArrayList<>();
        if (jugadorEnTurno==1){
            listaJugadorEnTurno = listaJugador1;
        } else if (jugadorEnTurno==2){
            listaJugadorEnTurno = listaJugador2;
        } else if (jugadorEnTurno==3){
            listaJugadorEnTurno = listaJugador3;
        }else{
            listaJugadorEnTurno = listaJugador4;
        }
        return listaJugadorEnTurno;
    }
    public static void crearListasDeJugadores(){

            listaJugador1 = new ArrayList<>() ;
            listaJugador2 = new ArrayList<>() ;
            listaJugador3 = new ArrayList<>() ;
            listaJugador4 = new ArrayList<>() ;
            listaJugador1.clear();
            listaJugador2.clear();
            listaJugador3.clear();
            listaJugador4.clear();

    }
    public static void quitarBotonDeLaLista(View boton){
        int idDelBoton = Integer.parseInt(boton.getTag().toString());

            if(listaJugador1.contains(idDelBoton)){
                listaJugador1.remove(listaJugador1.indexOf(idDelBoton));

            }
            if(listaJugador2.contains(idDelBoton)){
                listaJugador2.remove(listaJugador2.indexOf(idDelBoton));

            }
            if(listaJugador3.contains(idDelBoton)){
                listaJugador3.remove(listaJugador3.indexOf(idDelBoton));

            }
            if(listaJugador4.contains(idDelBoton)){
                listaJugador4.remove(listaJugador4.indexOf(idDelBoton));

            }
    }
    public static void crearPreguntas(){

        /*
        * En general, para conseguir un número entero entre M y N con M menor que N y ambos incluídos, debemos usar esta fórmula
  int valorEntero = Math.floor(Math.random()*(N-M+1)+M);  // Valor entre M y N, ambos incluidos.*/
        ArrayList<Integer> listaOperando1 = new ArrayList<Integer>();
        ArrayList<Integer> listaOperando2 = new ArrayList<Integer>();
        int i = 0;
        while (i<64){
            if(simboloOperacion.equals("-")||simboloOperacion.equals("+")||simboloOperacion.equals("/"))
            {
                int numeroRandom1 = (int) Math.floor(Math.random()*(35-4+1)+4);
                listaOperando1.add(numeroRandom1);
                int numeroRandom2 = (int) Math.floor(Math.random()*(35-4+1)+4);
                listaOperando2.add(numeroRandom2);
            }
            if(simboloOperacion.equals("x"))
            {
                int numeroRandom1 = (int) Math.floor(Math.random()*(10-1+1)+1);
                listaOperando1.add(numeroRandom1);
                int numeroRandom2 = (int) Math.floor(Math.random()*(10-1+1)+1);
                listaOperando2.add(numeroRandom2);
            }

            i++;
        }
        if(simboloOperacion.equals("+")){
            i=0;
            for(int operando1:listaOperando1){
                int operando2 = listaOperando2.get(i);
                int respuesta  = hacerOperacion(operando1,operando2,"+");
                Pregunta pregunta = new Pregunta(operando1,operando2,"+",respuesta);
                listaPreguntas.add(pregunta);
                i++;

            }
        }
        if(simboloOperacion.equals("-")){
            i=0;
            for(int operando1:listaOperando1){
                int operando2 = listaOperando2.get(i);
                while(operando1-operando2 < 0){
                    operando2 = (int) Math.floor(Math.random()*(35-4+1)+4);
                }
                int respuesta = hacerOperacion(operando1,operando2,"-");
                Pregunta pregunta = new Pregunta(operando1,operando2,"-",respuesta);
                listaPreguntas.add(pregunta);
                i++;

            }
        }
        if(simboloOperacion.equals("x")){
            i=0;
            for(int operando1:listaOperando1){
                int operando2 = listaOperando2.get(i);
                int respuesta  = hacerOperacion(operando1,operando2,"x");
                Pregunta pregunta = new Pregunta(operando1,operando2,"x",respuesta);
                listaPreguntas.add(pregunta);
                i++;

            }
        }
        if(simboloOperacion.equals("/")){
            i=0;
            for(int operando1:listaOperando1){
                int operando2 = listaOperando2.get(i);
                while(operando1%operando2 != 0){
                    operando2 = (int) Math.floor(Math.random()*(10-1+1)+1);;
                }
                int respuesta = hacerOperacion(operando1,operando2,"/");
                Pregunta pregunta = new Pregunta(operando1,operando2,"/",respuesta);
                listaPreguntas.add(pregunta);
                i++;

            }
        }



    }
    public static int hacerOperacion(int operando1,int operando2,String simbolo){
        int resultado = 0;
        if(simbolo.equals("+")){
            resultado = operando1+operando2;
        }
        if(simbolo.equals("-")){
            resultado = operando1-operando2;
        }
        if(simbolo.equals("x")){
            resultado = operando1*operando2;
        }
        if(simbolo.equals("/")){
            resultado = operando1/operando2;
        }
        return resultado;
    }
    public  void cargarBotones(){
        Button boton1 = (Button) findViewById(R.id.button1);
        listaBotones.add(boton1);
        Button boton2 = (Button) findViewById(R.id.button5);
        listaBotones.add(boton2);
        Button boton3 = (Button) findViewById(R.id.button6);
        listaBotones.add(boton3);
        Button boton4 = (Button) findViewById(R.id.button7);
        listaBotones.add(boton4);
        Button boton5 = (Button) findViewById(R.id.button8);
        listaBotones.add(boton5);
        Button boton6 = (Button) findViewById(R.id.button9);
        listaBotones.add(boton6);
        Button boton7 = (Button) findViewById(R.id.button10);
        listaBotones.add(boton7);
        Button boton8 = (Button) findViewById(R.id.button11);
        listaBotones.add(boton8);
        Button boton9 = (Button) findViewById(R.id.button12);
        listaBotones.add(boton9);
        Button boton10 = (Button) findViewById(R.id.button13);
        listaBotones.add(boton10);
        Button boton11 = (Button) findViewById(R.id.button14);
        listaBotones.add(boton11);
        Button boton12 = (Button) findViewById(R.id.button15);
        listaBotones.add(boton12);
        Button boton13 = (Button) findViewById(R.id.button16);
        listaBotones.add(boton13);
        Button boton14 = (Button) findViewById(R.id.button17);
        listaBotones.add(boton14);
        Button boton15 = (Button) findViewById(R.id.button18);
        listaBotones.add(boton15);
        Button boton16 = (Button) findViewById(R.id.button19);
        listaBotones.add(boton16);
        Button boton17 = (Button) findViewById(R.id.button20);
        listaBotones.add(boton17);
        Button boton18 = (Button) findViewById(R.id.button21);
        listaBotones.add(boton18);
        Button boton19 = (Button) findViewById(R.id.button22);
        listaBotones.add(boton19);
        Button boton20 = (Button) findViewById(R.id.button23);
        listaBotones.add(boton20);
        Button boton21 = (Button) findViewById(R.id.button24);
        listaBotones.add(boton21);
        Button boton22 = (Button) findViewById(R.id.button25);
        listaBotones.add(boton22);
        Button boton23 = (Button) findViewById(R.id.button26);
        listaBotones.add(boton23);
        Button boton24 = (Button) findViewById(R.id.button27);
        listaBotones.add(boton24);
        Button boton25 = (Button) findViewById(R.id.button28);
        listaBotones.add(boton25);
        Button boton26 = (Button) findViewById(R.id.button29);
        listaBotones.add(boton26);
        Button boton27 = (Button) findViewById(R.id.button30);
        listaBotones.add(boton27);
        Button boton28 = (Button) findViewById(R.id.button31);
        listaBotones.add(boton28);
        Button boton29 = (Button) findViewById(R.id.button32);
        listaBotones.add(boton29);
        Button boton30 = (Button) findViewById(R.id.button33);
        listaBotones.add(boton30);
        Button boton31 = (Button) findViewById(R.id.button34);
        listaBotones.add(boton31);
        Button boton32 = (Button) findViewById(R.id.button35);
        listaBotones.add(boton32);
        Button boton33 = (Button) findViewById(R.id.button36);
        listaBotones.add(boton33);
        Button boton34 = (Button) findViewById(R.id.button37);
        listaBotones.add(boton34);
        Button boton35 = (Button) findViewById(R.id.button38);
        listaBotones.add(boton35);
        Button boton36 = (Button) findViewById(R.id.button39);
        listaBotones.add(boton36);
        Button boton37 = (Button) findViewById(R.id.button40);
        listaBotones.add(boton37);
        Button boton38 = (Button) findViewById(R.id.button41);
        listaBotones.add(boton38);
        Button boton39 = (Button) findViewById(R.id.button42);
        listaBotones.add(boton39);
        Button boton40 = (Button) findViewById(R.id.button43);
        listaBotones.add(boton40);
        Button boton41 = (Button) findViewById(R.id.button44);
        listaBotones.add(boton41);
        Button boton42 = (Button) findViewById(R.id.button45);
        listaBotones.add(boton42);
        Button boton43 = (Button) findViewById(R.id.button46);
        listaBotones.add(boton43);
        Button boton44 = (Button) findViewById(R.id.button47);
        listaBotones.add(boton44);
        Button boton45 = (Button) findViewById(R.id.button48);
        listaBotones.add(boton45);
        Button boton46 = (Button) findViewById(R.id.button49);
        listaBotones.add(boton46);
        Button boton47 = (Button) findViewById(R.id.button50);
        listaBotones.add(boton47);
        Button boton48 = (Button) findViewById(R.id.button51);
        listaBotones.add(boton48);
        Button boton49 = (Button) findViewById(R.id.button52);
        listaBotones.add(boton49);
        Button boton50 = (Button) findViewById(R.id.button53);
        listaBotones.add(boton50);
        Button boton51 = (Button) findViewById(R.id.button54);
        listaBotones.add(boton51);
        Button boton52 = (Button) findViewById(R.id.button55);
        listaBotones.add(boton52);
        Button boton53 = (Button) findViewById(R.id.button56);
        listaBotones.add(boton53);
        Button boton54 = (Button) findViewById(R.id.button57);
        listaBotones.add(boton54);
        Button boton55 = (Button) findViewById(R.id.button58);
        listaBotones.add(boton55);
        Button boton56 = (Button) findViewById(R.id.button59);
        listaBotones.add(boton56);
        Button boton57 = (Button) findViewById(R.id.button60);
        listaBotones.add(boton57);
        Button boton58 = (Button) findViewById(R.id.button61);
        listaBotones.add(boton58);
        Button boton59 = (Button) findViewById(R.id.button62);
        listaBotones.add(boton59);
        Button boton60 = (Button) findViewById(R.id.button63);
        listaBotones.add(boton60);
        Button boton61 = (Button) findViewById(R.id.button64);
        listaBotones.add(boton61);
        Button boton62 = (Button) findViewById(R.id.button65);
        listaBotones.add(boton62);
        Button boton63 = (Button) findViewById(R.id.button66);
        listaBotones.add(boton63);
        Button boton64 = (Button) findViewById(R.id.button67);
        listaBotones.add(boton64);
        int i = 0;
        for (Pregunta pregunta:listaPreguntas) {
            Button boton = listaBotones.get(i);
            boton.setText(String.valueOf(pregunta.getResultado()));
            i++;
        }


    }
    public void hacerVisibleBotonesJugadorEnTurno(int cantidadDeJugadores){
        botonJugador1 = (Button) findViewById(R.id.button);
        botonJugador2 = (Button) findViewById(R.id.button2);
        botonJugador3 = (Button) findViewById(R.id.button3);
        botonJugador4 = (Button) findViewById(R.id.button4);

        if(cantidadDeJugadores ==2){
            botonJugador3.setVisibility(View.INVISIBLE);
            botonJugador4.setVisibility(View.INVISIBLE);
        }
        if(cantidadDeJugadores ==3){
            botonJugador4.setVisibility(View.INVISIBLE);
        }

    }

    public void cambiarDeJugadorEnInterfaz(){

        if(jugadorEnTurno==1){
            botonJugador1.setBackgroundColor(Color.argb(255,166,142,114));
            botonJugador1.setTextColor(Color.argb(255, 216, 193, 148));

            botonJugador2.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador2.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador3.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador3.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador4.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador4.setTextColor(Color.argb(255, 166, 142, 114));
        }
        if(jugadorEnTurno==2){
            botonJugador1.setBackgroundColor(Color.argb(255,216,193,148));
            botonJugador1.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador2.setBackgroundColor(Color.argb(255, 166, 142, 114));
            botonJugador2.setTextColor(Color.argb(255, 216, 193, 148));

            botonJugador3.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador3.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador4.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador4.setTextColor(Color.argb(255, 166, 142, 114));
        }
        if(jugadorEnTurno==3){
            botonJugador1.setBackgroundColor(Color.argb(255,216,193,148));
            botonJugador1.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador2.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador2.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador3.setBackgroundColor(Color.argb(255, 166, 142, 114));
            botonJugador3.setTextColor(Color.argb(255, 216, 193, 148));

            botonJugador4.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador4.setTextColor(Color.argb(255, 166, 142, 114));
        }
        if(jugadorEnTurno==4){
            botonJugador1.setBackgroundColor(Color.argb(255, 216, 193, 148));
            botonJugador3.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador2.setBackgroundColor(Color.argb(255,216,193,148));
            botonJugador3.setTextColor(Color.argb(255, 166, 142, 114));

            botonJugador3.setBackgroundColor(Color.argb(255,216,193,148));
            botonJugador3.setTextColor(Color.argb(255,166,142,114));

            botonJugador4.setBackgroundColor(Color.argb(255,166,142,114));
            botonJugador4.setTextColor(Color.argb(255, 216, 193, 148));
        }
    }
}
