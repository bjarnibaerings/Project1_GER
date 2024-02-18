public class BetterHeuristics implements Heuristics {
    private Environment env;

    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = ' ';
    private int most_advanced_black_pos, most_advanced_white_pos;
    private int nb_black_pieces, nb_white_pieces;

    public double eval(State current_state, Environment env) {
        this.env = env;
        double move_score;
        // Account for array index -1 and second row from top -1
        most_advanced_black_pos = this.env.height - 2;
        most_advanced_white_pos = 1;
        nb_white_pieces = nb_black_pieces = 0;
        // Update all relevant values
        update_board_information(current_state);
        most_advanced_black_pos = this.env.height - 1 - most_advanced_black_pos;

        // Check if move is a victory move
        boolean black_win_condition = most_advanced_black_pos == 0;
        boolean white_win_condition = most_advanced_white_pos == this.env.height-1;

        // White reaches end before black
        if (white_win_condition && !black_win_condition) {
            return 100;
        }

        // Black reaches end before white
        if (black_win_condition && !white_win_condition) {
            return -100;
        }

        // Minus in front, to properly calculate score for relevant side
        move_score = -((most_advanced_black_pos - 1) - most_advanced_white_pos);
        // distance of most advanced black piece to row 1> - <distance of most advanced white piece to row H>
        // terminal state
        return move_score;
    }

    private void update_board_information(State state) {
        for (int y = 0; y < this.env.height; y++) {
            for (int x = 0; x < this.env.width; x++) {
                char piece = state.board[y][x];
                if (piece == WHITE) {
                    nb_white_pieces++;
                    most_advanced_white_pos = Math.max(y, most_advanced_white_pos);
                }
                else if (piece == BLACK) {
                    nb_black_pieces++;
                    most_advanced_black_pos = Math.min(y, most_advanced_black_pos);
                }
            }
        }
    }
}
