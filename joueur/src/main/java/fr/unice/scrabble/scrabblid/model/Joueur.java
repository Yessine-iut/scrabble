package fr.unice.scrabble.scrabblid.model;

import fr.unice.scrabble.scrabblid.model.plateau.Case;
import fr.unice.scrabble.scrabblid.model.plateau.Plateau;
import fr.unice.scrabble.scrabblid.model.plateau.enumValeurCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings({"squid:S106", "squid:S1192", "squid:S2119"})

@Slf4j
public class Joueur {
    private int idJoueur;
    private Plateau pl;
    private int idPartie;
    public boolean placer=false;

    private ArrayList<Character> chevalet = new ArrayList<>();
    private int score = 0;
    String urlAnagrammeur = "http://172.128.0.84:8084";

    public Joueur(int id) {
        this.idJoueur = id;
    }

    public void piocher() {
        int nbLettreApiocher = 7 - chevalet.size();
        for (int i = 0; i < nbLettreApiocher; i++) {
            if(pl.getPioche().size()!=0){
                this.chevalet.add(pl.getPioche().remove(0));
            }else{
                System.out.println("Il n'y a plus de pioche");
            }
        }

    }

    // pour le moment ca renvoie des string plus tard un tableau de characteres
    public String interrogerAnagrammeur() {

        char[] chevaletCaractere = new char[this.chevalet.size()];
        for (int i = 0; i < chevalet.size(); i++) {
            chevaletCaractere[i] = Character.toLowerCase(this.chevalet.get(i).charValue());
        }
        //ArrayList<String> mots = new ArrayList<>();

        WebClient webClient = WebClient.create(urlAnagrammeur);

        ArrayList<String> mots = webClient.post().uri("/donnerAnagrammes").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(Mono.just(chevaletCaractere), char[].class).retrieve().bodyToMono(ArrayList.class).block();

        String rep = "";
        if (mots != null && !mots.isEmpty())
            rep = mots.get(new Random().nextInt(mots.size()));
        else
            log.error("requête donner anagrammes échouée!");
        return rep;
    }

    public void setPlateau(Plateau p) {
        this.pl = p;
    }
    public void placerPremierMot(String mot){
        char[]motC= mot.toCharArray();
                for(int i=0;i<motC.length;i++){
                    boolean alreadyRemove=false;
                    int j=0;
                    while(!alreadyRemove && j<this.chevalet.size()){
                        if (this.chevalet.get(j)==(motC[i])){
                            this.chevalet.remove(j);
                            alreadyRemove=true;

                        }
                        j++;

                    }
                    this.pl.getPlateauDuJeu()[7][7+i].setLettreSurLaCase(motC[i]);
                }
                this.pl.setPremierMot(false);
    }
    public boolean placerMot(int ligne, int colonne, String motAPlacer, boolean c) {
        Case[][] plat = this.pl.getPlateauDuJeu();
        int motCompte=1;
        int score=0;
        if(plat[ligne][colonne].getLettreSurLaCase()==' ')
            return false;

        char[] mot = motAPlacer.toCharArray();

        boolean error = true;
        int index = 0;

        for(int i = 0;i<mot.length;i++) {
            if(plat[ligne][colonne].getLettreSurLaCase()==mot[i]) {
                error = false;
                index = i;
            }
        }

        if(error)
            return false;

        if(!c) {
            if(colonne<index || plat.length-colonne-1<mot.length-index-1)
                return false;
            for(int i = 0;i<=index;i++) {
                if(plat[ligne][colonne-i].getLettreSurLaCase() !=mot[index-i] && plat[ligne][colonne-i].getLettreSurLaCase() !=' ' )
                    return false;
            }
            for(int i = 0;i<=mot.length-index-1;i++) {
                if(plat[ligne][colonne+i].getLettreSurLaCase() !=mot[index+i] && plat[ligne][colonne+i].getLettreSurLaCase() !=' ')
                    return false;
            }

            int start = colonne - index;
            for(int i = 0;i<mot.length;i++){
                if(plat[ligne][start+i].getLettreSurLaCase() ==' '){
                    plat[ligne][start+i].setLettreSurLaCase(mot[i]);
                    score+=this.pl.scoreCharacterCase(mot[i],plat[ligne][start+i]);
                    if(motCompte!=3 && plat[ligne][start+i].getValeurCase()== enumValeurCase.MOT_COMPTE_DOUBLE){
                        motCompte=2;
                    }else if(plat[ligne][start+i].getValeurCase()== enumValeurCase.MOT_COMPTE_TRIPLE){
                        motCompte=3;
                    }
                    boolean alreadyRemove=false;
                    int j=0;
                    while(!alreadyRemove && j<this.chevalet.size()){
                        if (this.chevalet.get(j)==(mot[i])){
                            this.chevalet.remove(j);
                            alreadyRemove=true;

                        }
                        j++;

                    }
                }
            }
            this.score+=score*motCompte;
        }
        else {
            if(ligne<index || plat.length-ligne-1<mot.length-index-1)
                return false;

            for(int i = 0;i<=index;i++) {

                if(plat[ligne-i][colonne].getLettreSurLaCase() !=mot[index-i] && plat[ligne-i][colonne].getLettreSurLaCase() !=' ' )
                    return false;
            }
            for(int i = 0;i<=mot.length-index-1;i++) {
                if(plat[ligne+i][colonne].getLettreSurLaCase() !=mot[index+i] && plat[ligne+i][colonne].getLettreSurLaCase() !=' ')
                    return false;
            }

            int start = ligne - index;
            for(int i = 0;i<mot.length;i++){
                if(plat[start+i][colonne].getLettreSurLaCase() ==' '){
                    plat[start+i][colonne].setLettreSurLaCase(mot[i]);
                    score+=this.pl.scoreCharacterCase(mot[i],plat[ligne][start+i]);
                    boolean alreadyRemove=false;
                    int j=0;
                    while(!alreadyRemove && j<this.chevalet.size()){
                        if (this.chevalet.get(j)==(mot[i])){
                            this.chevalet.remove(j);
                            alreadyRemove=true;
                        }
                        j++;

                    }
                }
            }
            this.score+=score*motCompte;
        }

        return true;
    }

    public Plateau getPlateau() {
        return this.pl;
    }

    public boolean isPossible(char[]mot){

        List<Character> listC = new ArrayList<Character>();
        List<Character> listChevalet = new ArrayList<Character>();

        boolean alreadyRemove=false;
        for (char c : this.chevalet) {
            listChevalet.add(c);
        }
        for (char c : mot) {
            listC.add(c);
        }


        for(int i=0;i<listC.size();i++) {
            int j=0;
            alreadyRemove=false;
            while (!alreadyRemove && j<listChevalet.size()) {
                if (listChevalet.get(j)==(listC.get(i))) {
                    listChevalet.remove(j);
                    alreadyRemove = true;
                }
                j++;
                if(!alreadyRemove){
                    return false;
                }
            }

        }

        return true;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public ArrayList<Character> getChevalet() {
        return chevalet;
    }

    public void setChevalet(ArrayList<Character> chevalet) {
        this.chevalet = chevalet;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }
}