package fr.unice.scrabble.scrabblid.controller;

import fr.unice.scrabble.scrabblid.model.Anagrammeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnagrammeurController {
    @Autowired
    Anagrammeur anagrammeur;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/donnerAnagrammes")
    public String[] getAnagrammes(@RequestBody char[] lettres){
        return anagrammeur.donnerAnagramme(lettres);
    }

}
