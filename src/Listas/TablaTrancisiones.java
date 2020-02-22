/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listas;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Andrea Palomo
 */
public class TablaTrancisiones {

    String NombreEstado;
    public Nodo n;
    LinkedList<Estados> tempi = new LinkedList<>();
    private LinkedList<Integer> Pila = new LinkedList<>();
    public LinkedList<OrdenarTabla> ordenartab = new LinkedList<>();
    int contador;

    public TablaTrancisiones(LinkedList<Estados> e, Nodo n) {
        this.n = n;
        HashMap<String, Integer> tabla = new HashMap<>();

        for (int i = 0; i < e.size(); i++) {
            //System.out.println(e.get(i).id + "  " + e.get(i).lexema + "  " + e.get(i).Siguientes);
            tabla.put(e.get(i).Siguientes, e.get(i).id);
        }
        Set<Map.Entry<String, Integer>> set = tabla.entrySet();
        generarEstados(set, e);
    }

    public void generarEstados(Set<Map.Entry<String, Integer>> set, LinkedList<Estados> state) {
        int contador = set.size() - 1;
        for (Entry<String, Integer> e : set) {
            // System.out.println("ESTADOS    " + e.getKey() + "  " + e.getValue());
            Pila.add(e.getValue());
        }
        Collections.sort(Pila);
        System.out.println(Pila);
        for (Integer i : Pila) {
            for (Entry<String, Integer> e : set) {
                if (i == e.getValue()) {
                    // System.out.println( e.getValue()+"  valor   "+ e.getKey());
                    for (int j = 0; j < state.size(); j++) {
                        if (e.getValue() == state.get(j).id) {
                            //System.out.println(state.get(j).id + "  " + state.get(j).lexema + "  " + state.get(j).Siguientes);

                            ordenartab.add(new OrdenarTabla(contador, state.get(j).Siguientes));
                            contador--;
                        }
                    }
                }
            }
        }
        generaArray(ordenartab, state);
    }

    public String generaArray(LinkedList<OrdenarTabla> e, LinkedList<Estados> state) {
        String cadena = "";
        Object[][] tabes = new Object[e.size() + 1][state.size()];
        System.out.println("tama;o   filas  " + e.size() + "  tama;o columnas   " + state.size());
        //este es para las columnas
        tabes[0][0] = "Estado";
        for (int j = 1; j < state.size(); j++) {
            tabes[0][j] = state.get(j).lexema;
            System.out.println(state.get(j).id + "  " + state.get(j).lexema + "  " + state.get(j).Siguientes + " ");

        }
        for (int j = 0; j < e.size(); j++) {
            tabes[j+1][0] = "S" + e.get(j).estado + " = {" + e.get(j).Siguientes + "}";
            System.out.println(j+" , "+0+"    Estados sin repetirse  " + e.get(j).estado + "  " + e.get(j).Siguientes);

        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        String[] inicial = n.getPrimeros().split(",");
        System.out.println("S" + (e.size() - 2) + "  " + n.getPrimeros());
        tempi.add(new Estados("S" + (e.size() - 2), n.getInformacion(), n.getPrimeros(), ""));
        contador = e.size() - 2;

        for (int u = 0; u < inicial.length; u++) {
            for (int j = 1; j < state.size(); j++) {
                //tabes[0][j] = state.get(j).lexema;
                //System.out.println(inicial[u] + "    " + state.get(j).id);
                if (inicial[u].equals(String.valueOf(state.get(j).id))) {
                    contador--;
                    tempi.add(new Estados("S" + contador, state.get(j).lexema, state.get(j).Siguientes, n.getPrimeros()));
                    NuevoEstado(new Estados("S" + contador, state.get(j).lexema, state.get(j).Siguientes, n.getPrimeros()), state);
                }

            }

        }
        contador = e.size() - 2;
        System.out.println("***************************************************************************************************");
        for (Estados ee : tempi) {
            if (!ee.SnumeString.equals("S-1") && !ee.SnumeString.equals("S-2") && !ee.SnumeString.equals("S-3") && !ee.SnumeString.equals("S-4")) {
                System.out.println(ee.SnumeString + " -> " + ee.lexema + "  ->  " + ee.Siguientes + "  ->  " + ee.perten);
                for (int i = 1; i < e.size(); i++) {
                    //tabes[0][j] = state.get(j).lexema;
                    
                    for (int j = 0; j < state.size(); j++) {
                        
                        
                        if(e.get(i).Siguientes.equals(ee.Siguientes)){
                            if(state.get(j).lexema.equals(ee.lexema)&& !ee.perten.equals("")){
                                //System.out.println("filas   " + (i+1) + " columnas  " + (j)+"   "+ee.SnumeString);
                                tabes[i+1][j] = ee.SnumeString;
                            }
                            else{
                                 System.out.println("filas   " + (i+1) + " columnas  " + (j)+"   "+"nada");
                                 tabes[i+1][j] = "";
                            }
                        }
                        
                    }
                }
            }
        }
        
        for (int i = 0; i < e.size(); i++) {
                    //tabes[0][j] = state.get(j).lexema;
                    for (int j = 0; j < state.size(); j++) {
                        if(tabes[i][j]==null){
                            tabes[i][j]="";
                        }
                        System.out.println("DATO   "+tabes[i][j]);
                    }
        }
        //new Imprimirtrancisiones(tabes, e.size(), state.size());
        return cadena;
    }

    public void NuevoEstado(Estados nuevo, LinkedList<Estados> state) {
        System.out.println(nuevo.SnumeString + " estado y  " + nuevo.lexema + " Siguientes de " + nuevo.Siguientes);
        String[] inicial = nuevo.Siguientes.split(",");
        if (inicial.length > 0) {
            for (int i = 0; i < inicial.length; i++) {
                for (int j = 1; j < state.size(); j++) {
                    //tabes[0][j] = state.get(j).lexema;
                    if (inicial[i].equals(String.valueOf(state.get(j).id))) {
                        System.out.println(" id  " + state.get(j).id + "  =  " + state.get(j).Siguientes + "   " + state.get(j).lexema);
                        if (contador > 0) {
                            System.out.println("hola2 ");
                            contador--;
                            tempi.add(new Estados("S" + contador, state.get(j).lexema, state.get(j).Siguientes, state.get(j).Siguientes));
                            NuevoEstado(new Estados("S" + contador, state.get(j).lexema, state.get(j).Siguientes, state.get(j).Siguientes), state);
                        } else {
                            System.out.println("hola1111");
                            Estados temporalito = null;
                            for (Estados e : tempi) {
                                //System.out.println( state.get(j).Siguientes+"   ????   "+ e.Siguientes);
                                if (state.get(j).Siguientes.equals(e.Siguientes)) {
                                    if (!state.get(j).lexema.equals(e.lexema)) {
                                        temporalito = new Estados(e.SnumeString, state.get(j).lexema, state.get(j).Siguientes, e.Siguientes);
                                    }
                                }
                            }
                            if (temporalito != null) {
                                tempi.add(temporalito);
                            }
                        }
                    }

                }
            }

        }

    }

}
