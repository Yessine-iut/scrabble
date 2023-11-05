package fr.unice.scrabble.scrabblid.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import fr.unice.scrabble.scrabblid.model.plateau.Plateau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;


@SuppressWarnings({"squid:S106", "squid:S1172"})

//code source : https://www.geeksforgeeks.org/print-valid-words-possible-using-characters-array/
@Slf4j
@Component
public class Anagrammeur {

    ArrayList<String> dict = new ArrayList<>();
    ArrayList<String> result;
    // Alphabet size
    static final int SIZE = 26;

    public Anagrammeur() throws IOException {
        this.initDictionnaire();
    }


    public void initDictionnaire() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/dictionnaire.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            while (line != null) {
                this.dict.add(line);
                // read next line
                line = reader.readLine();
            }
        } catch (IOException e) {
            //log.error("erreur:" + e);
            e.printStackTrace();
        }
    }

    // trie Node
    static class TrieNode {
        TrieNode[] child = new TrieNode[SIZE];

        // isLeaf is true if the node represents
        // end of a word
        boolean leaf;

        // Constructor
        public TrieNode() {
            leaf = false;
            for (int i = 0; i < SIZE; i++)
                child[i] = null;
        }
    }

    // If not present, inserts key into trie
    // If the key is prefix of trie node, just
    // marks leaf node
    public void insert(TrieNode root, String key) {
        int n = key.length();
        TrieNode pChild = root;

        for (int i = 0; i < n; i++) {
            int index = key.charAt(i) - 'a';

            if (pChild.child[index] == null)
                pChild.child[index] = new TrieNode();

            pChild = pChild.child[index];
        }

        // make last node as leaf node
        pChild.leaf = true;
    }

    // A recursive function to print all possible valid
    // words present in array
    public void searchWord(TrieNode root, boolean[] hash, String str) {
        // if we found word in trie / dictionary
        if (root.leaf)
            this.result.add(str);

        // traverse all child's of current root
        for (int k = 0; k < SIZE; k++) {
            if (hash[k] && root.child[k] != null) {
                // add current character
                char c = (char) (k + 'a');

                // Recursively search reaming character
                // of word in trie
                searchWord(root.child[k], hash, str + c);
            }
        }
    }

    // Prints all words present in dictionary.
    public void findAllWords(char[] arr, TrieNode root, int n) {
        // create a 'has' array that will store all
        // present character in Arr[]
        boolean[] hash = new boolean[SIZE];

        for (int i = 0; i < n; i++)
            hash[Character.toLowerCase(arr[i]) - 'a'] = true;

        // string to hold output words
        String str = "";

        // Traverse all matrix elements. There are only
        // 26 character possible in char array
        for (int i = 0; i < SIZE; i++) {
            // we start searching for word in dictionary
            // if we found a character which is child
            // of Trie root
            if (hash[i] && root.child[i] != null) {
                str = str + (char) (i + 'a');
                this.searchWord(root.child[i], hash, str);
                str = "";
            }
        }
    }

    public String[] donnerAnagramme(char[] arr) {
        this.result = new ArrayList<>();
        TrieNode root = new TrieNode();
        // insert all words of dictionary into trie
        for (String word : dict) insert(root, word);

        findAllWords(arr, root, arr.length);

        return this.result.toArray(new String[result.size()]);
    }

    public Map<String, Integer> getMeilleurMotAjouer(String[] motsPossiblesAJouer){
        Plateau p = new Plateau(true);
        int scoreMax = 0;
        String motScoreMax = "/!\\ aucun mot n'est jouable";
        for(String mot : motsPossiblesAJouer){
            int scoreMot=0;
            char[] lettresDuMot = mot.toCharArray();

            for(char lettre: lettresDuMot) {
                System.out.println(Character.toUpperCase(lettre));
                System.out.println(p.getValeurDuneLettre(Character.toUpperCase(lettre)));
                scoreMot+= p.getValeurDuneLettre(lettre);
                if (scoreMot > scoreMax){
                    scoreMax = scoreMot;
                    motScoreMax = mot;
                }
            }
        }
        log.debug("Le meilleur mot à jouer serit: "+motScoreMax+" valant "+scoreMax+" points");
        return Map.of(motScoreMax,scoreMax);
    }

    // Driver program to test above function
    /* public static void main(String[] args) throws IOException {
         Anagrammeur anag = new Anagrammeur();
         char[] arr = {'e', 'o', 'b', 'a', 'm', 'g', 'l'};
         for (String s : anag.donnerAnagramme(arr))
             log.debug("mot proposé par l'anagrammeur: " + s);
     }*/
}