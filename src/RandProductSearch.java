import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;

    public RandProductSearch() {
        setTitle("Product Search");
        setLayout(new BorderLayout());

        // Search input
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Product Name:"));
        searchField = new JTextField(20);
        inputPanel.add(searchField);
        searchButton = new JButton("Search");
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        // Result area
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void searchProducts() {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product name!");
            return;
        }

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("products.dat", "r")) {
            resultArea.setText("");
            int recordSize = 128;
            long fileLength = randomAccessFile.length();
            for (int i = 0; i < fileLength / recordSize; i++) {
                randomAccessFile.seek(i * recordSize);
                byte[] nameBytes = new byte[35];
                randomAccessFile.read(nameBytes);
                String name = new String(nameBytes).trim();
                if (name.contains(searchQuery)) {
                    byte[] descriptionBytes = new byte[75];
                    randomAccessFile.read(descriptionBytes);
                    String description = new String(descriptionBytes).trim();
                    byte[] idBytes = new byte[6];
                    randomAccessFile.read(idBytes);
                    String id = new String(idBytes).trim();
                    double cost = randomAccessFile.readDouble();
                    resultArea.append(String.format("Name: %s, Description: %s, ID: %s, Cost: %.2f%n", name, description, id, cost));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
