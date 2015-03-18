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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
//For Dynamically Creating Buttons
import android.widget.RelativeLayout;

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
    public GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        Intent intent = getIntent();
        //grid = (GridLayout) findViewById(R.id.currentWordButtonsLayout);
        getFirstWord();
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
        newFirstWord.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

    /**
     * After the two dialog boxes have gotten the first and last word, this method
     * creates the word chain using the member first and last word.
     */
    private void createWordChain() {
//        if(!Dictionary.lookUpAll(newFirstWord.getText().toString()))
//        {
//            getFirstWord();
//        }
        Log.i(TAG_BUILDER, "First Word: " + newFirstWord.getText().toString());
        Log.i(TAG_BUILDER, "Last  Word: " + newLastWord.getText().toString());

        if (newFirstWord.getText().toString().length() < 3) {
            Log.e(TAG_BUILDER, "String too short! Abort!!!");
        }

        wordChain = new WordChain(newFirstWord.getText().toString(), newLastWord.getText().toString());

        display();
    }

    public void save(View view) {

        wordChain.save();
    }

    public void listSave (View view){
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
                        //createWordButtons(currentWord);
                        list.setAdapter(adapter);
                    }
                });
                return null;
            }
        }
        new AsyncLoad().execute();
    }

    /** This method dynamically creates either three, four, or five buttons for
     * the current word, depending on the word length of the first and last words.
     *
     * @param currentWord word to spell out on the buttons
     */
    public void createWordButtons(String currentWord) {

        //grid.rowCount(wordChain.getWordLength());
        for(Integer i = 0; i < wordChain.getWordLength(); i++) {
            // Creating a new Left Button
            Button button = new Button(this);
            button.setText(currentWord.toCharArray()[i]);

            addButtonLayout(button, RelativeLayout.ALIGN_PARENT_LEFT);

            //RelativeLayout.addView(button);
        }

    }

    private void LayoutAddButton(Button button, int centerInParent, int marginLeft, int marginTop, int marginRight, int marginBottom) {

        // Defining the layout parameters of the Button
        RelativeLayout.LayoutParams buttonLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Add Margin to the LayoutParameters
        buttonLayoutParameters.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        // Add Rule to Layout
        buttonLayoutParameters.addRule(centerInParent);

        // Setting the parameters on the Button
        button.setLayoutParams(buttonLayoutParameters);

    }

    private void addButtonLayout(Button button, int centerInParent) {
        LayoutAddButton(button, centerInParent, 0, 0, 0, 0);
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
