package com.example.quanlysach;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(245, 248, 252)));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(40, 80, 40, 40);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(245, 248, 252));
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);
        root.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        TextView title = new TextView(this);
        title.setText("📖 THƯ VIỆN SÁCH CÁ NHÂN");
        title.setTextSize(22);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.rgb(35, 75, 130));
        title.setGravity(Gravity.CENTER);
        root.addView(title, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 32));

        EditText username = createStyledEdit("Tên đăng nhập (admin)");
        root.addView(username, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        EditText password = createStyledEdit("Mật khẩu (123456)");
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        root.addView(password, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        TextView error = new TextView(this);
        error.setTextColor(Color.RED);
        error.setTextSize(14);
        error.setGravity(Gravity.CENTER);
        root.addView(error, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        Button login = new Button(this);
        login.setText("ĐĂNG NHẬP");
        login.setTextColor(Color.WHITE);
        login.setTextSize(16);
        login.setTypeface(null, Typeface.BOLD);
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setColor(Color.rgb(35, 75, 130));
        btnBg.setCornerRadius(16);
        login.setBackground(btnBg);
        login.setPadding(24, 20, 24, 20);
        root.addView(login, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        login.setOnClickListener(v -> {
            String u = username.getText().toString().trim();
            String p = password.getText().toString();
            if (u.isEmpty() || p.isEmpty()) {
                error.setText("Vui lòng nhập tên đăng nhập và mật khẩu");
            } else if ("admin".equals(u) && "123456".equals(p)) {
                BookRepository.init();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                error.setText("Sai tên đăng nhập hoặc mật khẩu");
            }
        });

        setContentView(root);
    }

    private EditText createStyledEdit(String hint) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setHint(hint);
        e.setPadding(24, 20, 24, 20);
        e.setTextSize(15);
        e.setFocusable(true);
        e.setFocusableInTouchMode(true);
        e.setEnabled(true);
        e.setClickable(true);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setCornerRadius(16);
        bg.setStroke(1, Color.rgb(200, 210, 225));
        e.setBackground(bg);
        return e;
    }

    private LinearLayout.LayoutParams lp(int w, int h, int bottom) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        p.bottomMargin = bottom;
        return p;
    }
}
