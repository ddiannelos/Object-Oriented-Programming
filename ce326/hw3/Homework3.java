package ce326.hw3;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.awt.*;


public class Homework3 {
    private JFrame frame;
    private JMenuItem easy, intermediate, expert;
    private JMenu newGame;
    private JMenuBar bar;
    private JPanel outerCenterPanel;
    private JPanel[][] innerCenterPanels = new JPanel[3][3];
    private JButton[][] sudokuButtons = new JButton[9][9];
    private JPanel bottomPanel;
    private JButton[] numButtons = new JButton[9];
    private JButton removeButton, undoButton, solveButton;
    private JCheckBox checkBox;
    private Boolean[][] initializedButtons = new Boolean[9][9];
    private Color yellowRGB = new Color(255,255,200);
    private JButton idleButton = new JButton();
    private JButton chosenSudokuButton = idleButton;
    
    public Homework3() {
        // Create frame
        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(700, 700);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(null);
         
        // Create MenuItems easy, intermediate, expert
        easy = new JMenuItem("Easy");
        easy.setMnemonic(KeyEvent.VK_E);
        intermediate = new JMenuItem("Intermediate");
        intermediate.setMnemonic(KeyEvent.VK_I);
        expert = new JMenuItem("Expert");
        expert.setMnemonic(KeyEvent.VK_X);
        
        easy.addActionListener(newGameListener);
        intermediate.addActionListener(newGameListener);
        expert.addActionListener(newGameListener);
        
        // Create Menu
        newGame = new JMenu("New Game");
        newGame.setMnemonic(KeyEvent.VK_G);
        newGame.add(easy);
        newGame.add(intermediate);
        newGame.add(expert);
        
        // Create MenuBar
        bar = new JMenuBar();
        bar.add(newGame);
        
        // Create OuterPanel for the sudoku grid
        outerCenterPanel = new JPanel();
        outerCenterPanel.setBackground(Color.BLACK);
        outerCenterPanel.setBounds(0,50,700,500);
        outerCenterPanel.setLayout(new GridLayout(3,3,2,2));
        
        // Create InnerPlanels for the sudoku grid
        for (int i = 0; i < innerCenterPanels.length; i++) {
            for (int j = 0; j < innerCenterPanels[i].length; j++) {
                innerCenterPanels[i][j] = new JPanel();
                innerCenterPanels[i][j].setLayout(new GridLayout(3,3,0,0));
                innerCenterPanels[i][j].setBackground(Color.BLACK);
                outerCenterPanel.add(innerCenterPanels[i][j]);
            }
        }

        // Create Buttons for the sudoku grid
        int l = 0, k = 0;

        for (int i = 0; i < sudokuButtons.length; i++) {            
            if (i%3 == 0 && i != 0) {
                k++;
            }
            l = 0;
            for (int j = 0; j < sudokuButtons[i].length; j++) {
                sudokuButtons[i][j] = new JButton();
                sudokuButtons[i][j].setFocusable(false);
                sudokuButtons[i][j].setBackground(Color.WHITE);
                sudokuButtons[i][j].setText("");
                sudokuButtons[i][j].addActionListener(sudokuButtonsListener);
                initializedButtons[i][j] = false;

                if (j%3 == 0 && j != 0) {
                    l++;
                    if (l == 3) {
                        l = 0;
                    }
                }
                
                innerCenterPanels[k][l].add(sudokuButtons[i][j]);
            }
        }
   
        // Create BottomPanel
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBounds(0,550,700,150);
        bottomPanel.setLayout(new FlowLayout());
        
        // Create Buttons for the BottomPanel
        for (int i = 0; i < numButtons.length; i++) {
            numButtons[i] = new JButton(String.valueOf(i+1));
            numButtons[i].setFocusable(false);
            numButtons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                          put(KeyStroke.getKeyStroke(String.valueOf(i+1)), 
                                                     String.valueOf(i+1));
            numButtons[i].getActionMap().
                          put(String.valueOf(i+1), numberButtonsAction);
            numButtons[i].addActionListener(numberButtonsAction);
            bottomPanel.add(numButtons[i]);
        }
        
        removeButton = new JButton("remove");
        // need icon
        removeButton.setFocusable(false);
        removeButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                     put(KeyStroke.getKeyStroke("BACK_SPACE"), "remove");
        removeButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                     put(KeyStroke.getKeyStroke("DELETE"), "remove");
        removeButton.getActionMap().put("remove", optionButtonsAction);
        removeButton.addActionListener(optionButtonsAction);
        bottomPanel.add(removeButton);
        
        undoButton = new JButton("undo");
        //need icon
        undoButton.setFocusable(false);
        bottomPanel.add(undoButton);
        
        checkBox = new JCheckBox();
        checkBox.setText("Verify your solution");
        checkBox.setFocusable(false);
        checkBox.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(checkBox);
        
        solveButton = new JButton("solve");
        // need icon
        solveButton.setFocusable(false);
        bottomPanel.add(solveButton);

        // Add Components to the frame
        frame.setJMenuBar(bar);
        frame.add(outerCenterPanel);
        frame.add(bottomPanel);
        frame.setVisible(true);
    }

