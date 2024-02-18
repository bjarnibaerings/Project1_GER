public interface SearchAlgorithm {
    public void init(Heuristics heuristic);
    public Move search(Environment env, int play_clock);
}
