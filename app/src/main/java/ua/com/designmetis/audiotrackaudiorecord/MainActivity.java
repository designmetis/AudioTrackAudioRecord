package ua.com.designmetis.audiotrackaudiorecord;

import android.app.Activity;
import android.media.AudioRecord;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    AudioRec audioRec = new AudioRec();
    String TAG = "myLogRec";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // определяем кнопки

        Button actionButton = (Button) findViewById(R.id.record);
        //устанавливаем обработчик кнопки записи
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            audioRec.record();
                Log.d(TAG, "onClick butt rec");
            }
        });

        //находим кнопку play и устанавливаем обработчик для нее
        Button replayButton = (Button) findViewById(R.id.play);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            audioRec.play();
                Log.d(TAG, "onClick butt play");
            }
        });
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
