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

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Sprite extends ImageView {
    private float prevX;
    private float prevY;
    private int soundRes;
    private long lastPlayTime;

    public Sprite(Context context, int imageRes, int soundRes) {
        super(context);
        setImageResource(imageRes);
        this.soundRes = soundRes;
        lastPlayTime = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
            RelativeLayout parent = (RelativeLayout) getParent();
            layoutParams.leftMargin = Math.round(Utils.bound(layoutParams.leftMargin + event.getX() - prevX, 0,
                    parent.getWidth() - getWidth()));
            layoutParams.topMargin = Math.round(Utils.bound(layoutParams.topMargin + event.getY() - prevY, 0,
                    parent.getHeight() - getHeight()));
            getParent().requestLayout();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            prevX = event.getX();
            prevY = event.getY();
            playSound();
        }
        return true;
    }

    @SuppressLint("NewApi")
    private void playSound() {
        long now = System.currentTimeMillis();
        if (now >= lastPlayTime + 200) {
            lastPlayTime = now;
            MediaPlayer mp = MediaPlayer.create(getContext(), soundRes);
            mp.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();
        }
    }
}
