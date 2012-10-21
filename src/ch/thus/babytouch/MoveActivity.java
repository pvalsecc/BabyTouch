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

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MoveActivity extends Activity {

    private RelativeLayout mainView;
    private static final int RESOURCES[][] = { { R.drawable.cat, R.raw.cat }, { R.drawable.dog, R.raw.dog },
            { R.drawable.cow, R.raw.cow }, { R.drawable.elephant, R.raw.elephant }, { R.drawable.elk, R.raw.elk },
            { R.drawable.goat, R.raw.goat }, { R.drawable.owl, R.raw.owl }, { R.drawable.sheep, R.raw.sheep }, };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainView = new RelativeLayout(this);

        ImageView background = new SpriteBackground(this);
        background.setBackgroundResource(R.drawable.background);
        background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RelativeLayout.LayoutParams bgLP = new RelativeLayout.LayoutParams(0, 0);
        bgLP.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        bgLP.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        bgLP.topMargin = 0;
        bgLP.leftMargin = 0;
        mainView.addView(background, bgLP);

        setContentView(mainView);
        System.gc();
        createSprites();
        Utils.makeSystemBarHidden(mainView);
        Utils.makeHardwareAccel(mainView);
    }

    @SuppressWarnings("deprecation")
    private void addRandomSprite(int imageRes, int soundRes) {
        int totalWidth = getWindowManager().getDefaultDisplay().getWidth();
        int totalHeight = getWindowManager().getDefaultDisplay().getHeight();
        int size = totalWidth / 8;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        params.leftMargin = (int) Math.round(Math.random() * (totalWidth - size));
        params.topMargin = (int) Math.round(Math.random() * (totalHeight - size));

        mainView.addView(new Sprite(this, imageRes, soundRes), params);
    }

    private void createSprites() {
        for (int i = 0; i < RESOURCES.length; ++i) {
            addRandomSprite(RESOURCES[i][0], RESOURCES[i][1]);
        }
    }
}
