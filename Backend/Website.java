package Backend;

import java.awt.*;
import javax.swing.*;

public class Website extends JFrame {

    private JButton textButton;
    private JButton textSaveButton;

    public Website() {
        setTitle("Manhattan Tourist Map");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel titleLabel = new JLabel("Manhattan Tourist Map", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        textButton = new JButton("Text - Submit Comment");
        textButton.setFont(new Font("Arial", Font.PLAIN, 16));
        textButton.addActionListener(e -> openTextFrame());
        add(textButton);

        textSaveButton = new JButton("TextSave - Save Comment");
        textSaveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        textSaveButton.addActionListener(e -> openTextSaveFrame());
        add(textSaveButton);

        setVisible(true);
    }

    private void openTextFrame() {
        JFrame textFrame = new JFrame("Text - Submit Comment");
        textFrame.setSize(500, 400);
        textFrame.setLocationRelativeTo(null);
        textFrame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField     = new JTextField();
        JTextField locationField = new JTextField();
        JTextField latField      = new JTextField();
        JTextField lonField      = new JTextField();
        JTextField ratingField   = new JTextField();
        JTextArea  commentArea   = new JTextArea(5, 20);

        inputPanel.add(new JLabel("Your Name:"));      inputPanel.add(nameField);
        inputPanel.add(new JLabel("Location Name:"));  inputPanel.add(locationField);
        inputPanel.add(new JLabel("Latitude:"));       inputPanel.add(latField);
        inputPanel.add(new JLabel("Longitude:"));      inputPanel.add(lonField);
        inputPanel.add(new JLabel("Star Rating (1-5):")); inputPanel.add(ratingField);
        inputPanel.add(new JLabel("Comment:"));
        inputPanel.add(new JScrollPane(commentArea));

        JButton submitButton = new JButton("Submit Comment");
        submitButton.addActionListener(e -> {
            try {
                Text text = new Text(
                    commentArea.getText(),
                    Double.parseDouble(latField.getText()),
                    Double.parseDouble(lonField.getText()),
                    nameField.getText(),
                    Integer.parseInt(ratingField.getText())
                );
                text.setLocationName(locationField.getText());
                JOptionPane.showMessageDialog(textFrame,
                    "Submitted!\nLocation: " + text.getFormattedLocation()
                    + "\nRating: " + text.getStarRating() + " stars",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(textFrame,
                    "Please enter valid numbers for coordinates and rating!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        textFrame.add(inputPanel, BorderLayout.CENTER);
        textFrame.add(buttonPanel, BorderLayout.SOUTH);
        textFrame.setVisible(true);
    }

    private void openTextSaveFrame() {
        JFrame frame = new JFrame("TextSave - Save Comment");
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField     = new JTextField();
        JTextField locationField = new JTextField();
        JTextField latField      = new JTextField();
        JTextField lonField      = new JTextField();
        JTextField ratingField   = new JTextField();
        JTextArea  commentArea   = new JTextArea(5, 20);

        inputPanel.add(new JLabel("Your Name:"));      inputPanel.add(nameField);
        inputPanel.add(new JLabel("Location Name:"));  inputPanel.add(locationField);
        inputPanel.add(new JLabel("Latitude:"));       inputPanel.add(latField);
        inputPanel.add(new JLabel("Longitude:"));      inputPanel.add(lonField);
        inputPanel.add(new JLabel("Star Rating (1-5):")); inputPanel.add(ratingField);
        inputPanel.add(new JLabel("Comment:"));
        inputPanel.add(new JScrollPane(commentArea));

        JButton saveButton = new JButton("Save Comment to File");
        saveButton.addActionListener(e -> {
            try {
                TextSave ts = new TextSave(
                    commentArea.getText(),
                    Double.parseDouble(latField.getText()),
                    Double.parseDouble(lonField.getText()),
                    nameField.getText(),
                    Integer.parseInt(ratingField.getText())
                );
                ts.setLocationName(locationField.getText());
                ts.setId(System.currentTimeMillis());
                if (ts.save()) {
                    JOptionPane.showMessageDialog(frame,
                        "Saved!\nLocation: " + ts.getFormattedLocation()
                        + "\nFile: " + ts.getFilePath(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                    "Please enter valid numbers for coordinates and rating!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Website::new);
    }
}