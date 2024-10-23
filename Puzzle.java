/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.puzzle;

import java.util.*;

public class Puzzle {
    private static final int SIZE = 4;
    private int[][] board;
    private int emptyRow, emptyCol;

    public Puzzle() {
        board = new int[SIZE][SIZE];
        shuffleBoard();
    }

    private void shuffleBoard() {
        List<Integer> lst = new ArrayList<>();
        
        for(int i = 0; i < (SIZE * SIZE) - 1; i++){
            lst.add(i+1);
        }
        
        Collections.shuffle(lst);
        int index = 0;
        
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(i == SIZE - 1 && j == SIZE - 1){
                    board[i][j] =  0;
                }
                else{
                    board[i][j] = lst.get(index++);
                }
            }
        }
        
        emptyRow = SIZE - 1;
        emptyCol = SIZE - 1;
    }
    
    public boolean validMove(int row, int col){
        
        if(row + 1 == emptyRow && col == emptyCol) return true;
        else if(row - 1 == emptyRow && col == emptyCol) return true;
        else if(row == emptyRow && col + 1 == emptyCol) return true;
        else if(row == emptyRow && col - 1 == emptyCol) return true;
        return false;
        
    }
    
    public void move(int num) {
        int row = 0, col = 0;
        
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(board[i][j] == num){
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        
        if(validMove(row, col)){
            board[row][col] = 0;
            board[emptyRow][emptyCol] = num;
            emptyRow = row;
            emptyCol = col;
        }
        else{
            System.out.println("Invalid move");
        }
        
    }

    public boolean isSolved() {
        int num = 1;
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == SIZE - 1 && j == SIZE - 1) {
                    if (board[i][j] != 0) return false;
                } 
                else {
                    if (board[i][j] != num++) return false;
                }
            }
        }
        
        return true;
    }

    public void printBoard() {
        
        for (int[] row : board) {
            for (int num : row) {
                if (num == 0) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%2d ", num);
                }
            }
            System.out.println();
        }
        
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Puzzle puzzle = new Puzzle();

        while (!puzzle.isSolved()) {
            puzzle.printBoard();
            System.out.println("Enter the number to move: ");
            
            int move = scanner.nextInt();

            puzzle.move(move);
        }

        System.out.println("You've solved the puzzle!");
    }
}

