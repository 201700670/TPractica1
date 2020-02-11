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
    String nombreconjunto = "";
    String inicioconj, finconj;
    Integer contador = 0;

    public LinkedList<Token> escanear(String entrada) {
        salida = new LinkedList<Token>();
        errores = new LinkedList<Token>();
        estado = 0;
        columna = 0;
        fila = 1;
        auxLex = "";
        int muletilla = 0, muleta = 0, aux = 0;// SON MULETAS PARA LOS COMENTARIOS
        String c;
        char d;
        for (int i = 0; i < entrada.length(); i++) {
            //System.out.println("ESTO ES LO DE LA ENTRADA   "+ entrada.charAt(i));
            c = String.valueOf(entrada.charAt(i));
            d = entrada.charAt(i);

            columna++;
            switch (estado) {
                case 0:
                    ///************************************ESTADO INICIAL****************************************************
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
                case 1:
////*********ESTE SERVIRA PARA LOS COMENTARIOS Y EL INICIO DE LOS CONJUNTOS PARA LAS EXPRESIONES O SI VIENE UN "-" PARA***************
                    //UN IDENTIFICADOR DE UNA GRAMATICA O "ESTADO"
                    if (c.equals("/")) {
                        auxLex += c;
                        addToken(Tipo.DIVISION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;

                    } else if (c.equals("<")) {
                        if (muletilla == 1 && !auxLex.equals("")) {
                            addToken(Tipo.COMENTARIO, auxLex, fila, columna);
                            muletilla = 0;
                            estado = 1;
                        }
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
                        aux++;
                        if (muleta == 1 && !auxLex.equals("")) {
                            addToken(Tipo.COMENTARIO, auxLex, fila, columna);
                            muleta = 0;
                            estado = 1;
                            auxLex = "";
                        }
                        auxLex += c;
                        addToken(Tipo.ADMIRACION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                        if (aux == 2) {
                            muleta = 0;
                            aux = 0;
                        } else {
                            muleta = 1;
                        }

                    } else if (c.equals(" ")) {
                        auxLex += c;
                        estado = 1;
                    } else if (Character.isLetter(d)) {
                        auxLex += c;
                        if (auxLex.equals("CONJ")) {
                            addToken(Tipo.RESERVADA, auxLex, fila, columna);
                            auxLex = "";
                            estado = 2;
                            break;
                        }
                        estado = 1;
                    } else if (Character.isDigit(d)) {
                        auxLex += c;
                        estado = 1;
                    } else if (c.equals("\n")) {
                        if (muletilla == 1 && muleta != 1) {
                            addToken(Tipo.COMENTARIO, auxLex, fila, columna);
                            muletilla = 0;
                            estado = 1;
                        }
                        columna = 0;
                        fila += 1;
                        estado = 1;
                        muletilla = 1;
                    } else if (c.equals("\t") || c.equals("\r")) {
                        fila += 1;
                        estado = 1;
                    } else if (c.equals("-")) {
                        addToken(Tipo.IDENTIFICADOR, auxLex, fila, columna);
                        auxLex += c;
                        addToken(Tipo.MENOS, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;/////////ESTE ME LLEVARA PARA LAS EXPRESIONE REGULARES CON SUS IDENTIFICADORES
                        break;
                    }else if (c.equals("%")) {
                        auxLex += c;
                        addToken(Tipo.PORCENTAJE, auxLex, fila, columna);
                        auxLex = "";
                        estado = 4;
                        break;
                    } else {
                        auxLex += c;
                        addError(Tipo.DESCONOCIDO, auxLex, "Caracter no definido", fila, columna);
                        System.out.println("ERROR LEXICO CON: " + auxLex + " " + fila + "," + columna);
                        auxLex = "";
                        estado = 1;
                    }
                    break;
                case 2:
//*******************************ESTE ES PARA HACER EL RECONOCIMIENTO DE LOS CONJUNTOS Y SUS IDENTIFICADORES.*************************
                    if (c.equals(":")) {
                        if (auxLex.equals("CONJ")) {
                            addToken(Tipo.RESERVADA, auxLex, fila, columna);
                        }
                        auxLex += c;
                        addToken(Tipo.DOSPUNTOS, auxLex, fila, columna);
                        auxLex = "";
                        estado = 2;
                    } else if (Character.isLetter(d)) {
                        auxLex += c;
                        estado = 2;
                    } else if (Character.isDigit(d)) {
                        auxLex += c;
                        estado = 2;
                    } else if (c.equals("-")) {
                        addToken(Tipo.IDENTIFICADOR, auxLex, fila, columna);
                        auxLex += c;
                        addToken(Tipo.MENOS, auxLex, fila, columna);
                        auxLex = "";
                        estado = 2;
                    } else if (c.equals(">")) {
                        auxLex += c;
                        addToken(Tipo.MAYOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 2;

                    } else if (c.equals(" ")) {
                        columna += 1;
                        estado = 2;
                    } else if (c.equals("~")) {
                        inicioconj = auxLex;
                        addToken(Tipo.CONJUNTOINICIO, auxLex, fila, columna);
                        auxLex += c;
                        addToken(Tipo.VIRGULILLA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 2;
                    } else if (c.equals(",")) {
                        contador++;
                        if (contador == 1) {
                            inicioconj = auxLex;
                        }
                        addToken(Tipo.CONJUNTO, auxLex, fila, columna);
                        auxLex += c;
                        addToken(Tipo.COMA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 2;
                    } else if (c.equals(";")) {
                        finconj = auxLex;
                        addToken(Tipo.CONJUNTOFINAL, auxLex, fila, columna);
                        auxLex += c;
                        addToken(Tipo.PUNTOYCOMA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 2;
                        break;
                    } else if (c.equals("\n")) {
                        columna = 0;
                        fila += 1;
                        estado = 1;
                        auxLex = "";
                        break;
                    } else if (c.equals("\t") || c.equals("\r")) {
                        fila += 1;
                        estado = 2;
                        auxLex = "";
                    } else if (c.equals("/")) {
                        auxLex += c;
                        addToken(Tipo.DIVISION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;

                    } else if (c.equals("<")) {
                        auxLex += c;
                        addToken(Tipo.MENOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;

                    } else {
                        auxLex += c;
                        addError(Tipo.DESCONOCIDO, auxLex, "Caracter no definido", fila, columna);
                        System.out.println("ERROR LEXICO2 CON: " + auxLex + " " + fila + "," + columna);
                        auxLex = "";
                        estado = 2;
                    }
                    break;
                case 3:
//9****************************************ESTEE CONTENDRA LOS IDENTIFICADORES Y LAS EXPRESIONES REGULARES****************************
                    if (c.equals(">")) {
                        auxLex += c;
                        addToken(Tipo.MAYOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (c.equals(" ")) {
                        columna += 1;
                        estado = 3;
                    } else if (c.equals(".")) {
                        auxLex += c;
                        addToken(Tipo.CONCATENACION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (c.equals("|")) {
                        auxLex += c;
                        addToken(Tipo.OR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (c.equals("+")) {
                        auxLex += c;
                        addToken(Tipo.SUMA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (c.equals("*")) {
                        auxLex += c;
                        addToken(Tipo.MULTIPLICACION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (c.equals("?")) {
                        auxLex += c;
                        addToken(Tipo.INTERROGACION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (Character.isLetter(d)) {
                        auxLex += c;
                        estado = 3;
                    } else if (Character.isDigit(d)) {
                        auxLex += c;
                        estado = 3;
                    } else if (c.equals("\"")) {

                        auxLex += c;
                        addToken(Tipo.COMILLA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (c.equals("{")) {
                        auxLex += c;
                        addToken(Tipo.LLAVEIZQ, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    } else if (c.equals("}")) {
                        finconj = auxLex;
                        addToken(Tipo.IDENTIFICADOR, auxLex, fila, columna);
                        auxLex += c;
                        addToken(Tipo.LLAVEDER, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                        break;
                    } else if (c.equals(";")) {
                        auxLex += c;
                        addToken(Tipo.PUNTOYCOMA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                        break;
                    } else if (c.equals("\n")) {
                        columna = 0;
                        fila += 1;
                        estado = 1;
                        auxLex = "";
                        break;
                    } else if (c.equals("\t") || c.equals("\r")) {
                        fila += 1;
                        estado = 3;
                        auxLex = "";
                    } else if (c.equals("/")) {
                        auxLex += c;
                        addToken(Tipo.DIVISION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                    } else if (c.equals("<")) {
                        auxLex += c;
                        addToken(Tipo.MENOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                    } else if (c.equals("%")) {
                        auxLex += c;
                        addToken(Tipo.MENOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 4;
                        break;
                    } else {
                        auxLex += c;
                        addToken(Tipo.CADENA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 3;
                    }
                    break;
                case 4:
//********************************************************ESTE ESTADO SIRVE PARA LOS LEXEMAS******************************************
                    if (c.equals("%")) {
                        auxLex += c;
                        addToken(Tipo.MENOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 4;
                    } else if (c.equals(":")) {
                        addToken(Tipo.IDENTIFICADOR, auxLex, fila, columna);
                        auxLex += c;
                        addToken(Tipo.DOSPUNTOS, auxLex, fila, columna);
                        auxLex = "";
                        estado = 4;
                    } else if (Character.isLetter(d)) {
                        auxLex += c;
                        estado = 4;
                    } else if (Character.isDigit(d)) {
                        auxLex += c;
                        estado = 4;
                    } else if (c.equals("\"")) {
                        auxLex += c;
                        addToken(Tipo.COMILLA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 4;
                    } else if (c.equals(" ")) {
                        columna += 1;
                        estado = 4;
                    } else if (c.equals("\n")) {
                        columna = 0;
                        fila += 1;
                        estado = 4;
                        auxLex = "";
                        break;
                    } else if (c.equals("\t") || c.equals("\r")) {
                        fila += 1;
                        estado = 4;
                        auxLex = "";
                    } else if (c.equals("/")) {
                        auxLex += c;
                        addToken(Tipo.DIVISION, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                    } else if (c.equals("}")) {
                        auxLex += c;
                        addToken(Tipo.LLAVEDER, auxLex, fila, columna);
                        auxLex = "";
                        estado = 5;
                    }else if (c.equals("<")) {
                        auxLex += c;
                        addToken(Tipo.MENOR, auxLex, fila, columna);
                        auxLex = "";
                        estado = 1;
                    } else {
                        auxLex += c;
                        addToken(Tipo.CADENA, auxLex, fila, columna);
                        auxLex = "";
                        estado = 4;
                    }
                    break;
                case 5:
                    if (c.equals("\n")) {
                        columna = 0;
                        fila += 1;
                        estado = 5;
                        auxLex = "";
                        break;
                    } else if (c.equals("\t") || c.equals("\r")) {
                        fila += 1;
                        estado = 5;
                        auxLex = "";
                    } else {
                        auxLex += c;
                        addError(Tipo.DESCONOCIDO, auxLex, "Caracter no definido", fila, columna);
                        System.out.println("ERROR LEXICO5 CON: " + auxLex + " " + fila + "," + columna);
                        auxLex = "";
                        estado = 2;
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

    public static boolean esMinuscula(String s) {
        // Regresa el resultado de comparar la original con sun versión minúscula
        return s.equals(s.toLowerCase());
    }

    public static boolean esMayuscula(String s) {
        // Regresa el resultado de comparar la original con sun versión mayúscula
        return s.equals(s.toUpperCase());
    }

    public boolean buscarascii(char entrada) {
        LinkedList<Character> ascii = new LinkedList<Character>();
        for (int i = 32; i < 47; i++) {

        }
        for (int i = 58; i < 64; i++) {

        }
        for (int i = 91; i < 96; i++) {

        }
        for (int i = 123; i < 125; i++) {

        }
        return false;
    }
}
