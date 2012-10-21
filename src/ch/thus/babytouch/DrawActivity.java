/*
 * Copyright (C) 2012 Patrick Valsecchi <pvalsecc@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.thus.babytouch;

import ch.thus.babytouch.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

public class DrawActivity extends Activity {
    /** The view responsible for the touch painting. */
    private TouchView mView;

    private TouchPressureGuesser tpg;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // Create and attach the view that is responsible for painting.
        setContentView(R.layout.activity_draw);

        mView = (TouchView) findViewById(R.id.touch_view);
        tpg=new TouchPressureGuesser(getPreferences(Activity.MODE_PRIVATE));
        mView.setTouchPressureGuesser(tpg);
        mView.setDensityDpi(dm.densityDpi);

        ColorSelector colorSelector = (ColorSelector) findViewById(R.id.color_selector_view);
        mView.setColorSelector(colorSelector);

        View mainView = findViewById(R.id.draw_view);
        Utils.makeSystemBarHidden(mainView);
        Utils.makeHardwareAccel(mainView);
    }

    @Override
    protected void onDestroy() {
    	tpg.save(getPreferences(Activity.MODE_PRIVATE));
    	super.onDestroy();
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
