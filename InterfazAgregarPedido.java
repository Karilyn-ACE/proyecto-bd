import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class InterfazAgregarPedido extends JFrame {

    // ====== MODO COMPACTO (ajusta aquí si quieres aún más/menos compacto) ======
    private static final int INSET = 6;        // espacio entre celdas
    private static final int PAD   = 6;        // padding interno de campos
    private static final int GAP_H = 12;       // separación entre columnas
    private static final int RESUMEN_W = 230;  // ancho del panel de resumen

    private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FONT_FIELD = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_SEC   = new Font("SansSerif", Font.BOLD, 14);
    private static final Font FONT_HDR   = new Font("SansSerif", Font.BOLD, 18);

    // Paleta
    private static final Color BG_CREMA    = new Color(0xF8, 0xF5, 0xF2);
    private static final Color TEXT_MAIN   = new Color(0x13, 0x13, 0x11);
    private static final Color BURDEOS     = new Color(0x64, 0x29, 0x2F);
    private static final Color OLIVA       = new Color(0x68, 0x6F, 0x60);
    private static final Color BORDE_CLARO = new Color(0xCE, 0xCE, 0xCE);

    // Precios por categoría
    private static final Map<String, Integer> CATEGORY_PRICE = new LinkedHashMap<>();
    static {
        CATEGORY_PRICE.put("Labiales", 5000);
        CATEGORY_PRICE.put("Bases",   12000);
        CATEGORY_PRICE.put("Sombras",  8000);
        CATEGORY_PRICE.put("Brochas",  4000);
    }

    private JComboBox<String> cbCategoria;
    private JTextField txtPrecioUnitario;

    public InterfazAgregarPedido() {
        super("Tienda de Mia y Karilyn — Agregar pedido (maqueta)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 680);                // cabe en 1366x768
        setMinimumSize(new Dimension(920, 600));
        setLocationRelativeTo(null);

        // Tipografías base (compactas)
        UIManager.put("Label.font", FONT_LABEL);
        UIManager.put("TextField.font", FONT_FIELD);
        UIManager.put("ComboBox.font", FONT_FIELD);
        UIManager.put("Spinner.font", FONT_FIELD);
        UIManager.put("Button.font",  new Font("SansSerif", Font.BOLD, 12));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_CREMA);
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        JLabel header = new JLabel("Agregar pedido");
        header.setOpaque(true);
        header.setBackground(BURDEOS);
        header.setForeground(Color.WHITE);
        header.setFont(FONT_HDR);
        header.setBorder(new EmptyBorder(10, 14, 10, 14));
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(GAP_H, 0));
        center.setOpaque(false);
        root.add(center, BorderLayout.CENTER);

        // ===== FORM (izquierda) =====
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(12, 12, 12, 12)
        ));
        center.add(formCard, BorderLayout.CENTER);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(INSET, INSET, INSET, INSET);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        // Columna izquierda
        JPanel leftCols = new JPanel(new GridBagLayout());
        leftCols.setOpaque(false);
        GridBagConstraints gl = new GridBagConstraints();
        gl.insets = new Insets(INSET, INSET, 0, INSET);
        gl.fill = GridBagConstraints.HORIZONTAL;
        gl.weightx = 1;
        gl.anchor = GridBagConstraints.WEST;

        int yl = 0;
        yl = section(leftCols, gl, yl, "Datos de identificación");
        addRow(leftCols, gl, yl++, "Nombre 1 *", new JTextField(16));
        addRow(leftCols, gl, yl++, "Nombre 2",   new JTextField(16));
        addRow(leftCols, gl, yl++, "Apellido 1 *", new JTextField(16));
        addRow(leftCols, gl, yl++, "Apellido 2",   new JTextField(16));

        yl = section(leftCols, gl, yl, "Datos de contacto");
        addRow(leftCols, gl, yl++, "Teléfono *", new JTextField(16));
        addRow(leftCols, gl, yl++, "Correo *",   new JTextField(16));

        yl = section(leftCols, gl, yl, "Pago y fecha");
        addRow(leftCols, gl, yl++, "Fecha *", new JTextField("dd/mm/aaaa", 16));
        addRow(leftCols, gl, yl++, "Método de pago", new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "SINPE"}));

        // Monta izquierda en el form
        gc.gridx = 0; gc.gridy = 0;
        gc.gridwidth = 2;
        formCard.add(leftCols, gc);

        // Columna derecha
        JPanel rightCols = new JPanel(new GridBagLayout());
        rightCols.setOpaque(false);
        GridBagConstraints gr = new GridBagConstraints();
        gr.insets = new Insets(INSET, INSET, INSET, INSET);
        gr.fill = GridBagConstraints.HORIZONTAL;
        gr.weightx = 1;
        gr.anchor = GridBagConstraints.WEST;

        int yr = 0;
        cbCategoria = new JComboBox<>(CATEGORY_PRICE.keySet().toArray(new String[0]));
        addRow(rightCols, gr, yr++, "Categoría *", cbCategoria);
        addRow(rightCols, gr, yr++, "Tono/Variante", new JTextField(16));
        addRow(rightCols, gr, yr++, "Cantidad *", new JSpinner(new SpinnerNumberModel(1,1,999,1)));

        txtPrecioUnitario = new JTextField(16);
        txtPrecioUnitario.setEditable(false);
        txtPrecioUnitario.setBackground(new Color(0xF5,0xF5,0xF5));
        addRow(rightCols, gr, yr++, "Precio unitario (auto)", txtPrecioUnitario);

        addRow(rightCols, gr, yr++, "Descuento", new JComboBox<>(new String[]{"0%","5%","10%","15%","20%"}));
        addRow(rightCols, gr, yr++, "Notas", new JTextField(16));

        cbCategoria.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Integer p = CATEGORY_PRICE.get(e.getItem().toString());
                txtPrecioUnitario.setText(p != null ? String.valueOf(p) : "");
            }
        });
        if (cbCategoria.getItemCount() > 0) cbCategoria.setSelectedIndex(0);

        // Inserta derecha
        gc.gridx = 2; gc.gridy = 0;
        gc.gridwidth = 1;
        formCard.add(rightCols, gc);

        // Filler mínimo (empuja barra de botones sin generar scroll)
        gc.gridx = 0; gc.gridy = 1;
        gc.gridwidth = 3; gc.weighty = 1; gc.fill = GridBagConstraints.BOTH;
        JPanel filler = new JPanel(); filler.setOpaque(false);
        formCard.add(filler, gc);

        // Botonera
        JPanel btnsBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        btnsBar.setOpaque(true);
        btnsBar.setBackground(new Color(0xFA, 0xF8, 0xF8));
        btnsBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE_CLARO));

        JButton btnGuardar = primaryButton("Guardar");
        JButton btnLimpiar = outlinedButton("Limpiar");
        JButton btnVolver  = ghostButton("Volver");

        btnsBar.add(btnGuardar);
        btnsBar.add(btnLimpiar);
        btnsBar.add(btnVolver);

        gc.gridx = 0; gc.gridy = 2;
        gc.gridwidth = 3; gc.weighty = 0; gc.fill = GridBagConstraints.HORIZONTAL;
        formCard.add(btnsBar, gc);

        // ===== RESUMEN (derecha, fijo y compacto) =====
        JPanel resumen = new JPanel(new GridBagLayout());
        resumen.setBackground(Color.WHITE);
        resumen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(12, 12, 12, 12)
        ));
        resumen.setPreferredSize(new Dimension(RESUMEN_W, 0));
        center.add(resumen, BorderLayout.EAST);

        GridBagConstraints rs = new GridBagConstraints();
        rs.insets = new Insets(4, 4, 4, 4);
        rs.fill = GridBagConstraints.HORIZONTAL;
        rs.weightx = 1;

        JLabel rTitle = new JLabel("Resumen");
        rTitle.setForeground(BURDEOS);
        rTitle.setFont(FONT_SEC);
        rs.gridx = 0; rs.gridy = 0;
        resumen.add(rTitle, rs);

        rs.gridy++;
        resumen.add(new JSeparator(), rs);

        rs.gridy++; resumen.add(rowKV("Items:", "—"), rs);
        rs.gridy++; resumen.add(rowKV("Subtotal:", "₡0.00"), rs);
        rs.gridy++; resumen.add(rowKV("Impuestos:", "₡0.00"), rs);
        rs.gridy++; resumen.add(rowKV("Descuento:", "₡0.00"), rs);

        rs.gridy++;
        JPanel totalRow = rowKV("Total:", "₡0.00");
        totalRow.setBorder(new EmptyBorder(4,0,0,0));
        resumen.add(totalRow, rs);

        rs.gridy++;
        JButton btnMockRecalc = outlinedButton("Recalcular");
        btnMockRecalc.setPreferredSize(new Dimension(160, 30));
        rs.anchor = GridBagConstraints.CENTER;
        resumen.add(btnMockRecalc, rs);

        // Pie
        JLabel note = new JLabel("interfaz", SwingConstants.RIGHT);
        note.setForeground(OLIVA);
        note.setBorder(new EmptyBorder(6, 4, 0, 4));
        root.add(note, BorderLayout.SOUTH);
    }

    // ===== Helpers compactos =====
    private int section(JPanel p, GridBagConstraints g, int y, String title) {
        g.gridx = 0; g.gridy = y; g.gridwidth = 2;
        JLabel t = new JLabel(title);
        t.setForeground(BURDEOS);
        t.setFont(FONT_SEC);
        p.add(t, g);

        g.gridy = ++y;
        p.add(new JSeparator(), g);
        return y + 1;
    }

    private void addRow(JPanel p, GridBagConstraints g, int y, String label, JComponent field) {
        // etiqueta
        g.gridx = 0; g.gridy = y; g.weightx = 0; g.gridwidth = 1;
        JLabel l = new JLabel(label);
        l.setFont(FONT_LABEL);
        l.setForeground(BURDEOS);
        p.add(l, g);

        // campo
        g.gridx = 1; g.gridy = y; g.weightx = 1; g.gridwidth = 1;
        stylizeField(field, FONT_FIELD);
        p.add(field, g);
    }

    private void stylizeField(JComponent c, Font f) {
        c.setFont(f);
        c.setForeground(TEXT_MAIN);
        c.setBackground(Color.WHITE);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(PAD, PAD+2, PAD, PAD+2)
        ));
    }

    private JPanel rowKV(String k, String v) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel lk = new JLabel(k);
        JLabel lv = new JLabel(v, SwingConstants.RIGHT);
        lk.setForeground(TEXT_MAIN);
        lv.setForeground(TEXT_MAIN);
        lk.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lv.setFont(new Font("SansSerif", Font.BOLD, 13));
        row.add(lk, BorderLayout.WEST);
        row.add(lv, BorderLayout.EAST);
        return row;
    }

    // ===== Botones (compactos) =====
    private JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(BURDEOS);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setMargin(new Insets(6, 14, 6, 14));
        b.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(BURDEOS.darker(), 1, true),
                new EmptyBorder(1, 4, 1, 4)
        ));
        Color hover = BURDEOS.darker();
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(BURDEOS); }
        });
        return b;
    }

    private JButton outlinedButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setBackground(Color.WHITE);
        b.setForeground(BURDEOS);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setMargin(new Insets(6, 14, 6, 14));
        b.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(BURDEOS, 1, true),
                new EmptyBorder(1, 4, 1, 4)
        ));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(new Color(0xFB, 0xF8, 0xF8)); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(Color.WHITE); }
        });
        return b;
    }

    private JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setForeground(OLIVA);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                b.setForeground(BURDEOS);
                b.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BURDEOS));
            }
            @Override public void mouseExited(MouseEvent e)  {
                b.setForeground(OLIVA);
                b.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
            }
        });
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazAgregarPedido().setVisible(true));
    }
}
