# 📋 form-builder-api (Spring Boot)

RESTful API untuk membuat dan mengelola form dinamis lengkap dengan pertanyaan, jawaban, dan sistem login JWT.  

---

## 🚀 Tech Stack

- Java 21
- Spring Boot 3.5.2
- Spring Security (JWT)
- PostgreSQL
- Swagger UI (springdoc-openapi)
- Maven

---

## 👤 Test Users

| Name    | Email                    | Password   |
|---------|--------------------------|------------|
| User 1  | user1@webtech.id         | password1  |
| User 2  | user2@webtech.id         | password2  |
| User 3  | user3@worldskills.org    | password3  |

---

## 📦 Fitur Utama

- 🔐 Login & Logout (JWT Auth)
- 📄 CRUD Form (dengan allowed domains)
- ❓ Tambah & Hapus Pertanyaan
- ✍️ Kirim & Lihat Jawaban Form
- 🧾 Swagger UI dokumentasi otomatis

---

## ▶️ Cara Menjalankan

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Buka di browser:  
📚 `http://localhost:8080/swagger-ui/index.html`

---

## 🛡️ Authorization

Sebagian besar endpoint membutuhkan token JWT.  
Setelah login, salin `accessToken` dan klik tombol **Authorize** di Swagger UI:

```
Bearer <accessToken>
```

---

## 🧪 Endpoint Utama (via Swagger)

| Method | Endpoint                                        | Keterangan                        |
|--------|--------------------------------------------------|------------------------------------|
| POST   | `/api/v1/auth/login`                            | Login user                         |
| POST   | `/api/v1/auth/logout`                           | Logout user                        |
| POST   | `/api/v1/forms`                                 | Buat form baru                     |
| GET    | `/api/v1/forms`                                 | Lihat semua form user              |
| GET    | `/api/v1/forms/{slug}`                          | Lihat detail form + pertanyaan     |
| POST   | `/api/v1/forms/{slug}/questions`                | Tambah pertanyaan ke form          |
| DELETE | `/api/v1/forms/{slug}/questions/{question_id}`  | Hapus pertanyaan                   |
| POST   | `/api/v1/forms/{slug}/responses`                | Submit jawaban form                |
| GET    | `/api/v1/forms/{slug}/responses`                | Lihat semua jawaban form (creator) |

---


## 🙏 Terima Kasih
