package fr.unice.scrabble.scrabblid.controller;

import java.util.*;

import fr.unice.scrabble.scrabblid.model.Joueur;
import fr.unice.scrabble.scrabblid.model.plateau.Plateau;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SuppressWarnings({"squid:S106", "squid:S1192"})

@Slf4j
@RestController
public class JoueurController {
    HashMap<Integer, Joueur> joueurs = new HashMap<>();
    String urlPartie = "http://172.128.0.81:8081";
    String urlAppariement = "http://172.128.0.83:8083";
    String urlAnagrammeur = "http://172.128.0.84:8084";

    public Mono<Integer> getJoueurAsync(Integer j) {
        WebClient webClient = WebClient.create(urlAppariement);
        return webClient.post().uri("/rejoindrePartie").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(j), Integer.class)
                .retrieve()
                .bodyToMono(Integer.class);
    }

    public Mono<Plateau> getPlateauAsync(Integer idPartie) {
        WebClient webClient = WebClient.create(urlPartie);
        return webClient.get().uri("/getPlateau/" + idPartie)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Plateau.class);
    }

    public Mono<Boolean> verifierJoueurActuelAsync(Integer j) {
        WebClient webClient = WebClient.create(urlPartie);
        return webClient.post().uri("/verifierJoueurActuel/" + joueurs.get(j).getIdPartie()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(j), Integer.class)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    public Mono<Void> finTourAsync(Integer idJoueur) {
        log.debug("Le joueur " + idJoueur + " met fin à son tour");
        WebClient webPartie = WebClient.create(urlPartie);
        return webPartie.post().uri("/finTour/" + this.joueurs.get(idJoueur).getIdPartie())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(this.joueurs.get(idJoueur).getPlateau()), Plateau.class)
                .retrieve().bodyToMono(Void.class);
    }

    public Mono<String[]> getMotsAsync(Character[] caracteres) {
        WebClient webAnnagrammeur = WebClient.create(urlAnagrammeur);
        return webAnnagrammeur.post().uri("/donnerAnagrammes/")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(caracteres), Character[].class)
                .retrieve().bodyToMono(String[].class);
    }

    @GetMapping("/seConnecter")
    public Integer seConnecter() {
        Joueur j = new Joueur(new Random().nextInt((1000 - 1) + 1) + 1);
        joueurs.put(j.getIdJoueur(), j);
        this
                .getJoueurAsync(j.getIdJoueur())
                .subscribe(id -> j.setIdPartie(id));
        return j.getIdJoueur();
    }

    @GetMapping("/jouer/{id}")
    public void jouer(@PathVariable(value = "id") Integer idJoueur) {
        Joueur j = this.joueurs.get(idJoueur);
        j.placer=false;
                    log.debug("Joueur " + idJoueur + " possède ces lettres: " +  j.getChevalet());
                    Character[]  chev = new Character[j.getChevalet().size()];
                    j.getChevalet().toArray(chev);
        this
                            .getMotsAsync(chev)
                            .subscribe(result ->
                                    {
                                        List<String> result2 = Arrays.asList(result);
                                        Collections.shuffle(result2);
                                        result2.toArray(result);
                                        log.debug("Joueur " + idJoueur + " a reçu ces mots de l'anagrammeur:\n " + Arrays.toString(result));
                                        for(int v=0;v<result.length;v++){
                                            if(!j.placer) {
                                                if (j.getPlateau().getPremierMot()) {
                                                    j.placerPremierMot(result[v].toUpperCase());
                                                    j.placer = true;
                                                } else {
                                                    for (int y = 0; y < j.getPlateau().getPlateauDuJeu().length; y++) {
                                                        for (int c = 0; c < j.getPlateau().getPlateauDuJeu()[y].length; c++) {
                                                            if(!j.placer){
                                                                j.placer = j.placerMot(y, c, result[v].toUpperCase(), false);

                                                            }
                                                        }

                                                    }
                                                    for (int y = 0; y < j.getPlateau().getPlateauDuJeu()[0].length; y++) {
                                                        for (int c = 0; c < j.getPlateau().getPlateauDuJeu().length; c++) {
                                                            if(!j.placer){
                                                                j.placer = j.placerMot(y, c, result[v].toUpperCase(), true);
                                                            }

                                                        }

                                                    }
                                                }
                                            }
                                        }
                                        if(!j.placer){
                                            j.getPlateau().setBlocage(j.getPlateau().getBlocage()+1);
                                            j.getChevalet().clear();
                                        }else
                                            j.getPlateau().setBlocage(0);

                                        j.getPlateau().afficherPlateau();
                                    }
                            );




    }
    @GetMapping("/afficherScore")
    public void afficherScore() {
        log.debug("SCORE DE FIN DE PARTIE");
        for(Integer i : joueurs.keySet()){
        log.debug("Joueur "+i+": "+joueurs.get(i).getScore()+" pts");
    }

    }
    @GetMapping("/piocher/{id}")
    public void piocher(@PathVariable(value = "id") Integer idJoueur) {

        this
                .verifierJoueurActuelAsync(idJoueur)
                .subscribe(result -> joueurActuelPioche(result, idJoueur));

    }

    public void joueurActuelPioche(boolean result, int idJoueur) {
        if (result) {
            joueurs.get(idJoueur).piocher();
            log.debug("Le joueur " + idJoueur + " a pioché");
        } else
            log.error("Le joueur " + idJoueur + " essaye de piocher mais ce n'est pas son tour!");
    }

    public void joueurActuelPasser(boolean result, int idJoueur) {
        if (result) {
            this
                    .finTourAsync(idJoueur)
                    .subscribe();
            log.debug("Le joueur " + idJoueur + " a passé son tour");
        } else
            log.error("Le joueur " + idJoueur + " essaye de passer son tour mais ce n'est pas son tour!");
    }

    @GetMapping("/passerSonTour/{id}")
    public void passerSonTour(@PathVariable(value = "id") Integer idJoueur) {
        this
                .verifierJoueurActuelAsync(idJoueur)
                .subscribe(result -> joueurActuelPasser(result, idJoueur));

    }
    @GetMapping("/getPartie/{id}")
    public int getPartie(@PathVariable(value = "id") Integer idJoueur) {
      return this.joueurs.get(idJoueur).getIdPartie();
    }


    @GetMapping("/mettreAJourPlateau/{id}")
    public void mettreAJourPlateau(@PathVariable(value = "id") Integer idJoueur) {
        this
                .getPlateauAsync(joueurs.get(idJoueur).getIdPartie())
                .subscribe(result -> joueurs.get(idJoueur).setPlateau(result));
        log.debug("le plateau se met à jour");
    }

    @PostMapping("/interrogerAnnagramme")
    public void interrogerAnnagramme(@RequestBody Joueur joueur) {
        joueur.interrogerAnagrammeur();
        log.debug("Le joueur " + joueur.getIdJoueur() + " interroge l'annagammeur");
    }
}
