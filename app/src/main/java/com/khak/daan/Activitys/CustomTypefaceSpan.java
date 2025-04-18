package com.khak.daan.Activitys;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

    public class CustomTypefaceSpan extends MetricAffectingSpan {
        private final Typeface typeface;

        public CustomTypefaceSpan(String family, Typeface typeface) {
            this.typeface = typeface;
        }

        @Override
        public void updateMeasureState(TextPaint p) {
            applyCustomTypeface(p);
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            applyCustomTypeface(tp);
        }

        private void applyCustomTypeface(TextPaint paint) {
            Typeface oldTypeface = paint.getTypeface();
            int oldStyle = oldTypeface != null ? oldTypeface.getStyle() : 0;
            int fakeStyle = oldStyle & ~typeface.getStyle();

            if ((fakeStyle & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fakeStyle & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(typeface);
        }
    }

