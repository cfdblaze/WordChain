package com.example.blaze.wordchain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstStart = settings.getBoolean("firstStart", true);
        if (firstStart) {
            Context context = this.getApplicationContext();
            File file = new File(context.getFilesDir(), "chainList.txt");
            BufferedWriter fout = null;
            try {
                fout = new BufferedWriter(new FileWriter(file));
                fout.write("");
                fout.close();
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("firstStart", false);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called when the "Build" button on the main
     * page is selected, and starts a new activity.
     * @param view
     */
    public void build(View view) {
        Intent build = new Intent(this, Builder.class);
        startActivity(build);
    }

    /**
     * This method is called when the "Solve" button on the main
     * page is selected, and starts a new activity.
     * @param view
     */
    public void solve(View view) {
        Intent solve = new Intent(this, Solver.class);
        startActivity(solve);
    }

    /**
     * This method is called when the "Load" button on the main
     * page is selected, and starts a new activity.
     * @param view
     */
    public void load(View view) {
        Intent load = new Intent(this, Loader.class);
        startActivity(load);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
