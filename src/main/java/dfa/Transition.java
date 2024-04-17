package dfa;

public class Transition {
    private State startState;
    private State finalState;
    private Character character;

    private String stringformat;

    public String getStringformat() {
        return stringformat;
    }

    public Transition(State startState, State finalState, Character character) {
        this.startState = startState;
        this.finalState = finalState;
        this.character = character;
        this.stringformat = startState.getName()+","+character.toString()+","+finalState.getName();
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
        return this.startState == ((Transition) obj).startState &&
                this.startState == ((Transition) obj).startState &&
                this.character == ((Transition) obj).character;
    }
}
