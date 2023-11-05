package fr.unice.scrabble.scrabblid.model.plateau;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Slf4j
public class Plateau {
    private Case[][] plateauDuJeu;
    private ArrayList<Character> pioche;
    // une lettre est un charactère ayant une valeur
    private HashMap<Character, Integer> lettre;
    private boolean premierMot=true;
    private int blocage=0;
    public Plateau() {
        initPlateau();
        initPioche();
        setValeurLettre();
    }
    public void setPremierMot(boolean p){
        premierMot=p;
    }
    public boolean getPremierMot(){
        return premierMot;
    }
    public int getBlocage(){
        return blocage;
    }
    public void setBlocage(int p){
        blocage=p;
    }


    public void initPioche() {
        pioche = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            this.pioche.add('B');
            this.pioche.add('C');
            this.pioche.add('F');
            this.pioche.add('G');
            this.pioche.add('H');
            this.pioche.add('P');
            this.pioche.add('V');
        }
        for (int i = 1; i <= 3; i++) {
            this.pioche.add('D');
            this.pioche.add('M');
        }
        for (int i = 1; i <= 5; i++)
            this.pioche.add('L');
        for (int i = 1; i <= 6; i++) {
            this.pioche.add('N');
            this.pioche.add('O');
            this.pioche.add('R');
            this.pioche.add('S');
            this.pioche.add('T');
            this.pioche.add('U');
        }
        for (int i = 1; i <= 8; i++)
            this.pioche.add('I');
        for (int i = 1; i <= 9; i++)
            this.pioche.add('A');
        for (int i = 1; i <= 15; i++)
            this.pioche.add('E');
        for (int i = 1; i <= 1; i++) {
            this.pioche.add('J');
            this.pioche.add('K');
            this.pioche.add('Q');
            this.pioche.add('W');
            this.pioche.add('X');
            this.pioche.add('Y');
            this.pioche.add('Z');
        }
        Collections.shuffle(this.pioche);
    }

    public void setValeurLettre() {
        lettre = new HashMap<>();
        lettre.put('A', 1);
        lettre.put('B', 3);
        lettre.put('C', 3);
        lettre.put('D', 2);
        lettre.put('E', 1);
        lettre.put('F', 4);
        lettre.put('G', 2);
        lettre.put('H', 4);
        lettre.put('I', 1);
        lettre.put('J', 8);
        lettre.put('K', 10);
        lettre.put('L', 1);
        lettre.put('M', 2);
        lettre.put('N', 1);
        lettre.put('O', 1);
        lettre.put('P', 3);
        lettre.put('Q', 8);
        lettre.put('R', 1);
        lettre.put('S', 1);
        lettre.put('T', 1);
        lettre.put('U', 1);
        lettre.put('V', 4);
        lettre.put('W', 10);
        lettre.put('X', 10);
        lettre.put('Y', 10);
        lettre.put('Z', 10);
        lettre.put('*', 0);
    }

    public int scoreCharacterCase(Character mot,Case c){
        mot=Character.toUpperCase(mot);
        int score=lettre.get(mot);
            if(c.getValeurCase().equals(enumValeurCase.LETTRE_COMPTE_DOUBLE)){
                score*=2;
            }else if(c.getValeurCase().equals(enumValeurCase.LETTRE_COMPTE_TRIPLE)){
                score*=3;
            }
        return score;
    }

    public void initPlateau() {
        this.plateauDuJeu = new Case[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                plateauDuJeu[i][j] = new Case(' ', enumValeurCase.CLASSIQUE);
            }
        }
        plateauDuJeu[0][0] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
        plateauDuJeu[0][3] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[0][7] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
        plateauDuJeu[0][11] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[0][14] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
        plateauDuJeu[1][1] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[1][5] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[1][9] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[1][13] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[2][2] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[2][6] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[2][8] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[2][12] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[3][0] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[3][3] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[3][7] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[3][11] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[3][14] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[4][4] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[4][10] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[5][1] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[5][5] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[5][9] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[5][13] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[6][2] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[6][6] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[6][8] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[6][12] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[7][0] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
        plateauDuJeu[7][3] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[7][11] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[7][14] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
        plateauDuJeu[8][2] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[8][6] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[8][8] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[8][12] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[9][1] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[9][5] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[9][9] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[9][13] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[10][4] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[10][10] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[11][0] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[11][3] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[11][7] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[11][11] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[11][14] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[12][2] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[12][6] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[12][8] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[12][12] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[13][1] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[13][5] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[13][9] = new Case(' ', enumValeurCase.LETTRE_COMPTE_TRIPLE);
        plateauDuJeu[13][13] = new Case(' ', enumValeurCase.MOT_COMPTE_DOUBLE);
        plateauDuJeu[14][0] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
        plateauDuJeu[14][3] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[14][7] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
        plateauDuJeu[14][11] = new Case(' ', enumValeurCase.LETTRE_COMPTE_DOUBLE);
        plateauDuJeu[14][14] = new Case(' ', enumValeurCase.MOT_COMPTE_TRIPLE);
    }

    public void afficherPlateau() {
        String affichage = "";
        int i = 0;
        while (i < 15) {
            for (int j = 0; j < 15; j++) {
                affichage += "|" + plateauDuJeu[i][j].getLettreSurLaCase();
            }
            affichage += "|\n";
            i++;
        }
        log.debug("\n" + affichage);
    }
    public Case[][] getPlateauDuJeu() {
        return plateauDuJeu;
    }

    public ArrayList<Character> getPioche() {
        return pioche;
    }

    public Integer getValeurDuneLettre(Character laLettre) {
        return this.lettre.get(laLettre);
    }
    public Plateau(boolean b){setValeurLettre();}
}