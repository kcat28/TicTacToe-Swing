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
import javax.swing.SwingUtilities;

/**
 *
 * @author Jep
 */
public final class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_Panel = new JPanel();
    JPanel menu_Panel = new JPanel();
    JPanel button_Panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    
    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = -1;
    public static final int EMPTY = 0;
    
    public TicTacToe() {
        initializeGame();
    }
    
      private void initializeGame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color (50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));
        textfield.setFont(new Font("Poppins", Font.BOLD, 60));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true); 
        
        menu_Panel.setLayout(new FlowLayout());
        menu_Panel.setBounds(0, 0, 800, 100);
        JButton retry = new JButton("Retry again"){{
                setFocusable(false);
                addActionListener(e -> restartGame());
                
        }};
        JButton quit = new JButton("Quit"){{
                setFocusable(false);
               addActionListener(e -> frame.dispose());
        }};
        
        
        menu_Panel.add(retry);
        menu_Panel.add(quit);
        
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
        frame.add(menu_Panel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        
        
        firstTurn();
    }
       
    private void restartGame() {
        frame.dispose(); // Dispose the current frame
        SwingUtilities.invokeLater(() -> new TicTacToe());
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
                        if (!player1_turn) {
                            check();   
                            if (!check()) aiMove();                         
                        }
                             
                    }
                } 
    }
  }
}
    
    public void firstTurn(){ //function to decide which one goes first - via random
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(random.nextInt(2) == 0){
        player1_turn = true;
        textfield.setText("X turn");
    } else {
            player1_turn = false;
            textfield.setText("O turn");
            aiMove();
        }
    }
    public boolean check(){
        // X CHECK   
        if (
                (buttons[0].getText() == "X") &&
                (buttons[1].getText() == "X") &&
                (buttons[2].getText() == "X") ){
            Xwins(0, 1, 2);
            return true;
        }
        if (
                (buttons[3].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[5].getText() == "X") ){
            Xwins(3, 4, 5);
            return true;
        }
        if (
                (buttons[6].getText() == "X") &&
                (buttons[7].getText() == "X") &&
                (buttons[8].getText() == "X") ){
            Xwins(6, 7, 8);
            return true;
        }
        if (
                (buttons[0].getText() == "X") &&
                (buttons[3].getText() == "X") &&
                (buttons[6].getText() == "X") ){
            Xwins(0, 3, 6);
            return true;
        }
        if (
                (buttons[1].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[7].getText() == "X") ){
            Xwins(1, 4, 7);
            return true;
        }
        if (
                (buttons[2].getText() == "X") &&
                (buttons[5].getText() == "X") &&
                (buttons[8].getText() == "X") ){
            Xwins(2, 5, 8);
            return true;
        }
        if (
                (buttons[0].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[8].getText() == "X") ){
            Xwins(0, 4, 8);
            return true;
        }
        if (
                (buttons[2].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[6].getText() == "X") ){
            Xwins(2, 4, 6);
            return true;
        }
        // O CHECK
        
        if (
                (buttons[0].getText() == "O") &&
                (buttons[1].getText() == "O") &&
                (buttons[2].getText() == "O") ){
            Owins(0, 1, 2);
            return true;
        }
        if (
                (buttons[3].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[5].getText() == "O") ){
            Owins(3, 4, 5);
            return true;
        }
        if (
                (buttons[6].getText() == "O") &&
                (buttons[7].getText() == "O") &&
                (buttons[8].getText() == "O") ){
            Owins(6, 7, 8);
            return true;
        }
        if (
                (buttons[0].getText() == "O") &&
                (buttons[3].getText() == "O") &&
                (buttons[6].getText() == "O") ){
            Owins(0, 3, 6);
            return true;
        }
        if (
                (buttons[1].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[7].getText() == "O") ){
            Owins(1, 4, 7);
            return true;
        }
        if (
                (buttons[2].getText() == "O") &&
                (buttons[5].getText() == "O") &&
                (buttons[8].getText() == "O") ){
            Owins(2, 5, 8);
            return true;
        }
        if (
                (buttons[0].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[8].getText() == "O") ){
            Owins(0, 4, 8);
            return true;
        }
        if (
                (buttons[2].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[6].getText() == "O") ){
            Owins(2, 4, 6);
            return true;
        } 
        
        if (isBoardFull()) {
            tie();
            return true; // Exit the method early if it's a tie
    }
         return false;
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
    public void tie() {
        textfield.setText("It's a Tie!");
        for (int i = 0; i < 9; i++) {
            buttons[i].setBackground(Color.yellow);
            buttons[i].setEnabled(false);
        }
    }
    
     public boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals("")) {
                return false;
            }
        }
        return true;
    }
     
    public int[][] getBoardState() { // converts gui into 2d array for AI to understand the board
        int[][] board = new int[3][3];
        for (int i = 0; i < 9; i++) {
            switch (buttons[i].getText()) {
                case "X":
                    board[i / 3][i % 3] = PLAYER_X; // [row][col]  i/3 gives row index i%3 gives col index to map out for AI
                    break;
                case "O":
                    board[i / 3][i % 3] = PLAYER_O;
                    break;
                default:
                    board[i / 3][i % 3] = EMPTY;
                    break;
            }
        }
        return board;
    }
     
    public void aiMove(){
        int[][] board = getBoardState(); // get current 2d array 
        Move bestMove = findBestMove(board, PLAYER_O); // determines best move by giving minimax the current board and playerO; returns move[row][col]
        buttons[bestMove.row * 3 + bestMove.col].setForeground(new Color(0, 0, 255)); // *3 converts 2d array into 1d array for gui to access e.g., buttons[i] == x
        buttons[bestMove.row * 3 + bestMove.col].setText("O");
        player1_turn = true;
        textfield.setText("X turn");
        check();
     }
     private static class Move { //setup variable
        int row, col;
        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    private Move findBestMove(int[][] board, int player) {
        int bestVal = (player == PLAYER_X) ? Integer.MIN_VALUE : Integer.MAX_VALUE; // set value for playerX as lowest aiming to maximize score and playerO as highest aiming to minimize it
        Move bestMove = new Move(-1, -1); // invalid indices as placeholders

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {   //simulation 
                if (board[i][j] == EMPTY) { // iterates the 2d array or board and check if EMPTY, if true, move can be made in the index
                    board[i][j] = player; // temporarily sets to current player
                    int moveVal = minimax(board, 0, false, player); // evaluate board after this move and returns a score (moveVal)
                    board[i][j] = EMPTY; // index is reset to empty to undo move and restore board state

                    if (player == PLAYER_X && moveVal > bestVal) {  // if maximizing player (X) and evaluated move (moveVal) is greater than current bestMove, update bestMove to current cell and bestVal to moveVal
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    } else if (player == PLAYER_O && moveVal < bestVal) { // if minimizing player (O) and moveVal is less than bestVal, update bestMove and bestVal accordingly
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }                   // the function loops through 9 possible moves and checks for best value move: 1, 0, -1 accordingly and returns bestMove for current player
        return bestMove;
    }
    /*
    The findBestMove function:
        Initializes the best value and best move.
        Iterates over all cells on the board.
        For each empty cell, it simulates a move and uses the Minimax algorithm to evaluate the move.
        Updates the best move based on the Minimax evaluation.
        Returns the best move found.
    */
     
    private int minimax(int[][] board, int depth, boolean isMax, int player) {
    int score = evaluate(board, player);
    
    
    //base cases
    if (score == 10) return score - depth; // Adjusted score based on depth
    if (score == -10) return score + depth; // Adjusted score based on depth
    if (!isMovesLeft(board)) return 0;

    if (isMax) {    // maximizing player's turn
        int best = Integer.MIN_VALUE; // set lowest to try maximize
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) { 
                if (board[i][j] == EMPTY) { // if empty, player is able to simulate a move
                    board[i][j] = player;   
                    best = Math.max(best, minimax(board, depth + 1, !isMax, player)); // Increment depth in tree (recursively call minimax) && compare current best and minimax call value
                    board[i][j] = EMPTY; // end simulation;  undo move
                }
            }
        }
        return best;
    } else {    // minimizine player's turn
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) { // if empty, algo consider the cell for simulation
                    board[i][j] = player == PLAYER_X ? PLAYER_O : PLAYER_X; // if player is X, the simulation is for O vice versa
                    best = Math.min(best, minimax(board, depth + 1, !isMax, player)); // Increment depth ...
                    board[i][j] = EMPTY;
                }
            }
        }
        return best;
    }
}
    
    private boolean isMovesLeft(int[][] board) { // checks if there are still possible moves to be made in board
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == EMPTY)
                    return true;
        return false;
    }
    
