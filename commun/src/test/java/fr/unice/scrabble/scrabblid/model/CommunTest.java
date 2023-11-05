package fr.unice.scrabble.scrabblid.model;

import static org.junit.Assert.*;

import fr.unice.scrabble.scrabblid.model.plateau.Plateau;
import fr.unice.scrabble.scrabblid.model.plateau.enumValeurCase;
import org.junit.Test;


public class CommunTest {

    Plateau plateau;

    @Test
    public void initPiocheTest() {
        plateau = new Plateau();

        plateau.initPioche();
        assertEquals(plateau.getPioche().size(), 100);
        plateau.getPioche().remove(plateau.getPioche().size()-1);
        assertEquals(plateau.getPioche().size(), 99);
    }


    @Test
    public void setValeurLettreTest() {
        plateau = new Plateau();

        plateau.setValeurLettre();

        assertEquals(plateau.getValeurDuneLettre('A').intValue(), 1);
        assertEquals(plateau.getValeurDuneLettre('F').intValue(), 4);
        assertEquals(plateau.getValeurDuneLettre('W').intValue(), 10);
    }


    @Test
    public void initPlateauTest() {
        plateau = new Plateau();

        plateau.initPlateau();

        assertEquals(plateau.getPlateauDuJeu()[0][0].getValeurCase(), enumValeurCase.MOT_COMPTE_TRIPLE);
        assertEquals(plateau.getPlateauDuJeu()[8][12].getValeurCase(), enumValeurCase.LETTRE_COMPTE_DOUBLE);
        assertEquals(plateau.getPlateauDuJeu()[14][14].getValeurCase(), enumValeurCase.MOT_COMPTE_TRIPLE);


    }

}

