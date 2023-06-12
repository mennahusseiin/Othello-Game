package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private JComboBox<String> dropdownMenu;
    private JComboBox<String> dropdown1;
    private JComboBox<String> dropdown2;

    int playerType, firstAi, secondAi;

    public GameWindow(){
        // Create the dropdown menu
        dropdownMenu = new JComboBox<>();
        dropdownMenu.addItem("Human VS Human");
        dropdownMenu.addItem("Human VS AI");
        dropdownMenu.addItem("AI vs AI");

        // Create the button to open the GamePanel
        JButton openButton = new JButton("Open GamePanel");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGamePanel();
            }
        });

        // Create a panel to hold the label, dropdown menu, and button
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some spacing around the components

        // Add the label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Select Mode"), gbc);

        // Add the dropdown menu
        gbc.gridy = 1;
        panel.add(dropdownMenu, gbc);

        // Add the open button
        gbc.gridy = 2;
        panel.add(openButton, gbc);

        // Add an ActionListener to the dropdown menu
        dropdownMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dropdownMenu.getSelectedItem().equals("Human VS AI")) {
                    // Remove the additional dropdowns if they were previously added

                    if (dropdown1 != null) {
                        panel.remove(dropdown1);
                        dropdown1 = null;
                    }
                    if (dropdown2 != null) {
                        panel.remove(dropdown2);
                        dropdown2 = null;
                    }

                    // Create and add the first additional dropdown
                    gbc.gridy = 3;
                    dropdown1 = new JComboBox<>();
                    dropdown1.addItem("Easy");
                    dropdown1.addItem("Medium");
                    dropdown1.addItem("Hard");
                    panel.add(dropdown1, gbc);
                    // Repaint and revalidate the panel to reflect the changes
                    panel.revalidate();
                    panel.repaint();


                } else if (dropdownMenu.getSelectedItem().equals("AI vs AI")) {
                    // Remove the additional dropdowns if they were previously added

                    if (dropdown1 != null) {
                        panel.remove(dropdown1);
                        dropdown1 = null;
                    }
                    if (dropdown2 != null) {
                        panel.remove(dropdown2);
                        dropdown2 = null;
                    }

                    // Create and add the first additional dropdown
                    gbc.gridy = 3;
                    dropdown1 = new JComboBox<>();
                    dropdown1.addItem("Easy");
                    dropdown1.addItem("Medium");
                    dropdown1.addItem("Hard");
                    panel.add(dropdown1, gbc);

                    // Create and add the second additional dropdown
                    gbc.gridy = 4;
                    dropdown2 = new JComboBox<>();
                    dropdown2.addItem("Easy");
                    dropdown2.addItem("Medium");
                    dropdown2.addItem("Hard");
                    panel.add(dropdown2, gbc);

                    // Repaint and revalidate the panel to reflect the changes
                    panel.revalidate();
                    panel.repaint();
                } else {
                    // Remove the additional dropdowns if they were previously added

                    if (dropdown1 != null) {
                        panel.remove(dropdown1);
                        dropdown1 = null;
                    }
                    if (dropdown2 != null) {
                        panel.remove(dropdown2);
                        dropdown2 = null;
                    }

                    // Repaint and revalidate the panel to reflect the changes
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });

        this.add(panel);
        this.setTitle("AI Project");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set a preferred size for the window
        this.setPreferredSize(new Dimension(400, 400));

        // Pack the components before calculating the window's size
        this.pack();

        // Center the window horizontally
        this.setLocationRelativeTo(null);

        // Center the window vertically
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowHeight = this.getHeight();
        int windowY = (screenSize.height - windowHeight) / 2;
        this.setLocation(this.getX(), windowY);

        this.setVisible(true);
    }

    private void openGamePanel() {
        String mode = (String) dropdownMenu.getSelectedItem();

        switch (mode) {
            case "Human VS Human":
                // Code to execute if the variable is "Option 1"
                playerType = 0;
                break;
            case "Human VS AI":
                // Code to execute if the variable is "Option 2"
                playerType = 1;
                break;
            case "AI vs AI":
                // Code to execute if the variable is "Option 3"
                playerType = 2;
                break;
            default:
                // Code to execute if the variable does not match any of the cases
                playerType = 50;
                break;
        }
        if(dropdown1 != null){
            String ai1 = (String) dropdown1.getSelectedItem();
            System.out.println("First menu exist");
            switch (ai1) {
                case "Easy":
                    // Code to execute if the variable is "Option 1"
                    firstAi = 1;
                    break;
                case "Medium":
                    // Code to execute if the variable is "Option 2"
                    firstAi = 4;
                    break;
                case "Hard":
                    // Code to execute if the variable is "Option 3"
                    firstAi = 8;
                    break;
                default:
                    // Code to execute if the variable does not match any of the cases
                    firstAi = 6;
                    break;
            }
        }

        if(dropdown2 != null){
            String ai2 = (String) dropdown2.getSelectedItem();
            switch (ai2) {
                case "Easy":
                    // Code to execute if the variable is "Option 1"
                    secondAi = 1;
                    break;
                case "Medium":
                    // Code to execute if the variable is "Option 2"
                    secondAi = 4;
                    break;
                case "Hard":
                    // Code to execute if the variable is "Option 3"
                    secondAi = 8;
                    break;
                default:
                    // Code to execute if the variable does not match any of the cases
                    secondAi = 9;
                    break;
            }
        }



        // Get the selected argument from the dropdown menu

        // Create the GamePanel with the selected argument
        GamePanel gp = new GamePanel(playerType,firstAi,secondAi);

        // Open the GamePanel window
        JFrame gamePanelWindow = new JFrame();
        gamePanelWindow.add(gp);
        gamePanelWindow.setTitle("GamePanel");
        gamePanelWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Center the GamePanel window horizontally
        gamePanelWindow.setLocationRelativeTo(null);

        // Center the GamePanel window vertically
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowHeight = gamePanelWindow.getHeight();
        int windowY = (screenSize.height - windowHeight) / 2;
        gamePanelWindow.setLocation(gamePanelWindow.getX(), windowY);

        gamePanelWindow.pack();
        gamePanelWindow.setVisible(true);
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}


