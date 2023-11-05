package fr.unice.scrabble.scrabblid.controller;


import fr.unice.scrabble.scrabblid.model.Partie;
import fr.unice.scrabble.scrabblid.model.plateau.Plateau;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
public class PartieController {

    HashMap<Integer, Partie> parties = new HashMap<>();

    @PostMapping("/apparierPartie/{id}")
    public void apparierPartie(@PathVariable(value = "id") Integer idPartie, @RequestBody Integer idJoueur) {
        parties.get(idPartie).addJoueur(idJoueur);
        log.debug("le joueur idJoueur=" + idJoueur + " s'est apparié à la partie idPartie=" + idPartie);
        if (parties.get(idPartie).getJoueurs().size() == 4) {
            parties.get(idPartie).initPartie();
            log.debug("La partie ID=" + idPartie + " a commencé!");
        }
    }

    @PostMapping("/verifierJoueurActuel/{id}")
    public Boolean verifierJoueurActuel(@PathVariable(value = "id") Integer idPartie, @RequestBody Integer idJoueur) {
        return parties.get(idPartie).getJoueurs().get(parties.get(idPartie).getJoueurActuel()) == idJoueur.intValue();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/creerPartie/{id}")
    public void creerPartie(@RequestBody Integer idJoueur, @PathVariable(value = "id") Integer idPartie) {
        Partie p = new Partie(idPartie);
        parties.put(p.getIdPartie(), p);
        p.addJoueur(idJoueur);
        log.debug("la partie ID=" + idPartie + " s'est créée");
    }

    @PostMapping("/finTour/{id}")
    public void finTour(@RequestBody Plateau pl, @PathVariable(value = "id") Integer idPartie) {
        Partie p = this.parties.get(idPartie);
        p.setPlateau(pl);
        p.avancerJoueur();
    }
    @GetMapping("/finPartie/{id}")
    public boolean finPartie(@PathVariable(value = "id") Integer idPartie) {
        Partie p = this.parties.get(idPartie);
        if(p.getPlateau().getBlocage()>=8 || p.getPlateau().getPioche().size()==0){
            return true;
        }
        return false;

    }

    @GetMapping("/getPlateau/{id}")
    public Plateau finTour(@PathVariable(value = "id") Integer idPartie) {
        Partie p = this.parties.get(idPartie);
        //TODO afficher plateau quand le mot sera déposé
        // p.getPlateau().afficherPlateau();
        return p.getPlateau();
    }
}
