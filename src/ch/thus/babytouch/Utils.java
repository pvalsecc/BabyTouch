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
    }

    static public void makeSystemBarHidden(View view) {
        if (impl != null) {
            impl.makeSystemBarHidden(view);
        }
    }

    static public void makeHardwareAccel(View view) {
        if (impl != null) {
            impl.makeHardwareAccel(view);
        }
    }

    public static float bound(float val, float min, float max) {
        return Math.max(Math.min(val, max), min);
    }
}
