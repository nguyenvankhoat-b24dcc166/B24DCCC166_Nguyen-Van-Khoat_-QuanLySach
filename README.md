# Ứng dụng Quản lý thư viện sách cá nhân

## Mở dự án
1. Giải nén `QuanLySachAndroid.zip`.
2. Android Studio → **Open** → chọn thư mục `QuanLySachAndroid`.
3. Chờ Gradle Sync hoàn tất, chọn máy ảo/điện thoại Android rồi nhấn **Run**.
4. Đăng nhập bằng `admin` / `123456`.

## Chức năng
- Đăng nhập và kiểm tra dữ liệu trống/sai.
- Danh sách sách có 3 dữ liệu mẫu và thống kê.
- Thêm sách, kiểm tra tên/tác giả, chọn thể loại, tình trạng, đánh giá và ghi chú.
- Chi tiết sách, chỉnh đánh giá, đánh dấu đã đọc và xóa sách.
- Dữ liệu dùng `ArrayList<Book>` trong bộ nhớ, theo đúng phạm vi đề bài; dữ liệu sẽ mất khi tiến trình ứng dụng bị đóng.

## Cấu trúc
- `LoginActivity.java`
- `MainActivity.java`
- `AddBookActivity.java`
- `BookDetailActivity.java`
- `Book.java`, `BookRepository.java`
- Các màn hình được dựng bằng Java view trực tiếp; manifest đăng ký 4 Activity.

## Nộp bài
Tự chạy ứng dụng và chụp ảnh thật của cả 4 màn hình. Đưa thư mục dự án lên GitHub/GitLab (không nộp ZIP theo yêu cầu đề), sau đó bổ sung link repository và thông tin sinh viên vào báo cáo.
