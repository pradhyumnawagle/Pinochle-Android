/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/

package edu.ramapo.pwagle.pinochle.model;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Vector;

public class Game {

    //ArrayList of players
    private int numberOfRounds = 0;
    private ArrayList<Player> current_players = new ArrayList<Player>();
    private ArrayList<Card> current_deck = new ArrayList<Card>();
    private ArrayList<Card> allCards = new ArrayList<Card>();

    private Card trump_card = new Card();
    private String trump_suit = "";

    private Round current_round;

    /**
     This is the default constructor for the Game Class
     */
    public Game() {
    }

    /**
     This function initializes 2 players, creates a new deck and passes it to start a new Round.
     */
    public void createNewGame(){

        Player human = new Human();
        Player computer = new Computer();

        current_players.add(computer);
        current_players.add(human);

        Deck deck = new Deck();
        current_deck = deck.getNewDeck();
        current_round = new Round(current_players,current_deck);
    }

    /**
     This function creates a new Deck of cards for a new round but same game. Same players from previous round are used.
     @param players ArrayList<Player>, it is the array list containing current players of a game
     */
    public void newRound(ArrayList<Player> players){
        Deck deck = new Deck();
        current_deck = deck.getNewDeck();
        current_round = new Round(players,current_deck);
    }

    /**
     This function returns the number of rounds played in a game
      @return numberOfRounds, the number of rounds played in a game
     */
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     This function returns the current Round being played
     @return current_round, An object of class Round
     */
    public Round getCurrent_round() {
        return current_round;
    }

    /**
     This function returns the current deck being used
     @return current_deck, An object of class Deck
     */
    public ArrayList<Card> getCurrent_deck() {
        return current_round.getCurrent_deck();
    }

    /**
     This function returns an array list of current players
     @return current_players, An array list containing player objects
     */
    public ArrayList<Player> getCurrent_players() {
        return current_round.getCurrent_players();
    }

    /**
     This function returns the current Trump Card of the current round
     @return current_round.trump_Card, The current Trump Card
     */
    public Card getTrumpCard(){
        return current_round.getTrump_card();
    }


    /* *********************************************
    Source Code to save the game
    ********************************************* */
    /**
     This function evaluates the output to save when a game is saved
     @return output String, which contains all the data as a single string to be saved
     */
   public String saveGame(){

        String output = "";

        output  = "Round: " + numberOfRounds + "\n\n";

        for(Player player:current_players) {
            output = output + player.getName() + ":\n" +
                    "\tScore: " + player.getGame_score() + "/" + player.getRound_score() +
                    "\n\tHand: " + printCards(player.getCardsInHand()) + "\n\tCapture Pile: " + printCards(player.getCapture_pile()) +
                    "\n\tMelds: " + printMeldsPile(player.getMeld_pile()) + "\n\n";
        }

        output = output + "Trump Card: " + current_round.getTrump_card().getFace() + current_round.getTrump_card().getSuit();
        output = output + "\nStock: " + printCards(current_deck) + "\n\n";
        output = output + "Next player: " + getNextPlayer().getName() + "\n";

        return output;
    }

    /**
     This function creates a string of all cards passed in an array list
     @param cards ArrayList<Card>, array list containing all cards
     @return out, a String containing the face and suit of all cards
     */
    private String printCards(ArrayList<Card> cards){

        String out = "";

        for (Card card:cards){
            out = out + " " + card.getFace() + card.getSuit();
        }
        return out;
    }

    /**
     This function creates a string of all cards passed in an array list containing array lists
     @param cards ArrayList<ArrayList<Card>>, array list containing array lists containing meld cards
     @return out, a String containing the face and suit of all cards
     */
    private String printMeldsPile(ArrayList<ArrayList<Card>> cards){

        String out = "";

        for(ArrayList<Card> meld : cards){
            for(Card card:meld){
                if(card.moreThanOneActiveMeld()){
                    out = out + " " + card.getFace() + card.getSuit() + "*";
                }else {
                    out = out + " " + card.getFace() + card.getSuit();
                }
            }
            if(!cards.get(cards.size()-1).equals(meld)) {
                out = out + ",";
            }
        }

        return out;
    }

    /**
     This function returns the next player for the game if the player is a leader
     @return a player object, which is the next player for a turn
     */
    private Player getNextPlayer(){
        for(Player player:current_players){
            if(player.isleader()){
                return player;
            }
        }
        return new Player();
    }


