package ch.thus.babytouch;

import ch.thus.babytouch.R;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    private int backCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View mainView = findViewById(R.id.main_view);
        Utils.makeSystemBarHidden(mainView);
        Utils.makeHardwareAccel(mainView);
    }

    public void moveClick(View v) {
        backCount = 0;
        startActivity(new Intent(this, MoveActivity.class));
    }

    public void drawClick(View v) {
        backCount = 0;
        startActivity(new Intent(this, DrawActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (++backCount >= 3) {
            super.onBackPressed();
        }
    }
}
