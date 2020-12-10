/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/

package edu.ramapo.pwagle.pinochle.model;

import java.util.ArrayList;

public class Round {

    private ArrayList<Player> current_players = new ArrayList<Player>();
    private ArrayList<Card> current_deck = new ArrayList<Card>();
    private Card trump_card = new Card();
    private Card turn_card;
    private String trump_suit;
    private int round_number = 0;
    private boolean round_ended = false;

    /**
    This is the default constructor for the Game Class
     */
    public Round(){

    }

    /**
     This is the parameterized constructor for the Card Class
     @param current_players ArrayList<Player>, array list containing players for the round
     @param current_deck ArrayList<Card>, array list containing the cards for the round
     */
    public Round(ArrayList<Player> current_players, ArrayList<Card> current_deck) {
        this.current_players = current_players;
        for(Player player:current_players){
            player.clearCards();
        }
        this.current_deck = current_deck;
        round_number += 1;
        distributeCards();
    }

    /**
     This function returns the array list containing the players
     @return current_players, an array list containing players
     */
    public ArrayList<Player> getCurrent_players() {
        return current_players;
    }

    /**
     This function sets the current players of the round
     @param current_players ArrayList<Player>, Array list containing the players
     */
    public void setCurrent_players(ArrayList<Player> current_players) {
        this.current_players = current_players;
    }

    /**
     This function sets the number of the round
     @param round_number int, The number of current round
     */
    public void setRound_number(int round_number) {
        this.round_number = round_number;
    }

    /**
     This function returns the array list containing cards in the stock pile
     @return current_deck, an array list containing cards
     */
    public ArrayList<Card> getCurrent_deck() {
        return current_deck;
    }

    /**
     This function sets the current deck
     @param current_deck ArrayList<Card>, An array list containing cards
     */
    public void setCurrent_deck(ArrayList<Card> current_deck) {
        this.current_deck = current_deck;
    }

    /**
     This function returns the trump card of the current round
     @return trump_card
     */
    public Card getTrump_card() {
        return trump_card;
    }

    /**
     This function sets the trump card of the current round
     @param trump_card Card, A card object
     */
    public void setTrump_card(Card trump_card) {
        this.trump_card = trump_card;
    }

    /**
     This function sets the trump suit of the current round
     @param trump_suit String, value of the trump suit
     */
    public void setTrump_suit(String trump_suit) {
        this.trump_suit = trump_suit;
        trump_card.setSuit(trump_suit);
    }

    /**
     This function sets the turn card card of the current turn
     @param turn_card Card, A card object
     */
    public void setTurn_card(Card turn_card) {
        this.turn_card = turn_card;
    }

    /**
     This function returns the turn card of the current turn
     @return turn_card, a card object
     */
    public Card getTurn_card() {
        return turn_card;
    }

    /**
     This function returns the value of round_ended member variable
     @return round_ended, a boolean which is true if round has ended
     */
    public boolean isRound_ended() {
        return round_ended;
    }

    /**
     This function sets the round_ended variable for the round
     @param round_ended Boolean, A boolean value
     */
    public void setRound_ended(boolean round_ended) {
        this.round_ended = round_ended;
    }

    /**
     This function distributes cards to players
     */
    public void distributeCards(){

        int currReceiver = 1;
        Card currentCard;

        //Distribute 4 cards at a time from current deck to players until
        //both players have 12 cards
        for(int i=0;i<6;i++){
            if(1==currReceiver){
               for(int j=0;j<4;j++){
                   currentCard = current_deck.get(0);
                   current_players.get(0).addCardToHand(currentCard);
                   current_deck.remove(0);
               }
               currReceiver = 2;
            }
            else if(2==currReceiver){
                for(int j=0;j<4;j++){
                    currentCard = current_deck.get(0);
                    current_players.get(1).addCardToHand(currentCard);
                    current_deck.remove(0);
                }
                currReceiver = 1;
            }
        }
        //Set the next card from current deck as trump card
        trump_card = current_deck.get(0);
        current_deck.remove(0);

        //Update hand pile and trump card for both players
        for(int i=0;i<2;i++){
            current_players.get(i).updateAvailableCardsForSelection();
            current_players.get(i).setTrump_card(trump_card);
        }
    }


