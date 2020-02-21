/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpractica1;

import Interfaz.VPrincipal;
import Listas.ArboldeExpresiones;
import Listas.DatoExpresion;
import Listas.Nodo;
import java.util.LinkedList;
import javax.swing.JFrame;
import org.jvnet.substance.SubstanceLookAndFeel;
/**
 *
 * @author Andrea Palomo
 */
public class TPractica1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame.setDefaultLookAndFeelDecorated(true);
        SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.OfficeSilver2007Skin");
         SubstanceLookAndFeel.setCurrentTheme("org.jvnet.substance.theme.SubstancePurpleTheme");
         SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBubblesWatermark");
        VPrincipal ventana= new VPrincipal();
        ventana.setVisible(true);
        
        
        //. {digito} * | "_" | {letra} {digito}
        
        
        /*LinkedList<DatoExpresion> con = new LinkedList<>();
        con.add(new DatoExpresion(".", DatoExpresion.TipoExpresion.OPERADOR, "punto"+1));
        con.add(new DatoExpresion("digito", DatoExpresion.TipoExpresion.CONJUNTOS,"null1"));
        con.add(new DatoExpresion("*", DatoExpresion.TipoExpresion.OPERADOR,"multi"+0));
        con.add(new DatoExpresion("|", DatoExpresion.TipoExpresion.OPERADOR,"inte"+0));
        con.add(new DatoExpresion("_", DatoExpresion.TipoExpresion.CADENA,"cadena_"));
        con.add(new DatoExpresion("|", DatoExpresion.TipoExpresion.OPERADOR,"inte"+1));
        con.add(new DatoExpresion("letra", DatoExpresion.TipoExpresion.CONJUNTOS,"null2"));
        con.add(new DatoExpresion("digito", DatoExpresion.TipoExpresion.CONJUNTOS,"null3"));

        ArboldeExpresiones arbolito = new ArboldeExpresiones();

        Nodo raiz = arbolito.construirArbol(con);
        System.out.println("raiz   "+raiz.getInformacion());
        System.out.println("El arbol es ");
        arbolito.imprime(raiz);
        //System.out.println(arbolito.tamano());
        System.out.println("");
        System.out.println(arbolito.CrearArchivoDot());*/
    }

}
