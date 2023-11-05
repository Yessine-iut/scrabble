package fr.unice.scrabble.scrabblid.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import fr.unice.scrabble.scrabblid.model.Appariement;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Random;

@Slf4j
@RestController
public class AppariementController {
    Appariement appariement = new Appariement();
    String urlPartie = "http://172.128.0.81:8081";

    public Mono<Void> apparierAsync(Integer p, Integer idJoueur) {
        WebClient webClient = WebClient.create(urlPartie);
        return webClient.post().uri("/apparierPartie/" + p).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).body(Mono.just(idJoueur), Integer.class)
                .retrieve().bodyToMono(Void.class);
    }

    public Mono<Void> createPartieAsync(Integer idJoueur, Integer idPartie) {
        WebClient webClient = WebClient.create(urlPartie);
        return webClient.post().uri("/creerPartie/" + idPartie)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(idJoueur), Integer.class)
                .retrieve().bodyToMono(Void.class);
    }

    @PostMapping("/rejoindrePartie")
    public Integer rejoindrePartie(@RequestBody Integer idJoueur) {
        for (Integer p : appariement.getParties().keySet()) {
            if (appariement.getParties().get(p) < 4) {
                appariement.getParties().replace(p, appariement.getParties().get(p) + 1);
                this
                        .apparierAsync(p, idJoueur)
                        .subscribe();
                return p;
            }
        }
        Integer idPartie = new Random().nextInt((1000 - 1) + 1) + 1;
        this.appariement.getParties().put(idPartie, 1);
        this
                .createPartieAsync(idJoueur, idPartie)
                .subscribe();
        return idPartie;
    }

}
