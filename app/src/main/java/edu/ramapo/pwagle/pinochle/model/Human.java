/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/

package edu.ramapo.pwagle.pinochle.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Human extends Player {

    private String name;

    /**
     This is the default constructor for the Human Class
     */
    public Human(){
    }

    /**
    This is the parameterized constructor for the Human Class
    @param name String, name for the Human player
     */
    public Human(String name) {
        this.name = name;
    }

    /**
     This function returns the name of the player
     @return Human, as the name of human player
     */
    public String getName() {
        return "Human";
    }

    /**
     This function returns the turn card the human has played
     @param card Card, The card object that is a lead card if the player is chasing
     @return turnCard Card, the card played for a turn by human
     */
    public Card play(Card card){
        return turnCard;
    }

    /**
     This function sets the turn card for human player
     @param card  Card, the turn card for human
     */
    public void setTurnCard(Card card){
        turnCard = card;
        updateReason();
    }

    /**
     This function returns the turn recommendation for the human player
     @param card Card, The card object that is a lead card if the player is chasing
     @return help String, that is a recommendation for the human player
     */
    public String help(Card card){
        if(isleader()){
            helpLeadCard();
        } else{
            helpChaseCard(card);
        }

        String help;
        help = "I recommend you to play " + turnCard.getFace() + turnCard.getSuit() + play_reason;
        return help;
    }

    /**
     This function returns the meld recommendation for the human player
     @return help String, that is a recommendation for the human player for meld
     */
    public String meldHelp(){
        //create all melds
        updateAvailableCardsForSelection();
        createAllPossibleMelds();

        String meldName = "";
        String help;

        ArrayList<Card> meldSelection = new ArrayList<Card>();

        //select meld by points
        for(String meld:meldsByPoints){
            if(allPossibleMelds.get(meld)!=null && allPossibleMelds.get(meld).size()>0){
                meldSelection.addAll(allPossibleMelds.get(meld).get(0));
                meldName = meld;
                break;
            }
        }

        //update the recommendation string
        if(meldSelection.size()>0){
            help = "I recommend you to play ";

            for(Card card:meldSelection){
                help = help + card.getFace() + card.getSuit() + " ";
            }
            help = help + "as " + meldName + " to earn " + getMeldPoints(meldName) + " points.";
        } else{
            help = "No possible meld combinations!!!";
        }

        return help;
    }

    /**
     This function updates the reason after human has played a turn
     */
    public void updateReason(){
        String str = "Human played " + turnCard.getFace() + turnCard.getSuit();
        play_reason = str;
    }

    /**
     This function updates the reason after human has declared a meld
     */
    public void updateMeldReason(String meldName){
        play_reason = "Human declared " + meldName + ".";
    }

}

