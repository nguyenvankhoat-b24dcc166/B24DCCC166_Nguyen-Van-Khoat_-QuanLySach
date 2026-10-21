package com.example.quanlysach;

import java.util.ArrayList;

public class BookRepository {
    public static final ArrayList<Book> books = new ArrayList<>();
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        books.add(new Book("Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", "Đang đọc", 4, "Sách hướng dẫn lập trình Android cơ bản."));
        books.add(new Book("Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", "Đã đọc", 5, "Cuốn sách nghệ thuật ứng xử hàng đầu thế giới."));
        books.add(new Book("Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", "Chưa đọc", 0, "Tác phẩm văn học thiếu nhi kinh điển của Tô Hoài."));
        initialized = true;
    }
}
