/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/

package edu.ramapo.pwagle.pinochle.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Player {

    protected ArrayList<Card> cards_in_hand = new ArrayList<Card>();
    protected ArrayList<Card> capture_pile = new ArrayList<Card>();
    protected ArrayList<Card> available_cards_for_selection = new ArrayList<Card>();

    protected ArrayList<ArrayList<Card>> meld_pile = new ArrayList<ArrayList<Card>>();
    protected HashMap<String, ArrayList<ArrayList<Card>>> allPossibleMelds = new HashMap<String, ArrayList<ArrayList<Card>>>();

    protected boolean leader = false;
    protected boolean turn = false;
    protected Card turnCard;
    public Card trump_card = new Card();
    protected String trump_suit;
    protected String name;
    protected String play_reason = "";

    protected int round_score = 0;
    protected int game_score = 0;

    protected String[] meldsByPoints = { "flush","four Aces","four Kings", "four Queens" ,"royal marriage",
            "four Jacks", "pinochle", "marriage", "dix" };

    /**
     This is the default constructor for the Player Class
     */
    public Player() {

    }

    /**
     This is the parameterized constructor for the Card Class
     @param name String, the name of the player
     @param cards_in_hand ArrayList<Card>, cards in hand of the player

     */
    public Player(String name, ArrayList<Card> cards_in_hand) {
        this.name = name;
        this.cards_in_hand = cards_in_hand;
    }

    /**
     This function returns the name of the player
     @return mame, the string name of the card
     */
    public String getName() {
        return name;
    }

    /**
     This function sets the name for the player
     @param name String, the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     This function returns the turn card the player has played
     @param card Card, The card object that is a lead card if the player is chasing
     @return turnCard Card, the card played for a turn by the player
     */
    public Card play(Card card){
        return turnCard;
    }

    /**
     This function returns melds shown by the player
     @return An array list containing cards declared for a meld
     */
    public ArrayList<Card> showMeld(){
        return new ArrayList<Card>();
    }

    /**
     This function sets the trump suit
     @param trump_suit String, the trump suit value
     */
    public void setTrump_suit(String trump_suit) {
        this.trump_suit = trump_suit;
        trump_card.setSuit(trump_suit);
    }

    /**
     This function returns the cards in hand of the player
     @return cards_in_hand ArrayList<Card>, the cards in hand of the player
     */
    public ArrayList<Card> getCardsInHand() {
        return cards_in_hand;
    }

    /**
     This function sets the cards in hand of the player
     @param cardsInHand ArrayList<Card>, array list containing cards
     */
    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cards_in_hand = cardsInHand;
    }

    /**
     This function adds a card to the cards in hand of the player
     @param card Card, the card to add
     */
    public void addCardToHand(Card card){
        cards_in_hand.add(card);
    }

    /**
     This function adds a card to the capture pile of the player
     @param card Card, the card to add
     */
    public void addCardToCapturePile(Card card){capture_pile.add(card);}

    /**
     This function adds a card to the melds  of the player
     @param cards ArrayList<Card>, the meld to add
     */
    public void addCardsToMeldPile(ArrayList<Card> cards){
        String meldName = getMeldName(cards);

        ArrayList<Card> cardsMelds = new ArrayList<Card>();
        cardsMelds.addAll(cards);
        meld_pile.add(cardsMelds);

       for(Card card:cards){
            if(cards_in_hand.contains(card)){
                cards_in_hand.remove(card);
            }
        }

    }

    /**
     This function returns the reason after a player plays a turn
     @return play_reason String, the reason for the move
     */
    public String getPlay_reason() {
        return play_reason;
    }

    /**
     This function updates the reason after the player has played a turn
     */
    public void updateReason(){}

    /**
     This function updates the reason after a player has declared a meld
     */
    public void updateMeldReason(String meldName){}

    /**
     This function returns the capture pile of the player
     @return capture_pile ArrayList<Card>, the capture pile of the player
     */
    public ArrayList<Card> getCapture_pile() {
        return capture_pile;
    }

    /**
     This function sets the capture pile of the player
     @param capture_pile ArrayList<Card>, array list containing cards
     */
    public void setCapture_pile(ArrayList<Card> capture_pile) {
        this.capture_pile = capture_pile;
    }

    /**
     This function clears the all the cards a player has
     */
    public void clearCards(){
        round_score = 0;
        capture_pile.clear();
        cards_in_hand.clear();
        meld_pile.clear();
    }

    /**
     This function returns the melds of the player
     @return meld_pile ArrayList<ArrayList<Card>>, the meld_pile of the player
     */
    public ArrayList<ArrayList<Card>> getMeld_pile() {
        return meld_pile;
    }

    /**
     This function sets the meld pile of the player
     @param meld_pile ArrayList<ArrayList<Card>>, array list containing array list containing cards
     */
    public void setMeld_pile(ArrayList<ArrayList<Card>> meld_pile) {
        this.meld_pile = meld_pile;
    }

    /**
     This function returns the value of boolean leader
     @return leader boolean
     */
    public boolean isleader() {
        return leader;
    }

    /**
     This function sets the value of boolean leader
     @param is_leader boolean
     */
    public void setleader(boolean is_leader) {
        this.leader = is_leader;
    }

    /**
     This function updates all cards available for selection for a player
     */
    public void updateAvailableCardsForSelection(){

        ArrayList<Card> aList = new ArrayList<Card>();
                aList.addAll(cards_in_hand);
                available_cards_for_selection = aList;

        for (ArrayList<Card> cards : meld_pile) {
            for (Card card : cards) {
                if (!available_cards_for_selection.contains(card)) {
                    available_cards_for_selection.add(card);
                }
            }
        }

    }

    /**
     This function returns the available cards for selection of the player
     @return available_cards_for_selection ArrayList<Card>
     */
    public ArrayList<Card> getAvailable_cards_for_selection() {
        return available_cards_for_selection;
    }

    /**
     This function returns the turn card of the player
     @return turnCard Card
     */
    public Card getTurnCard() {
        return turnCard;
    }

    /**
     This function sets the turn card of the player
     @param turnCard Card, the turn card player by the player
     */
    public void setTurnCard(Card turnCard) {
        this.turnCard = turnCard;
    }

    /**
     This function returns the value of boolean turn
     @return leader turn
     */
    public boolean isTurn() {
        return turn;
    }

    /**
     This function sets the value of boolean turn
     @param turn boolean, the boolean value of the turn variable
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     This function removes a card from the hand of a player
     @param card Card, the card to delete from hand
     */
    public void removeCardfromHandPile(Card card){
        cards_in_hand.remove(card);
    }

    /**
     This function returns the round score of the player
     @return round_score int
     */
    public int getRound_score() {
        return round_score;
    }

    /**
     This function sets the round score of the player
     @param round_score int, the round score of the player
     */
    public void setRound_score(int round_score) {
        this.round_score = round_score;
    }

    /**
     This function returns the game score of the player
     @return game_score int
     */
    public int getGame_score() {
        return game_score;
    }

    /**
     This function sets the game score of the player
     @param game_score int, the game score of the player
     */
    public void setGame_score(int game_score) {
        this.game_score = game_score;
    }

    /**
     This function adds the round score of the player
     @param score int, the turn score of the player to add to round score
     */
    public void addRoundScore(int score){
        round_score += score;
    }

    /**
     This function sets the trump card
     @param trump_card Card, the trump card
     */
    public void setTrump_card(Card trump_card) {
        this.trump_card = trump_card;
    }

    /**
     This function adds the game score of the player
     @param score int, the round score of the player to add to game score
     */
    public void addGameScore(int score){
        game_score += score;
    }

    /**
     This function checks if the meld is valid
     @param cards ArrayList<Card>, the list of cards in a meld to validate
     @param meldName String, the name of the meld
     @return boolean value true if meld is valid and false otherwise
     */
    public boolean validateMeld(ArrayList<Card> cards, String meldName){

        for(int i=0;i<cards.size();i++){
            if(cards.get(i).meldExists(meldName)){
                return false;
            }
        }
        return true;
    }

    /**
     This function checks if the meld has at least one new card which is not in meld pile
     @param cards ArrayList<Card>, the list of cards in a meld to validate
     @return boolean value true if meld is valid and false otherwise
     */
    public boolean atLeastOneNewCard(ArrayList<Card> cards){
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).noOldMelds()){
                return true;
            }
        }
        return false;
    }

    /**
     This function sets if the card has been used in a meld
     @param cards ArrayList<Card>, the list of cards in a meld to validate
     */
    public void setCardSelectedForMeld(ArrayList<Card> cards){
        for(int i=0;i<cards.size();i++){
            cards.get(i).setAt_least_one_meld(true);
        }
    }

    /**
     This function gets the name of the meld
     @param cards ArrayList<Card>, the list of cards in a meld
     @return meldname String, the name of the melds formed by cards
     */
    public String getMeldName(ArrayList<Card> cards){
        switch(cards.size()){

            case 5:
                //Check if Ace, Ten, King, Queen and Jack-------That means no nine---------------------------
                if (!hasDuplicates(cards, "face") && sameSuitCards(cards) && !containsNine(cards) && trump_card.getSuit().equals(cards.get(0).getSuit()) ) {
                return "flush";
                }
                break;
            case 4:
                //unique suites and same faced cards
                if (!hasDuplicates(cards, "suit") && sameFacedCards(cards)) {
			        if ("A".equals(cards.get(0).getFace())) { return "four Aces"; }
			        else if ("K".equals(cards.get(0).getFace())) { return "four Kings"; }
			        else if ("Q".equals(cards.get(0).getFace())) { return "four Queens"; }
			        else if ("J".equals(cards.get(0).getFace())) { return "four Jacks"; }
		        }
                break;
            case 2:
                if (isPinochle(cards)) { return "pinochle"; }
		        else {
			        if (isMarriage(cards)) {
				        if (trump_card.getSuit().equals(cards.get(0).getSuit()) ) { return "royal marriage"; }
				        else {
                            System.out.println("Biya bhako bhako!!");
					        return "marriage";
				        }
			        }
		        }
                break;
            case 1:
                if(cards.get(0).getFace().equals("9") && cards.get(0).getSuit().equals(trump_card.getSuit())){
                    return "dix";
                }
                break;
        }
        System.out.println("Invalid pugeko");
        return "invalid";
    }

    /**
     This function helps to know if the cards have duplicates based on the string 'basis'
     @param cards ArrayList<Card>, the list of cards in a meld
     @param basis String, the string based on which duplicates is searched
     @return boolean true if duplicates are found, if not then false
     */
    private boolean hasDuplicates(ArrayList<Card> cards, String basis){

        for(int i=0;i<cards.size();i++){
            for(int j=i+1;j<cards.size();j++){
                if(basis.equals("suit")){
                    if(cards.get(i).getSuit().equals(cards.get(j).getSuit())){
                        return true;
                    }
                }
                else if(basis.equals("face")){
                    if(cards.get(i).getFace().equals(cards.get(j).getFace())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     This function helps to know if the cards are of the same suit
     @param cards ArrayList<Card>, the list of cards
     @return boolean true if all cards have same suit, false otherwise
     */
    private boolean sameSuitCards(ArrayList<Card> cards){

        String suit = cards.get(0).getSuit();
        for(int i=0;i<cards.size();i++){
            if(!suit.equals(cards.get(i).getSuit())){
                return false;
            }
        }
        return true;
    }

    /**
     This function helps to know if the cards have the same face
     @param cards ArrayList<Card>, the list of cards
     @return boolean true if all cards have same face, false otherwise
     */
    private boolean sameFacedCards(ArrayList<Card> cards){
        String face = cards.get(0).getFace();
        for(int i=0;i<cards.size();i++){
            if(!face.equals(cards.get(i).getFace())){
                return false;
            }
        }
        return true;
    }

    /**
     This function helps to know if the cards contain a 9
     @param cards ArrayList<Card>, the list of cards
     @return boolean true if all cards are 9, if not then false
     */
    private boolean containsNine(ArrayList<Card> cards){
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).getFace().equals("9")){
                return true;
            }
        }
        return false;
    }

    /**
     This function helps to know if the cards make Pinochle meld
     @param cards ArrayList<Card>, the list of cards
     @return boolean true if all cards make Pinochle, if not then false
     */
    private boolean isPinochle(ArrayList<Card> cards){

        if(cards.size() > 2){return false;}

        int queenOfSpadesCount = 0, jackOfDiamondCount = 0;

        for(int i=0;i<cards.size();i++){
            if("Q".equals(cards.get(i).getFace()) && "S".equals(cards.get(i).getSuit())){
                queenOfSpadesCount += 1;
            } else if("J".equals(cards.get(i).getFace()) && "D".equals(cards.get(i).getSuit())){
                jackOfDiamondCount += 1;
            }
        }
        if (1 == queenOfSpadesCount && 1 == jackOfDiamondCount) {
            return true;
        }
        return false;
    }

    /**
     This function helps to know if the cards make Marriage meld
     @param cards ArrayList<Card>, the list of cards
     @return boolean true if all cards make marriage, if not then false
     */
    private boolean isMarriage(ArrayList<Card> cards){

        if(cards.size() > 2){return false;}
        int kingCount = 0, queenCount = 0;
        String suit = cards.get(0).getSuit();

        for(int i=0;i<cards.size();i++){
            if("K".equals(cards.get(i).getFace())){
                kingCount += 1;
            } else if("Q".equals(cards.get(i).getFace())){
                queenCount += 1;
            }
            if(!suit.equals(cards.get(i).getSuit())){
                return false;
            }
        }
        if (1 == kingCount && 1 == queenCount) {
            return true;
        }
        return false;
    }

    /**
     This function gets the points by the name of meld
     @param meldName String, the name of the meld to return corresponding points
     @return integer value that is the point for the meld
     */
    public int getMeldPoints(String meldName){
        if ("flush" == meldName) { return 150; }
        else if ("four Aces" == meldName) { return 100; }
        else if ("four Kings" == meldName) { return 80; }
        else if ("four Queens" == meldName) { return 60; }
        else if ("four Jacks" == meldName) { return 40; }
        else if ("pinochle" == meldName) { return 40; }
        else if ("royal marriage" == meldName) { return 40; }
        else if ("marriage" == meldName) { return 20; }
        else if ("dix" == meldName) { return 10; }
        else { return 0; }
    }

    /**
     This function updates the meld map of all cards in a meld
     @param cards ArrayList<Card>, the list of cards in a meld
     */
    public void updateCardMeldsMap(ArrayList<Card> cards){

        String meldName = getMeldName(cards);
        if(meldName.equals("dix")){
            ArrayList<Card> meldCards = new ArrayList<Card>();
            meldCards.addAll(cards);
            cards.get(0).setMeldInMap(meldName,meldCards);
        } else{
            for(Card card:cards){
                ArrayList<Card> meldCards = new ArrayList<Card>();
                for(Card innerCard:cards){
                    if(card!=innerCard){
                        meldCards.add(innerCard);
                    }
                }
                card.setMeldInMap(meldName,meldCards);
            }
        }
    }

    /**
     This function deletes a meld from meld pile once card is shown in turn and
        replace corresponding cards in melds back to hand
     @param card Card, The card that is a turn card
     */
    public void replaceCorrespondingCards(Card card){

        HashMap<String, ArrayList<Card>> correspondingCards = new HashMap<String, ArrayList<Card>>();
        correspondingCards = card.getMelds();

        ArrayList<String> meldsUsed = new ArrayList<String>();

        //Put corresponding cards back to hand if not in more than one active meld
        for(HashMap.Entry<String,ArrayList<Card>> entry: correspondingCards.entrySet()){
            ArrayList<Card> corrCards = new ArrayList<Card>();
            corrCards = entry.getValue();
            for(Card eachCard:corrCards){
                if(!moreThanOneActiveMeld(eachCard) && !((card.getFace().equals("9")) && (card.getSuit().equals(trump_card.getSuit())))){
                    for(ArrayList<Card> currMeld:meld_pile){
                        //THis if statement and the one above it added.
                        if(currMeld.contains(eachCard)){
                            if(!cards_in_hand.contains(eachCard)) {
                                cards_in_hand.add(eachCard);
                            }
                        }
                    }
                }
            }
            meldsUsed.add(entry.getKey());
        }

        //Remove the meld from the card's map
        for(String meld:meldsUsed){
            card.removeMeld(meld);
        }

        //Get the index for melds to delete
        int index = 0;
        ArrayList<Integer> pos = new ArrayList<Integer>();
        for(int i=0;i<meld_pile.size();i++){
            if(meld_pile.get(i).contains(card)){
                pos.add(index);
            }
            index += 1;
        }

        //Remove melds from meld pile
        Collections.sort(pos);
        for(int i=pos.size()-1;i>=0;i--){
            meld_pile.remove((int)pos.get(i));
        }

    }

    /**
     This function checks if a card is involved in more than one active meld in the meld pile
     @param card Card, The card which is checked if it exists in more than one active meld
     @return boolean true if more than one active meld, false if otherwise
     */
    private boolean moreThanOneActiveMeld(Card card){
        HashMap<String, ArrayList<Card>> melds = new HashMap<String, ArrayList<Card>>();
        melds = card.getMelds();

        int activeMelds = 0;
        ArrayList<Card> tempCards = new ArrayList<Card>();

        for(HashMap.Entry<String,ArrayList<Card>> entry: melds.entrySet()){
            ArrayList<Card> corrCards = new ArrayList<Card>();
            corrCards = entry.getValue();

            for(Card eachCard:corrCards){
                if(searchInMeldsMap(card, eachCard)){
                    if(searchCardInMeldPile(eachCard)) {
                        if(!tempCards.contains(eachCard)){
                            tempCards.add(eachCard);
                            break;
                        }
                       // activeMelds += 1;
                    }
                }
            }
        }

        if(tempCards.size() >= 2){
            return true;
        }
        return false;

    }

    /**
     This function searches for a card in the melds pile
     @param card Card, The card which is checked if it exists in meld pile
     @return boolean true if card exists in meld pile, false if otherwise
     */
    private boolean searchCardInMeldPile(Card card){
        for(ArrayList<Card> currMeld:meld_pile){
            if(currMeld.contains(card)){
               return true;
            }
        }
        return false;
    }

    /**
     This function checks if a card is present in the melds map of secondary card
     @param mainCard Card, The card which is which is searched
     @param secondaryCard Card, The card whose melds map is searched
     @return boolean true if card exists in meld pile, false if otherwise
     */
    public boolean searchInMeldsMap(Card mainCard, Card secondaryCard){
        HashMap<String, ArrayList<Card>> melds = new HashMap<String, ArrayList<Card>>();
        melds = secondaryCard.getMelds();

        for(HashMap.Entry<String,ArrayList<Card>> entry: melds.entrySet()){
            ArrayList<Card> corrCards = new ArrayList<Card>();
            corrCards = entry.getValue();

            for(Card eachCard: corrCards){
                if(eachCard.equals(mainCard)){
                    return true;
                }
            }
        }
        return false;

    }

    /**
     This function prints all possible melds
     */
    public void printAllPossibleMelds(){
        for(HashMap.Entry<String,ArrayList<ArrayList<Card>>> entry: allPossibleMelds.entrySet()){
            System.out.println(entry.getKey());
            for(ArrayList<Card> melds:entry.getValue()){
                System.out.println(melds);
            }
            System.out.println("----------------------");
        }
    }

    /**
     This function creates all possible melds from a player's cards and store in the class
        member allPossibleMelds
     */
    public void createAllPossibleMelds(){

        allPossibleMelds.clear();

        HashMap<String, ArrayList<Card>> cardsBySuits = new HashMap<String, ArrayList<Card>>();
        HashMap<String, ArrayList<Card>> cardsByFace = new HashMap<String, ArrayList<Card>>();

        //Separate cards by suit
        for(Card card:available_cards_for_selection){
            if("H".equals(card.getSuit())){
                insertIntoMap(cardsBySuits,"H",card);
            }else if("D".equals(card.getSuit())){
                insertIntoMap(cardsBySuits,"D",card);
            } else if("C".equals(card.getSuit())){
                insertIntoMap(cardsBySuits,"C",card);
            } else if("S".equals(card.getSuit())){
                insertIntoMap(cardsBySuits,"S",card);
            }
        }

        //Separate cards by face
        for(Card card:available_cards_for_selection){
            if("A".equals(card.getFace())){
                insertIntoMap(cardsByFace,"A",card);
            }else if("K".equals(card.getFace())){
                insertIntoMap(cardsByFace,"K",card);
            } else if("Q".equals(card.getFace())){
                insertIntoMap(cardsByFace,"Q",card);
            } else if("J".equals(card.getFace())){
                insertIntoMap(cardsByFace,"J",card);
            }
        }

        ArrayList<Card> trumpSuitCards = new ArrayList<Card>();
        trumpSuitCards = (cardsBySuits.get(trump_card.getSuit()));

        //Check flush and add all possible melds to allMelds map
        if(trumpSuitCards!=null && trumpSuitCards.size()>=5) {
            allPossibleMelds.put("flush", checkFlush(trumpSuitCards));
        }
        //Check Royal Marriage and add all possible melds to allMelds map
        if(trumpSuitCards!=null && trumpSuitCards.size()>=2) {
            allPossibleMelds.put("royal marriage", checkRoyalMarriage(trumpSuitCards));
        }
        //Check Pinochle and add all possible melds to allMelds map
        allPossibleMelds.put("pinochle", checkPinochle(available_cards_for_selection));


        //Get all possible marriage melds
        ArrayList<ArrayList<Card>> marriageMelds = new ArrayList<ArrayList<Card>>();
        for(HashMap.Entry<String,ArrayList<Card>> entry: cardsBySuits.entrySet()){
            if(!entry.getKey().equals(trump_card.getSuit())){
                ArrayList<ArrayList<Card>> mM = new ArrayList<ArrayList<Card>>();
                mM = checkMarriage(entry.getValue());
                marriageMelds.addAll(mM);
            }
        }
        allPossibleMelds.put("marriage", marriageMelds);

        //Check for four same cards
        for(HashMap.Entry<String,ArrayList<Card>> entry: cardsByFace.entrySet()){
            ArrayList<ArrayList<Card>> cardList = new ArrayList<ArrayList<Card>>();
            cardList = checkFourSameCards(entry.getValue());

            if(entry.getKey().equals("A")){
                allPossibleMelds.put("four Aces", cardList);
            } else if(entry.getKey().equals("K")){
                allPossibleMelds.put("four Kings", cardList);
            } else if(entry.getKey().equals("Q")){
                allPossibleMelds.put("four Queens", cardList);
            } else if(entry.getKey().equals("J")){
                allPossibleMelds.put("four Jacks", cardList);
            }
        }

        //Check for dix from all available cards for selection
        for(Card card:available_cards_for_selection){
            if(card.getFace().equals("9") && card.getSuit().equals(trump_card.getSuit()) && !meld_pile.contains(card)){
                int count = Collections.frequency(card.getMelds().keySet(), "dix");
                if(count == 0 ){
                    ArrayList<Card> myList = new ArrayList<Card>();
                    myList.add(card);
                    if(allPossibleMelds.containsKey("dix")){
                        allPossibleMelds.get("dix").add(myList);
                    } else{
                        ArrayList< ArrayList<Card>> arr = new ArrayList< ArrayList<Card>>();
                        arr.add(myList);
                        allPossibleMelds.put("dix",arr);
                    }
                }
            }
        }

        printAllPossibleMelds();
    }

    /**
     This function inserts a card into a map by the key
     @param map HashMap<String, ArrayList<Card>>, The map in which card is inserted
     @param key String, The key under which the card is put into the map
     @param card Card, the card which is added into the map
     */
    private void insertIntoMap(HashMap<String, ArrayList<Card>> map, String key, Card card){

        if(map.containsKey(key)){
            map.get(key).add(card);
        }else{
            ArrayList<Card> cardList = new ArrayList<Card>();
            cardList.add(card);
            map.put(key,cardList);
        }
    }

    /**
     This function checks if the cards make pinochle meld
     @param cards ArrayList<Card>, a list containing cards to check
     @return An array list containing array list with cards creating pinochle melds
     */
    private ArrayList<ArrayList<Card>> checkPinochle(ArrayList<Card> cards){
        ArrayList<ArrayList<Card>> pinochleCards = new ArrayList<ArrayList<Card>>();
        int index = 0;
        ArrayList<Card> mCards = new ArrayList<Card>();

        if(cards.size()>0) {
            for (Card card : cards) {
                if ((card.getFace().equals("Q") && card.getSuit().equals("S")) || (card.getFace().equals("J") && card.getSuit().equals("D"))) {
                    int count = Collections.frequency(card.getMelds().keySet(), "pinochle");
                    if (count == 0) {
                        card.setUnique_index(index);
                        index += 1;
                        mCards.add(card);
                    }
                }
            }
        }

        ArrayList<ArrayList<Card>> sameFaceDifferentSuit = new ArrayList<ArrayList<Card>>();
        sameFaceDifferentSuit = sameFaceButDifferentSuit(mCards);


        if(sameFaceDifferentSuit.size()<2){
            return pinochleCards;
        }

        pinochleCards = allMeldCombinations(mCards,sameFaceDifferentSuit);
        ArrayList<ArrayList<Card>> pinochleMelds = new ArrayList<ArrayList<Card>>();

        for(ArrayList<Card> cardList:pinochleCards){
            if(atLeastOneNewCard(cardList)){
                pinochleMelds.add(cardList);
            }
        }
        return pinochleMelds;
    }

    /**
     This function separates cards having same face by suit
     @param cards ArrayList<Card>, a list containing cards
     @return An array list containing array list with cards separated by suit
     */
    public ArrayList<ArrayList<Card>> sameFaceButDifferentSuit(ArrayList<Card> cards){

        ArrayList<ArrayList<Card>> sameFaceDifferentSuit = new ArrayList<ArrayList<Card>>();

        for(Card card:cards){
            if(sameFaceDifferentSuit.size() > 0){
                for(int i=0;i<sameFaceDifferentSuit.size();i++){
                    //Check if duplicate faced card exists
                    if(duplicateFacedCard(sameFaceDifferentSuit.get(i).get(0),card)){
                        sameFaceDifferentSuit.get(i).add(card);
                        break;
                    }
                    //If no duplicate faced cards, create a new vector for cards with same suit
                    //and push back to the main vector
                    else if(i == sameFaceDifferentSuit.size()-1){
                        ArrayList<Card> sameSuit = new ArrayList<Card>();
                        sameSuit.add(card);
                        sameFaceDifferentSuit.add(sameSuit);
                        break;
                    }
                }
            } else{
                ArrayList<Card> sameSuit = new ArrayList<Card>();
                sameSuit.add(card);
                sameFaceDifferentSuit.add(sameSuit);
            }
        }

        return sameFaceDifferentSuit;
    }

    /**
     This function separates cards having same suit by face
     @param cards ArrayList<Card>, a list containing cards
     @return An array list containing array list with cards separated by face
     */
    public ArrayList<ArrayList<Card>> sameSuitButDifferent(ArrayList<Card> cards){

        ArrayList<ArrayList<Card>> sameSuitDifferentFace = new ArrayList<ArrayList<Card>>();

        for(Card card:cards){
            if(sameSuitDifferentFace.size() > 0){
                for(int i=0;i<sameSuitDifferentFace.size();i++){
                    //Check if duplicate faced card exists
                    if(duplicateFacedCard(sameSuitDifferentFace.get(i).get(0),card)){
                        sameSuitDifferentFace.get(i).add(card);
                        break;
                    }
                    //If no duplicate faced cards, create a new vector for cards with same suit
                    //and push back to the main vector
                    else if(i == sameSuitDifferentFace.size()-1){
                        ArrayList<Card> sameFace = new ArrayList<Card>();
                        sameFace.add(card);
                        sameSuitDifferentFace.add(sameFace);
                        break;
                    }
                }
            } else{
                ArrayList<Card> sameFace = new ArrayList<Card>();
                sameFace.add(card);
                sameSuitDifferentFace.add(sameFace);
            }
        }

        return sameSuitDifferentFace;
    }

    /**
     This function checks if the cards have same face and suit
     @param card1 Card, one card
     @param card2 Card, second card
     @return Boolean true if both cards have same face and suit, false if otherwise
     */
    private boolean duplicateFacedCard(Card card1, Card card2){
        if(card1.getFace().equals(card2.getFace()) && card1.getSuit().equals(card2.getSuit())){
            return true;
        }
        return false;
    }

    /**
     This function checks if the cards make marraige meld
     @param cards ArrayList<Card>, a list containing cards to check
     @return An array list containing array list with cards creating marraige melds
     */
    private ArrayList<ArrayList<Card>> checkMarriage(ArrayList<Card> cards){
        ArrayList<ArrayList<Card>> marriageCards = new ArrayList<ArrayList<Card>>();
        int index = 0;
        ArrayList<Card> mCards = new ArrayList<Card>();

        if(cards!=null) {
            for (Card card : cards) {
                if (card.getFace().equals("Q") || card.getFace().equals("K")) {
                    int count = Collections.frequency(card.getMelds().keySet(), "marriage");
                    if (count == 0) {
                        card.setUnique_index(index);
                        index += 1;
                        mCards.add(card);
                    }
                }
            }
        }

        ArrayList<ArrayList<Card>> sameSuitDifferentFace = new ArrayList<ArrayList<Card>>();
        sameSuitDifferentFace = sameSuitButDifferent(mCards);


        if(sameSuitDifferentFace.size()<2){
            return marriageCards;
        }

        marriageCards = allMeldCombinations(mCards,sameSuitDifferentFace);

        ArrayList<ArrayList<Card>> marriageMelds = new ArrayList<ArrayList<Card>>();

        for(ArrayList<Card> cardList:marriageCards){
            if(atLeastOneNewCard(cardList)){
                marriageMelds.add(cardList);
            }
        }
        return marriageMelds;
    }

    /**
     This function checks if the cards make four of same meld
     @param cards ArrayList<Card>, a list containing cards to check
     @return An array list containing array list with cards creating four of same melds
     */
    private ArrayList<ArrayList<Card>> checkFourSameCards(ArrayList<Card> cards){
        ArrayList<ArrayList<Card>> fourSame = new ArrayList<ArrayList<Card>>();
        int index = 0;

        ArrayList<Card> mCards = new ArrayList<Card>();

        String face = cards.get(0).getFace();
        String name = "";

        //Check the card and set name
        if(face.equals("A")){
            name = "four Aces";
        } else if(face.equals("K")){
            name = "four Kings";
        } else if(face.equals("Q")){
            name = "four Queens";
        } else if(face.equals("J")){
            name = "four Jacks";
        }

        //Check if the cards do not have a meld already present and add to a vector
        if(cards!=null) {
            for (Card card : cards) {
                int count = Collections.frequency(card.getMelds().keySet(), name);
                if (count == 0) {
                    card.setUnique_index(index);
                    index += 1;
                    mCards.add(card);
                }
            }
        }

        //Separate cards by suit
        ArrayList<ArrayList<Card>> sameFaceDifferentSuit = new ArrayList<ArrayList<Card>>();
        sameFaceDifferentSuit = sameFaceButDifferentSuit(mCards);

        if(sameFaceDifferentSuit.size() < 4){
            return fourSame;
        }

        //Generate all possible meld combinations and return
        fourSame = allMeldCombinations(mCards, sameFaceDifferentSuit);

        ArrayList<ArrayList<Card>> fourMelds = new ArrayList<ArrayList<Card>>();

        for(ArrayList<Card> cardList:fourSame){
            if(atLeastOneNewCard(cardList)){
                fourMelds.add(cardList);
            }
        }
        return fourMelds;
    }

    /**
     This function checks if the cards make royal marriage meld
     @param cards ArrayList<Card>, a list containing cards to check
     @return An array list containing array list with cards creating royal marriage melds
     */
    private ArrayList<ArrayList<Card>> checkRoyalMarriage(ArrayList<Card> cards){
        ArrayList<ArrayList<Card>> royalMarriageCards = new ArrayList<ArrayList<Card>>();
        int index = 0;
        ArrayList<Card> mCards = new ArrayList<Card>();

        if(cards!=null) {
            for (Card card : cards) {
                if (card.getFace().equals("Q") || card.getFace().equals("K")) {
                    int count = Collections.frequency(card.getMelds().keySet(), "royal marriage");
                    if (count == 0) {
                        card.setUnique_index(index);
                        index += 1;
                        mCards.add(card);
                    }
                }
            }
        }

        ArrayList<ArrayList<Card>> sameSuitDifferentFace = new ArrayList<ArrayList<Card>>();
        sameSuitDifferentFace = sameSuitButDifferent(mCards);


        if(sameSuitDifferentFace.size()<2){
            return royalMarriageCards;
        }

        royalMarriageCards = allMeldCombinations(mCards,sameSuitDifferentFace);

        ArrayList<ArrayList<Card>> royalMelds = new ArrayList<ArrayList<Card>>();

        for(ArrayList<Card> cardList:royalMarriageCards){
            if(atLeastOneNewCard(cardList)){
                royalMelds.add(cardList);
            }
        }
        return royalMelds;
    }

    /**
     This function checks if the cards make flush meld
     @param cards ArrayList<Card>, a list containing cards to check
     @return An array list containing array list with cards creating flush melds
     */
    private ArrayList<ArrayList<Card>> checkFlush(ArrayList<Card> cards){
        ArrayList<ArrayList<Card>> flushCards = new ArrayList<ArrayList<Card>>();
        int index = 0;
        ArrayList<Card> mCards = new ArrayList<Card>();

        if(cards!=null) {
            for (Card card : cards) {
                if (!card.getFace().equals("9")) {
                    int count = Collections.frequency(card.getMelds().keySet(), "flush");
                    if (count == 0) {
                        card.setUnique_index(index);
                        index += 1;
                        mCards.add(card);
                    }
                }
            }
        }

        ArrayList<ArrayList<Card>> sameSuitDifferentFace = new ArrayList<ArrayList<Card>>();
        sameSuitDifferentFace = sameSuitButDifferent(mCards);


        if(sameSuitDifferentFace.size()<5){
            return flushCards;
        }

        flushCards = allMeldCombinations(mCards,sameSuitDifferentFace);

        ArrayList<ArrayList<Card>> flushMelds = new ArrayList<ArrayList<Card>>();

        for(ArrayList<Card> cardList:flushCards){
            if(atLeastOneNewCard(cardList)){
                flushMelds.add(cardList);
            }
        }
        return flushMelds;
    }

    /**
     This function creates all possible meld combinations
     @param cards ArrayList<Card>, a list containing cards
     @param sameSuitDifferentFace ArrayList<ArrayList<Card>>, Has all cards of same suit but cards having same face in separate vectors
     @return An array list containing array list with cards creating melds
     */
    private ArrayList<ArrayList<Card>> allMeldCombinations(ArrayList<Card> cards, ArrayList<ArrayList<Card>> sameSuitDifferentFace){

        ArrayList<ArrayList<Card>> meldCombinations = new ArrayList<ArrayList<Card>>();
        ArrayList<ArrayList<Integer>> possibleMelds = new ArrayList<ArrayList<Integer>>();
        possibleMelds = getPossibleMeldPaths(cards,sameSuitDifferentFace);

        for(int i=0;i<possibleMelds.size();i++){
            ArrayList<Card> temp = new ArrayList<Card>();
            for(int j = 0;j<possibleMelds.get(i).size();j++){
                for(Card card:cards){
                    if(card.getUnique_index() == possibleMelds.get(i).get(j)){
                        temp.add(card);
                    }
                }
            }
            //ArrayList<Card> meldCards = new ArrayList<Card>();
            //meldCards = temp;
            meldCombinations.add(temp);
        }

        return meldCombinations;
    }

    /**
     This function gets all possible paths using DFS in a graph
     @param suitCards ArrayList<Card>, a list containing cards
     @param sameSuitDifferentFace ArrayList<ArrayList<Card>>, Has all cards of same suit but cards having same face in separate vectors
     @return An array list containing array list with integers that has contain paths
     */
    private ArrayList<ArrayList<Integer>> getPossibleMeldPaths(ArrayList<Card> suitCards, ArrayList<ArrayList<Card>> sameSuitDifferentFace){

        Graph graph = new Graph(suitCards.size());
        ArrayList<Integer> positions = new ArrayList<Integer>();
        positions = checkDouble(sameSuitDifferentFace);

        if(!positions.isEmpty()){
            for(Integer i:positions) {
                sameSuitDifferentFace.get((int)i).remove(1);
            }
        }

        //generates edges for graph now send to graph
        if(sameSuitDifferentFace.size() > 1){
            for(int i=0;i<sameSuitDifferentFace.size();i++){
                if(i < sameSuitDifferentFace.size() - 1){
                    ArrayList<ArrayList<Integer>> pairs = getPathPairs(sameSuitDifferentFace.get(i),sameSuitDifferentFace.get(i+1));
                    for(int j=0;j<pairs.size();j++){
                        graph.addEdge(pairs.get(j).get(0), pairs.get(j).get(1));
                    }
                }
            }
        }

        //Evaluate all paths where source is all possible values in the firat vector element and destination is all values in last vector element
        graph.evaluateAllPaths(sameSuitDifferentFace.get(0).get(0).getUnique_index(), sameSuitDifferentFace.get(sameSuitDifferentFace.size()-1).get(0).getUnique_index());
        if(sameSuitDifferentFace.get(0).size() > 1){
            graph.evaluateAllPaths(sameSuitDifferentFace.get(0).get(1).getUnique_index(), sameSuitDifferentFace.get(sameSuitDifferentFace.size()-1).get(0).getUnique_index());
        }
        if(sameSuitDifferentFace.get(sameSuitDifferentFace.size()-1).size() > 1){
            graph.evaluateAllPaths(sameSuitDifferentFace.get(0).get(0).getUnique_index(), sameSuitDifferentFace.get(sameSuitDifferentFace.size()-1).get(1).getUnique_index());
            if(sameSuitDifferentFace.get(0).size() > 1){
                graph.evaluateAllPaths(sameSuitDifferentFace.get(0).get(1).getUnique_index(), sameSuitDifferentFace.get(sameSuitDifferentFace.size()-1).get(1).getUnique_index());
            }
        }

        //Get all paths and return the paths
        ArrayList<ArrayList<Integer>> possibleMelds = new  ArrayList<ArrayList<Integer>>();
        possibleMelds = graph.getAllPaths();
        return possibleMelds;
    }

    /**
     This function checks if more than one card with same faces are found
     @param sameSuitDifferentFace ArrayList<ArrayList<Card>>, Has all cards of same suit but cards having same face in separate vectors
     @return An array list containing array list with integers that are positions where doubles exist
     */
    private ArrayList<Integer> checkDouble(ArrayList<ArrayList<Card>> sameSuitDifferentFace){
        int count = 0;
        int index = 0;
        int pos = 0;
        ArrayList<Integer> positions = new ArrayList<Integer>();

        for(ArrayList<Card> cards:sameSuitDifferentFace){
            if(cards.size()>1){
                positions.add(index);
            }
            index+=1;
        }

        if(!positions.isEmpty()){
            if(positions.size() < sameSuitDifferentFace.size()){
                return positions;
            }
        }
        return new ArrayList<Integer>();
    }

    /**
     This function generates integer values as vertices to evaluate all paths between two adjacent vectors
     @param list1 ArrayList<Card>, an array list containing cards
     @param list2 ArrayList<Card>, an array list containing cards
     @return An array list containing array list with integers that contsin path pairs for graph evaluation
     */
    private ArrayList<ArrayList<Integer>> getPathPairs(ArrayList<Card> list1, ArrayList<Card> list2){

        ArrayList<ArrayList<Integer>> pairs = new ArrayList<ArrayList<Integer>>() ;

        for(int i=0;i<list1.size();i++){
            //Iterate over all elements of the adjacent vector to get pairs
            for(int j=0;j<list2.size();j++){
                ArrayList<Integer> myArr = new ArrayList<Integer>();
                myArr.add(list1.get(i).getUnique_index());
                myArr.add(list2.get(j).getUnique_index());
                pairs.add(myArr);
            }
        }

        return pairs;
    }

    /**
     This function generates all cards which do not create any meld
     @return An array list containing Cards that do not create any meld
     */
    private ArrayList<Card> cardsNotCreatingMelds(){

        ArrayList<Card> cardsNotInMelds = new ArrayList<Card>();

        for(Card availableCard:available_cards_for_selection){
            int count = 0 ;
            for(HashMap.Entry<String,ArrayList<ArrayList<Card>>> entry: allPossibleMelds.entrySet()){
                for(int i=0;i<entry.getValue().size();i++){
                    if(entry.getValue().get(i).contains(availableCard)){
                        count += 1;
                    }
                }
            }
            if(count == 0){
                cardsNotInMelds.add(availableCard);
            }
        }
        return cardsNotInMelds;
    }

    /**
     This function deals with help regarding lead card selection
     */
    protected void helpLeadCard(){

        updateAvailableCardsForSelection();
        createAllPossibleMelds();

        Card playedCard = new Card();

        ArrayList<Card> cardsNotInMelds = cardsNotCreatingMelds();

        ArrayList<Card> trumpCardsNotInMelds = new ArrayList<Card>();
        ArrayList<Card> nonTrumpCardsNotInMelds = new ArrayList<Card>();

        for(Card card : cardsNotInMelds){
            if(card.getSuit().equals(trump_card.getSuit())){
                trumpCardsNotInMelds.add(card);
            } else{
                nonTrumpCardsNotInMelds.add(card);
            }
        }

        //Among trump cards not in melds, get the card with maximum points and play the card
        if(trumpCardsNotInMelds!=null && trumpCardsNotInMelds.size()>0){
            playedCard = getCardWithMaxPoints(trumpCardsNotInMelds);
            play_reason = " as it does not affect any melds and is the card with highest point.";
        }
        //If non trump card not in melds is available, play the card having maximum points from non
        //trump cards not in any melds
        else if(nonTrumpCardsNotInMelds!=null && nonTrumpCardsNotInMelds.size()>0){
            playedCard = getCardWithMaxPoints(nonTrumpCardsNotInMelds);
            play_reason = " as it does not affect any melds and is the card with highest point.";
        }
        else{
        //* is because there are total 9 possible melds in the meldsByPoints string array sorted by points max to min
            for(int i=8;i>=0;i--){
                if(allPossibleMelds.get(meldsByPoints[i]) != null){
                    ArrayList<Card> tempList = allPossibleMelds.get(meldsByPoints[i]).get(0);
                    playedCard = getCardWithMaxPoints(tempList);
                }
            }
            play_reason = " as it uses the meld with least point.";
        }

        turnCard = playedCard;
        System.out.println("Lead card: " + playedCard.getFace() + playedCard.getFace());

    }

    /**
     This function deals with help regarding chase card selection
     @param leadCard Card, the lead card on the basis of which chase card is chosen
     */
    protected void helpChaseCard(Card leadCard){
        updateAvailableCardsForSelection();
        createAllPossibleMelds();

        Card playedCard = new Card();

        ArrayList<Card> cardsNotInMelds = cardsNotCreatingMelds();

        ArrayList<Card> cardsWithMorePointsNotInMelds = new ArrayList<Card>();
        ArrayList<Card> cardsWithMorePoints = new ArrayList<Card>();

        ArrayList<Card> trumpCards = new ArrayList<Card>();
        ArrayList<Card> nonTrumpCards = new ArrayList<Card>();

        ArrayList<Card> trumpCardsNotInMelds = new ArrayList<Card>();
        ArrayList<Card> nonTrumpCardsNotInMelds = new ArrayList<Card>();



        for(Card card : cardsNotInMelds){
            if(card.getSuit().equals(trump_card.getSuit())){
                trumpCardsNotInMelds.add(card);
            } else{
                nonTrumpCardsNotInMelds.add(card);
            }
        }

        for(Card card : available_cards_for_selection){
            if(card.getSuit().equals(trump_card.getSuit())){
                trumpCards.add(card);
            } else{
                nonTrumpCards.add(card);
            }
        }

        for(Card card : available_cards_for_selection) {
            if (card.getSuit().equals(leadCard.getSuit()) && card.getPoints_worth() > leadCard.getPoints_worth()) {
                if(cardsNotInMelds.contains(card)) {
                    cardsWithMorePointsNotInMelds.add(card);
                }
                cardsWithMorePoints.add(card);
            }
        }

        if(cardsWithMorePoints.size()>0 && cardsWithMorePointsNotInMelds.size()>0){
            if(cardsWithMorePointsNotInMelds.size()>0) {
                playedCard = getCardWithMinPoints(cardsWithMorePointsNotInMelds);
                play_reason = " as it is a larger card of the same suit and conserves all melds.";
            }
        }

        //If needed to play trump card
        else if(trumpCards.size()>0 && !leadCard.getSuit().equals(trump_card.getSuit())){
            if(trumpCardsNotInMelds.size()>0){
                playedCard = getCardWithMinPoints(trumpCardsNotInMelds);
                play_reason = " as it is a trump card not in any melds and there is no winning card of other suit.";
            } else{
                playedCard = cardFromMeldWithLeastPoint(trumpCards);
                play_reason = " as it is a trump card in meld with least points and there is no winning card of other suit.";
            }
        }
        else{
            //get the card with lowest points
            if(trumpCardsNotInMelds!=null && cardsWithMorePoints!=null){
                playedCard = cardFromMeldWithLeastPoint(cardsWithMorePoints);
                play_reason = " as it is a larger card of the same suit and uses the meld with least point.";
            }
            //else haleko
             else if(nonTrumpCardsNotInMelds.size()>0){
                playedCard = getCardWithMinPoints(nonTrumpCardsNotInMelds);
                play_reason = " as there is no winning card and it is the card with least points.";
            } else{
                playedCard = cardFromMeldWithLeastPoint(nonTrumpCards);
                play_reason = " as there is no winning card and it is the card with least points saving maximum melds.";
            }

        }
        if(playedCard.getFace()=="" && nonTrumpCardsNotInMelds.size()>0){
            playedCard = playedCard = getCardWithMinPoints(nonTrumpCardsNotInMelds);
            play_reason = " as there is no winning card and it is the card with least points.";
        }

        System.out.println("Chase card: " + playedCard.getFace() + playedCard.getFace());
        turnCard = playedCard;
    }

    /**
     This function returns card from melds worth minimum points
     @param cards ArrayList<Card>, a list of cards to evaluate
     @return Card object that is a card with minimum points worth and is in the meld worth minimum possible points
     */
    private Card cardFromMeldWithLeastPoint(ArrayList<Card> cards){
        boolean cardSelected = false;
        Card cardToPlay = new Card();
        for (int i = 8; i >= 0; i--) {
            if (allPossibleMelds.get(meldsByPoints[i]) != null) {
                ArrayList<Card> cardsToComp = new ArrayList<Card>();
                for(Card card:cards) {
                    for(ArrayList<Card> meldList:allPossibleMelds.get(meldsByPoints[i] )){
                        if(meldList.contains(card)){
                            cardsToComp.add(card);
                            cardSelected = true;
                        }
                    }
                }
                if(cardsToComp!=null && cardsToComp.size()>0) {
                    cardToPlay = getCardWithMinPoints(cardsToComp);
                }
            }
            if(cardSelected){
                break;
            }
        }

        return cardToPlay;
    }

    /**
     This function returns card worth maximum points
     @param cards ArrayList<Card>, a list of cards to evaluate
     @return Card object that is a card with maximum points
     */
    private Card getCardWithMaxPoints(ArrayList<Card> cards){
        Card maxCard = cards.get(0);

        for(Card card : cards){
            if(card.getPoints_worth() >= maxCard.getPoints_worth()){
                maxCard = card;
            }
        }

        return maxCard;
    }

    /**
     This function returns card worth minimum points
     @param cards ArrayList<Card>, a list of cards to evaluate
     @return Card object that is a card with minimum points
     */
    private Card getCardWithMinPoints(ArrayList<Card> cards){
        Card minCard = new Card();
        if(cards.size()>0) {
             minCard = cards.get(0);
        }

        for(Card card : cards){
            if(card.getPoints_worth() < minCard.getPoints_worth()){
                minCard = card;
            }
        }

        return minCard;
    }

    /**
     This function returns the turn recommendation for the  player
     @param card Card, The card object that is a lead card if the player is chasing
     @return help String, that is a recommendation for the  player
     */
    public String help(Card card){
        return "";
    }

    /**
     This function returns the meld recommendation for the player
     @return help String, that is a recommendation for the player for meld
     */
    public String meldHelp(){
        return "";
    }


}

