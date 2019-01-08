package cs445.a3;

import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;

public class Sudoku {
    private static int[][] original=new int[9][9];

    static boolean isFullSolution(int[][] board) {
       // System.out.println("full");
        for (int i=8;i>=0;i--) {
            for(int j=8;j>=0;j--){
                if(board[i][j]==0) {
                    return false;
                }
            }
        }
        return true;
    }
    static boolean reject(int[][] board) {
     //   System.out.println("rej");
        int row[]=new int[9];
        int column[]=new int[9];
        int box[]=new int[9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
            	if(board[i][j]!=0){
					if(board[i][j]!=original[i][j]&&original[i][j]!=0){
                    	return true;
                	}
            	}
                row[j]=board[i][j];
                column[j]=board[j][i];
                int correctCol=i/3;//int division will truncate!!
                int correctRow=j/3;
                box[j] = board[correctCol*3+correctRow][i*3%9+j%3]; 
            }
            if(!valid(row)){
                return true;
            }
            if(!valid(column)){
                return true;
            }
            if(!valid(box)){
                return true;
            }
            //check rows and columns first before box
        }
        return false;
    }
    private static boolean valid(int[] check){
        Arrays.sort(check);
        for(int i=0;i<8;i++){
            if(check[i]==check[i+1]){
                if(check[i]!=0){
                    return false;
                }
            }
        }
        return true;
    }
    static int[][] extend(int[][] board) {
       // System.out.println();
        //printBoard(board);
        //System.out.println();
        boolean found=false;
        int[][] temp=new int[9][9];
        for (int i=0;i<9;i++) {
            for(int j=0;j<9;j++){
            	if(board[i][j]==0){
            		//if(!found){
            			temp[i][j]=1;
            		//	found=true;
            		//}
            		
            		return temp;
            	}
            	else{
            		temp[i][j]=board[i][j];
            	}
            }
        }
        
        return null;
    }

    static int[][] next(int[][] board) {
        int[][] temp=new int[9][9];
        boolean found=false;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
            	if(i==8&&j==8){
            		found=true;
            	}
            	else{
            		if(j==8){
            			if(board[i+1][0]==0){
            				found=true;
            			}
            		}
            		else if(j<8){
            			if(board[i][j+1]==0){
            				found=true;
            			}
            		}
            	}
            	if(found){
            		if(board[i][j]>=9){
            			return null;
            		}
            		else{
            			temp[i][j]=board[i][j]+1;
            			return temp;
            		}
            	}
            	else{
            		temp[i][j]=board[i][j];
            	}  
       
            }
        }
        return null;
    }

    static void testIsFullSolution() {
        ArrayList<int[][]> fullSolutions = new ArrayList<int[][]>();
     	ArrayList<int[][]> notFullSolutions = new ArrayList<int[][]>();
	   fullSolutions.add(new int[][]{
						{3,6,1,7,9,4,2,5,8},
						{2,8,4,5,6,3,1,9,7},
						{7,9,5,8,2,1,4,6,3},
						{6,2,3,9,7,8,5,4,1},
						{5,7,9,1,4,6,3,8,2},
						{1,4,8,3,5,2,9,7,6},
						{9,3,7,2,8,5,6,1,4},
						{4,5,2,6,1,7,8,3,9},
						{8,1,6,4,3,9,7,2,5}
						});
        fullSolutions.add(new int[][]{
                        {3,3,1,7,9,4,2,5,8},
                        {2,8,4,5,6,3,1,9,7},
                        {7,9,5,8,2,1,4,6,3},
                        {6,2,3,9,7,8,5,4,1},
                        {5,7,9,1,4,6,3,8,2},
                        {1,4,8,3,5,2,9,7,6},
                        {9,3,7,2,8,5,6,1,4},
                        {4,5,2,6,1,7,8,3,9},
                        {8,1,6,4,3,9,7,2,5}
                        });
        notFullSolutions.add(new int[][]{
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0}
                        });
		notFullSolutions.add(new int[][]{
						{3,6,0,7,9,4,2,5,8},
						{2,8,4,5,6,3,1,9,7},
						{7,9,5,8,2,1,4,6,3},
						{6,2,3,9,7,8,5,4,1},
						{5,7,9,1,4,6,3,8,2},
						{1,4,8,3,5,2,9,7,6},
						{9,3,7,2,8,5,6,1,4},
						{4,5,2,6,1,7,8,3,9},
						{8,1,6,4,3,9,7,2,5}
						});
        notFullSolutions.add(new int[][]{
                        {3,3,0,7,9,4,2,5,8},
                        {2,8,4,5,6,3,1,9,7},
                        {7,9,5,8,2,1,4,6,3},
                        {6,2,3,9,7,8,5,4,1},
                        {5,7,9,1,4,6,3,8,2},
                        {1,4,8,3,5,2,9,7,6},
                        {9,3,7,2,8,5,6,1,4},
                        {4,5,2,6,1,7,8,3,9},
                        {8,1,6,4,3,9,7,2,5}
                        });
        System.out.println("TESTING FULLSOLUTION METHOD\n");
        System.out.println("EXPECTING TRUE USING A FULL WORKING SOLUTION:"+isFullSolution(fullSolutions.get(0)));
        System.out.println("EXPECTING TRUE USING A FULL NOT WORKING SOLUTION:"+isFullSolution(fullSolutions.get(1)));
        System.out.println("\nEXPECTING FALSE USING ALL 0'S:"+isFullSolution(notFullSolutions.get(0)));
        System.out.println("EXPECTING FALSE USING PARTIAL CORRECT SOLUTION:"+isFullSolution(notFullSolutions.get(1)));
        System.out.println("EXPECTING FALSE USING PARTIAL INCORRECT SOLUTION:"+isFullSolution(notFullSolutions.get(2)));
    }
    static void testReject() {
    	ArrayList<int[][]> notRejected = new ArrayList<int[][]>();
     	ArrayList<int[][]> rejected = new ArrayList<int[][]>();
     	int[][] og=new int[9][9];
     	for(int i=0;i<9;i++){
     		for(int j=0;j<9;j++){
     			original[i][j]=0;
     		}
     	}
     	notRejected.add(new int[][]{
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0}
                        });
     	notRejected.add(new int[][]{
                        {3,6,1,7,9,4,2,5,8},
                        {2,8,4,5,6,3,1,9,7},
                        {7,9,5,8,2,1,4,6,3},
                        {6,2,3,9,7,8,5,4,1},
                        {5,7,9,1,4,6,3,8,2},
                        {1,4,8,3,5,2,9,7,6},
                        {9,3,7,2,8,5,6,1,4},
                        {4,5,2,6,1,7,8,3,9},
                        {8,1,6,4,3,9,7,2,5}
                        });
     	notRejected.add(new int[][]{
                        {3,6,1,7,9,4,2,5,8},
                        {2,8,4,5,6,3,1,9,7},
                        {7,9,5,8,2,1,4,6,3},
                        {6,2,0,9,7,8,5,4,1},
                        {5,7,9,1,4,6,3,8,2},
                        {1,4,8,3,0,2,9,7,6},
                        {9,3,7,2,8,5,6,1,4},
                        {4,5,2,6,1,7,8,3,9},
                        {8,1,6,4,3,9,7,2,0}
                        });
        rejected.add(new int[][]{
                        {1,0,0,0,0,1,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0}
                        });
        rejected.add(new int[][]{
                        {1,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {1,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0}
                        });
        rejected.add(new int[][]{
                        {1,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0}
                        });
        rejected.add(new int[][]{
                        {1,1,0,0,0,1,0,0,0},
                        {1,0,0,0,0,0,0,0,0},
                        {0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {1,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0}
                        });
        rejected.add(new int[][]{
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {2,0,0,0,0,0,0,0,0}
                        });
     	rejected.add(new int[][]{
						{0,0,0,0,2,0,0,2,0},
						{2,8,4,5,6,3,1,9,7},
						{7,9,5,0,2,1,4,6,3},
						{6,2,3,9,7,8,5,4,1},
						{5,7,9,1,0,6,3,8,2},
						{1,4,8,3,5,2,9,7,6},
						{9,3,7,2,8,5,6,1,4},
						{4,5,2,6,1,7,8,3,9},
						{8,1,6,4,3,9,7,2,0}
						});
        System.out.println("\nTESTING REJECT METHOD\n");
        System.out.println("EXPECTING TRUE DUPLICATE ROW:"+reject(rejected.get(0)));
        System.out.println("EXPECTING TRUE DUPLICATE COLUMN:"+reject(rejected.get(1)));
        System.out.println("EXPECTING TRUE DUPLICATE BOX:"+reject(rejected.get(2)));
        System.out.println("EXPECTING TRUE DUPLICATE ROW,COLUMN,BOX:"+reject(rejected.get(3)));
        original[8][0]=3;
        System.out.println("EXPECTING TRUE INCONSISTENT VALUE BETWEEN BOARD AND ORIGINAL:"+reject(rejected.get(4)));
        System.out.println("EXPECTING TRUE PARTIAL BOARD WITH BAD VALUE, SIMULATING NEXT METHOD:"+reject(rejected.get(5)));
        System.out.println("\nEXPECTING FALSE ALL 0 BOARD:"+reject(notRejected.get(0)));
        original[8][0]=0;
        System.out.println("EXPECTING FALSE FULL SOLUTION BOARD:"+reject(notRejected.get(1)));
        System.out.println("EXPECTING FALSE PARTIAL CORRECT SOLUTION:"+reject(notRejected.get(2)));
    }

    static void testExtend() {
       	ArrayList<int[][]> noExtend = new ArrayList<int[][]>();
     	ArrayList<int[][]> extend = new ArrayList<int[][]>();
     	for(int i=0;i<9;i++){
     		for(int j=0;j<9;j++){
     			original[i][j]=0;
     		}
     	}
     	noExtend.add(new int[][]{
                        {3,6,1,7,9,4,2,5,8},
                        {2,8,4,5,6,3,1,9,7},
                        {7,9,5,8,2,1,4,6,3},
                        {6,2,3,9,7,8,5,4,1},
                        {5,7,9,1,4,6,3,8,2},
                        {1,4,8,3,5,2,9,7,6},
                        {9,3,7,2,8,5,6,1,4},
                        {4,5,2,6,1,7,8,3,9},
                        {8,1,6,4,3,9,7,2,5}
                        });
     	noExtend.add(new int[][]{
						{1,2,3,4,5,6,7,8,9},
						{1,1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1,1},
						{1,1,1,1,1,1,1,1,1}
						});
     	extend.add(new int[][]{
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0}
						});
     	extend.add(new int[][]{
                        {3,6,1,7,9,4,2,5,8},
                        {2,8,4,5,6,3,1,9,7},
                        {7,9,5,8,2,1,4,6,3},
                        {6,2,3,9,7,8,5,4,1},
                        {5,7,9,1,4,6,3,8,2},
                        {1,4,8,3,5,2,9,7,6},
                        {9,3,7,2,8,5,6,1,4},
                        {4,5,2,6,1,7,8,3,9},
                        {8,1,6,4,3,9,7,0,5}
                        });
     	extend.add(new int[][]{
                        {3,6,1,7,9,4,2,5,8},
                        {2,8,4,5,6,3,1,9,7},
                        {7,9,5,8,2,1,4,6,3},
                        {6,2,3,9,7,8,5,4,1},
                        {5,7,9,1,4,6,3,8,2},
                        {1,4,8,3,5,2,9,7,6},
                        {9,3,7,2,8,5,6,1,4},
                        {4,5,2,6,1,7,8,3,3},
                        {8,1,6,4,3,7,7,0,3}
                        });
        System.out.println("\nTESTING EXTEND METHOD\n");
        System.out.println("EXPECTING EXTEND FOR EMPTY BOARD(POSITION[0][0]=1):");
        printBoard(extend(extend.get(0)));
        System.out.println("EXPECTING EXTEND FOR PARTIAL BOARD(POSITION[8][7]=1):");
        printBoard(extend(extend.get(1)));
        System.out.println("EXPECTING EXTEND FOR INCORRECT PARTIAL BOARD(POSITION[8][7]=1):");
        printBoard(extend(extend.get(2)));
        System.out.println("\nEXPECTING NULL FOR FULL SOLUTION:"+extend(noExtend.get(0)));
        System.out.println("EXPETING NULL FOR FULL INCORRECT SOLUTION:"+extend(noExtend.get(1)));
    }

    static void testNext() {
        ArrayList<int[][]> noNext = new ArrayList<int[][]>();
     	ArrayList<int[][]> next = new ArrayList<int[][]>();
     	for(int i=0;i<9;i++){
     		for(int j=0;j<9;j++){
     			original[i][j]=0;
     		}
     	}
     	noNext.add(new int[][]{
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9}
                        });
     	noNext.add(new int[][]{
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9}
                        });
     	next.add(new int[][]{
                        {1,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0}
                        });
     	next.add(new int[][]{
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,9},
						{9,9,9,9,9,9,9,9,1}
						});
        next.add(new int[][]{
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,4,0,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9},
                        {9,9,9,9,9,9,9,9,9}
                        });


        System.out.println("\nTESTING NEXT METHOD\n");
        System.out.println("EXPECTING NEXT FOR EMPTY BOARD(POSITION[0][0]=2):");
        printBoard(next(next.get(0)));
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                original[i][j]=9;
            }
        }
        original[8][8]=0;
        System.out.println("EXPECTING NEXT FOR LAST ELEMENT(POSITION[8][8]=2):");
        printBoard(next(next.get(1)));
        System.out.println("EXPECTING NEXT FOR PARTIAL SOLUTION(POSITION[5][5]=5):");
        original[8][8]=9;
        original[5][5]=0;
        original[5][6]=0;
        printBoard(next(next.get(2)));
        original[5][5]=9;
        System.out.println("\nEXPECTING NULL FOR INCORRECT FILLED BOARD:"+next(noNext.get(0)));
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                original[i][j]=1;
            }
        }
        System.out.println("EXPECTING NULL FOR FULL SOLUTION:"+next(noNext.get(1)));
    }
    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("No Solution");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else {
        	int[][] board = readBoard(args[0]);
            printBoard(board);
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    original[i][j]=board[i][j];
                    board[i][j]=0;
                }
            } 
            System.out.println("Solution:");
            if(reject(board)){
            	System.out.println("No Solution");
            	System.exit(0);
            }
            printBoard(solve(board));
        }
    }
}

