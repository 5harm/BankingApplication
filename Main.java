public class Main {
    public static void main(String[] args) {
        // Launch the GUI on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> new GUI());
    }
}
