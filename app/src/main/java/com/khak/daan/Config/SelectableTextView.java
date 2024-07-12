package com.khak.daan.Config;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class SelectableTextView extends androidx.appcompat.widget.AppCompatTextView {
    private ClipboardManager clipboardManager;

    public SelectableTextView(Context context) {
        super(context);
        init(context);
    }

    public SelectableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == android.R.id.copy) {
                    CharSequence selectedText = getText().subSequence(getSelectionStart(), getSelectionEnd());
                    ClipData clipData = ClipData.newPlainText("text", selectedText);
                    clipboardManager.setPrimaryClip(clipData);
                    mode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    @Override
    public boolean getDefaultEditable() {
        return false;
    }

    @Override
    public MovementMethod getDefaultMovementMethod() {
        return ScrollingMovementMethod.getInstance();
    }
}