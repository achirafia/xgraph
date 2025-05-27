package app.gui;

import javax.swing.JFrame;

import app.conf.Config;
import app.gui.page.content.graph.Graph;

/**
 * Classe principale de l'application.
 *
 * @author Achirafi Amal [ amalachirafi@gmail.com ] [ amal.achirafi@etu.u-paris.fr]
 */
public class Main {

//////////////////////////////////////////////////////////////////////
//#__________________________  Champs  ____________________________#//
//////////////////////////////////////////////////////////////////////

    
//////////////////////////////////////////////////////////////////////
//#_________________________  Fonctions  __________________________#//
//////////////////////////////////////////////////////////////////////
    
    public static void main (String [] args){

        // Début de l'initialisation de la configuration.
        Config.initConfig();
        
        // Initialisation de la fenêtre.
        Config.window = new JFrame("XGraph");

        // Mise à jour de la taille de la fenêtre.
        Config.window.setSize(Config.windowDimension);   

        // Ajout du panneau principale.
        Config.window.setContentPane(new Graph()); 

        // Fermeture du programme au moment la fermeture de la GUI.
        Config.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Affichage de la fenêtre.
        Config.window.setVisible(true);

    }



//////////////////////////////////////////////////
//#________________  Accesseurs  ______________#//
//////////////////////////////////////////////////

    

//////////////////////////////////////////////////
//#________________   Override   ______________#//
//////////////////////////////////////////////////

    

//////////////////////////////////////////////////
//#________________  Abstraites  ______________#//
//////////////////////////////////////////////////

}
