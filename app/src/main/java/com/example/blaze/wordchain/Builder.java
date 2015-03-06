package com.example.blaze.wordchain;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


public class Builder extends ActionBarActivity {

    private WordChain wordChain = new WordChain((String)"warm", (String) "cold");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_page);

        Intent intent = getIntent();
        display();
    }

    public void save(View view) {
        //TEST:
        EditText currentWord = (EditText) findViewById(R.id.currentWord);
        if (wordChain.dictionary.lookUpCommon(currentWord.getText().toString())) {
            wordChain.next(currentWord.getText().toString());
            display();
        } else if (wordChain.dictionary.lookUpAll(currentWord.getText().toString())) {
            wordChain.next(currentWord.getText().toString());
            display();
        }
        //wordChain.save();
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
