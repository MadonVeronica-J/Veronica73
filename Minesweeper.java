/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.minesweeper;
import java.util.*;

public class Minesweeper {
    private static final int SIZE = 10; 
    private static final int MINES = 10; 
    private static final char MINE = '*';
    private static final char UNOPENED = '-';
    private static final char FLAG = 'F';
    private static final char EMPTY = ' ';

    private static char[][] board = new char[SIZE][SIZE]; 
    private static char[][] minesBoard = new char[SIZE][SIZE]; 
    private static boolean[][] revealed = new boolean[SIZE][SIZE]; 
    private static boolean gameOver = false;

     
    private static void initializeBoards() {
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(board[i], UNOPENED);
            Arrays.fill(minesBoard[i], EMPTY);
        }
    }

    
    private static void placeMines() {
        int minesPlaced = 0;
        Random rand = new Random();
        
        while (minesPlaced < MINES) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);

            if (minesBoard[row][col] != MINE) {
                minesBoard[row][col] = MINE;
                minesPlaced++;
            }
        }
    }


    private static void playGame() {
        Scanner sc = new Scanner(System.in);
        while (!gameOver) {
            printBoard();
            System.out.println("Enter row and column to reveal(eg: 3 4)\n or type 'F' to place flag(eg: F 3 4)");
            String input = sc.nextLine();

            String[] parts = input.split(" ");
            
            if(parts[0].equals("F")){
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                placeFlag(row, col);
            }
            else {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                revealCell(row, col);
            }

            if (checkWin()) {
                gameOver = true;
                System.out.println("You've solved the Game");
            }
        }
    }

    
    private static void revealCell(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || revealed[row][col]) {
            return; 
        }

        if (minesBoard[row][col] == MINE) {
            gameOver = true;
            revealMines();
            printBoard();
            System.out.println("Boom! You hit a mine. Game over.");
            return;
        }

        revealed[row][col] = true;
        int minesAround = countMines(row, col);

        if (minesAround > 0) {
            board[row][col] = (char) ('0' + minesAround);
        } 
        else {
            board[row][col] = EMPTY;
            
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        revealCell(row + i, col + j);
                    }
                }
            }
        }
    }

    
    private static int countMines(int row, int col) {
        int minesCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (row + i >= 0 && row + i < SIZE && col + j >= 0 && col + j < SIZE) {
                    if (minesBoard[row + i][col + j] == MINE) {
                        minesCount++;
                    }
                }
            }
        }
        return minesCount;
    }

    
    private static void placeFlag(int row, int col) {
        if (board[row][col] == UNOPENED) {
            board[row][col] = FLAG;
            revealed[row][col] = true;
        }
        else if (board[row][col] == FLAG) {
            board[row][col] = UNOPENED;
            revealed[row][col] = false;
        }
    }

    
    private static void revealMines() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (minesBoard[i][j] == MINE) {
                    board[i][j] = MINE;
                    revealed[i][j] = true;
                }
            }
        }
    }


    private static boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (minesBoard[i][j] != MINE && !revealed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    
    private static void printBoard() {
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
        System.out.println("  +---------------------+");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < SIZE; j++) {
                if (revealed[i][j]) {
                    System.out.print(board[i][j] + " ");
                } 
                else {
                    System.out.print(UNOPENED + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("  +---------------------+");
    }
    
     public static void main(String[] args) {
        initializeBoards();
        placeMines();
        playGame();
    }
}
