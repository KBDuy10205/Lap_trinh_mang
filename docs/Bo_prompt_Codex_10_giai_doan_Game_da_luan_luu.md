# BỘ PROMPT CODEX — XÂY DỰNG GAME ĐÁ LUÂN LƯU 2 NGƯỜI


## Cách sử dụng

1. Đặt tài liệu quy trình vào thư mục dự án, ưu tiên `docs/Quy_trinh_xay_dung_phan_mem_chi_tiet.md`. Nếu dùng tên/vị trí khác, chỉ rõ cho Codex.
2. Gửi **Khối quy tắc chung** bên dưới một lần, kèm **Prompt 1**. Codex lưu quy tắc vào `docs/CODEX_RULES.md` để đọc lại ở những lượt sau.
3. Kiểm tra `docs/BAO_CAO_DU_AN.md` sau mỗi phần. Đây là **một file báo cáo tổng hợp duy nhất**, được cập nhật dần; không tạo 10 báo cáo tách rời.
4. Chỉ gửi prompt của phần tiếp theo khi muốn tiếp tục. Không gửi toàn bộ bộ prompt kèm lệnh “thực hiện tất cả”.
5. Nếu bắt đầu cuộc trò chuyện Codex mới, yêu cầu đọc `docs/CODEX_RULES.md`, `docs/BAO_CAO_DU_AN.md` và tài liệu nguồn, rồi gửi đúng prompt cần làm.
6. Riêng phần 8 có các điểm dừng nhỏ 8.1–8.6 để khối lượng code dễ xem xét. Chỉ làm một điểm dừng mỗi lần được yêu cầu; vẫn ghi chung trong mục 8 của báo cáo.

**Lưu ý về phạm vi:** không đổi 10 giai đoạn thành 10 chức năng. Ví dụ đăng nhập, tạo phòng, chấm lượt là các phần lập trình thuộc giai đoạn 8.

## Bảng giao việc và sơ đồ bắt buộc

| Phần | Việc được làm | Đầu ra trong báo cáo | Sơ đồ bắt buộc |
| --- | --- | --- | --- |
| 1 | Xác lập cách làm và kiểm kê dự án | Phạm vi công việc, quy trình, trạng thái ban đầu | Không bắt buộc |
| 2 | Nghiên cứu bài toán | Bối cảnh, bằng chứng, giả thuyết, câu hỏi khảo sát | Không bắt buộc |
| 3 | Mục tiêu, phạm vi, khả thi | Mục tiêu, MVP, bản hoàn chỉnh, rủi ro | Không bắt buộc |
| 4 | Đặc tả yêu cầu | FR/NFR/BR, tiêu chí chấp nhận | Không bắt buộc |
| 5 | Phân tích | Tác nhân, use case, mô hình miền, trạng thái | Tác nhân–chức năng; luồng lượt; mô hình miền; trạng thái trận |
| 6 | Thiết kế | Kiến trúc, SQL, giao thức, giao diện | Kiến trúc; ERD; sequence lượt; sequence reconnect |
| 7 | Chuẩn bị môi trường | Backlog, cấu trúc thật, phiên bản, lệnh chạy | Không bắt buộc; cập nhật sơ đồ thiết kế nếu có thay đổi |
| 8 | Lập trình và tích hợp theo từng điểm dừng | Code thực tế, SQL sử dụng, kiểm tra và giới hạn | Sequence xử lý lượt theo code thật; đồng bộ ERD/kiến trúc |
| 9 | Kiểm thử và sửa lỗi | Test case, bằng chứng chạy, lỗi, truy vết | Cập nhật sơ đồ bị ảnh hưởng bởi lỗi đã sửa |
| 10 | Chuẩn bị và ghi nhận nghiệm thu | Kịch bản, checklist, kết quả thật, tồn đọng | Cập nhật toàn bộ sơ đồ để khớp phiên bản nghiệm thu |

---

## Khối quy tắc chung — gửi kèm Prompt 1

Sao chép nguyên khối sau:

