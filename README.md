# Aplikasi Bioskop Sederhana

Program Java sederhana untuk pemesanan tiket bioskop, mirip TIX ID versi kecil. Dibuat dengan Java Swing (tampilan) dan MySQL (database).

## Fitur

- **Admin**: menambah dan menghapus film.
- **Pelanggan**: memilih film, jam tayang, dan jumlah tiket, lalu klik Beli Tiket. Total harga langsung ditampilkan.

## File Program

| File | Fungsi |
|---|---|
| `Main.java` | Menu awal (pilih Admin atau Pelanggan) |
| `HalamanAdmin.java` | Halaman admin |
| `HalamanPelanggan.java` | Halaman pelanggan |
| `Film.java` | Menyimpan data film (id, judul, harga) |
| `FilmDAO.java` | Berisi semua perintah SQL |
| `Koneksi.java` | Menghubungkan Java ke MySQL |

## Database

Buat manual di phpMyAdmin atau MySQL Workbench:

- Nama database: `db_bioskop`
- Nama tabel: `film`

| Kolom | Tipe |
|---|---|
| id | INT, primary key, auto increment |
| judul | VARCHAR(100) |
| harga | INT |

## Cara Menjalankan

1. Nyalakan MySQL dan pastikan database sudah dibuat.
2. Siapkan file MySQL Connector/J (`.jar`).
3. Kompilasi dan jalankan:

```
javac *.java
java -cp ".;mysql-connector-j-xxx.jar" Main
```

(Di Mac/Linux, ganti tanda `;` dengan `:`)

## Penjelasan Thread

Di Java Swing, tampilan dijalankan oleh satu thread utama. Kalau kita menjalankan proses lama (misalnya mengambil data dari database) di thread itu, jendela akan **macet atau hang**.

Karena itu proses database dijalankan di **thread terpisah**. Setelah selesai, hasilnya ditampilkan ke layar dengan `SwingUtilities.invokeLater`, karena komponen tampilan hanya boleh diubah dari thread utama.

Alurnya saat tombol diklik:

1. Tombol dinonaktifkan dan status berubah menjadi "Saving..." (supaya tidak diklik dua kali).
2. Thread baru dibuat untuk menyimpan data ke database.
3. Setelah selesai, status dan tombol dikembalikan lewat `invokeLater`.

Contoh singkat:

```java
new Thread(() -> {
    // proses database di sini
    SwingUtilities.invokeLater(() -> {
        // ubah tampilan di sini
    });
}).start();
```

`Thread.sleep(1000)` hanya dipakai untuk simulasi proses lambat, supaya tulisan "Saving..." sempat terlihat.

## Penjelasan SQL

Semua perintah SQL ada di `FilmDAO.java`:

| Method | Perintah SQL |
|---|---|
| `insert` | `INSERT INTO film (judul, harga) VALUES (?, ?)` |
| `delete` | `DELETE FROM film WHERE id = ?` |
| `getAll` | `SELECT * FROM film` |

Hal yang dipakai:

- **PreparedStatement**: tanda `?` diisi dengan nilai lewat `setString` dan `setInt`. Ini lebih aman dari SQL Injection.
- **commit dan rollback** (di `insert`): kalau berhasil data disimpan (`commit`), kalau gagal dibatalkan (`rollback`).
- **`conn.close()`**: koneksi selalu ditutup setelah dipakai, dengan blok `finally`.

## Catatan

- Checkout pelanggan hanya simulasi dan belum disimpan ke database.
- Belum ada login dan pemilihan kursi.