    /**
     This function evaluates lead and chase card for a turn and the turn winner
     @return Player object of the winning player
     */
    public Player selectTurnWinner(){
        int playerIndex,leadIndex=0,chaseIndex=0;
        Card leadCard = new Card();
        Card chaseCard = new Card();


        //Get the index of lead player, lead card and chase card for evaluation
        for(playerIndex=0;playerIndex<current_players.size();playerIndex++){
           if(current_players.get(playerIndex).isleader()){
               leadCard = current_players.get(playerIndex).play(leadCard);
               leadIndex = playerIndex;
               break;
           }
        }

        if(playerIndex == 0){
            chaseCard = current_players.get(1).play(leadCard);
            chaseIndex = 1;
        }
        else {
            chaseCard = current_players.get(0).play(leadCard);
            chaseIndex = 0;
        }


        //Evaluate wining card and winning player
        if(turnWinnerCard(leadCard,chaseCard).equals("chase")){
            System.out.println("Here Chase: " + chaseIndex + leadIndex);

            current_players.get(chaseIndex).setleader(true);
            current_players.get(chaseIndex).setTurn(true);//------
            current_players.get(leadIndex).setleader(false);
        } else{
            System.out.println("Here");
            current_players.get(chaseIndex).setleader(false);
            current_players.get(leadIndex).setleader(true);
            current_players.get(leadIndex).setTurn(true);//------
        }


        //Update cards pile for the winning player
        for(playerIndex=0;playerIndex<current_players.size();playerIndex++){
            if(current_players.get(playerIndex).isleader()){
                current_players.get(playerIndex).addCardToCapturePile(leadCard);
                current_players.get(playerIndex).addRoundScore(leadCard.getPoints_worth());
                current_players.get(playerIndex).addCardToCapturePile(chaseCard);
                current_players.get(playerIndex).addRoundScore(chaseCard.getPoints_worth());
                break;
            }
        }

        //If card from hand, remove from hand, else remove from meld and move associated cards back to hand
        if(current_players.get(leadIndex).getCardsInHand().contains(leadCard)) {
            current_players.get(leadIndex).removeCardfromHandPile(leadCard);
        } else {
            //Remove cards from meld pile
            current_players.get(leadIndex).replaceCorrespondingCards(leadCard);
        }

        if(current_players.get(chaseIndex).getCardsInHand().contains(chaseCard)) {
            current_players.get(chaseIndex).removeCardfromHandPile(chaseCard);
        } else{
            //Remove cards from meld pile
            current_players.get(chaseIndex).replaceCorrespondingCards(chaseCard);
        }

        if(current_players.get(0).getCardsInHand().isEmpty() && current_players.get(1).getCardsInHand().isEmpty()){
            round_ended = true;
        }

        //Return the winning player
        return current_players.get(playerIndex);
    }

    /**
     This function evaluates lead and chase card for a turn and returns the winning card's name
     @param leadCard Card, the lead card for the turn
     @param chaseCard Card, the chase card for the turn
     @return String lead if lead card wins, chase if otherwise
     */
    private String turnWinnerCard(Card leadCard, Card chaseCard){

        if(leadCard.getSuit().equals(trump_card.getSuit())){
            //if chase card also trump
            if(chaseCard.getSuit().equals(trump_card.getSuit())){
                //if same cards
                if(chaseCard.getFace().equals(leadCard.getFace())){
                    return "lead";
                } else{
                    //if both not same, greater card wins
                    if(getCardPrecedence(leadCard.getFace()) > getCardPrecedence(chaseCard.getFace())){
                        return "lead";
                    } else{
                        return "chase";
                    }
                }
            }
            //If lead card not trump
        } else{
            if(chaseCard.getSuit().equals(trump_card.getSuit())){
                return "chase";
            }
            //both not trump but same suit
            else if(chaseCard.getSuit().equals(leadCard.getSuit())){
                //if same card lead wins
                if(chaseCard.getFace().equals(leadCard.getFace())){
                    return "lead";
                    //if not same cards
                } else{
                    if(getCardPrecedence(leadCard.getFace()) > getCardPrecedence(chaseCard.getFace())){
                        return "lead";
                    } else{
                        return "chase";
                    }
                }
            }
        }
        return "lead";
    }

