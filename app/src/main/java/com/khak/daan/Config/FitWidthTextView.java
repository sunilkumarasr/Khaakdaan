package com.khak.daan.Config;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class FitWidthTextView extends AppCompatTextView {

    public FitWidthTextView(Context context) {
        super(context);
    }

    public FitWidthTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FitWidthTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int contentWidth = getContentWidth();

        if (measuredWidth < contentWidth) {
            String text = getText().toString();
            int numSpaces = countSpaces(text);
            int totalSpacesWidth = contentWidth - measuredWidth;
            int spaceWidth = totalSpacesWidth / numSpaces;

            StringBuilder justifiedText = new StringBuilder(text);
            int index = 0;
            for (int i = 0; i < numSpaces; i++) {
                index = justifiedText.indexOf(" ", index + 1);
                justifiedText.insert(index, getSpaces(spaceWidth));
            }

            setText(justifiedText.toString());
        }
    }

    private int getContentWidth() {
        Paint paint = getPaint();
        String text = getText().toString();
        return Math.round(paint.measureText(text));
    }

    private int countSpaces(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                count++;
            }
        }
        return count;
    }

    private String getSpaces(int count) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < count; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
}
