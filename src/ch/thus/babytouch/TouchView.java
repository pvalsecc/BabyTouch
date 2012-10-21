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

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TouchView extends View {
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private final Paint paint;
    private final Rect mRect;
    private float maxWidth;
    private static final int BgColor = Color.BLACK;
    private boolean cleared;

    public TouchView(Context c, AttributeSet attrs) {
        super(c, attrs);

        maxWidth = 30;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Cap.ROUND);
        mRect = new Rect();
        cleared = false;
    }

    public void setDensityDpi(int densityDpi) {
        maxWidth = densityDpi / 3;
    }

    public void clear() {
        if (mCanvas != null) {
            paint.setColor(BgColor);
            mCanvas.drawPaint(paint);
            invalidate();
        }
        cleared = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int curW = mBitmap != null ? mBitmap.getWidth() : 0;
        int curH = mBitmap != null ? mBitmap.getHeight() : 0;
        if (curW >= w && curH >= h) {
            return;
        }

        if (curW < w) curW = w;
        if (curH < h) curH = h;

        Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.RGB_565);
        Canvas newCanvas = new Canvas();
        newCanvas.setBitmap(newBitmap);
        if (mBitmap != null) {
            newCanvas.drawBitmap(mBitmap, 0, 0, null);
        }
        mBitmap = newBitmap;
        mCanvas = newCanvas;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    private class PointInfo {
        private float x, y, size;

        public PointInfo(float x, float y, float size, float pressure) {
            this.x = x;
            this.y = y;
            float adjustedSize=tpg.getAdjusted(size, pressure);
            this.size = Math.max(Math.min(adjustedSize * maxWidth, maxWidth), 1.0f);
        }
    }

    private ArrayList<PointInfo> prevPoints = new ArrayList<PointInfo>();
    private ColorSelector colorSelector;
	private TouchPressureGuesser tpg;

    public void doPoint(int ptrId, float x, float y, float size, float pressure) {
        PointInfo newPoint = new PointInfo(x, y, size, pressure);
        drawLine(prevPoints.get(ptrId), newPoint);
        prevPoints.set(ptrId, newPoint);
        cleared = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_POINTER_DOWN:
            int downPtrIdx = event.getActionIndex();
            int downPtrId = event.getPointerId(downPtrIdx);
            while (prevPoints.size() <= downPtrId) {
                prevPoints.add(null);
            }
            prevPoints.set(
                    downPtrId,
                    new PointInfo(event.getX(downPtrIdx), event.getY(downPtrIdx), event.getSize(downPtrIdx), event
                            .getPressure(downPtrIdx)));
        }

        paint.setColor(colorSelector.getCurColor());
        for (int ptrIdx = 0; ptrIdx < event.getPointerCount(); ptrIdx++) {
            int ptrId = event.getPointerId(ptrIdx);

            for (int i = 0; i < event.getHistorySize(); i++) {
                doPoint(ptrId, event.getHistoricalX(ptrIdx, i), event.getHistoricalY(ptrIdx, i),
                        event.getHistoricalSize(ptrIdx, i), event.getHistoricalPressure(ptrIdx, i));
            }
            doPoint(ptrId, event.getX(ptrIdx), event.getY(ptrIdx), event.getSize(ptrIdx), event.getPressure(ptrIdx));
        }

        switch (action) {
        case MotionEvent.ACTION_POINTER_UP:
            int upPtrIdx = event.getActionIndex();
            int upPtrId = event.getPointerId(upPtrIdx);
            prevPoints.set(upPtrId, null);
            break;
        case MotionEvent.ACTION_UP:
            prevPoints.clear();
            onLastFingerGone();
            break;
        }

        return true;
    }

    private void onLastFingerGone() {
        colorSelector.shakeIt();
    }

    private void drawLine(PointInfo p1, PointInfo p2) {
        float width = (p1.size + p2.size) / 2;
        paint.setStrokeWidth(width);
        if (p1.x == p2.x && p1.y == p2.y) {
            mCanvas.drawPoint(p1.x, p2.y, paint);
        } else {
            mCanvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
        }
        mRect.set((int) (p1.x - width - 2), (int) (p1.y - width - 2), (int) (p1.x + width + 2),
                (int) (p1.y + width + 2));
        mRect.union((int) (p2.x + width + 2), (int) (p2.y + width + 2));
        mRect.union((int) (p2.x - width - 2), (int) (p2.y - width - 2));
        invalidate(mRect);
    }

    public void setColorSelector(ColorSelector value) {
        colorSelector = value;
    }

    public boolean isCleared() {
        return cleared;
    }

	public void setTouchPressureGuesser(TouchPressureGuesser tpg) {
		this.tpg=tpg;
	}
}
