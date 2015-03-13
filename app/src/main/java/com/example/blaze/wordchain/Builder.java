package com.example.blaze.wordchain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class Builder extends ActionBarActivity {

    private static final String TAG_BUILDER = "Builder";
    private WordChain wordChain;
    private EditText newFirstWord;
    private EditText newLastWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        Intent intent = getIntent();

        getFirstWord();
    }

    private void getFirstWord() {
        //Get First Word dialog box
        newFirstWord = new EditText(this);
        AlertDialog.Builder firstWordBuilder = new AlertDialog.Builder(this);
        firstWordBuilder.setTitle("First Word");
        firstWordBuilder.setCancelable(false);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        newFirstWord.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        firstWordBuilder.setView(newFirstWord);

        // Set up the buttons
        firstWordBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG_BUILDER, "First Word: " + newFirstWord.getText().toString());
                getLastWord();
            }
        });
//        firstWordBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });

        Log.i(TAG_BUILDER, "Starting call to show");
        firstWordBuilder.show();
        Log.i(TAG_BUILDER, "After call to show");

        //AlertDialog firstWordPopUp = firstWordBuilder.create();
        //firstWordPopUp.show();

    }

    private void getLastWord() {
        //Get Last Word dialog box
        newLastWord = new EditText(this);
        AlertDialog.Builder lastWordBuilder = new AlertDialog.Builder(this);
        lastWordBuilder.setTitle("First Word");
        lastWordBuilder.setCancelable(false);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        newLastWord.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        lastWordBuilder.setView(newLastWord);

        // Set up the buttons
        lastWordBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG_BUILDER, "Last  Word: " + newLastWord.getText().toString());
                createWordChain();
            }
        });
//        lastWordBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });

        lastWordBuilder.show();

        //AlertDialog lastWordPopUp = lastWordBuilder.create();
        //lastWordPopUp.show();
    }

    private void createWordChain() {
        Log.i(TAG_BUILDER, "First Word: " + newFirstWord.getText().toString());
        Log.i(TAG_BUILDER, "Last  Word: " + newLastWord.getText().toString());
        Log.i(TAG_BUILDER, "Building Word Chain . . .");

        if (newFirstWord.getText().toString().length() < 3) {
            Log.e(TAG_BUILDER, "String too short! Abort!!!");
        }

        wordChain = new WordChain(newFirstWord.getText().toString(), newLastWord.getText().toString());

        display();
    }

    public void save(View view) {

        wordChain.save();
    }

    public void undo(View view) {
        wordChain.undo();
        display();
    }

    public void clear(View view) {
        wordChain.clear();
        display();
    }

    /**
     * Function: Display
     *
     * Uses and AsyncLoad to reload the ListView in the background every time a word is changed
     */
    public void display() {

        class AsyncLoad extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... params) {
                final ListView list = (ListView) findViewById(R.id.listView);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Builder.this, android.R.layout.simple_list_item_1, wordChain.chain);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.setAdapter(adapter);
                    }
                });
                return null;
            }
        }
        new AsyncLoad().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_build_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