```text
Bạn là Codex hỗ trợ tôi thực hiện đồ án lập trình mạng. Làm việc trực tiếp trong thư mục dự án và giải thích bằng tiếng Việt, đủ để sinh viên đọc code và bảo vệ được.

A. ĐỀ TÀI VÀ NGUỒN THÔNG TIN
- Game đá luân lưu 2 người dùng Java Spring Boot, WebSocket; frontend HTML/CSS/JavaScript thuần; MySQL lưu tài khoản và kết quả.
- Đọc tài liệu Quy_trinh_xay_dung_phan_mem_chi_tiet.md tại vị trí tôi cung cấp; chỉ đọc phần và thông tin liên quan, không coi toàn bộ lộ trình là lệnh thực hiện ngay.
- Nếu thiếu tài liệu nguồn, ghi rõ thiếu; dùng phạm vi mô tả dưới đây làm cơ sở tạm thời, không giả vờ đã đọc file.
- Chức năng cuối: đăng ký/đăng nhập/đăng xuất, hồ sơ người chơi, tạo/phòng bằng mã đúng 2 người, chat nhanh, sân bóng ba hướng, chọn kín, đổi vai, 5 lượt mỗi bên, sút từng cặp khi hòa, hoạt ảnh, đồng bộ, deadline, mất kết nối, lịch sử, thống kê và yêu cầu chơi lại.
- Luật: trùng hướng cản phá; khác hướng ghi bàn; chỉ server chấm. Một lượt = một lần sút, một cặp = mỗi người sút một lần. Đủ 5 lượt mỗi bên rồi mới xét kết thúc giai đoạn đầu; không tự thêm kết thúc sớm. Khi hòa, xét thắng sau đủ cặp bổ sung.
- Tham số đề xuất từ tài liệu: 15 giây chọn, 30 giây kết nối lại, khoảng 2 giây hoạt ảnh. Đây là mặc định thiết kế minh họa, không khẳng định đã được giảng viên duyệt. Ghi quyết định hiện hành trong báo cáo; khi người dùng đã chốt thì không hỏi lại.
- Chính sách minh họa: người sút thiếu chọn => MISS; chỉ thủ môn thiếu chọn => GOAL; cả hai thiếu => MISS. Mất kết nối không reset deadline hiện tại; xử lý lượt hiện tại rồi chờ nối lại trước lượt kế. Quá hạn, bên offline thua nếu bên kia online; cả hai quá hạn thì hủy, không tính thắng/thua. Trận đã kết thúc không bị timer sau đó ghi đè.

B. GIỮ CODE ĐƠN GIẢN, RÕ RÀNG
- Java thông thường, tên biến/hàm tiếng Anh rõ nghĩa; giải thích và comment cần thiết bằng tiếng Việt. Ưu tiên if/else, vòng lặp, enum, class nhỏ có trách nhiệm cụ thể; tránh viết quá tắt, lambda/stream khó đọc hoặc generic phức tạp khi vòng lặp dễ hiểu hơn.
- Không tự tạo design pattern, lớp cha, interface hoặc abstraction nếu không giải quyết nhu cầu thực tế. Không viết interface + implementation chỉ cho một lớp đơn giản. Không ép mọi class phải có service/repository riêng.
- Dùng cấu trúc package gọn theo chức năng; không để cả ứng dụng trong một class, cũng không chia một thao tác ngắn thành hàng chục file.
- Spring Boot chỉ chủ yếu phục vụ khởi động, HTTP, WebSocket, cấu hình và quản lý dependency cần thiết. Luật chơi là Java thuần, tách khỏi controller/handler để đọc và kiểm thử độc lập.
- Không dùng JPA, Hibernate, Spring Data Repository, MyBatis, Lombok, MapStruct, Redis, microservices, message broker hoặc frontend framework nếu tôi chưa yêu cầu.
- Frontend dùng HTML/CSS/JavaScript thuần, fetch và WebSocket của trình duyệt; không thêm React/Vue hoặc chuỗi build frontend khi không cần.
- Không tự viết thuật toán băm mật khẩu hay cơ chế mật mã. Có thể dùng thư viện BCrypt đã được duy trì cho đúng phần băm/kiểm tra mật khẩu; ghi rõ lý do thêm dependency nhỏ này. Không suy rộng thành việc dùng cả hệ thống ORM hoặc framework khác.

C. SQL VÀ JDBC
- Tự viết DDL, INSERT, SELECT, UPDATE và các truy vấn cần thiết. Truy cập bằng JDBC trực tiếp: Connection, PreparedStatement, ResultSet; không dùng ORM hoặc JdbcTemplate trong phương án mặc định này.
- Dùng DataSource được cấu hình để lấy Connection; không tự viết connection pool. DataSource/driver/pool hỗ trợ kết nối không thay thế việc tự viết SQL.
- Dùng PreparedStatement cho dữ liệu đầu vào; tuyệt đối không nối dữ liệu người dùng vào câu SQL. Tên cột/sắp xếp động nếu có phải qua whitelist cố định.
- Đóng tài nguyên bằng try-with-resources. Transaction nhiều truy vấn phải dùng cùng Connection, setAutoCommit(false), commit/rollback và khôi phục trạng thái cần thiết trước khi trả kết nối về pool.
- Không giả định @Transactional tự bao phủ các Connection lấy thủ công. Nếu dùng transaction JDBC trực tiếp, thể hiện ranh giới rõ trong code.
- Lưu script thay đổi cấu trúc trong database/ theo thứ tự; không dùng tự sinh bảng. Không tự chạy DROP, TRUNCATE hoặc reset database đang có dữ liệu. Không ghi mật khẩu thật vào source hay báo cáo.
- Giải thích các truy vấn quan trọng: tham số, join, lọc lịch sử của chính người dùng, thống kê thắng/thua và chống lưu kết quả trùng.

D. ĐỘ TIN CẬY VÀ XÁC THỰC VỪA ĐỦ
- Server xác định danh tính từ phiên đã xác thực, vai trò, tỷ số, deadline và kết quả; không tin userId/vai/tỷ số do client tự gửi.
- Ưu tiên frontend cùng origin do Spring Boot phục vụ, phiên HttpSession và cookie phù hợp thay cho JWT nếu không có yêu cầu khác. Thiết kế kiểm tra phiên, đăng xuất, chống CSRF cho HTTP thay đổi dữ liệu và kiểm tra Origin/quyền cho WebSocket. Không bỏ kiểm soát truy cập để giảm số dòng code.
- Không đưa hướng đã chọn vào payload hoặc snapshot công khai trước khi công bố. Tạo DTO phản hồi riêng; không serialize toàn bộ trạng thái trận nội bộ.
- Chỉ một tiến trình server trong phạm vi hiện tại. Tuần tự hóa lệnh và timer của cùng trận bằng một cách đơn giản được giải thích; không trộn nhiều cơ chế đồng thời không cần thiết.
- Một lượt chấm một lần; gửi lặp không cộng lại; message lượt/trận cũ bị từ chối; snapshot có phiên bản. Một tài khoản chỉ có một kết nối được phép điều khiển; callback đóng socket cũ không làm socket mới bị offline.
- Không deploy công khai, đổi dịch vụ hoặc thêm kiến trúc ngoài phạm vi phần đang được giao.

E. MỘT BÁO CÁO CHUNG, CÓ SƠ ĐỒ THẬT
- Lưu quy tắc này vào docs/CODEX_RULES.md. Nếu đã có thì cập nhật có chủ đích, không xóa quy tắc của người dùng. Đọc AGENTS.md nếu có và không ghi đè file đó.
- Báo cáo tổng hợp duy nhất là docs/BAO_CAO_DU_AN.md. Không tạo report-1.md ... report-10.md. README, SQL, code và CODEX_RULES là file sản phẩm/hướng dẫn, không phải báo cáo thay thế.
- Báo cáo có 10 mục chính tương ứng 10 phần, mục lục, bảng tiến độ, bảng giả định/quyết định và nhật ký cập nhật. Chỉ điền kết quả phần đã làm; phần chưa làm ghi CHƯA THỰC HIỆN.
- Mỗi mục có: mục tiêu; đầu vào; công việc/kết quả cụ thể; file tạo/sửa; kiểm tra và bằng chứng; giả định/chưa chốt; hạn chế/tồn đọng; trạng thái; bước tiếp theo CHỜ YÊU CẦU.
- Trạng thái phân biệt: HOÀN THÀNH, HOÀN THÀNH CÓ GIỚI HẠN, BỊ CHẶN, CHƯA THỰC HIỆN. “Đã chuẩn bị” không đồng nghĩa “đã chạy kiểm tra” hoặc “đã được người dùng nghiệm thu”.
- Khi cập nhật, giữ nội dung đúng của phần trước, sửa các chỗ bị ảnh hưởng có ghi lý do; không nối lặp vô hạn các báo cáo giống nhau.
- Nơi prompt yêu cầu sơ đồ: chèn sơ đồ thực sự bằng fenced Mermaid hợp lệ vào đúng mục báo cáo. Không chỉ ghi “cần vẽ sơ đồ”, không để TODO, không dẫn sang trang ngoài thay cho sơ đồ.
- Dùng flowchart, sequenceDiagram, classDiagram, stateDiagram-v2, erDiagram theo mục đích. Mermaid không có cú pháp useCaseDiagram chuẩn: nếu mô tả tác nhân–chức năng bằng flowchart, ghi rõ là sơ đồ chức năng dạng flowchart, không gọi nhầm là UML use case chuẩn.
- Dùng nhãn ngắn, quote nhãn có ký tự đặc biệt, chia sơ đồ lớn thành các phần dễ đọc. Không vẽ bằng ASCII. Có chú giải và đoạn giải thích sau sơ đồ; tên đối tượng nhất quán với yêu cầu/code/SQL.
- Nếu có công cụ render Mermaid thì render kiểm tra; nếu chưa có thì kiểm tra cú pháp, khai báo rõ “chưa render xác minh”, không bịa đã xem hình.
- Không bịa nguồn, phỏng vấn, kết quả benchmark, test pass, ảnh chụp hoặc xác nhận nghiệm thu. Việc chưa thực hiện phải ghi đúng tình trạng.

F. ĐIỂM DỪNG BẮT BUỘC
- Chỉ thực hiện đúng phần/điểm dừng được yêu cầu trong tin nhắn hiện tại. Không tự làm phần sau vì đã có toàn bộ kế hoạch.
- Phần 1–6: chủ yếu phân tích và thiết kế; không viết code ứng dụng chạy thật. Phần 6 được viết SQL thiết kế và đặc tả giao thức. Phần 7 được dựng bộ khung, cấu hình và kiểm tra môi trường; chưa triển khai nghiệp vụ.
- Phần 8 chia 8.1–8.6: mỗi lần chỉ làm một điểm dừng được yêu cầu. Chỉ sang phần 9 sau khi phần 8 đủ điều kiện hoặc có quyết định điều chỉnh phạm vi rõ ràng của tôi.
- Hoàn tất phần hiện tại, cập nhật và tự kiểm tra báo cáo rồi DỪNG. Trả lời ngắn: việc đã làm, file chính, kiểm tra thật, vấn đề còn lại và “Đã dừng tại phần X, chờ yêu cầu tiếp theo”.
- Một giả định thường lệ không ngăn việc đang làm: ghi rõ và tiếp tục trong phạm vi. Nếu thiếu thông tin bắt buộc hoặc môi trường không cho kiểm chứng, hoàn tất phần độc lập, ghi BỊ CHẶN/giới hạn; không đoán dữ liệu nhạy cảm hoặc giả vờ hoàn tất.
- Lệnh “sửa phần X” chỉ cho phép sửa phần X và cập nhật ảnh hưởng liên quan; không cho phép tự chạy phần X+1.

Chỉ ghi nhận và lưu quy tắc
```

