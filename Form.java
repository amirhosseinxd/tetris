package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import java.util.*;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class Form {
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    Color color;
    private String name;
    public int form = 1;

    public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        switch (name) {
            case "j":
                color = Color.SLATEGRAY;
                break;
            case "l":
                color = Color.DARKGOLDENROD;
                break;
            case "o":
                color = Color.ORANGERED;
                break;
            case "s":
                color = Color.DARKVIOLET;
                break;
            case "t":
                color = Color.AQUA;
                break;
            case "z":
                color = Color.HOTPINK;
                break;
            case "i":
                color = Color.SANDYBROWN;
                break;

        }
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }


    public String getName() {
        return name;
    }


    public void changeForm() {
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }
}