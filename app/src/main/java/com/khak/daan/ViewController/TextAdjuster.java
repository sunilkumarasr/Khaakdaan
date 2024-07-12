package com.khak.daan.ViewController;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.widget.TextView;

public class TextAdjuster {

    public static void adjustTextWithSpaces(TextView textView, String originalText, float maxWidthDp) {
        Context context = textView.getContext();
        float maxAllowedWidthPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                maxWidthDp,
                context.getResources().getDisplayMetrics()
        );
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        paint.setTypeface(textView.getTypeface()); // Set the same typeface as the TextView

        float originalWidthPx = paint.measureText(originalText);

        if (originalWidthPx < maxAllowedWidthPx) {
            // Text does not fill the allowed width, we need to add spaces between words
            String[] words = originalText.split(" ");
            int wordCount = words.length;
            if (wordCount > 1) {
                // Calculate font metrics for the custom typeface
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                float spaceWidth = paint.measureText(" "); // Width of a single space

                // Calculate the average space needed between words
                float averageSpacePx = (maxAllowedWidthPx - originalWidthPx) / (wordCount - 1);
                float spaceAdjustment = averageSpacePx - spaceWidth;

                StringBuilder adjustedTextBuilder = new StringBuilder();
                for (int i = 0; i < wordCount - 1; i++) {
                    adjustedTextBuilder.append(words[i]);
                    // Append the adjusted space between words
                    for (int j = 0; j < spaceAdjustment / spaceWidth; j++) {
                        adjustedTextBuilder.append(" ");
                    }
                }
                adjustedTextBuilder.append(words[wordCount - 1]);
                textView.setText(adjustedTextBuilder.toString());
            } else {
                // Only one word, use the original text
                textView.setText(originalText);
            }
        } else {
            // Text fits within the allowed width, use the original text
            textView.setText(originalText);
        }
    }


    public static String adjustTextWithSpacesreturn(TextView textView, String originalText, float maxWidthDp) {
        Context context = textView.getContext();
        float maxAllowedWidthPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                maxWidthDp,
                context.getResources().getDisplayMetrics()
        );
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        paint.setTypeface(textView.getTypeface()); // Set the same typeface as the TextView

        float originalWidthPx = paint.measureText(originalText);

        if (originalWidthPx < maxAllowedWidthPx) {
            // Text does not fill the allowed width, we need to add spaces between words
            String[] words = originalText.split(" ");
            int wordCount = words.length;
            if (wordCount > 1) {
                // Calculate font metrics for the custom typeface
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                float spaceWidth = paint.measureText(" "); // Width of a single space

                // Calculate the average space needed between words
                float averageSpacePx = (maxAllowedWidthPx - originalWidthPx) / (wordCount - 1);
                float spaceAdjustment = averageSpacePx - spaceWidth;

                StringBuilder adjustedTextBuilder = new StringBuilder();
                for (int i = 0; i < wordCount - 1; i++) {
                    adjustedTextBuilder.append(words[i]);
                    // Append the adjusted space between words
                    for (int j = 0; j < spaceAdjustment / spaceWidth; j++) {
                        adjustedTextBuilder.append(" ");
                    }
                }
                adjustedTextBuilder.append(words[wordCount - 1]);
                textView.setText(adjustedTextBuilder.toString());
            } else {
                // Only one word, use the original text
                textView.setText(originalText);
            }
        } else {
            // Text fits within the allowed width, use the original text
            textView.setText(originalText);
        }

        return originalText;
    }
}
