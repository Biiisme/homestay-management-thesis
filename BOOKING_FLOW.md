# Booking Flow

File này là tài liệu nguồn để mô tả flow đặt phòng. Khi cần đổi nghiệp vụ, hãy sửa nội dung trong file này trước; phần code backend/frontend sẽ được cập nhật theo tài liệu này.

## Mục Tiêu

Khách hàng có thể xem phòng, chọn ngày lưu trú, tạo booking, theo dõi trạng thái booking. Admin hoặc nhân viên quản lý booking từ lúc khách tạo đến lúc hoàn tất hoặc hủy.

## Vai Trò

- Khách hàng: xem phòng, đăng ký/đăng nhập, tạo booking, xem lịch sử booking, gửi yêu cầu hủy nếu được phép.
- Nhân viên: xem booking, xác nhận booking, cập nhật check-in/check-out.
- Admin: có toàn quyền như nhân viên và quản lý dữ liệu hệ thống.

## Trạng Thái Booking

- `PENDING`: booking mới tạo, đang chờ xác nhận.
- `CONFIRMED`: booking đã được nhân viên/admin xác nhận.
- `CHECKED_IN`: khách đã nhận phòng.
- `CHECKED_OUT`: khách đã trả phòng, booking hoàn tất.
- `CANCELLED`: booking đã bị hủy.
- `NO_SHOW`: khách không đến.

## Flow Chính

1. Khách hàng vào trang danh sách phòng.
2. Khách hàng xem thông tin phòng:
   - tên phòng
   - loại phòng
   - sức chứa
   - diện tích
   - tiện nghi
   - ảnh phòng
   - giá tham khảo
3. Khách hàng chọn phòng muốn đặt.
4. Hệ thống yêu cầu khách đăng nhập nếu chưa đăng nhập.
5. Khách hàng nhập thông tin đặt phòng:
   - ngày check-in
   - ngày check-out
   - số lượng khách
6. Frontend gửi yêu cầu tạo booking lên backend.
7. Backend kiểm tra dữ liệu.
8. Nếu hợp lệ, backend tạo booking trạng thái `PENDING`.
9. Frontend hiển thị thông báo đặt phòng thành công và thông tin booking.
10. Nhân viên/admin xác nhận booking, chuyển trạng thái sang `CONFIRMED`.
11. Khi khách đến, nhân viên/admin chuyển trạng thái sang `CHECKED_IN`.
12. Khi khách trả phòng, nhân viên/admin chuyển trạng thái sang `CHECKED_OUT`.

## Flow Hủy Booking

1. Khách hàng hoặc nhân viên/admin yêu cầu hủy booking.
2. Backend kiểm tra booking có được phép hủy không.
3. Chỉ cho phép hủy khi booking đang ở trạng thái:
   - `PENDING`
   - `CONFIRMED`
4. Không cho hủy nếu booking đã bắt đầu hoặc đã quá hạn check-in.
5. Nếu hợp lệ, backend chuyển booking sang `CANCELLED`.
6. Hệ thống tính phần trăm hoàn tiền nếu có chính sách hoàn tiền.

## Quy Tắc Kiểm Tra Khi Tạo Booking

Backend phải kiểm tra:

- Khách hàng tồn tại.
- Phòng tồn tại.
- Check-in và check-out không được để trống.
- Check-in phải sau thời điểm hiện tại.
- Check-out phải sau check-in.
- Thời gian lưu trú tối thiểu là 1 đêm.
- Số lượng khách phải lớn hơn hoặc bằng 1.
- Số lượng khách không vượt quá sức chứa loại phòng.
- Phòng không bị trùng booking trong khoảng thời gian đã chọn.
- Có bảng giá áp dụng cho loại phòng trong khoảng thời gian lưu trú.

## Request Tạo Booking

```json
{
  "customerId": 9,
  "employeeId": null,
  "roomId": 1,
  "checkIn": "2026-05-25T14:00:00",
  "checkOut": "2026-05-27T12:00:00",
  "guestCount": 2
}
```

## Response Booking Mong Muốn

```json
{
  "id": 123,
  "customerId": 9,
  "employeeId": null,
  "roomId": 1,
  "checkIn": "2026-05-25T14:00:00",
  "checkOut": "2026-05-27T12:00:00",
  "guestCount": 2,
  "totalAmount": 1200000,
  "currentStatus": "PENDING",
  "pendingExpiresAt": "2026-05-21T13:00:00"
}
```

## Quy Tắc Chuyển Trạng Thái

Các chuyển trạng thái hợp lệ:

- `PENDING` -> `CONFIRMED`
- `PENDING` -> `CANCELLED`
- `PENDING` -> `NO_SHOW`
- `CONFIRMED` -> `CHECKED_IN`
- `CONFIRMED` -> `CANCELLED`
- `CONFIRMED` -> `NO_SHOW`
- `CHECKED_IN` -> `CHECKED_OUT`

Các trạng thái kết thúc:

- `CHECKED_OUT`
- `CANCELLED`
- `NO_SHOW`

Không cho chuyển tiếp từ trạng thái kết thúc.

## Chính Sách Giữ Booking

Booking mới tạo ở trạng thái `PENDING`.

Mặc định:

- Booking `PENDING` được giữ trong 30 phút.
- Nếu quá thời gian giữ mà chưa được xác nhận, hệ thống có thể tự động hủy booking.

Có thể chỉnh:

- Thời gian giữ booking.
- Có cần thanh toán trước để xác nhận hay không.
- Ai được quyền xác nhận booking.

## Chính Sách Hoàn Tiền

Mặc định:

- Hủy trước check-in hơn 24 giờ: hoàn 100%.
- Hủy trong vòng 24 giờ trước check-in: hoàn 50%.
- Hủy sau thời điểm check-in: không cho hủy.

Có thể chỉnh lại chính sách này theo nhu cầu kinh doanh.

## Màn Hình Frontend Cần Có

### Khách Hàng

- Trang danh sách phòng.
- Modal/trang chi tiết phòng.
- Form đặt phòng.
- Thông báo kết quả đặt phòng.
- Trang lịch sử booking của khách.
- Nút hủy booking nếu booking còn được phép hủy.

### Admin/Nhân Viên

- Trang danh sách booking.
- Bộ lọc theo khách hàng, phòng, chi nhánh, trạng thái.
- Chi tiết booking.
- Nút xác nhận booking.
- Nút check-in.
- Nút check-out.
- Nút hủy booking.
- Nút đánh dấu no-show.

## Các Điểm Có Thể Chỉnh Sửa Sau

Ghi thay đổi mong muốn ở đây để làm cơ sở sửa code:

- [ ] Có cần khách thanh toán trước không?
- [ ] Booking `PENDING` giữ trong bao lâu?
- [ ] Khách có được tự hủy booking không?
- [ ] Nhân viên có được tạo booking thay khách không?
- [ ] Có cần chọn thêm dịch vụ đi kèm không?
- [ ] Có cần nhập ghi chú booking không?
- [ ] Có cần gửi email/SMS xác nhận không?
- [ ] Có cần đặt cọc không?
- [ ] Chính sách hoàn tiền cụ thể là gì?

## Ghi Chú Thay Đổi

Thêm các thay đổi nghiệp vụ mới vào đây:

- Chưa có.
