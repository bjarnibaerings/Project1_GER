public class SimpleHeuristics implements Heuristics {
    private Environment env;

    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = ' ';

    public double eval(State current_state) {
        int most_advanced_white_2 = get_most_advanced_piece(current_state, WHITE);
        // distance of most advanced black piece to row 1> - <distance of most advanced white piece to row H>
        if (most_advanced_white_2 == env.height-1) {
            return 100;
        }
        
        int most_advanced_black_2 = get_most_advanced_piece(current_state, BLACK);
        if (most_advanced_black_2 == 1) {
            return -100;
        }

        // terminal state
       return ((most_advanced_black_2-1)-(env.height-most_advanced_white_2));
    }

    public int get_most_advanced_piece(State state, char friendly){
        if (friendly == 'W') {
            // start at the top (BLACK) and go down the board
            for(int y = env.height-1; y > 0; y--){
                for(int x = 0; x < this.env.width; x++){
                    if (state.board[y][x] == friendly ) {
                        return y;
                    }
                }
            }
        }
        // if black
        // start at the bottem (WHITE) and go UP the board
        for(int y = 0; y < env.height; y++){
            for(int x = 0; x < env.width; x++){
                if (state.board[y][x] == friendly ) {
                    return y;
                }
            }
        }
        // need to have a return 
        return 0;
    }
}
