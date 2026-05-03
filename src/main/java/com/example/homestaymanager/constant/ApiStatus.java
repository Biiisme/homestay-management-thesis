package com.example.homestaymanager.constant;

public class ApiStatus {
    public static final int OK = 200; // Thành công

    public static final int BAD_REQUEST = 400; //Gửi request email k có @
    public static final int UNAUTHORIZED = 401; // chưa đăng nhâp
    public static final int FORBIDDEN = 403;   //Không có quyền
    public static final int NOT_FOUND = 404; // Không tìm thấy

    public static final int INTERNAL_ERROR = 500; // Lỗi hệ thống
}
