package com.ronhalder.rgbkeyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(28,36,28,28); root.setBackgroundColor(Color.rgb(10,11,20));
        TextView title = new TextView(this); title.setText("🌈 RGB KEYBOARD"); title.setTextColor(Color.WHITE); title.setTextSize(28); title.setPadding(0,10,0,18); root.addView(title);
        TextView info = new TextView(this); info.setText("Native animated RGB keyboard\n\n1. Enable RGB Keyboard\n2. Select it as your default keyboard\n3. Try typing in any app\n\nLetters stay white while each key cycles through different neon colours."); info.setTextColor(Color.LTGRAY); info.setTextSize(16); info.setPadding(0,0,0,24); root.addView(info);
        Button enable = new Button(this); enable.setText("1  ENABLE KEYBOARD"); enable.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))); root.addView(enable);
        Button choose = new Button(this); choose.setText("2  SELECT KEYBOARD"); choose.setOnClickListener(v -> { ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).showInputMethodPicker(); }); root.addView(choose);
        Button test = new Button(this); test.setText("3  TEST TYPING"); test.setOnClickListener(v -> { android.widget.EditText edit = new android.widget.EditText(this); edit.setSingleLine(false); edit.setHint("Tap here and type..."); edit.setTextColor(Color.WHITE); edit.setHintTextColor(Color.GRAY); root.addView(edit, 1); edit.requestFocus(); ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT); }); root.addView(test);
        setContentView(root);
    }
}