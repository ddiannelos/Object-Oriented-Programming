package ce326.hw3;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.List;
import java.util.ArrayList;


public class Homework3 {
    private JFrame frame;
    
    private JMenuItem easy, intermediate, expert;
    private JMenu newGame;
    private JMenuBar bar;
    
    private JPanel outerCenterPanel;
    private JPanel[][] innerCenterPanels = new JPanel[3][3];
    private JPanel bottomPanel;
    
    private JButton[][] sudokuButtons = new JButton[9][9];
    private JButton[] numButtons = new JButton[9];
    private JButton removeButton, undoButton, solveButton;
    private JButton idleButton = new JButton();
    private JButton chosenSudokuButton = idleButton;
    
    private JCheckBox checkBox;
    
    private Color yellowRGB = new Color(255,255,200);
    private Color blueRGB = new Color(51,153,255);
    
    private int initializedButtonsNumber = 0;
    private int insertedButtons = 0;
    private int[][] solvedSudoku = new int[9][9];
    
    private boolean gameOn = false;
    private boolean verifyMode = false;
    private boolean[][] initializedButtons = new boolean[9][9];

    private List<OptionEvent> numberSequence = new ArrayList<>();
    
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
        
        // Create ButtonsNumber for the BottomPanel
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
        
        // Create OptionButtons and CheckBox for 
        // BottomPanel
        Image image = new ImageIcon("eraser.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        removeButton = new JButton(new ImageIcon(image));
        removeButton.setFocusable(false);
        removeButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                     put(KeyStroke.getKeyStroke("BACK_SPACE"), "remove");
        removeButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                     put(KeyStroke.getKeyStroke("DELETE"), "remove");
        removeButton.getActionMap().put("remove", optionButtonsAction);
        removeButton.addActionListener(optionButtonsAction);
        bottomPanel.add(removeButton);
        
        image = new ImageIcon("undo.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        undoButton = new JButton(new ImageIcon(image));
        undoButton.setFocusable(false);
        undoButton.addActionListener(optionButtonsAction);
        bottomPanel.add(undoButton);
        
        checkBox = new JCheckBox();
        checkBox.setText("Verify your solution");
        checkBox.setFocusable(false);
        checkBox.setBackground(Color.LIGHT_GRAY);
        checkBox.addItemListener(checkBoxListener);
        bottomPanel.add(checkBox);
        
        image = new ImageIcon("rubik.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        solveButton = new JButton(new ImageIcon(image));
        solveButton.setFocusable(false);
        solveButton.addActionListener(optionButtonsAction);
        bottomPanel.add(solveButton);

        // Add Components to the frame
        frame.setJMenuBar(bar);
        frame.add(outerCenterPanel);
        frame.add(bottomPanel);
        frame.setVisible(true);
    }

    // Class to save needed info to perform
    // undo option
    private class OptionEvent {
        private JButton button;
        private char option;
        private int number;

        OptionEvent (JButton button, char option, int number) {
            this.button = button;
            this.option = option;
            this.number = number;
        }

        public JButton getButton() {
            return button;
        }

        public char getOption() {
            return option;
        }

        public int getNumber() {
            return number;
        }
    }


    // Create ActionListener for the MenuItems
    ActionListener newGameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {           
            String URLName;

            // Depending on the difficulty use the appropriate
            // URL
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

                // Initialize info for new game
                initializedButtonsNumber = 0;
                insertedButtons = 0;
                numberSequence.clear();
                
                // Copy the content of the URL and create the sudoku grid
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
                            solvedSudoku[i][j] = Character.getNumericValue(sudokuNumber);
                            initializedButtons[i][j] = true;
                            initializedButtonsNumber++;
                        }
                        else {
                            sudokuButtons[i][j].setText("");
                            sudokuButtons[i][j].setBackground(Color.WHITE);
                            solvedSudoku[i][j] = 0;
                            initializedButtons[i][j] = false;
                        }
                    }
                } 
                
                gameOn = solveSudoku();
                setEnabledBottomPanelButtons(true);
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
            
