package ch.thus.babytouch;

import ch.thus.babytouch.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

public class DrawActivity extends Activity {
    /** The view responsible for the touch painting. */
    private TouchView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // Create and attach the view that is responsible for painting.
        setContentView(R.layout.activity_draw);

        mView = (TouchView) findViewById(R.id.touch_view);
        mView.setDensityDpi(dm.densityDpi);

        ColorSelector2 colorSelector = (ColorSelector2) findViewById(R.id.color_selector_view);
        mView.setColorSelector(colorSelector);

        View mainView = findViewById(R.id.draw_view);
        Utils.makeSystemBarHidden(mainView);
        Utils.makeHardwareAccel(mainView);
    }

    @Override
    public void onBackPressed() {
        if (mView.isCleared()) {
            super.onBackPressed();
        } else {
            mView.clear();
        }
    }

}
