public class SimpleHeuristics implements Heuristics {
    private Environment env;

    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = ' ';

    public double eval(State current_state, Environment env) {
        this.env = env;
        // Get position of most advanced pieces
        int most_advanced_white = get_most_advanced_piece(current_state, WHITE);
        int most_advanced_black = get_most_advanced_piece(current_state, BLACK);

        // Check if move is a victory move
        boolean white_win_condition = most_advanced_white == env.height-1;
        boolean black_win_condition = most_advanced_black == 0;

        // White reaches end before black
        if (white_win_condition && !black_win_condition) {
            return 100;
        }

        // Black reaches end before white
        if (black_win_condition && !white_win_condition) {
            return -100;
        }

        // distance of most advanced black piece to row 1> - <distance of most advanced white piece to row H>
        // terminal state
        return (this.env.height - most_advanced_black) - ((this.env.height - 1) - most_advanced_white);
    }

    private int get_most_advanced_piece(State state, char friendly){
        if (friendly == WHITE) {
            // start at the top (BLACK) and go down the board
            for(int y = this.env.height-1; y >= 0; y--){
                for(int x = 0; x < this.env.width; x++){
                    if (state.board[y][x] == friendly ) {
                        return y;
                    }
                }
            }
        } else {
            // if black
            // start at the bottom (WHITE) and go UP the board
            for(int y = 0; y < this.env.height; y++){
                for(int x = 0; x < this.env.width; x++){
                    if (state.board[y][x] == friendly ) {
                        return y;
                    }
                }
            }
        }
        // need to have a return
        return 0;
    }
}
