package app.gui.page.content.graph;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * [Documentation ici !]
 * 
 * @author Achirafi Amal [ amalachirafi@gmail.com ] [ amal.achirafi@etu.u-paris.fr]
 */
public class Graph extends JPanel {

//////////////////////////////////////////////////////////////////////
//#__________________________  Champs  ____________________________#//
//////////////////////////////////////////////////////////////////////

    /**
     * Il s'agit d'un gestionnaire d'évènement qui sera ajouté à chaque noeud qui 
     * sera inséré dans le graphe et va appeler des fonctions du graphe pour agir 
     * en conséquence.
     */
    private MouseAdapter nodeMouseEventListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            // Désséléction de tous les autres noeuds (sauf en cas de CTRL)
            Graph.this.unselecComponents();
            // Selection du noeud.    
            Graph.this.selectNode((GraphNode)e.getSource());
        }
    };

    /**
     * Il s'agit de la liste des noeud qui sont actuellement présent dans le graphe.
     */
    private ArrayList<GraphNode> nodes = new ArrayList<>();

    /**
     * Il s'agit de la liste des noeud qui sont séléctionnés dans l'interface graphique.
     */
    private ArrayList<GraphNode> selectedNodes = new ArrayList<>();

    /**
     * Il s'agit de la liste des touches actuellement préssées 
     * par l'utilisateur (touches du clavier).
     */
    private ArrayList<Integer> keyPressed = new ArrayList<>();

//////////////////////////////////////////////////////////////////////
//#________________________  Constructeur  ________________________#//
//////////////////////////////////////////////////////////////////////


    /**
     * Fonction qui ajoute un noeud dans l'interface la liste des 
     * noeuds ainsi que dans l'interface graphique.
     */
    private void addNode(GraphNode node){
        
        // Ajout d'un gestionnaire d'évènement pour le noeud.
        node.addMouseListener(this.nodeMouseEventListener);

        // Ajout du noeud dans l'interface graphique ainsi que dans la liste des noeuds du graphe.
        Graph.this.add(node);
        Graph.this.nodes.addLast(node);
        Graph.this.repaint(); // Mise à jour de l'interface.

    }

    /**
     * Fonction qui va déséléctionner tous les composants qui sont 
     * actuellement séléctionnés dans l'interface graphique.
     */
    private void unselecComponents(  ){
            
        // Déséléction des noeuds.
        selectedNodes.stream().forEach(node -> node.setSelected(false));
        selectedNodes.clear();
    
    }

    /**
     * Fonction qui va séléctionner un noeud et va l'ajouter dans la liste des 
     * noeuds séléctionnés.
     */
    private void selectNode( GraphNode s ){
        
        // Mise à jour de l'état de séléction du noeud;
        s.setSelected(true);
        // Ajout du noeud dans la liste des séléctions.
        this.selectedNodes.add(s);
    
      }

    /**
     * Fonction qui ajoute des gestionnaire d'évènement pour la 
     * souris dans le panneau.
     */
    private void addMListeners(){
    
        this.addMouseListener( new MouseAdapter() {
            long lastTime = 0; ///< Temps écoulé depuis le dernier clique (permet de détecter un double clique. )
            @Override 
            public void mouseClicked( MouseEvent e ){
                if (System.currentTimeMillis() - lastTime <300){ // Double clique
                    lastTime = System.currentTimeMillis(); // Mise à jour du temps/
                    // Ajout d'un nouveau noeud dans l'interface graphique.
                    GraphNode node = GraphNode.createDefaultNode(e.getX(), e.getY());
                    // Ajout du noeud dans l'interface graphique ainsi que dans la liste des noeuds du graphe.
                    Graph.this.addNode(node);
                }

                lastTime = System.currentTimeMillis(); // Mise à jour du temps/
                // On déselectionne tous les éléments de l'interface graphique.
                unselecComponents(); 
                
            }
        } );

    }

    /**
     * Fonction qui ajoute des gestionnaire d'évènement pour le
     * clavier dans le panneau.
     */
    private void addKListeners(){
    
        this.addKeyListener( new KeyAdapter() {
            @Override 
            public void keyPressed( KeyEvent e ){
                Graph.this.keyPressed.addLast(e.getID());
                System.out.println("Touche pressée \033[1;35m`"+e.getID()+"`\033[0m");
            }
        } );

    }

    /**
     * Fonction qui initialise le panneau.
     */
    private void init(){
        
        // suppression de la mise en forme.
        this.setLayout(null);
        
        // Ajout des gestionnaire d'évènement pour la souris.
        this.addMListeners();

        // Ajout des gestionnaire d'évènement pour le clavier.
        this.addKListeners();

            
    }

        
    public Graph (){
        super();
        
        // Mise en place du panneau.
        this.init();

    }    

//////////////////////////////////////////////////////////////////////
//#_________________________  Fonctions  __________________________#//
//////////////////////////////////////////////////////////////////////

       

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
