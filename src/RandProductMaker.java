import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, idField, costField, countField;
    private JButton addButton;
    private RandomAccessFile randomAccessFile;
    private int recordCount = 0;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setLayout(new GridLayout(6, 2));

        // Fields and labels
        add(new JLabel("Product Name (max 35):"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Description (max 75):"));
        descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("ID (max 6):"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Cost:"));
        costField = new JTextField();
        add(costField);

        add(new JLabel("Record Count:"));
        countField = new JTextField("0");
        countField.setEditable(false);
        add(countField);

        // Add button
        addButton = new JButton("Add Product");
        add(addButton);

        // Button action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addProduct();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error writing to file.");
                }
            }
        });

        // Open RandomAccessFile
        try {
            randomAccessFile = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open file.");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void addProduct() throws IOException {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String id = idField.getText();
        double cost = Double.parseDouble(costField.getText());

        if (name.isEmpty() || description.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
            return;
        }

        Product product = new Product(name, description, id, cost);
        randomAccessFile.seek(recordCount * 128); // Each record is fixed-size
        randomAccessFile.writeBytes(product.getName());
        randomAccessFile.writeBytes(product.getDescription());
        randomAccessFile.writeBytes(product.getId());
        randomAccessFile.writeDouble(product.getCost());

        recordCount++;
        countField.setText(String.valueOf(recordCount));

        // Clear fields
        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");
        costField.setText("");
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
