package com.mycompany.tictactoegame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

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
    public static final int AI_PLAYER = PLAYER_O;
    public static final int EMPTY = 0;
    public static final int SIZE = 3;

    public TicTacToe() {
        initializeGame();
    }

    private void initializeGame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Poppins", Font.BOLD, 60));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        menu_Panel.setLayout(new FlowLayout());
        menu_Panel.setBounds(0, 0, 800, 100);
        JButton retry = new JButton("Retry again") {{
            setFocusable(false);
            addActionListener(e -> restartGame());
        }};
        JButton quit = new JButton("Quit") {{
            setFocusable(false);
            addActionListener(e -> frame.dispose());
        }};
        menu_Panel.add(retry);
        menu_Panel.add(quit);

        title_Panel.setLayout(new BorderLayout());
        title_Panel.setBounds(0, 0, 800, 100);

        button_Panel.setLayout(new GridLayout(3, 3));
        button_Panel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
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
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (buttons[i].getText().isEmpty()) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        checkWin();
                        player1_turn = false;
                        textfield.setText("O turn");
                        checkWin();
                        if (!player1_turn) {
                            checkWin();
                            if (!checkWin()) aiMove();
                        }
                    }
                }
            }
        }
    }

    public void firstTurn() { //function to decide which one goes first - via random
        try {
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
            }
            Thread.sleep(1000);
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(true);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X turn");
        } else {
            player1_turn = false;
            textfield.setText("O turn");
            aiMove();
        }
    }

    public boolean buttonIs(int index, String value) {
        return buttons[index].getText().equals(value);
    }

    public boolean checkWin() {
        // X CHECK

        int[][] patterns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
                {0, 4, 8}, {2, 4, 6} // diagonals
        };

        // Check if the patterns matches for X
        for (int[] pattern : patterns) {
            if (buttonIs(pattern[0], "X") && buttonIs(pattern[1], "X") && buttonIs(pattern[2], "X")) {
                setXWin(pattern[0], pattern[1], pattern[2]);
                return true;
            }
        }

        // Check if the patterns matches for O
        for (int[] pattern : patterns) {
            if (buttonIs(pattern[0], "O") && buttonIs(pattern[1], "O") && buttonIs(pattern[2], "O")) {
                setOWin(pattern[0], pattern[1], pattern[2]);
                return true;
            }
        }

        if (isBoardFull()) {
            setBoardTie();
            return true;
        }

        return false;
    }

    public void setXWin(int a, int b, int c) {
        buttons[a].setBackground(Color.green);
        buttons[b].setBackground(Color.green);
        buttons[c].setBackground(Color.green);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X Wins");
    }

    public void setOWin(int a, int b, int c) {
        buttons[a].setBackground(Color.green);
        buttons[b].setBackground(Color.green);
        buttons[c].setBackground(Color.green);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O Wins");
    }

    public void setBoardTie() {
        textfield.setText("It's a Tie!");
        for (int i = 0; i < 9; i++) {
            buttons[i].setBackground(Color.yellow);
            buttons[i].setEnabled(false);
        }
    }

    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int[][] getBoardState() { // converts GUI into 2d array for AI to understand the board
        int[][] board = new int[3][3];
        for (int i = 0; i < 9; i++) {
            switch (buttons[i].getText()) {
                case "X":
                    board[i / 3][i % 3] = PLAYER_X; // [row][col] i/3 gives row index i%3 gives col index to map out for AI
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

    public void aiMove() {
        int[][] board = getBoardState(); // get current 2d array
        if (checkWin()) return;
        Move bestMove = findBestMove(board, AI_PLAYER); // determines best move using greedy algorithm
        buttons[bestMove.row * 3 + bestMove.col].setForeground(new Color(0, 0, 255)); // *3 converts 2d array into 1d array for GUI to access e.g., buttons[i] == x
        buttons[bestMove.row * 3 + bestMove.col].setText("O");
        checkWin();
        player1_turn = true;
        textfield.setText("X turn");
    }

    private static class Move { // setup variable
        int row, col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private Move findBestMove(int[][] board, int player) {
        // Try to make a winning move
        Move winningMove = makeStrategicMove(board, player);
        if (winningMove != null) return winningMove;

        // Try to block opponent's winning move
        int opponent = (player == PLAYER_X) ? PLAYER_O : PLAYER_X;
        Move blockingMove = makeStrategicMove(board, opponent);
        if (blockingMove != null) return blockingMove;

        // Make a random move if no strategic move is found
        return makeRandomMove(board);
    }

    private Move makeStrategicMove(int[][] board, int player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = player;
                    if (checkWinner(board, player)) {
                        board[i][j] = EMPTY; // reset the cell
                        return new Move(i, j);
                    }
                    board[i][j] = EMPTY; // reset the cell
                }
            }
        }
        return null;
    }

    private Move makeRandomMove(int[][] board) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    availableMoves.add(new Move(i, j));
                }
            }
        }
        return availableMoves.get(new Random().nextInt(availableMoves.size()));
    }

    private boolean checkWinner(int[][] board, int player) {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) || (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) || (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
