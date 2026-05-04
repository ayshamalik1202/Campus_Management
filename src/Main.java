import utils.DataStore;
import views.RoleSelection;

public class Main {
    public static void main(String[] args) {
        // Initialize DataStore (loads saved data or creates fresh)
        DataStore.getInstance();

        // Launch UI on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new RoleSelection().setVisible(true);
        });
    }
}