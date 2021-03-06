package com.example.blaze.wordchain;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.util.Log;

/**
 * Created by Corey on 2/21/2015.
 */
public class Dictionary {
    private static ArrayList<String> commonWordList = new ArrayList<>();
    private static String wordXML = null;
    public static void setWordXML(String xmlPass){
        wordXML = xmlPass;
    }

    /**
     * Checks if the word is on a list of common words
     * @param wordCheck - word to be checked
     * @return whether the word is considered common
     */
    public static boolean lookUpCommon(String wordCheck){
        if (commonWordList.contains(wordCheck)){
            return true;
        }
        else {
            return false;
        }
    };

    /**
     * Uses on online dictionary API to see if the word exists at all
     * @param wordCheck - word to be checked
     * @return whether the word exists
     */
    public static boolean lookUpAll(String wordCheck){
        final String wordLook = wordCheck;
        final Thread checkThread = new Thread() {
            @Override
            public void run() {
                String xmlData = null;
                String uri = "http://www.dictionaryapi.com/api/v1/references/collegiate/xml/" + wordLook;
                uri = uri + "?key=0ea7d1b6-2729-47fc-af60-1aee785807e9";

                try {
                    // defaultHttpClient
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(uri);

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    int httpcode = httpResponse.getStatusLine().getStatusCode();
                    Tester tester = new Tester();
                    tester.responseTest(httpcode);
                    xmlData = EntityUtils.toString(httpEntity);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setWordXML(xmlData);
            }
        };
        checkThread.start();
        Log.i("Dictionary", "Thread started.");
        try {
            checkThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("Dictionary", "Online dictionary XML: " + wordXML);
        return wordXML.contains("<def>");
    };

    /**
     * Nondefault constructor
     * @param context - passes MainActivity.this context to use files
     * @param wordLength - used to get list of common words
     */
    Dictionary(Context context, int wordLength){
        BufferedReader br = null;
        try {
            String filename = null;
            if (wordLength == 3) {
                filename = "three_letter_words.txt";
            }
            else if (wordLength == 4) {
                filename = "four_letter_words.txt";
            }
            else if (wordLength == 5) {
                filename = "five_letter_words.txt";
            }
            else {
                Log.e("Dictionary", "Word length " + wordLength + " does not correspond to a file.");
            }
            InputStream fin = context.getAssets().open(filename);
            br = new BufferedReader(new InputStreamReader(fin));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File I/O Exception");
            e.printStackTrace();
        }
        String nextline;
        try {
            while ((nextline = br.readLine()) != null) {
                commonWordList.add(nextline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
