package com.example.quanlysach;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddBookActivity extends Activity {
    private EditText title, author, notes;
    private Spinner genre;
    private RadioButton rbUnread, rbReading, rbRead;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(245, 248, 252)));

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 24, 28, 32);
        root.setBackgroundColor(Color.rgb(245, 248, 252));
        scroll.addView(root);

        TextView heading = new TextView(this);
        heading.setText("➕ Thêm sách mới");
        heading.setTextSize(22);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(Color.rgb(35, 75, 130));
        heading.setGravity(Gravity.CENTER);
        root.addView(heading, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 20));

        title = createEdit("Tên sách *");
        root.addView(title, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 14));

        author = createEdit("Tác giả *");
        root.addView(author, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 14));

        createLabel(root, "Thể loại");
        genre = new Spinner(this);
        String[] genres = {"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Kinh tế", "Lịch sử", "Tâm lý học", "Ngoại ngữ", "Thiếu nhi", "Khác"};
        genre.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genres));
        root.addView(genre, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 14));

        createLabel(root, "Tình trạng đọc");
        RadioGroup statusGroup = new RadioGroup(this);
        statusGroup.setOrientation(RadioGroup.VERTICAL);

        rbUnread = new RadioButton(this);
        rbUnread.setText("Chưa đọc");
        statusGroup.addView(rbUnread);

        rbReading = new RadioButton(this);
        rbReading.setText("Đang đọc");
        statusGroup.addView(rbReading);

        rbRead = new RadioButton(this);
        rbRead.setText("Đã đọc");
        statusGroup.addView(rbRead);

        rbUnread.setChecked(true);
        root.addView(statusGroup, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 14));

        createLabel(root, "Đánh giá (0–5 sao)");
        rating = new RatingBar(this);
        rating.setNumStars(5);
        rating.setStepSize(1);
        rating.setRating(0);
        root.addView(rating, lp(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 14));

        createLabel(root, "Ghi chú");
        notes = new EditText(this);
        notes.setHint("Nhập ghi chú (không bắt buộc)");
        notes.setMinLines(3);
        notes.setGravity(Gravity.TOP);
        notes.setPadding(20, 16, 20, 16);
        notes.setTextSize(15);
        notes.setFocusable(true);
        notes.setFocusableInTouchMode(true);
        notes.setEnabled(true);
        notes.setClickable(true);
        GradientDrawable notesBg = new GradientDrawable();
        notesBg.setColor(Color.WHITE);
        notesBg.setCornerRadius(16);
        notesBg.setStroke(1, Color.rgb(200, 210, 225));
        notes.setBackground(notesBg);
        notes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        root.addView(notes, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 20));

        Button save = new Button(this);
        save.setText("LƯU SÁCH");
        save.setTextColor(Color.WHITE);
        save.setTextSize(15);
        save.setTypeface(null, Typeface.BOLD);
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setColor(Color.rgb(35, 75, 130));
        btnBg.setCornerRadius(16);
        save.setBackground(btnBg);
        save.setPadding(24, 18, 24, 18);
        root.addView(save, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 12));

        save.setOnClickListener(v -> {
            String t = title.getText().toString().trim();
            String a = author.getText().toString().trim();
            if (t.isEmpty()) {
                title.setError("Vui lòng nhập tên sách");
                title.requestFocus();
                return;
            }
            if (a.isEmpty()) {
                author.setError("Vui lòng nhập tác giả");
                author.requestFocus();
                return;
            }

            String selectedStatus = "Chưa đọc";
            if (rbRead.isChecked()) {
                selectedStatus = "Đã đọc";
            } else if (rbReading.isChecked()) {
                selectedStatus = "Đang đọc";
            }

            String selectedGenre = genre.getSelectedItem() != null ? genre.getSelectedItem().toString() : "Khác";

            BookRepository.books.add(new Book(t, a, selectedGenre, selectedStatus, rating.getRating(), notes.getText().toString().trim()));
            Toast.makeText(this, "Đã thêm sách thành công", Toast.LENGTH_SHORT).show();
            finish();
        });

        setContentView(scroll);
    }

    private EditText createEdit(String hint) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setSingleLine(true);
        e.setPadding(20, 16, 20, 16);
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

    private void createLabel(LinearLayout root, String labelText) {
        TextView t = new TextView(this);
        t.setText(labelText);
        t.setTextSize(14);
        t.setTypeface(null, Typeface.BOLD);
        t.setTextColor(Color.rgb(80, 90, 110));
        root.addView(t, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6));
    }

    private LinearLayout.LayoutParams lp(int w, int h, int bottom) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        p.bottomMargin = bottom;
        return p;
    }
}
