package com.example.blaze.wordchain;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WordChain {

    private String firstWord;
    private String lastWord;
    private Integer wordLength;
    public List<String> chain = new ArrayList<>();
    //private Dictionary dictionary;
    private Integer points;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_word_chain);
//    }

    WordChain() {

    }

    WordChain(String first, String last) {
        setFirstWord(first);
        setLastWord(last);
        if(first.length() == last.length()) {
            setWordLength(getFirstWord().length());
        }
        //else: throw some sort of error
        chain.add(firstWord);
        setPoints(0);
    }

    public String  getFirstWord()                       { return firstWord; }
    public String  getLastWord()                        { return lastWord; }
    public Integer getWordLength()                      { return wordLength; }
    public Integer getPoints()                          { return points; }
    public void setFirstWord(String newFirstWord)       { firstWord = newFirstWord; }
    public void setLastWord(String newLastWord)         { lastWord = newLastWord; }
    public void setWordLength(Integer newWordLength)    { wordLength = newWordLength; }
    public void setPoints(Integer newPoints)            { points = newPoints; }

    public void undo() {
        if (chain != null && !chain.isEmpty() && chain.size() > 1) { //if there is something in the array other than the first word. . .
            chain.remove(chain.size() - 1); //. . .delete the most recent word
        }
    }

    public void clear() {
        //delete everything but the first word
        chain.clear();
        chain.add(firstWord); //put the first word back

    }

    private boolean validate(String currentWord) {
        //dictionary look up
        if (currentWord.length() == getWordLength()) {
            return true;
        }
        else {
            return false;
        }
    }

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

    public void save() {
        //save to a text file
    }
}
