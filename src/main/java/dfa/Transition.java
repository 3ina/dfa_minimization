package dfa;

import java.util.Objects;

public class Transition {
    private State startState;
    private State finalState;
    private Character character;
    private final String stringformat;

    public Transition(State startState, State finalState, Character character) {
        this.startState = startState;
        this.finalState = finalState;
        this.character = character;
        this.stringformat = startState.getName() + "," + character + "," + finalState.getName();
    }

    public String getStringformat() {
        return stringformat;
    }

    public State getFinalState() {
        return finalState;
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setFinalState(State finalState) {
        this.finalState = finalState;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transition that = (Transition) obj;
        return Objects.equals(startState, that.startState) &&
                Objects.equals(finalState, that.finalState) &&
                Objects.equals(character, that.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startState, finalState, character);
    }
}