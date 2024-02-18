import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class AlphaBetaSearch implements SearchAlgorithm{
    private Heuristics heuristic;
    private Environment env;
    private Move current_best_move;
    public int nb_expansions = 0;
    private long start_time;
    private int play_clock;
    public void init(Heuristics heuristic) {
        this.heuristic = heuristic;
    }

    public Move search(Environment env, int play_clock) {
        this.env = env;
        this.play_clock = play_clock * 1000; // Convert from seconds to milliseconds
        int depth = 8;
        boolean top_level_iteration = true;
        try {
            this.start_time = System.currentTimeMillis();
            MoveValuePair bestMovePair = alpha_beta(
                    depth,
                    this.env.current_state,
                    Double.NEGATIVE_INFINITY,
                    Double.POSITIVE_INFINITY,
                    top_level_iteration
            );
            System.err.println(bestMovePair);
            System.err.println("Expansions: " + this.nb_expansions);

            return bestMovePair.move;
        } catch (Exception TimeoutException) {
            System.err.println("OUT OF TIME: " + current_best_move);
            return current_best_move;
        }
    }


    // TODO: Check whether state can be stored as class variable instead of passing it down
    // Finds the best move for each player by utilizing the Minimax algorithm in
    // conjunction with alpha-beta pruning.
    private MoveValuePair alpha_beta(int depth, State state, double alpha, double beta, boolean top_level) throws TimeoutException {
        // Check whether search has run put of time
        if (System.currentTimeMillis() - this.start_time >= this.play_clock) {
            throw new TimeoutException("playclock ran out");
        }

        // Initialize value as 0 since a draw state is reached if there are no moves to check
        // (and value would never get reassigned)

        double value = 0;
        ArrayList<Move> legalMoves = this.env.get_legal_moves(state);
        Move bestMove = null;
        MoveValuePair mvp = new MoveValuePair(bestMove, value);

        if (depth <= 0 || legalMoves.isEmpty()) {
            mvp.value = this.heuristic.eval(state, this.env);
            return mvp;
        }
        double best_value = Double.NEGATIVE_INFINITY;

//        System.out.println("Currently on depth: " + depth);
        for (Move m : legalMoves) {
            this.nb_expansions++;
            // Perform the move on the state to check successor nodes
            this.env.move(state, m);

            // Send in false to only store best moves from the top-level state
            // Switch and negate bounds when moving into depth of next move
            mvp = alpha_beta(depth - 1, state, -beta, -alpha, false);
            mvp.value = -mvp.value;

            if (bestMove == null) {
                bestMove = m;
            }

            // Undo the move to revert the current state to its original version
            // and check the other moves from the current state
            this.env.undo_move(state, m);

            if (mvp.value > best_value) {
                // Update the best value and the best move
                best_value = mvp.value;
                bestMove = m;
                mvp.move = bestMove;
                if (top_level) {
                    current_best_move = bestMove;
                }
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
//        System.err.println(mvp);
        return mvp;
    }
}