     /* *********************************************
    Source Code to load a game
    ********************************************* */
    /**
     This function loads a game from the saved file and assigns all values needed for a game
     @param file, String which contains all the details from the file as a string
     */
    public void loadGame(String file){

        //Array lists for all input values
        ArrayList<String> hand = new ArrayList<String>();
        ArrayList<String> capture = new ArrayList<String>();
        ArrayList<String> melds = new ArrayList<String>();
        ArrayList<String> trumpCardVal = new ArrayList<String>();
        ArrayList<String> stockPile = new ArrayList<String>();
        ArrayList<String> score = new ArrayList<String>();
        String nextPlayer = "";
        int roundnum = 0;

        String[] lines = file.split("\n");

        //Read lines based on the values found
        for(String line:lines){
            if(line.contains("Round")){
                String str = parseValues("Round", line);
                str = str.trim();
                int r = Integer.parseInt(str);
                roundnum = r;
            } else if(line.contains("Hand")) {
                hand.add(parseValues("Hand", line));
            } else if(line.contains("Capture Pile")) {
                capture.add(parseValues("Capture Pile", line));
            }else if(line.contains("Melds")) {
                melds.add(parseValues("Melds", line));
            }else if(line.contains("Trump Card")) {
                trumpCardVal.add(parseValues("Trump Card", line));
            }else if(line.contains("Stock")) {
                stockPile.add(parseValues("Stock", line));
            }else if(line.contains("Score")) {
                score.add(parseValues("Score", line));
            }else if(line.contains("Next Player")) {
                nextPlayer = parseValues("Next Player:", line);
                nextPlayer = nextPlayer.trim();
            }
        }

        //Create new player objects
        Player human = new Human();
        Player computer = new Computer();

        current_players.add(computer);
        current_players.add(human);

        //Start a new round
        Round round = new Round();


        //Set trump card for the round
        setTrumpCardFromLoad(trumpCardVal.get(0));

        if( trumpCardVal.get(0).trim().length()==1){
            current_players.get(0).setTrump_suit(trump_suit);
            current_players.get(1).setTrump_suit(trump_suit);
            round.setTrump_suit(trump_suit);
        } else{
            current_players.get(0).setTrump_card(trump_card);
            current_players.get(1).setTrump_card(trump_card);
            round.setTrump_card(trump_card);
        }

        //Set capture pile and hand pile for both players
        for(int i=0;i<2;i++){
            if(hand.get(i)!=null && hand.get(i).length()>0){storePlayerCards(hand.get(i), current_players.get(i), "Hand");}
            if(capture.get(i)!=null && capture.get(i).length()>0){storePlayerCards(capture.get(i), current_players.get(i), "Capture");}
            updateLoadScores(score.get(i),current_players.get(i));
            if( melds.get(i)!=null && melds.get(i).length() > 0){storePlayerMelds(melds.get(i), current_players.get(i));}
            if(current_players.get(i).getName().equals(nextPlayer)){
                current_players.get(i).setleader(true);
            }
        }

        //Set current players for a round
        round.setCurrent_players(current_players);
        if(stockPile.get(0).trim().length()>0) {
            round.setCurrent_deck(getStockPileFromLoad(stockPile.get(0)));
        }
        round.setRound_number(roundnum);
        numberOfRounds = roundnum;

        //Assign round as the current round
        current_round = round;
    }

    /**
     To get the values from a line according to the key
     @param key, String on the basis of which the line is parsed
     @param line, String rom which values are parsed on the basis of key
     @return str String, which contains the parsed string
     */
    private String parseValues(String key, String line){

        String str = "";

        if(line.contains(key)){
            str = line.substring(line.lastIndexOf(":") + 1);
        }
        return str;
    }

    /**
     To store the cards for a player from string
     @param str, String containing all cards
     @param player, Player whose cards are stored from the string
     @param pile String, Players pile where the cards are stored
     */
    private void storePlayerCards(String str, Player player, String pile){
        str = str.trim();
        String[] cards = str.split(" ");
        ArrayList<Card> handCards = new ArrayList<Card>();

        //Create new cards, set values and add to the pile
        for(String cardStr:cards){
            Card card = new Card();
            card.setFace(Character.toString(cardStr.charAt(0)));
            card.setSuit(Character.toString(cardStr.charAt(1)));
            boolean firstOccur = true;
            for(Card singleCard:allCards){
                //Check if the car with same identifier has not been created before
                if(singleCard.getFace().equals(card.getFace()) && singleCard.getSuit().equals(card.getSuit())){
                    firstOccur = false;
                    break;
                }
            }
            if(firstOccur){
                card.setUnique_identifier("a");
            } else{
                card.setUnique_identifier("b");
            }

            allCards.add(card);
            handCards.add(card);
        }

        if(pile.equals("Hand")){
            player.setCardsInHand(handCards);
        } else if(pile.equals("Capture")){
            player.setCapture_pile(handCards);
        }

    }

    /**
     To update scores for a player from the load file
     @param str, String containing scores
     @param player, Player whose scores are updated
     */
    private void updateLoadScores(String str, Player player){
        String[] scores = str.split("/");

        for(String score:scores){
            score = score.trim();
        }

        int game_score = Integer.parseInt(scores[0].trim());
        int round_score = Integer.parseInt(scores[1].trim());

        player.setRound_score(round_score);
        player.setGame_score(game_score);
    }

