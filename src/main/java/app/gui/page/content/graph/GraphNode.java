package app.gui.page.content.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import app.conf.Config;

/**
 * [Documentation ici !]
 * 
 * @author Achirafi Amal [ amalachirafi@gmail.com ] [ amal.achirafi@etu.u-paris.fr]
 */
public abstract class GraphNode extends JPanel {

//////////////////////////////////////////////////////////////////////
//#__________________________  Champs  ____________________________#//
//////////////////////////////////////////////////////////////////////

    /**
     * Compteur sur le nombre totale de noeuds qui ont été créé.
     *
     * Il permet de créer des noeuds sans étiquettes, en ne leur 
     * donnant le numero (unique car incrémental) comme nom par défaut.
     */
    private static long COUNTER = 1;

    /**
     * Il s'agit de l'etiquette du noeud.
     */
    private String name;

    /**
     * Il s'agit de la couleur du noeud dans l'interface graphique.
     *
     * Par défaut, cette couleur est noire.
     */
    private Color nodeColor = Config.graphNodeColor; 

//////////////////////////////////////////////////////////////////////
//#________________________  Constructeur  ________________________#//
//////////////////////////////////////////////////////////////////////

    public GraphNode( int x, int y, String name ){
        super();
        
        // Mise à jour du nom.
        this.name = name;
        
        // Récupération de la dimension des points de l'interface graphique.
        Dimension pointDim = Config.graphNodeDimension;
        this.setBounds(x, y, pointDim.width, pointDim.height );
        
    }   

    public GraphNode( int x, int y ){
        this( x, y, ""+(COUNTER++) ); 
    }   

    public GraphNode(){ this(10,10 ); }

//////////////////////////////////////////////////////////////////////
//#_________________________  Fonctions  __________________________#//
//////////////////////////////////////////////////////////////////////


    /**
     * Fonction qui va déssiner le noeud dans l'interface graphique.
     *
     * Cette fonction est mise en abstraite pour donner la possibilité de créer 
     * plusieurs noeuds qui se déssine différament.
     */
    public abstract void drawNode (Graphics g);

    @Override
    public void paintComponent( Graphics g ){
            
        super.paintComponent(g);

        // Appel à la fonction de dessin.
        this. drawNode(g);
        
    }
    
    /**
     * Fonction qui crée et renvoie des noeuds dont la fonction de dessin est la fonction par défaut.
     */
    public static GraphNode createDefaultNode( int x, int y, String name ){

        return new GraphNode(x, y, name) {
            @Override
            public void drawNode(Graphics g_){
                Graphics2D g = (Graphics2D)g_;

                // Changement de l'épaisseur du trait.
                g.setStroke(Config.graphNodeStroke);

                // Entier de décalage pour éviter de toucher le bord.
                int dec = (int) Config.graphNodeStroke.getLineWidth();

                // Changement de la couleur pour les contours.
                g.setColor( this.getColor() );
                // Ajout des contour
                g.drawOval(dec, dec, this.getWidth()-2*dec, this.getHeight()-2*dec);

                /* Affichage du nom du noeud. */
                // Récupération de la police d'écriture.
                Font font = Config.getFont(Config.nodeFont).deriveFont(Config.nodeFontSize);
                // Changement de la police d'écriture
                g.setFont(font);

                // calcul de la taillé nécessaire pour faire l'affichage.
                FontRenderContext frc = g.getFontRenderContext();
                Rectangle2D bounds = font.getStringBounds(this.getName(), frc);
                
                double xText = (getWidth()  - bounds.getWidth())  / 2;
                double yBase = (getHeight() - bounds.getHeight()) / 2 - bounds.getY(); // –bounds.y = ascentEffectif
    
                g.drawString(this.getName(), (float)xText, (float)yBase);
                /* g.setStroke(new BasicStroke(2)); */
                /* g.setColor(Color.RED); */
                /* g.setStroke(new BasicStroke(2)); */
                /* g.draw(new Rectangle2D.Double( */
                /*     xText + bounds.getX(),      // bounds.getX() vaut 0 la plupart du temps */
                /*     yBase + bounds.getY(),      // yBase - ascentEffectif */
                /*     bounds.getWidth(), */
                /*     bounds.getHeight() */
                /* )); */

            }
        };

    }

    /**
     * Sucharge de la fonction {@link GraphNode#createDefaultNode(int, int, String)}.
     */
    public static GraphNode createDefaultNode( int x, int y ){ return createDefaultNode(x, y, ""+(COUNTER++)); }

    /**
     * Sucharge de la fonction {@link GraphNode#createDefaultNode(int, int)}.
     */
    public static GraphNode createDefaultNode( ){ return createDefaultNode(10, 10); }

    @Override
    public boolean contains(int x, int y ){
        return 
            (x >= this.getX() && x<=this.getX()+this.getWidth())
            && (y >= this.getY() && y<=this.getY()+this.getHeight());
    }

//////////////////////////////////////////////////
//#________________  Accesseurs  ______________#//
//////////////////////////////////////////////////

    /**
     * Fonction qui change l'étiquette du noeud.
     *
     * @param newName Le nouvelle étiquette du noeud.
     */
    public void setName( String newName ){
        this.name = newName;
    }
    
    /**
     * Fonction qui renvoie l'étiquette actuelle du noeud.
     *
     * @return La valeur de l'étiquette du noeud.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Fonction qui renvoie la couleur du noeud.
     */
    public Color getColor(){
        return this.nodeColor;
    }

//////////////////////////////////////////////////
//#________________   Override   ______________#//
//////////////////////////////////////////////////

    

//////////////////////////////////////////////////
//#________________  Abstraites  ______________#//
//////////////////////////////////////////////////

}
