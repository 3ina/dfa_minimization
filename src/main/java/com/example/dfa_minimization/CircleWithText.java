package com.example.dfa_minimization;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CircleWithText {
    private Circle circle;
    private Text text;

    public CircleWithText(double x, double y, double radius, String content) {
        circle = new Circle(x, y, radius, Color.WHITE);
        circle.setStroke(Color.BLACK);

        text = new Text(x - radius / 2, y + radius / 4, content);
        text.setFont(Font.font("Arial", radius / 2));
    }

    public Circle getCircle() {
        return circle;
    }

    public Text getText() {
        return text;
    }

    public void setCenterX(double x) {
        circle.setCenterX(x);
        text.setX(x - circle.getRadius() / 2);
    }

    public void setCenterY(double y) {
        circle.setCenterY(y);
        text.setY(y + circle.getRadius() / 4);
    }
}
