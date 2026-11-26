import java.awt.FlowLayout;
import javax.swing.*;

public class Main extends JFrame {
    private JButton btnAdmin = new JButton("Masuk sebagai Admin");
    private JButton btnPelanggan = new JButton("Masuk sebagai Pelanggan");

    public Main() {
        setTitle("Bioskop");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Selamat datang di Bioskop"));
        add(btnAdmin);
        add(btnPelanggan);

        btnAdmin.addActionListener(e -> {
            new HalamanAdmin().setVisible(true);
            dispose();
        });

        btnPelanggan.addActionListener(e -> {
            new HalamanPelanggan().setVisible(true);
            dispose();
        });

        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
