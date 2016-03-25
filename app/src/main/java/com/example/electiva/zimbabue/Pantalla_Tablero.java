package com.example.electiva.zimbabue;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Pantalla_Tablero extends AppCompatActivity {


    public static ArrayList<Integer> listaJugador1 ;
    public static ArrayList<Integer>  listaJugador2 ;
    public static ArrayList<Integer>  listaJugador3 ;
    public static ArrayList<Integer>  listaJugador4 ;
    public static int cantidadDeJugadores=0;
     public static int jugadorEnTurno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle otrasVariables = intent.getExtras();
        cantidadDeJugadores = otrasVariables.getInt("cantidadDeJugadores",0);
        System.out.println("CANTIDA DE JUGADORES --------------------------------"+cantidadDeJugadores);
        crearListasDeJugadores();
        jugadorEnTurno = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_tablero);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            startActivity(new Intent(Pantalla_Tablero.this, Pantalla_Inicio.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void colocarEnLista(View Boton){
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

        }
        cambiarDeJugador();

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
        }else if(hayCuatroEnLineaDiagonalSlash(listaJugadorEnTurno)){
            hayGanador=true;
            System.out.println("HAY GANADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
        }

        return hayGanador;
    } public static boolean hayCuatroEnLineaDiagonalSlash(ArrayList<Integer> listaJugadorEnTurno){
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
                    else if (listaDiagonalSlash.get(indiceListaDiagonalSlash) != listaJugadorEnTurno.get(j)-9){
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

    public static void cambiarDeJugador(){
        if(jugadorEnTurno ==cantidadDeJugadores){
            jugadorEnTurno=1;
        }else{
            jugadorEnTurno++;
        }
        System.out.println("EL JUGADOR EN TURNO ES: "+jugadorEnTurno);

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
}
