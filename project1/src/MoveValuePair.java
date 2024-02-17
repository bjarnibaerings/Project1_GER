public class MoveValuePair {
    public Move move;
    public double value;

    public MoveValuePair(Move move, double value) {
        this.move = move;
        this.value = value;
    }

    public MoveValuePair negate() {
        this.value = -this.value;
        return this;
    }
}
