/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listas;

import Analizador.AnalizadorTexto;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Andrea Palomo
 */
public class ArboldeExpresiones {

    LinkedList< Nodo> Operandos = new LinkedList<Nodo>();
    LinkedList<String> Operadores = new LinkedList<String>();
    String operadores, idgrafica="";
    AnalizadorTexto lex = new AnalizadorTexto();
    public static int conta;
    int arbol = 0;
    String datoanterior = "";
    String Resultado="";
    public void ArbodeExpresiones() {

    }
    private Nodo raiz;

    public boolean EstaVacio() {
        if (raiz == null) {
            return true;
        }
        return false;
    }

    public boolean contruir(LinkedList<DatoExpresion> con) {
        construirArbol(con);
        return true;
    }

    public Nodo construirArbol(LinkedList<DatoExpresion> Pila) {
        Pila.addFirst(new DatoExpresion("#", DatoExpresion.TipoExpresion.CADENA, "aceptacion"));
        Pila.addFirst(new DatoExpresion(".", DatoExpresion.TipoExpresion.OPERADOR,"punto0"));
        for (int i = Pila.size() - 1; i >= 0; i--) {
            //System.out.println(Pila.get(i).lexema);
            if (Pila.get(i).lexema.equals(".") && (Pila.get(i).tipo == DatoExpresion.TipoExpresion.OPERADOR)) {
                //entonces aca se puede guardar el sub arbol de lo que debe tener las dos hojas
                idgrafica=Pila.get(i).idgrafica;
                Operadores.add(Pila.get(i).lexema);
                guardarSubArbol();
                Pila.remove(i);
                raiz = (Nodo) Operandos.peek();

            } else if (Pila.get(i).lexema.equals("?") && (Pila.get(i).tipo == DatoExpresion.TipoExpresion.OPERADOR)) {
                // este es de subarbol un nodo
                idgrafica=Pila.get(i).idgrafica;
                Operadores.add(Pila.get(i).lexema);
                guardarSubArbolunahoja();
                Pila.remove(i);
                raiz = (Nodo) Operandos.peek();
            } else if (Pila.get(i).lexema.equals("*") && (Pila.get(i).tipo == DatoExpresion.TipoExpresion.OPERADOR)) {
                // este es de subarbol un nodo
                idgrafica=Pila.get(i).idgrafica;
                Operadores.add(Pila.get(i).lexema);
                guardarSubArbolunahoja();
                Pila.remove(i);
                raiz = (Nodo) Operandos.peek();
            } else if (Pila.get(i).lexema.equals("+") && (Pila.get(i).tipo == DatoExpresion.TipoExpresion.OPERADOR)) {
                // este es de subarbol un nodo
                idgrafica=Pila.get(i).idgrafica;
                Operadores.add(Pila.get(i).lexema);
                guardarSubArbolunahoja();
                Pila.remove(i);
                raiz = (Nodo) Operandos.peek();
            } else if (Pila.get(i).lexema.equals("|") && (Pila.get(i).tipo == DatoExpresion.TipoExpresion.OPERADOR)) {
                //entonces aca se puede guardar el sub arbol de lo que debe tener las dos hojas
                idgrafica=Pila.get(i).idgrafica;
                Operadores.add(Pila.get(i).lexema);
                guardarSubArbol();
                Pila.remove(i);
                raiz = (Nodo) Operandos.peek();
            } else if (Pila.get(i).lexema.equals("#") && (Pila.get(i).tipo == DatoExpresion.TipoExpresion.OPERADOR)) {
                Operandos.addFirst(new Nodo(Pila.get(i).getLexema(),Pila.get(i).idgrafica));
            } else {
                boolean bandera = false;
                if (lex.L_Conjuntos != null) {
                    for (Conjuntos conjunto : lex.L_Conjuntos) {
                        if (Pila.get(i).equals(conjunto.identificador)) {
                            bandera = true;
                        }
                    }
                }
                if (lex.CadenasExpresion != null) {
                    for (String cadenas : lex.CadenasExpresion) {
                        if (Pila.get(i).equals(cadenas)) {
                            bandera = true;
                        }
                    }
                }
                if (true) {
                    //es operando conjuntos (nombre)
                    Operandos.addFirst(new Nodo(Pila.get(i).getLexema(), Pila.get(i).idgrafica));
                    Pila.remove(i);
                } else {
                    // NO EXISTE LA CADENA NO SE PUEDE RECONOCER LA EXPRESION REGULAR
                    System.out.println("ERROR EN EL PUSH Y POP DEL ARBOL");
                }
            }
        }
        return raiz;
    }

