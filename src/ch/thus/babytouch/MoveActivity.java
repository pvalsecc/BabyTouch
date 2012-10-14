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
        // mainView.addOnLayoutChangeListener(this);
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