---

## Prompt 1 — Hiểu quy trình và xác lập cách thực hiện

```text
THỰC HIỆN PHẦN 1 — HIỂU QUY TRÌNH VÀ KIỂM KÊ BAN ĐẦU.

Áp dụng quy tắc chung đã gửi. Nếu đã có docs/CODEX_RULES.md thì đọc lại trước khi làm. Không tự bắt đầu phần 2.

Công việc:
1. Đọc AGENTS.md nếu có; xác định thư mục dự án, tài liệu nguồn, code/config/SQL đang tồn tại. Chỉ kiểm kê, không xóa hoặc tái cấu trúc code sẵn có.
2. Xác nhận cách hiểu: tôi muốn đi qua mục 1–10 của tài liệu quy trình; đề tài minh họa là game đá luân lưu 2 người.
3. Mô tả ngắn vai trò frontend, server, WebSocket và MySQL; phân biệt “phần mềm chạy” với “đáp ứng yêu cầu đã kiểm chứng”.
4. Lập bảng 10 giai đoạn: mục tiêu, đầu vào, đầu ra, điều kiện hoàn thành và giới hạn công việc.
5. Đề xuất nhịp làm việc nhỏ, demo thường xuyên; giữ nguyên các điểm dừng do tôi đặt. Không tự nhận nhóm đang làm Scrum đầy đủ nếu không có bằng chứng.
6. Chốt nguyên tắc đơn giản: Java rõ nghĩa, JDBC và SQL tự viết, HTML/CSS/JS thuần, hạn chế dependency theo quy tắc chung.
7. Tạo docs/BAO_CAO_DU_AN.md nếu chưa có: mục lục, bảng tiến độ 1–10, các mục chính và bảng quyết định/giả định. Nếu đã có thì tiếp nối, không làm mất nội dung.
8. Điền mục 1 bằng kết quả kiểm kê thực tế và cách làm; mục sau để CHƯA THỰC HIỆN.

Tiêu chí hoàn thành:
- Người đọc hiểu lộ trình, sản phẩm dự kiến và cách kiểm soát phạm vi.
- Có CODEX_RULES và báo cáo tổng hợp.
- Chưa viết ứng dụng, chưa chọn thêm framework, chưa làm phần 2.

Tự kiểm tra nội dung mục 1, lưu file, tóm tắt và DỪNG tại phần 1.
```

## Prompt 2 — Nghiên cứu bài toán và căn cứ

```text
THỰC HIỆN PHẦN 2 — NGHIÊN CỨU BÀI TOÁN.

Đọc docs/CODEX_RULES.md, báo cáo hiện tại và mục 2 của tài liệu nguồn. Chỉ làm phần 2; không viết ứng dụng.

Công việc:
1. Mô tả bối cảnh đồ án lập trình mạng và người chơi mục tiêu. Tách mục tiêu học tập khỏi các kết luận về nhu cầu thị trường.
2. Chọn khoảng 3–4 vấn đề phù hợp: mời đúng đối thủ, giữ kín hướng, hai máy thống nhất lượt/kết quả, trận không bị treo khi thiếu lựa chọn/mất mạng.
3. Phân biệt cho mỗi vấn đề: yêu cầu đã có trong đề tài, giả thuyết về trải nghiệm người dùng, rủi ro kỹ thuật và bằng chứng đang có. Không biến rủi ro dự kiến thành lỗi đã quan sát.
4. Nếu có khả năng tra cứu, chỉ nghiên cứu đủ để hỗ trợ nhận định cần nguồn; ưu tiên tài liệu chính thức cho vấn đề kỹ thuật. Ghi tên nguồn, URL, ngày truy cập, nhận định hỗ trợ và giới hạn. Không khẳng định đã truy cập nếu không truy cập được.
5. Chuẩn bị 6–8 câu hỏi khảo sát ngắn cho hai người chơi: cách mời, hiểu vai, nhận biết đã chọn, thời gian chờ, mất mạng, kết quả và chơi lại.
6. Chuẩn bị kịch bản quan sát prototype về ba nút trái/giữa/phải. Ở phần này chỉ mô tả; chưa tự dựng prototype ứng dụng hoặc thực hiện khảo sát thay người dùng.
7. Lập bảng bằng chứng EV-xx. Không có dữ liệu thì ghi CHƯA THU THẬP và cách thu thập; không điền số liệu mẫu như dữ liệu thật.
8. Đề xuất cách trình bày lý do chọn đề tài trung thực, không phóng đại vấn đề xã hội hoặc nhu cầu thương mại.

Báo cáo:
- Cập nhật mục 2 trong docs/BAO_CAO_DU_AN.md: bối cảnh, người dùng, bảng vấn đề–tác động–bằng chứng–giới hạn, câu hỏi khảo sát, nguồn và điều chưa xác minh.
- Phân biệt hoàn thành nghiên cứu bàn giấy với chưa thực hiện phỏng vấn thực tế. Chưa có khảo sát không được tự ghi đã khảo sát thành công.

Kiểm tra không có nguồn/kết quả bịa, ghi trạng thái phù hợp rồi DỪNG tại phần 2. Không tự sang phần 3.
```

## Prompt 3 — Mục tiêu, phạm vi và tính khả thi

