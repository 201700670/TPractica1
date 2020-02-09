/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import Analizador.Token.Tipo;
import Interfaz.VPrincipal;
import java.util.LinkedList;

/**
 *
 * @author Andrea Palomo
 */
public class AnalizadorTexto {

    public LinkedList<Token> salida;
    public LinkedList<Token> errores;
    private Integer estado;
    private String auxLex;
    private Integer fila, columna;
    private String imprime = "";
    String nombreconjunto="";
    String inicioconj,finconj;
    public LinkedList<Token> escanear(String entrada) {
        salida = new LinkedList<Token>();
        errores = new LinkedList<Token>();
        estado = 0;
        columna = 0;
        fila = 1;
        auxLex = "";
        int muletilla = 0, muleta = 0;// SON MULETAS PARA LOS COMENTARIOS
        String c;
        char d;
        for (int i = 0; i < entrada.length(); i++) {
            //System.out.println("ESTO ES LO DE LA ENTRADA   "+ entrada.charAt(i));
            c = String.valueOf(entrada.charAt(i));
            d = entrada.charAt(i);
            columna++;
            switch (estado) {
                case 0:
                    if (c.equals("{")) {
                        auxLex += c;
                        addToken(Tipo.LLAVEIZQ, auxLex, fila, columna);
                        estado = 1;
                        auxLex = "";
                    } else if (c.equals("\n") || c.equals("\t") || c.equals("\r")) {
                        columna = 0;
                        fila += 1;
                        estado = 0;
                        auxLex = "";
                    } else {
                        auxLex += c;
                        addError(Tipo.DESCONOCIDO, auxLex, "Caracter no definido", fila, columna);
                        System.out.println("ERROR LEXICO CON: " + auxLex + " " + fila + "," + columna);
                        auxLex = "";
                        estado = 0;
                    }
                    break;
                case 1:////ESTE SERVIRA PARA LOS COMENTARIOS Y EL INICIO DE LOS CONJUNTOS PARA LAS EXPRESIONES O SI VIENE UN "-" PARA
                    //UN IDENTIFICADOR DE UNA GRAMATICA O "ESTADO"
                    if (c.equals("/")) {
                        auxLex += c;
                        addToken(Tipo.DIVISION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;

                    } else if (c.equals("<")) {
                        auxLex += c;
                        addToken(Tipo.MENOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;

                    } else if (c.equals(">")) {
                        auxLex += c;
                        addToken(Tipo.MAYOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;

                    } else if (c.equals("!")) {
                        if (muleta == 1) {
                            addToken(Tipo.COMENTARIO, auxLex, fila, columna);
                            muleta = 0;
                            estado = 1;
                            columna = 0;
                            fila += 1;
                            auxLex = "";
                        }
                        auxLex += c;
                        addToken(Tipo.ADMIRACION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                        muleta = 1;

                    } else if (c.equals(" ")) {
                        auxLex += c;
                        estado = 1;
                    } else if (Character.isLetter(d)) {
                        auxLex += c;
                        if (auxLex.equals("CONJ")) {
                            addToken(Tipo.RESERVADA, auxLex, fila, columna);
                            auxLex = "";
                            estado = 2;
                        }
                        estado = 1;
                    } else if (Character.isDigit(d)) {
                        auxLex += c;
                        estado = 1;
                    } else if (c.equals("\n") || c.equals("\t") || c.equals("\r")) {
                        if (muletilla == 1 && muleta != 1) {
                            addToken(Tipo.COMENTARIO, auxLex, fila, columna);
                            muletilla = 0;
                            estado = 1;
                            columna = 0;
                            fila += 1;
                        }
                        columna = 0;
                        fila += 1;
                        estado = 1;
                        muletilla = 1;
                    } else {
                        auxLex += c;
                        addError(Tipo.DESCONOCIDO, auxLex, "Caracter no definido", fila, columna);
                        System.out.println("ERROR LEXICO CON: " + auxLex + " " + fila + "," + columna);
                        auxLex = "";
                        estado = 1;
                    }
                    break;
                case 2:
                    if(c.equals(":")){
                        auxLex += c;
                        addToken(Tipo.DOSPUNTOS, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                    }else if (Character.isLetter(d)) {
                        auxLex += c;
                        estado = 1;
                    }
                    break;
            }
        }
        if (errores.size() > 0) {
            return errores;
        } else {
            return salida;
        }
    }

    public void addToken(Tipo tipo, String lexema, Integer fila, Integer columna) {
        salida.add(new Token(tipo, auxLex, fila, columna));
        auxLex = "";
        estado = 0;
    }

    public void addError(Tipo tipo, String lexema, String descripcion, Integer filas, Integer columnas) {
        errores.add(new Token(Tipo.DESCONOCIDO, auxLex, descripcion, fila, columna));
        auxLex = "";
        estado = 0;
    }

    public void imprimirLista(LinkedList<Token> l) {
        l.forEach((t) -> {
            System.out.println(t.getTipoEnString() + "<-->" + t.getValor() + "<-->" + t.getFila() + "<-->" + t.getColumna());
            imprime += String.valueOf(t.getTipoEnString() + " <--> " + t.getValor() + " <--> " + t.getFila() + " <--> " + t.getColumna());
            //System.out.println(imprime);
        });

    }
}
