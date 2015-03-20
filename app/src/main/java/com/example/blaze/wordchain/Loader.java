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
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Loader extends ActionBarActivity {

    public List<WordChain> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        load();
    }

    public void load() {
        Context context = GlobalVars.getAppContext();
        String nextline = "";
        int i = 0;
        String nextArray[];
        WordChain tempChain = null;
        BufferedReader br = null;
        try {
            File loadFile = new File(context.getFilesDir(), "chainList.txt");
            br = new BufferedReader(new FileReader(loadFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while ((nextline = br.readLine()) != null) {
                System.out.println(nextline);
                nextArray = nextline.split(" ");
                int arraySize = nextArray.length - 1;
                tempChain = new WordChain(nextArray[0], nextArray[arraySize]);
                list.add(tempChain);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayList();
    }

    public void displayList() {
        Context context = GlobalVars.getAppContext();
        ArrayList<String> arrayList = new ArrayList<>();
        String line;
        for(int i = 0; i < list.size(); i++) {
            line = list.get(i).getFirstWord() + " -> " + list.get(i).getLastWord();
            arrayList.add(line);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        ListView writeScreen = (ListView) findViewById(R.id.listView);
        writeScreen.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_page, menu);
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