```text
THỰC HIỆN PHẦN 3 — MỤC TIÊU, PHẠM VI VÀ TÍNH KHẢ THI.

Đọc quy tắc, báo cáo và mục 3 tài liệu nguồn. Chỉ làm phần 3; chưa viết code ứng dụng hoặc tự chạy thử nghiệm kỹ thuật thuộc giai đoạn khác.

Công việc:
1. Từ vấn đề phần 2, lập mục tiêu cụ thể: hai người cùng phòng, chọn kín, cùng trạng thái, xử lý thời gian/mạng, tra cứu kết quả.
2. Với mỗi mục tiêu, ghi cách kiểm chứng và đầu ra. Chỉ tiêu độ trễ/tải nếu đề xuất phải ghi điều kiện, cách đo và trạng thái chưa kiểm chứng; không tự nhận đạt một con số.
3. Tách ba mức: MVP kỹ thuật ban đầu; bản cuối đủ nội dung đăng ký; ngoài phạm vi. MVP chưa có chat/lịch sử không được coi là hoàn thành đề tài.
4. Giữ nguyên stack đề tài; ràng buộc SQL tự viết bằng JDBC, không ORM; server một tiến trình; frontend cùng origin và JavaScript thuần.
5. Đánh giá khả thi về kỹ thuật, thời gian, dữ liệu, thiết bị/môi trường, vận hành và chi phí. Nếu chưa biết quy mô nhóm/hạn nộp, ghi giả định; không gán số tiền hoặc lịch cố định như thông tin đã được cung cấp.
6. Lập bảng rủi ro: lộ lựa chọn, race condition, hai người tranh suất phòng, timer/reconnect, gửi trùng, kết quả lưu hai lần và tích hợp muộn.
7. Lập kế hoạch thử nghiệm kỹ thuật cho những điểm chưa chắc, chỉ ghi kế hoạch ở phần này; triển khai kiểm chứng ở phần được giao sau.
8. Liệt kê các tham số mặc định đề xuất và điểm cần quyết định. Không hỏi lại những lựa chọn đã được tôi chốt trong báo cáo.

Báo cáo:
- Cập nhật mục 3 và bảng quyết định/giả định chung.
- Có bảng mục tiêu → yêu cầu liên quan → cách đo/kiểm chứng; bảng phạm vi; bảng khả thi và rủi ro.
- Tách “đề xuất khả thi” khỏi “đã chứng minh chạy được”.

Tự đối chiếu với mô tả đề tài, lưu báo cáo rồi DỪNG tại phần 3. Không tự sang phần 4.
```

## Prompt 4 — Đặc tả yêu cầu

```text
THỰC HIỆN PHẦN 4 — THU THẬP VÀ ĐẶC TẢ YÊU CẦU.

Đọc quy tắc, báo cáo và mục 4 tài liệu nguồn. Chỉ làm phần 4, chưa thiết kế chi tiết database hoặc viết ứng dụng.

Công việc:
1. Hoàn thiện FR-01 đến FR-12 theo tài liệu nguồn: tài khoản; hồ sơ; tạo phòng; tham gia; chat nhanh; sân/vai; chọn kín; chấm/đồng bộ; kết thúc; thời gian/mất mạng; lịch sử/thống kê; chơi lại. Không đổi mã tùy tiện.
2. Mỗi FR có: vai trò, tiền điều kiện, đầu vào, luồng chính, lỗi/luồng thay thế, hậu điều kiện, tiêu chí chấp nhận và ưu tiên.
3. Đặc tả NFR về bí mật, nhất quán, xác thực, đồng bộ, khôi phục và mật khẩu. Yêu cầu phải có cách kiểm tra.
4. Đặc tả BR: ba hướng; lần chọn đầu khóa; server chấm; phân biệt lượt/cặp; đủ 5 lượt mỗi bên; xét thắng sau đủ cặp khi hòa; không thêm luật kết thúc sớm.
5. Chốt rõ cách xử lý thiếu lựa chọn: sút thiếu = MISS; chỉ thủ môn thiếu = GOAL; cả hai thiếu = MISS. Dùng mặc định đề xuất nếu chưa có quyết định khác và đánh dấu giả định, không ghi đã được giảng viên duyệt.
6. Làm rõ thời gian server, ranh giới deadline, kết nối lại, không mở lượt mới khi thiếu người online, thua bỏ cuộc, cả hai offline và server restart. Trận đã kết thúc không bị timer cũ đổi kết quả.
7. Làm rõ mã phòng và danh tính thành viên; một người không ở hai phòng hoạt động; một tài khoản không điều khiển bằng hai socket cùng lúc.
8. Làm rõ chat nhanh là các câu có sẵn, không tự thêm chat tự do; chơi lại phải đủ hai đồng ý và tạo trận mới.
9. Viết ít nhất 3 user story trọng tâm, có tiêu chí Given/When/Then hoặc các bước kiểm chứng rõ.
10. Lập bảng truy vết ban đầu: vấn đề/mục tiêu → FR/NFR/BR → cách nghiệm thu. Ghi xung đột và điều chưa chốt.

Báo cáo:
- Mục 4 là đặc tả yêu cầu đủ chi tiết để phần 5–6 dựa vào, không tản ra nhiều file SRS khác nhau.
- Có bảng thuật ngữ: roomId/roomCode, matchId, turnId, turnNo, pairNo, shooter/keeper, GOAL/SAVE/MISS và FINISHED/ABORTED.

Kiểm tra từng yêu cầu có tiêu chí chấp nhận, lưu báo cáo rồi DỪNG tại phần 4. Không tự sang phần 5.
```

## Prompt 5 — Phân tích hệ thống, có sơ đồ

```text
THỰC HIỆN PHẦN 5 — PHÂN TÍCH HỆ THỐNG.

Đọc quy tắc, báo cáo và mục 5 tài liệu nguồn. Chỉ phân tích phần 5; không viết code ứng dụng, không chọn kiến trúc phức tạp.

Công việc:
1. Xác định tác nhân và vai: khách, người đăng nhập, chủ phòng, người sút, thủ môn. Hai vai sút/bắt thay đổi theo lượt, không phải hai loại tài khoản. Không coi database hoặc server là người dùng nghiệp vụ bên ngoài.
2. Lập danh sách UC có ánh xạ FR. Đặc tả kỹ các UC: tham gia phòng, thực hiện lượt kín, xử lý mất kết nối, kết thúc trận và chơi lại.
3. Với từng UC trọng tâm, có tiền điều kiện, luồng chính, thay thế, ngoại lệ, hậu điều kiện và quy tắc liên quan.
4. Phân tích mô hình miền: User, Room, Match, Turn, Choice, MatchResult, QuickChat; chỉ rõ khái niệm nào chưa chắc cần bảng riêng.
5. Phân tích trạng thái phòng, trận và lượt; không nhầm “kết quả đã chấm” với “hoạt ảnh đã xong”, không cho trận kết thúc quay lại đang chơi vì callback đến muộn.
6. Phân tích đồng thời: tranh suất phòng, hai hướng đến cùng lúc, timer cạnh tranh lựa chọn, retry, reconnect và socket cũ đóng sau socket mới.
7. Bổ sung ví dụ từng lượt cho giai đoạn đầu và một cặp sút khi hòa để người mới hiểu cách đếm; số lượt/tỷ số phải hợp lệ.

SƠ ĐỒ BẮT BUỘC trong chính mục 5 của docs/BAO_CAO_DU_AN.md:
A. Flowchart tác nhân–chức năng theo nhóm, ghi rõ đây là biểu diễn chức năng bằng flowchart, không gọi là UML use case chuẩn.
B. Activity dạng flowchart cho một lượt: mở lượt → nhận kín → đủ hai hoặc hết giờ → GOAL/SAVE/MISS → cập nhật → đổi vai hoặc kết thúc.
C. classDiagram mô hình miền với quan hệ và số lượng phù hợp, chưa ép thành sơ đồ class code.
D. stateDiagram-v2 cho vòng đời Match; thể hiện các nhánh kết thúc và hủy. Nếu cần, thêm sơ đồ nhỏ cho Turn để không nhồi nhiều ý vào một hình.

Mỗi sơ đồ phải có nội dung thực, nhãn rõ và giải thích ngắn; không để placeholder. Kiểm tra sơ đồ khớp yêu cầu phần 4; render nếu có công cụ, nếu chưa có phải ghi chưa render xác minh.

Cập nhật mục 5 và các quyết định ảnh hưởng, kiểm tra rồi DỪNG tại phần 5. Không tự sang thiết kế phần 6.
```

