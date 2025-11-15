import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

    private static final Color BG_CREMA    = new Color(0xF8, 0xF5, 0xF2);
    private static final Color TEXT_MAIN   = new Color(0x13, 0x13, 0x11);
    private static final Color BURDEOS     = new Color(0x64, 0x29, 0x2F);
    private static final Color TAUPE       = new Color(0xA3, 0x97, 0x7C);
    private static final Color OLIVA       = new Color(0x68, 0x6F, 0x60);
    private static final Color BORDE_CLARO = new Color(0xCE, 0xCE, 0xCE);

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnLogin;
    private JLabel lblNoUsuario;

    public Login() {
        super("Tienda de Mia y Karilyn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 420);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BG_CREMA);
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(root);

        JLabel titulo = new JLabel("Login");
        titulo.setOpaque(true);
        titulo.setBackground(BURDEOS);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setBorder(new EmptyBorder(12, 14, 12, 14));
        titulo.setPreferredSize(new Dimension(420, 50));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(true);
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(20, 20, 20, 20)
        ));
        form.setPreferredSize(new Dimension(420, 260));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 8, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        Font fLabel  = new Font("SansSerif", Font.BOLD, 15);
        Font fField  = new Font("SansSerif", Font.PLAIN, 14);
        Font fLink   = new Font("SansSerif", Font.PLAIN, 13);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(fLabel);
        lblUsuario.setForeground(BURDEOS);

        txtUsuario = new JTextField(20);
        txtUsuario.setFont(fField);
        txtUsuario.setBackground(Color.WHITE);
        txtUsuario.setForeground(TEXT_MAIN);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setFont(fLabel);
        lblClave.setForeground(BURDEOS);

        txtClave = new JPasswordField(20);
        txtClave.setFont(fField);
        txtClave.setBackground(Color.WHITE);
        txtClave.setForeground(TEXT_MAIN);
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(10, 12, 10, 12)
        ));

        btnLogin = new JButton("Iniciar sesión");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnLogin.setBackground(TAUPE);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createLineBorder(BURDEOS));

        Color TAUPE_HOVER = TAUPE.darker();
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnLogin.setBackground(TAUPE_HOVER); }
            @Override public void mouseExited(MouseEvent e)  { btnLogin.setBackground(TAUPE); }
        });
        btnLogin.addActionListener(e -> JOptionPane.showMessageDialog(
                Login.this,
                "Demo visual: aquí iría la validación.\n(En esta fase no hay lógica)",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        ));

        lblNoUsuario = new JLabel("¿No tienes un usuario?");
        lblNoUsuario.setFont(fLink);
        lblNoUsuario.setForeground(BURDEOS);
        lblNoUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblNoUsuario.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { lblNoUsuario.setForeground(OLIVA); }
            @Override public void mouseExited(MouseEvent e)  { lblNoUsuario.setForeground(BURDEOS); }
            @Override public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(Login.this,
                        "Demo visual: aquí abriría el registro.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        GridBagConstraints gf = new GridBagConstraints();
        gf.insets = new Insets(10, 8, 10, 8);
        gf.fill = GridBagConstraints.HORIZONTAL;
        gf.weightx = 1;

        int r = 0;
        gf.gridx = 0; gf.gridy = r; gf.anchor = GridBagConstraints.WEST;
        form.add(lblUsuario, gf);
        gf.gridx = 1; gf.gridy = r;
        form.add(txtUsuario, gf);
        r++;

        gf.gridx = 0; gf.gridy = r;
        form.add(lblClave, gf);
        gf.gridx = 1; gf.gridy = r;
        form.add(txtClave, gf);
        r++;

        gf.gridx = 0; gf.gridy = r; gf.gridwidth = 2; gf.anchor = GridBagConstraints.CENTER;
        form.add(btnLogin, gf);
        r++;

        gf.gridx = 0; gf.gridy = r; gf.gridwidth = 2; gf.anchor = GridBagConstraints.CENTER;
        form.add(lblNoUsuario, gf);

        g.gridx = 0; g.gridy = 0; g.anchor = GridBagConstraints.NORTH;
        root.add(titulo, g);
        g.gridy = 1; g.anchor = GridBagConstraints.CENTER;
        root.add(form, g);
    }

    
}
