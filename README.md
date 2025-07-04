
# 📘 LearnByDoing - REST API Spring Boot dengan OTP & JWT

Proyek ini adalah backend REST API menggunakan **Spring Boot**, **PostgreSQL**, dan **JWT**. Sistem autentikasi dibuat aman dengan verifikasi **OTP via email** saat pendaftaran dan login. Setelah berhasil login, user mendapatkan token **JWT** untuk mengakses endpoint yang dilindungi. Proyek ini siap diintegrasikan dengan frontend seperti **Flutter (GetX)**.

---

## 🚀 Fitur Utama

- ✅ Register dengan OTP via email
- ✅ Verifikasi OTP untuk aktivasi akun
- ✅ Login menggunakan email dan password
- ✅ Validasi input (email/password tidak boleh kosong)
- ✅ Login menghasilkan JWT
- ✅ Endpoint `profile` dilindungi menggunakan token JWT

---

## 🛠️ Teknologi

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Token)
- Jakarta Mail (JavaMailSender)
- PostgreSQL
- Lombok
- Jakarta Persistence (JPA)

---

## 📁 Struktur Proyek

```
learnbydoing/
└── src/main/java/com/learnbydoing/
    ├── config/               // Konfigurasi Spring Security & JWT
    ├── controller/           // Endpoint REST API
    ├── dto/                  // DTO untuk login/register/otp
    ├── entity/               // Entity JPA (User)
    ├── repository/           // Interface Spring Data JPA
    ├── security/             // JWT util & authentication filter
    └── service/              // Logika bisnis register, login, otp
```

---

## ⚙️ Konfigurasi `application.properties`

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/learnbydoing
spring.datasource.username=postgres
spring.datasource.password=your_password

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Secret (minimal 256 bit)
jwt.secret=MySuperSecretKeyForJWTTokenWhichIsLongEnough1234567890

# Email (gunakan Gmail App Password)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_gmail@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 🧪 Endpoint API

| Method | Endpoint                       | Keterangan                             |
|--------|--------------------------------|----------------------------------------|
| POST   | `/api/auth/register`           | Mendaftar dengan email dan password    |
| POST   | `/api/auth/verify-register-otp`| Verifikasi OTP pendaftaran             |
| POST   | `/api/auth/login`              | Login & kirim kembali OTP              |
| POST   | `/api/auth/verify-otp`         | Verifikasi OTP login & keluarkan token |
| GET    | `/api/user/profile`            | Endpoint hanya untuk user login        |

---

## 🔄 Contoh Testing via Postman

### ✅ Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@gmail.com",
  "password": "123456"
}
```

### 🔐 Verifikasi OTP Register
```http
POST /api/auth/verify-register-otp
Content-Type: application/json

{
  "email": "user@gmail.com",
  "otp": "123456"
}
```

### 🔑 Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@gmail.com",
  "password": "123456"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 🔓 Akses Endpoint Dilindungi
```http
GET /api/user/profile
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 🧠 Catatan Tambahan

- Email OTP dikirim menggunakan SMTP Gmail, pastikan **aktifkan App Password**.
- JWT hanya berlaku untuk beberapa menit/jam, tergantung implementasi `JwtUtil`.
- Semua endpoint selain `/api/auth/**` membutuhkan header `Authorization`.

---

## 📱 Rencana Integrasi Flutter

- Login ➜ simpan token JWT (misalnya pakai `shared_preferences`)
- Setiap request ➜ sertakan header `Authorization: Bearer <token>`
- Gunakan package `http` atau `dio` + `GetX` untuk state management

---

## 👨‍💻 Kontributor

- Raehan Putra Mahendra (Backend Developer)
- Dibantu dengan AI ChatGPT oleh OpenAI

---

## 📄 Lisensi

Proyek ini terbuka untuk penggunaan pembelajaran dan pengembangan aplikasi berbasis autentikasi modern menggunakan Spring Boot & JWT.