private int evaluate(int[][] board, int player) {
    int score = 0;
    int opponent = (player == PLAYER_X) ? PLAYER_O : PLAYER_X;

    // Check rows and columns for winning conditions and immediate threats
    for (int i = 0; i < 3; i++) {
        // Check rows
        if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
            if (board[i][0] == player) score += 10;
            else if (board[i][0] == opponent) score -= 20; // Increased penalty for opponent's win
        }
        // Check columns
        if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
            if (board[0][i] == player) score += 10;
            else if (board[0][i] == opponent) score -= 20; // Increased penalty for opponent's win
        }
    }

    // Check diagonals for winning conditions and immediate threats
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
        if (board[0][0] == player) score += 10;
        else if (board[0][0] == opponent) score -= 20; // Increased penalty for opponent's win
    }
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
        if (board[0][2] == player) score += 10;
        else if (board[0][2] == opponent) score -= 20; // Increased penalty for opponent's win
    }

    // Add weights to certain positions
    if (board[1][1] == player) score += 8; // Increased weight for the center
    if ((board[0][0] == player && board[2][2] == player) || (board[0][2] == player && board[2][0] == player)) score += 3; // Diagonals
    if ((board[0][1] == player && board[2][1] == player) || (board[1][0] == player && board[1][2] == player)) score += 2; // Middle rows/columns
    if ((board[0][0] == player && board[0][2] == player) || (board[2][0] == player && board[2][2] == player)) score += 2; // Top and bottom edges
    if ((board[0][0] == player && board[2][1] == player) || (board[0][1] == player && board[2][0] == player) || (board[0][2] == player && board[2][1] == player)) score += 1; // Top and bottom corners

    // Check for opponent's almost-wins (indicate potential threat)
    for (int i = 0; i < 3; i++) {
        // Check rows for opponent almost-wins
        if (board[i][0] == opponent && board[i][1] == opponent && board[i][2] == EMPTY) {
            score -= 25; // Increased penalty for opponent's almost-win
        } else if (board[i][0] == opponent && board[i][1] == EMPTY && board[i][2] == opponent) {
            score -= 25; // Increased penalty for opponent's almost-win
        } else if (board[i][0] == EMPTY && board[i][1] == opponent && board[i][2] == opponent) {
            score -= 25; // Increased penalty for opponent's almost-win
        }

        // Check columns for opponent almost-wins
        if (board[0][i] == opponent && board[1][i] == opponent && board[2][i] == EMPTY) {
            score -= 25; // Increased penalty for opponent's almost-win
        } else if (board[0][i] == opponent && board[1][i] == EMPTY && board[2][i] == opponent) {
            score -= 25; // Increased penalty for opponent's almost-win
        } else if (board[0][i] == EMPTY && board[1][i] == opponent && board[2][i] == opponent) {
            score -= 25; // Increased penalty for opponent's almost-win
        }
    }

    // Check diagonals for opponent almost-wins
    if (board[0][0] == opponent && board[1][1] == opponent && board[2][2] == EMPTY) {
        score -= 25; // Increased penalty for opponent's almost-win
    } else if (board[0][0] == opponent && board[1][1] == EMPTY && board[2][2] == opponent) {
        score -= 25; // Increased penalty for opponent's almost-win
    } else if (board[0][0] == EMPTY && board[1][1] == opponent && board[2][2] == opponent) {
        score -= 25; // Increased penalty for opponent's almost-win
    }

    if (board[0][2] == opponent && board[1][1] == opponent && board[2][0] == EMPTY) {
        score -= 25; // Increased penalty for opponent's almost-win
    } else if (board[0][2] == opponent && board[1][1] == EMPTY && board[2][0] == opponent) {
        score -= 25; // Increased penalty for opponent's almost-win
    } else if (board[0][2] == EMPTY && board[1][1] == opponent && board[2][0] == opponent) {
        score -= 25; // Increased penalty for opponent's almost-win
    }

    return score;
}

     public static void main(String[] args) {   
        TicTacToe TTT = new TicTacToe();   
    }
}
