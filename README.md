Inisialisasi Proyek Spring Boot
Detail:
Membuat proyek baru menggunakan Spring Initializr.
Menambahkan dependency dasar seperti Spring Web, Spring Data JPA, MySQL Driver, dan Spring Boot DevTools.
Menambahkan konfigurasi awal untuk application.properties dengan pengaturan koneksi ke database MySQL.
Files:
pom.xml
src/main/resources/application.properties
Membuat Model untuk Product dan Category
Detail:
Menambahkan entitas Product dan Category.
Menambahkan relasi @ManyToOne pada Product untuk menghubungkan dengan Category.
Menambahkan relasi @OneToMany pada Category untuk menghubungkan dengan Product.
Menggunakan anotasi @Entity, @Table, dan @Id untuk mendefinisikan entitas.
Files:
Product.java
Category.java
Menambahkan Repository untuk Product dan Category
Detail:
Membuat interface ProductRepository dan CategoryRepository yang memperluas JpaRepository.
Menambahkan metode custom untuk pencarian produk berdasarkan kategori dan pencarian dengan pagination.
Files:
ProductRepository.java
CategoryRepository.java
Implementasi Service Layer untuk Product dan Category
Detail:
Membuat ProductService dan CategoryService untuk mengelola logika bisnis.
Menambahkan metode seperti createProduct, updateProduct, deleteProduct, getProductById, dan getAllProducts.
Menambahkan fitur cascading untuk penghapusan kategori yang terkait dengan produk.
Files:
ProductService.java
CategoryService.java
Membuat Controller untuk Product dan Category
Detail:
Membuat ProductController dan CategoryController.
Menambahkan endpoint REST API untuk operasi CRUD pada produk dan kategori.
Menggunakan anotasi @RestController dan @RequestMapping.
Files:
ProductController.java
CategoryController.java
Menambahkan Validasi untuk Input Data
Detail:
Menambahkan validasi input menggunakan anotasi seperti @NotNull, @Size, dan @Min.
Menggunakan BindingResult untuk menangani error validasi dan mengembalikan pesan error yang sesuai.
Files:
Product.java (dengan anotasi validasi)
Category.java (dengan anotasi validasi)
Implementasi Pagination dan Sorting pada Produk
Detail:
Menambahkan fitur pagination dan sorting pada metode pencarian produk di ProductService.
Menambahkan endpoint untuk mendukung pagination dan sorting di ProductController.
Files:
ProductService.java
ProductController.java
Implementasi Soft Delete untuk Product
Detail:
Menggunakan anotasi @SQLDelete dan @Where untuk mengimplementasikan soft delete pada entitas Product.
Menambahkan metode deleteProduct di ProductService untuk melakukan soft delete.
Files:
Product.java (dengan anotasi @SQLDelete dan @Where)
ProductService.java
Menambahkan Fungsi Upload Gambar untuk Produk
Detail:
Menambahkan kemampuan untuk mengunggah gambar produk menggunakan MultipartFile.
Menyimpan gambar yang diunggah ke file system dan menyimpan path gambar di database.
Menambahkan validasi file gambar yang diunggah (tipe file, ukuran maksimum).
Files:
ProductController.java
FileUploadUtil.java (helper class untuk menangani file upload)
Menambahkan Fitur Authentication dan Authorization
Detail:
Mengintegrasikan Spring Security untuk autentikasi dan otorisasi.
Membuat model User dan Role dengan relasi @ManyToMany.
Mengimplementasikan JWT untuk manajemen sesi pengguna.
Membuat endpoint login dan register.
Files:
User.java
Role.java
SecurityConfig.java
AuthController.java
Implementasi Role-based Access Control (RBAC)
Detail:
Menambahkan peran pengguna (admin, user) dengan otorisasi berbasis peran.
Membatasi akses ke endpoint tertentu hanya untuk admin.
Menggunakan anotasi @PreAuthorize untuk mengamankan metode di service.
Files:
SecurityConfig.java
ProductController.java (dengan anotasi @PreAuthorize)
Menambahkan Fitur Cart Management
Detail:
Membuat entitas Cart dan CartItem.
Menambahkan endpoint untuk menambah, menghapus, dan melihat item di keranjang belanja.
Menambahkan logika untuk mengelola stok produk saat ditambahkan ke keranjang.
Files:
Cart.java
CartItem.java
CartController.java
Implementasi Fitur Order Management
Detail:
Membuat entitas Order dan OrderItem.
Menambahkan endpoint untuk membuat, melihat, dan mengelola pesanan.
Mengurangi stok produk saat pesanan dibuat.
Files:
Order.java
OrderItem.java
OrderController.java
Menambahkan Fungsi Checkout dan Payment
Detail:
Mengintegrasikan sistem pembayaran (contoh: Stripe, PayPal).
Menambahkan endpoint untuk memproses pembayaran dan mengonfirmasi pesanan.
Mengirim email konfirmasi setelah pesanan berhasil diproses.
Files:
PaymentService.java
OrderController.java
EmailService.java
Deployment Configuration
Detail:
Menambahkan konfigurasi untuk deployment (contoh: Dockerfile, Kubernetes YAML, atau Heroku).
Menambahkan konfigurasi untuk build produksi.
Mengatur application.properties untuk environment staging dan production.
Files:
Dockerfile
application-staging.properties
application-production.properties
Dokumentasi Proyek
Detail:
Menambahkan dokumentasi API menggunakan Swagger.
Membuat README.md dengan instruksi untuk setup dan menjalankan proyek.
Menambahkan komentar pada kode untuk meningkatkan keterbacaan.
Files:
README.md
SwaggerConfig.java
