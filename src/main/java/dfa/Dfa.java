package dfa;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Dfa {
    private Set<State> Q = new HashSet<State>();
    private State startState;

    private Set<Character> alphabet = new HashSet<Character>();


    private LinkedList<Transition> transitions = new LinkedList<Transition>();

    public LinkedList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransition(State startState,State finalState , Character character) {
        if(alphabet.contains(character) && Q.contains(startState) && Q.contains(finalState)){
            Transition transition = new Transition(startState,finalState,character);
            this.transitions.add(transition);

        }
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Character character) {
        this.alphabet.add(character);
    }

    public Set<State> getQ() {
        return Q;
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        if(this.containsState(startState)){
            return;
        }else {
            this.startState = startState;
        }

    }

    public void add_state(State state){
        if (this.containsState(state)){
            return;
        }else {
            Q.add(state);

        }
    }

    private boolean containsState(State state){
        for (State s: Q
        ) {
            if (state.getName() == s.getName()){
                return true;
            }
        }
        return false;
    }


}
