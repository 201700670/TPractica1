/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

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
    private Integer fila,columna;
    
    
    public LinkedList<Token> escanear(String entrada){
        
        
        if(errores.size()>0){
            return errores;
        }else{
            return salida;
        }
    }
    
    public void addToken(){
        
    }
}
