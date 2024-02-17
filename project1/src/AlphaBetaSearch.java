
public class AlphaBetaSearch implements SearchAlgorithm{
    private Heuristics heuristic;
    private Environment env;
//    public Move bestMove;
    public int nb_expansions;

    public void init(Heuristics heuristic) {
        this.heuristic = heuristic;
    }

    public Move search(Environment env) {
        this.env = env;
        MoveValuePair bestMovePair = alpha_beta(100, this.env.current_state, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        return bestMovePair.move;
    }


    // TODO: Check whether state can be stored as class variable instead of passing it down
    // Finds the best move for each player by utilizing the Minimax algorithm in
    // conjunction with alpha-beta pruning.
    //
    private MoveValuePair alpha_beta(int depth, State state, double alpha, double beta) {
        // TODO: Store the best move to return
        double value = 0;
        Move bestMove = null;
        MoveValuePair mvp = new MoveValuePair(bestMove, value);

        if (depth <= 0) {
            value = this.heuristic.eval(state, this.env);
            return mvp;
        }

        double best_value = Double.NEGATIVE_INFINITY;
        // Initialize value as 0 since a draw state is reached if there are no moves to check
        // (and value would never get reassigned)

        System.out.println("Currently on depth: " + depth);
        for (Move m : this.env.get_legal_moves(state)) {
            this.nb_expansions++;
            // Perform the move on the state to check successor nodes
            this.env.move(state, m);
            // Switch and negate bounds when moving into depth of next move
            value = -alpha_beta(depth - 1, state, -beta, -alpha).value;
            // Undo the move to revert the current state to its original version
            // and check the other moves from the current state
            this.env.undo_move(state, m);

            if (value > best_value) {
//              best_value = Math.max(value, best_value);
                // Update the best value and the best move
                best_value = value;
                bestMove = m;

            }
            if (best_value > alpha) {
                // Adjust the lower bound
                alpha = best_value;
                // Beta cutoff (Alpha beta pruning)
                if (alpha >= beta) break;
            }
        }

        // TODO: might be buggy
//        best_value = Math.max(value, best_value);
        mvp.value = best_value;
        mvp.move = bestMove;
        return mvp;
    }
}
