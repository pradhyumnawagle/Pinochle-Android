/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/

package edu.ramapo.pwagle.pinochle.model;

import java.util.ArrayList;

public class Computer extends Player {

    /**
     This is the default constructor for the Computer Class
     */
    public Computer(){}

    /**
     This is the parameterized constructor for the Computer Class
     @param name String, name for the Computer player
     */
    public Computer(String name) {
        this.name = name;
    }

    /**
     This function returns the name of the player
     @return Computer, as the name of computer player
     */
    public String getName() {
        return "Computer";
    }

    /**
     This function returns the turn card the computer has played
     @param card Card, The card object that is a lead card if the player is chasing
     @return turnCard Card, the card played for a turn by computer
     */
    public Card play(Card card){

        if(isleader()){
            helpLeadCard();
        } else{
            helpChaseCard(card);
        }
        updateReason();
        return turnCard;
    }

    /**
     This function updates the reason after Computer has played a turn
     */
    public void updateReason(){
        String str = "Computer played " + turnCard.getFace() + turnCard.getSuit();
        play_reason = str + play_reason;
    }

    /**
     This function sets the turn card for computer player
     @param card  Card, the turn card for computer
     */
    public void setTurnCard(Card card){
        card = play(card);
    }

    /**
     This function returns melds shown by computer
     @return meldSelection, An array list containing cards declared for a meld
     */
    public ArrayList<Card> showMeld(){
        updateAvailableCardsForSelection();
        createAllPossibleMelds();
        String meldName = "";

        ArrayList<Card> meldSelection = new ArrayList<Card>();

        //Get melds by points
        for(String meld:meldsByPoints){
            if( allPossibleMelds.get(meld)!=null && allPossibleMelds.get(meld).size() != 0){
                meldSelection.addAll(allPossibleMelds.get(meld).get(0));
                meldName = meld;
                break;
            }
        }

        if(meldSelection.size()>0){
            play_reason = "The computer chose to show " + meldName + " because it is the best possible meld.";
            System.out.println(play_reason);
        }else{
            play_reason = "No melds available!!!";
            System.out.println(play_reason);
        }
        return meldSelection;
    }

    /**
     This function returns the turn recommendation for the computer player
     @param card Card, The card object that is a lead card if the player is chasing
     @return help String, that is a recommendation for the computer player
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
}
