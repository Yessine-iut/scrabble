package fr.unice.scrabble.scrabblid.model.plateau;

public class Case {
    private Character lettreSurLaCase;
    private enumValeurCase valeurCase;
    public Case(){
    }
    public Case(Character lettreSurLaCase, enumValeurCase valeurCase) {
        this.lettreSurLaCase = lettreSurLaCase;
        this.valeurCase = valeurCase;
    }

    public Case(enumValeurCase valeurCase) {
        this.valeurCase = valeurCase;
    }

    public Character getLettreSurLaCase() {
        return lettreSurLaCase;
    }

    public void setLettreSurLaCase(Character lettreSurLaCase) {
        this.lettreSurLaCase = lettreSurLaCase;
    }

    public enumValeurCase getValeurCase() {
        return valeurCase;
    }

    public void setValeurCase(enumValeurCase valeurCase) {
        this.valeurCase = valeurCase;
    }
}
