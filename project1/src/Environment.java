import java.util.ArrayList;

public class Environment {
    public State current_state;
    public int width, height;
    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = ' ';
    int most_advanced_white = 0;
    int most_advanced_black = height;

    // Constructor
    public Environment(int width, int height){
        this.width = width;
        this.height = height;
        this.current_state = new State(width, height);
    }

    // Heuristic function
    private int heuristic(){
        // distance of most advanced black piece to row 1> - <distance of most advanced white piece to row H>
        if (most_advanced_white == height-1) {
            return 100;
        }
        if (most_advanced_black == 1) {
            return -100;
        }
        // Make if draw statement???

        // terminal state
       return ((most_advanced_black-1)-(height-most_advanced_white));
    }


    private boolean can_move_n_steps_forward(State state, int y, int max_height_black, int max_height_white){
        if (state.white_turn && y <= max_height_white) {
            return true;
        }
        return !state.white_turn && y >= max_height_black;
    }

    private boolean can_move_right(int x){
        return x <= this.width - 3;
    }

    private boolean can_move_left(int x){
        return x >= 2;
    }

    private void get_moves(State state, ArrayList<Move> moves, int y, int x){
        char opponent = state.white_turn ? BLACK : WHITE;
        // Set the step variables, > 0 for white and < 0 for black
        int one_step = state.white_turn ? 1 : -1;
        int two_steps = state.white_turn ? 2 : -2;

        /*
        * Order move generation based on the which moves are most likely to give the best
        * result. I.e. a diagonal "kill" move is probably more beneficial than moving
        * 1 step forward and 2 steps to the side.
        * Currently:
        *   Kill diagonal
        *   Move 2 steps forward
        *   Move 1 step forward
        */

        // Diagonal (capture) is opponent there ?
        // Diagonal right
        if (x+1 < this.width - 1 && y+one_step < this.height && state.board[y+one_step][x+1] == opponent) {
//            System.err.println("ADDED DIAGONAL RIGHT");
//            System.err.println((new Move(x, y, x+1, y+one_step)));
            moves.add(new Move(x, y, x+1, y+one_step));
//              Check is new move is most advanced
            if (state.white_turn && most_advanced_white < y+one_step) {
                most_advanced_white = y+one_step;
            }
            else if (!state.white_turn && most_advanced_black > y+one_step) {
                most_advanced_black = y+one_step;
            }
        }
        // Diagonal left
        if (x-1 > 0 && y+one_step < this.height && state.board[y+one_step][x-1] == opponent) {
//            System.err.println("ADDED DIAGONAL LEFT");
//            System.err.println((new Move(x, y, x-1, y+one_step)));
            moves.add(new Move(x, y, x-1, y+one_step));
//              Check is new move is most advanced
            if (state.white_turn && most_advanced_white < y+one_step) {
                most_advanced_white = y+one_step;
            }
            else if (!state.white_turn && most_advanced_black > y+one_step) {
                most_advanced_black = y+one_step;
            }
        }


        // Two steps forward and one step left/right
         if (can_move_n_steps_forward(state, y, 2, this.height-3)) {
            // Left step
            if (x - 1 > 0 && state.board[y + two_steps][x - 1] == EMPTY) {
//                System.err.println("ADDED MOVE 2 FORWARD 1 LEFT");
//                System.err.println((new Move(x, y, x - 1, y + two_steps)));
                moves.add(new Move(x, y, x - 1, y + two_steps));
//              Check is new move is most advanced
                if (state.white_turn && most_advanced_white < y+two_steps) {
                    most_advanced_white = y+two_steps;
                }
                else if (!state.white_turn && most_advanced_black > y+two_steps) {
                    most_advanced_black = y+two_steps;
                }
            }
            // Right step
            if (x < this.width - 1 && state.board[y + two_steps][x + 1] == EMPTY) {
//                System.err.println("ADDED MOVE 2 FORWARD 1 RIGHT");
//                System.err.println((new Move(x, y, x + 1, y + two_steps)));
                moves.add(new Move(x, y, x + 1, y + two_steps));
            }
        }


        // One step forward and two steps left/right
        if (can_move_right(x)) {
            //System.err.println(this.width);
            //System.err.println(x);
            if (x <= this.width-2 && state.board[y+one_step][x+2] == EMPTY) {
//                System.err.println("ADDED MOVE 2 RIGHT 1 FORWARD");
//                System.err.println((new Move(x, y, x+2, y+one_step)));
                moves.add(new Move(x, y, x+2, y+one_step));
            }
//              Check is new move is most advanced
            if (state.white_turn && most_advanced_white < y+two_steps) {
                most_advanced_white = y+two_steps;
            }
            else if (!state.white_turn && most_advanced_black > y+two_steps) {
                most_advanced_black = y+two_steps;
            }
        }

        if (can_move_left(x)) {
            //System.err.println(this.width);
            //System.err.println(x);

            if (x-2 > 0 && state.board[y+one_step][x-2] == EMPTY) {
//                System.err.println("ADDED MOVE 2 LEFT 1 FORWARD");
//                System.err.println((new Move(x, y, x-2, y+one_step)));
                moves.add(new Move(x, y, x-2, y+one_step));
            }
//              Check is new move is most advanced
            if (state.white_turn && most_advanced_white < y+two_steps) {
                most_advanced_white = y+two_steps;
            }
            else if (!state.white_turn && most_advanced_black > y+two_steps) {
                most_advanced_black = y+two_steps;
            }
        }
    }

