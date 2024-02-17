import java.util.ArrayList;

public class MyAgent implements Agent {


	private String role; // the name of this agent's role (white or black)
	private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
	private boolean myTurn; // whether it is this agent's turn or not
	private int width, height; // dimensions of the board
	private Environment env;
	private SearchAlgorithm algorithm;
	/*
		init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
	*/
    public void init(String role, int width, int height, int playclock) {
		this.role = role;
		this.playclock = playclock;
		myTurn = !role.equals("white");
		this.width = width;
		this.height = height;
		// TODO: add your own initialization code here
		this.env = new Environment(width, height);
		this.algorithm = new AlphaBetaSearch();
		this.algorithm.init(new SimpleHeuristics());
		// Heuristic variable
		// Alphabeta varible???
    }

	

	// lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
    	if (lastMove != null) {
    		int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
    		String roleOfLastPlayer;
    		if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
    			roleOfLastPlayer = "white";
    		} else {
    			roleOfLastPlayer = "black";
    		}
   			System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
    		// Update the internal world model according to the action that was just executed
			// sub() removes 0 to convert move from BoardSpace to ProgramSpace
			this.env.move(this.env.current_state, new Move(x1, y1, x2, y2).sub());
    		
    	}


		
    	// update turn (above that line it myTurn is still for the previous state)
		myTurn = !myTurn;
		if (myTurn) {
			// TODO: 2. run alpha-beta search to determine the best move
//			ArrayList<Move> moves = this.env.get_legal_moves(this.env.current_state);
//			Move firstMove = moves.get(0);
//			firstMove.add();

			// Get best move
			Move bestMove = algorithm.search(this.env);

			// this needs to be replaced with the actual best move.
			// Move best_move = get_best_move();
//			System.out.println(this.env.current_state);
//			System.out.println(this.env.heruistic());
			return bestMove.toString();
		} else {
			return "noop";
		}
	}

	// is called when the game is over or the match is aborted
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match
		this.env = null;
		this.algorithm = null;

	}
}
