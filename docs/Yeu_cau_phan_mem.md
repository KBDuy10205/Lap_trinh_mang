# Prompt xây dựng game đá luân lưu hai người

Hãy giúp tôi xây dựng game đá luân lưu trực tuyến cho 2 người, phù hợp với bài tập lớn của sinh viên.

## 1. Công nghệ sử dụng

- Backend: Java Spring Boot.
- Kết nối hai người chơi theo thời gian thực: WebSocket.
- Giao diện: HTML, CSS, JavaScript thuần.
- Cơ sở dữ liệu: MySQL.
- Tự viết câu lệnh SQL bằng JdbcTemplate, không dùng JPA/Hibernate.
- Không dùng React, Redis, Docker hoặc các công nghệ không cần thiết.

## 2. Yêu cầu về code

- Code đơn giản, rõ ràng, dễ đọc và dễ giải thích.
- Chia thư mục theo chức năng: tài khoản, phòng chơi, trận đấu, lịch sử.
- Trong mỗi chức năng, tách phần nhận yêu cầu, xử lý và truy vấn database khi cần.
- Đặt tên biến, hàm dễ hiểu; chú thích những đoạn xử lý quan trọng.
- Không tạo quá nhiều lớp hoặc chia nhỏ file không cần thiết.
- Nếu cần dùng kỹ thuật mới, giải thích bằng lời đơn giản trước khi áp dụng.

## 3. Các chức năng

### Tài khoản

- Đăng ký, đăng nhập, đăng xuất.
- Lưu mật khẩu đã mã hóa bằng BCrypt.
- Dùng phiên đăng nhập của Spring Boot để xác định người chơi.
- Xem và sửa tên hiển thị.

### Phòng chơi

- Tạo phòng và sinh mã phòng.
- Tham gia phòng bằng mã, mỗi phòng tối đa 2 người.
- Hiển thị hai người chơi và trạng thái sẵn sàng.
- Chủ phòng bắt đầu khi cả hai đã sẵn sàng.
- Rời phòng và gửi các câu chat có sẵn.

### Thi đấu

- Hai người luân phiên làm người sút và thủ môn.
- Mỗi lượt có ba hướng: trái, giữa, phải.
- Người chơi chọn hướng rồi nhấn xác nhận; xác nhận xong không được đổi.
- Server giữ kín hướng chọn cho đến khi lượt kết thúc.
- Hai hướng giống nhau thì thủ môn cản phá; khác nhau thì ghi bàn.
- Mỗi lượt có 15 giây để chọn.
- Người sút không chọn đúng hạn thì không ghi bàn.
- Người sút đã chọn nhưng thủ môn hết giờ thì tính ghi bàn.
- Cả hai không chọn thì không ghi bàn.
- Sau mỗi lượt, hiển thị kết quả, cập nhật tỷ số và đổi vai.
- Mỗi người sút 5 lần. Nếu hòa, mỗi người sút thêm một lần rồi so sánh tỷ số; tiếp tục như vậy đến khi có người thắng.
- Dùng hiệu ứng CSS đơn giản để minh họa bóng và thủ môn.

### Kết thúc

- Hiển thị người thắng và tỷ số.
- Lưu lịch sử và thống kê số trận thắng, thua.
- Cho phép yêu cầu chơi lại; cả hai đồng ý thì tạo trận mới.
- Người chơi có thể bỏ cuộc.
- Nếu mất kết nối, chờ 20 giây để kết nối lại; quá hạn thì xử thua nếu trận chưa kết thúc. Nếu cả hai đều mất kết nối quá hạn thì hủy trận.

## 4. Luồng hoạt động

Đăng nhập → tạo hoặc vào phòng → hai người sẵn sàng → chủ phòng bắt đầu → thi đấu → xem kết quả → chơi lại hoặc rời phòng.

## 5. Lưu và kiểm tra dữ liệu

- Dùng thiết kế MySQL đã thống nhất trong cuộc trò chuyện. Nếu không có nội dung đó, yêu cầu tôi cung cấp file SQL trước khi viết phần truy vấn.
- Kiểm tra dữ liệu ở server, không chỉ kiểm tra trên giao diện.
- Server tự xác định người chơi từ phiên đăng nhập, không tin ID người chơi do giao diện gửi lên.
- Ngăn một người chọn hai lần trong một lượt.
- Ngăn một lượt bị tính kết quả hai lần khi hết giờ trùng lúc nhận lựa chọn.
- Dùng cách xử lý đơn giản, giải thích rõ cách hoạt động.
- Chỉ lưu và cập nhật những dữ liệu cần thiết.

## 6. Cách thực hiện

Chia công việc thành các bước:

1. Tạo dự án, cấu trúc thư mục và kết nối MySQL.
2. Làm đăng ký, đăng nhập, đăng xuất.
3. Làm tạo phòng, vào phòng và sẵn sàng.
4. Làm kết nối WebSocket và chat nhanh.
5. Làm luật thi đấu và giao diện sân bóng.
6. Làm xử lý hết giờ, mất kết nối và bỏ cuộc.
7. Làm kết quả, lịch sử và chơi lại.
8. Kiểm tra toàn bộ chương trình.

Ở mỗi bước:

- Nêu ngắn gọn việc cần làm.
- Viết code và ghi rõ đường dẫn từng file.
- Hướng dẫn chạy, kiểm tra bằng thao tác cụ thể.
- Ghi phần đã hoàn thành và cách kiểm tra vào một file Markdown chung.
- Hoàn thành bước nào thì dừng ở bước đó, chờ tôi yêu cầu mới làm tiếp.

**Bắt đầu với bước 1.**