    /**
     This function returns integer value as precedence of card
     @param cardFace String, The face of the card
     @return Integer value by precedence of the card
     */
    private int getCardPrecedence(String cardFace){
        if ("9".equals(cardFace)) { return 1; }
        else if("J".equals(cardFace)) { return 2; }
        else if ("Q".equals(cardFace)) { return 3; }
        else if ("K".equals(cardFace)) { return 4; }
        else if ("X".equals(cardFace)) { return 5; }
        else if ("A".equals(cardFace)) { return 6; }

        return -1;
    }

    /**
     This function evaluates if a meld is valid or not
     @param cards ArrayList<Card>, Array list containing cards in a meld
     @return Boolean true if melds are valid and maps are updated, false if not
     */
    public boolean evaluateMeld(ArrayList<Card> cards){
        int leaderIndex = 0;
        //Get the leader index
        for(int i=0;i<current_players.size();i++){
            if(current_players.get(i).isleader()){
                leaderIndex = i;
                break;
            }
        }
        if(cards.size()!=0){
            //If valid meld which includes meld check, each card's map check and at least one new card for a meld
            if(current_players.get(leaderIndex).validateMeld(cards, current_players.get(leaderIndex).getMeldName(cards))
                    && current_players.get(leaderIndex).atLeastOneNewCard(cards) && !current_players.get(leaderIndex).getMeldName(cards).equals("invalid")){


                current_players.get(leaderIndex).setCardSelectedForMeld(cards);
                //Update cards for melds i.e. each card's map
                current_players.get(leaderIndex).updateCardMeldsMap(cards);
                current_players.get(leaderIndex).addCardsToMeldPile(cards);

                //Update reason for playing meld
                if(current_players.get(leaderIndex).getName().equals("Human")){
                    current_players.get(leaderIndex).updateMeldReason(current_players.get(leaderIndex).getMeldName(cards));
                }

                //Update points
                int leaderPoints = current_players.get(leaderIndex).getMeldPoints(current_players.get(leaderIndex).getMeldName(cards));
                current_players.get(leaderIndex).addRoundScore(leaderPoints);

                return true;
            }
            else{
                return false;
            }
        }
        return true;
    }

    /**
     This function updates the stock pile after each turn
     */
    public void updateStockPile(){
        int leaderIndex = 0;
        //Get the leader index
        for(int i=0;i<current_players.size();i++){
            if(current_players.get(i).isleader()){
                leaderIndex = i;
                break;
            }
        }

        //Leader gets card from top of stick pile
        if(!current_deck.isEmpty()) {
            current_players.get(leaderIndex).addCardToHand(current_deck.get(0));
            current_deck.remove(0);
        }

        int chaserIndex = 0;
        if(leaderIndex == 0){
            chaserIndex = 1;
        } else{
            chaserIndex = 0;
        }

        //If stock empty, give trump card and store trump suit
        if(current_deck.isEmpty()){
            if(!trump_card.getFace().equals("")) {
                Card newCard = new Card();
                newCard.setFace(trump_card.getFace());
                newCard.setSuit(trump_card.getSuit());
                newCard.setUnique_identifier(trump_card.getUnique_identifier());
                current_players.get(chaserIndex).addCardToHand(newCard);
                trump_card.setFace("");
            }
        } else {
            current_players.get(chaserIndex).addCardToHand(current_deck.get(0));
            current_deck.remove(0);
        }
    }

    /**
     This function creates all possible melds for a player
     @param player Player, a player whose melds are created
     */
    public void createAllMelds(Player player){
        player.updateAvailableCardsForSelection();
        player.createAllPossibleMelds();
    }

}
