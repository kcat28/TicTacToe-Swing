package com.mycompany.tictactoegame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public final class TicTacToe {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_Panel = new JPanel();
    JPanel menu_Panel = new JPanel();
    JPanel button_Panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];

    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = -1;
    public static final int MINIMAX = PLAYER_O;
    public static final int GREEDY = PLAYER_X;
    public static final int EMPTY = 0;
    public static final int SIZE = 3;
    private int CURRENT_PLAYER;

    public TicTacToe() {
        createComponents();
        initializeGame();
        firstTurn();
    }

    private void createComponents() {
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
        }

        title_Panel.add(textfield);
        frame.add(title_Panel, BorderLayout.NORTH);
        frame.add(button_Panel);
        frame.add(menu_Panel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
    }

    private void initializeGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(new Color(238, 238, 238));
        }
    }

    private void restartGame() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                initializeGame();
                firstTurn();
                return null;
            }

            @Override
            protected void done() {
            }
        };
        worker.execute();
    }

    public void firstTurn() { //function to decide which one goes first - via random
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (random.nextInt(2) == 0) {
            CURRENT_PLAYER = PLAYER_X;
        } else {
            CURRENT_PLAYER = PLAYER_O;
        }

        while (!checkWin()) {
            textfield.setText(getPlayerSymbol(CURRENT_PLAYER) + " turn");

            aiMove(CURRENT_PLAYER);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (CURRENT_PLAYER == PLAYER_X) {
                CURRENT_PLAYER = PLAYER_O;
            } else {
                CURRENT_PLAYER = PLAYER_X;
            }
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

        textfield.setText("X Wins");
    }

    public void setOWin(int a, int b, int c) {
        buttons[a].setBackground(Color.green);
        buttons[b].setBackground(Color.green);
        buttons[c].setBackground(Color.green);

        textfield.setText("O Wins");
    }

  private Move findBestMoveMiniMax(int[][] board, int player) {
    int bestVal = Integer.MIN_VALUE;
    Move bestMove = new Move(-1, -1);

    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (board[i][j] == EMPTY) {
                board[i][j] = player;
                int moveVal = minimax(board, 0, false, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
                board[i][j] = EMPTY;

                if (moveVal > bestVal) {
                    bestVal = moveVal;
                    bestMove = new Move(i, j);
                }
            }
        }
    }

    return bestMove;
}

private int minimax(int[][] board, int depth, boolean isMax, int player, int alpha, int beta) {
    int score = evaluate(board, player);

    if (score == 1000 || score == -1000) { // Immediate win or loss
        return score;
    }

    if (!isMovesLeft(board)) {
        return 0; // Tie state
    }

    if (depth >= 6) { // Depth limit (adjust as needed)
        return score; // Return current evaluation score
    }

    int opponent = (player == PLAYER_X) ? PLAYER_O : PLAYER_X;

    if (isMax) { // Maximizing player's turn
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = player;
                    best = Math.max(best, minimax(board, depth + 1, !isMax, player, alpha, beta));
                    alpha = Math.max(alpha, best);
                    board[i][j] = EMPTY;
                    if (beta <= alpha) {
                        break; // Beta cutoff
                    }
                }
            }
        }
        return best;
    } else { // Minimizing player's turn
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = opponent;
                    best = Math.min(best, minimax(board, depth + 1, !isMax, player, alpha, beta));
                    beta = Math.min(beta, best);
                    board[i][j] = EMPTY;
                    if (beta <= alpha) {
                        break; // Alpha cutoff
                    }
                }
            }
        }
        return best;
    }
}


private int evaluate(int[][] board, int player) {
    int opponent = (player == PLAYER_X) ? PLAYER_O : PLAYER_X;
    int score = 0;

    // Evaluate rows and columns
    for (int i = 0; i < SIZE; i++) {
        // Rows
        score += evaluateLine(board[i][0], board[i][1], board[i][2], player, opponent);
        // Columns
        score += evaluateLine(board[0][i], board[1][i], board[2][i], player, opponent);
    }

    // Evaluate diagonals
    score += evaluateLine(board[0][0], board[1][1], board[2][2], player, opponent);
    score += evaluateLine(board[0][2], board[1][1], board[2][0], player, opponent);

    // If an immediate win or loss is detected, return that score directly
    if (score == 1000 || score == -1000) {
        return score;
    }

    // Add weights for strategic positions
    score += evaluatePositionalWeights(board, player);

    return score;
}