    public ArrayList<Move> get_legal_moves(State state){
        ArrayList<Move> moves = new ArrayList<>();
        char friendly = state.white_turn ? WHITE : BLACK;

        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                if (state.board[y][x] == friendly) {
                    get_moves(state, moves, y, x);
                }
            }
        }

        return moves;
    }

    public void move(State state, Move move){
        // set new position in what was old position. then make the old position be empty and flip the turn.
        state.board[move.y2][move.x2] = state.board[move.y1][move.x1];
        state.board[move.y1][move.x1] = EMPTY;
        state.white_turn = !state.white_turn;
    }

    private boolean was_diagonal_move(Move move){
        // diagonal like a pawn in chess
        // Was diagonal right
        if (move.y2 -1 == move.y1 && move.x2 -1 == move.x1) {
            return true;
        }
        // Was diagonal left
        if (move.y2 +1 == move.y1 && move.x2 -1 == move.x1) {
            return true;
        }
        if (move.y2 -1 == move.y1 && move.x2 +1 == move.x1) {
            return true;
        }
        if (move.y2 +1 == move.y1 && move.x2 +1 == move.x1) {
            return true;
        }
        return false;
    }

    public void undo_move(State state, Move move){
        if (was_diagonal_move(move)) {
            state.board[move.y1][move.x1] = state.board[move.y2][move.x2];
            state.board[move.y2][move.x2] = state.white_turn ? WHITE : BLACK;
        }else{
            char tmp = state.board[move.y1][move.x1];
            state.board[move.y1][move.x1] = state.board[move.y2][move.x2];
            state.board[move.y2][move.x2] = tmp;
        }

        state.white_turn = !state.white_turn;
    }

    // TODO: Check whether state can be stored as class variable instead of passing it down
    private double alpha_beta(int depth, State state, double alpha, double beta) {
        if (depth <= 0) {
            return heuristic();
        }
        double best_value = Double.NEGATIVE_INFINITY;
        // Initialize value as 0 since a draw state is reached if there are no moves to check
        // (and value would never get reassigned)
        double value = 0;
        for (Move m : get_legal_moves(state)) {
            // Perform the move on the state to check successor nodes
            move(state, m);
            // Switch and negate bounds when moving into depth of next move
            value = -alpha_beta(depth - 1, state, -beta, -alpha);
            // Undo the move to revert the current state to its original version
            // and check the other moves from the current state
            undo_move(state, m);

            best_value = Math.max(value, best_value);
            if (best_value > alpha) {
                // Adjust the lower bound
                alpha = best_value;
                // Beta cutoff (Alpha beta pruning)
                if (alpha >= beta) break;
            }
        }
        return best_value;
    }
}
