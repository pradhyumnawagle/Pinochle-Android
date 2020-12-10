/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/

package edu.ramapo.pwagle.pinochle.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import edu.ramapo.pwagle.pinochle.R;

public class InitialActivity extends AppCompatActivity {

    private Button new_game, load_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        new_game = (Button) findViewById(R.id.newgame_btn);
        load_game = (Button) findViewById(R.id.loadgame_btn);

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tossDialog();
            }
        });

        load_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                files_dialog();
            }
        });

    }

    /**
     This function creates a dialog box with the list of files to load
     */
    private void files_dialog(){

        File f = new File(getExternalFilesDir("MyFiles").getAbsolutePath());

        File files[] = f.listFiles();

        final String[] items = new String[files.length];

        for(int i=0;i<files.length;i++){
            items[i] = files[i].getName();
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println(items[which]);
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                intent.putExtra("fileName",items[which]);
                startActivity(intent);
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /**
     This function creates a dialog box for toss and sends the selection to MainActivity intent
     */
    private void tossDialog(){

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
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                intent.putExtra("tossSelection",items[which]);
                startActivity(intent);
            }
        });


        AlertDialog alert11 = builder3.create();
        alert11.show();
    }

}
