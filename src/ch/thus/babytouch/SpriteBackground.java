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

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SpriteBackground extends ImageView {

    public SpriteBackground(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // if the baby touches the background with a finger, you don't want it
        // to be taken into account as a touch and
        // disable the touch on the sprites.
        return true;
    }

}