## Prompt 6 — Thiết kế kỹ thuật, SQL và sơ đồ

```text
THỰC HIỆN PHẦN 6 — THIẾT KẾ HỆ THỐNG.

Đọc quy tắc, báo cáo và mục 6 tài liệu nguồn. Chỉ thiết kế phần 6. Được viết file SQL thiết kế trong database/, chưa thực thi lên database có dữ liệu và chưa viết ứng dụng hoàn chỉnh.

Công việc:
1. Chọn kiến trúc nhỏ: một Spring Boot phục vụ frontend cùng origin, REST + WebSocket JSON thuần, Java GameEngine và JDBC/MySQL. Không thêm JPA/STOMP/Redis/React khi chưa có yêu cầu.
2. Đề xuất package/file tối thiểu và trách nhiệm. Một service có thể trực tiếp dùng một repository cụ thể; không tạo interface chỉ để “đúng mẫu”. Luật chơi độc lập Spring.
3. Chốt session HttpSession, cấu hình cookie theo môi trường, đổi session id khi đăng nhập, logout thu hồi phiên/socket, kiểm soát CSRF cho REST và Origin/quyền của WebSocket. Không tự viết mật mã; chọn dependency băm mật khẩu nhỏ có lý do.
4. Thiết kế users, matches và match_turns nếu cần lưu chi tiết. Phân biệt roomCode và roomId. Nêu dữ liệu RAM, dữ liệu MySQL, khóa/ràng buộc/chỉ mục, trạng thái kết thúc và lý do hủy.
5. Tự viết database/001_schema.sql. Không dùng DROP/TRUNCATE; không dùng auto-DDL của ORM. Nếu schema đã có, thiết kế script thay đổi riêng thay vì phá dữ liệu.
6. Viết database/queries.sql với truy vấn tham số minh họa cho: đăng ký/tìm tài khoản; sửa hồ sơ; tạo trận; lưu kết quả theo matchId; lịch sử phân trang của chính người chơi; thống kê thắng/thua bỏ qua ABORTED. Ghi rõ truy vấn mẫu chưa phải kết quả đã chạy và cách bind tham số JDBC.
7. Thiết kế transaction lưu kết quả: cùng Connection, commit/rollback, điều kiện chuyển IN_PROGRESS → FINISHED hoặc ABORTED; retry không lưu/cộng thống kê hai lần. Phân biệt commit database với phát socket.
8. Đặc tả REST và thông điệp WebSocket: lệnh vào, sự kiện ra, private/broadcast, payload JSON, lỗi, actionId, matchId/turnId, stateVersion, deadline/serverTime. SUBMIT_CHOICE không tự khai danh tính hay tỷ số.
9. Thiết kế DTO công khai tách state kín; snapshot không lộ hướng trước công bố. Giải thích cách lọc sự kiện cũ, lấy snapshot và reconnect.
10. Chọn MỘT cách đồng bộ dễ hiểu cho một server, ví dụ mọi lệnh và timer đi qua hàng đợi FIFO đơn giản cho phạm vi đồ án. Giải thích quy tắc tiếp nhận trước deadline, thứ tự timeout và hạn chế hiệu năng; không vừa khẳng định đơn giản vừa tạo nhiều cơ chế khóa lồng nhau.
11. Phác thảo màn hình bằng mô tả/bảng thành phần hoặc wireframe hỗ trợ: auth, hồ sơ, sảnh, phòng chờ, sân, kết quả, lịch sử. Sân có vai, ba hướng, tỷ số, số lượt và trạng thái chờ.
12. Chốt chính sách restart: đánh dấu ABORTED cho trận còn dang dở của phiên server trước theo mô hình một tiến trình; không hứa phục hồi RAM khi chưa có thiết kế lưu tương ứng.

SƠ ĐỒ BẮT BUỘC chèn ngay trong mục 6 của báo cáo:
A. Flowchart kiến trúc: trình duyệt, lớp HTTP/socket, room/game, JDBC và MySQL; thể hiện luồng giao tiếp cần thiết.
B. erDiagram đúng với SQL, bao gồm hai vai người chơi trong matches và các khóa ngoại liên quan.
C. sequenceDiagram nhận hai lựa chọn kín → ACK riêng → công bố → chuyển lượt/kết thúc. Không có hướng được gửi sớm sang client đối thủ.
D. sequenceDiagram mất kết nối → deadline hiện tại → kết nối lại/snapshot hoặc quá hạn → kết thúc/hủy.

Báo cáo có đường dẫn SQL, danh sách dependency dự kiến kèm lý do, hợp đồng giao thức, ranh giới transaction và các sơ đồ thực tế. Chưa tạo sơ đồ chung chung không khớp tên trường.

Kiểm tra tính nhất quán phần 4–6 và các file SQL; ghi phần chưa chạy. Lưu báo cáo rồi DỪNG tại phần 6, không tự dựng môi trường phần 7.
```

## Prompt 7 — Lập kế hoạch, dựng khung và kiểm tra môi trường

```text
THỰC HIỆN PHẦN 7 — LẬP KẾ HOẠCH VÀ CHUẨN BỊ MÔI TRƯỜNG.

Đọc quy tắc, báo cáo và mục 7 tài liệu nguồn. Được dựng bộ khung và kiểm tra môi trường, chưa triển khai chức năng nghiệp vụ phần 8.

Công việc:
1. Kiểm tra runtime/công cụ sẵn có: Java, Maven hoặc wrapper đang có, MySQL/kết nối được cấp. Ghi phiên bản thực tế; không đọc/in bí mật, không cài thêm công cụ hệ thống nặng nếu không cần.
2. Dùng repository hiện tại nếu có; không tạo lại project hoặc xóa file người dùng. Chọn phiên bản tương thích theo tài liệu chính thức, tái sử dụng cấu hình đã chốt.
3. Tạo bộ khung Maven/Spring Boot tối thiểu: entrypoint, config mẫu, static/index.html tối giản và test smoke khởi động phù hợp. Trang trống phải ghi đúng “bộ khung”, không giả là game hoàn thành.
4. Đặt frontend tại src/main/resources/static/ để giảm cấu hình. Package dự kiến: auth, player, room, game, history, websocket và config nếu thực sự cần. Không tạo hàng loạt file trống/interface chưa dùng.
5. Dependency chỉ đủ chạy HTTP, WebSocket, DataSource/JDBC + driver MySQL, băm mật khẩu cần thiết và test phù hợp. Không vô tình kéo JPA/Hibernate qua starter không cần thiết.
6. Tạo cấu hình bằng biến môi trường và file mẫu không chứa bí mật. Tạo/cập nhật .gitignore; không đưa thông tin đăng nhập thật vào source.
7. Giữ script SQL phần 6. Chỉ khởi tạo lên database phát triển mới/trống có thông tin rõ; nếu không có môi trường, ghi lệnh hướng dẫn, không đoán mật khẩu hoặc dùng database đang có dữ liệu.
8. Viết README ngắn: cấu hình, tạo schema, lệnh build/run/test; nếu người dùng ở Windows thì có lệnh PowerShell tương ứng với wrapper thực sự có trong dự án. Không ghi wrapper đã tồn tại nếu chưa tạo được.
9. Chia backlog có ID, phụ thuộc, đầu ra và cách kiểm tra. Phần code chia 8.1–8.6 đúng các điểm dừng của bộ prompt.
10. Thực hiện build/smoke/kết nối ở mức môi trường cho phép. Nếu thiếu MySQL hoặc không tải được dependency, ghi lệnh, lỗi và phần đã kiểm tra độc lập; không báo build hoặc DB thành công khi chưa chạy.

Báo cáo:
- Mục 7 có bảng phiên bản, cấu trúc thư mục thực tế dạng bảng, dependency + lý do, backlog, cách chạy và kết quả thật.
- Định nghĩa sẵn sàng/hoàn thành cho phần 8; xác định việc tiếp theo là 8.1.
- Nếu thay thiết kế phần 6 để phù hợp môi trường, ghi lý do và cập nhật sơ đồ liên quan ngay trong báo cáo.

Kiểm tra phạm vi không lẫn code nghiệp vụ, lưu báo cáo rồi DỪNG tại phần 7. Không tự viết chức năng phần 8.
```

