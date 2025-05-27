package app.conf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

/**
 * La classe {@code Config} centralise toutes les configurations globales de l'application.
 * 
 * <p>Elle fournit des constantes (par exemple, taille de la fenêtre), des paramètres modifiables
 * à l'exécution (par exemple, l'algorithme sélectionné, le type de graphe), ainsi que des méthodes
 * utilitaires pour initialiser ou modifier ces paramètres.</p>
 *
 * @author Achirafi Amal [ amalachirafi@gmail.com ] [ amal.achirafi@etu.u-paris.fr]
 */
public class Config {

//////////////////////////////////////////////////////////////////////
//#__________________________  Champs  ____________________________#//
//////////////////////////////////////////////////////////////////////

    
    /**
     * Il s'agit de la fenêtre principale de l'application. 
     */
    public static JFrame window;
    
    /**
     * Il s'agit de la dimension par défaut de l'interface graphique.
     */
    public static Dimension windowDimension = new Dimension( 800, 600 );

    /**
     * Il s'agit de la dimension par défaut des noeuds dans l'interface graphique..
     */
    public static Dimension graphNodeDimension = new Dimension( 50, 50 );

    /**
     * Il s'agit de couleur par défaut des noeuds de l'interface graphique.
     */
    public static Color graphNodeColor = Color.BLACK;

    /**
     * Il s'agit de l'épaisseur du trait par défaut lors du dessin des noeuds.
     */
    public static int graphNodeStroke = 5;
    
    /**
     * Table de hachage utilisée pour stocker les différentes police d'écriture du projet.
     */
    private static HashMap<String, Font> fontMap = new HashMap<>();

    /**
     * Il s'agit du chemin vers les polices d'écritures.
     */
    public static String fontPath = "src/main/java/res/font";

//////////////////////////////////////////////////////////////////////
//#_________________________  Fonctions  __________________________#//
//////////////////////////////////////////////////////////////////////


    /**
     * Fonction qui va récupérer et importer toutes les polices d'écritures 
     * qui seront utilisées dans le projet.
     */
    private static void Config_loadFonts(){
    
        // Ouverture du répertoire des polices.
        File fontDir = new File(fontPath);
        if (!fontDir.exists() || !fontDir.isDirectory()){
            System.out.println( "Le chemin vers le repértoire des polices n'est pas valide." );
            return;
        }

        // Parcours de la liste des fichiers.
        for (File f : fontDir.listFiles()){
            
            try{
                // Création de la police d'écriture en mémoire.
                Font font = Font.createFont(Font.TRUETYPE_FONT, f);
                System.out.println(String.format("La police \033[1;33m`%s`\033[0m a été ajoutée.", font.getName()));
                // Ajout de la police dans la table de hachage.
                fontMap.put(font.getName(), font);
            }
            catch( IOException | FontFormatException e ){
                if (f.getName().equals("LICENCE.txt")) continue; // Le fichier de licence est connu.
                System.out.println("\033[1;31mAvertissement\033[0m : Le fichier \033[1;31m`"+f.getName()+"`\033[0m n'est pas une police valide.");
                continue;
            }

        }        

    }

    /**
     * Fonction qui initialise la configuration.
     */
    public static void initConfig(){
    
       // Chargement des polices de couleurs.
       Config_loadFonts();

    }    

    /**
     * Fonction qui récupère une police d'écriture dont le nom est connu.
     *
     * @param name La police d'écriture dont on cherche le nom.
     * @return La police d'écriture ou la police d'écriutre par défaut si 
     * celle recherchée n'existe pas.
     */
    public static Font getFont( String name ){
            
        // Récupération de la police d'écriture.
        Font f = fontMap.get(name);
        if (f == null)
            return new Font("Serif", Font.BOLD, 14);
        
        return f;

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
