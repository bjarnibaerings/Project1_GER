import java.util.ArrayList;

public class Environment {
    public State current_state;
    public int width, height;
    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = ' ';

    // Constructer
    public Environment(int width, int height){
        this.width = width;
        this.height = height;
        this.current_state = new State(width, height);
    }


    private boolean can_move_n_steps_forward(State state, int y, int max_height_black, int max_height_white){
        if (state.white_turn && y <= max_height_white) {
            return true;
        }
        if (!state.white_turn && y >= max_height_black) {
            return true;
        }
        return false;
    }

    private boolean can_move_right(State state, int x){
        if (x <= this.width-3) {
            return true;
        }
        return false;
    }

    private boolean can_move_left(State state, int x){
        if (x >= 2) {
            return true;
        }
        return false;
    }

    private void get_moves(State state, ArrayList<Move> moves, int y, int x){
        char opponent = state.white_turn ? BLACK : WHITE;
        int one_step = state.white_turn ? 1 : -1;
        int two_steps = state.white_turn ? 2 : -2;

        // Two steps forward and one step left/right
        if (can_move_n_steps_forward(state, y, 2, this.height-3)) {
            if (x > 0 && state.board[y+two_steps][x-1] == EMPTY) {
                moves.add(new Move(x, y, x-1, y + two_steps));
            }

            if (x < this.width-1 && state.board[y+two_steps][x+1] == EMPTY) {
                moves.add(new Move(x, y, x+1, y+two_steps));
            }
        }

        // One step forward and two steps left/right

        if (can_move_right(state, x)) {
            if (state.board[y+one_step][x+2] == EMPTY) {
                System.err.println("ADDED MOVE RIGHT");
                moves.add(new Move(x, y, x+2, y+one_step));
            }
        }

        if (can_move_left(state, x)) {
            if (y > 0 && state.board[y+one_step][x-2] == EMPTY) {
                System.err.println("ADDED MOVE LEFT");
                moves.add(new Move(x, y, x-2, y+one_step));
            }
            if (y < this.height-1 && state.board[y+one_step][x-2] == EMPTY) {
                System.err.println("ADDED MOVE LEFT");
                moves.add(new Move(x, y, x-2, y+one_step));
            }
        }

        // Diagonal (capture) is opponent there ?


        /*if (state.board[y+one_step][x+1] == opponent) {
            System.err.println("ADDED DIAGONAL RIGHT");
            moves.add(new Move(x, y, x+1, y+one_step));
        }

        if (state.board[y+one_step][x-1] == opponent) {
            System.err.println("ADDED DIAGONAL LEFT");
            moves.add(new Move(x, y, x-1, y+one_step));
        }*/
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
        if (move.y2 -1 == move.y1 && move.x2 -1 == move.x1) {
            return true;
        }
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
}