    private void guardarSubArbol() {
        Nodo op2 = (Nodo) Operandos.pop();
        Nodo op1 = (Nodo) Operandos.pop();
        Operandos.push(new Nodo(op2, Operadores.pop(), idgrafica, op1));

    }

    private void guardarSubArbolunahoja() {
        Nodo op2 = (Nodo) Operandos.pop();
        //Nodo op1 = (Nodo) Operandos.pop();
        Operandos.push(new Nodo(op2, Operadores.pop(), idgrafica, null));
    }

    public void imprime(Nodo n) {
        /*if (n != null) {
            conta++;
            imprime(n.getNodoDerecho());
            System.out.println(n.getInformacion() + " ");
            imprime(n.getNodoIzquierdo());
        }*/
 /*if (n != null) {
            if (arbol == 0) {
                datoanterior= String.valueOf(arbol);
                arbol++;
            }
            else if (arbol == 1 && datoanterior=="") {
                System.out.println(datoanterior + "->" +arbol);
                datoanterior=String.valueOf(arbol);
                arbol++;
            }else{
                System.out.println(datoanterior + "->"+arbol);
                datoanterior=String.valueOf(arbol);
                arbol++;
            }

        }*/
        if (n != null) {

            imprime(n.getNodoDerecho());
            System.out.print("[" + n.getInformacion() + "]->" );
            conta++;
            imprime(n.getNodoIzquierdo());

        }
    }

    public int tamano() {
        System.out.println("TAMANO  " + conta);
        return conta;
    }

    public String grafica(Nodo n) {
        String cadena = "";
        while (n != null) {
            System.out.println(n.getNodoDerecho());
            System.out.println(n.getNodoIzquierdo());
        }
        return cadena;
    }

   
    
    private String getCodigoInterno(Nodo n) {
        String etiqueta="";
        if(n.getNodoIzquierdo()==null ){
            etiqueta="nodo"+ n.getIdgrafica()+" [ label =\""+ n.getInformacion()+"\"];\n";
        }else if( n.getNodoDerecho()==null){
            etiqueta="nodo"+n.getIdgrafica()+" [ label =\"<C0>|"+n.getInformacion()+"|<C1>\"];\n";
        }
        if(n.getNodoIzquierdo()!=null){
            etiqueta+="nodo"+ n.getNodoIzquierdo().getIdgrafica()+" [ label =\""+ n.getNodoIzquierdo().getInformacion()+"\"];\n";
            etiqueta= etiqueta + getCodigoInterno(n.getNodoIzquierdo()) +
               "nodo"+n.getIdgrafica()+":C0->nodo"+n.getNodoIzquierdo().getIdgrafica()+"\n";
        }
        if(n.getNodoDerecho()!=null){
            etiqueta+="nodo"+ n.getNodoDerecho().getIdgrafica()+" [ label =\""+ n.getNodoDerecho().getInformacion()+"\"];\n";
            etiqueta=etiqueta + getCodigoInterno(n.getNodoDerecho()) +
               "nodo"+n.getIdgrafica()+":C1->nodo"+n.getNodoDerecho().getIdgrafica()+"\n";                    
        }
        return etiqueta;
    }        

    public String CrearArchivoDot() {
        String cadena="";
        if (!EstaVacio()) // Si no está vacío ...
        {
            
            cadena="digraph grafica{\n" +
               "rankdir=TB;\n" +
               "node [shape = circle, width=0.5,fixedsize=\"true\", style=filled, fillcolor=	aquamarine];\n"+
                    "nodo"+ raiz.getIdgrafica()+" [ label =\""+ raiz.getInformacion()+"\"];\n"+
                getCodigoInterno(raiz)+
                "}\n";
        }
        return cadena;
    }


