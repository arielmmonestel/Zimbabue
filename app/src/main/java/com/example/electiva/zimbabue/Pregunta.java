package com.example.electiva.zimbabue;

/**
 * Created by ariel on 17/4/2016.
 */
public class Pregunta {
    private int operandoUno;

    public Pregunta(int operandoUno, int operandoDos, String operacion, int resultado) {
        this.operandoUno = operandoUno;
        this.operandoDos = operandoDos;
        this.operacion = operacion;
        this.resultado = resultado;
    }

    private int operandoDos;

    public int getOperandoUno() {
        return operandoUno;
    }

    public void setOperandoUno(int operandoUno) {
        this.operandoUno = operandoUno;
    }

    public int getOperandoDos() {
        return operandoDos;
    }

    public void setOperandoDos(int operandoDos) {
        this.operandoDos = operandoDos;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    private String operacion;
    private int resultado;


}
