import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

public class Menu extends JFrame {

    // Paleta de Tienda de Mia y Karilyn
    private static final Color BG_CREMA    = new Color(0xF8, 0xF5, 0xF2);
    private static final Color TEXT_MAIN   = new Color(0x13, 0x13, 0x11);
    private static final Color BURDEOS     = new Color(0x64, 0x29, 0x2F);
    private static final Color TAUPE       = new Color(0xA3, 0x97, 0x7C);
    private static final Color OLIVA       = new Color(0x68, 0x6F, 0x60);
    private static final Color BORDE_CLARO = new Color(0xCE, 0xCE, 0xCE);

    public Menu() {
        super("Tienda de Mia y Karilyn — Menú principal ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 520);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_CREMA);
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(root);

        // Encabezado
        JLabel titulo = new JLabel("Tienda de Mia y Karilyn");
        JLabel subtituloJLabel = new JLabel("Bienvenido al menú principal");
    // mostrar el subtítulo debajo del título usando HTML en el mismo JLabel
        titulo.setText("<html>Tienda de Mia y Karilyn<br><small>Bienvenido al menú principal</small></html>");

        titulo.setOpaque(true);
        titulo.setBackground(BURDEOS);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setBorder(new EmptyBorder(12, 16, 12, 16));
        root.add(titulo, BorderLayout.NORTH);

        // Grid 2x2 con tarjetas/botones
        JPanel grid = new JPanel(new GridLayout(2, 2, 16, 16));
        grid.setOpaque(false);

        grid.add(menuCard("Agregar pedido", new PlusIcon(26, BURDEOS), BURDEOS));
        grid.add(menuCard("Listar pedido", new ListIcon(26, TAUPE), TAUPE));
        grid.add(menuCard("Administrar pedido", new GearIcon(26, OLIVA), OLIVA));
        grid.add(menuCard("Salir", new ExitIcon(26, BURDEOS), BORDE_CLARO)); // solo visual

        root.add(grid, BorderLayout.CENTER);

        // Pie de página opcional (solo info)
        JLabel note = new JLabel("menu", SwingConstants.RIGHT);
        note.setForeground(OLIVA);
        note.setBorder(new EmptyBorder(8, 4, 0, 4));
        root.add(note, BorderLayout.SOUTH);
    }

    // Tarjeta/botón grande con icono vectorial
    private JComponent menuCard(String text, Icon icon, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLARO),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JButton btn = new JButton(text, icon);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 17f));
        btn.setForeground(TEXT_MAIN);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Línea/acento inferior
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accent);
        accentLine.setPreferredSize(new Dimension(1, 4));

        // Hover: eleva tarjeta
        card.addMouseListener(new MouseAdapter() {
            Color shadow = new Color(0,0,0,25);
            @Override public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accent),
                        new EmptyBorder(18, 18, 18, 18)
                ));
                accentLine.setBackground(accent.darker());
                card.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDE_CLARO),
                        new EmptyBorder(18, 18, 18, 18)
                ));
                accentLine.setBackground(accent);
                card.repaint();
            }
        });

        // (Sin acción real; solo visual)
        btn.addActionListener(e -> JOptionPane.showMessageDialog(card,
                "hola \"" + text + "\".",
                "Información", JOptionPane.INFORMATION_MESSAGE));

        card.add(btn, BorderLayout.CENTER);
        card.add(accentLine, BorderLayout.SOUTH);
        return card;
    }

    // ====== Iconos vectoriales (sin imágenes) ======
    interface SimpleIcon extends Icon {}

    static class PlusIcon implements SimpleIcon {
        private final int size; private final Color color;
        PlusIcon(int size, Color color){ this.size = size; this.color = color; }
        public void paintIcon(Component c, Graphics g, int x, int y){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int m=size/2, pad=6;
            g2.drawLine(x+m, y+pad, x+m, y+size-pad);
            g2.drawLine(x+pad, y+m, x+size-pad, y+m);
            g2.dispose();
        }
        public int getIconWidth(){ return size; }
        public int getIconHeight(){ return size; }
    }

    static class ListIcon implements SimpleIcon {
        private final int size; private final Color color;
        ListIcon(int size, Color color){ this.size=size; this.color=color; }
        public void paintIcon(Component c, Graphics g, int x, int y){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int pad=6, gap=9;
            for(int i=0;i<3;i++){
                int yy=y+pad+i*gap;
                g2.drawLine(x+pad, yy, x+size-pad, yy);
            }
            g2.dispose();
        }
        public int getIconWidth(){ return size; }
        public int getIconHeight(){ return size; }
    }

    static class GearIcon implements SimpleIcon {
        private final int size; private final Color color;
        GearIcon(int size, Color color){ this.size=size; this.color=color; }
        public void paintIcon(Component c, Graphics g, int x, int y){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            int r=size/2-3, cx=x+size/2, cy=y+size/2;
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for(int i=0;i<8;i++){
                double ang=i*Math.PI/4.0;
                int rx1=cx+(int)Math.round(Math.cos(ang)*(r-2));
                int ry1=cy+(int)Math.round(Math.sin(ang)*(r-2));
                int rx2=cx+(int)Math.round(Math.cos(ang)*(r+2));
                int ry2=cy+(int)Math.round(Math.sin(ang)*(r+2));
                g2.drawLine(rx1, ry1, rx2, ry2);
            }
            g2.drawOval(cx-r, cy-r, 2*r, 2*r);
            g2.drawOval(cx-3, cy-3, 6, 6);
            g2.dispose();
        }
        public int getIconWidth(){ return size; }
        public int getIconHeight(){ return size; }
    }

    static class ExitIcon implements SimpleIcon {
        private final int size; private final Color color;
        ExitIcon(int size, Color color){ this.size=size; this.color=color; }
        public void paintIcon(Component c, Graphics g, int x, int y){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            // puerta
            g2.drawRoundRect(x+4, y+4, size-14, size-8, 4, 4);
            // flecha
            Path2D p=new Path2D.Double();
            p.moveTo(x+size-5, y+size/2.0);
            p.lineTo(x+size-13, y+size/2.0);
            p.moveTo(x+size-9, y+size/2.0);
            p.lineTo(x+size-12, y+size/2.0-3);
            p.moveTo(x+size-9, y+size/2.0);
            p.lineTo(x+size-12, y+size/2.0+3);
            g2.draw(p);
            g2.dispose();
        }
        public int getIconWidth(){ return size; }
        public int getIconHeight(){ return size; }
    }

    // Ejecutar solo este archivo (demo visual)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu().setVisible(true));
    }
}
