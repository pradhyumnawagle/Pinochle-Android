package edu.ramapo.pwagle.pinochle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> current_deck;

    Deck() {

    }

    public Deck(ArrayList<Card> current_deck) {
        this.current_deck = current_deck;
    }

    public ArrayList<Card> getCurrent_deck() {
        return current_deck;
    }


    //arraylist get new deck

    public void printDeck(){

    }

    //private arraylist current_Deck

    private void createDeck(){
        current_deck = generateCards();
    }

    private void shuffleDeck(){
        Collections.shuffle(current_deck);
    }

    //private arraylist generateCards
    private ArrayList<Card> generateCards(){
        String[] cardFaces = { "9", "J", "Q", "K", "X" , "A" };
        String[] cardSuits = { "H", "S", "C", "D" };
        String[] unique_identifiers = {"a","b"};

       ArrayList<Card> new_deck = new ArrayList<Card>();

       for(String cardFace:cardFaces){
           for (String cardSuit:cardSuits){
               Card card = new Card();
               card.setFace(cardFace);
               card.setSuit(cardSuit);

               Card card2 = new Card();
               card2.setFace(cardFace);
               card2.setSuit(cardSuit);

               card.setUnique_identifier(unique_identifiers[0]);
               card2.setUnique_identifier(unique_identifiers[1]);
               new_deck.add(card);
               new_deck.add(card2);

           }
       }

       return new_deck;
    }

    public ArrayList<Card> getNewDeck(){
        createDeck();
        shuffleDeck();
        return current_deck;
    }
}
