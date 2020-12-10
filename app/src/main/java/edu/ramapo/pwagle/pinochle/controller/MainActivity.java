/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/

package edu.ramapo.pwagle.pinochle.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.ramapo.pwagle.pinochle.R;
import edu.ramapo.pwagle.pinochle.model.Card;
import edu.ramapo.pwagle.pinochle.model.Game;
import edu.ramapo.pwagle.pinochle.model.Player;
import edu.ramapo.pwagle.pinochle.model.Round;

public class MainActivity extends AppCompatActivity {

    private Button computer_btn, human_btn, play_move_btn, help_btn, no_meld_btn, logs_btn, save_btn, quit_btn;
    private TextView hand_pile_view, meld_pile_view, capture_pile_view, stock_pile_view, trump_card_view, next_player, help_text_layout, computer_score, human_score, round_view, round_score_view;
    private HorizontalScrollView hand_pile_scroll, meld_pile_scroll, capture_pile_scroll, stock_pile_scroll, trump_card_scroll, available_cards_scroll;
    private LinearLayout hand_pile_linear_layout, capture_pile_linear_layout,meld_pile_linear_layout,
            stock_pile_linear_layout,trump_card_linear_layout, available_cards_layout;

    private Player curr_player;
    private ArrayList<Card> selectedCards = new ArrayList<Card>();
    private boolean  newTurn = false, cardSelected = false, turnPlayedByComp = false,
            turnPlayedByHuman = false, meldShown = false, meldSelected = false, turnCardSelected = false,
            firstRound = true;
    private int select_color, disselect_color;

    private ArrayList<String> logs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFields();

        final Game game = new Game();

        /* *********************************************
          Code that separates new game and load game
        ********************************************* */
        String key = getIntent().getStringExtra("fileName");
        if(key!=null){
            //call load game
            loadFile(key, game);
            setLeaderTurn(game);
            updateScores(game);
        } else if(firstRound){
            game.createNewGame();
            String tossStr = getIntent().getStringExtra("tossSelection");
            toss(tossStr, game);
            setLeaderTurn(game);
        }


        /* *********************************************
          Code that deals with all buttons and their on click functions
        ********************************************* */
        select_color = ContextCompat.getColor(MainActivity.this, R.color.selected);
        disselect_color = ContextCompat.getColor(MainActivity.this, R.color.not_selected);

        if (game.getCurrent_players().get(0).isleader()) {
            computer_btn.performClick();
        } else {
            human_btn.performClick();
        }

        displayCards(game);

        if (!turnPlayedByComp && !turnPlayedByHuman && !meldSelected) {
            play_move_btn.setText("Play New Turn");
            help_btn.setVisibility(View.GONE);
        }

