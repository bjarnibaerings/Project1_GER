public class MoveValuePair {
    private final Move move;
    private final double value;

    public MoveValuePair(Move move, double value) {
        this.move = move;
        this.value = value;
    }

    public Move getMove() {
        return this.move;
    }

    public double getValue() {
        return this.value;
    }
}
