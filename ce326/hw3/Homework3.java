package ce326.hw3;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Homework3 {
    public Homework3() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);
         
        JMenuItem easy = new JMenuItem("Easy");
        easy.setMnemonic(KeyEvent.VK_E);
        JMenuItem intermediate = new JMenuItem("Intermediate");
        intermediate.setMnemonic(KeyEvent.VK_I);
        JMenuItem expert = new JMenuItem("Expert");
        expert.setMnemonic(KeyEvent.VK_X);
        
        JMenu newGame = new JMenu("New Game");
        newGame.setMnemonic(KeyEvent.VK_G);
        newGame.add(easy);
        newGame.add(intermediate);
        newGame.add(expert);
        
        JMenuBar bar = new JMenuBar();
        bar.add(newGame);

        JPanel outerCenterPanel = new JPanel();
        outerCenterPanel.setBackground(Color.WHITE);
        outerCenterPanel.setBounds(0,50,500,300);
        outerCenterPanel.setLayout(new GridLayout(3,3,1,1));

        JPanel[] innerCenterPanels = new JPanel[9];
        for (int i = 0; i < innerCenterPanels.length; i++) {
            innerCenterPanels[i] = new JPanel();
            innerCenterPanels[i].setLayout(new GridLayout(3,3,1,1));
            innerCenterPanels[i].setBackground(Color.BLACK);
            outerCenterPanel.add(innerCenterPanels[i]);
        }
        
        JButton[][] sudokuButtons = new JButton[9][9];
        for (int i = 0; i < sudokuButtons.length; i++) {
            for (int j = 0; j < sudokuButtons[i].length; j++) {
                sudokuButtons[i][j] = new JButton();
                sudokuButtons[i][j].setFocusable(false);
                sudokuButtons[i][j].setEnabled(false);
                sudokuButtons[i][j].setBackground(Color.LIGHT_GRAY);
                innerCenterPanels[i].add(sudokuButtons[i][j]);
            }
        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBounds(0,350,500,150);
        bottomPanel.setLayout(new FlowLayout());

        JButton[] numButtons = new JButton[9];
        for (int i = 0; i < numButtons.length; i++) {
            numButtons[i] = new JButton(String.valueOf(i+1));
            numButtons[i].setFocusable(false);
            bottomPanel.add(numButtons[i]);
        }

        JButton removeButton = new JButton("remove");
        // need icon
        removeButton.setFocusable(false);
        bottomPanel.add(removeButton);
        
        JButton undoButton = new JButton("undo");
        //need icon
        undoButton.setFocusable(false);
        bottomPanel.add(undoButton);
        
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText("Verify your solution");
        checkBox.setFocusable(false);
        checkBox.setBackground(Color.WHITE);
        bottomPanel.add(checkBox);

        JButton solveButton = new JButton("solve");
        // need icon
        solveButton.setFocusable(false);
        bottomPanel.add(solveButton);

        frame.setJMenuBar(bar);
        frame.add(outerCenterPanel);
        frame.add(bottomPanel);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Homework3();
    }
}