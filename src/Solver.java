import java.util.ArrayList;
import java.util.Stack;

public class Solver {
	boolean done = false;
	StringBuilder path=new StringBuilder(""); 
	StringBuilder solutionPath;
	int solutionPathDepth = Integer.MAX_VALUE;
	int maxDepth = 10;
	boolean isSolved = false;
	static int[][] puzzleBoard = new int[5][];
	Piece emptyPiece;
 	public void init() {
 		// Create ragged array
 		for(int rows = 0; rows < 5; rows++) {
 			if(rows == 0)
 				puzzleBoard[rows] = new int[1];
 			else
 				puzzleBoard[rows] = new int[3];
 		}
 	
 		puzzleBoard[0][0] = 1; 
 		puzzleBoard[1][0] = 2; puzzleBoard[1][1] = 5; puzzleBoard[1][2] = 3;
 		puzzleBoard[2][0] = 4; puzzleBoard[2][1] = 6; puzzleBoard[2][2] = 9;
 		puzzleBoard[3][0] = 7; puzzleBoard[3][1] = 8; puzzleBoard[3][2] = 12;
 		puzzleBoard[4][0] = 10; puzzleBoard[4][1] = 0; puzzleBoard[4][2] = 11;
 	
 		emptyPiece = emptyPiece();
 	}
	
	public static ArrayList<Piece> getPossibleMoves(Piece emptyPiece) {
		int x = emptyPiece.x;
		int y = emptyPiece.y;
		
		ArrayList<Piece> moves = new ArrayList<Piece>();
		
		// Check up
		if(canMove(new Piece(x-1,y))) {
			moves.add(new Piece(x-1,y, 'U'));
		}		
		// Check right
		if(canMove(new Piece(x,y+1))) {
			moves.add(new Piece(x,y+1, 'R'));
		}
		// Check bottom
		if(canMove(new Piece(x+1,y))) {
			moves.add(new Piece(x+1,y, 'B'));
		}
		// Check left
		if(canMove(new Piece(x,y-1))) {
			moves.add(new Piece(x,y-1,'L'));
		}
		
		return moves;
	}
	
	
	public static void drawPuzzleBoard() {

		print(" ------- ",true);
		printRow(new int[] {puzzleBoard[0][0]});
		printRow(new int[] {puzzleBoard[1][0],puzzleBoard[1][1],puzzleBoard[1][2]});
		printRow(new int[] {puzzleBoard[2][0],puzzleBoard[2][1],puzzleBoard[2][2]});
		printRow(new int[] {puzzleBoard[3][0],puzzleBoard[3][1],puzzleBoard[3][2]});
		printRow(new int[] {puzzleBoard[4][0],puzzleBoard[4][1],puzzleBoard[4][2]});
	}
	
	public static void print(String str,boolean newLine) {
		if(newLine)
			System.out.println(str);
		else
			System.out.print(str);
	}
	
	static void printRow(int[] pieces) {
		int pieceCount = pieces.length;
		// For space above the piece 
		System.out.print("|");
		for(int i = 0; i < pieceCount; i++) {
			System.out.print("       |");
		}
		print("",true);
		// For piece printing part
		System.out.print("|");
		for(int i = 0; i < pieceCount; i++) {
			int length = String.valueOf(pieces[i]).length();
			if(length == 1)
				print("   "+pieces[i]+"   |",false);
			else
				print("   "+pieces[i]+"  |",false);
		}
		// For space below the piece
		print("",true);
		System.out.print("|");
		for(int i = 0; i < pieceCount; i++) {
			System.out.print("       |");
		}
		print("",true);
		for(int i = 0; i < 3; i++) {
			System.out.print(" -------");
		}		
		print("",true);
	}
	
	public static boolean canMove(Piece piece) {
		try {
			int temp = puzzleBoard[piece.x][piece.y];
		}catch(ArrayIndexOutOfBoundsException ex) {
			return false;
		}
		return true;
	}
	
	public static boolean isSolved() {
		int count = 1;
		for(int i = 1; i < 5; i++) {
			for(int j = 0; j < 3; j++) {
				if(puzzleBoard[i][j] != count++) return false;
			}
		}
		return true;
	}
	
	public void solver(int depth) {
	
		if(done) {
//			System.out.println("already solved :");
			return;
		}
		if(isSolved()) {
			
			done = true;
//			drawPuzzleBoard();
//			System.out.println("Solved in depth :"+depth);
//			if(depth < solutionPathDepth) {
//				solutionPathDepth = depth;
				solutionPath = new StringBuilder(path);
//				System.out.println(solutionPath);
//			}else {
//				System.out.println(path);
//			}
			return;
		}
		if(depth > maxDepth) {
			return;
		}
		ArrayList<Piece> moves = getPossibleMoves(emptyPiece);
		for(Piece piece : moves) {
//			drawPuzzleBoard();
			makeMove(piece);
			path.append(piece.position);
			solver(depth+1);
			undoMove(piece);
			path.delete(path.length()-1,path.length());

		}
	}
	
