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
