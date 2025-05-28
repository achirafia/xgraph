package app.gui.page.content.graph;

import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

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
        public void mousePressed(MouseEvent e){
            
            // Récupération du noeud source 
            GraphNode source = (GraphNode) e.getSource();

            // Si la touche CTRL est maintenue on ne retire rien
            if (Graph.this.keyPressed.contains(32))
                return;

            // Désséléction de tous les autres noeuds (sauf en cas de CTRL)
            Graph.this.unselectComponents();
            // Selection du noeud s'il n'est pas séléctionné.

            if (!Graph.this.selectedNodes.contains(source)) {
                Graph.this.selectNode(source);
                System.out.println("Ajouté !");
            }
            else { unselectNode(source); System.out.println("Retiré !"); }

            Graph.this.repaint();
        }
    };

    /**
     * Il s'agit de la liste des noeud qui sont actuellement présent dans le graphe.
     */
    private ArrayList<GraphNode> nodes = new ArrayList<>();

    /**
     * Il s'agit de la liste des noeud qui sont séléctionnés dans l'interface graphique.
     */
    private CopyOnWriteArrayList<GraphNode> selectedNodes = new CopyOnWriteArrayList<>();

    /**
     * Il s'agit de la liste des touches actuellement préssées 
     * par l'utilisateur (touches du clavier).
     */
    private ArrayList<Integer> keyPressed = new ArrayList<>();

//////////////////////////////////////////////////////////////////////
//#________________________  Constructeur  ________________________#//
//////////////////////////////////////////////////////////////////////


    public Graph (){
        super();
        
        // Mise en place du panneau.
        this.init();

    }    

//////////////////////////////////////////////////////////////////////
//#_________________________  Fonctions  __________________________#//
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
    private void unselectComponents(  ){
        
        // Si la touche CTRL est maintenu on ne retire rien
        if (this.keyPressed.size() == 1 && this.keyPressed.getFirst() == 17)
            return;

        // Déséléction des noeuds.
        selectedNodes.stream().forEach(node -> this.unselectNode(node));
        assert(this.selectedNodes.isEmpty()); 

    }
    
    /**
     * Fonction qui va déséléctionner le noeud donné en argument.
     *
     * @param n Le noeud à déséléctionner.
     */
    private void unselectNode( GraphNode n ){
        
        // On s'assure que le noeuds est bel et bien dans la liste.
        assert (this.selectedNodes.contains(n));
        // Déséléction;
        n.setSelected(false);
        assert(this.selectedNodes.remove(n));
        // On s'assure que le noeuds n'est plus dans la liste.
        assert (!this.selectedNodes.contains(n));
    
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
                
                // Si la touche ESPACE a été saisie, alors on ne fait rien tout.
                if (Graph.this.keyPressed.contains(32))
                    return;

                if (System.currentTimeMillis() - lastTime < 250){ // Double clique
                    lastTime = System.currentTimeMillis(); // Mise à jour du temps/
                    // Ajout d'un nouveau noeud dans l'interface graphique.
                    GraphNode node = GraphNode.createDefaultNode(e.getX(), e.getY());
                    // Ajout du noeud dans l'interface graphique ainsi que dans la liste des noeuds du graphe.
                    Graph.this.addNode(node);
                }

                lastTime = System.currentTimeMillis(); // Mise à jour du temps/
                // On déselectionne tous les éléments de l'interface graphique.
                unselectComponents(); 
                Graph.this.repaint();
                
            }
        } );

    }

    /**
     * Fonction qui ajoute des gestionnaire d'évènement pour le
     * clavier dans le panneau.
     */
    private void addKListeners(){
        
        // Activation du focus du clavier sur le panneau.
        this.setFocusable(true);     
        // demande le focus
        this.requestFocusInWindow(); 

        this.addKeyListener( new KeyAdapter() {
            @Override 
            public void keyPressed( KeyEvent e ){
                // 17 Ctrl
                // 32 Espace
                
                // Ajout de la touche si elle n'existe pas.
                if (!Graph.this.keyPressed.contains(e.getKeyCode())) Graph.this.keyPressed.add(e.getKeyCode());

                // Changement du cuseur s'il s'agit de la touche espace.
                if (e.getKeyCode() == 32)
                    Graph.this.setCursor(new Cursor(Cursor.HAND_CURSOR));

                if (e.getKeyCode() == 80)
                    Graph.this.keyPressed.stream().forEach(i-> System.out.println("Touche \033[1;31m"+i+"\033[0m"));

                // System.out.println("Touche pressée \033[1;35m`"+e.getKeyCode()+"`\033[0m");
            }
            
            @Override
            public void keyReleased( KeyEvent e ){
                Graph.this.keyPressed.remove((Integer)e.getKeyCode());
                // Changement du cuseur s'il s'agit de la touche espace.
                if (e.getKeyCode() == 32)
                    Graph.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