            loop: {
                // Find the sudokuButton that was pressed
                for (int i = 0; i < sudokuButtons.length; i++) {
                    for (int j = 0; j < sudokuButtons[i].length; j++) {
                        if (e.getSource() == sudokuButtons[i][j]) {
                            String string = sudokuButtons[i][j].getText();
                            if (string != "") {
                                number = Integer.valueOf(string);
                            }
                            chosenSudokuButton = sudokuButtons[i][j];

                            break loop;
                        }
                    }
                }
            }

            // Depending on the verifyMode, paint
            // the sudoku grid accordingly
            if (verifyMode == true) {
                paintVerifiedSudokuGrid(number);
            }
            else {
                repaintSudokuGrid(number);
            }
            chosenSudokuButton.setBackground(yellowRGB);
        }
    };

    // Action for the numberButtons
    Action numberButtonsAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // If chosenSudokuButton is idle then
            // don't do anything
            if (chosenSudokuButton.equals(idleButton)) {
                return;
            }
            
            // Depending on the verifyMode, paint
            // the sudoku grid accordingly
            if (verifyMode == true) {
                paintVerifiedSudokuGrid(0);
            }
            else {
                repaintSudokuGrid(0);
            }

            int number = 0;

            loop: {
                // Find the numButton that was pressed
                for (int i = 0; i < numButtons.length; i++) {
                    if (e.getSource() == numButtons[i]) {
                        // Find which sudokuButton is pressed at the moment
                        for (int j = 0; j < sudokuButtons.length; j++) {
                            for (int k = 0; k < sudokuButtons[j].length; k++) {
                                if (chosenSudokuButton.equals(sudokuButtons[j][k])) {
                                    // Check if the button pressed, is an initialized
                                    // one
                                    if (initializedButtons[j][k] == false) {
                                        number = i+1;
                                        
                                        // Check if the number can be assigned to the
                                        // button pressed depending on the sudoku rules
                                        if (checkCollisions(number) == false) {
                                            chosenSudokuButton.setBackground(yellowRGB);
                                            return;
                                        }
                                        
                                        // If there wasn't a number in the sudokuButton
                                        // increase the numbersInserted in the sudoku
                                        if (chosenSudokuButton.getText() == "") {
                                            insertedButtons++;
                                        }
                                        // Add the insertion in the list
                                        numberSequence.add(new OptionEvent(sudokuButtons[j][k], 'a', number));
                                        
                                        chosenSudokuButton.setText(String.valueOf(number));
                                        
                                        
                                        // Check if the numbers that was initialized and 
                                        // the numbers that the player inserted are equal
                                        // to the size of the sudoku
                                        if (insertedButtons+initializedButtonsNumber == sudokuButtons.length*sudokuButtons[0].length) {
                                            JOptionPane.showMessageDialog(null, "Congratulations, you solved the sudoku!", 
                                                                                "Sudoku", JOptionPane.PLAIN_MESSAGE);
                                            setEnabledBottomPanelButtons(false);
                                                
                                        }
                                        
                                        // Check if the verifyMode is on, and
                                        // paint the button pressed accordingly
                                        if (verifyMode == true) {
                                            if (solvedSudoku[j][k] != Integer.valueOf(chosenSudokuButton.getText())) {
                                                chosenSudokuButton.setBackground(blueRGB);
                                            }
                                            else {
                                                chosenSudokuButton.setBackground(Color.WHITE);
                                            }
                                        }

                                        chosenSudokuButton = idleButton;
                                        break loop;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    // Action for the optionButtons
    Action optionButtonsAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Find the optionButton that was pressed
            if (e.getSource() == removeButton) {
                // Check if chosenSudokuButton is the idle one
                if (chosenSudokuButton.equals(idleButton)) {
                    return;
                }
                
                loop: {
                    // Find the sudokuButton that is pressed
                    for (int i = 0; i < sudokuButtons.length; i++) {
                        for (int j = 0; j < sudokuButtons[i].length; j++) {
                            if (chosenSudokuButton.equals(sudokuButtons[i][j])) {
                                // Check if the sudokuButton is an
                                // initialized one or it doesn't have
                                // a number in it
                                if (initializedButtons[i][j] || chosenSudokuButton.getText() == "") {
                                    return;
                                }
                                
                                // Add the removal in the list and decrease 
                                // the numberInserted in the sudoku
                                numberSequence.add(new OptionEvent(sudokuButtons[i][j], 'r', Integer.valueOf(chosenSudokuButton.getText())));
                                insertedButtons--;
                                
                                chosenSudokuButton.setText("");
                                
                                // Find the button remove on the adding NumberSequence
                                // and remove it from the list
                                chosenSudokuButton = idleButton;
                                
                                break loop;
                            }
                        }
                    }
                }
            }
            else if (e.getSource() == undoButton) {
                // Check if the addingNumberSequence is empty
                if (numberSequence.size() == 0) {
                    return;
                }
                
                // Find the lastPosition of the list
                int lastObjectPosition = numberSequence.size()-1;
                
                // Depending on the last action
                // perform a removal or insertion
                switch (numberSequence.get(lastObjectPosition).getOption()) {
                    // If the last action was a removal
                    // add the last number in the sudoku grid
                    case 'r': {
                        JButton button = numberSequence.get(lastObjectPosition).getButton();
                        int number = numberSequence.get(lastObjectPosition).getNumber();
                        
                        insertedButtons++;
                        button.setText(String.valueOf(number));
                        
                        break;
                    }
                    // If the last  action was a insertion
                    // remove the last number from the sudoku grid
                    case 'a': {
                        JButton button = numberSequence.get(lastObjectPosition).getButton();
                        
                        insertedButtons--;
                        button.setText("");                        
                        
                        break;
                    }
                }
                numberSequence.remove(lastObjectPosition);
                chosenSudokuButton = idleButton;
            }
            else if (e.getSource() == solveButton) {
                // Check if the game has started
                if (gameOn == false) {
                    return;
                }

                
                for (int i = 0; i < sudokuButtons.length; i++) {
                    for (int j = 0; j < sudokuButtons[i].length; j++) {
                        if (initializedButtons[i][j] == true) {
                            continue;
                        }
                        sudokuButtons[i][j].setText(String.valueOf(solvedSudoku[i][j]));
                    }
                }
                setEnabledBottomPanelButtons(false);
                
                chosenSudokuButton = idleButton;

                gameOn = false;
            }
            
            // Depending on the verifyMode, paint
            // the sudoku grid accordingly
            if (verifyMode == true) {
                paintVerifiedSudokuGrid(0);
            }
            else {
                repaintSudokuGrid(0);
            }
        }
    };

    // ItemListener for the checkbox
    ItemListener checkBoxListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            // Check if the checkBox was selected
            if(e.getStateChange() == ItemEvent.SELECTED) {
                verifyMode = true;
                
                // Check if the game has started
                if (gameOn == false) {
                    return;
                }

                if (chosenSudokuButton.equals(idleButton) == true || chosenSudokuButton.getText() == "") {
                    paintVerifiedSudokuGrid(0);
                    chosenSudokuButton.setBackground(yellowRGB);
                }
                else {
                    paintVerifiedSudokuGrid(Integer.valueOf(chosenSudokuButton.getText()));
                }
            }
            else if (e.getStateChange() == ItemEvent.DESELECTED) {
                verifyMode = false;

                // Check if the game has started
                if (gameOn == false) {
                    return;
                }
                
                if (chosenSudokuButton.equals(idleButton) == true || chosenSudokuButton.getText() == "") {
                    repaintSudokuGrid(0);
                    chosenSudokuButton.setBackground(yellowRGB);
                }
                else {
                    repaintSudokuGrid(Integer.valueOf(chosenSudokuButton.getText()));
                }
            }
        }
    };

    // ***paintVerifiedSudokuGrid***
    // Method that paints the grid appropriately
    // when verify mode is on
    void paintVerifiedSudokuGrid(int number) {
        for (int i = 0; i < sudokuButtons.length; i++) {
            for (int j = 0; j < sudokuButtons[i].length; j++) {
                String string = sudokuButtons[i][j].getText();
                
                if (string != "" && solvedSudoku[i][j] != Integer.valueOf(string)) {
                    sudokuButtons[i][j].setBackground(blueRGB);
                }
                else if (string != "" && number == Integer.valueOf(string)) {
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

    // ***setEnabledBottomPanelButtons
    // Method that enables the ButtopPanel
    // buttons
    void setEnabledBottomPanelButtons(boolean bool) {
        for (JButton button : numButtons) {
            button.setEnabled(bool);
        }
        removeButton.setEnabled(bool);
        undoButton.setEnabled(bool);
        checkBox.setEnabled(bool);
        solveButton.setEnabled(bool);
    }

    // ***repaintSudokuGrid***
    // Method to reapaint sudoku grid depending on
    // the number given as parameter
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

    // ***checkCollisions***
    // Method to check for collisions depending
    // on the number give as parameter
    boolean checkCollisions(int number) {
        int i = 0, j = 0, success = 0;
        loop: {
            for (i = 0; i < sudokuButtons.length; i++) {
                for (j = 0; j < sudokuButtons[i].length; j++) {
                    if (chosenSudokuButton.equals(sudokuButtons[i][j])) {
                        break loop;
                    }
                }
            }
        }
        
        // Find collisions on the same row
        for (int k = 0; k < sudokuButtons[i].length; k++) {
            if (chosenSudokuButton.equals(sudokuButtons[i][k])) {
                continue;
            }
            
            if (sudokuButtons[i][k].getText().compareTo(String.valueOf(number)) == 0) {
                sudokuButtons[i][k].setBackground(Color.RED);
                success++;
            }
        }
        
        // Find collisions on the same collumn
        for (int k = 0; k < sudokuButtons.length; k++) {
            if (chosenSudokuButton.equals(sudokuButtons[k][j])) {
                continue;
            }
            
            if (sudokuButtons[k][j].getText().compareTo(String.valueOf(number)) == 0) {
                sudokuButtons[k][j].setBackground(Color.RED);
                success++;
            }
        }

        // Find collisions on the same block
        int panelStartingPositionX = i, panelStartingPositionY = j;
        
        while (panelStartingPositionX%3 != 0 || panelStartingPositionY%3 != 0) {
            if (panelStartingPositionX%3 != 0) {
                panelStartingPositionX--;
            }
            if (panelStartingPositionY%3 != 0) {
                panelStartingPositionY--;
            }
        } 

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int x = k + panelStartingPositionX;
                int y = l + panelStartingPositionY;
                
                if (chosenSudokuButton.equals(sudokuButtons[x][y])) {
                    continue;
                }

                if (sudokuButtons[x][y].getText().compareTo(String.valueOf(number)) == 0) {
                    sudokuButtons[x][y].setBackground(Color.RED);
                    success++;
                }
            }
        }
        
        return (success > 0) ? false : true;
    }

    // ***checkCollisions***
    // Method that checks collision on the 
    // solvedSudoku array depending on the number
    boolean checkCollisions(int row, int col, int number) {
        // Check collisions on the same row
        for (int i = 0; i < solvedSudoku[row].length; i++) {
            if (i == col) {
                continue;
            }
            
            if (solvedSudoku[row][i] == number) {
                return false;
            }
        }

        // Check collisions on the same column
        for (int i = 0; i < solvedSudoku.length; i++) {
            if (i == row) {
                continue;
            }

            if (solvedSudoku[i][col] == number) {
                return false;
            }
        }
        
        // Check collisions on the same block
        int panelStartingPositionX = row, panelStartingPositionY = col;

        while (panelStartingPositionX%3 != 0 || panelStartingPositionY%3 != 0) {
            if (panelStartingPositionX%3 != 0) {
                panelStartingPositionX--;
            }
            if (panelStartingPositionY%3 != 0) {
                panelStartingPositionY--;
            }
        } 

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = i + panelStartingPositionX;
                int y = j + panelStartingPositionY;
                
                if (x == row && y == col) {
                    continue;
                }
                
                if (solvedSudoku[x][y] == number) {
                    return false;
                }
            }
        }

        return true;
    }
    
    // ***sudokuSolver***
    // Method that solves the sudoku and
    // save the solved sodoku to a two
    // dimensional array
    boolean solveSudoku() {
        for (int i = 0; i < solvedSudoku.length; i++) {
            for (int j = 0; j < solvedSudoku[i].length; j++) {
                if (solvedSudoku[i][j] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        solvedSudoku[i][j] = k;
                        if (checkCollisions(i, j, k) && solveSudoku()) {
                            return true;
                        }
                        solvedSudoku[i][j] = 0;
                    }

                    return false;
                }
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        new Homework3();
    }
}