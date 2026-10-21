package com.example.quanlysach;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailActivity extends Activity {
    private Book book;
    private TextView state;
    private RatingBar rating;
    private Button markRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(245, 248, 252)));

        int position = getIntent().getIntExtra("position", -1);
        if (position < 0 || position >= BookRepository.books.size()) {
            finish();
            return;
        }

        book = BookRepository.books.get(position);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 24, 28, 32);
        root.setBackgroundColor(Color.rgb(245, 248, 252));
        scroll.addView(root);

        TextView heading = new TextView(this);
        heading.setText("📖 Chi tiết sách");
        heading.setTextSize(22);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(Color.rgb(35, 75, 130));
        heading.setGravity(Gravity.CENTER);
        root.addView(heading, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 20));

        TextView name = new TextView(this);
        name.setText(book.getTitle());
        name.setTextSize(20);
        name.setTypeface(null, Typeface.BOLD);
        name.setTextColor(Color.BLACK);
        root.addView(name, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 12));

        TextView authorGenre = new TextView(this);
        authorGenre.setText("✍️ Tác giả: " + book.getAuthor() + "\n🏷️ Thể loại: " + book.getGenre());
        authorGenre.setTextSize(15);
        authorGenre.setTextColor(Color.DKGRAY);
        root.addView(authorGenre, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        state = new TextView(this);
        state.setText("📌 Tình trạng: " + book.getStatus());
        state.setTextSize(15);
        state.setTypeface(null, Typeface.BOLD);
        state.setTextColor(Color.rgb(50, 100, 180));
        root.addView(state, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        TextView rateLabel = new TextView(this);
        rateLabel.setText("⭐ Đánh giá");
        rateLabel.setTextSize(14);
        rateLabel.setTypeface(null, Typeface.BOLD);
        rateLabel.setTextColor(Color.rgb(80, 90, 110));
        root.addView(rateLabel, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6));

        rating = new RatingBar(this);
        rating.setNumStars(5);
        rating.setStepSize(1);
        rating.setRating(book.getRating());
        root.addView(rating, lp(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));
        rating.setOnRatingBarChangeListener((bar, value, user) -> book.setRating(value));

        TextView note = new TextView(this);
        String noteText = book.getNotes().isEmpty() ? "Không có ghi chú" : book.getNotes();
        note.setText("📝 Ghi chú:\n" + noteText);
        note.setTextSize(15);
        note.setTextColor(Color.DKGRAY);
        root.addView(note, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 24));

        markRead = createButton("Đánh dấu đã đọc", Color.rgb(40, 167, 69));
        root.addView(markRead, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 12));

        if ("Đã đọc".equals(book.getStatus())) {
            markRead.setEnabled(false);
            markRead.setAlpha(0.6f);
        }

        markRead.setOnClickListener(v -> {
            book.setStatus("Đã đọc");
            state.setText("📌 Tình trạng: Đã đọc");
            markRead.setEnabled(false);
            markRead.setAlpha(0.6f);
            Toast.makeText(this, "Đã đánh dấu là đã đọc", Toast.LENGTH_SHORT).show();
        });

        Button back = createButton("Quay lại danh sách", Color.rgb(108, 117, 125));
        root.addView(back, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 12));
        back.setOnClickListener(v -> finish());

        Button delete = createButton("Xóa sách", Color.rgb(220, 53, 69));
        root.addView(delete, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 12));

        delete.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Xóa sách")
                .setMessage("Bạn có chắc muốn xóa sách \"" + book.getTitle() + "\"?")
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Xóa", (dialog, which) -> {
                    BookRepository.books.remove(book);
                    Toast.makeText(this, "Đã xóa sách", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .show());

        setContentView(scroll);
    }

    private Button createButton(String text, int bgColor) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setTextColor(Color.WHITE);
        btn.setTextSize(15);
        btn.setTypeface(null, Typeface.BOLD);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(bgColor);
        bg.setCornerRadius(16);
        btn.setBackground(bg);
        btn.setPadding(24, 18, 24, 18);
        return btn;
    }

    private LinearLayout.LayoutParams lp(int w, int h, int bottom) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        p.bottomMargin = bottom;
        return p;
    }
}
