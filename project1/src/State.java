public class State{

    public char[][] board;
    // whose turn is it
    public boolean white_turn;

    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = ' ';

    private final int width;
    // constructer
    public State(int width, int height){
        // board 2d char array of height and width
        this.board = new char[height][width];
        this.white_turn = true;
        // ask TA why no this.height
        this.width = width;

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(i < 2) // First 2 rows
                    this.board[i][j] = WHITE;
                else if (i > height-3) // Last 2 rows (-3 since arr.len is height - 1)
                    this.board[i][j] = BLACK;
                else
                    this.board[i][j] = EMPTY;
            }
        }
    }

    // print out board
    public String toString(){
        int dash_count = this.width * 5-6;
        String line = "\n" + "-".repeat(dash_count) + "\n";
        String result = line;

        for(int i = 0; i < this.width; i++){
            result += new String(this.board[i]).replaceAll("", " ! ");
            result += line;
        }
        return result;
    }
}