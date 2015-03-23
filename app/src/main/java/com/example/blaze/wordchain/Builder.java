package com.example.blaze.wordchain;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * This class is linked to the Builder page that allows the user
 * to create a word chain. It will read words into a list and
 * have the user add words using pop up boxes and buttons. The
 * class also contains undo, clear, and save capabilities.
 */
public class Builder extends ActionBarActivity {

    private static final String TAG_BUILDER = "Builder";
    private WordChain wordChain;
    private EditText newFirstWord;
    private EditText newLastWord;
    private TextView currentChildView;
    private EditText newLetter;
    public GridView grid;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        Intent intent = getIntent();

        Bundle bundle = getIntent().getExtras();

        if(bundle == null) {
            getFirstWord();
        }
        else {
            String newFirstWord = bundle.getString("firstWord");
            String newLastWord = bundle.getString("lastWord");
            ArrayList<String> newWordList = bundle.getStringArrayList("wordChain");
            newWordList.remove(newWordList.size() - 1);
            wordChain = new WordChain(newFirstWord, newLastWord, newWordList);
            display();
        }
    }

    /**
     * This method creates a popup box that prompts the user for the first word
     * of their word chain. It then calls getLastWord() after it finishes.
     */
    private void getFirstWord() {

        //Get First Word dialog box
        newFirstWord = new EditText(this);
        final AlertDialog.Builder firstWordBuilder = new AlertDialog.Builder(this);
        firstWordBuilder.setTitle("First Word");
        firstWordBuilder.setCancelable(false);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        newFirstWord.setInputType(InputType.TYPE_CLASS_TEXT);
        firstWordBuilder.setView(newFirstWord);

        // Set up the buttons
        firstWordBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!Dictionary.lookUpAll(newFirstWord.getText().toString())) {
                    Log.e(TAG_BUILDER, "Invalid word!!!");
                }
                getLastWord();
            }
        });
        firstWordBuilder.show();
    }

    /**
     * This method creates a popup box that prompts the user for the last word
     * of their word chain. It then calls createWordChain() after it finishes.
     */
    private void getLastWord() {

        //Get Last Word dialog box
        newLastWord = new EditText(this);
        AlertDialog.Builder lastWordBuilder = new AlertDialog.Builder(this);
        lastWordBuilder.setTitle("Last Word");
        lastWordBuilder.setCancelable(false);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        newLastWord.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        lastWordBuilder.setView(newLastWord);

        // Set up the buttons
        lastWordBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!Dictionary.lookUpAll(newFirstWord.getText().toString())) {
                    Log.e(TAG_BUILDER, "Invalid word!!!");
                }
                createWordChain();
            }
        });
        lastWordBuilder.show();
    }

    /**
     * After the two dialog boxes have gotten the first and last word, this method
     * creates the word chain using the member first and last word.
     */
    private void createWordChain() {
        wordChain = new WordChain(newFirstWord.getText().toString(), newLastWord.getText().toString(), null);
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

    public void next(String currentWord) {

        wordChain.next(currentWord);
        display();
    }

    /**
     *  Uses and AsyncLoad to reload the ListView in the background every time a word is changed
     *  or a button is pressed.
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
                        TextView lastWordText = (TextView) findViewById(R.id.lastWord);
                        lastWordText.setText(wordChain.getLastWord());

                        list.setAdapter(adapter);
                    }
                });
                return null;
            }
        }
        new AsyncLoad().execute();
        createWordButtons(wordChain.getCurrentWord());
    }

    /** This method dynamically creates either three, four, or five buttons for
     * the current word, depending on the word length of the first and last words.
     *
     * @param currentWord word to spell out on the buttons
     */
    public void createWordButtons(String currentWord) {

        //Get grid view
        Log.e(TAG_BUILDER, "Current Word in Buttons: " + wordChain.getCurrentWord());
        grid = (GridView) findViewById(R.id.currentWordButtons);

        //set and fill a string array
        grid.setNumColumns(wordChain.getWordLength());
        final String[] currentWordArray = new String[wordChain.getWordLength()];
        char[] currentWordLetters = currentWord.toCharArray();
        for (Integer i = 0; i < wordChain.getWordLength(); i++) {
            currentWordArray[i] = "" + currentWordLetters[i];
        }

        //Set adapter with the string array
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currentWordArray);
        grid.setAdapter(adapter);

        //When the user hits the button...
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentChildView = (TextView) view;

                //...Get New Letter dialog box...
                newLetter = new EditText(Builder.this);
                AlertDialog.Builder newLetterBuilder = new AlertDialog.Builder(Builder.this);
                newLetterBuilder.setTitle("New Letter");
                newLetterBuilder.setCancelable(false);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                newLetter.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE | InputType.TYPE_CLASS_TEXT);
                newLetterBuilder.setView(newLetter);
                // Set up the button
                newLetterBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nextWord = "";
                        TextView gridChild = null;
                        char[] nextLetter = newLetter.getText().toString().toCharArray();
                        Log.e(TAG_BUILDER, "New Letter: " + newLetter.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                currentChildView.setText(newLetter.getText().toString());
                                Log.e(TAG_BUILDER, "Grid Child new Letter: " + newLetter.getText().toString());
                            }
                        });
                        currentChildView.setText(newLetter.getText().toString());

                        for (Integer i = 0; i < wordChain.getWordLength(); i++) {
                            gridChild = (TextView) grid.getChildAt(i);
                            nextWord += gridChild.getText();
                        }

                        next(nextWord);

                        if (!Dictionary.lookUpAll(newFirstWord.getText().toString())) {
                            Log.e(TAG_BUILDER, "Invalid word!!!");
                        }
                    }
                });
                newLetterBuilder.show();
                //currentChildView.setText(newLetter.getText().toString());
            }
        });
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