        computer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computer_btn.setBackgroundColor(select_color);
                human_btn.setBackgroundColor(disselect_color);
                displayPlayerCards(game.getCurrent_players().get(0));
                //curr_player = game.getCurrent_players().get(0);
            }
        });

        human_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                computer_btn.setBackgroundColor(disselect_color);
                human_btn.setBackgroundColor(select_color);
                displayPlayerCards(game.getCurrent_players().get(1));
                //curr_player = game.getCurrent_players().get(1);
            }
        });

        play_move_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_text_layout.setVisibility(View.GONE);
                //Before starting each turn
                if (!newTurn) {
                    newTurn = true;
                    play_move_btn.setText("Play Card");
                    help_btn.setVisibility(View.VISIBLE);
                    help_text_layout.setText("");
                    save_btn.setVisibility(View.GONE);
                }
                if(curr_player!=null) {
                    if (curr_player.getName().equals("Human") && turnCardSelected) {
                        selectMeld();
                    }
                }
                //If human's turn
                if (cardSelected && game.getCurrent_players().get(1).isTurn() && !meldSelected) {
                    cardSelected = false;
                    if (selectedCards.size() > 0) {
                        //Only when player selects
                        curr_player.setTurnCard(selectedCards.get(0));
                        updateLog(game.getCurrent_players().get(1));
                        turnPlayedByHuman = true;
                        turnCardSelected = true;
                        game.getCurrent_round().setTurn_card(curr_player.getTurnCard());
                        computer_btn.performClick();
                        swapTurn(game);
                    } else {
                        help_text_layout.setVisibility(View.VISIBLE);
                        help_text_layout.setText(R.string.select_card);
                    }
                }
                if(!cardSelected && game.getCurrent_players().get(1).isTurn()){
                    help_text_layout.setVisibility(View.VISIBLE);
                    help_text_layout.setText(R.string.select_card);
                }
                //If computer's turn
                if (game.getCurrent_players().get(0).isTurn() && !meldSelected) {
                    turnPlayedByComp = true;
                    curr_player.setTurnCard(game.getCurrent_round().getTurn_card());
                    updateLog(game.getCurrent_players().get(0));
                    game.getCurrent_round().setTurn_card(curr_player.getTurnCard());
                    human_btn.performClick();
                    swapTurn(game);
                }
                available_cards_layout.removeAllViews();
                if (turnPlayedByComp && turnPlayedByHuman && !meldSelected) {
                    playTurn(game);
                    turnPlayedByComp = false;
                    turnPlayedByHuman = false;

                }
                selectMeld();
                if (meldSelected) {
                    meldSelection(game);
                }
            }
        });
        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                available_cards_scroll.setVisibility(View.GONE);
                help_text_layout.setVisibility(View.VISIBLE);
                String help = "";
                if (!turnCardSelected) {
                    help = curr_player.help(game.getCurrent_round().getTurn_card());
                } else {
                    help = curr_player.meldHelp();
                }
                help_text_layout.setText(help);
            }
        });

        no_meld_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStockPile(game);
                newTurn = false;
                turnCardSelected = false;
                play_move_btn.setText("Play new Turn");
                help_btn.setVisibility(View.GONE);
                selectedCards.clear();
                available_cards_layout.removeAllViews();
                save_btn.setVisibility(View.VISIBLE);
                available_cards_scroll.setVisibility(View.INVISIBLE);
                help_text_layout.setVisibility(View.VISIBLE);
                help_text_layout.setText("");
                no_meld_btn.setVisibility(View.GONE);
            }
        });

        logs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logs_dialog(game);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDialog(game);
            }
        });

        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitDialog();
            }
        });

    }

    /* *********************************************
       Codes that deal with displaying cards in views
     ********************************************* */

    /**
     This function displays the card selected by a player
     @param card Card, card selected by the user from the card views
     */
    private void displaySelectedCard(Card card){

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 190);
        lp.setMargins(10,10,10,10);
        available_cards_scroll.setVisibility(View.VISIBLE);
        help_text_layout.setVisibility(View.GONE);
        //available_cards_layout.removeAllViews();
        String cardName = card.getUnique_identifier() + card.getFace() + card.getSuit();
        cardName = cardName.toLowerCase();
        final ImageView iv = new ImageView(this);
        final int resID = getResources().getIdentifier(cardName , "drawable", getPackageName());
        card.setResource_id(resID);
        iv.setBackgroundResource(resID);
        iv.setLayoutParams(lp);
        iv.setId(resID);
        available_cards_layout.addView(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                available_cards_layout.removeView(iv);
                Card cardToDelete = new Card();
                for (Card tempCard:selectedCards){
                    if(tempCard.getResource_id() == iv.getId()){
                        cardToDelete = tempCard;
                    }
                }
                selectedCards.remove(cardToDelete);
            }
        });

    }

    /**
     This function intializes all layout fields used in the xml file for the Activity
     */
    private void initializeFields(){
        computer_btn = (Button) findViewById(R.id.computer_btn);
        human_btn = (Button) findViewById(R.id.human_btn);
        play_move_btn = (Button) findViewById(R.id.play_btn);
        help_btn = (Button) findViewById(R.id.help_btn);
        no_meld_btn = (Button) findViewById(R.id.no_meld_btn);
        logs_btn = (Button) findViewById(R.id.logs_btn);
        save_btn = (Button) findViewById(R.id.save_btn);
        quit_btn = (Button) findViewById(R.id.quit_btn);

        hand_pile_view = (TextView) findViewById(R.id.hand_pile_view);
        meld_pile_view = (TextView) findViewById(R.id.meld_pile_view);
        capture_pile_view = (TextView) findViewById(R.id.capture_pile_view);
        stock_pile_view = (TextView) findViewById(R.id.stock_pile_view);
        trump_card_view = (TextView) findViewById(R.id.trump_card_view);
        next_player = (TextView) findViewById(R.id.next_player);
        help_text_layout = (TextView) findViewById(R.id.help_text_view);
        computer_score = (TextView) findViewById(R.id.computer_score);
        human_score = (TextView) findViewById(R.id.human_score);
        round_view = (TextView) findViewById(R.id.round_view);
        round_score_view = (TextView) findViewById(R.id.round_num_view);


        hand_pile_scroll = (HorizontalScrollView) findViewById(R.id.hand_pile_scroll_view);
        meld_pile_scroll = (HorizontalScrollView) findViewById(R.id.meld_pile_scroll_view);
        capture_pile_scroll = (HorizontalScrollView) findViewById(R.id.capture_pile_scroll_view);
        stock_pile_scroll = (HorizontalScrollView) findViewById(R.id.stock_pile_scroll_view);
        trump_card_scroll = (HorizontalScrollView) findViewById(R.id.trump_card_scroll_view);
        available_cards_scroll = (HorizontalScrollView) findViewById(R.id.available_cards_scroll_view);

        hand_pile_linear_layout = (LinearLayout) findViewById(R.id.hand_pile_linear_layout);
        meld_pile_linear_layout = (LinearLayout) findViewById(R.id.meld_pile_linear_layout);
        capture_pile_linear_layout = (LinearLayout) findViewById(R.id.capture_pile_linear_layout);
        stock_pile_linear_layout = (LinearLayout) findViewById(R.id.stock_pile_linear_layout);
        trump_card_linear_layout = (LinearLayout) findViewById(R.id.trump_card_linear_layout);
        available_cards_layout = (LinearLayout) findViewById(R.id.available_cards_linear_layout);


    }

    /**
     This function displays all cards unique to a player
     @param player Player, player whose cards are displayed
     */
    private void displayPlayerCards(Player player){
        hand_pile_linear_layout.removeAllViews();
        capture_pile_linear_layout.removeAllViews();
        meld_pile_linear_layout.removeAllViews();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 190);
        lp.setMargins(10,10,10,10);

        final ArrayList<Card> hand_pile = player.getCardsInHand();
        displayCardsInView(hand_pile,hand_pile_linear_layout,player,false);

        ArrayList<Card> capture_pile = player.getCapture_pile();
        for(Card card:capture_pile){
            String cardName = card.getUnique_identifier() + card.getFace() + card.getSuit();
            cardName = cardName.toLowerCase();
            ImageView iv = new ImageView(this);
            int resID = getResources().getIdentifier(cardName , "drawable", getPackageName());
            iv.setBackgroundResource(resID);
            iv.setLayoutParams(lp);
            capture_pile_linear_layout.addView(iv);
        }

        ArrayList<ArrayList<Card>> meldsList = player.getMeld_pile();
            for(ArrayList<Card> meld:meldsList){
                displayCardsInView(meld,meld_pile_linear_layout,player,true);

                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(10, 190);
                lp1.setMargins(10, 10, 10, 10);
                ImageView iv = new ImageView(this);
                int resID = getResources().getIdentifier("dot", "drawable", getPackageName());
                iv.setBackgroundResource(resID);
                iv.setLayoutParams(lp1);
                meld_pile_linear_layout.addView(iv);

            }


    }

    /**
     This function displays star image after a card if it needs that image
     @param ll LinearLayout, tha linear layout where the image is displayed
     */
    private void displayStar(LinearLayout ll){
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(10, 10);
        lp1.setMargins(10, 10, 10, 10);
        ImageView iv = new ImageView(this);
        int resID = getResources().getIdentifier("dot", "drawable", getPackageName());
        iv.setBackgroundResource(resID);
        iv.setLayoutParams(lp1);
        ll.addView(iv);
    }

    /**
     This function displays cards of a player in view
     @param cards ArrayList<Card>, an Array List containing cards to displayed
     @param ll LinearLayout, tha linear layout where the image is displayed
     @param player Player, player whose cards are displayed
     @param checkStar Boolean, a boolean value which is true if cards need a star
     */
    private void displayCardsInView(final ArrayList<Card> cards, LinearLayout ll, Player player, boolean checkStar){
        int index = 0;
        boolean star=false;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 190);
        lp.setMargins(10,10,10,10);
        for(Card card:cards){
            if(card.checkForStar(player.getMeld_pile())){
                star = true;
            }
            String cardName = card.getUnique_identifier() + card.getFace() + card.getSuit();
            cardName = cardName.toLowerCase();
            ImageView iv = new ImageView(this);
            final int finalIndex = index;
            iv.setId(finalIndex);
            int resID = getResources().getIdentifier(cardName , "drawable", getPackageName());
            iv.setBackgroundResource(resID);
            iv.setLayoutParams(lp);
            ll.addView(iv);
            if(star){
                star = false;
                displayStar(meld_pile_linear_layout);
            }
            if(player.getName().equals("Human")) {
                if(newTurn && !cardSelected) {
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cardSelected = true;
                            available_cards_scroll.setVisibility(View.VISIBLE);
                            help_text_layout.setVisibility(View.INVISIBLE);
                            if (!selectedCards.contains(cards.get(finalIndex))) {
                                if (!turnCardSelected) {
                                    selectedCards.clear();
                                    available_cards_layout.removeAllViews();
                                    selectedCards.add(cards.get(finalIndex));
                                } else {
                                    selectedCards.add(cards.get(finalIndex));
                                }
                                displaySelectedCard(cards.get(finalIndex));
                            }
                        }
                    });
                }
            }

            index+=1;
        }
    }

    /**
     This function displays all common cards like the stock pile and trump card
     @param game Game, current game
     */
    private void displayCards(Game game){

        if(curr_player!=null) {
            displayPlayerCards(curr_player);
            if(curr_player.getName().equalsIgnoreCase("computer")){
                computer_btn.setBackgroundColor(select_color);
                human_btn.setBackgroundColor(disselect_color);
            }else{
                computer_btn.setBackgroundColor(disselect_color);
                human_btn.setBackgroundColor(select_color);
            }
        }

        stock_pile_linear_layout.removeAllViews();
        trump_card_linear_layout.removeAllViews();


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 190);
        lp.setMargins(10,10,10,10);
        ArrayList<Card> stock_pile = game.getCurrent_deck();
        for(Card card:stock_pile){
            String cardName = card.getUnique_identifier() + card.getFace() + card.getSuit();
            cardName = cardName.toLowerCase();
            ImageView iv = new ImageView(this);
            int resID = getResources().getIdentifier(cardName , "drawable", getPackageName());
            iv.setBackgroundResource(resID);
            iv.setLayoutParams(lp);
            stock_pile_linear_layout.addView(iv);
        }

        Card card = game.getTrumpCard();
        ImageView iv = new ImageView(this);
        int resID;
        String cardName;

        if("" != card.getFace()) {
            cardName = card.getUnique_identifier() + card.getFace() + card.getSuit();
            cardName = cardName.toLowerCase();

        } else{
            if(card.getSuit().equals("D")){
                cardName = "diamond";
            }else if(card.getSuit().equals("H")){
                cardName = "heart";
            }else if(card.getSuit().equals("S")){
                cardName = "spade";
            }else{
                cardName = "club";
            }
        }
        resID = getResources().getIdentifier(cardName, "drawable", getPackageName());
        iv.setBackgroundResource(resID);
        iv.setLayoutParams(lp);
        trump_card_linear_layout.addView(iv);

        round_score_view.setText(Integer.toString(game.getNumberOfRounds()));
    }

    /**
     This function plays turn and evaluates after both players select turn card
     @param game Game, current game
     */
    private void playTurn(Game game) {
        Round currRound = game.getCurrent_round();
        curr_player = currRound.selectTurnWinner();
        //System.out.println(curr_player.getName());
        displayCards(game);
        updateScores(game);
        setLeaderTurn(game);

        play_move_btn.setText("Show Meld");
        selectedCards.clear();
        if(turnPlayedByHuman && turnPlayedByComp){
            no_meld_btn.setVisibility(View.VISIBLE);
        }
        if(game.getCurrent_round().isRound_ended()){
            roundEndDialog(game.getEndRoundDetails(), game);
        }

    }

    /**
     This function swaps turns between players
     @param game Game, current game
     */
    private void swapTurn(Game game){

        if(game.getCurrent_players().get(0).isTurn()){
            game.getCurrent_players().get(0).setTurn(false);
            game.getCurrent_players().get(1).setTurn(true);
            curr_player = game.getCurrent_players().get(1);
        } else{
            game.getCurrent_players().get(0).setTurn(true);
            game.getCurrent_players().get(1).setTurn(false);
            curr_player = game.getCurrent_players().get(0);
        }
        next_player.setText(curr_player.getName());
    }

    /**
     This function sets the next turn of the leader
     @param game Game, current game
     */
    private void setLeaderTurn(Game game){
        for(int i=0;i<game.getCurrent_players().size();i++){
            if(game.getCurrent_players().get(i).isleader()){
                game.getCurrent_players().get(i).setTurn(true);
                curr_player = game.getCurrent_players().get(i);
            }
        }
        next_player.setText(curr_player.getName());
    }

    /**
     This function updates scores in the view
     @param game Game, current game
     */
    private void updateScores(Game game){
        computer_score.setText(game.getCurrent_players().get(0).getGame_score() +"/"+ game.getCurrent_players().get(0).getRound_score());
        human_score.setText(game.getCurrent_players().get(1).getGame_score() +"/"+ game.getCurrent_players().get(1).getRound_score());
    }

    /**
     This function checks if cards for melds have been selected by each player
     */
    private void selectMeld(){

        displayPlayerCards(curr_player);
        if(curr_player.getName().equals("Human")) {
            if (selectedCards.size() == 0) {
                meldSelected = false;
            } else if(turnCardSelected && selectedCards.size() > 0){
                meldSelected = true;
            }
        }else{
            meldSelected = true;
        }

    }

    /**
     This function sends the selected cards for melds to evaluates and updates all piles
     @param game Game, current game
     */
    private void meldSelection(final Game game){

        boolean meldShown = false;

        if(curr_player.getName().equals("Computer")){
            meldShown = game.getCurrent_round().evaluateMeld(curr_player.showMeld());
            displayPlayerCards(curr_player);

        } else {
            for (int i = 0; i < game.getCurrent_players().size(); i++) {
                if (game.getCurrent_players().get(i).isleader()) {
                    meldShown = game.getCurrent_round().evaluateMeld(selectedCards);
                    curr_player = game.getCurrent_players().get(i);
                }
            }
        }
        //If meld shown, update
        if (meldShown == true) {
            updateStockPile(game);
            updateScores(game);
            setLeaderTurn(game);
            updateLog(curr_player);
            meldSelected = false;
            turnCardSelected = false;
            cardSelected = false;
            selectedCards.clear();
            available_cards_layout.removeAllViews();
            no_meld_btn.setVisibility(View.GONE);
            save_btn.setVisibility(View.VISIBLE);
            newTurn = false;
            play_move_btn.setText("Play new Turn");
            help_btn.setVisibility(View.GONE);
        } else {
            available_cards_scroll.setVisibility(View.INVISIBLE);
            help_text_layout.setVisibility(View.VISIBLE);
            help_text_layout.setText("Invalid meld shown. Please try again!!!");
            selectedCards.clear();
        }


    }

    /**
     This function updates the stock pile for view after each turn
     @param game Game, current game
     */
    private void updateStockPile(Game game){
        game.getCurrent_round().updateStockPile();
        displayCards(game);
    }

    /**
     This function updates the logs array after each player plays
     @param player Player, player who has recently played
     */
    private void updateLog(Player player){
        logs.add(player.getPlay_reason());
    }

    /**
     This function creates a dialog box and displays all logs
     @param game Game, current game
     */
    private void logs_dialog(Game game){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        String toPrint = "";

        for(int i=logs.size()-1;i>=0;i--)
        {
            toPrint = toPrint + logs.get(i) + "\n-------\n";

        }
        builder1.setCancelable(true);
        builder1.setMessage(toPrint);
        builder1.setNegativeButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /**
     This function creates a dialog box to save a game
     @param game Game, current game
     */
    private void saveDialog(final Game game){
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("Enter filename: ");
        builder2.setCancelable(true);

        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder2.setView(input);

        builder2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = input.getText().toString();
                try {
                    saveGame(game, fileName);
                    System.exit(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        builder2.setNegativeButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder2.create();
        alert11.show();

    }

    /**
     This function saves the game in a .txt file
     @param game Game, current game
     @param fileName String, the name of file where the game is saved
     */
    private void saveGame(Game game, String fileName) throws IOException {

        String output = game.saveGame();
        //fileName = "serial.txt";

        File myExternalFile = new File(getExternalFilesDir("MyFileDir"),fileName);
        FileOutputStream outFile = null;

        try{
            outFile = new FileOutputStream(myExternalFile);
            outFile.write(output.getBytes());
            //File file = new File(getFilesDir(),fileName);
            System.out.println("File saved in " + fileName);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        } finally{
            if(outFile != null){
                try {
                    outFile.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        System.exit(1);

    }

    /**
     This function loads a game from a .txt file
     @param fileName String, the name of file from where the game is saved
     @param game Game, current game
     */
    private void loadFile(String fileName, Game game){

        FileReader fr = null;
        String fileItems = "";

        File f = new File(getExternalFilesDir("MyFileDir"), fileName);
        StringBuilder strBuilder = new StringBuilder();
        try{
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line!=null){
                strBuilder.append(line).append('\n');
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileItems = strBuilder.toString();
        }

        loadGame(fileItems, game);
    }

    /**
     This function sends the loaded details to the game model class
     @param info String, all the data from the file is passed in this string
     @param game Game, current game
     */
    private void loadGame(String info, Game game){
        game.loadGame(info);
    }

    /**
     This function creates a dialog box with options for toss
     */
    private void tossDialog(final Game game){

        final String[] items = {"Heads", "Tails"};

        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setCancelable(true);
        builder3.setTitle("Select Heads or Tails for toss: ");

        builder3.setNegativeButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder3.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println(items[which]);
                toss(items[which],game);
            }
        });


        AlertDialog alert11 = builder3.create();
        alert11.show();
    }

    /**
     This function handles the toss after each round
     @param selection String, it is the toss value selected by the user
     @param game Game, current game
     */
    private void toss(String selection,Game game){

        String[] options = {"Heads", "Tails"};
        String outcome = "";

        Random r = new Random();
        int i = r.nextInt(2);

        if(0==i){
            outcome = options[0];
        } else{
            outcome = options[1];
        }

        if(selection.equals(outcome)){
            //Human winner
            game.getCurrent_players().get(1).setleader(true);
            curr_player = game.getCurrent_players().get(1);

        } else{
            //Computer winner
            game.getCurrent_players().get(0).setleader(true);
            curr_player = game.getCurrent_players().get(0);
        }

        logs.add("Toss output: " + outcome);
        logs.add(curr_player.getName() + " won the toss and will start. ");
    }

    /**
     This function creates a dialog box after a round ends
     @param info String, all the required information to display after each round
     @param game Game, current game
     */
    private void roundEndDialog(String info, final Game game){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        String toPrint = "";
        final String endDetails = game.getEndGameDetails();

        builder1.setCancelable(true);
        builder1.setMessage(info);
        builder1.setNegativeButton(
                "End Game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        endGameDialog(endDetails);
                    }
                });
        builder1.setPositiveButton("New Round", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (game.nextGameWinner() == -1) {
                    tossDialog(game);
                }
                game.newRound();
                updateScores(game);
                displayCards(game);
                setLeaderTurn(game);
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /**
     This function creates a dialog box after the game ends
     @param info String, all the required information to display after game ends
     */
    private void endGameDialog(String info){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        String toPrint = "";

        builder1.setCancelable(true);
        builder1.setMessage(info);
        builder1.setNegativeButton(
                "Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(1);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /**
     This function creates a dialog box to ensure if a player has selected to quit
     */
    private void quitDialog(){
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("Are you sure you want to quit?");
        builder2.setCancelable(true);

        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               System.exit(1);
            }
        });
        builder2.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder2.create();
        alert11.show();

    }

}
