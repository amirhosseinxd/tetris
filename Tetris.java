import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Tetris extends Application {
    public static final int move = 25;
    public static final int size = 25;
    public static int xmax = size * 12;
    public static int ymax = size * 24;
    public static int[][] MESH = new int[12][24];
    public static int score = 0;
    private static FlowPane layout = new FlowPane(Orientation.VERTICAL);
    private static Form object;
    private static Scene scene = new Scene(layout, 550, ymax);
    private static int filled = 0;
    private static boolean game = true;
    private static Form nextObj = Controller.generate();
    private static int numoflines = 0;
    private static Pane newLayout = new Pane();
    private static Stage newWindo = new Stage();
    private static Scene secondScene = new Scene(newLayout, xmax + 150, ymax);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        layout.setBackground(new Background(new BackgroundFill(Color.DARKKHAKI, null, null)));
        primaryStage.setTitle("Tetris Project");
        TilePane text = new TilePane();
        text.setAlignment(Pos.CENTER);
        Button butt = new Button("Tetris");
        butt.setFont(new Font("Serif", 30));
        butt.setStyle("-fx-background-color: black; -fx-text-fill: white");
        text.getChildren().add(butt);
        layout.getChildren().add(text);
        Button image = new Button();
        image.setContentDisplay(ContentDisplay.CENTER);
        image.setGraphic(new ImageView("m.png"));
        image.setAlignment(Pos.CENTER);
        layout.getChildren().add(image);
        TilePane but = new TilePane(Orientation.VERTICAL, 10, 10);
        Button newGame = new Button("       new Game       ");
        newGame.setFont(new Font("Serif", 20));
        newGame.setMaxWidth(Integer.MAX_VALUE);
        Button scoresTable = new Button("       Scores Table       ");
        scoresTable.setMaxWidth(Integer.MAX_VALUE);
        Button settings = new Button("       Settings       ");
        settings.setMaxWidth(Integer.MAX_VALUE);
        Button exit = new Button("       Exit       ");
        exit.setMaxWidth(Integer.MAX_VALUE);
        but.setAlignment(Pos.CENTER);
        newGame.setStyle("-fx-background-color: black; -fx-text-fill: white");
        settings.setStyle("-fx-background-color: black; -fx-text-fill: white");
        scoresTable.setStyle("-fx-background-color: black; -fx-text-fill: white");
        exit.setStyle("-fx-background-color: black; -fx-text-fill: white");
        but.getChildren().add(newGame);
        but.getChildren().add(scoresTable);
        but.getChildren().add(settings);
        but.getChildren().add(exit);
        layout.getChildren().add(but);
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
        settings.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FlowPane selayout = new FlowPane(Orientation.HORIZONTAL);
                Scene seScene = new Scene(selayout, 300, 200);
                Stage sewindo = new Stage();
                sewindo.setTitle("setings");
                sewindo.setScene(seScene);
                Label label = new Label("Score per row:  ");
                TextField textField1 = new TextField();
                textField1.setPromptText("score");
                label.setLineSpacing(10.0);
                selayout.getChildren().addAll(label, textField1);
                Label label1 = new Label("Background color: ");
                ColorPicker colorPicker = new ColorPicker(Color.BLUE);
                colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        label1.setBackground(
                                new Background(new BackgroundFill(colorPicker.getValue(), null, null)));
                    }
                });
                selayout.getChildren().addAll(label1, colorPicker);
                sewindo.show();
            }
        });
        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newWindo.setTitle("Game 'Tetris' ");
                newWindo.setScene(secondScene);
                for (int[] a : MESH) {
                    Arrays.fill(a, 0);
                }
                Line line = new Line(xmax, 0, xmax, ymax);
                Button start=new Button("Start");
                start.setLayoutX(xmax+5);
                start.setLayoutY(500);
                start.setFont(new Font("serif",40));
                start.setStyle("-fx-background-color: black; -fx-text-fill: white");
                Text scoretxt = new Text("Score: ");
                scoretxt.setStyle("-fx-font: 20 arial;");
                scoretxt.setY(50);
                scoretxt.setX(xmax + 5);
                Text lvltxt = new Text("Lines: ");
                lvltxt.setStyle("-fx-font: 20 arial;");
                lvltxt.setY(100);
                lvltxt.setX(xmax + 5);
                lvltxt.setFill(Color.GREEN);
                start.setTooltip(new Tooltip("S=down, D=right, A=left "));
                start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        newLayout.getChildren().addAll(scoretxt, line, lvltxt);
                        Form a = nextObj;
                        newLayout.getChildren().addAll(a.a, a.b, a.c, a.d);
                        keyboardcontroll(a);
                        object = a;
                        nextObj = Controller.generate();
                        Timer fall = new Timer();
                        TimerTask task = new TimerTask() {
                            public void run() {
                                Platform.runLater(new Runnable() {
                                    public void run() {
                                        if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0)
                                            filled++;
                                        else
                                            filled = 0;
                                        if (filled == 2) {
                                            Text over = new Text("GAME OVER");
                                            over.setFill(Color.RED);
                                            over.setStyle("-fx-font: 70 arial;");
                                            over.setY(300);
                                            over.setX(10);
                                            newLayout.getChildren().add(over);
                                            game = false;
                                        }
                                        if (filled == 15) {
                                            FlowPane chlayout = new FlowPane(Orientation.VERTICAL);
                                            Scene chwindo = new Scene(chlayout, 250, 250);
                                            Label label = new Label("your name is:   ");
                                            TextField textField1 = new TextField();
                                            textField1.setPromptText("Name");
                                            label.setLineSpacing(40.0);
                                            chlayout.getChildren().addAll(label, textField1);
                                            Button exited = new Button("OK");
                                            exited.setTooltip(new Tooltip("exit in game"));
                                            chlayout.getChildren().add(exited);
                                            exited.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                                @Override
                                                public void handle(MouseEvent event) {
                                                    System.exit(0);
                                                }
                                            });
                                            Stage chwin = new Stage();
                                            chwin.setScene(chwindo);
                                            chwin.show();
                                        }
                                        if (game) {
                                            movedown(object);
                                            scoretxt.setText("Score: " + Integer.toString(score));
                                            lvltxt.setText("Lines: " + Integer.toString(numoflines));
                                        }
                                    }
                                });
                            }
                        };
                        fall.schedule(task, 0, 300);
                    }
                });
                newLayout.getChildren().addAll(start);
                newWindo.setScene(secondScene);
                newWindo.show();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void keyboardcontroll(Form form) {
        secondScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        Controller.moveright(form);
                        break;
                    case D:
                        Controller.moveright(form);
                        break;
                    case DOWN:
                        movedown(form);
                        score++;
                        break;
                    case S:
                        movedown(form);
                        score++;
                        break;
                    case LEFT:
                        Controller.moveleft(form);
                        break;
                    case A:
                        Controller.moveleft(form);
                        break;
                    case UP:
                        rotation(form);
                        break;
                    case W:
                        rotation(form);
                        break;
                }
            }
        });
    }

    private void rotation(Form form) {
        int f = form.form;
        Rectangle a = form.a;
        Rectangle b = form.b;
        Rectangle c = form.c;
        Rectangle d = form.d;
        switch (form.getName()) {
            case "j":
                if (f == 1 && checkbool(a, 1, -1) && checkbool(c, -1, -1) && checkbool(d, -2, -2)) {
                    moveright(form.a);
                    movedown(form.a);
                    movedown(form.c);
                    moveleft(form.c);
                    movedown(form.d);
                    movedown(form.d);
                    moveleft(form.d);
                    moveleft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && checkbool(a, -1, -1) && checkbool(c, -1, 1) && checkbool(d, -2, 2)) {
                    movedown(form.a);
                    moveleft(form.a);
                    moveleft(form.c);
                    moveup(form.c);
                    moveleft(form.d);
                    moveleft(form.d);
                    moveup(form.d);
                    moveup(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && checkbool(a, -1, 1) && checkbool(c, 1, 1) && checkbool(d, 2, 2)) {
                    moveleft(form.a);
                    moveup(form.a);
                    moveup(form.c);
                    moveright(form.c);
                    moveup(form.d);
                    moveup(form.d);
                    moveright(form.d);
                    moveright(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && checkbool(a, 1, 1) && checkbool(c, 1, -1) && checkbool(d, 2, -2)) {
                    moveup(form.a);
                    moveright(form.a);
                    moveright(form.c);
                    movedown(form.c);
                    moveright(form.d);
                    moveright(form.d);
                    movedown(form.d);
                    movedown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "l":
                if (f == 1 && checkbool(a, 1, -1) && checkbool(c, 1, 1) && checkbool(b, 2, 2)) {
                    moveright(form.a);
                    movedown(form.a);
                    moveup(form.c);
                    moveright(form.c);
                    moveup(form.b);
                    moveup(form.b);
                    moveright(form.b);
                    moveright(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 2 && checkbool(a, -1, -1) && checkbool(b, 2, -2) && checkbool(c, 1, -1)) {
                    movedown(form.a);
                    moveleft(form.a);
                    moveright(form.b);
                    moveright(form.b);
                    movedown(form.b);
                    movedown(form.b);
                    moveright(form.c);
                    movedown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && checkbool(a, -1, 1) && checkbool(c, -1, -1) && checkbool(b, -2, -2)) {
                    moveleft(form.a);
                    moveup(form.a);
                    movedown(form.c);
                    moveleft(form.c);
                    movedown(form.b);
                    movedown(form.b);
                    moveleft(form.b);
                    moveleft(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 4 && checkbool(a, 1, 1) && checkbool(b, -2, 2) && checkbool(c, -1, 1)) {
                    moveup(form.a);
                    moveright(form.a);
                    moveleft(form.b);
                    moveleft(form.b);
                    moveup(form.b);
                    moveup(form.b);
                    moveleft(form.c);
                    moveup(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "o":
                break;
            case "s":
                if (f == 1 && checkbool(a, -1, -1) && checkbool(c, -1, 1) && checkbool(d, 0, 2)) {
                    movedown(form.a);
                    moveleft(form.a);
                    moveleft(form.c);
                    moveup(form.c);
                    moveup(form.d);
                    moveup(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && checkbool(a, 1, 1) && checkbool(c, 1, -1) && checkbool(d, 0, -2)) {
                    moveup(form.a);
                    moveright(form.a);
                    moveright(form.c);
                    movedown(form.c);
                    movedown(form.d);
                    movedown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && checkbool(a, -1, -1) && checkbool(c, -1, 1) && checkbool(d, 0, 2)) {
                    movedown(form.a);
                    moveleft(form.a);
                    moveleft(form.c);
                    moveup(form.c);
                    moveup(form.d);
                    moveup(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && checkbool(a, 1, 1) && checkbool(c, 1, -1) && checkbool(d, 0, -2)) {
                    moveup(form.a);
                    moveright(form.a);
                    moveright(form.c);
                    movedown(form.c);
                    movedown(form.d);
                    movedown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "t":
                if (f == 1 && checkbool(a, 1, 1) && checkbool(d, -1, -1) && checkbool(c, -1, 1)) {
                    moveup(form.a);
                    moveright(form.a);
                    movedown(form.d);
                    moveleft(form.d);
                    moveleft(form.c);
                    moveup(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 2 && checkbool(a, 1, -1) && checkbool(d, -1, 1) && checkbool(c, 1, 1)) {
                    moveright(form.a);
                    movedown(form.a);
                    moveleft(form.d);
                    moveup(form.d);
                    moveup(form.c);
                    moveright(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && checkbool(a, -1, -1) && checkbool(d, 1, 1) && checkbool(c, 1, -1)) {
                    movedown(form.a);
                    moveleft(form.a);
                    moveup(form.d);
                    moveright(form.d);
                    moveright(form.c);
                    movedown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 4 && checkbool(a, -1, 1) && checkbool(d, 1, -1) && checkbool(c, -1, -1)) {
                    moveleft(form.a);
                    moveup(form.a);
                    moveright(form.d);
                    movedown(form.d);
                    movedown(form.c);
                    moveleft(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "z":
                if (f == 1 && checkbool(b, 1, 1) && checkbool(c, -1, 1) && checkbool(d, -2, 0)) {
                    moveup(form.b);
                    moveright(form.b);
                    moveleft(form.c);
                    moveup(form.c);
                    moveleft(form.d);
                    moveleft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && checkbool(b, -1, -1) && checkbool(c, 1, -1) && checkbool(d, 2, 0)) {
                    movedown(form.b);
                    moveleft(form.b);
                    moveright(form.c);
                    movedown(form.c);
                    moveright(form.d);
                    moveright(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && checkbool(b, 1, 1) && checkbool(c, -1, 1) && checkbool(d, -2, 0)) {
                    moveup(form.b);
                    moveright(form.b);
                    moveleft(form.c);
                    moveup(form.c);
                    moveleft(form.d);
                    moveleft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && checkbool(b, -1, -1) && checkbool(c, 1, -1) && checkbool(d, 2, 0)) {
                    movedown(form.b);
                    moveleft(form.b);
                    moveright(form.c);
                    movedown(form.c);
                    moveright(form.d);
                    moveright(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "i":
                if (f == 1 && checkbool(a, 2, 2) && checkbool(b, 1, 1) && checkbool(d, -1, -1)) {
                    moveup(form.a);
                    moveup(form.a);
                    moveright(form.a);
                    moveright(form.a);
                    moveup(form.b);
                    moveright(form.b);
                    movedown(form.d);
                    moveleft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && checkbool(a, -2, -2) && checkbool(b, -1, -1) && checkbool(d, 1, 1)) {
                    movedown(form.a);
                    movedown(form.a);
                    moveleft(form.a);
                    moveleft(form.a);
                    movedown(form.b);
                    moveleft(form.b);
                    moveup(form.d);
                    moveright(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && checkbool(a, 2, 2) && checkbool(b, 1, 1) && checkbool(d, -1, -1)) {
                    moveup(form.a);
                    moveup(form.a);
                    moveright(form.a);
                    moveright(form.a);
                    moveup(form.b);
                    moveright(form.b);
                    movedown(form.d);
                    moveleft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && checkbool(a, -2, -2) && checkbool(b, -1, -1) && checkbool(d, 1, 1)) {
                    movedown(form.a);
                    movedown(form.a);
                    moveleft(form.a);
                    moveleft(form.a);
                    movedown(form.b);
                    moveleft(form.b);
                    moveup(form.d);
                    moveright(form.d);
                    form.changeForm();
                    break;
                }
                break;
        }
    }

    private void removelines(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Node> newrects = new ArrayList<Node>();
        int full = 0;
        for (int i = 0; i < MESH[0].length; i++) {
            for (int j = 0; j < MESH.length; j++) {
                if (MESH[j][i] == 1)
                    full++;
            }
            if (full == MESH.length)
                lines.add(i + lines.size());
            full = 0;
        }
        if (lines.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                score += 50;
                numoflines++;

                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() == lines.get(0) * size) {
                        MESH[(int) a.getX() / size][(int) a.getY() / size] = 0;
                        pane.getChildren().remove(node);
                    } else
                        newrects.add(node);
                }

                for (Node node : newrects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() < lines.get(0) * size) {
                        MESH[(int) a.getX() / size][(int) a.getY() / size] = 0;
                        a.setY(a.getY() + size);
                    }
                }
                lines.remove(0);
                rects.clear();
                newrects.clear();
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    try {
                        MESH[(int) a.getX() / size][(int) a.getY() / size] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                rects.clear();
            } while (lines.size() > 0);
    }

    private void movedown(Rectangle rect) {
        if (rect.getY() + move < ymax)
            rect.setY(rect.getY() + move);

    }

    private void moveright(Rectangle rect) {
        if (rect.getX() + move <= xmax - size)
            rect.setX(rect.getX() + move);
    }

    private void moveleft(Rectangle rect) {
        if (rect.getX() - move >= 0)
            rect.setX(rect.getX() - move);
    }

    private void moveup(Rectangle rect) {
        if (rect.getY() - move > 0)
            rect.setY(rect.getY() - move);
    }

    private void movedown(Form form) {
        if (form.a.getY() == ymax - size || form.b.getY() == ymax - size || form.c.getY() == ymax - size
                || form.d.getY() == ymax - size || checkAmove(form) || checkBmove(form) || checkCmove(form) || checkDmove(form)) {
            MESH[(int) form.a.getX() / size][(int) form.a.getY() / size] = 1;
            MESH[(int) form.b.getX() / size][(int) form.b.getY() / size] = 1;
            MESH[(int) form.c.getX() / size][(int) form.c.getY() / size] = 1;
            MESH[(int) form.d.getX() / size][(int) form.d.getY() / size] = 1;
            removelines(newLayout);

            Form a = nextObj;
            nextObj = Controller.generate();
            object = a;
            newLayout.getChildren().addAll(a.a, a.b, a.c, a.d);
            keyboardcontroll(a);
        }

        if (form.a.getY() + move < ymax && form.b.getY() + move < ymax && form.c.getY() + move < ymax
                && form.d.getY() + move < ymax) {
            int movea = MESH[(int) form.a.getX() / size][((int) form.a.getY() / size) + 1];
            int moveb = MESH[(int) form.b.getX() / size][((int) form.b.getY() / size) + 1];
            int movec = MESH[(int) form.c.getX() / size][((int) form.c.getY() / size) + 1];
            int moved = MESH[(int) form.d.getX() / size][((int) form.d.getY() / size) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setY(form.a.getY() + move);
                form.b.setY(form.b.getY() + move);
                form.c.setY(form.c.getY() + move);
                form.d.setY(form.d.getY() + move);
            }
        }
    }

    private boolean checkAmove(Form form) {
        return (MESH[(int) form.a.getX() / size][((int) form.a.getY() / size) + 1] == 1);
    }

    private boolean checkBmove(Form form) {
        return (MESH[(int) form.b.getX() / size][((int) form.b.getY() / size) + 1] == 1);
    }

    private boolean checkCmove(Form form) {
        return (MESH[(int) form.c.getX() / size][((int) form.c.getY() / size) + 1] == 1);
    }

    private boolean checkDmove(Form form) {
        return (MESH[(int) form.d.getX() / size][((int) form.d.getY() / size) + 1] == 1);
    }

    private boolean checkbool(Rectangle rect, int x, int y) {
        boolean xb = false;
        boolean yb = false;
        if (x >= 0)
            xb = rect.getX() + x * move <= xmax - size;
        if (x < 0)
            xb = rect.getX() + x * move >= 0;
        if (y >= 0)
            yb = rect.getY() - y * move > 0;
        if (y < 0)
            yb = rect.getY() + y * move < ymax;
        return xb && yb && MESH[((int) rect.getX() / size) + x][((int) rect.getY() / size) - y] == 0;
    }

}