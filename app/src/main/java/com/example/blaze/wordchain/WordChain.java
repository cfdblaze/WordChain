package com.example.blaze.wordchain;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordChain {

    private String firstWord;
    private String lastWord;
    private String currentWord;
    private Integer wordLength;
    public ArrayList<String> chain = new ArrayList<>();
    public Dictionary dictionary;
    private Integer points;

    public WordChain() {

    }

    /**
     * Constructor
     * @param first - the first word in the chain
     * @param last - the last or "target" word in the chain
     * @param arrayList - a possible list of words already used
     *                  (only used if called from the Loader, otherwise, it's null)
     */
    public WordChain(String first, String last, ArrayList<String> arrayList) {
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
    public Integer getPoints()                          { return calculatePoints(); }
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

    /**
     * This method is called from the Builder display, so every time something changes, so does
     * the number of points. The points are golf scoring, so a common word is worth 1 point, but
     * an uncommon word is worth 2.
     * @return
     */
    public Integer calculatePoints() {
        Context context = GlobalVars.getAppContext();
        Integer calculatedPoints = 0;
        List<String> neffList = new ArrayList<String>();
        File file = new File(context.getFilesDir(), "NeffWords.txt");
        String nextline;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((nextline = br.readLine()) != null) {
                neffList.add(nextline);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String word : chain) {
             if(Dictionary.lookUpCommon(word) || neffList.contains(word)) {
                calculatedPoints += 1;
            } else {
                 calculatedPoints += 2;
            }
        }
        setPoints(calculatedPoints);
        return calculatedPoints;
    }

    /**
     * Writes the chain into a file, converting it into a String beforehand
     */
    public void save() {
        Context context = GlobalVars.getAppContext();
        String fullList = "";
        String nextline = "";
        WordChain tempChain = null;
        BufferedReader br = null;
        String nextArray[];
        int i = 0;
        boolean flag = false;

        try {
            File loadFile = new File(context.getFilesDir(), "chainList.txt");
            br = new BufferedReader(new FileReader(loadFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //checks if the chain exists in the file
        try {
            while ((nextline = br.readLine()) != null) {
                if (!nextline.equals("")) {
                    System.out.println(nextline);
                    nextArray = nextline.split(" ");
                    int arraySize = nextArray.length - 1;
                    ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(nextArray));
                    tempChain = new WordChain(nextArray[0], nextArray[arraySize], stringList);
                    if (tempChain.getFirstWord().equals(getFirstWord()) && tempChain.getLastWord().equals(getLastWord())) {
                        flag = true;
                        break;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if the chain is not in the file, simply appends the new chain at the end of the file.
        if (flag != true) {
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
        //if the chain does exist, reads the file into an array of strings,
        //replaces the old chain with the new, then saves the array to the file again.
        else {
            String replacerline = nextline;
            ArrayList<String> fulltext = new ArrayList<>();
            try {
                File loadFile = new File(context.getFilesDir(), "chainList.txt");
                br = new BufferedReader(new FileReader(loadFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while ((nextline = br.readLine()) != null) {
                    fulltext.add(nextline);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            chain.add(chain.size(), getLastWord());
            File file = new File(context.getFilesDir(), "chainList.txt");
            for (i = 0; i < chain.size(); i++) {
                fullList = fullList + chain.get(i) + " ";
            }
            BufferedWriter fout = null;
            fulltext.set(fulltext.indexOf(replacerline), fullList);

            try {
                fout = new BufferedWriter(new FileWriter(file));
                for (i = 0; i < fulltext.size(); i++) {
                    fout.write(fulltext.get(i));
                    fout.newLine();
                }
                fout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
