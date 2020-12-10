/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/


package edu.ramapo.pwagle.pinochle.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {

    private String face ="", suit ,unique_identifier;
    private int points_worth;
    private int unique_index, resource_id;

    private boolean at_least_one_meld;

    private HashMap<String, ArrayList<Card>> melds = new HashMap<String, ArrayList<Card>>();


    /**
     This is the default constructor for the Card Class
     */
    public Card() {
    }

    /**
     This is the parameterized constructor for the Card Class
     @param face String, the face of the card
     @param suit String, the suit of the card
     @param unique_identifier String, the unique identifier of a card because there are 2 cards of same face and suit
     @param points_worth int,The points worth of the card
     @param at_least_one_meld boolean, The boolean value that is true if a card is involved in at leat one meld
     */
    public Card(String face, String suit, String unique_identifier, int points_worth, int unique_index, boolean at_least_one_meld) {
        this.face = face;
        this.suit = suit;
        this.points_worth = points_worth;
        this.unique_index = unique_index;
        this.at_least_one_meld = at_least_one_meld;
        this.unique_identifier = unique_identifier;  }

    /**
     This function returns the face of the card
     @return face, the string face of the card
     */
    public String getFace() {
        return face;
    }

    /**
     This function sets the face of the card
     @param face String, the string face of the card
     */
    public void setFace(String face) {
        this.face = face;
    }

    /**
     This function returns the suit of the card
     @return suit, the string suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     This function sets the suit of the card
     @param suit String, the string suit of the card
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     This function returns the unique identifier of the card
     @return unique_identifier, the string unique identifier of the card
     */
    public String getUnique_identifier() {
        return unique_identifier;
    }

    /**
     This function sets the uniqur identifier of the card
     @param unique_identifier String, the string identifier of the card
     */
    public void setUnique_identifier(String unique_identifier) {
        this.unique_identifier = unique_identifier;
    }

    /**
     This function returns the points worth of the card
     @return face, the int value of points worth of the card
     */
    public int getPoints_worth() {

        if ("J".equals(face)) {
            return 2;
        }
        else if ("Q".equals(face)) {
            return 3;
        }
        else if ("K".equals(face)) {
            return 4;
        }
        else if ("X".equals(face)) {
            return 10;
        }
        else if ("A".equals(face)) {
            return 11;
        }
        else {
            return 0;
        }
    }

    /**
     This function sets the points of the card
     @param points_worth int, the points value of the card
     */
    public void setPoints_worth(int points_worth) {
        this.points_worth = points_worth;
    }

    /**
     This function returns the unique index of the card
     @return face, the string unique index of the card
     */
    public int getUnique_index() {
        return unique_index;
    }

    /**
     This function sets the unique index of the card
     @param unique_index int, the unique index of the card
     */
    public void setUnique_index(int unique_index) {
        this.unique_index = unique_index;
    }

    /**
     This function returns the value of boolean at_least_one_meld
     @return at_least_one_meld boolean
     */
    public boolean isAt_least_one_meld() {
        return at_least_one_meld;
    }

    /**
     This function sets the boolean at_least_one_meld of the card
     @param at_least_one_meld boolean
     */
    public void setAt_least_one_meld(boolean at_least_one_meld) {
        this.at_least_one_meld = at_least_one_meld;
    }

    /**
     This function checks if a meld exists in the card's melds map
     @param meldName String, the name of the meld to be checked in the map
     */
    public boolean meldExists(String meldName){


        if(melds.containsKey(meldName)) {
            if (melds.get(meldName).size() > 0) {
                return true;
            }
            return false;
        }
        return false;
    }


    /**
     To find if the melds map of the card is empty or not
     @return Boolean true if the map is empty and false if not
     */
    public boolean noOldMelds(){
        return melds.isEmpty();
    }

    /**
     This function returns the resource id of the card
     @return resource_id, the int resource id of the card
     */
    public int getResource_id() {
        return resource_id;
    }

    /**
     This function sets the resource id of the card
     @param resource_id int, the resource id of the card
     */
    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }

    /**
     This function returns the melds map of the card
     @return melds, The melds hash map of the card as a map of string key and array list of Cards as value
     */
    public HashMap<String, ArrayList<Card>> getMelds() {
        return melds;
    }

    /**
     This function adds a meld in melds map of the card using the meld's name as key
     @param meldName String, a string with the meld name as key in the map
     @param cards ArrayList<Card>>, an array list containing cards used in the meld
     */
    public void setMeldInMap(String meldName, ArrayList<Card> cards){

        if(melds.containsKey(meldName)){
            ArrayList<Card> meldCards = new ArrayList<Card>();
            meldCards.addAll(cards);
            melds.get(meldName).addAll(meldCards);
        } else{
            ArrayList<Card> meldCards = new ArrayList<Card>();
            meldCards.addAll(cards);
            melds.put(meldName,meldCards);
        }

    }

    /**
     This function removes a meld from the card's melds map
     @param meldName String, a string with the meld name as key in the map
     */
    public void removeMeld(String meldName){
        melds.remove(meldName);
    }

    /**
     This function checks if a card needs a star i.e. involved in more than one active meld
     @param cards ArrayList<ArrayList<Card>>, a array list containing array lists of Cards
     @return boolean, true if the card needs star, false if not
     */
    public boolean checkForStar(ArrayList<ArrayList<Card>> cards){
        int count = 0;

        if(moreThanOneActiveMeld()){
            for(ArrayList<Card> cardList: cards){
                for(Card card:cardList){
                    if(card.equals(this)){
                        count += 1;
                    }
                }
            }
        }

        if(count >= 2){
            return true;
        }
        return false;
    }

    /**
     This function checks if a card is involved in more than one active meld
     @return boolean, true if the involved in more than one active meld, false if not
     */
    public boolean moreThanOneActiveMeld(){

        int activeMelds = 0;

        for(HashMap.Entry<String,ArrayList<Card>> entry: melds.entrySet()){
            ArrayList<Card> meldCards = new ArrayList<Card>();
            meldCards = entry.getValue();

            for(Card card : meldCards){
                if(searchInMeldsMap(card)){
                    activeMelds += 1;
                    break;
                }
            }
        }

        if(activeMelds >= 2){
            return true;
        }
        return false;
    }

    /**
     This function checks if a card is present in the melds map of the main card
     @param mainCard Card, the card which is searched in the map
     @return boolean, true if the card is present in the map
     */
    private boolean searchInMeldsMap(Card mainCard){

        for(HashMap.Entry<String,ArrayList<Card>> entry: melds.entrySet()){
            ArrayList<Card> meldCards = new ArrayList<Card>();
            meldCards = entry.getValue();
            for(Card card:meldCards){
                if(card.equals(mainCard)){
                    return true;
                }
            }
        }
        return false;
    }
}
