public class Move {
    public int x1, y1, x2, y2;

    public Move(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Move add() {
        this.x1 += 1;
        this.y1 += 1;
        this.x2 += 1;
        this.y2 += 1;
        return this;
    }

    public Move sub() {
        this.x1 -= 1;
        this.y1 -= 1;
        this.x2 -= 1;
        this.y2 -= 1;
        return this;
    }

    public String toString(){
        return "(move " + this.x1 + " " + this.y1 + " " + this.x2 + " " + this.y2 +")"; 
    }
}
