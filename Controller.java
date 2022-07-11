package sample;

import javafx.scene.shape.*;

public class Controller {
    // Getting the numbers and the MESH from Tetris
    public static final int move = Tetris.move;
    public static final int size = Tetris.size;
    public static int xmax = Tetris.xmax;
    public static int ymax = Tetris.ymax;
    public static int[][] MESH = Tetris.MESH;
    public static void moveright(Form form) {
        if (form.a.getX() + move <= xmax - size && form.b.getX() + move <= xmax - size && form.c.getX() + move <= xmax - size && form.d.getX() + move <= xmax - size) {
            int movea = MESH[((int) form.a.getX() / size) + 1][((int) form.a.getY() / size)];
            int moveb = MESH[((int) form.b.getX() / size) + 1][((int) form.b.getY() / size)];
            int movec = MESH[((int) form.c.getX() / size) + 1][((int) form.c.getY() / size)];
            int moved = MESH[((int) form.d.getX() / size) + 1][((int) form.d.getY() / size)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setX(form.a.getX() + move);
                form.b.setX(form.b.getX() + move);
                form.c.setX(form.c.getX() + move);
                form.d.setX(form.d.getX() + move);
            }
        }
    }

    public static void moveleft(Form form) {
        if (form.a.getX() - move >= 0 && form.b.getX() - move >= 0 && form.c.getX() - move >= 0 && form.d.getX() - move >= 0) {
            int movea = MESH[((int) form.a.getX() / size) - 1][((int) form.a.getY() / size)];
            int moveb = MESH[((int) form.b.getX() / size) - 1][((int) form.b.getY() / size)];
            int movec = MESH[((int) form.c.getX() / size) - 1][((int) form.c.getY() / size)];
            int moved = MESH[((int) form.d.getX() / size) - 1][((int) form.d.getY() / size)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setX(form.a.getX() - move);
                form.b.setX(form.b.getX() - move);
                form.c.setX(form.c.getX() - move);
                form.d.setX(form.d.getX() - move);
            }
        }
    }

    public static Form generate() {
        int block = (int) (Math.random() * 100);
        String name;
        Rectangle a = new Rectangle(size -1, size -1), b = new Rectangle(size -1, size -1), c = new Rectangle(size -1, size -1), d = new Rectangle(size -1, size -1);
        if (block < 15) {
            a.setX(xmax / 2 - size);
            b.setX(xmax / 2 - size);
            b.setY(size);
            c.setX(xmax / 2);
            c.setY(size);
            d.setX(xmax / 2 + size);
            d.setY(size);
            name = "j";
        } else if (block < 30) {
            a.setX(xmax / 2 + size);
            b.setX(xmax / 2 - size);
            b.setY(size);
            c.setX(xmax / 2);
            c.setY(size);
            d.setX(xmax / 2 + size);
            d.setY(size);
            name = "l";
        } else if (block < 45) {
            a.setX(xmax / 2 - size);
            b.setX(xmax / 2);
            c.setX(xmax / 2 - size);
            c.setY(size);
            d.setX(xmax / 2);
            d.setY(size);
            name = "o";
        } else if (block < 60) {
            a.setX(xmax / 2 + size);
            b.setX(xmax / 2);
            c.setX(xmax / 2);
            c.setY(size);
            d.setX(xmax / 2 - size);
            d.setY(size);
            name = "s";
        } else if (block < 75) {
            a.setX(xmax / 2 - size);
            b.setX(xmax / 2);
            c.setX(xmax / 2);
            c.setY(size);
            d.setX(xmax / 2 + size);
            name = "t";
        } else if (block < 90) {
            a.setX(xmax / 2 + size);
            b.setX(xmax / 2);
            c.setX(xmax / 2 + size);
            c.setY(size);
            d.setX(xmax / 2 + size + size);
            d.setY(size);
            name = "z";
        } else {
            a.setX(xmax / 2 - size - size);
            b.setX(xmax / 2 - size);
            c.setX(xmax / 2);
            d.setX(xmax / 2 + size);
            name = "i";
        }
        return new Form(a, b, c, d, name);
    }
}