## Prompt 8 — Lập trình theo các điểm dừng nhỏ

Gửi prompt này để bắt đầu **8.1**, không phải cho phép Codex tự làm liên tục cả sáu điểm dừng.

```text
THỰC HIỆN PHẦN 8 — LẬP TRÌNH VÀ TÍCH HỢP.
LẦN NÀY CHỈ LÀM 8.1, HOẶC ĐÚNG ĐIỂM DỪNG TÔI CHỈ ĐỊNH TRONG TIN NHẮN.

Đọc docs/CODEX_RULES.md, báo cáo phần 4–7, SQL và code thực tế. Áp dụng thiết kế đã chốt, không dựng lại dự án. Mọi kết quả ghi chung vào mục 8 của docs/BAO_CAO_DU_AN.md.

Kế hoạch điểm dừng:

8.1 — Tài khoản, hồ sơ và JDBC:
- Viết truy cập users bằng JDBC trực tiếp và PreparedStatement, không ORM/JdbcTemplate.
- Đăng ký, đăng nhập/đăng xuất, xem/sửa tên hiển thị; băm/kiểm tra mật khẩu bằng thư viện đã chọn.
- Quản lý session, lỗi đăng nhập, CSRF HTTP, cấu hình cookie theo môi trường. Không trả password_hash.
- Giao diện auth/hồ sơ tối giản, có loading/lỗi và validation cần thiết.
- Kiểm tra hợp lệ, trùng định danh, sai mật khẩu, chưa đăng nhập, sửa người khác và logout. Không viết phòng/trận trong điểm dừng này.

8.2 — Phòng, kết nối WebSocket và chat nhanh:
- Tạo/tham gia phòng bằng mã, đúng 2 người khác nhau, sẵn sàng và rời phòng chờ.
- Quản lý một phòng hoạt động/người; tranh suất phòng xử lý nguyên tử.
- Xác thực socket từ session, kiểm tra Origin và thành viên mỗi lệnh; một kết nối được phép điều khiển/tài khoản.
- Chat nhanh theo danh mục câu, giới hạn tần suất và phạm vi phòng.
- Test hai tài khoản độc lập, người thứ ba, phòng khác, đăng xuất khi còn socket và thay socket cũ. Chưa chấm trận ở bước này.

8.3 — Lượt sút và luật trận bằng Java rõ ràng:
- Viết GameEngine/MatchState/TurnState tối thiểu, tách khỏi HTTP/socket và database.
- Tích hợp chọn LEFT/CENTER/RIGHT, khóa chọn, ACK riêng, DTO công khai và chấm một lần.
- Đổi vai; đủ 5 lượt mỗi bên; hòa thì xét thắng sau đủ cặp.
- Gửi lại actionId, sai turnId/matchId và lệnh sau khóa không gây tác dụng lặp.
- Kiểm tra 9 tổ hợp hướng, số lượt, kết thúc và tính kín bằng các test có giá trị. Không ghi luồng mất mạng đã hoàn thành khi chưa làm 8.4.

8.4 — Deadline, ngắt mạng và snapshot:
- Timer dùng cùng cơ chế tuần tự hóa với lệnh người chơi; dùng đồng hồ server/Clock có thể kiểm thử.
- Áp dụng thiếu lựa chọn, reconnect không reset deadline, chờ trước lượt mới, bỏ cuộc, cả hai offline và timer cũ theo BR.
- Snapshot có phiên bản và lọc hướng kín; kết nối mới thay kết nối cũ đúng danh tính.
- Mô phỏng race timer/lựa chọn, retry, reconnect, socket cũ đóng, trận vừa kết thúc rồi nhận timer muộn. Chưa tuyên bố đã lưu lịch sử nếu 8.5 chưa hoàn tất.

8.5 — SQL kết quả, lịch sử, thống kê và chơi lại:
- Tự viết SQL tạo/cập nhật matches và match_turns theo thiết kế; transaction JDBC rõ ranh giới.
- Chống lưu/cộng thống kê trùng khi retry hoặc lỗi giữa các truy vấn. Nếu tính thống kê bằng SELECT thì không tạo thêm bộ đếm thừa.
- Trạng thái lưu kết quả phân biệt với chấm trận và gửi socket; lỗi DB được ghi nhận và có cách thử lại hợp lý.
- Lịch sử phân trang và thống kê chỉ của tài khoản được phép; ABORTED không tính thắng/thua.
- Hai bên chấp nhận chơi lại mới tạo matchId mới; giữ trận cũ. Restart xử lý trận dang dở theo thiết kế.
- Kiểm tra rollback/retry, lỗi ghi kết quả và dữ liệu sau chơi lại.

8.6 — Hoàn thiện giao diện và tích hợp toàn bộ:
- Hoàn thiện sân 3 hướng, vai, tỷ số, số lượt, thời gian, trạng thái chờ và hoạt ảnh theo TURN_RESULT.
- Hiển thị kết quả, lịch sử/thống kê, chat nhanh, mất kết nối và yêu cầu chơi lại rõ ràng.
- Không để frontend chấm luật, tự cộng tỷ số hoặc phụ thuộc hoạt ảnh để quyết định server chuyển lượt.
- Chạy luồng hoàn chỉnh trên hai phiên độc lập; cập nhật README theo code thật.
- Hoàn tất các liên kết còn thiếu; phân biệt smoke/integration đã chạy với kiểm thử đầy đủ sẽ làm ở phần 9.

YÊU CẦU Ở MỌI ĐIỂM DỪNG:
1. Chỉ làm đúng một điểm dừng hiện tại; sửa phần trước nếu là phụ thuộc bắt buộc và ghi rõ phạm vi. Không tự sang điểm dừng sau.
2. Trước khi sửa, đọc code/SQL liên quan. Giữ thay đổi tập trung, không viết lại toàn dự án cho đẹp.
3. Tên hàm/class rõ, comment giải thích quyết định khó; không comment mọi dòng hoặc dùng pattern dư thừa.
4. Chạy kiểm tra thích hợp với thay đổi. Không tạo test vô nghĩa chỉ so sánh chính cách triển khai; ưu tiên luật, quyền, JDBC transaction và đồng thời.
5. Báo cáo mục 8.x: chức năng thực tế; file tạo/sửa; truy vấn đã viết và bind tham số; lệnh chạy/kết quả; tồn đọng; điểm dừng hiện tại. Code chính nằm trong source, không dán toàn bộ vào báo cáo.
6. Sau 8.3 chèn sequenceDiagram nhận/chấm/công bố lượt theo code thật. Sau 8.4 chèn/cập nhật sequence reconnect. Khi schema/kiến trúc đổi, đồng bộ ERD/kiến trúc trong báo cáo. Đó phải là sơ đồ thực, không phải lời hứa sẽ vẽ sau.
7. Chỉ ghi phần 8 hoàn thành khi 8.1–8.6 thực sự đạt hoặc có quyết định thay đổi phạm vi của tôi. Khi còn mục chưa làm, ghi đúng trạng thái tổng thể, không đánh dấu hoàn thành cả phần 8.

Lần này thực hiện 8.1 nếu tôi chưa chỉ định điểm khác. Cập nhật báo cáo, tóm tắt, rồi DỪNG ở đúng 8.x. Không tự làm phần 9.
```

