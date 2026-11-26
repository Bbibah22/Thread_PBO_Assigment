import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;

public class HalamanAdmin extends JFrame {
    private JTextField txtJudul = new JTextField(12);
    private JTextField txtHarga = new JTextField(8);
    private JButton btnTambah = new JButton("Tambah Film");
    private JComboBox<Film> cmbFilm = new JComboBox<>();
    private JButton btnHapus = new JButton("Hapus");
    private JButton btnKembali = new JButton("Kembali");
    private JLabel lblStatus = new JLabel("Siap");
    private FilmDAO dao = new FilmDAO();

    public HalamanAdmin() {
        setTitle("Admin Bioskop");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Judul Film"));
        add(txtJudul);
        add(new JLabel("Harga Tiket"));
        add(txtHarga);
        add(btnTambah);
        add(new JLabel("Pilih Film"));
        add(cmbFilm);
        add(btnHapus);
        add(btnKembali);
        add(lblStatus);

        btnTambah.addActionListener(e -> {
            String judul = txtJudul.getText();
            String harga = txtHarga.getText();
            btnTambah.setEnabled(false);
            lblStatus.setText("Saving...");
            new Thread(() -> {
                String pesan;
                try {
                    Thread.sleep(1000);
                    dao.insert(judul, Integer.parseInt(harga));
                    pesan = "Film berhasil ditambah";
                } catch (Exception ex) {
                    pesan = "Error: " + ex.getMessage();
                }
                String hasil = pesan;
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText(hasil);
                    btnTambah.setEnabled(true);
                    muatFilm();
                });
            }).start();
        });

        btnHapus.addActionListener(e -> {
            Film f = (Film) cmbFilm.getSelectedItem();
            if (f == null) {
                lblStatus.setText("Pilih film dulu");
                return;
            }
            btnHapus.setEnabled(false);
            lblStatus.setText("Deleting...");
            new Thread(() -> {
                String pesan;
                try {
                    dao.delete(f.getId());
                    pesan = "Film berhasil dihapus";
                } catch (Exception ex) {
                    pesan = "Error: " + ex.getMessage();
                }
                String hasil = pesan;
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText(hasil);
                    btnHapus.setEnabled(true);
                    muatFilm();
                });
            }).start();
        });

        btnKembali.addActionListener(e -> {
            new Main().setVisible(true);
            dispose();
        });

        muatFilm();
        setSize(450, 200);
        setLocationRelativeTo(null);
    }

    private void muatFilm() {
        new Thread(() -> {
            try {
                List<Film> list = dao.getAll();
                SwingUtilities.invokeLater(() -> {
                    cmbFilm.removeAllItems();
                    for (Film f : list) {
                        cmbFilm.addItem(f);
                    }
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> lblStatus.setText("Error: " + ex.getMessage()));
            }
        }).start();
    }
}
