# QUY TRÌNH XÂY DỰNG PHẦN MỀM CHI TIẾT

> Tài liệu thực hành dành cho sinh viên IT và nhóm phát triển nhỏ. Áp dụng chủ yếu cho ứng dụng web có giao diện, backend và cơ sở dữ liệu. Ví dụ xuyên suốt: **game đá luân lưu 2 người chơi sử dụng Java, Spring Boot, WebSocket; giao diện HTML/CSS/JavaScript; MySQL lưu tài khoản và kết quả trận đấu**. Giữ quy trình chung và áp dụng từng giai đoạn vào đề tài này. Các thời lượng và chính sách xử lý ngoại lệ được ghi là đề xuất để nhóm và giảng viên xác nhận.

## Mục lục

1. [Hiểu đúng về quy trình phát triển phần mềm](#1-hiểu-đúng-về-quy-trình-phát-triển-phần-mềm)
2. [Xác định bài toán và nghiên cứu thực tế](#2-xác-định-bài-toán-và-nghiên-cứu-thực-tế)
3. [Xác định mục tiêu, phạm vi và tính khả thi](#3-xác-định-mục-tiêu-phạm-vi-và-tính-khả-thi)
4. [Thu thập và đặc tả yêu cầu](#4-thu-thập-và-đặc-tả-yêu-cầu)
5. [Phân tích hệ thống](#5-phân-tích-hệ-thống)
6. [Thiết kế hệ thống](#6-thiết-kế-hệ-thống)
7. [Lập kế hoạch và chuẩn bị môi trường](#7-lập-kế-hoạch-và-chuẩn-bị-môi-trường)
8. [Lập trình và tích hợp](#8-lập-trình-và-tích-hợp)
9. [Kiểm thử và sửa lỗi](#9-kiểm-thử-và-sửa-lỗi)
10. [Nghiệm thu](#10-nghiệm-thu)
11. [Triển khai và bàn giao](#11-triển-khai-và-bàn-giao)
12. [Vận hành, bảo trì và cải tiến](#12-vận-hành-bảo-trì-và-cải-tiến)
13. [Các công việc xuyên suốt dự án](#13-các-công-việc-xuyên-suốt-dự-án)
14. [Ví dụ triển khai trọn vẹn một chức năng](#14-ví-dụ-triển-khai-trọn-vẹn-một-chức-năng)
15. [Kế hoạch tham khảo cho đồ án 8 tuần](#15-kế-hoạch-tham-khảo-cho-đồ-án-8-tuần)
16. [Bộ tài liệu và cấu trúc thư mục](#16-bộ-tài-liệu-và-cấu-trúc-thư-mục)
17. [Checklist và các lỗi thường gặp](#17-checklist-và-các-lỗi-thường-gặp)
18. [Mẫu biểu dùng ngay](#18-mẫu-biểu-dùng-ngay)

## 1. Hiểu đúng về quy trình phát triển phần mềm

### 1.1. Xây dựng phần mềm gồm những gì?

Xây dựng phần mềm là quá trình tìm hiểu một vấn đề, xác định điều cần giải quyết, thiết kế giải pháp, lập trình, kiểm chứng và đưa giải pháp vào sử dụng. Viết code chỉ là một phần của quá trình này.

Ví dụ: trước khi viết chức năng sút bóng, nhóm phải biết ai đang sút, ai đang bắt, khi nào khóa lựa chọn, cách giữ bí mật hướng chọn, cách tính bàn thắng và cách xử lý hết giờ hoặc mất kết nối.

### 1.2. Bức tranh tổng thể

| Giai đoạn | Câu hỏi cần trả lời | Đầu ra chính |
| --- | --- | --- |
| Nghiên cứu bài toán | Người dùng đang gặp khó khăn gì? | Mô tả vấn đề và bằng chứng |
| Xác định phạm vi | Phiên bản này giải quyết phần nào? | Mục tiêu, phạm vi, MVP |
| Thu thập yêu cầu | Phần mềm phải làm gì? | Đặc tả yêu cầu, tiêu chí chấp nhận |
| Phân tích | Nghiệp vụ, dữ liệu và quy tắc hoạt động thế nào? | Mô hình nghiệp vụ, use case, mô hình miền |
| Thiết kế | Cấu trúc giải pháp cụ thể ra sao? | Kiến trúc, dữ liệu, API, giao diện |
| Chuẩn bị | Ai làm gì, theo thứ tự nào, trong bao lâu? | Backlog, kế hoạch, môi trường |
| Lập trình | Biến thiết kế thành phần mềm thế nào? | Mã nguồn, migration, bản chạy thử |
| Kiểm thử | Phần mềm có đáp ứng yêu cầu không? | Kết quả kiểm thử và lỗi đã xử lý |
| Nghiệm thu | Sản phẩm có đủ điều kiện được chấp nhận? | Biên bản hoặc danh sách xác nhận |
| Triển khai | Đưa vào sử dụng và phục hồi khi lỗi thế nào? | Bản phát hành, hướng dẫn, phương án khôi phục |
| Vận hành | Theo dõi, sửa lỗi và cải tiến ra sao? | Nhật ký vận hành và backlog mới |

Các giai đoạn được trình bày theo thứ tự để dễ học. Trong thực tế, chúng có thể chồng lấn và lặp lại: phát hiện yêu cầu thiếu khi kiểm thử thì quay lại làm rõ yêu cầu và cập nhật thiết kế.

### 1.3. Lựa chọn cách tổ chức công việc

| Cách tổ chức | Cách thực hiện | Khi phù hợp | Điều cần lưu ý |
| --- | --- | --- | --- |
| Tuần tự, thường gọi Waterfall | Hoàn thành phần lớn một giai đoạn trước khi sang giai đoạn tiếp theo | Phạm vi khá ổn định, yêu cầu hồ sơ rõ | Phản hồi muộn có thể làm tăng chi phí sửa |
| Lặp và tăng trưởng | Mỗi vòng hoàn thiện thêm một phần sản phẩm | Nhóm cần có bản chạy sớm và học từ phản hồi | Cần quản lý phụ thuộc và ưu tiên |
| Agile | Làm việc theo các nguyên tắc thích ứng, hợp tác và phản hồi thường xuyên | Yêu cầu có thể thay đổi | Agile không đồng nghĩa với bỏ tài liệu hoặc không lập kế hoạch |
| Scrum | Tổ chức công việc bằng một khung có vai trò, sự kiện và các vòng Sprint | Nhóm có thể duy trì nhịp làm việc và trách nhiệm phù hợp | Không nên gọi mọi cuộc họp ngắn là Scrum |
| Kanban | Trực quan hóa luồng việc và giới hạn việc đang làm | Công việc đến liên tục, nhóm nhỏ | Cần giới hạn việc dở dang để tránh ùn tắc |

Với đồ án nhỏ, có thể chia thành các vòng 1 tuần: chọn chức năng → làm rõ yêu cầu → thiết kế vừa đủ → code → kiểm thử → demo → nhận phản hồi.

## 2. Xác định bài toán và nghiên cứu thực tế

### 2.1. Mục đích

Chứng minh vấn đề có thật và hiểu đúng nguyên nhân trước khi chọn công nghệ.

### 2.2. Công việc cụ thể

1. Xác định người chơi mục tiêu: hai người muốn chơi với nhau qua trình duyệt; giảng viên là bên đánh giá yêu cầu môn học.
2. Phỏng vấn về cách mời bạn, thời gian chờ mỗi lượt, cách nhận biết đến lượt và mong đợi khi mất mạng.
3. Cho hai người thử một bản phác thảo sân bóng ba hướng; quan sát họ có hiểu vai người sút/thủ môn không.
4. Tìm hiểu cách các sản phẩm tham khảo tổ chức phòng và thông báo kết quả; lưu nguồn và ngày quan sát nếu dùng làm bằng chứng.
5. Tách nhu cầu người chơi khỏi mục tiêu học tập: rèn lập trình mạng, quản lý kết nối và xử lý đồng thời là mục tiêu của đồ án, chưa phải bằng chứng về nhu cầu thị trường.
6. Xác nhận phạm vi với giảng viên theo nội dung đề tài được cung cấp.

### 2.3. Phân biệt vấn đề, nguyên nhân và giải pháp

Những nội dung sau là **giả thuyết cần khảo sát hoặc tình huống kỹ thuật cần thử nghiệm**, chưa phải kết quả nghiên cứu đã thực hiện.

| Thành phần | Ví dụ áp dụng đề tài |
| --- | --- |
| Nhu cầu giả định | Hai người muốn mời nhau chơi một trận ngắn bằng trình duyệt |
| Vấn đề cần xác minh | Người chơi khó nhận biết đã chọn thành công hoặc đang chờ đối thủ |
| Nguyên nhân kỹ thuật có thể có | Giao diện thiếu xác nhận từ server, trạng thái hai máy không đồng nhất |
| Tác động cần quan sát | Bấm gửi nhiều lần, hiểu nhầm kết quả hoặc bỏ trận |
| Giải pháp dự kiến | Phòng bằng mã, server quản lý luật và phát trạng thái qua WebSocket |

Không tự khẳng định “game hiện có đều đồng bộ chậm” hoặc “người dùng có nhu cầu cao” nếu chưa có căn cứ. Đề tài có thể được trình bày trung thực là sản phẩm học tập nhằm triển khai và kiểm chứng cơ chế chơi hai người theo thời gian thực.

### 2.4. Cách ghi bằng chứng

| Mã | Phát hiện | Nguồn | Thời điểm | Giới hạn |
| --- | --- | --- | --- | --- |
| EV-01 | Điền nhận xét thực tế về tạo phòng và nhận biết lượt | Biên bản thử prototype của hai người chơi | Điền ngày thử | Chưa suy rộng thành nhu cầu thị trường |
| EV-02 | Điền kết quả quan sát khi gửi lựa chọn đồng thời | Log thử nghiệm kỹ thuật và ảnh hai trình duyệt | Điền ngày thử | Chỉ áp dụng môi trường và tải đã thử |

Nếu dùng báo cáo hoặc bài nghiên cứu, lưu tên, tác giả/đơn vị, thời điểm công bố, liên kết và phần thông tin hỗ trợ nhận định. Kiểm tra tính phù hợp với bối cảnh hiện tại; không suy rộng kết quả của đơn vị khác thành kết luận về đơn vị mình.

**Đầu ra:** mô tả hiện trạng, danh sách vấn đề, bằng chứng và nhóm người dùng.

**Tiêu chí hoàn thành:** mỗi vấn đề ưu tiên có căn cứ; nhóm có thể giải thích ai bị ảnh hưởng và vì sao cần giải quyết.

## 3. Xác định mục tiêu, phạm vi và tính khả thi

### 3.1. Đặt mục tiêu gắn với vấn đề

Mục tiêu mô tả kết quả cần đạt, còn chức năng mô tả điều phần mềm thực hiện.

| Mục tiêu của đề tài | Chức năng hỗ trợ | Cách đánh giá |
| --- | --- | --- |
| Hai người vào cùng một trận qua trình duyệt | Tạo phòng, nhập mã, giới hạn 2 người | Hai tài khoản tham gia; người thứ ba bị từ chối |
| Lựa chọn được giữ kín cho đến khi đủ điều kiện công bố | Server nhận và khóa hướng sút/bắt riêng tư | Kiểm tra payload của đối thủ trước công bố không có hướng chọn |
| Hai máy thống nhất lượt và kết quả | Server xử lý luật, phát trạng thái WebSocket | So sánh matchId, turnId, stateVersion và tỷ số trên hai máy |
| Trận không bị treo vô hạn vì thiếu lựa chọn | Deadline, timeout và xử lý mất kết nối | Mô phỏng hết giờ, ngắt mạng, kết nối lại |
| Người chơi theo dõi được kết quả của mình | Lịch sử, thống kê thắng/thua, chơi lại | Đối chiếu lịch sử với bản ghi trận và kiểm tra tạo trận mới |

Có thể đặt chỉ tiêu độ trễ cho sự kiện, nhưng phải ghi môi trường, số phòng đồng thời, cách đo và giới hạn trước khi thử. Không ghi số liệu chưa đo thành kết quả đã đạt.

### 3.2. Chốt phạm vi và MVP

MVP là bản nhỏ nhất kiểm chứng được luồng cốt lõi. Với đề tài này, có thể làm MVP kỹ thuật trước, sau đó hoàn thiện toàn bộ nội dung đã đăng ký.

| Mức | Phạm vi |
| --- | --- |
| MVP kỹ thuật ban đầu | Hai người vào phòng, chọn ba hướng, server giữ bí mật, tính kết quả, đổi vai và đồng bộ tỷ số |
| Bản hoàn thành đề tài | Đăng ký/đăng nhập, thông tin người chơi, phòng 2 người, chat nhanh, sân bóng và hoạt ảnh; mỗi bên 5 lượt sút, hòa thì sút từng cặp; giới hạn thời gian, mất kết nối; lịch sử, thống kê và yêu cầu chơi lại |
| Ngoài phạm vi dự kiến | Đồ họa 3D, vật lý bóng thực tế, chơi với AI, ghép hạng, khán giả, nhiều hơn 2 người trong phòng, chat tự do và hệ thống giải đấu |

Không bỏ chat nhanh, lịch sử hoặc xử lý mất kết nối khỏi bản cuối chỉ vì đã chạy được MVP. Các nội dung này đã nằm trong mô tả đề tài.

### 3.3. Đánh giá tính khả thi

| Khía cạnh | Cần kiểm tra | Hành động nếu chưa chắc chắn |
| --- | --- | --- |
| Kỹ thuật | Nhóm có làm được chức năng khó nhất không? | Tạo thử nghiệm kỹ thuật nhỏ, giới hạn thời gian |
| Dữ liệu | Có nguồn dữ liệu, quyền sử dụng và dữ liệu mẫu không? | Xin dữ liệu phù hợp hoặc tạo dữ liệu giả |
| Thời gian | Khối lượng có vừa hạn nộp không? | Giảm phạm vi, ưu tiên luồng cốt lõi |
| Chi phí | Có phí máy chủ, tên miền, API hoặc dịch vụ ngoài không? | Lập dự toán theo phương án thực tế |
| Vận hành | Ai triển khai, theo dõi, sao lưu và sửa lỗi? | Phân công người phụ trách |
| Ràng buộc | Có yêu cầu về quyền riêng tư, hợp đồng, giấy phép? | Xác định nghĩa vụ áp dụng trước khi dùng dữ liệu/dịch vụ |

**Đầu ra:** bản mô tả dự án, MVP, danh sách ngoài phạm vi, giả định và rủi ro ban đầu.

**Tiêu chí hoàn thành:** nhóm thống nhất sản phẩm cần giao; các rủi ro lớn có cách xử lý hoặc phương án thay thế.

## 4. Thu thập và đặc tả yêu cầu

### 4.1. Thu thập yêu cầu

Kết hợp phỏng vấn, quan sát, phân tích biểu mẫu, khảo sát và trao đổi trên bản phác thảo giao diện. Sau mỗi buổi, gửi lại bản tóm tắt để xác nhận; ghi rõ điểm chưa thống nhất.

### 4.2. Các loại yêu cầu

| Loại | Ý nghĩa | Ví dụ |
| --- | --- | --- |
| Chức năng | Hệ thống phải làm gì | Tạo phòng, gửi lựa chọn, xem lịch sử |
| Phi chức năng | Chất lượng và điều kiện hoạt động | Không lộ lựa chọn; hai client hội tụ về trạng thái server |
| Quy tắc nghiệp vụ | Luật điều khiển trò chơi | Trùng hướng thì cản phá, khác hướng thì ghi bàn |
| Ràng buộc | Điều kiện của đề tài | Java Spring Boot, WebSocket, HTML/CSS/JavaScript, MySQL |

### 4.3. Viết yêu cầu có thể kiểm thử

#### Danh sách chức năng

| Mã | Yêu cầu | Tiêu chí chấp nhận chính |
| --- | --- | --- |
| FR-01 | Đăng ký, đăng nhập, đăng xuất | Từ chối định danh trùng; xác thực đúng; sau đăng xuất không tiếp tục điều khiển bằng phiên đã bị thu hồi |
| FR-02 | Quản lý thông tin người chơi | Xem/sửa tên hiển thị của mình; không sửa tài khoản người khác |
| FR-03 | Tạo phòng | Người đã đăng nhập nhận mã phòng còn hiệu lực, là thành viên đầu tiên |
| FR-04 | Tham gia phòng bằng mã | Nhận đúng thành viên thứ hai; từ chối mã sai, phòng đã chơi hoặc đã đủ người |
| FR-05 | Chat nhanh trong phòng | Chỉ thành viên gửi/nhận các câu có sẵn; server kiểm tra mã câu và giới hạn tần suất |
| FR-06 | Hiển thị sân và vai trò | Có ba hướng trái/giữa/phải, tỷ số, lượt sút của mỗi bên và thời gian còn lại |
| FR-07 | Gửi và xác nhận lựa chọn | Chỉ thành viên trận hiện tại được gửi; hướng hợp lệ; khóa sau lần chấp nhận đầu; xác nhận riêng cho người gửi |
| FR-08 | Xử lý và đồng bộ lượt | Server công bố khi hai bên chọn hoặc áp dụng ngoại lệ hết giờ; trùng hướng cản phá, khác hướng ghi bàn; phát hoạt ảnh và tỷ số thống nhất |
| FR-09 | Kết thúc trận | Mỗi bên đủ 5 lượt sút; nếu hòa, đánh giá sau mỗi cặp bổ sung, chỉ kết thúc khi số lượt sút hai bên bằng nhau và tỷ số khác nhau |
| FR-10 | Thời gian và mất kết nối | Deadline do server quyết định; có quy tắc cho không chọn, kết nối lại và bỏ cuộc |
| FR-11 | Lịch sử và thống kê | Lưu kết quả đúng một lần; người chơi xem trận của mình, số thắng/thua từ trận hợp lệ |
| FR-12 | Yêu cầu chơi lại | Chỉ tạo trận mới khi cả hai đồng ý và còn trong phòng; giữ nguyên lịch sử trận trước |

#### Yêu cầu phi chức năng

- NFR-01 — Bí mật: trước khi công bố, mọi thông điệp công khai và snapshot không chứa hướng đã chọn của bất kỳ bên nào. Không đưa hướng vào log mà đối thủ có thể truy cập.
- NFR-02 — Nhất quán: cùng một lượt chỉ được chấm một lần, kể cả khi nhận hai lựa chọn, timeout hoặc sự kiện mất kết nối gần như đồng thời.
- NFR-03 — Đồng bộ: sự kiện có phiên bản trạng thái; client bỏ qua phiên bản cũ, lấy snapshot khi nghi thiếu sự kiện. Chỉ tiêu độ trễ phải gắn với bài đo cụ thể.
- NFR-04 — Xác thực: danh tính lấy từ phiên đã xác minh; client không được tự khai người chơi, vai trò hoặc tỷ số để server tin theo.
- NFR-05 — Khôi phục: kết nối lại nhận trạng thái hiện tại đã lọc thông tin mật. Chính sách server khởi động lại được công bố ở thiết kế vận hành.
- NFR-06 — Tài khoản: băm mật khẩu bằng cơ chế chuyên dụng; không trả mật khẩu hoặc giá trị băm về client.

#### Quy tắc và tham số cần chốt

Các luật ba hướng, đổi vai, 5 lượt mỗi bên và sút từng cặp khi hòa lấy từ đề tài. **15 giây chọn, 30 giây kết nối lại và các quy tắc ngoại lệ dưới đây là đề xuất triển khai**, cần nhóm/giảng viên xác nhận trước khi chốt SRS.

| Mã | Quy tắc áp dụng cho bản thiết kế minh họa |
| --- | --- |
| BR-01 | Một phòng có tối đa 2 người chơi khác nhau; một người chỉ có một phòng/trận đang hoạt động |
| BR-02 | Một lượt là một lần sút: một người sút và một thủ môn cùng chọn LEFT/CENTER/RIGHT; một cặp lượt gồm mỗi người sút một lần |
| BR-03 | Hai bên đều chọn: trùng hướng = SAVE, khác hướng = GOAL. Chỉ server chấm; hoạt ảnh minh họa kết quả đã chấm |
| BR-04 | Mỗi bên sút đủ 5 lần, tổng 10 lượt ban đầu; phiên bản đề tài này không kết thúc sớm dù đã cách biệt |
| BR-05 | Nếu hòa sau 10 lượt, tiếp tục từng cặp. Không xác định thắng sau lượt đầu của cặp vì bên kia chưa sút |
| BR-06 | Lựa chọn được khóa sau lần đầu server chấp nhận, không đổi. Người chưa chọn chỉ thấy đối thủ đã sẵn sàng, không thấy hướng |
| BR-07 | Đề xuất mỗi lượt có 15 giây theo đồng hồ server; request chỉ hợp lệ khi server tiếp nhận trước deadline. Đến deadline mới xử lý lựa chọn thiếu |
| BR-08 | Người sút không chọn: MISS, không ghi bàn. Người sút đã chọn nhưng thủ môn không chọn: GOAL. Cả hai không chọn: MISS; lượt sút vẫn được tính |
| BR-09 | Mất kết nối tạm thời không tự đặt lại hoặc kéo dài deadline lượt. Cho 30 giây kết nối lại; trong thời gian đó vẫn xử lý lượt đang mở theo deadline, nhưng chưa mở lượt tiếp theo khi thiếu người kết nối |
| BR-10 | Hết hạn kết nối lại, một bên còn online: bên offline thua bỏ cuộc. Cả hai offline hết thời hạn: hủy trận, không cộng thắng/thua. Nếu lượt hiện tại đã kết thúc cả trận theo luật trước đó thì giữ kết quả, bỏ qua timeout kết nối đến sau |
| BR-11 | Chơi lại tạo matchId mới trong phòng, reset tỷ số và lượt, không xóa trận cũ. Cần cả hai đồng ý; người bắt đầu được luân phiên giữa các trận |

Nếu cần thay chính sách timeout, cập nhật đồng thời SRS, mã xử lý và test case. Không diễn giải hai lựa chọn thiếu thành “trùng hướng”.

### 4.4. User story và tiêu chí chấp nhận

**User story:** Là người chơi, tôi muốn chọn hướng kín trong lượt hiện tại để đối thủ không biết quyết định của tôi trước khi họ xác nhận.

**Tiêu chí chấp nhận của FR-07/FR-08:**

1. Chỉ hai thành viên trận đang ở trạng thái nhận lựa chọn mới được gửi.
2. Server xác định vai người gửi; chỉ nhận một trong ba hướng hợp lệ.
3. Người gửi nhận xác nhận riêng; đối thủ chỉ nhận cờ đã chọn.
4. Khi mới một bên chọn, hướng không xuất hiện trong sự kiện công khai hoặc snapshot của đối thủ.
5. Khi cả hai đã chọn, server chấm đúng một lần, công bố hai hướng, kết quả và tỷ số.
6. Gửi lại cùng actionId không tạo tác dụng lần hai; gửi hướng khác sau khi đã khóa bị từ chối.
7. Lựa chọn của lượt cũ hoặc đến sau deadline bị từ chối, client lấy lại trạng thái nếu cần.
8. Hết giờ thì áp dụng BR-08; không treo trận chờ vô hạn.

### 4.5. Ưu tiên yêu cầu

- **Must have:** thiếu thì phiên bản không đạt mục đích chính.
- **Should have:** có giá trị cao nhưng có thể lùi nếu cần.
- **Could have:** làm khi còn nguồn lực.
- **Won’t have this time:** xác nhận chưa làm ở phiên bản này.

### 4.6. Nội dung tài liệu đặc tả yêu cầu — SRS

1. Mục đích, phạm vi, thuật ngữ.
2. Nhóm người dùng và quyền hạn.
3. Hiện trạng và bối cảnh hệ thống.
4. Danh sách yêu cầu có mã định danh.
5. Use case hoặc user story, tiêu chí chấp nhận.
6. Quy tắc nghiệp vụ và yêu cầu dữ liệu.
7. Yêu cầu phi chức năng và tích hợp ngoài.
8. Giả định, phụ thuộc, nội dung ngoài phạm vi.
9. Điểm chưa chốt và lịch sử thay đổi.

**Đầu ra:** SRS ở mức phù hợp với dự án, backlog được ưu tiên.

**Tiêu chí hoàn thành:** yêu cầu rõ và có thể kiểm chứng; người dùng và nhóm phát triển hiểu thống nhất các luồng quan trọng.

## 5. Phân tích hệ thống

### 5.1. Mục đích và sự khác nhau với thiết kế

Phân tích làm rõ nghiệp vụ cần giải quyết và các quy tắc của nó. Thiết kế lựa chọn cấu trúc kỹ thuật để thực hiện nghiệp vụ đó.

Ví dụ: “hai người luân phiên vai người sút và thủ môn” là kết quả phân tích. “GameService tuần tự hóa lệnh theo matchId và phát sự kiện có stateVersion” là một quyết định thiết kế.

### 5.2. Xác định tác nhân và quyền

| Tác nhân/vai trò | Trách nhiệm |
| --- | --- |
| Khách | Đăng ký, đăng nhập |
| Người chơi đã đăng nhập | Sửa thông tin mình, tạo/tham gia phòng, xem lịch sử mình |
| Chủ phòng | Thành viên tạo phòng; không có quyền sửa luật, tỷ số hoặc xem hướng đối thủ |
| Người sút | Chọn hướng sút trong lượt được chỉ định |
| Thủ môn | Chọn hướng bắt trong cùng lượt |

Người sút và thủ môn là vai trò thay đổi từng lượt, không cần tạo hai loại tài khoản. Server và database là thành phần bên trong hệ thống, không coi là người dùng nghiệp vụ.

### 5.3. Mô tả use case

Ví dụ **UC-07 — Thực hiện một lượt sút**:

| Thành phần | Nội dung |
| --- | --- |
| Tác nhân chính | Hai người chơi trong trận |
| Tiền điều kiện | Trận hoạt động, lượt đang mở, server đã xác định người sút/thủ môn và deadline |
| Kích hoạt | Một người chọn trái, giữa hoặc phải và xác nhận |
| Luồng chính | Server xác thực → nhận và khóa lựa chọn → xác nhận riêng → chờ bên còn lại → chấm → công bố cho cả hai → đổi vai hoặc kết thúc |
| Luồng thay thế | Một người chưa chọn khi hết giờ → áp dụng luật timeout |
| Ngoại lệ | Sai phòng/trận/lượt, hướng sai, đã chọn, mất phiên hoặc kết nối bị ngắt |
| Hậu điều kiện | Lượt được chấm tối đa một lần; tỷ số, số lượt sút và phiên bản trạng thái được cập nhật nhất quán |

Tiền điều kiện không thay thế việc kiểm tra trên server. Người dùng có thể gửi gói tin trực tiếp mà không đi qua nút trên giao diện.

### 5.4. Phân tích quy trình và quy tắc nghiệp vụ

Với từng luồng, ghi ai thực hiện, dữ liệu vào/ra, trạng thái trước/sau, lỗi và xử lý đồng thời.

**Luồng một trận:**

1. A đăng nhập và tạo phòng; B đăng nhập, nhập mã phòng.
2. Khi đủ hai người, mỗi người xác nhận sẵn sàng; server tạo trận và chọn người sút trước theo quy ước.
3. Server mở lượt, gửi vai trò và deadline cho cả hai.
4. Hai bên chọn độc lập; server giữ kín và khóa lựa chọn.
5. Server xử lý khi đủ hai hướng hoặc hết giờ; gửi kết quả để hai máy chạy hoạt ảnh.
6. Đổi vai. Lặp đến đủ 5 lượt sút mỗi bên.
7. Nếu hòa, tiếp tục từng cặp lượt và xét thắng sau khi cả hai đã sút trong cặp.
8. Lưu kết quả cuối; hiển thị lịch sử, thống kê và yêu cầu chơi lại.

Bước sẵn sàng là đề xuất giao diện để tránh bắt đầu lúc người chơi chưa chuẩn bị. Chat nhanh hoạt động độc lập với chấm điểm và không làm thay đổi luật.

**Các tình huống cần phân tích:** hai người giành suất cuối phòng; hai hướng đến cùng lúc; timeout chạy đồng thời với nhận lựa chọn; gửi lặp; gói tin lượt cũ; mất mạng trước/sau khi chọn; rời phòng và chơi lại.

### 5.5. Xây dựng mô hình miền

| Khái niệm | Ý nghĩa | Quan hệ chính |
| --- | --- | --- |
| User | Tài khoản và thông tin người chơi | Tham gia nhiều trận theo thời gian |
| Room | Không gian ghép hai người bằng mã | Có tối đa 2 thành viên hoạt động, có thể chứa nhiều trận nối tiếp |
| Match | Một trận độc lập | Thuộc phòng, có 2 người và nhiều lượt |
| Turn | Một lần sút của một người | Có người sút, thủ môn, deadline, lựa chọn và kết quả |
| Choice | Hướng riêng tư đã được chấp nhận | Gắn với người chơi và lượt; chỉ công bố khi lượt đã được giải quyết |
| MatchResult | Kết quả cuối | Người thắng/thua hoặc trạng thái hủy và lý do kết thúc |
| QuickChat | Một câu chat có sẵn | Chỉ phát cho các thành viên phòng |

Không cần mọi khái niệm đều thành bảng. Choice có thể là dữ liệu trong trạng thái Turn; QuickChat có thể chỉ là danh mục câu cố định và sự kiện tạm thời.

Phân biệt `turnNo` (một lần sút) với `pairNo` (mỗi bên sút một lần). Nhầm hai khái niệm dễ khiến hệ thống kết thúc sai sau lượt đầu ở giai đoạn hòa.

### 5.6. Chọn sơ đồ phù hợp

| Sơ đồ | Khi cần dùng |
| --- | --- |
| Use case | Tóm tắt tác nhân và chức năng |
| Activity/BPMN | Giải thích quy trình, nhánh điều kiện, phối hợp vai trò |
| Domain/class diagram | Mô tả khái niệm, thuộc tính và quan hệ |
| State diagram | Đối tượng có nhiều trạng thái và quy tắc chuyển trạng thái |
| Sequence diagram | Làm rõ thứ tự tương tác trong luồng quan trọng; thường được bổ sung ở thiết kế |

Chỉ vẽ sơ đồ giúp giải thích hoặc kiểm chứng hệ thống; giữ tên và quy tắc nhất quán giữa các sơ đồ.

**Đầu ra:** đặc tả use case, mô hình miền, quy trình và quy tắc nghiệp vụ.

**Tiêu chí hoàn thành:** các luồng chính, lỗi và trường hợp biên quan trọng được giải thích rõ; không còn mâu thuẫn lớn giữa yêu cầu và mô hình.

## 6. Thiết kế hệ thống

### 6.1. Thiết kế kiến trúc

Đề xuất một ứng dụng Spring Boot chạy một tiến trình server cho phạm vi đồ án. Server là nơi quyết định trạng thái và luật; client chỉ gửi ý định và hiển thị kết quả.

| Thành phần | Trách nhiệm |
| --- | --- |
| HTML/CSS/JavaScript | Giao diện đăng nhập, phòng, sân ba hướng, chat nhanh, hoạt ảnh và lịch sử |
| REST Controller | Tài khoản, hồ sơ, lịch sử; tạo/tham gia phòng theo hợp đồng đã chốt |
| WebSocket Handler | Nhận lệnh realtime, xác thực phiên, trả xác nhận và phát sự kiện |
| RoomService | Quản lý mã phòng, thành viên, sẵn sàng, quyền truy cập phòng |
| GameService/GameEngine | Vai trò, lựa chọn kín, deadline, chấm lượt, thắng/thua và chơi lại |
| ConnectionRegistry | Ánh xạ tài khoản với kết nối đang được phép điều khiển; phát hiện ngắt kết nối |
| Scheduler | Đưa lệnh hết giờ vào cùng luồng xử lý trạng thái trận |
| Repository + MySQL | Tài khoản, lịch sử và kết quả bền vững |

Không dùng đồng hồ hoặc tỷ số do client gửi làm căn cứ. Hoạt ảnh không tính bàn và không quyết định khi nào server chuyển lượt.

### 6.2. Lựa chọn công nghệ

Theo đề tài: **Java Spring Boot** xử lý server và luật chơi; **WebSocket** truyền sự kiện thời gian thực; **HTML/CSS/JavaScript** làm frontend; **MySQL** lưu tài khoản và kết quả trận đấu.

Bản thiết kế minh họa dùng WebSocket thuần với thông điệp JSON để dễ trình bày giao thức; REST dùng cho thao tác không cần phát đồng thời. STOMP là phương án thay thế cần được chốt riêng, không trộn định dạng của hai phương án.

Ghi phiên bản Java, Spring Boot, MySQL và công cụ build thực sự được dùng. Chọn và kiểm tra thư viện dựa trên tài liệu chính thức tương ứng khi triển khai; tài liệu này không ấn định phiên bản mới nhất.

### 6.3. Thiết kế cơ sở dữ liệu và trạng thái chạy

#### Dữ liệu lưu bền vững

| Bảng | Trường minh họa | Ràng buộc/lưu ý |
| --- | --- | --- |
| users | id, username, email, password_hash, display_name, created_at | username/email duy nhất theo chính sách đăng ký |
| matches | id, room_id, player_a_id, player_b_id, first_shooter_id, score_a, score_b, status, winner_id, end_reason, started_at, ended_at | Hai người khác nhau; winner phải là người tham gia hoặc null khi hủy |
| match_turns, nếu lưu chi tiết | id, match_id, turn_no, shooter_id, keeper_id, shooter_direction, keeper_direction, outcome, resolved_at | Duy nhất (match_id, turn_no); hướng có thể null khi không chọn |

Lịch sử phòng lâu dài là tùy chọn. Mã phòng chỉ dùng để tham gia phiên hiện tại, không dùng làm khóa định danh lịch sử vì mã có thể tái sử dụng. `room_id` là mã định danh nội bộ khác mã mời hiển thị.

Thống kê thắng/thua có thể tính từ các trận đã kết thúc hợp lệ để tránh lệch bộ đếm. Nếu lưu bộ đếm riêng, cập nhật cùng transaction và có cơ chế chống cộng hai lần theo matchId. Trận thua bỏ cuộc được tính; trận hủy không được tính.

#### Trạng thái realtime trong bộ nhớ

Với một server cho đồ án, có thể lưu RoomState/MatchState trong bộ nhớ: thành viên, trạng thái kết nối, currentTurn, hai lựa chọn kín, deadline, tỷ số, stateVersion và các actionId đã xử lý trong phạm vi thích hợp.

- Dữ liệu trận kết thúc phải được ghi MySQL; không đánh dấu đã lưu nếu transaction thất bại.
- Chốt trận trong cùng một transaction: trạng thái kết thúc, tỷ số, người thắng và chi tiết cần lưu. Retry theo matchId, không tạo bản ghi kết quả mới cho cùng trận.
- Nếu cần sự kiện “kết thúc và đã lưu”, chỉ phát sau khi commit thành công. Nếu lỗi lưu, báo trạng thái đang lưu và retry; không chấm lại lượt.
- Phương án đơn giản khi server khởi động lại: hủy các trận còn dang dở, không cộng thắng/thua, yêu cầu tạo trận mới. Lưu dấu trận IN_PROGRESS từ khi bắt đầu để nhận diện các trận bị gián đoạn và đánh dấu ABORTED khi khởi động lại.
- Phương án này chỉ hỗ trợ **một tiến trình server**; muốn chạy nhiều tiến trình phải thiết kế lại điều phối và trạng thái dùng chung.

#### Trạng thái và chuyển trạng thái

| Đối tượng | Trạng thái | Điều kiện chuyển chính |
| --- | --- | --- |
| Room | WAITING | Chờ đủ hai người và sẵn sàng |
| Room | PLAYING | Đã tạo trận; không nhận người thứ ba |
| Room | POST_MATCH | Trận đã kết thúc, chờ yêu cầu chơi lại |
| Room | CLOSED | Không còn thành viên hoặc hết thời gian chờ theo chính sách |
| Turn | COLLECTING | Nhận tối đa một lựa chọn/người trước deadline |
| Turn | RESOLVED | Đã chấm; không nhận lựa chọn mới; kết quả được công bố |
| Match | IN_PROGRESS | Đang nhận/chấm lượt hoặc chờ kết nối lại |
| Match | FINISHED | Có thắng/thua theo luật hoặc bỏ cuộc |
| Match | ABORTED | Trận bị hủy; không có người thắng |

GameEngine giữ trạng thái chờ kết nối và mốc công bố hoạt ảnh như thuộc tính riêng, không tự chuyển trận đã kết thúc về IN_PROGRESS. Mỗi chuyển trạng thái cần được kiểm tra hợp lệ.

### 6.4. Thiết kế API và hợp đồng dữ liệu

#### REST API minh họa

| Phương thức | Đường dẫn | Mục đích | Quyền |
| --- | --- | --- | --- |
| POST | /api/auth/register | Đăng ký | Khách |
| POST | /api/auth/login | Đăng nhập | Khách |
| POST | /api/auth/logout | Thu hồi phiên và kết nối điều khiển | Người đăng nhập |
| GET / PATCH | /api/me | Xem/sửa tên hiển thị | Chính tài khoản đó |
| POST | /api/rooms | Tạo phòng, nhận mã | Người đăng nhập chưa ở phòng khác |
| POST | /api/rooms/join | Tham gia bằng mã | Người đăng nhập |
| GET | /api/me/matches | Lịch sử có phân trang | Chính tài khoản đó |
| GET | /api/me/statistics | Thống kê thắng/thua | Chính tài khoản đó |

Tạo và tham gia phòng vẫn phải được xử lý nguyên tử tại RoomService. Hai request vào suất cuối cùng không được cùng thành công.

#### Giao thức WebSocket minh họa

Endpoint dự kiến: `/ws/game`. Các tên bên dưới là quy ước thiết kế của dự án, không phải sự kiện WebSocket có sẵn.

| Hướng | Loại thông điệp | Nội dung và phạm vi |
| --- | --- | --- |
| Client → server | READY | Xác nhận sẵn sàng trong phòng |
| Client → server | SUBMIT_CHOICE | matchId, turnId, actionId, direction; không gửi tỷ số hoặc tự khai userId |
| Client → server | QUICK_CHAT | Mã câu có sẵn, được kiểm tra trên server |
| Client → server | SYNC_REQUEST | Yêu cầu snapshot sau kết nối lại hoặc nghi mất sự kiện |
| Client → server | REMATCH_REQUEST / REMATCH_ACCEPT | Đề nghị/chấp nhận chơi lại sau trận |
| Client → server | LEAVE_ROOM | Rời phòng, áp dụng luật bỏ cuộc nếu trận đang chạy |
| Server → riêng người gửi | CHOICE_ACK | actionId và xác nhận lựa chọn đã được nhận; không phát hướng cho đối thủ |
| Server → cả phòng | PLAYER_READY / CHOICE_STATUS | Ai đã sẵn sàng/đã chọn; không chứa hướng |
| Server → cả phòng | TURN_STARTED | matchId, turnId, vai trò, deadline, serverTime, stateVersion |
| Server → cả phòng | TURN_RESULT | Hai hướng hoặc null khi thiếu chọn, outcome, tỷ số, số lượt, thời điểm kết thúc hoạt ảnh |
| Server → cả phòng | QUICK_CHAT | Câu chat đã kiểm tra và người gửi |
| Server → cả phòng | PLAYER_CONNECTION_CHANGED | Trạng thái kết nối và hạn kết nối lại |
| Server → riêng người yêu cầu | STATE_SNAPSHOT | Trạng thái hiện tại đã lọc lựa chọn bí mật; kèm stateVersion |
| Server → cả phòng | MATCH_FINISHED / MATCH_ABORTED | Kết quả, lý do kết thúc, trạng thái lưu kết quả |
| Server → riêng người gửi | ERROR | Mã lỗi, actionId nếu có, mô tả an toàn |

Ví dụ lệnh lựa chọn:

```json
{
  "type": "SUBMIT_CHOICE",
  "matchId": "match-001",
  "turnId": "turn-003",
  "actionId": "action-a-003",
  "direction": "LEFT"
}
```

Ví dụ thông điệp công khai **trước khi công bố**, tuyệt đối không có `direction`:

```json
{
  "type": "CHOICE_STATUS",
  "matchId": "match-001",
  "turnId": "turn-003",
  "playerId": "player-a",
  "hasChosen": true,
  "stateVersion": 12
}
```

Ví dụ kết quả **sau khi công bố**:

```json
{
  "type": "TURN_RESULT",
  "matchId": "match-001",
  "turnId": "turn-003",
  "shooterId": "player-a",
  "keeperId": "player-b",
  "shooterDirection": "LEFT",
  "keeperDirection": "RIGHT",
  "outcome": "GOAL",
  "scoreA": 2,
  "scoreB": 0,
  "shotsTakenA": 2,
  "shotsTakenB": 1,
  "stateVersion": 13
}
```

Ở lượt 3, A sút lần thứ hai và B mới sút một lần. Dữ liệu này chưa đủ để xét thắng ở giai đoạn sút từng cặp.

Quy ước lỗi dự kiến: `ROOM_FULL`, `NOT_ROOM_MEMBER`, `INVALID_DIRECTION`, `STALE_TURN`, `CHOICE_LOCKED`, `TURN_EXPIRED`, `MATCH_ENDED`. Frontend và backend phải thống nhất hành vi cho từng lỗi.

#### Đồng thời, gửi lặp và bảo mật lựa chọn

1. Đưa mọi lệnh sửa trạng thái và lệnh timeout vào cùng cơ chế tuần tự hóa theo phòng/trận, ví dụ hàng đợi hoặc khóa phù hợp; transaction database không tự bảo vệ trạng thái trong RAM.
2. Ghi nhận thời điểm tiếp nhận ở server; chỉ chấp nhận lựa chọn trước deadline, xử lý timeout sau các lệnh hợp lệ đã được tiếp nhận trước đó trong hàng đợi tương ứng.
3. Kiểm tra matchId/turnId, tư cách thành viên, vai và trạng thái trước mỗi lệnh. Bỏ qua timer đã cũ sau khi trận/lượt thay đổi.
4. Chỉ chuyển COLLECTING → RESOLVED một lần; dùng actionId để nhận diện request gửi lại. Kết quả trả cho retry phải phản ánh việc đã xử lý, không chấm lại.
5. Snapshot được tạo bằng DTO dành riêng; không serialize trực tiếp MatchState vì có lựa chọn kín.
6. Phát sự kiện theo thứ tự trạng thái đã chốt. Client dùng stateVersion và matchId/turnId để bỏ sự kiện cũ; nếu thiếu sự kiện thì lấy snapshot.
7. Một tài khoản chỉ có một kết nối được phép điều khiển trận; khi thay kết nối phải vô hiệu hóa kết nối cũ và không để sự kiện đóng của kết nối cũ đánh dấu nhầm kết nối mới là offline.

### 6.5. Thiết kế giao diện và trải nghiệm

1. Liệt kê màn hình và luồng đi lại.
2. Vẽ wireframe để kiểm tra bố cục và thao tác trước.
3. Tạo prototype cho luồng khó, nhờ người dùng thử.
4. Quy định thành phần dùng chung: form, nút, bảng, thông báo.
5. Mô tả trạng thái: tải dữ liệu, thành công, lỗi, trống, mất quyền.
6. Kiểm tra bàn phím, nhãn trường, độ tương phản và kích thước màn hình cần hỗ trợ.

Không chỉ thiết kế màn hình có dữ liệu đẹp; cần thể hiện rõ trạng thái tải và thao tác thất bại.

**Các màn hình của đề tài:** đăng ký/đăng nhập; hồ sơ; sảnh tạo hoặc nhập mã phòng; phòng chờ hai người; sân ba hướng; kết quả và chơi lại; lịch sử và thống kê.

Trên sân cần hiện “Bạn đang sút” hoặc “Bạn đang bắt”, số lượt đã sút của từng bên, tỷ số, đếm ngược và trạng thái “Đã xác nhận, chờ đối thủ”. Chỉ khi TURN_RESULT đến mới chạy hoạt ảnh theo hai hướng đã công bố. Đề xuất server gửi mốc kết thúc hoạt ảnh khoảng 2 giây sau kết quả; client không gửi yêu cầu cộng bàn và không được kéo dài lượt vì hoạt ảnh chưa xong.

### 6.6. Thiết kế bảo vệ dữ liệu và độ tin cậy

- Xác định nơi lưu dữ liệu cá nhân, ai được đọc và sửa.
- Kiểm tra quyền tại backend, kể cả quyền trên từng bản ghi.
- Quyết định cơ chế phiên đăng nhập theo kiến trúc; không mặc định mọi hệ thống đều cần JWT.
- Thiết kế lưu mật khẩu bằng cơ chế băm chuyên dụng, quản lý bí mật ngoài mã nguồn.
- Xác định transaction cho các thao tác phải cùng thành công hoặc cùng thất bại.
- Kiểm tra phiên khi bắt tay WebSocket, kiểm tra Origin theo môi trường, quyền thành viên trên từng thông điệp; khi đăng xuất phải thu hồi quyền của socket hiện tại.
- Giới hạn kích thước gói tin, tần suất chat/lựa chọn và số kết nối; không coi mã phòng là bằng chứng đủ về quyền thành viên.
- Giữ hướng chọn ngoài sự kiện/snapshot công khai; xung đột, retry và timer cùng đi qua cơ chế tuần tự hóa.
- Quy định log cần ghi, dữ liệu không được ghi, sao lưu và phục hồi.

**Đầu ra:** tài liệu kiến trúc, ERD, API contract, bản thiết kế giao diện, quyết định kỹ thuật.

**Tiêu chí hoàn thành:** nhóm có thể triển khai luồng cốt lõi dựa trên thiết kế; frontend và backend đã thống nhất dữ liệu và lỗi trả về.

## 7. Lập kế hoạch và chuẩn bị môi trường

### 7.1. Chia nhỏ công việc

Từ mỗi chức năng, tách thành việc có thể giao và kiểm tra được. Ví dụ “Thực hiện một lượt sút” gồm:

- Chốt yêu cầu và tiêu chí chấp nhận.
- Thiết kế ba nút hướng và hợp đồng sự kiện WebSocket.
- Xây dựng TurnState, deadline và cơ chế tuần tự hóa lệnh.
- Viết nhận/khóa lựa chọn, chấm kết quả và kiểm thử đồng thời.
- Làm giao diện chờ, hoạt ảnh, tỷ số, snapshot và trạng thái mất kết nối.
- Kiểm thử luồng hoàn chỉnh và demo.

Mỗi việc có người phụ trách, kết quả mong đợi, phụ thuộc và ước lượng. Chọn kích thước để người thực hiện có thể hoàn thành và báo cáo rõ, thường trong một hoặc vài ngày làm việc.

### 7.2. Sắp xếp thứ tự

Ưu tiên luồng tạo giá trị cốt lõi và các rủi ro khó. Làm sớm một lát cắt hoàn chỉnh từ giao diện đến cơ sở dữ liệu để phát hiện vấn đề tích hợp.

Không cần hoàn thiện toàn bộ backend rồi mới cho frontend bắt đầu. Hai phía có thể làm dựa trên API contract và dữ liệu giả, sau đó tích hợp thường xuyên.

### 7.3. Chuẩn bị môi trường

1. Tạo repository và README hướng dẫn chạy.
2. Thống nhất cách đặt tên, định dạng code và quy trình review.
3. Ghi phiên bản runtime, công cụ build và dependency.
4. Cung cấp file cấu hình mẫu như `.env.example`, chỉ chứa giá trị mẫu.
5. Tạo migration, dữ liệu giả và tài khoản thử theo vai trò.
6. Tách môi trường phát triển, kiểm thử và vận hành theo quy mô dự án.
7. Cấu hình kiểm tra tự động phù hợp khi có thay đổi mã nguồn.

### 7.4. Định nghĩa sẵn sàng và hoàn thành

**Một việc sẵn sàng để làm khi:** yêu cầu đủ rõ, có tiêu chí chấp nhận, biết phụ thuộc và có dữ liệu/API cần thiết.

**Một việc hoàn thành khi:** code chạy, đã được xem xét, các kiểm tra liên quan đạt, tích hợp thành công, tài liệu cần thiết được cập nhật và có thể demo.

**Đầu ra:** backlog, kế hoạch vòng làm việc, môi trường dựng lại được.

**Tiêu chí hoàn thành:** thành viên khác có thể clone dự án và chạy theo hướng dẫn mà không cần đoán cấu hình.

## 8. Lập trình và tích hợp

### 8.1. Vòng thực hiện một công việc

1. Đọc yêu cầu và kiểm tra tiêu chí chấp nhận.
2. Tạo nhánh hoặc không gian làm việc theo quy ước nhóm.
3. Thực hiện thay đổi nhỏ, tập trung vào mục tiêu.
4. Kiểm tra dữ liệu và quyền tại backend.
5. Viết kiểm thử tự động ở nơi có giá trị: nghiệp vụ, ràng buộc, quyền, lỗi dễ tái diễn.
6. Tự chạy và kiểm tra các luồng thành công/thất bại liên quan.
7. Mở yêu cầu review, ghi rõ thay đổi và cách kiểm tra.
8. Sửa góp ý, tích hợp và xác minh trên bản chung.

### 8.2. Nguyên tắc phân chia trách nhiệm

- REST Controller xử lý HTTP; WebSocket Handler phân tích thông điệp, xác thực và chuyển lệnh vào service, không tự chấm bàn.
- Service chứa quy tắc và phối hợp nghiệp vụ.
- Repository tập trung truy cập dữ liệu.
- DTO định nghĩa dữ liệu trao đổi; tránh trả toàn bộ entity có trường nhạy cảm.
- Frontend tổ chức phần hiển thị, gọi API và quản lý trạng thái ở mức phù hợp.
- Validation trên giao diện giúp người dùng nhập đúng; backend vẫn phải xác minh độc lập.

Tổ chức theo module nghiệp vụ như `auth`, `player`, `room`, `game`, `history` giúp nhóm tìm chức năng dễ hơn. Với dự án nhỏ, tránh tạo quá nhiều lớp chỉ để chuyển tiếp lời gọi.

### 8.3. Tích hợp thường xuyên

Mỗi khi có một phần chạy được, kiểm tra trên hai tài khoản và hai trình duyệt/thiết bị: REST, socket, mã sự kiện, deadline, snapshot, quyền phòng, hướng bí mật và tỷ số. Thử cả trường hợp một người gửi chậm hoặc kết nối lại; không chỉ chạy hai tab dùng chung một phiên đăng nhập.

Nếu dùng dữ liệu giả, ghi rõ phần nào chưa gọi API thật. Không đánh dấu hoàn thành chức năng chỉ vì giao diện đã hiển thị.

### 8.4. Khi sử dụng AI hỗ trợ code

Giao nhiệm vụ nhỏ có bối cảnh và tiêu chí chấp nhận. Đọc phần thay đổi, xác minh API/thư viện với phiên bản đang dùng và chạy kiểm tra liên quan. Không đưa mật khẩu, khóa truy cập hoặc dữ liệu cá nhân thật vào prompt. Người phát triển chịu trách nhiệm về đoạn code được đưa vào dự án.

**Đầu ra:** các chức năng đã tích hợp, mã nguồn được quản lý phiên bản, migration và kiểm thử liên quan.

**Tiêu chí hoàn thành:** bản dùng chung chạy được luồng hoàn chỉnh; không có bí mật bị đưa vào repository.

## 9. Kiểm thử và sửa lỗi

Kiểm thử bắt đầu từ lúc xem xét yêu cầu và thiết kế, tiếp tục trong lập trình và trước phát hành. Không đợi đến cuối dự án mới kiểm tra.

### 9.1. Các mức và loại kiểm thử

| Kiểm thử | Kiểm tra điều gì? | Ví dụ của đề tài |
| --- | --- | --- |
| Unit | Luật và chuyển trạng thái thuần | 9 tổ hợp ba hướng; đổi vai; 5 lượt; sút từng cặp; timeout |
| Integration | Thành phần phối hợp | Kết thúc trận và ghi MySQL đúng một lần |
| API | HTTP và quyền | Tài khoản, hồ sơ, lịch sử cá nhân, hai request tranh suất phòng |
| WebSocket | Giao thức, phiên và phạm vi phát | ACK riêng, không lộ hướng, người ngoài không điều khiển phòng |
| End-to-end | Toàn bộ trận trên hai client | Tạo phòng → chơi → kết thúc → lịch sử → chơi lại |
| Đồng thời | Race condition và gửi lặp | Lựa chọn thứ hai đến cùng lúc timer chạy; không cộng hai bàn |
| Mạng và khôi phục | Mất kết nối, snapshot, sự kiện cũ | Ngắt mạng rồi vào lại không đổi lựa chọn đã khóa |
| Hiệu năng | Độ trễ dưới tải xác định | Nhiều phòng độc lập; đo thời gian từ server chấp nhận đến client nhận |
| Khả dụng | Hiểu vai và trạng thái | Người mới nhận biết đang sút/bắt và chờ ai |
| Hồi quy | Luồng cũ còn đúng | Sửa reconnect không làm reset tỷ số hoặc sai lịch sử |

Lập trình kiểm thử luật chơi tách khỏi socket để có thể cấp thời gian giả và dữ liệu lựa chọn xác định. Việc này giúp kiểm tra deadline mà không phải chờ thật hoặc dựa vào thao tác tay thiếu ổn định.

### 9.2. Dữ liệu và trường hợp kiểm thử

Chuẩn bị ít nhất 3 tài khoản: A, B trong phòng và C ngoài phòng; 2 phòng độc lập; một lịch sử có thắng/thua và một trận hủy. Hai client A/B phải dùng phiên độc lập.

| Mã | Điều kiện | Kết quả mong đợi |
| --- | --- | --- |
| TC-01 | Đăng ký trùng username/email | Từ chối, không tạo tài khoản thừa |
| TC-02 | Hai request đồng thời tranh suất thứ hai | Chỉ một request thành công; phòng không quá 2 người |
| TC-03 | C gửi lựa chọn vào trận A/B | Từ chối, trạng thái không đổi |
| TC-04 | A chọn trước, B chưa chọn | A nhận ACK riêng; B chỉ biết A đã chọn, không có hướng trong payload/snapshot |
| TC-05 | Hai hướng trùng | SAVE; tỷ số không tăng; lượt sút vẫn tăng một |
| TC-06 | Hai hướng khác | GOAL; chỉ tỷ số người sút tăng một |
| TC-07 | Gửi lại actionId đã nhận hoặc đổi hướng sau khóa | Không tạo lựa chọn/bàn thứ hai; hướng khác bị từ chối |
| TC-08 | Gửi hướng sai, lượt cũ, trận cũ hoặc sau deadline | Lỗi phù hợp; không thay đổi lượt hiện tại |
| TC-09 | Hai lựa chọn và timer gần đồng thời | Lượt chỉ RESOLVED một lần; kết quả theo thời điểm tiếp nhận server |
| TC-10 | Hết giờ, người sút không chọn | MISS; không cộng bàn, tính lượt sút |
| TC-11 | Hết giờ, người sút đã chọn, thủ môn chưa chọn | GOAL; tính lượt và bàn đúng một lần |
| TC-12 | Hết giờ, cả hai chưa chọn | MISS; không coi hai null là trùng hướng |
| TC-13 | A ghi bàn ở lượt đầu của một cặp sút bổ sung | Chưa kết thúc; B vẫn được sút |
| TC-14 | Cả hai sút xong cặp bổ sung, tỷ số khác nhau | Xác định người thắng; nếu hòa thì mở cặp tiếp theo |
| TC-15 | Đang cách biệt sau lượt 7 nhưng chưa đủ 5 lượt mỗi bên | Tiếp tục theo luật đề tài, không kết thúc sớm |
| TC-16 | B mất mạng và vào lại trong hạn | Nhận snapshot đã lọc; không reset deadline, tỷ số hoặc lựa chọn |
| TC-17 | B quá hạn reconnect, A còn online | B thua bỏ cuộc; lưu kết quả đúng một lần |
| TC-18 | Cả hai offline hết hạn hoặc server restart | ABORTED theo chính sách; không cộng thắng/thua |
| TC-19 | Trận đã kết thúc nhưng timer cũ tiếp tục chạy | Bỏ qua, không đổi người thắng/kết quả |
| TC-20 | Hai người đồng ý chơi lại | matchId mới, tỷ số/lượt mới; lịch sử cũ giữ nguyên |
| TC-21 | Ghi kết quả MySQL lỗi rồi retry | Không tạo kết quả hoặc thống kê trùng; thông báo trạng thái lưu đúng |
| TC-22 | Chat sai mã, quá nhanh hoặc gửi sang phòng khác | Từ chối/giới hạn; không phát sang phòng khác |
| TC-23 | Đăng xuất khi socket còn mở | Socket mất quyền điều khiển, áp dụng chính sách ngắt kết nối |
| TC-24 | Kết nối mới thay kết nối cũ cùng tài khoản | Chỉ kết nối mới điều khiển; sự kiện đóng kết nối cũ không đánh dấu mới offline |

Đối chiếu luật với cả 9 tổ hợp LEFT/CENTER/RIGHT, các mốc sát deadline và dữ liệu đủ 10 lượt ban đầu. Ghi môi trường, log sự kiện đã lọc và kết quả trên cả hai máy để làm bằng chứng.

### 9.3. Ghi nhận và xử lý lỗi

Một bug report cần: tiêu đề, phiên bản/môi trường, điều kiện ban đầu, bước tái hiện, kết quả thực tế, kết quả mong đợi và bằng chứng đã loại bỏ dữ liệu nhạy cảm.

Phân biệt **mức độ ảnh hưởng** với **ưu tiên sửa**. Lỗi lộ hướng, sai người thắng hoặc cộng bàn hai lần có mức ảnh hưởng cao; lỗi căn lề có thể ưu tiên thấp. Quyết định ưu tiên còn phụ thuộc thời điểm phát hành và số người bị ảnh hưởng.

Quy trình sửa: tái hiện → tìm nguyên nhân → sửa → kiểm thử lại → kiểm tra phần liên quan → đóng lỗi. Với lỗi có nguy cơ lặp lại, bổ sung kiểm thử phù hợp để ngăn hồi quy.

### 9.4. Truy vết yêu cầu

| Yêu cầu | Thiết kế/thành phần | Kiểm thử | Trạng thái |
| --- | --- | --- | --- |
| FR-04 phòng 2 người | RoomService, khóa/điều phối thành viên | TC-02, TC-03 | Điền theo thực tế |
| FR-07 lựa chọn kín | SUBMIT_CHOICE, ACK riêng, DTO snapshot | TC-04, TC-07, TC-08 | Điền theo thực tế |
| FR-08 chấm và đồng bộ | GameEngine, stateVersion, timer | TC-05, TC-06, TC-09 | Điền theo thực tế |
| FR-09 kết thúc trận | Bộ đếm lượt và luật sút từng cặp | TC-13, TC-14, TC-15 | Điền theo thực tế |
| FR-10 mạng/timeout | Deadline, ConnectionRegistry, snapshot | TC-10 đến TC-12, TC-16 đến TC-19, TC-24 | Điền theo thực tế |
| FR-11 lịch sử | Transaction kết quả theo matchId | TC-17, TC-18, TC-21 | Điền theo thực tế |
| FR-12 chơi lại | POST_MATCH và tạo Match mới | TC-20 | Điền theo thực tế |

**Đầu ra:** danh sách kiểm thử, kết quả, lỗi và bằng chứng kiểm tra.

**Tiêu chí hoàn thành:** đạt các tiêu chí đã chốt; các lỗi ảnh hưởng tính kín, luật, đồng bộ hoặc kết quả lưu phải được xử lý trước bản nghiệm thu. Các lỗi còn lại được ghi rõ, không tự đánh dấu đạt khi chưa thử.

## 10. Nghiệm thu

### 10.1. Mục đích

Người dùng hoặc bên có trách nhiệm xác nhận sản phẩm đáp ứng nhu cầu và tiêu chí chấp nhận. Kiểm thử kỹ thuật đạt chưa đồng nghĩa với nghiệp vụ đã phù hợp.

### 10.2. Cách thực hiện

1. Chốt phiên bản được nghiệm thu.
2. Chuẩn bị môi trường, dữ liệu mẫu và tài khoản đúng vai trò.
3. Chọn kịch bản thực tế gắn với yêu cầu.
4. Để người dùng thực hiện; ghi thao tác khó hiểu và kết quả sai.
5. Phân loại: lỗi, yêu cầu chưa đáp ứng, hay yêu cầu mới.
6. Sửa phần trong phạm vi rồi xác nhận lại.
7. Ghi kết luận và nội dung tồn đọng, người phụ trách, thời hạn xử lý.

**Kịch bản nghiệm thu đề tài:** hai người đăng nhập độc lập, tạo/tham gia phòng bằng mã, chat nhanh, hoàn thành 5 lượt mỗi bên; dùng dữ liệu thử để tạo hòa và đá thêm một cặp; ngắt mạng ở một lượt để kiểm tra deadline và reconnect; kiểm tra lịch sử/thống kê rồi cùng chấp nhận chơi lại. Quan sát payload trước công bố để xác nhận hướng được giữ kín.

**Đầu ra:** xác nhận nghiệm thu hoặc danh sách điều kiện còn thiếu.

**Tiêu chí hoàn thành:** các yêu cầu bắt buộc đã đạt; không còn lỗi ngăn sử dụng theo điều kiện phát hành đã thống nhất.

## 11. Triển khai và bàn giao

### 11.1. Chuẩn bị bản phát hành

1. Chọn đúng commit/phiên bản đã kiểm thử.
2. Build từ nguồn xác định, kiểm tra cấu hình môi trường.
3. Chuẩn bị cơ sở dữ liệu và kế hoạch migration.
4. Nếu cập nhật hệ thống đang dùng, sao lưu và kiểm tra khả năng phục hồi.
5. Cấu hình bí mật, kết nối, quyền truy cập, HTTPS nếu có truy cập qua mạng thực tế.
6. Chuẩn bị log, theo dõi sức khỏe và người nhận sự cố. Với đề tài này, cấu hình proxy chuyển tiếp WebSocket và timeout kết nối; dùng HTTPS/WSS khi triển khai qua mạng thực tế. Theo dõi số phòng, kết nối, độ dài hàng đợi lệnh và lỗi ghi kết quả.
7. Xác định điều kiện dừng triển khai và cách khôi phục.

### 11.2. Thực hiện triển khai

- Đưa bản phát hành lên môi trường đích bằng quy trình tái lập được.
- Thực hiện migration theo kế hoạch và theo dõi lỗi.
- Chạy smoke test bằng hai tài khoản: tạo phòng, tham gia, nhận sự kiện socket, gửi hai hướng, nhận cùng kết quả và kiểm tra lịch sử. Thử kết nối lại qua đúng địa chỉ triển khai, không chỉ localhost.
- Theo dõi sau phát hành, đối chiếu phiên bản và ghi lại kết quả.

Theo thiết kế một server, khởi động lại làm gián đoạn trận đang chơi. Trước cập nhật nên ngừng nhận trận mới, chờ trận hiện tại kết thúc hoặc thông báo áp dụng chính sách hủy trận; sau khởi động đánh dấu trận dang dở là ABORTED.

Rollback mã nguồn không tự đảo ngược thay đổi dữ liệu. Với migration làm mất hoặc đổi cấu trúc dữ liệu, cần phương án khôi phục riêng, hiểu rõ dữ liệu phát sinh sau sao lưu có thể bị ảnh hưởng.

### 11.3. Nội dung bàn giao

| Hạng mục | Nội dung |
| --- | --- |
| Mã nguồn | Repository, phiên bản/tag đã bàn giao |
| Cài đặt | Runtime, cấu hình mẫu, lệnh build/chạy |
| Dữ liệu | Migration, dữ liệu mẫu, quy trình sao lưu/khôi phục |
| Sử dụng | Hướng dẫn theo từng vai trò |
| Kỹ thuật | Kiến trúc, API, cấu trúc module, phụ thuộc |
| Chất lượng | Kết quả kiểm thử, giới hạn và lỗi tồn đọng |
| Vận hành | Địa điểm chạy, cách theo dõi, xử lý sự cố, người phụ trách |

Bàn giao quyền truy cập bằng kênh phù hợp; tài liệu công khai chỉ dùng giá trị mẫu.

**Đầu ra:** bản phát hành sử dụng được, bộ hướng dẫn và biên bản bàn giao.

**Tiêu chí hoàn thành:** bên tiếp nhận có thể chạy, sử dụng và thực hiện các thao tác vận hành thiết yếu theo hướng dẫn.

## 12. Vận hành, bảo trì và cải tiến

### 12.1. Theo dõi sau triển khai

- Ứng dụng có hoạt động và truy cập được không?
- Tỷ lệ lỗi, thời gian phản hồi và tài nguyên có bất thường không?
- Dữ liệu có nhất quán và sao lưu có thành công không?
- Người dùng gặp khó ở bước nào?
- Có trận bị treo, tỷ số lệch, sự kiện trễ hoặc kết quả chưa được lưu không?
- Mục tiêu ban đầu đã đạt đến đâu theo cách đo đã chọn?

### 12.2. Các loại bảo trì

| Loại | Ví dụ |
| --- | --- |
| Sửa lỗi | Sửa cộng bàn hai lần hoặc kết thúc sai giữa cặp sút bổ sung |
| Thích nghi | Điều chỉnh theo môi trường hoặc quy định nghiệp vụ thay đổi |
| Hoàn thiện | Cải thiện thông báo mất kết nối và tra cứu lịch sử |
| Phòng ngừa | Nâng phụ thuộc phù hợp, đơn giản hóa phần code khó bảo trì |

### 12.3. Xử lý sự cố

1. Ghi nhận tác động và xác định người xử lý.
2. Ưu tiên khôi phục dịch vụ, bảo vệ tính toàn vẹn dữ liệu.
3. Thu thập log và bằng chứng cần thiết.
4. Sửa nguyên nhân, kiểm tra và triển khai theo quy trình.
5. Xác nhận dịch vụ và dữ liệu đã ổn định.
6. Ghi bài học và công việc ngăn tái diễn khi sự cố đáng kể.

Định kỳ thử phục hồi từ bản sao lưu. Có file sao lưu chưa đủ chứng minh có thể khôi phục hệ thống.

### 12.4. Kết thúc vòng đời nếu cần

Khi ngừng sử dụng, lập kế hoạch thông báo, xuất hoặc chuyển dữ liệu, thu hồi tài khoản/bí mật, dừng dịch vụ và xử lý dữ liệu theo nghĩa vụ áp dụng.

**Đầu ra:** thông tin vận hành, bản sửa lỗi, phản hồi và backlog cải tiến.

**Tiêu chí hoàn thành từng vòng cải tiến:** vấn đề đã được xác nhận giải quyết, bản mới được kiểm chứng và tài liệu liên quan được cập nhật.

## 13. Các công việc xuyên suốt dự án

### 13.1. Quản lý thay đổi yêu cầu

Khi có yêu cầu mới:

1. Ghi lại nội dung, lý do và người đề nghị.
2. Đánh giá ảnh hưởng đến dữ liệu, giao diện, API, kiểm thử và thời gian.
3. Quyết định đưa vào phiên bản hiện tại, đổi với việc khác hay để sau.
4. Cập nhật backlog, đặc tả và tiêu chí chấp nhận.
5. Thông báo quyết định cho người bị ảnh hưởng.

Thay đổi nhỏ có thể xử lý nhanh; thay đổi làm lệch mục tiêu hoặc hạn giao cần quyết định rõ về phạm vi.

### 13.2. Quản lý rủi ro

| Rủi ro | Dấu hiệu | Cách giảm thiểu | Phương án dự phòng |
| --- | --- | --- | --- |
| Phạm vi tăng liên tục | Nhiều tính năng thêm nhưng hạn không đổi | Chốt MVP, xét ảnh hưởng từng thay đổi | Lùi việc ít ưu tiên |
| Tích hợp muộn | Các phần chỉ chạy riêng | Tích hợp và demo thường xuyên | Ưu tiên khôi phục luồng cốt lõi |
| Race condition | Đôi lúc cùng lượt tăng hai bàn | Tuần tự hóa lệnh và timer, test gửi đồng thời | Tạm dừng phát hành, sửa luồng chấm trước |
| Mất kết nối/restart | Trận chờ mãi hoặc lưu thiếu kết quả | Deadline, reconnect, snapshot và chính sách hủy rõ | Hủy trận gián đoạn, không tính thắng/thua sai |
| Phụ thuộc một thành viên | Chỉ một người hiểu module quan trọng | Review, chia sẻ và hướng dẫn chạy | Bàn giao ngắn trước mốc quan trọng |
| Mất dữ liệu | Chưa thử khôi phục | Sao lưu và diễn tập phục hồi | Khôi phục theo phương án đã kiểm tra |

### 13.3. Quản lý phiên bản và chất lượng

- Commit có mục đích rõ; tránh gộp nhiều thay đổi không liên quan.
- Review phần nghiệp vụ, quyền truy cập và dữ liệu quan trọng.
- Tự động hóa các kiểm tra lặp lại có giá trị.
- Ghi lại quyết định kỹ thuật lớn và lý do.
- Cập nhật tài liệu khi thay đổi hành vi, API hoặc cách triển khai.

### 13.4. Phân công trong nhóm nhỏ

Một người có thể kiêm nhiều vai trò, nhưng mỗi đầu ra cần có người chịu trách nhiệm. Ví dụ: người phụ trách yêu cầu, kỹ thuật, kiểm thử và triển khai có thể đồng thời là lập trình viên. Với chức năng quan trọng, nên có người khác xem lại để phát hiện giả định sai.

## 14. Ví dụ triển khai trọn vẹn một chức năng

**Chức năng: hai người chọn hướng kín, server chấm một lượt và công bố kết quả qua WebSocket.**

| Bước | Việc thực hiện | Kết quả cần có |
| --- | --- | --- |
| 1. Xác minh nhu cầu | Người chơi cần biết lựa chọn đã được nhận nhưng không biết hướng của nhau | Trạng thái chờ dễ hiểu, yêu cầu giữ kín |
| 2. Đặc tả | FR-07/FR-08, ba hướng, khóa chọn, timeout và tiêu chí chấp nhận | Quy tắc không mơ hồ |
| 3. Phân tích | Hai người chọn bất kỳ thứ tự; một người bỏ chọn; hai lệnh đến đồng thời | Use case và ngoại lệ |
| 4. Thiết kế | TurnState, SUBMIT_CHOICE, ACK riêng, TURN_RESULT, hàng đợi lệnh | Hợp đồng giao tiếp và trạng thái |
| 5. Lập trình | Xác thực thành viên, deadline, actionId, khóa lựa chọn và chấm một lần | Lượt chạy được trên hai client |
| 6. Kiểm thử | 9 tổ hợp hướng, gửi lặp, sai lượt, timeout, snapshot và race condition | Kết quả có bằng chứng |
| 7. Nghiệm thu | Hai người chơi thử, kiểm tra payload và tỷ số | Xác nhận đáp ứng FR-07/FR-08 |
| 8. Triển khai | Kiểm tra WebSocket và luồng tương tự ở môi trường đích | Hai máy chơi được qua mạng |
| 9. Theo dõi | Độ trễ, lỗi từ chối và trận không tiến triển | Phát hiện lỗi thực tế để cải tiến |

### 14.1. Pseudocode nhận lựa chọn

Mọi lệnh và timer của cùng trận phải được xử lý theo thứ tự thống nhất. `receivedAt` do server ghi nhận khi tiếp nhận, không nhận từ client.

```text
onSubmitChoice(authenticatedConnection, command, receivedAt):
    đưa command vào bộ xử lý tuần tự của phòng/trận

    kiểm tra kết nối đang có quyền điều khiển
    lấy playerId từ danh tính đã xác thực
    kiểm tra là thành viên của matchId, turnId được chỉ định

    nếu actionId đã được xử lý:
        trả ACK hoặc lỗi đã lưu của lệnh đó; dừng

    kiểm tra trận đang chạy, lượt ở COLLECTING
    kiểm tra receivedAt < deadline và hướng thuộc LEFT/CENTER/RIGHT
    nếu người này đã có lựa chọn: trả CHOICE_LOCKED; dừng

    lưu lựa chọn riêng tư theo vai trò của người này
    tăng stateVersion
    ghi nhận actionId; gửi CHOICE_ACK riêng cho người gửi
    phát CHOICE_STATUS chỉ gồm hasChosen, không chứa direction

    nếu cả hai đã chọn:
        resolveTurnOnce(currentTurn)
```

### 14.2. Pseudocode chấm lượt và xét kết thúc

```text
onTurnTimeout(expectedMatchId, expectedTurnId):
    đưa lệnh timeout vào cùng bộ xử lý tuần tự
    bỏ qua nếu trận/lượt không còn khớp hoặc đã kết thúc
    nếu hiện tại đã đến deadline: resolveTurnOnce(currentTurn)

resolveTurnOnce(turn):
    nếu turn không ở COLLECTING: dừng
    nếu chưa đủ lựa chọn và chưa hết giờ: dừng

    nếu người sút chưa chọn:
        outcome = MISS
    ngược lại nếu thủ môn chưa chọn:
        outcome = GOAL
    ngược lại nếu hai hướng trùng:
        outcome = SAVE
    ngược lại:
        outcome = GOAL

    chuyển turn sang RESOLVED
    tăng số lượt đã sút của người sút đúng một lần
    nếu outcome là GOAL: tăng tỷ số người sút đúng một lần
    tăng stateVersion
    phát TURN_RESULT với dữ liệu đã được phép công bố

    nếu mỗi bên đã sút ít nhất 5 lần
       và số lượt sút hai bên bằng nhau
       và tỷ số hai bên khác nhau:
        kết thúc trận và lưu kết quả theo matchId đúng một lần
    ngược lại:
        lên lịch chuyển lượt sau mốc hoạt ảnh
        chỉ mở lượt mới khi cả hai vẫn được phép tiếp tục và online
```

### 14.3. Những điểm dễ làm sai

- Không dùng `null == null` để suy ra cản phá khi cả hai hết giờ.
- Không kết thúc sau lượt đầu của cặp sút bổ sung.
- Không đọc hướng từ gói tin đối thủ trên frontend để “tự so sánh”. Chỉ server được chấm.
- Không giữ khóa trạng thái rồi chờ người chơi hoặc chờ hoạt ảnh; mỗi thao tác xử lý nhanh và kết thúc.
- Callback kết nối lại, bỏ cuộc và timer phải kiểm tra trận chưa kết thúc để không ghi đè kết quả.
- `stateVersion` chỉ áp dụng trong matchId tương ứng; trận chơi lại có matchId mới, client phải thay trạng thái theo thông báo trận mới.
- Lưu kết quả và phát socket là hai tác vụ khác nhau. Nếu gửi sự kiện thất bại sau commit, người chơi kết nối lại phải lấy được kết quả đã lưu; không tạo lại kết quả chỉ vì chưa nhận ACK từ client.

## 15. Kế hoạch tham khảo cho đồ án 8 tuần

Đây là lịch minh họa cho nhóm nhỏ với phạm vi vừa phải, không phải cam kết thời gian cho mọi dự án.

| Tuần | Trọng tâm | Sản phẩm kiểm tra được |
| --- | --- | --- |
| 1 | Khảo sát, vấn đề, mục tiêu, phạm vi, rủi ro | Đề cương và bằng chứng khảo sát |
| 2 | Yêu cầu, use case, mô hình miền, wireframe | SRS ban đầu, backlog, prototype |
| 3 | Kiến trúc, dữ liệu, REST/socket; thử hai client và lựa chọn kín | Gửi/nhận sự kiện, không lộ hướng, xác định cách tuần tự hóa |
| 4 | Đăng ký/đăng nhập, hồ sơ, tạo/tham gia phòng và sẵn sàng | Hai tài khoản vào phòng, chặn người thứ ba |
| 5 | Sân ba hướng, luật lượt, đổi vai, 5 lượt mỗi bên và sút từng cặp | Trận hoàn chỉnh, hoạt ảnh theo kết quả server |
| 6 | Deadline, reconnect, chat nhanh, lịch sử/thống kê và chơi lại | Bản đủ nội dung đề tài, test đồng thời và mất mạng |
| 7 | Người dùng thử, sửa lỗi, kiểm tra dữ liệu và chất lượng | Bản sẵn sàng nghiệm thu, lỗi tồn đọng rõ |
| 8 | Nghiệm thu, triển khai, tài liệu, tập demo và dự phòng | Bản giao cuối, hướng dẫn và báo cáo |

Mỗi tuần nên demo được kết quả thật. Duy trì khoảng dự phòng bằng cách không lấp đầy kế hoạch với tính năng phụ; điều chỉnh theo tiến độ và năng lực thực tế.

## 16. Bộ tài liệu và cấu trúc thư mục

### 16.1. Bộ tài liệu tối thiểu

| Tài liệu | Mục đích |
| --- | --- |
| project-brief.md | Vấn đề, mục tiêu, phạm vi, bên liên quan |
| requirements.md | Yêu cầu, tiêu chí chấp nhận, quy tắc |
| analysis.md | Use case, quy trình, mô hình miền |
| architecture.md | Kiến trúc và quyết định kỹ thuật |
| database.md | Mô hình dữ liệu và ràng buộc |
| api.md | REST và giao thức sự kiện WebSocket; quyền, lỗi, dữ liệu kín/công khai |
| test-plan.md | Phạm vi, cách kiểm thử, điều kiện đạt |
| test-results.md | Kết quả thực tế và lỗi tồn đọng |
| deployment.md | Build, cấu hình, migration, phục hồi |
| user-guide.md | Hướng dẫn thao tác theo vai trò |
| README.md | Tổng quan và cách bắt đầu nhanh |

Dự án nhỏ có thể gộp các tài liệu, miễn vẫn đủ thông tin và dễ tìm. Không cần duy trì hai bản mô tả API thủ công nếu một hợp đồng API có thể là nguồn chính đáng tin cậy.

### 16.2. Cấu trúc gợi ý

| Đường dẫn | Nội dung |
| --- | --- |
| `README.md` | Tổng quan, cách cài và chạy |
| `docs/` | Yêu cầu, phân tích, thiết kế, kiểm thử, bàn giao |
| `docs/decisions/` | Các quyết định kỹ thuật quan trọng |
| `frontend/` | Mã nguồn giao diện |
| `backend/` | Mã nguồn API và nghiệp vụ |
| `backend/.../auth/` | Đăng ký, đăng nhập, phiên và xác thực socket |
| `backend/.../player/` | Hồ sơ và thông tin người chơi |
| `backend/.../room/` | Mã phòng, thành viên, sẵn sàng, chat nhanh |
| `backend/.../game/` | GameEngine, MatchState, TurnState, luật, timer, reconnect |
| `backend/.../history/` | Lưu/đọc kết quả và thống kê |
| `backend/.../websocket/` | Handler, DTO thông điệp và kết nối được phép điều khiển |
| `frontend/js/` | Tách phần gọi REST, socket, giao diện phòng, sân và hoạt ảnh |
| `database/` | Thiết kế, dữ liệu giả; migration có thể nằm trong backend theo công cụ |
| `scripts/` | Tác vụ build, khởi tạo, kiểm tra và vận hành |
| `deployment/` | Cấu hình triển khai không chứa bí mật |

Đây là gợi ý tổ chức, không phải cấu trúc bắt buộc. Kiểm thử nên đặt tại vị trí phù hợp với công cụ của từng phần để dễ chạy và bảo trì.

## 17. Checklist và các lỗi thường gặp

### 17.1. Trước khi code

- [ ] Có căn cứ cho vấn đề cần giải quyết.
- [ ] Biết người dùng và mục tiêu chính.
- [ ] Chốt phạm vi MVP và nội dung chưa làm.
- [ ] Luồng chính có tiêu chí chấp nhận.
- [ ] Quyền và quy tắc nghiệp vụ quan trọng đã rõ.
- [ ] Có thiết kế dữ liệu, API và giao diện đủ để bắt đầu.
- [ ] Xác định rủi ro và thử phần kỹ thuật chưa chắc chắn.
- [ ] Phân biệt một lượt với một cặp; chốt timeout, reconnect, restart và chơi lại.

### 17.2. Trước khi tích hợp

- [ ] Kiểm tra đầu vào và quyền tại backend.
- [ ] Dữ liệu được bảo vệ bằng ràng buộc/transaction phù hợp.
- [ ] Giao diện xử lý tải, lỗi và dữ liệu trống.
- [ ] Payload/snapshot trước công bố không chứa hướng; timer cũ và lệnh lặp không chấm lại lượt.
- [ ] Các kiểm tra liên quan đạt; thay đổi đã được review.
- [ ] Migration và cấu hình mẫu đã cập nhật.
- [ ] Không có mật khẩu, khóa API hoặc dữ liệu thật không phù hợp trong mã nguồn.

### 17.3. Trước khi bàn giao

- [ ] Các yêu cầu bắt buộc đã được xác nhận.
- [ ] Lỗi còn lại và giới hạn được ghi rõ.
- [ ] Xác định đúng bản phát hành và có thể dựng lại.
- [ ] Kiểm tra thành công ở môi trường đích trên hai tài khoản/thiết bị.
- [ ] Đã thử 5 lượt mỗi bên, hòa/sút từng cặp, mất mạng, chat nhanh, lịch sử và chơi lại.
- [ ] Có phương án sao lưu và phục hồi phù hợp.
- [ ] Người khác có thể chạy và sử dụng theo hướng dẫn.
- [ ] Báo cáo, giao diện, API và code phản ánh cùng một phiên bản.

### 17.4. Lỗi thường gặp

| Lỗi | Hậu quả | Cách xử lý |
| --- | --- | --- |
| Chọn công nghệ trước khi hiểu bài toán | Dùng giải pháp không cần thiết | Xác minh nhu cầu và ràng buộc trước |
| Mục tiêu chỉ liệt kê chức năng | Khó chứng minh giá trị | Gắn mục tiêu với vấn đề và cách đánh giá |
| Yêu cầu mơ hồ | Khó nghiệm thu | Viết hành vi và tiêu chí kiểm chứng |
| Không phân biệt lượt sút và cặp lượt | Kết thúc sai khi hòa | Đếm số lượt từng người, chỉ xét thắng khi cân bằng lượt |
| Client tự chấm hoặc nhận hướng quá sớm | Lộ lựa chọn, có thể giả kết quả | Server giữ kín và quyết định luật |
| Timer và lệnh cùng sửa trận không điều phối | Cộng bàn/lưu kết quả hai lần | Tuần tự hóa và kiểm tra chuyển trạng thái |
| Phân quyền chỉ ở giao diện | Có thể gọi API vượt quyền | Kiểm tra tại backend và trên từng tài nguyên |
| Mỗi thành viên làm riêng đến cuối | Lỗi tích hợp dồn sát hạn | Tích hợp và demo theo từng lát cắt |
| Báo hoàn thành khi mới xong màn hình | Luồng thực tế chưa chạy | Định nghĩa hoàn thành gồm tích hợp và kiểm tra |
| Thêm quá nhiều tính năng | Không hoàn thiện phần chính | Giữ MVP, xét ảnh hưởng thay đổi |
| Đưa số liệu chưa đo vào kết quả | Kết luận không có căn cứ | Phân biệt giả định, mục tiêu và kết quả thực nghiệm |
| Tài liệu khác code | Khó bảo trì và bảo vệ đồ án | Cập nhật cùng thay đổi hành vi |

## 18. Mẫu biểu dùng ngay

### 18.1. Mẫu mô tả dự án

```markdown
# Tên dự án

## Vấn đề
- Ai gặp vấn đề:
- Công việc hiện tại:
- Khó khăn và tác động:
- Bằng chứng, thời điểm, giới hạn:

## Mục tiêu
- Kết quả cần đạt:
- Cách đánh giá:

## Phạm vi
- Trong phiên bản này:
- Ngoài phiên bản này:

## Điều kiện thực hiện
- Thành viên, thời gian, chi phí:
- Dữ liệu và quyền sử dụng:
- Ràng buộc, giả định:
- Rủi ro và phương án dự phòng:
```

### 18.2. Mẫu đặc tả một chức năng

```markdown
## FR-xx — Tên chức năng

- Người dùng/vai trò:
- Nhu cầu và giá trị:
- Tiền điều kiện:
- Dữ liệu đầu vào:
- Luồng chính:
- Luồng thay thế và lỗi:
- Quy tắc nghiệp vụ:
- Quyền truy cập:
- Kết quả và dữ liệu thay đổi:
- Tiêu chí chấp nhận:
- Mức ưu tiên:
- Điểm chưa chốt:
```

### 18.3. Mẫu công việc phát triển

```markdown
## Tên công việc

- Liên kết yêu cầu:
- Người phụ trách:
- Kết quả cần giao:
- Phạm vi thay đổi:
- Phụ thuộc:
- Ước lượng và mốc dự kiến:
- Cách kiểm tra:
- Điều kiện hoàn thành:
- Rủi ro hoặc điểm cần trao đổi:
```

### 18.4. Mẫu báo lỗi

```markdown
## Tiêu đề lỗi

- Phiên bản/môi trường:
- Mức độ ảnh hưởng và ưu tiên:
- Điều kiện ban đầu:
- Các bước tái hiện:
  1.
  2.
  3.
- Kết quả thực tế:
- Kết quả mong đợi:
- Bằng chứng đã loại bỏ dữ liệu nhạy cảm:
- Yêu cầu/test case liên quan:
- Kết quả kiểm thử lại sau sửa:
```

### 18.5. Mẫu chốt một vòng làm việc

```markdown
## Vòng làm việc: ...

- Mục tiêu:
- Việc hoàn thành và bằng chứng demo/kiểm tra:
- Việc chưa hoàn thành, nguyên nhân:
- Phản hồi người dùng:
- Thay đổi được thống nhất:
- Rủi ro mới:
- Ưu tiên vòng tiếp theo:
```

---

**Cách áp dụng cho đề tài:** làm trước luồng hai người vào phòng → chọn kín → server chấm → hai máy cùng nhận kết quả; sau đó hoàn thiện luật toàn trận, timeout/reconnect, tài khoản, chat nhanh, lịch sử và chơi lại. Mỗi giai đoạn chỉ coi là đạt khi có đầu ra kiểm tra được, phù hợp quy mô và mục tiêu của dự án.
