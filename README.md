# Game đá luân lưu trực tuyến cho 2 người

Đây là bài tập lớn dùng Java Spring Boot, WebSocket, HTML/CSS/JavaScript thuần và MySQL. Dự án đã hoàn thành **Bước 1** (khung ứng dụng/kết nối MySQL) và **Bước 2** (đăng ký, đăng nhập, đăng xuất). Phòng chơi và trận đấu chưa được triển khai.

## Cấu trúc chính

- `penalty_game/`: mã nguồn Maven/Spring Boot.
- `penalty_game/src/main/java/penaltygame/config/DatabaseHealthController.java`: endpoint kiểm tra kết nối MySQL bằng `JdbcTemplate`.
- `penalty_game/src/main/java/penaltygame/account/`: controller, service và repository cho tài khoản; repository dùng `JdbcTemplate`, mật khẩu dùng BCrypt.
- `penalty_game/src/main/resources/static/`: hai trang HTML/CSS/JavaScript riêng cho đăng nhập và đăng ký.
- `database/penalty_game_mysql.sql`: DDL MySQL gồm 9 bảng; `accounts.email` là bắt buộc và duy nhất.
- `database/002_add_email_to_accounts.sql`: hướng dẫn migration an toàn nếu schema cũ đã được tạo.
- `docs/BAO_CAO_DU_AN.md`: báo cáo chung và nhật ký thực hiện.

## Chuẩn bị MySQL

Với database phát triển mới/trống, chạy script sau. Script tạo database `penalty_game` nếu database chưa tồn tại và không chứa `DROP` hoặc `TRUNCATE`.

```powershell
mysql -u root -p < database/penalty_game_mysql.sql
```

Nếu database đã được tạo bằng schema cũ, xem và chạy tuần tự `database/002_add_email_to_accounts.sql`. Script này không tự đặt email cho tài khoản cũ.

Thiết lập thông tin kết nối cho phiên PowerShell hiện tại. Không ghi mật khẩu thật vào source:

```powershell
cd D:\Lap_trinh_mang\penalty_game

$env:DB_URL = "jdbc:mysql://127.0.0.1:3306/penalty_game?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "12345678"
```

## Chạy và kiểm tra Bước 1–2

```powershell
cd penalty_game
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Khi ứng dụng đã chạy, mở `http://localhost:8080/api/health/database`:

- `200 {"status":"UP"}`: Spring Boot kết nối được MySQL.
- `503 {"status":"DOWN",...}`: ứng dụng chạy nhưng cấu hình MySQL, server MySQL hoặc schema chưa sẵn sàng.

Khi endpoint database trả `UP`, mở `http://localhost:8080/` để đăng nhập hoặc `http://localhost:8080/register.html` để đăng ký. Hai trang có liên kết qua lại. Ứng dụng tạo session HTTP sau đăng ký/đăng nhập; giao diện tự gửi CSRF token cho các yêu cầu thay đổi dữ liệu.

Các API tài khoản:

- `GET /api/auth/csrf`: lấy CSRF token của session.
- `POST /api/auth/register`: tạo tài khoản với `email`, `username`, `password`, `confirmPassword`.
- `POST /api/auth/login`: đăng nhập với `username`, `password`.
- `POST /api/auth/logout`: hủy session.
- `GET /api/auth/me`: xem người chơi của session hiện tại.

Mật khẩu thật chỉ đặt trong biến môi trường `DB_PASSWORD`, không ghi vào source. Kết nối DB hiện phải dùng một tài khoản MySQL hợp lệ có quyền trên `penalty_game`; nếu `/api/health/database` còn trả `DOWN`, chưa thể kiểm tra luồng tài khoản với database thật.

Maven Wrapper lưu dependency tại `penalty_game/.mvn/repository/`; thư mục này đã được bỏ qua bởi Git.