private int evaluateLine(int cell1, int cell2, int cell3, int player, int opponent) {
    int score = 0;

    // Evaluate for player
    if (cell1 == player && cell2 == player && cell3 == player) {
        return 1000; // Player wins
    } else if (cell1 == opponent && cell2 == opponent && cell3 == opponent) {
        return -1000; // Opponent wins
    }

    return score;
}


private int evaluatePositionalWeights(int[][] board, int player) {
    int score = 0;
    int center = board[1][1];
    int corners = board[0][0] + board[0][2] + board[2][0] + board[2][2];
    int edges = board[0][1] + board[1][0] + board[1][2] + board[2][1];

    // Evaluate center control
    if (center == player) {
        score += 50;
    }

    // Evaluate corner control
    if (corners > 0) {
        score += 10 * corners;
    }

    // Evaluate edge control
    if (edges > 0) {
        score += 5 * edges;
    }

    // Evaluate forking opportunities
    int forkCount = countForks(board, player);
    score += 100 * forkCount;

    return score;
}

private int countForks(int[][] board, int player) {
    int forkCount = 0;

    // Check all possible pairs of empty cells
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (board[i][j] == EMPTY) {
                board[i][j] = player; // Place player's move temporarily
                int threats = countThreats(board, player); // Count resulting threats
                board[i][j] = EMPTY; // Reset cell

                if (threats >= 2) {
                    forkCount++;
                }
            }
        }
    }

    return forkCount;
}

private int countThreats(int[][] board, int player) {
    int threatCount = 0;

    // Check rows and columns
    for (int i = 0; i < SIZE; i++) {
        if ((board[i][0] == player && board[i][1] == player && board[i][2] == EMPTY) ||
            (board[i][0] == player && board[i][2] == player && board[i][1] == EMPTY) ||
            (board[i][1] == player && board[i][2] == player && board[i][0] == EMPTY)) {
            threatCount++;
        }
        if ((board[0][i] == player && board[1][i] == player && board[2][i] == EMPTY) ||
            (board[0][i] == player && board[2][i] == player && board[1][i] == EMPTY) ||
            (board[1][i] == player && board[2][i] == player && board[0][i] == EMPTY)) {
            threatCount++;
        }
    }

    // Check diagonals
    if ((board[0][0] == player && board[1][1] == player && board[2][2] == EMPTY) ||
        (board[0][0] == player && board[2][2] == player && board[1][1] == EMPTY) ||
        (board[1][1] == player && board[2][2] == player && board[0][0] == EMPTY)) {
        threatCount++;
    }
    if ((board[0][2] == player && board[1][1] == player && board[2][0] == EMPTY) ||
        (board[0][2] == player && board[2][0] == player && board[1][1] == EMPTY) ||
        (board[1][1] == player && board[2][0] == player && board[0][2] == EMPTY)) {
        threatCount++;
    }

    return threatCount;
}



private boolean isMovesLeft(int[][] board) {
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (board[i][j] == EMPTY) {
                return true;
            }
        }
    }
    return false;
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

    public String getPlayerSymbol(int player) {
        if (player == PLAYER_X) {
            return "X";
        } else {
            return "O";
        }
    }

    public void aiMove(int player) {
        int[][] board = getBoardState(); // get current 2d array
        if (checkWin()) return;

        if (player == MINIMAX) {
            Move bestMove = findBestMoveMiniMax(board, player); // determines best move using minimax algorithm
            buttons[bestMove.row * 3 + bestMove.col].setForeground(new Color(255, 0, 0)); // *3 converts 2d array into 1d array for GUI to access e.g., buttons[i] == x
            buttons[bestMove.row * 3 + bestMove.col].setText(getPlayerSymbol(player));
        } else {
            Move bestMove = findBestMoveGreedy(board, player); // determines best move using greedy algorithm
            buttons[bestMove.row * 3 + bestMove.col].setForeground(new Color(0, 0, 255)); // *3 converts 2d array into 1d array for GUI to access e.g., buttons[i] == x
            buttons[bestMove.row * 3 + bestMove.col].setText(getPlayerSymbol(player));
        }

        checkWin();
    }

    private static class Move { // setup variable
        int row, col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private Move findBestMoveGreedy(int[][] board, int player) {
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