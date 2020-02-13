/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listas;

import java.util.LinkedList;

/**
 *
 * @author Andrea Palomo
 */
public class ArboldeExpresiones {

    LinkedList< Nodo> pOperandos = new LinkedList<Nodo>();
    LinkedList<String> pOperadores = new LinkedList<String>();
    String operadores;

    public void ArbodeExpresiones() {

    }
    private Nodo raiz;

    public boolean contruir(LinkedList<String> con) {
        construirArbol(con);
        return true;
    }

    public Nodo construirArbol(LinkedList<String> Pila) {
        for (String objetoexpresion : Pila) {
            System.out.println(">>>>>>>>>>>>>>>   " + objetoexpresion);
            
        }
        return null;
    }
}
