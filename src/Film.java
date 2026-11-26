public class Film {
    private int id;
    private String judul;
    private int harga;

    public Film(int id, String judul, int harga) {
        this.id = id;
        this.judul = judul;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public int getHarga() {
        return harga;
    }

    public String toString() {
        return judul + " - Rp" + harga;
    }
}
