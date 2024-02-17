public class SimpleHeuristics implements Heuristics {
    private Environment env;

    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = ' ';

    public double eval(State current_state, Environment env) {
        this.env = env;
        int most_advanced_white = get_most_advanced_piece(current_state, WHITE);
        // distance of most advanced black piece to row 1> - <distance of most advanced white piece to row H>
        if (most_advanced_white == env.height-1) {
            return 100;
        }
        
        int most_advanced_black = get_most_advanced_piece(current_state, BLACK);
        if (most_advanced_black == 0) {
            return -100;
        }

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
