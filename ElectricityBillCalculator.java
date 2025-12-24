
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.print.PrinterException;

public class ElectricityBillCalculator extends JFrame implements ActionListener {
    JTextField nameField, idField, unitsField, rateField, dateField;
    JTextArea resultArea;
    JButton calcBtn, clearBtn, saveBtn, exitBtn;
    JComboBox<String> customerTypeBox;

    public ElectricityBillCalculator() {
        setTitle("💡 Smart Electricity Bill Calculator");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 247, 250));

        // Header
        JLabel title = new JLabel("Electricity Bill Calculator", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 0, 950, 60);
        add(title);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 15));
        inputPanel.setBounds(100, 100, 350, 300);
        inputPanel.setBackground(new Color(245, 247, 250));

        JLabel nameLabel = new JLabel("Customer Name:");
        nameField = new JTextField();

        JLabel idLabel = new JLabel("Customer ID:");
        idField = new JTextField();

        JLabel unitsLabel = new JLabel("Units Consumed:");
        unitsField = new JTextField();

        JLabel rateLabel = new JLabel("Rate per Unit (₹):");
        rateField = new JTextField();

        JLabel typeLabel = new JLabel("Customer Type:");
        String[] types = {"Domestic", "Commercial", "Industrial"};
        customerTypeBox = new JComboBox<>(types);

        JLabel dateLabel = new JLabel("Billing Date (yyyy-mm-dd):");
        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(unitsLabel);
        inputPanel.add(unitsField);
        inputPanel.add(rateLabel);
        inputPanel.add(rateField);
        inputPanel.add(typeLabel);
        inputPanel.add(customerTypeBox);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        add(inputPanel);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createTitledBorder("Bill Summary"));

        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(500, 100, 400, 350);
        add(scroll);

        // Buttons
        calcBtn = new JButton("Calculate");
        calcBtn.setBackground(new Color(0, 120, 215));
        calcBtn.setForeground(Color.WHITE);
        calcBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        clearBtn = new JButton("Clear");
        clearBtn.setBackground(new Color(255, 165, 0));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        saveBtn = new JButton("Save Bill");
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        exitBtn = new JButton("Exit");
        exitBtn.setBackground(new Color(231, 76, 60));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBounds(150, 450, 650, 50);
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.add(calcBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(exitBtn);
        add(buttonPanel);

        // Action Listeners
        calcBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        exitBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcBtn) {
            calculateBill();
        } else if (e.getSource() == clearBtn) {
            nameField.setText("");
            idField.setText("");
            unitsField.setText("");
            rateField.setText("");
            dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            resultArea.setText("");
        } else if (e.getSource() == saveBtn) {
            try {
                resultArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Error printing bill!");
            }
        } else if (e.getSource() == exitBtn) {
            System.exit(0);
        }
    }

    private void calculateBill() {
        try {
            String name = nameField.getText();
            String id = idField.getText();
            double units = Double.parseDouble(unitsField.getText());
            double rate = Double.parseDouble(rateField.getText());
            String type = (String) customerTypeBox.getSelectedItem();
            String date = dateField.getText();

            double energyCharge = units * rate;
            double fixedCharge = switch (type) {
                case "Commercial" -> 150.0;
                case "Industrial" -> 300.0;
                default -> 75.0;
            };

            double gst = 0.05 * energyCharge;
            double total = energyCharge + fixedCharge + gst;

            resultArea.setText("========================================\n");
            resultArea.append("        ELECTRICITY BILL RECEIPT        \n");
            resultArea.append("========================================\n\n");
            resultArea.append("Customer Name : " + name + "\n");
            resultArea.append("Customer ID   : " + id + "\n");
            resultArea.append("Customer Type : " + type + "\n");
            resultArea.append("Billing Date  : " + date + "\n");
            resultArea.append("Units Used    : " + units + "\n");
            resultArea.append("Rate per Unit : ₹" + rate + "\n\n");
            resultArea.append("----------------------------------------\n");
            resultArea.append(String.format("Energy Charge : ₹%.2f\n", energyCharge));
            resultArea.append(String.format("Fixed Charge  : ₹%.2f\n", fixedCharge));
            resultArea.append(String.format("GST (5%%)      : ₹%.2f\n", gst));
            resultArea.append("----------------------------------------\n");
            resultArea.append(String.format("Total Payable : ₹%.2f\n", total));
            resultArea.append("========================================\n");
            resultArea.append("Thank you for paying your bill on time!\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElectricityBillCalculator().setVisible(true));
    }
}