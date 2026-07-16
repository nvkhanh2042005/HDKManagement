# HDK Management System

## Giới thiệu

HDK Management là phần mềm quản lý doanh nghiệp được xây dựng bằng **Java Swing** theo mô hình **MVC (Model - View - Controller)**. Dự án hỗ trợ quản lý các hoạt động kinh doanh như nhân viên, khách hàng, nhà cung cấp, sản phẩm, hóa đơn và tài khoản người dùng.

Phần mềm được phát triển trên **NetBeans IDE (Ant Project)** và sử dụng **MySQL** để lưu trữ dữ liệu.

---

## Chức năng chính

- Đăng nhập hệ thống
- Quản lý nhân viên
- Quản lý khách hàng
- Quản lý nhà cung cấp
- Quản lý sản phẩm
- Quản lý danh mục sản phẩm
- Quản lý hóa đơn nhập
- Quản lý hóa đơn bán
- Quản lý tài khoản
- Thống kê dữ liệu

---

## Công nghệ sử dụng

- Java SE
- Java Swing
- MVC Architecture
- NetBeans IDE
- Apache Ant
- MySQL
- JDBC (MySQL Connector/J)

---

## Cấu trúc dự án

```
HDKManagement
│
├── database/          # File cơ sở dữ liệu MySQL
├── lib/               # Thư viện ngoài
├── nbproject/         # Cấu hình NetBeans
├── src/
│   ├── controller/
│   ├── dao/
│   ├── entity/
│   ├── model/
│   ├── service/
│   ├── utils/
│   └── view/
│
└── README.md
```

---

## Yêu cầu hệ thống

- JDK 17 hoặc mới hơn
- NetBeans IDE
- MySQL Server 8.x
- MySQL Connector/J 8.0.33

---

## Cài đặt

### Bước 1. Clone hoặc tải dự án

```bash
git clone <repository-url>
```

hoặc tải file ZIP và giải nén.

---

### Bước 2. Import cơ sở dữ liệu

Mở MySQL và import file:

```
database/hdk_management.sql
```

---

### Bước 3. Thêm thư viện JDBC

Tải MySQL Connector/J:

https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.33

Sau đó thêm file:

```
mysql-connector-j-8.0.33.jar
```

vào thư mục

```
lib/
```

hoặc Add Library trong NetBeans.

---

### Bước 4. Cấu hình kết nối

Mở lớp cấu hình Database và sửa các thông tin:

```java
host
database
username
password
```

cho phù hợp với máy của bạn.

---

### Bước 5. Chạy chương trình

Mở dự án bằng NetBeans và chọn:

```
Run Project (F6)
```

---

## Kiến trúc dự án

Dự án được xây dựng theo mô hình MVC.

- **Model**: xử lý dữ liệu và kết nối cơ sở dữ liệu
- **View**: giao diện Java Swing
- **Controller**: xử lý nghiệp vụ và điều hướng

---

## Cơ sở dữ liệu

Hệ thống sử dụng MySQL với các bảng chính:

- TaiKhoan
- NhanVien
- KhachHang
- NhaCungCap
- DanhMuc
- SanPham
- HoaDonNhap
- ChiTietHoaDonNhap
- HoaDonBan
- ChiTietHoaDonBan

---

## Tác giả

Nhóm phát triển: **Nhóm 4**

Đề tài:

**Xây dựng phần mềm quản lý doanh nghiệp HDK**

---

## Giấy phép

Dự án được sử dụng cho mục đích học tập và nghiên cứu.
