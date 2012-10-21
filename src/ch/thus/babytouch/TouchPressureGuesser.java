package ch.thus.babytouch;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class TouchPressureGuesser {

	private class MinMax {
		private final String minName;
		private final String maxName;
		private float min;
		private float max;

		public MinMax(SharedPreferences preferences, String prefix) {
			minName = prefix + "Min";
			maxName = prefix + "Max";
			min = preferences.getFloat(minName, 1.0f);
			max = preferences.getFloat(maxName, 0.0f);
		}

		public float getAdjusted(float value) {
			min = Math.min(min, value);
			max = Math.max(max, value);
			return min==max ? 0.5f : (value - min) / (max - min);
		}

		public void save(Editor editor) {
			if(min<max) {
				Log.i(minName, Float.toString(min));
				Log.i(maxName, Float.toString(max));
				editor.putFloat(minName, min);
				editor.putFloat(maxName, max);
			}
		}
	}

	private MinMax pressureMM;
	private MinMax sizeMM;

	public TouchPressureGuesser(SharedPreferences preferences) {
		pressureMM = new MinMax(preferences, "pressure");
		sizeMM = new MinMax(preferences, "size");
	}

	public float getAdjusted(float size, float pressure) {
		if (size > 0) {
			return sizeMM.getAdjusted(size);
		} else if (pressure > 0) {
			return pressureMM.getAdjusted(pressure);
		} else {
			return 0.5f;
		}
	}

	public void save(SharedPreferences preferences) {
		Editor editor = preferences.edit();
		pressureMM.save(editor);
		sizeMM.save(editor);
		editor.apply();
	}
}
