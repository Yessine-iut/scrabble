package fr.unice.scrabble.scrabblid.model;

import java.util.ArrayList;


import fr.unice.scrabble.scrabblid.model.plateau.Plateau;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Partie {

    private ArrayList<Integer> joueurs = new ArrayList<>();
    private Plateau plateau;
    private int idPartie;
    private int nbTour;
    private int joueurActuel;

    public int getNbTour() {
        return nbTour;
    }

    public int getJoueurActuel() {
        return joueurActuel;
    }

    public Partie(int id) {
        this.idPartie = id;
    }

    public void addJoueur(int i) {
        joueurs.add(i);
    }

    public ArrayList<Integer> getJoueurs() {
        return joueurs;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public void initPartie() {
        plateau = new Plateau();
        this.nbTour = 0;
        this.joueurActuel = 0;
    }

    public void setPlateau(Plateau pl) {
        this.plateau = pl;
    }

    public void avancerJoueur() {
        joueurActuel += 1;
        if (joueurActuel >= joueurs.size()) {
            joueurActuel = 0;
            log.debug("Le tour n°" + (nbTour+1) + " est terminé");
            nbTour += 1;
        }
    }

    public Plateau getPlateau() {
        return this.plateau;
    }
}