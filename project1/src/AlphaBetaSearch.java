import java.util.ArrayList;

public class AlphaBetaSearch implements SearchAlgorithm{
    private Heuristics heuristic;
    private Environment env;
//    public Move bestMove;
    public int nb_expansions = 0;

    public void init(Heuristics heuristic) {
        this.heuristic = heuristic;
    }

    public Move search(Environment env) {
        this.env = env;
        MoveValuePair bestMovePair = alpha_beta(10, this.env.current_state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.err.println(bestMovePair.value + " : " + bestMovePair.move);
        System.err.println("Expansions: " + this.nb_expansions);

        return bestMovePair.move;
    }


    // TODO: Check whether state can be stored as class variable instead of passing it down
    // Finds the best move for each player by utilizing the Minimax algorithm in
    // conjunction with alpha-beta pruning.
    //
    private MoveValuePair alpha_beta(int depth, State state, double alpha, double beta) {
        // Initialize value as 0 since a draw state is reached if there are no moves to check
        // (and value would never get reassigned)
        double value = 0;
        ArrayList<Move> legalMoves = this.env.get_legal_moves(state);
        Move bestMove = null;
        MoveValuePair mvp = new MoveValuePair(bestMove, value);

        if (depth <= 0 || legalMoves.isEmpty()) {
            mvp.value = this.heuristic.eval(state, this.env);
            System.err.println("No Moves: " + mvp.value + " : " + mvp.move);
            return mvp;
        }
        double best_value = Double.NEGATIVE_INFINITY;

//        System.out.println("Currently on depth: " + depth);
        for (Move m : legalMoves) {
            this.nb_expansions++;
            // Perform the move on the state to check successor nodes
            this.env.move(state, m);

            // Switch and negate bounds when moving into depth of next move
            mvp = alpha_beta(depth - 1, state, -beta, -alpha);
            mvp.value = -mvp.value;

            // Undo the move to revert the current state to its original version
            // and check the other moves from the current state
            this.env.undo_move(state, m);

            if (mvp.value > best_value) {
                // Update the best value and the best move
                best_value = mvp.value;
                bestMove = m;

            }
            if (best_value > alpha) {
                // Adjust the lower bound
                alpha = best_value;
                // Beta cutoff (Alpha beta pruning)
                if (alpha >= beta) break;
            }

        }

        mvp.value = best_value;
        mvp.move = bestMove;
//        System.err.println("In abcx " + mvp.value + " : " + mvp.move);
        return mvp;
    }
}
