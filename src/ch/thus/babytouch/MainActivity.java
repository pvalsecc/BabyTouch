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
