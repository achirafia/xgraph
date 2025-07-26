package app.gui.page.content.graph;

/**
 * Il s'agit d'une interface qui représente un élément d'un graphe.
 * 
 * @author Achirafi Amal [ amalachirafi@gmail.com ] [ amal.achirafi@etu.u-paris.fr]
 */
public interface GraphElt {

//////////////////////////////////////////////////////////////////////
//#_________________________  Fonctions  __________________________#//
//////////////////////////////////////////////////////////////////////

    /**
     * Fonction qui va modifier l'état de séléction de l'élément 
     * du graphe.
     *
     * @param s Le nouvel état de l'élément.
     */
    public void setSelected( boolean s );

    /**
     * Fonction qui va récupérer l'état de séléction de l'élément 
     * du graphe.
     *
     * @param s Le nouvel état de l'élément.
     */
    public boolean isSelected();

}