	public void undoMove(Piece piece) {
		int x = piece.x;
		int y = piece.y;
		char direction = piece.position;
		switch(direction) {
		case 'U':
			puzzleBoard[x][y] = puzzleBoard[x+1][y]; // move				
			puzzleBoard[x+1][y] = 0; 
			break;
		case 'R':
			puzzleBoard[x][y] = puzzleBoard[x][y-1]; // move
			puzzleBoard[x][y-1] = 0; 
			break;
		case 'B':
			puzzleBoard[x][y] = puzzleBoard[x-1][y]; // move
			puzzleBoard[x-1][y] = 0; 
			break;
		case 'L':
			puzzleBoard[x][y] = puzzleBoard[x][y+1]; // move
			puzzleBoard[x][y+1] = 0; 
			break;
				
		}
	}
	
	public void makeMove(Piece piece) {
		int x = piece.x;
		int y = piece.y;
		char direction = piece.position;
		switch(direction) {
			case 'U':
				puzzleBoard[x+1][y] = puzzleBoard[x][y]; // move				
				break;
			case 'R':
				puzzleBoard[x][y-1] = puzzleBoard[x][y]; // move
				break;
			case 'B':
				puzzleBoard[x-1][y] = puzzleBoard[x][y]; // move
				break;
			case 'L':
				puzzleBoard[x][y+1] = puzzleBoard[x][y]; // move
				break;
				
		}
		puzzleBoard[x][y] = 0;
		emptyPiece = new Piece(x,y);
	}
	
	public void start() {
		init();
		System.out.println("Initial puzzle board state : ");
		drawPuzzleBoard();
		solver(0);
		
		if(done) {
			StringBuilder optimalSolutionPath = convertToOptimal();
			
			System.out.println("Solution is "+optimalSolutionPath+".");
			int steps = 0;
			for(int i = 0; i < optimalSolutionPath.length(); i++) {
				// Find empty piece location
				Piece emptyPiece =  emptyPiece();
				
				char move = optimalSolutionPath.charAt(i);
				int temp;
				if(move == 'L') {
					temp = puzzleBoard[emptyPiece.x][emptyPiece.y-1];
					puzzleBoard[emptyPiece.x][emptyPiece.y-1] = puzzleBoard[emptyPiece.x][emptyPiece.y];
					puzzleBoard[emptyPiece.x][emptyPiece.y] = temp;
				}
				if(move == 'U') {
					temp = puzzleBoard[emptyPiece.x-1][emptyPiece.y];
					puzzleBoard[emptyPiece.x-1][emptyPiece.y] = puzzleBoard[emptyPiece.x][emptyPiece.y];
					puzzleBoard[emptyPiece.x][emptyPiece.y] = temp;
				}		
				if(move == 'R') {
					temp = puzzleBoard[emptyPiece.x][emptyPiece.y+1];
					puzzleBoard[emptyPiece.x][emptyPiece.y+1] = puzzleBoard[emptyPiece.x][emptyPiece.y];
					puzzleBoard[emptyPiece.x][emptyPiece.y] = temp;
				}
				if(move == 'B') {
					temp = puzzleBoard[emptyPiece.x+1][emptyPiece.y];
					puzzleBoard[emptyPiece.x+1][emptyPiece.y] = puzzleBoard[emptyPiece.x][emptyPiece.y];
					puzzleBoard[emptyPiece.x][emptyPiece.y] = temp;
				}
				System.out.println("STEP : "+(++steps));
				System.out.println("====================");
				
				drawPuzzleBoard();
			}
			System.out.println("DONE");
		//	System.out.println("Solution is "+solutionPath);
		}else
		{
			System.out.println("Sorry, No solution found!");
		}
	}

	public Piece emptyPiece() {
		
		for(int i = 0; i < puzzleBoard.length; i++) {
			for(int j = 0; j < puzzleBoard[i].length; j++) {
				if(puzzleBoard[i][j] == 0)
					return new Piece(i,j);
			}
		}
		return null;
	}
	//RBUBUBULLU
	public StringBuilder convertToOptimal() {
		StringBuilder optimalSolutionPath = new StringBuilder();
		Stack<Character> s = new Stack<Character>();
		for(int i = 0 ; i < solutionPath.length(); i++) {
			char c = solutionPath.charAt(i);
			if(!s.isEmpty()) {
				char top = s.peek();
				if(shouldPOP(c,top))
					s.pop();
				else
					s.add(c);
			}else {
				s.add(c);
			}
		}
		while(!s.isEmpty()) {
			optimalSolutionPath.append(s.pop());
		}
		return  optimalSolutionPath.reverse();
	}
	
	public boolean shouldPOP(char c, char top) {
		if((c == 'U' && top == 'B') | (c == 'B' && top == 'U')) return true;
		if((c == 'R' && top == 'L') | (c == 'L' && top == 'R')) return true;
		return false;
	}
}




