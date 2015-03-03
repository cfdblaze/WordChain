package com.example.blaze.wordchain;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Loader extends ActionBarActivity {

    public List<WordChain> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_page);

        Intent intent = getIntent();
    }

    public void load() {
//      Eliska's code from Multithread assignment:
//
//        public void load(View view) {
//
//            class AsyncLoad extends AsyncTask<String, Integer, String> {
//
//                ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
//
//                @Override
//                protected String doInBackground(String... params) {
//                    String fileName = "numbers.txt";
//                    File file = new File(getFilesDir(), fileName);
//
//                    String[] readNumbers = new String[10];
//                    final ArrayList<String> arrayList = new ArrayList();
//
//                    try {
//                        BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
//                        String inputString;
//                        StringBuffer stringBuffer = new StringBuffer();
//                        while ((inputString = inputReader.readLine()) != null) {
//
//                            arrayList.add(inputString);
//                            Thread.sleep(250);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    final ListView list = (ListView) findViewById(R.id.listView);
//
//                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            list.setAdapter(adapter);
//                        }
//                    });
//                    return null;
//                }
//
//                @Override
//                protected void onProgressUpdate(Integer... values) {
//                    progress.setProgress(values[0]);
//                }
//            }
//            new AsyncLoad().execute();
//        }

    }

    public void displayList() {

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
