package ocasiocorp.ambition.View;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Ocasiorey on 6/8/2016.
 */
public class Activity_Logger extends AppCompatActivity{

    public final String LOG = getClass().getName().toString();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(LOG, "onCreate() called " + LOG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG, "OnStop() called " + LOG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "onDestroy() called " + LOG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG, "onPause() called " + LOG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG, "onResume() called " + LOG);
    }
}
