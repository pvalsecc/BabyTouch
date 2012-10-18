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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorSelector extends View {
    private final Paint paint;
    private int curColor;
    private boolean random;
    private RectF rect;
    private Bitmap dieBitmap;
    private float initialTouchX = 0;

    public ColorSelector(Context c, AttributeSet attrs) {
        super(c, attrs);
        paint = new Paint();
        curColor = Color.RED;
        random = true;
        shakeIt();
        rect = new RectF();
        dieBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.six_sided_dice);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int w = getWidth();
        final int h = getHeight();

        final float gradientHeight = h - (w * 2);
        final int nbColors = (h - w) / 3;

        // background
        paint.setColor(Color.DKGRAY);
        canvas.drawPaint(paint);

        // cur color
        final float curColMargin = w * 1.0f / 10.0f;
        paint.setColor(curColor);
        paint.setStyle(Style.FILL);
        rect.set(curColMargin, curColMargin, w - curColMargin, w - curColMargin);
        canvas.drawRoundRect(rect, curColMargin, curColMargin, paint);

        // Gradient
        float hsv[] = { 0.0f, 1.0f, 1.0f };
        for (int i = 0; i < nbColors; ++i) {
            hsv[0] = i * 360.0f / nbColors;
            paint.setColor(Color.HSVToColor(255, hsv));
            canvas.drawRect(0, w + gradientHeight * i / nbColors, w, w + gradientHeight * (i + 1) / nbColors, paint);
        }

        // random
        paint.setColor(random ? Color.GREEN : Color.RED);
        rect.set(curColMargin, h - w + curColMargin, w - curColMargin, h - curColMargin);
        canvas.drawRoundRect(rect, curColMargin, curColMargin, paint);
        rect.set(curColMargin * 2, h - w + curColMargin * 2, w - curColMargin * 2, h - curColMargin * 2);
        canvas.drawBitmap(dieBitmap, null, rect, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int w = getWidth();
        int h = getHeight();
        float y = event.getY();
        float x = event.getX();
        float topGradient = w;
        float bottomGradient = h - w;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialTouchX = x;
        }

        if (topGradient <= y && y <= bottomGradient) {
            float hsv[] = { 0.0f, 1.0f, 1.0f };
            hsv[0] = (y - topGradient) / (bottomGradient - topGradient) * 360.0f;
            hsv[1] = 1.0f - Math.min(Math.max((x - initialTouchX) / (w * 3), 0.0f), 1.0f);
            curColor = Color.HSVToColor(255, hsv);
            random = false;
            redrawCurColor();
            redrawRandom();
            return true;
        } else if (y > bottomGradient) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                random = !random;
                shakeIt();
                redrawRandom();
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    public int getCurColor() {
        return curColor;
    }

    public void shakeIt() {
        if (random) {
            float hsv[] = { 0.0f, 1.0f, 1.0f };
            hsv[0] = ((float) Math.random()) * 360.0f;
            curColor = Color.HSVToColor(255, hsv);
            redrawCurColor();
        }
    }

    private void redrawCurColor() {
        int w = getWidth();
        invalidate(0, 0, w, w - 1);
    }

    private void redrawRandom() {
        int w = getWidth();
        int h = getHeight();
        invalidate(0, h - w + 1, w, h);
    }
}
