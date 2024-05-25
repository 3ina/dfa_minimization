package dfa;


import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        State s = (State) obj;
        return Objects.equals(this.name, s.name);
    }
}

