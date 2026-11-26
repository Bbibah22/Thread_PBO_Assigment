import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;

public class HalamanPelanggan extends JFrame {
    private JComboBox<Film> cmbFilm = new JComboBox<>();
    private JComboBox<String> cmbJam = new JComboBox<>(
            new String[] { "10:00", "13:00", "16:00", "19:00", "21:30" });
    private JSpinner spnJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    private JButton btnCheckout = new JButton("Beli Tiket");
    private JButton btnReset = new JButton("Reset");
    private JButton btnKembali = new JButton("Kembali");
    private JLabel lblStatus = new JLabel("Siap");
    private FilmDAO dao = new FilmDAO();

    public HalamanPelanggan() {
        setTitle("Pelanggan Bioskop");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Pilih Film"));
        add(cmbFilm);
        add(new JLabel("Jam Tayang"));
        add(cmbJam);
        add(new JLabel("Jumlah Tiket"));
        add(spnJumlah);
        add(btnCheckout);
        add(btnReset);
        add(btnKembali);
        add(lblStatus);

        btnCheckout.addActionListener(e -> {
            Film f = (Film) cmbFilm.getSelectedItem();
            if (f == null) {
                lblStatus.setText("Belum ada film");
                return;
            }
            String jam = (String) cmbJam.getSelectedItem();
            int jumlah = (Integer) spnJumlah.getValue();
            int total = f.getHarga() * jumlah;
            btnCheckout.setEnabled(false);
            lblStatus.setText("Processing...");
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                }
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Berhasil: " + f.getJudul() + " jam " + jam
                            + ", " + jumlah + " tiket, total Rp" + total);
                    btnCheckout.setEnabled(true);
                });
            }).start();
        });

        btnReset.addActionListener(e -> {
            lblStatus.setText("Siap");
            spnJumlah.setValue(1);
            muatFilm();
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
