package com.example.quanlysach;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    private TextView stats;
    private ListView list;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(245, 248, 252)));
        buildView();
    }

    private void buildView() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 24, 24, 24);
        root.setBackgroundColor(Color.rgb(245, 248, 252));

        // Header Title Card
        TextView title = new TextView(this);
        title.setText("📚 Thư viện sách cá nhân");
        title.setTextSize(22);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.rgb(35, 75, 130));
        title.setGravity(Gravity.CENTER);
        root.addView(title, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        // Stats Card
        stats = new TextView(this);
        stats.setTextSize(14);
        stats.setPadding(16, 16, 16, 16);
        stats.setTextColor(Color.rgb(40, 50, 70));
        stats.setGravity(Gravity.CENTER);
        GradientDrawable statsBg = new GradientDrawable();
        statsBg.setColor(Color.rgb(230, 240, 255));
        statsBg.setCornerRadius(16);
        stats.setBackground(statsBg);
        root.addView(stats, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        // Action Buttons Row
        LinearLayout buttonBar = new LinearLayout(this);
        buttonBar.setOrientation(LinearLayout.HORIZONTAL);

        Button add = createStyledButton("+ Thêm sách", Color.rgb(35, 75, 130));
        add.setOnClickListener(v -> startActivity(new Intent(this, AddBookActivity.class)));

        Button logout = createStyledButton("Đăng xuất", Color.rgb(108, 117, 125));
        logout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        LinearLayout.LayoutParams addParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        addParams.rightMargin = 8;
        buttonBar.addView(add, addParams);

        LinearLayout.LayoutParams logoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        logoutParams.leftMargin = 8;
        buttonBar.addView(logout, logoutParams);

        root.addView(buttonBar, lp(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16));

        // ListView
        list = new ListView(this);
        list.setDivider(null);
        list.setDividerHeight(16);
        root.addView(list, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));

        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        setContentView(root);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        BookRepository.init();
        int read = 0;
        for (Book b : BookRepository.books) {
            if ("Đã đọc".equals(b.getStatus())) {
                read++;
            }
        }
        stats.setText(String.format(Locale.getDefault(), "📖 Tổng số: %d   |   ✅ Đã đọc: %d   |   ⏳ Chưa đọc: %d",
                BookRepository.books.size(), read, BookRepository.books.size() - read));

        if (adapter == null) {
            adapter = new BookAdapter(this, BookRepository.books);
            list.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private Button createStyledButton(String text, int bgColor) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setTextColor(Color.WHITE);
        btn.setTextSize(14);
        btn.setTypeface(null, Typeface.BOLD);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(bgColor);
        bg.setCornerRadius(16);
        btn.setBackground(bg);
        btn.setPadding(20, 16, 20, 16);
        return btn;
    }

    private LinearLayout.LayoutParams lp(int w, int h, int bottom) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        p.bottomMargin = bottom;
        return p;
    }

    private static class BookAdapter extends ArrayAdapter<Book> {
        public BookAdapter(Context context, ArrayList<Book> books) {
            super(context, 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);
            LinearLayout itemLayout;
            if (convertView == null) {
                itemLayout = new LinearLayout(getContext());
                itemLayout.setOrientation(LinearLayout.VERTICAL);
                itemLayout.setPadding(24, 20, 24, 20);

                GradientDrawable bg = new GradientDrawable();
                bg.setColor(Color.WHITE);
                bg.setCornerRadius(16);
                bg.setStroke(1, Color.rgb(215, 222, 232));
                itemLayout.setBackground(bg);
            } else {
                itemLayout = (LinearLayout) convertView;
                itemLayout.removeAllViews();
            }

            // Title
            TextView tvTitle = new TextView(getContext());
            tvTitle.setText(book != null ? book.getTitle() : "");
            tvTitle.setTextSize(16);
            tvTitle.setTypeface(null, Typeface.BOLD);
            tvTitle.setTextColor(Color.rgb(35, 75, 130));
            itemLayout.addView(tvTitle);

            // Author & Genre
            TextView tvAuthorGenre = new TextView(getContext());
            tvAuthorGenre.setText(String.format("✍️ %s  •  🏷️ %s", book != null ? book.getAuthor() : "", book != null ? book.getGenre() : ""));
            tvAuthorGenre.setTextSize(13);
            tvAuthorGenre.setTextColor(Color.DKGRAY);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.topMargin = 6;
            lp.bottomMargin = 8;
            itemLayout.addView(tvAuthorGenre, lp);

            // Bottom row: Status, Rating, Delete
            LinearLayout bottomRow = new LinearLayout(getContext());
            bottomRow.setOrientation(LinearLayout.HORIZONTAL);
            bottomRow.setGravity(Gravity.CENTER_VERTICAL);

            TextView tvStatus = new TextView(getContext());
            tvStatus.setText(book != null ? book.getStatus() : "");
            tvStatus.setTextSize(12);
            tvStatus.setTypeface(null, Typeface.BOLD);
            tvStatus.setPadding(12, 4, 12, 4);

            GradientDrawable badgeBg = new GradientDrawable();
            badgeBg.setCornerRadius(8);
            String status = book != null ? book.getStatus() : "";
            if ("Đã đọc".equals(status)) {
                badgeBg.setColor(Color.rgb(225, 245, 230));
                tvStatus.setTextColor(Color.rgb(40, 167, 69));
            } else if ("Đang đọc".equals(status)) {
                badgeBg.setColor(Color.rgb(255, 243, 224));
                tvStatus.setTextColor(Color.rgb(230, 126, 34));
            } else {
                badgeBg.setColor(Color.rgb(240, 240, 240));
                tvStatus.setTextColor(Color.GRAY);
            }
            tvStatus.setBackground(badgeBg);

            float rating = book != null ? book.getRating() : 0f;
            String stars = rating == 0 ? "Chưa đánh giá" : String.format(Locale.getDefault(), "⭐ %.0f/5", rating);
            TextView tvRating = new TextView(getContext());
            tvRating.setText("  " + stars);
            tvRating.setTextSize(13);
            tvRating.setTextColor(Color.rgb(100, 100, 100));

            LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
            LinearLayout statusContainer = new LinearLayout(getContext());
            statusContainer.setOrientation(LinearLayout.HORIZONTAL);
            statusContainer.setGravity(Gravity.CENTER_VERTICAL);
            statusContainer.addView(tvStatus);
            statusContainer.addView(tvRating);
            bottomRow.addView(statusContainer, statusParams);

            // Delete Button
            Button btnDelete = new Button(getContext());
            btnDelete.setText("🗑️ Xóa");
            btnDelete.setTextSize(12);
            btnDelete.setTextColor(Color.rgb(220, 53, 69));
            btnDelete.setFocusable(false);
            btnDelete.setFocusableInTouchMode(false);
            
            GradientDrawable delBg = new GradientDrawable();
            delBg.setColor(Color.rgb(253, 237, 237));
            delBg.setCornerRadius(12);
            btnDelete.setBackground(delBg);
            btnDelete.setPadding(20, 6, 20, 6);

            btnDelete.setOnClickListener(v -> new AlertDialog.Builder(getContext())
                    .setTitle("Xóa sách")
                    .setMessage("Bạn có chắc muốn xóa sách \"" + (book != null ? book.getTitle() : "") + "\"?")
                    .setNegativeButton("Hủy", null)
                    .setPositiveButton("Xóa", (d, w) -> {
                        BookRepository.books.remove(book);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "Đã xóa sách", Toast.LENGTH_SHORT).show();
                    })
                    .show());

            bottomRow.addView(btnDelete);
            itemLayout.addView(bottomRow);

            return itemLayout;
        }
    }
}
