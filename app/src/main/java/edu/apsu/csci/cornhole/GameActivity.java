package edu.apsu.csci.cornhole;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class GameActivity extends Activity {

    TextView p2tv;
    TextView p1tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

       p2tv = (TextView) findViewById(R.id.player2score_textView3);
        p2tv.setText("0");


        p1tv = (TextView) findViewById(R.id.player1score_textView);
        p1tv.setText("0");


    }

    public void setText(int id, int score){
        if (id == 1){
           // p2tv = (TextView) findViewById(R.id.player2score_textView3);
            p2tv.setText(Integer.toString(score));
        }else if (id ==2) {
           // p1tv = (TextView) findViewById(R.id.player1score_textView);
            p1tv.setText(Integer.toString(score));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