Các lệnh tiếp tục ngắn sau khi đã xem báo cáo từng điểm dừng:

```text
Tiếp tục riêng 8.2 theo kế hoạch trong Prompt 8 và docs/CODEX_RULES.md. Hoàn tất, cập nhật cùng báo cáo rồi dừng; không làm 8.3.
```

```text
Tiếp tục riêng 8.3 theo kế hoạch trong Prompt 8 và docs/CODEX_RULES.md. Có sequenceDiagram theo code thật trong báo cáo. Hoàn tất rồi dừng; không làm 8.4.
```

```text
Tiếp tục riêng 8.4 theo kế hoạch trong Prompt 8 và docs/CODEX_RULES.md. Cập nhật sơ đồ reconnect và các thay đổi liên quan. Hoàn tất rồi dừng; không làm 8.5.
```

```text
Tiếp tục riêng 8.5 theo kế hoạch trong Prompt 8 và docs/CODEX_RULES.md. SQL tự viết, kiểm tra transaction và chống lưu trùng. Hoàn tất, cập nhật báo cáo rồi dừng; không làm 8.6.
```

```text
Tiếp tục riêng 8.6 theo kế hoạch trong Prompt 8 và docs/CODEX_RULES.md. Tích hợp, kiểm tra luồng thật, cập nhật báo cáo và các sơ đồ. Hoàn tất rồi dừng; không làm phần 9.
```

Nếu cuộc trò chuyện mới không còn Prompt 8, gửi lại toàn bộ Prompt 8 và ghi rõ điểm cần làm; đừng chỉ nói “tiếp tục” khi báo cáo chưa cho biết đang ở đâu.

## Prompt 9 — Kiểm thử và sửa lỗi

```text
THỰC HIỆN PHẦN 9 — KIỂM THỬ VÀ SỬA LỖI.

Đọc quy tắc, báo cáo, tiêu chí chấp nhận phần 4, thiết kế phần 6 và code/SQL hiện tại. Chỉ làm phần 9. Nếu phần 8 chưa hoàn tất, ghi rõ các chức năng chưa đủ điều kiện kiểm thử; không tự phát triển chúng để giả như phần 8 đã xong.

Công việc:
1. Lập kế hoạch theo rủi ro: luật chơi, lựa chọn kín, quyền phòng, đồng thời, deadline/reconnect, JDBC transaction và lịch sử.
2. Dùng lại và mở rộng các test có giá trị từ phần 8; không chạy lại vô hạn hoặc thêm framework test nặng khi công cụ hiện có đủ dùng.
3. Chuẩn bị dữ liệu giả: A/B trong phòng, C ngoài phòng, phòng khác, trận đủ 10 lượt, trận hòa, trận bỏ cuộc và trận hủy. Hai client dùng phiên độc lập.
4. Kiểm tra đủ các nhóm TC-01 đến TC-24 trong tài liệu nguồn, ánh xạ với case thực tế. Bổ sung case CSRF/Origin/logout và lỗi SQL phù hợp thiết kế đã chọn.
5. Unit test GameEngine: 9 tổ hợp hướng, đổi vai, không kết thúc sớm, hòa/sút đủ cặp, thiếu lựa chọn và timer cũ.
6. Integration/WebSocket: ACK riêng, payload/snapshot không lộ hướng, người ngoài không điều khiển, actionId retry, lượt cũ, timer cạnh tranh, hai người tranh suất cuối, thay socket và reconnect.
7. JDBC/MySQL: lưu đúng một lần, rollback nhiều truy vấn cùng Connection, retry sau lỗi, lịch sử chỉ người tham gia, thống kê bỏ ABORTED. Không gọi kết quả trên DB khác là bằng chứng đã chạy MySQL.
8. End-to-end qua hai phiên: đăng nhập → phòng → chat → chơi → kết quả → lịch sử → chơi lại; mất mạng và hết giờ có bằng chứng đúng phạm vi.
9. Nếu có bài đo hiệu năng khả thi, ghi phần cứng, môi trường, số phòng, thời gian chạy, cách tính và kết quả; không tự đưa số liệu ước lượng vào cột kết quả đo.
10. Ghi bug với bước tái hiện/expected/actual; sửa lỗi trong phạm vi yêu cầu đã chốt; kiểm thử lại và hồi quy phần liên quan. Không dùng giai đoạn này để thêm tính năng mới hoặc refactor toàn dự án.
11. Khi công cụ/môi trường thiếu, phân biệt PASS, FAIL, BLOCKED, NOT RUN; ghi rõ lệnh, nguyên nhân và cách người dùng chạy lại. Không thay việc thử hai client bằng tuyên bố “đọc code thấy đúng”.

Báo cáo mục 9:
- Kế hoạch; môi trường; dữ liệu thử; bảng từng test và trạng thái; tóm tắt tự động và thủ công; bug và sửa; giới hạn.
- Ma trận FR/NFR/BR → code/SQL → test → bằng chứng → kết quả.
- Đường dẫn test/source và lệnh chạy thực tế; không đưa secrets vào log.
- Khi sửa lỗi làm đổi quy trình/SQL/trạng thái, cập nhật sơ đồ liên quan trong cùng báo cáo để khớp code thật. Nếu không thay sơ đồ thì ghi đã đối chiếu, không dựng hình vô nghĩa chỉ cho đủ số lượng.

Đánh giá điều kiện sang nghiệm thu bằng bằng chứng thực. Cập nhật báo cáo, tóm tắt rồi DỪNG tại phần 9. Không tự nghiệm thu hoặc triển khai.
```

## Prompt 10 — Chuẩn bị và ghi nhận nghiệm thu

