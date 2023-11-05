package fr.unice.scrabble.scrabblid.model;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnagrammeurTest {

    @Test
    public void testDonnerAnnagramme() throws IOException {
        Anagrammeur a=new Anagrammeur();
        String[]res= a.donnerAnagramme(new char[0]);
        assertEquals(res.length,0);
        res= a.donnerAnagramme(new char[]{'e','l','t','o','p'});
        assertTrue(res.length>0);
        assertTrue(Arrays.toString(res).contains("pote"));
        assertTrue(Arrays.toString(res).contains("top"));
    }
}