    // Create ActionListener for the MenuItems
    ActionListener newGameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {           
            String URLName;

            if (event.getSource() == easy) {
                URLName = new String("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=easy");
            }
            else if (event.getSource() == intermediate) {
                URLName = new String("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=intermediate");
            }
            else {
                URLName = new String("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=expert");
            }

            try {
                URL gameURL = new URL(URLName);
                URLConnection gameURLConn = gameURL.openConnection();
                InputStream gameURLInput = gameURLConn.getInputStream();

                for (int i = 0; i < sudokuButtons.length; i++) {
                    for (int j = 0; j < sudokuButtons[i].length; j++) {
                        char sudokuNumber = (char) gameURLInput.read();
                        if (sudokuNumber == ' ' || sudokuNumber == '\n') {
                            j--;
                            continue;
                        }
                        if (sudokuNumber != '.') {
                            sudokuButtons[i][j].setText(String.valueOf(sudokuNumber));
                            sudokuButtons[i][j].setBackground(Color.GRAY);
                            initializedButtons[i][j] = true;
                        }
                        else {
                            sudokuButtons[i][j].setText("");
                            sudokuButtons[i][j].setBackground(Color.WHITE);
                            initializedButtons[i][j] = false;
                        }
                    }
                } 
            }
            catch (MalformedURLException exception) {
                System.err.println(exception);
                System.exit(1);
            }
            catch (IOException exception) {
                System.err.println(exception);
                System.exit(1);
            }
        }
    };

    // ActionListener for the sudokuButtons
    ActionListener sudokuButtonsListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int number = 0;
            
            for (int i = 0; i < sudokuButtons.length; i++) {
                for (int j = 0; j < sudokuButtons[i].length; j++) {
                    if (initializedButtons[i][j]) {
                        sudokuButtons[i][j].setBackground(Color.GRAY);
                    }
                    else {
                        sudokuButtons[i][j].setBackground(Color.WHITE);
                    }
                    
                    if (e.getSource() == sudokuButtons[i][j]) {
                        String string = sudokuButtons[i][j].getText();
                        if (string != "") {
                            number = Integer.valueOf(string);
                        }
                        sudokuButtons[i][j].setBackground(yellowRGB);
                        chosenSudokuButton = sudokuButtons[i][j];
                    }
                }
            }

            if (number != 0) {
                repaintSudokuGrid(number);
            }

        }
    };

    // Action for the numberButtons
    Action numberButtonsAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (chosenSudokuButton.equals(idleButton)) {
                return;
            }
            
            int number = 0;

            loop: {
                for (int i = 0; i < numButtons.length; i++) {
                    if (e.getSource() == numButtons[i]) {
                        for (int j = 0; j < sudokuButtons.length; j++) {
                            for (int k = 0; k < sudokuButtons[j].length; k++) {
                                if (chosenSudokuButton.equals(sudokuButtons[j][k])) {
                                    if (initializedButtons[j][k] == false) {
                                        number = i+1;
                                        if (checkCollisions(number) == false) return;
                                        chosenSudokuButton.setText(String.valueOf(number));
                            
                                        break loop;
                                    }
                                }
                            }
                        }          
                    }
                }
            }

            if (number != 0) {
                repaintSudokuGrid(number);
            }
            
        }
    };

    Action optionButtonsAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (chosenSudokuButton.equals(idleButton)) {
                return;
            }
            
            loop : {
                if (e.getSource() == removeButton) {
                    for (int i = 0; i < sudokuButtons.length; i++) {
                        for (int j = 0; j < sudokuButtons[i].length; j++) {
                            if (chosenSudokuButton.equals(sudokuButtons[i][j])) {
                                if (initializedButtons[i][j]) {
                                    return;
                                }
                                else {
                                    chosenSudokuButton.setText("");
                                    chosenSudokuButton = idleButton;
                                    break loop;
                                }
                            }
                        }
                    }
                }
            }

            repaintSudokuGrid(0);
        }
    };

    void repaintSudokuGrid(int number) {
        for (int i = 0; i < sudokuButtons.length; i++) {
            for (int j = 0; j < sudokuButtons[i].length; j++) {
                String string = sudokuButtons[i][j].getText();
                if (string != "" && number == Integer.valueOf(string)) {
                    sudokuButtons[i][j].setBackground(yellowRGB);
                }
                else if (string != "" && initializedButtons[i][j]) {
                    sudokuButtons[i][j].setBackground(Color.GRAY);
                }
                else {
                    sudokuButtons[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    boolean checkCollisions(int number) {
        int i = 0, j = 0, success = 0;
        System.out.println(number);
        loop: {
            for (i = 0; i < sudokuButtons.length; i++) {
                for (j = 0; j < sudokuButtons[i].length; j++) {
                    if (chosenSudokuButton.equals(sudokuButtons[i][j])) {
                        break loop;
                    }
                }
            }
        }

        for (int k = 0; k < sudokuButtons[i].length; k++) {
            if (sudokuButtons[i][k].getText() == String.valueOf(number)) {
                sudokuButtons[i][k].setBackground(Color.RED);
                success++;
            }
        }
        
        return (success > 0) ? false : true;
    }
    public static void main(String[] args) {
        new Homework3();
    }
}