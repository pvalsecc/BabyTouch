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

import android.annotation.TargetApi;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.ViewGroup;

@TargetApi(11)
public class Utils11 implements UtilsI {
    public void makeSystemBarHidden(final View view) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);

        view.setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
            public void onSystemUiVisibilityChange(final int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_LOW_PROFILE) == 0) {
                    // the bar is back, make it go away again in 1s
                    view.getHandler().postDelayed(new Runnable() {
                        public void run() {
                            view.setSystemUiVisibility(visibility | View.SYSTEM_UI_FLAG_LOW_PROFILE);
                        }
                    }, 1000);
                }
            }
        });

        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); ++i) {
                makeSystemBarHidden(vg.getChildAt(i));
            }
        }
    }

    public void makeHardwareAccel(View view) {
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); ++i) {
                makeHardwareAccel(vg.getChildAt(i));
            }
        }
    }
}
