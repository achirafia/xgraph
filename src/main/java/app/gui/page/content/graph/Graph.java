package app.gui.page.content.graph;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import app.conf.Config;

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

    /**
     * Il s'agit des coordonnées de la souris au moment du clique 
     * pour une séléction.
     */
    private int[] selectionCoords = {0 , 0};

    /**
     * Il s'agit d'une liste qui va contenir les noeuds qui ont été séléctionnés 
     * par l'utilsiateur avec le rectangle de séléction.
     *
     * Cette liste permet de faire la différence entre les noeuds qui étaient 
     * déjà séléctionnés au départ (donc qui doivent être déséléctionnés) et les 
     * noeuds qui ont été séléctionnés pendant la nouvelle séléction.
     */
    private ArrayList<GraphNode> selectionTemp = new ArrayList<>();

    /**
     * Il s'agit du panneau visuel qui montre la séléction dans l'interface graphique.
     */
    private JPanel selectionRectangle = new JPanel(){{
            this.setOpaque(false);
            this.setBounds(0, 0, 0, 0);
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Config.selectionColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    };

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
        Graph.this.add(node, 1);
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
     * Fonction qui va faire la séléction des noeuds à partir de la séléction de la souris.
     */
    private void selection( MouseEvent e ){

        int rectWidth = Math.abs( e.getX() - Graph.this.selectionCoords[0] ); // Largeur du rectangle.
        int rectHeight = Math.abs( e.getY() - Graph.this.selectionCoords[1] ); // Hauteur du rectangle.
        int rectX = Math.min( e.getX(), Graph.this.selectionCoords[0] ); // Coord X de la séléction.
        int rectY = Math.min( e.getY(), Graph.this.selectionCoords[1] ); // Coord Y de la séléction.
        Graph.this.selectionRectangle.setBounds( rectX, rectY, rectWidth, rectHeight );

        // bounds
        Rectangle rectBounds = this.selectionRectangle.getBounds();

        /* Ajout des noeuds. */
        // Parcours de la liste des noeds.
        this.nodes.stream().forEach( n -> {
            if (rectBounds.contains(n.getX() + n.getWidth()/2, n.getY() + n.getHeight()/2)){ // Le noeud est contenu dans la séléction.
                // Si le noeud est déjà présent dans la liste de séléction.
                if ( this.selectionTemp.contains(n) ) return; // Si le noeuds est déjà dans la liste de séléction actuelle on ne fait rien du tout.
                if ( n.isSelected() ) { // S'il était déjà séléctionné avant la séléction, alors on le désélctionne (CTRL maintenue).
                    assert( this.keyPressed.contains( 17 ) ); // Si 17 n'est pas maintenu alors il y a un problème.
                    this.unselectNode(n);
                    this.selectionTemp.addLast(n);
                    return;
                }
                // Si on est ici c'est que le noeud doit être séléctionné.
                this.selectNode(n);
                this.selectionTemp.addLast(n);
            }
            else{
                if ( !this.selectionTemp.contains(n) ) return; // Si le noeuds n'est pas dans la liste de séléction actuelle on ne fait rien du tout.
                if ( !n.isSelected() ) { // Si le noeud est déséléctionné alors, il était déjà séléctionné avant la séléction. (CTRL maintenue)
                    assert( this.keyPressed.contains( 17 ) ); // Si 17 n'est pas maintenu alors il y a un problème.
                    this.selectNode(n);
                    this.selectionTemp.remove(n);
                    return;
                }
                // Si on est ici c'est que le noeud doit être déséléctionné.
                this.unselectNode(n);
                this.selectionTemp.remove(n);
                 
            }
        }
        );

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
                if (!Graph.this.keyPressed.contains(17)) unselectComponents(); 
                Graph.this.repaint();
                
            }
    
            @Override
            public void mousePressed( MouseEvent e ){
                // Si la touche ESPACE a été saisie, alors on ne fait rien tout.
                if (Graph.this.keyPressed.contains(32))
                    return;

                // Mise à jour des coordonnées de la souris.
                Graph.this.selectionCoords[0] = e.getX();
                Graph.this.selectionCoords[1] = e.getY();

                if (!Graph.this.keyPressed.contains(17)) unselectComponents(); 
                Graph.this.repaint();
            }
    
            @Override
            public void mouseReleased( MouseEvent e ){
                // Mise à jour du panneau de séléction.
                Graph.this.selectionRectangle.setBounds( 0, 0, 0, 0 );
                // On vide la liste temporaire de séléction.
                Graph.this.selectionTemp.clear();
            }

        } );

        this.addMouseMotionListener( new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                
                // Si la touche ESPACE a été saisie, alors on ne fait rien tout.
                if (Graph.this.keyPressed.contains(32)){ // Décalage.
                     
                }
                else{ // Mode séléction
                    selection(e);
                }
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

                if (e.getKeyCode() == 80){ // DEBUG AFFICHE TOUTES LES INFOS.
                    System.out.println("Touches : -----");
                    Graph.this.keyPressed.stream().forEach(i-> System.out.println("Touche \033[1;31m"+i+"\033[0m"));
                    System.out.println("Noeuds : -----");
                    Graph.this.nodes.stream().forEach(n -> System.out.println(n));
                    System.out.println("Selection : -----");
                    Graph.this.selectedNodes.stream().forEach(n -> System.out.println(n));
                }

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
        
        // Ajout du rectangle de séléction dans le panneau.
        this.add( this.selectionRectangle, 0 );
            
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
