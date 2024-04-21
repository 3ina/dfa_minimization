package dfa;


import javafx.scene.shape.Circle;

public class State {
    private String name;
    private boolean isFinal = false;


    public String getName() {
        return name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State(String name) {
        this.name = name;
    }
}

