package com.example.blaze.wordchain;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class WordChain {

    private String firstWord;
    private String lastWord;
    private String currentWord;
    private Integer wordLength;
    public ArrayList<String> chain = new ArrayList<>();
    public Dictionary dictionary;
    private Integer points;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_word_chain);
//    }

    WordChain() {

    }

    /**
     * Basic constructor
     * @param first - the first word in the chain
     * @param last - the last or "target" word in the chain
     */
    WordChain(String first, String last, ArrayList<String> arrayList) {
        setFirstWord(first);
        setCurrentWord(first);
        setLastWord(last);
        setWordLength(getFirstWord().length());
        Context context = GlobalVars.getAppContext();
        dictionary = new Dictionary(context, getWordLength());
        if (arrayList == null) {
            chain.add(firstWord);
        }
        else {
            chain = arrayList;
        }
        setPoints(0);
    }

    public String  getFirstWord()                       { return firstWord; }
    public String  getCurrentWord()                     { return chain.get(chain.size() - 1); }
    public String  getLastWord()                        { return lastWord; }
    public Integer getWordLength()                      { return wordLength; }
    public Integer getPoints()                          { return points; }
    public void setFirstWord(String newFirstWord)       { firstWord = newFirstWord; }
    public void setCurrentWord(String newCurrentWord)   { currentWord = newCurrentWord; }
    public void setLastWord(String newLastWord)         { lastWord = newLastWord; }
    public void setWordLength(Integer newWordLength)    { wordLength = newWordLength; }
    public void setPoints(Integer newPoints)            { points = newPoints; }

    /**
     * Removes the most recently added word from the list
     */
    public void undo() {
        if (chain != null && !chain.isEmpty() && chain.size() > 1) { //if there is something in the array other than the first word. . .
            chain.remove(chain.size() - 1); //. . .delete the most recent word
        }
    }

    /**
     * delete everything but the first word
     */
    public void clear() {
        chain.clear();
        chain.add(firstWord); //put the first word back

    }

    /**
     * Checks to ensure a word is valid (usually used before adding)
     * @param currentWord - the word being validated
     * @return whether or not the word is valid
     */
    private boolean validate(String currentWord) {
        if (currentWord.length() == getWordLength()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds a word to the chain
     * @param currentWord - the word the user is attempting to add
     * @return whether the word was successfully added
     */
    public boolean next(String currentWord) {
        if (validate(currentWord)) {
            chain.add(currentWord); //if it is valid, append it to the array
            return true;
        }
        else {
            return false;
        }
    }

    public Integer calculatePoints() {
        return null;
    }

    /**
     * Writes the chain into a file, converting it into a String beforehand
     */
    public void save() {
        Context context = GlobalVars.getAppContext();
        String fullList = "";
        int i;
        chain.add(chain.size(), getLastWord());
        File file = new File(context.getFilesDir(), "chainList.txt");
        for (i = 0; i < chain.size(); i++) {
            fullList = fullList + chain.get(i) + " ";
        }
        BufferedWriter fout = null;
        try {
            fout = new BufferedWriter(new FileWriter(file, true));
            fout.newLine();
            fout.write(fullList);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
