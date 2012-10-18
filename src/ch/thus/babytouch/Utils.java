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

import android.os.Build;
import android.view.View;

public class Utils {

    private static UtilsI impl;

    static {
        String cls = null;
        if (Build.VERSION.SDK_INT > 11) {
            cls = "Utils11";
        }
        if (cls != null) {
            try {
                impl = (UtilsI) Class.forName("ch.thus.babytouch." + cls).newInstance();
            } catch (Exception e) {
            }
        }
        if (impl == null) {
            impl = new Utils10();
        }
    }

    static public void makeSystemBarHidden(View view) {
        impl.makeSystemBarHidden(view);
    }

    static public void makeHardwareAccel(View view) {
        impl.makeHardwareAccel(view);
    }

    public static float bound(float val, float min, float max) {
        return Math.max(Math.min(val, max), min);
    }
}
