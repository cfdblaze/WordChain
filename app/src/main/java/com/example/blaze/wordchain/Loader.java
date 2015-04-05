package com.example.blaze.wordchain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is linked to the Loader, which creates a list from a saved
 * file and displays it.  The user can select one of the options from the list
 * and continue working on that chain.
 */
public class Loader extends ActionBarActivity {

    public List<WordChain> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        load();

        //When an item is clicked, sends a bundle to Builder
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Loader.this, Builder.class);
                Bundle bundle = new Bundle();
                bundle.putString("firstWord", list.get(position).getFirstWord());
                bundle.putString("lastWord", list.get(position).getLastWord());
                bundle.putStringArrayList("wordChain", list.get(position).chain);
                intent.putExtras(bundle);
                Loader.this.startActivity(intent);
            }
        });
    }

    /**
    * Loads the chainList file, turns the file into an array of Strings,
     */
    public void load() {
        Context context = GlobalVars.getAppContext();
        String nextline = "";
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
                if (!nextline.equals("")) {
                    System.out.println(nextline);
                    nextArray = nextline.split(" ");
                    int arraySize = nextArray.length - 1;
                    ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(nextArray));
                    tempChain = new WordChain(nextArray[0], nextArray[arraySize], stringList);
                    list.add(tempChain);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayList();
    }

    /**
     * Displays the array of Strings from load() in a ListView
     */
    public void displayList() {
        Context context = GlobalVars.getAppContext();
        ArrayList<String> arrayList = new ArrayList<>();
        String line;
        for(int i = 0; i < list.size(); i++) {
            line = list.get(i).getFirstWord() + " -> " + list.get(i).getLastWord();
            arrayList.add(line);
        }

        //When creating the adapter, overrides getView to ensure text in the ListView is black
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };
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
