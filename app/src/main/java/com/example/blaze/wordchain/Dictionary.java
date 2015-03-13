package com.example.blaze.wordchain;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import android.content.res.AssetManager;
import android.util.Log;

/**
 * Created by Blaze on 2/21/2015.
 */
public class Dictionary {
    private ArrayList<String> commonWordList = new ArrayList<>();
    private static String wordXML = null;
    public static void setWordXML(String xmlPass){
        wordXML = xmlPass;
    }
    public boolean lookUpCommon(String wordCheck){
        if (commonWordList.contains(wordCheck)){
            return true;
        }
        else {
            return false;
        }
    };
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
        try {
            checkThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Data reads: " + wordXML);
        return wordXML.contains("<def>");
    };
    //public boolean parseXML(String XMLToParse) {
    //    System.out.println("Data reads: " + XMLToParse);
    //    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
    //    DocumentBuilder dbuilder = null;
    //    Tester tester = new Tester();
    //    tester.testXML(XMLToParse);
    //    try {
    //        dbuilder = dbfactory.newDocumentBuilder();
    //    } catch (ParserConfigurationException e) {
    //       System.out.println("Parser misconfigured.");
    //    }
    //    Document doc = null;
    //    try {
    //        doc = dbuilder.parse(XMLToParse);
    //    } catch (IOException e) {
    //        System.out.println("I/O Error.");
    //    }
    //    catch (SAXException e) {
    //        System.out.println("SAX Exception.");
    //    }
    //    NodeList nList = doc.getElementsByTagName("def");
    //    if (nList.getLength() > 0)
    //        return true;
    //    else
    //        return false;
    //}
    Dictionary(Context context, Integer wordLength){
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
                Log. e("DICTIONARY", "Word too short!!! Abort!!!");
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
    }
}
