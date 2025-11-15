import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DetalleEnvio extends JFrame {

    // Paleta Tienda de Mia y Karilyn
    private static final Color BG_CREMA    = new Color(0xF8, 0xF5, 0xF2);
    private static final Color TEXT_MAIN   = new Color(0x13, 0x13, 0x11);
    private static final Color BURDEOS     = new Color(0x64, 0x29, 0x2F);
    private static final Color TAUPE       = new Color(0xA3, 0x97, 0x7C);
    private static final Color OLIVA       = new Color(0x68, 0x6F, 0x60);
    private static final Color BORDE_CLARO = new Color(0xCE, 0xCE, 0xCE);

    public DetalleEnvio() {
        super("Tienda de Mia y Karilyn ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(BG_CREMA);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        JLabel header = new JLabel("Detalle de envío");
        header.setOpaque(true);
        header.setBackground(BURDEOS);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 20f));
        header.setBorder(new EmptyBorder(12, 16, 12, 16));
        root.add(header, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(16, 16, 16, 16)
        ));
        root.add(card, BorderLayout.CENTER);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        Font fLabel = new Font("SansSerif", Font.BOLD, 14);
        Font fField = new Font("SansSerif", Font.PLAIN, 13);

        int r = 0;
        addLabel(card, gc, 0, r, "Numero Guia*", fLabel);
        addField(card, gc, 1, r++, new JTextField(16), fField);


        addLabel(card, gc, 0, r, "Cantón *", fLabel);
        addField(card, gc, 1, r++, new JComboBox<>(new String[]{
                "Liberia","Abangares","Bagaces","Cañas","Carrillo","Hojancha","La Cruz","Nandayure","Nicoya","Santa Cruz","Tilarán"
        }), fField);

        addLabel(card, gc, 0, r, "Distrito *", fLabel);
        addField(card, gc, 1, r++, new JComboBox<>(new String[]{
                "Las Juntas","Bagaces","Cañas","Filadelfia","Hojancha","Liberia","Nicoya","La Cruz","Carmona","Santa Cruz","Tilarán"
        }), fField);

        addLabel(card, gc, 0, r, "Estado *", fLabel);
        addField(card, gc, 1, r++, new JComboBox<>(new String[]{
                "Pendiente","Preparación","En ruta","Entregado","Cancelado"
        }), fField);

     
        // Botonera
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnGuardar = primaryButton("Guardar");
        JButton btnCancelar = secondaryButton("Cancelar");
        JButton btnVolver = textButton("Volver");
        btns.setOpaque(false);
        btns.add(btnGuardar); btns.add(btnCancelar); btns.add(btnVolver);

        gc.gridx = 0; gc.gridy = r; gc.gridwidth = 2; gc.anchor = GridBagConstraints.EAST;
        card.add(btns, gc);

        // Acciones “demo” (solo visual)
        btnGuardar.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Demo visual: envío guardado.\nAquí finaliza el proceso.", "Información",
                JOptionPane.INFORMATION_MESSAGE));
        btnCancelar.addActionListener(e -> dispose());
        btnVolver.addActionListener(e -> dispose());

        // Pie
        JLabel note = new JLabel("Demo visual • sin conexión ni lógica", SwingConstants.RIGHT);
        note.setForeground(OLIVA);
        note.setBorder(new EmptyBorder(6, 4, 0, 4));
        root.add(note, BorderLayout.SOUTH);
    }

    private void addLabel(JPanel p, GridBagConstraints gc, int x, int y, String text, Font f) {
        gc.gridx = x; gc.gridy = y; gc.gridwidth = 1; gc.weightx = 0;
        JLabel l = new JLabel(text);
        l.setFont(f);
        l.setForeground(BURDEOS);
        p.add(l, gc);
    }
    private void addField(JPanel p, GridBagConstraints gc, int x, int y, JComponent field, Font f) {
        gc.gridx = x; gc.gridy = y; gc.gridwidth = 1; gc.weightx = 1;
        stylizeField(field, f);
        p.add(field, gc);
    }
    private void stylizeField(JComponent c, Font f) {
        c.setFont(f);
        c.setForeground(TEXT_MAIN);
        c.setBackground(Color.WHITE);
        if (!(c instanceof JComboBox)) {
            c.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDE_CLARO),
                    new EmptyBorder(8, 10, 8, 10)
            ));
        }
    }

    private JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BURDEOS);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BURDEOS));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Color hover = BURDEOS.darker();
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(BURDEOS); }
        });
        return b;
    }
    private JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(Color.WHITE);
        b.setForeground(BURDEOS);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BURDEOS));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
    private JButton textButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        b.setContentAreaFilled(false);
        b.setForeground(OLIVA);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // Ejecutar sola si quieres probar esta pantalla
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DetalleEnvio().setVisible(true));
    }
}
