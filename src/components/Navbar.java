package components;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class Navbar extends JComponent {
    
    public Navbar() {
    this(null);
    }

    public Navbar(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        initComponents();
    }

    private void initComponents() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);

        for (int i = 0; i < items.length; i++){
                addItem(items[i], i);
        }
    }

    public void addItem(String name, int index) {
        item menuItem = new item(name, index, false);

        menuItem.addActionListener((ActionEvent ae) -> {
            navigateToPage(index);
        });
        add(menuItem);
        revalidate();
        repaint();
    }

    private void navigateToPage(int index) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        String pageName = getPageName(index);
        cardLayout.show(mainPanel, pageName);
    }

    private String getPageName(int index) {
        return switch (index) {
            case 0 -> "HomePage";
            case 1 -> "WarehousePage";
            case 2 -> "InventoryPage";
            default -> "HomePage";
        }; // Default page
    }
//Variable Declaration
        private MigLayout layout;
private String[] items = new String[] {
    "Home",
    "Warehouse",
    "Inventory"
};

    private JPanel mainPanel; // This will hold the different pages (e.g., using CardLayout)
}