```text
THỰC HIỆN PHẦN 10 — NGHIỆM THU.

Đọc quy tắc, báo cáo phần 4–9 và phiên bản code hiện tại. Chỉ làm phần 10; không triển khai công khai, không tự sang phần 11 của quy trình.

Công việc:
1. Xác định phiên bản/commit hoặc trạng thái mã nguồn được xem xét. Không tự commit/push nếu không được yêu cầu hoặc không có quy trình cho phép.
2. Kiểm tra điều kiện: chức năng bắt buộc, test quan trọng, lỗi tồn đọng, môi trường và dữ liệu giả. Thiếu điều kiện thì ghi chưa đủ nghiệm thu, không làm đẹp kết luận.
3. Chuẩn bị kịch bản dễ làm theo cho người dùng/giảng viên:
   - Hai tài khoản đăng nhập, cập nhật hồ sơ.
   - Tạo/tham gia bằng mã; người thứ ba bị chặn; chat nhanh.
   - Hai bên chọn kín, chỉ công bố sau đủ điều kiện.
   - Đủ 5 lượt mỗi bên; tình huống hòa và sút thêm từng cặp.
   - Timeout, reconnect trong hạn, quá hạn/bỏ cuộc và trận hủy theo chính sách.
   - Kết quả lưu, lịch sử/thống kê và cả hai chấp nhận chơi lại.
4. Mỗi kịch bản ghi dữ liệu ban đầu, thao tác, kết quả mong đợi, cách đối chiếu, kết quả thực tế và người xác nhận. Kịch bản chưa thực hiện ghi CHƯA THỰC HIỆN.
5. Nếu có thể tự chạy demo kỹ thuật ở môi trường hiện tại thì thực hiện và ghi là kiểm tra kỹ thuật của Codex. Không tự ký thay người dùng/giảng viên và không biến kiểm tra kỹ thuật thành chấp thuận nghiệp vụ.
6. Phân loại phản hồi: lỗi trong phạm vi, yêu cầu chưa đáp ứng, yêu cầu mới. Với lỗi nhỏ được phép sửa trong phần hiện tại, kiểm tra lại tương ứng và ghi thay đổi; không tự nhận làm yêu cầu mới.
7. Rà soát toàn bộ báo cáo: tên trường, code/SQL, giao thức, luật và kết quả nhất quán. Cập nhật trực tiếp các sơ đồ phần 5–6–8 nếu code cuối đã thay đổi; sơ đồ bắt buộc không được còn TODO hoặc chỉ là danh sách tên sơ đồ.
8. Chuẩn bị hướng dẫn demo ngắn, giới hạn kỹ thuật, cách cấu hình an toàn và lỗi chưa xử lý. Không bịa dữ liệu đánh giá hoặc danh tính người nghiệm thu.
9. Viết kết luận có điều kiện: “đủ điều kiện trình nghiệm thu kỹ thuật”, “chưa đủ điều kiện”, hoặc “đã được người dùng xác nhận” chỉ khi thật sự có xác nhận được cung cấp.

Báo cáo mục 10:
- Phiên bản; checklist; kịch bản; kết quả/bằng chứng; người xác nhận hoặc CHỜ XÁC NHẬN; lỗi/tồn đọng; quyết định nghiệm thu thật.
- Tổng kết mục tiêu đạt/chưa đạt và phạm vi đã triển khai theo FR.
- Kiểm tra tất cả sơ đồ bắt buộc có nội dung thực và khớp phiên bản cuối. Nêu trạng thái render xác minh.
- Cập nhật bảng tiến độ; phần xác nhận người dùng chưa có thì để chờ, không đánh dấu toàn bộ dự án đã được nghiệm thu.

Lưu docs/BAO_CAO_DU_AN.md, gửi tóm tắt kết quả và DỪNG tại phần 10. Chỉ thực hiện công việc tiếp theo khi tôi có yêu cầu mới.
```

---

## Mẫu cấu trúc báo cáo chung

Mẫu này để Codex khởi tạo và điền dần. Sau khi hoàn thành một mục cần có nội dung thực và sơ đồ được yêu cầu, không để nguyên phần hướng dẫn làm kết quả.

```markdown
# BÁO CÁO DỰ ÁN GAME ĐÁ LUÂN LƯU 2 NGƯỜI

## Thông tin và phiên bản
- Tên đề tài:
- Tài liệu nguồn:
- Thời điểm cập nhật:
- Phiên bản/commit được xem xét:
- Giai đoạn và điểm dừng hiện tại:

## Mục lục
[Liên kết đến các mục chính]

## Bảng tiến độ
| Phần | Trạng thái | Đầu ra/bằng chứng | Tồn đọng |
| --- | --- | --- | --- |
| 1 | CHƯA THỰC HIỆN | | |
| 2 | CHƯA THỰC HIỆN | | |
| 3 | CHƯA THỰC HIỆN | | |
| 4 | CHƯA THỰC HIỆN | | |
| 5 | CHƯA THỰC HIỆN | | |
| 6 | CHƯA THỰC HIỆN | | |
| 7 | CHƯA THỰC HIỆN | | |
| 8 | CHƯA THỰC HIỆN | Gồm 8.1–8.6 | |
| 9 | CHƯA THỰC HIỆN | | |
| 10 | CHƯA THỰC HIỆN | | |

## Giả định và quyết định hiện hành
| Mã | Nội dung | Cơ sở | Trạng thái chốt | Ảnh hưởng |
| --- | --- | --- | --- | --- |

## 1. Hiểu quy trình và kiểm kê
CHƯA THỰC HIỆN

## 2. Nghiên cứu bài toán
CHƯA THỰC HIỆN

## 3. Mục tiêu, phạm vi và khả thi
CHƯA THỰC HIỆN

## 4. Đặc tả yêu cầu
CHƯA THỰC HIỆN

## 5. Phân tích hệ thống
CHƯA THỰC HIỆN

## 6. Thiết kế hệ thống
CHƯA THỰC HIỆN

## 7. Kế hoạch và môi trường
CHƯA THỰC HIỆN

## 8. Lập trình và tích hợp
CHƯA THỰC HIỆN

## 9. Kiểm thử và sửa lỗi
CHƯA THỰC HIỆN

## 10. Nghiệm thu
CHƯA THỰC HIỆN

## Nhật ký cập nhật
| Thời điểm | Phần | Thay đổi | Kiểm chứng |
| --- | --- | --- | --- |
```

Trong một mục đã thực hiện, dùng các tiêu đề con: **Mục tiêu — Đầu vào — Kết quả cụ thể — Sơ đồ nếu được yêu cầu — File thay đổi — Kiểm tra/bằng chứng — Giả định/tồn đọng — Trạng thái và điểm dừng**.

## Lệnh sửa hoặc kiểm tra báo cáo mà không làm tiếp

```text
Chỉ rà soát và sửa phần [X] theo phản hồi sau: [nội dung]. Đọc quy tắc chung, giữ cấu trúc code đơn giản và cập nhật docs/BAO_CAO_DU_AN.md cùng sơ đồ bị ảnh hưởng. Không làm phần tiếp theo. Hoàn tất thì dừng.
```

```text
Chỉ kiểm tra docs/BAO_CAO_DU_AN.md với code và SQL hiện tại: phát hiện chỗ ghi đã làm nhưng chưa có bằng chứng, sơ đồ thiếu/sai, tên trường không khớp, kết quả test bị ghi quá mức và điểm dừng không rõ. Sửa báo cáo/sơ đồ để phản ánh đúng thực tế; không tự thêm chức năng, không triển khai và không chạy giai đoạn tiếp theo. Xong thì dừng.
```
