import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class AdministrarOrden extends JFrame {

    // Paleta Tienda de Mia y Karilyn
    private static final Color BG_CREMA    = new Color(0xF8, 0xF5, 0xF2);
    private static final Color TEXT_MAIN   = new Color(0x13, 0x13, 0x11);
    private static final Color BURDEOS     = new Color(0x64, 0x29, 0x2F);
    private static final Color TAUPE       = new Color(0xA3, 0x97, 0x7C);
    private static final Color OLIVA       = new Color(0x68, 0x6F, 0x60);
    private static final Color BORDE_CLARO = new Color(0xCE, 0xCE, 0xCE);

    // UI refs
    private JTable tbl;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    private JLabel lbId, lbFecha, lbCliente, lbItems, lbTotal, lbEstadoChip;
    private JComboBox<String> cbEstado;
    private JTextArea txtNota;

    public AdministrarOrden() {
        super("Tienda de Mia y Karilyn — Administrar pedido (maqueta)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 600);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(BG_CREMA);
        root.setBorder(new EmptyBorder(16,16,16,16));
        setContentPane(root);

        // Header
        JLabel header = new JLabel("Administrar pedido");
        header.setOpaque(true);
        header.setBackground(BURDEOS);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 20f));
        header.setBorder(new EmptyBorder(12,16,12,16));
        root.add(header, BorderLayout.NORTH);

        // Center split: left list + right detail
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.5);
        split.setDividerSize(8);
        split.setBorder(null);
        split.setOpaque(false);
        split.setLeftComponent(buildLeftList());
        split.setRightComponent(buildRightDetail());
        root.add(split, BorderLayout.CENTER);

        // Footer note
        JLabel note = new JLabel("Demo visual • sin conexión ni lógica", SwingConstants.RIGHT);
        note.setForeground(OLIVA);
        note.setBorder(new EmptyBorder(0,4,0,4));
        root.add(note, BorderLayout.SOUTH);

        // Selección inicial
        if (model.getRowCount() > 0) {
            tbl.setRowSelectionInterval(0, 0);
            updateDetailFromSelection();
        }
    }

    // ---------- Left: list & filters ----------
    private JComponent buildLeftList() {
        JPanel left = new JPanel(new BorderLayout(10,10));
        left.setOpaque(false);

        // Top bar: search + filter
        JPanel top = new JPanel(new GridBagLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(10,10,10,10)
        ));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty("JTextField.placeholderText","Buscar por cliente o #");
        stylizeField(txtSearch);

        JComboBox<String> cbFilter = new JComboBox<>(new String[]{"Todos","Pendiente","Enviado","Entregado","Cancelado"});
        cbFilter.setBackground(Color.WHITE);

        g.gridx=0; g.gridy=0; top.add(txtSearch, g);
        g.gridx=1; g.gridy=0; g.weightx=0; top.add(cbFilter, g);

        left.add(top, BorderLayout.NORTH);

        // Table
        String[] cols = {"#Pedido","Fecha","Cliente","Items","Total","Estado"};
        Object[][] data = {
              
        };
        model = new DefaultTableModel(data, cols){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        tbl = new JTable(model);
        tbl.setRowHeight(26);
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.setGridColor(BORDE_CLARO);
        tbl.getTableHeader().setReorderingAllowed(false);

        sorter = new TableRowSorter<>(model);
        tbl.setRowSorter(sorter);

        txtSearch.getDocument().addDocumentListener((SimpleDocumentListener) e -> applyFilters(txtSearch.getText(), (String) cbFilter.getSelectedItem()));
        cbFilter.addItemListener(e -> { if(e.getStateChange()==ItemEvent.SELECTED) applyFilters(txtSearch.getText(), (String) e.getItem()); });

        tbl.getSelectionModel().addListSelectionListener(e -> { if(!e.getValueIsAdjusting()) updateDetailFromSelection(); });

        left.add(new JScrollPane(tbl), BorderLayout.CENTER);
        return left;
    }

    private void applyFilters(String text, String estado) {
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                String id = entry.getStringValue(0);
                String cliente = entry.getStringValue(2);
                String est = entry.getStringValue(5);
                boolean matchText = text == null || text.isBlank() ||
                        id.toLowerCase().contains(text.toLowerCase()) ||
                        cliente.toLowerCase().contains(text.toLowerCase());
                boolean matchEstado = "Todos".equals(estado) || est.equalsIgnoreCase(estado);
                return matchText && matchEstado;
            }
        });
    }

    private JComponent buildRightDetail() {
        JPanel right = new JPanel(new BorderLayout(10,10));
        right.setOpaque(false);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(16,16,16,16)
        ));
        right.add(card, BorderLayout.CENTER);

        GridBagConstraints d = new GridBagConstraints();
        d.insets = new Insets(8,8,8,8);
        d.fill = GridBagConstraints.HORIZONTAL;
        d.weightx = 1;

        JLabel title = new JLabel("Detalle del pedido");
        title.setForeground(BURDEOS);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        d.gridx=0; d.gridy=0; d.gridwidth=2; card.add(title, d);

        lbId     = kvRow(card, d, 1, "N° Pedido:",    "—");
        lbFecha  = kvRow(card, d, 2, "Fecha:",        "—");
        lbCliente= kvRow(card, d, 3, "Cliente:",      "—");
        lbItems  = kvRow(card, d, 4, "Items:",        "—");
        lbTotal  = kvRow(card, d, 5, "Total:",        "—");

        JPanel rowEstado = new JPanel(new BorderLayout());
        rowEstado.setOpaque(false);
        JLabel lk = new JLabel("Estado:");
        lk.setForeground(TEXT_MAIN);
        lbEstadoChip = chip("Pendiente", new Color(0xCE,0xCE,0xCE), TEXT_MAIN);
        cbEstado = new JComboBox<>(new String[]{"Pendiente","Enviado","Entregado","Cancelado"});
        cbEstado.addItemListener(e -> {
            if(e.getStateChange()==ItemEvent.SELECTED){
                String val = (String) cbEstado.getSelectedItem();
                applyChipState(val);
            }
        });
        rowEstado.add(lk, BorderLayout.WEST);
        rowEstado.add(lbEstadoChip, BorderLayout.CENTER);
        rowEstado.add(cbEstado, BorderLayout.EAST);
        d.gridy=6; d.gridx=0; d.gridwidth=2; card.add(rowEstado, d);

        JLabel lnota = new JLabel("Nota:");
        lnota.setForeground(TEXT_MAIN);
        txtNota = new JTextArea(4, 20);
        txtNota.setLineWrap(true);
        txtNota.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(txtNota);
        sp.setBorder(BorderFactory.createLineBorder(BORDE_CLARO));
        d.gridy=7; d.gridwidth=1; card.add(lnota, d);
        d.gridx=1; card.add(sp, d);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnEditarPedido = primaryButton("Editar pedido");
        JButton btnEditarEnvio  = secondaryButton("Editar envío");
        JButton btnImprimir     = secondaryButton("Imprimir");
        JButton btnEliminar     = dangerButton("Eliminar");
        actions.setOpaque(false);
        actions.add(btnEditarPedido);
        actions.add(btnEditarEnvio);
        actions.add(btnImprimir);
        actions.add(btnEliminar);

        d.gridx=0; d.gridy=8; d.gridwidth=2; d.anchor=GridBagConstraints.EAST;
        card.add(actions, d);

        btnEditarPedido.addActionListener(e -> JOptionPane.showMessageDialog(this,"Demo: abriría formulario de edición del pedido.","Información",JOptionPane.INFORMATION_MESSAGE));
        btnEditarEnvio.addActionListener(e -> new DetalleEnvio().setVisible(true));
        btnImprimir.addActionListener(e -> JOptionPane.showMessageDialog(this,"Demo: vista previa de impresión.","Información",JOptionPane.INFORMATION_MESSAGE));
        btnEliminar.addActionListener(e -> {
            int i = tbl.getSelectedRow();
            if (i < 0) { JOptionPane.showMessageDialog(this,"Selecciona un pedido.","Aviso",JOptionPane.WARNING_MESSAGE); return; }
            int viewIndex = tbl.convertRowIndexToModel(i);
            String id = model.getValueAt(viewIndex,0).toString();
            int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar el pedido #"+id+"? (demo visual)","Confirmar",JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                model.removeRow(viewIndex);
                if (model.getRowCount()>0) tbl.setRowSelectionInterval(0,0);
            }
        });

        return right;
    }

    private JLabel kvRow(JPanel card, GridBagConstraints d, int row, String k, String v){
        JLabel lk = new JLabel(k);
        lk.setForeground(TEXT_MAIN);
        JLabel lv = new JLabel(v);
        lv.setForeground(TEXT_MAIN);
        d.gridwidth=1; d.gridx=0; d.gridy=row; card.add(lk, d);
        d.gridx=1; card.add(lv, d);
        return lv;
    }

    private JLabel chip(String text, Color bg, Color fg){
        JLabel c = new JLabel("  " + text + "  ", SwingConstants.CENTER);
        c.setOpaque(true);
        c.setBackground(bg);
        c.setForeground(fg);
        c.setBorder(BorderFactory.createLineBorder(BORDE_CLARO));
        return c;
    }

    private void applyChipState(String state){
        switch (state){
            case "Pendiente" -> {
                lbEstadoChip.setText("  Pendiente  ");
                lbEstadoChip.setBackground(new Color(0xCE,0xCE,0xCE));
                lbEstadoChip.setForeground(TEXT_MAIN);
            }
            case "Enviado" -> {
                lbEstadoChip.setText("  Enviado  ");
                lbEstadoChip.setBackground(OLIVA);
                lbEstadoChip.setForeground(Color.WHITE);
            }
            case "Entregado" -> {
                lbEstadoChip.setText("  Entregado  ");
                lbEstadoChip.setBackground(TAUPE);
                lbEstadoChip.setForeground(Color.WHITE);
            }
            case "Cancelado" -> {
                lbEstadoChip.setText("  Cancelado  ");
                lbEstadoChip.setBackground(BURDEOS);
                lbEstadoChip.setForeground(Color.WHITE);
            }
        }
    }

    private void updateDetailFromSelection(){
        int view = tbl.getSelectedRow();
        if (view < 0) return;
        int row = tbl.convertRowIndexToModel(view);

        lbId.setText(model.getValueAt(row,0).toString());
        lbFecha.setText(model.getValueAt(row,1).toString());
        lbCliente.setText(model.getValueAt(row,2).toString());
        lbItems.setText(model.getValueAt(row,3).toString());
        lbTotal.setText(model.getValueAt(row,4).toString());

        String estado = model.getValueAt(row,5).toString();
        cbEstado.setSelectedItem(estado);
        applyChipState(estado);

        txtNota.setText(""); 
    }

    private void stylizeField(JComponent c){
        c.setForeground(TEXT_MAIN);
        c.setBackground(Color.WHITE);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(8,10,8,10)
        ));
    }
    private JButton primaryButton(String text){
        JButton b = new JButton(text);
        b.setBackground(BURDEOS);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BURDEOS));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Color hover = BURDEOS.darker();
        b.addMouseListener(new MouseAdapter(){ @Override public void mouseEntered(MouseEvent e){ b.setBackground(hover);} @Override public void mouseExited(MouseEvent e){ b.setBackground(BURDEOS);} });
        return b;
    }
    private JButton secondaryButton(String text){
        JButton b = new JButton(text);
        b.setBackground(Color.WHITE);
        b.setForeground(BURDEOS);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BURDEOS));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
    private JButton dangerButton(String text){
        JButton b = new JButton(text);
        b.setBackground(new Color(0x8B,0x2F,0x39)); // burdeos más intenso
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BURDEOS));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // --- Small helper to avoid full DocumentListener boilerplate
    @FunctionalInterface interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
        void update(javax.swing.event.DocumentEvent e);
        @Override default void insertUpdate(javax.swing.event.DocumentEvent e){ update(e); }
        @Override default void removeUpdate(javax.swing.event.DocumentEvent e){ update(e); }
        @Override default void changedUpdate(javax.swing.event.DocumentEvent e){ update(e); }
    }

    // Ejecutar esta pantalla sola
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdministrarOrden().setVisible(true));
    }
}
