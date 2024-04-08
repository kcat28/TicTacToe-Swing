/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoegame;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jep
 */
public final class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_Panel = new JPanel();
    JPanel button_Panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    
     TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color (50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));
        textfield.setFont(new Font("Poppins", Font.BOLD, 60));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);
        
        title_Panel.setLayout(new BorderLayout());
        title_Panel.setBounds(0,0,800,100);
        
        button_Panel.setLayout(new GridLayout(3,3));
        button_Panel.setBackground(new Color(150,150,150));
        
        for(int i = 0; i<9; i++){
            buttons[i] = new JButton();
            button_Panel.add(buttons[i]);
            buttons[i].setFont(new Font("Poppins", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
        
        title_Panel.add(textfield);
        frame.add(title_Panel, BorderLayout.NORTH);
        frame.add(button_Panel);
        frame.setLocationRelativeTo(null);
        
        
        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        for(int i = 0; i<9; i++){
            if(e.getSource()== buttons[i]){
                if(player1_turn){
                    if(buttons[i].getText() == ""){
                        buttons[i].setForeground(new Color(255,0,0));
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O turn");
                        check();
                    }
                }
                  else{
                    if (buttons[i].getText() == ""){
                        buttons[i].setForeground(new Color(0,0, 255));
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X turn"); 
                        check();
                }
            }
        }
    }
    }
    
    public void firstTurn(){
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(random.nextInt(2) == 0){
        player1_turn = true;
        textfield.setText("X turn");
    } else {
            player1_turn = false;
            textfield.setText("O turn");
        }
    }
    public void check(){
        // X CHECK 
        
        if (
                (buttons[0].getText() == "X") &&
                (buttons[1].getText() == "X") &&
                (buttons[2].getText() == "X") ){
            Xwins(0, 1, 2);
        }
        if (
                (buttons[3].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[5].getText() == "X") ){
            Xwins(3, 4, 5);
        }
        if (
                (buttons[6].getText() == "X") &&
                (buttons[7].getText() == "X") &&
                (buttons[8].getText() == "X") ){
            Xwins(6, 7, 8);
        }
        if (
                (buttons[0].getText() == "X") &&
                (buttons[3].getText() == "X") &&
                (buttons[6].getText() == "X") ){
            Xwins(0, 3, 6);
        }
        if (
                (buttons[1].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[7].getText() == "X") ){
            Xwins(1, 4, 7);
        }
        if (
                (buttons[2].getText() == "X") &&
                (buttons[5].getText() == "X") &&
                (buttons[8].getText() == "X") ){
            Xwins(2, 5, 8);
        }
        if (
                (buttons[0].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[8].getText() == "X") ){
            Xwins(0, 4, 8);
        }
        if (
                (buttons[2].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[6].getText() == "X") ){
            Xwins(2, 4, 6);
        }
        // O CHECK
        
        if (
                (buttons[0].getText() == "O") &&
                (buttons[1].getText() == "O") &&
                (buttons[2].getText() == "O") ){
            Owins(0, 1, 2);
        }
        if (
                (buttons[3].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[5].getText() == "O") ){
            Owins(3, 4, 5);
        }
        if (
                (buttons[6].getText() == "O") &&
                (buttons[7].getText() == "O") &&
                (buttons[8].getText() == "O") ){
            Owins(6, 7, 8);
        }
        if (
                (buttons[0].getText() == "O") &&
                (buttons[3].getText() == "O") &&
                (buttons[6].getText() == "O") ){
            Owins(0, 3, 6);
        }
        if (
                (buttons[1].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[7].getText() == "O") ){
            Owins(1, 4, 7);
        }
        if (
                (buttons[2].getText() == "O") &&
                (buttons[5].getText() == "O") &&
                (buttons[8].getText() == "O") ){
            Owins(2, 5, 8);
        }
        if (
                (buttons[0].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[8].getText() == "O") ){
            Owins(0, 4, 8);
        }
        if (
                (buttons[2].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[6].getText() == "O") ){
            Owins(2, 4, 6);
        }
    }
    public void Xwins(int a, int b, int c){
        buttons[a].setBackground(Color.green);
        buttons[b].setBackground(Color.green);
        buttons[c].setBackground(Color.green);
        
        for(int i =0; i<9; i++){
            buttons[i].setEnabled(false);
        }
        textfield.setText("X Wins");
    }
     public void Owins(int a, int b, int c){
        buttons[a].setBackground(Color.green);
        buttons[b].setBackground(Color.green);
        buttons[c].setBackground(Color.green);
        
        for(int i =0; i<9; i++){
            buttons[i].setEnabled(false);
        }
        textfield.setText("O Wins");
    }
     
     
    
}
