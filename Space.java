/*
 * I think we should use this class instead of using arrays of 2 integers for spaces in the piece class -- Noah
 */

public class Space {
    private int x, y;
    private boolean empty;

    public Space(int a, int b) {
        x = a;
        y = b;
        empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }
}
