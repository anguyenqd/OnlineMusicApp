package com.bigcatfamily.myringtones.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class UiUtil {
	public static final String TAG = "UiUtil";

	public static int dipToPixels(Context ctx, int dip) {
		Resources r = ctx.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
				r.getDisplayMetrics());
		return (int) px;
	}

	public static void setCustomFont(View textViewOrButton, Context ctx,
			AttributeSet attrs, int[] attributeSet, int fontId) {
		TypedArray a = ctx.obtainStyledAttributes(attrs, attributeSet);
		String customFont = a.getString(fontId);
		setCustomFont(textViewOrButton, ctx, customFont);
		a.recycle();
	}

	public static boolean setCustomFont(View textViewOrButton, Context ctx,
			String asset) {
		if (TextUtils.isEmpty(asset))
			return false;
		Typeface tf = null;
		try {
			tf = getFont(ctx, asset);
			if (textViewOrButton instanceof TextView) {
				((TextView) textViewOrButton).setTypeface(tf);
			} else {
				((Button) textViewOrButton).setTypeface(tf);
			}
		} catch (Exception e) {
			Log.e(TAG, "Could not get typeface: " + asset, e);
			return false;
		}

		return true;
	}

	private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();

	public static Typeface getFont(Context c, String name) throws Exception {
		synchronized (fontCache) {
			if (fontCache.get(name) != null) {
				SoftReference<Typeface> ref = fontCache.get(name);
				if (ref.get() != null) {
					return ref.get();
				}
			}
			Typeface typeface = Typeface.createFromAsset(c.getAssets(),
					"fonts/" + name);
			fontCache.put(name, new SoftReference<Typeface>(typeface));
			return typeface;
		}
	}
}