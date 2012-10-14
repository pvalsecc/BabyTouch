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