    /**
     To set the meld pile received as string loaded from file
     @param str, String containing meld cards
     @param player, Player whose meld pile is updated
     */
    private void storePlayerMelds(String str, Player player){

        String[] allMelds = str.split(",");

        //Get each meld
        for(String eachMeld:allMelds){
            eachMeld = eachMeld.trim();

            String[] cards = eachMeld.split(" ");
            ArrayList<Card> cardsInMeld = new ArrayList<Card>();

            //Create cards and check if already created with similar values, if not create and assign
            for(String cardStr: cards){
                Card card = new Card();
                card.setFace(Character.toString(cardStr.charAt(0)));
                card.setSuit(Character.toString(cardStr.charAt(1)));
                boolean firstOccur = true;
                for(Card singleCard:allCards){
                    if(singleCard.getFace().equals(card.getFace()) && singleCard.getSuit().equals(card.getSuit())){
                        firstOccur = false;
                        break;
                    }
                }
                if(firstOccur){
                    card.setUnique_identifier("a");
                } else{
                    card.setUnique_identifier("b");
                }

                allCards.add(card);
                cardsInMeld.add(card);
            }

            player.updateCardMeldsMap(cardsInMeld);
            player.setCardSelectedForMeld(cardsInMeld);
            player.addCardsToMeldPile(cardsInMeld);
        }
    }

    /**
     To set the trump card received as string loaded from file
     @param str, String containing trump card
     */
    private void setTrumpCardFromLoad(String str){

        str= str.trim();
        Card trump = new Card();

        if(str.length() == 2){
            Card card = new Card();
            card.setFace(Character.toString(str.charAt(0)));
            card.setSuit(Character.toString(str.charAt(1)));
            boolean firstOccur = true;
            for(Card singleCard:allCards){
                if(singleCard.getFace().equals(card.getFace()) && singleCard.getSuit().equals(card.getSuit())){
                    firstOccur = false;
                    break;
                }
            }
            if(firstOccur){
                card.setUnique_identifier("a");
            } else{
                card.setUnique_identifier("b");
            }

            allCards.add(card);
            trump_card =  card;
        } else{
            trump_suit = Character.toString(str.charAt(0));
            trump_card.setSuit(trump_suit);
        }
    }

    /**
     To get the stock pile received as string loaded from file
     @param str, String containing cards in the stock pile
     @return stockCards ArrayList<Card>, which is an array list containing cards for the stock pile
     */
    private ArrayList<Card> getStockPileFromLoad(String str){
        str = str.trim();
        String[] cards = str.split(" ");
        ArrayList<Card> stockCards = new ArrayList<Card>();

        for(String cardStr:cards){
            Card card = new Card();
            card.setFace(Character.toString(cardStr.charAt(0)));
            card.setSuit(Character.toString(cardStr.charAt(1)));
            boolean firstOccur = true;
            for(Card singleCard:allCards){
                if(singleCard.getFace().equals(card.getFace()) && singleCard.getSuit().equals(card.getSuit())){
                    firstOccur = false;
                    break;
                }
            }
            if(firstOccur){
                card.setUnique_identifier("a");
            } else{
                card.setUnique_identifier("b");
            }

            allCards.add(card);
            stockCards.add(card);
        }
        return stockCards;
    }

    /**
     To get the details to print when a round has ended
     @return details, String, which contains all details to print as a single string
     */
    public String getEndRoundDetails(){

        String details = "Round number: " + numberOfRounds + "\n\n\t";
        details = details + current_players.get(0).getName() + ": " +  current_players.get(0).getRound_score() +
                            "\n\t" + current_players.get(1).getName() + ": " +  current_players.get(1).getRound_score();


        if(current_players.get(0).getRound_score() > current_players.get(1).getRound_score()){
            details = details + "\n\n Winner is: " + current_players.get(0).getName();
        }else{
            details = details + "\n\n Winner is: " + current_players.get(1).getName();
        }
        for(Player player:current_players){
            player.addGameScore(player.getRound_score());
        }
        return details;
    }

    /**
     To get the details to print when a game has ended
     @return details, String, which contains all details to print as a single string
     */
    public String getEndGameDetails(){

        String details = "Game ended!!!" + "\n\n\t";
        details = details + current_players.get(0).getName() + ": " +  current_players.get(0).getGame_score() +
                "\n\t" + current_players.get(1).getName() + ": " +  current_players.get(1).getGame_score();


        if(current_players.get(0).getGame_score() > current_players.get(1).getGame_score()){
            details = details + "\n\n Winner is: " + current_players.get(0).getName();
        }else{
            details = details + "\n\n Winner is: " + current_players.get(1).getName();
        }
        return details;
    }

     /* *********************************************
    Source Code to start a new round
    ********************************************* */
    /**
     To get the index of the round winner who starts new round
     @return int, 1 if winner has been assigned, -1 if points are tied and toss is needed.
     */
    public int nextGameWinner(){
        if(current_players.get(0).getRound_score() > current_players.get(1).getRound_score()){
            current_players.get(0).setleader(true);
            current_players.get(1).setleader(false);
            current_players.get(0).setTurn(true);
            current_players.get(1).setTurn(false);
            return 1;
        } else if(current_players.get(0).getRound_score() < current_players.get(1).getRound_score()){
            current_players.get(1).setleader(true);
            current_players.get(0).setleader(false);
            current_players.get(0).setTurn(false);
            current_players.get(1).setTurn(true);
            return 1;
        } else{
            return -1;
        }
    }

    /**
     To start a new round with current players
     */
    public void newRound(){
        numberOfRounds+=1;
        newRound(current_players);
    }

}