    /*public static String grafica(Nodo node, int level) {
        String nodocad = "";
        String nodito = "";
        int prueba = 0;
        int nivel1 = 0;
        int nivel2 = 0;
        int nivel3 = 0;
        nodocad += "nodo" + conta + "[ label =\"";
        if (node == null) {
            return "";
        }
        System.out.print("Level : " + level + " " + "Data : ");
        for (int i = 0; i < node.tamano; i++) {
            System.out.print(node.data[i].clave + " " + node.data[i].dia);
            nodocad += "<C" + i + ">|" + node.data[i].clave + "|";
            nivel1 = i;
            nivel2 = i;
            nivel3 = i;
        }
        if (level == 1) {
            nodocad += "<C" + (nivel1 + 1) + "> \" ];\n";
            //System.out.println((nivel1+1)/2);
            int cero = 0;
        }

        if (level == 2) {
            nodocad += "<C" + (nivel2 + 1) + "> \" ];\n";

            nodocad += "nodo" + 0 + ":C" + (contac) + " -> " + "nodo" + conta + "\n";
            //System.out.println(nodocad);
            //System.out.println(r.data[i]);
            contac++;
            numero = conta;

        }
        if (level == 3) {
            nodocad += "<C" + (nivel3 + 1) + "> \" ];\n";

            //System.out.println(numero+"              holis");
            //System.out.println("nodo" + numero + ":C" + lvl3 + " -> " + "nodo" + conta+ "\n");
            nodocad += "nodo" + numero + ":C" + prueba + " -> " + "nodo" + conta + "\n";
            prueba++;
        }
        //System.out.println(nodocad);
        System.out.println();
        conta++;
        for (int i = 0; i < node.tamano + 1; i++) {

            nodocad += grafica(node.hijos[i], level + 1);
        }
        lvl3 = 0;
        return nodocad;
    }

    public static void Creartxt(NodoB node, int level) {

        try {
            String ruta = "C:/Users/Andrea Palomo/Dropbox/Estructuras/[EDD]PROYECTO2_201700670/codigobtree.txt";

            File files = new File(ruta);
            if (!files.exists()) {
                files.createNewFile();
            }

            FileWriter fw = new FileWriter(files);
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("");
            bw.write("digraph grafica{\n");
            bw.newLine();
            bw.write("rankdir=TB;\n");
            bw.newLine();
            bw.write("node [shape = record, style=filled, fillcolor=deeppink];");
            bw.newLine();

            bw.write(grafica(node, level));
            //bw.write(noditos(node, level));
            bw.write("}");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void generar() {
        try {

//path del dot.exe,por lo general es la misma, pero depende de donde hayas instalado el paquete de Graphviz
            String dotPath = "C:/Program Files (x86)/Graphviz 2.28/bin/dot.exe";

//path del archivo creado con el codigo del graphviz que queremos
            String fileInputPath = "C:/Users/Andrea Palomo/Dropbox/Estructuras/[EDD]PROYECTO2_201700670/codigobtree.txt";

//path de salida del grafo, es decir el path de la imagen que vamos a crear con graphviz
            String fileOutputPath = "C:/Users/Andrea Palomo/Dropbox/Estructuras/[EDD]PROYECTO2_201700670/grafobtree.jpg";
            // tipo de imagen de salida, en este caso es jpg

            String tParam = "-Tjpg";

            String tOParam = "-o";

//concatenamos nuestras direcciones. Lo que hice es crear un vector, para poder editar las direcciones de entrada y salida, usando las variables antes inicializadas
//recordemos el comando en la consola de windows: C:\Archivos de programa\Graphviz 2.21\bin\dot.exe -Tjpg grafo1.txt -o grafo1.jpg Esto es lo que concatenamos en el vector siguiente:
            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

//Invocamos nuestra clase 
            Runtime rt = Runtime.getRuntime();

//Ahora ejecutamos como lo hacemos en consola
            rt.exec(cmd);
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("C:/Users/Andrea Palomo/Dropbox/Estructuras/[EDD]PROYECTO2_201700670/grafobtree.jpg");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }*/
}
