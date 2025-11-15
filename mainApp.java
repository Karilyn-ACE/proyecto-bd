import javax.swing.SwingUtilities;

public class mainApp  {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
