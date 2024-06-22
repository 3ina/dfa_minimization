package dfa;

import java.util.*;

public class Dfa {
    private Set<State> Q = new HashSet<>();
    private State startState = new State("");
    private Set<Character> alphabet = new HashSet<>();
    private List<Transition> transitions = new ArrayList<>();

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransition(State startState, State finalState, Character character) {
        if (alphabet.contains(character) && Q.contains(startState) && Q.contains(finalState)) {
            Transition transition = new Transition(startState, finalState, character);
            transitions.add(transition);
        }
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void addAlphabet(Character character) {
        alphabet.add(character);
    }

    public Set<State> getQ() {
        return Q;
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        if (!containsState(startState)) {
            addState(startState);
        }
        this.startState = startState;

    }

    public void addState(State state) {
        if (!containsState(state)) {
            Q.add(state);
        }
    }

    private boolean containsState(State state) {
        return Q.stream().anyMatch(s -> s.getName().equals(state.getName()));
    }

    public void printDfa() {
        System.out.println("Start state: " + startState.getName());

        System.out.print("All states: ");
        Q.forEach(s -> System.out.print(s.getName() + " "));
        System.out.println();

        System.out.print("Final states: ");
        Q.stream().filter(State::isFinal).forEach(s -> System.out.print(s.getName() + " "));
        System.out.println();

        System.out.println("Alphabet: " + alphabet);

        Set<String> transitionsToPrint = new HashSet<>();
        for (Transition t : transitions) {
            String s = "from " + t.getStartState().getName() + " to " + t.getFinalState().getName() + " with character " + t.getCharacter();
            transitionsToPrint.add(s);
        }

        transitionsToPrint.forEach(System.out::println);
    }

    public void preProcessor() {
        State trap = new State("Trap");
        boolean trapNeeded = false;

        for (State s : Q) {
            for (Character ch : alphabet) {
                boolean transitionExists = transitions.stream()
                        .anyMatch(t -> t.getStartState().equals(s) && t.getCharacter().equals(ch));

                if (!transitionExists) {
                    trapNeeded = true;
                    transitions.add(new Transition(s, trap, ch));
                }
            }
        }

        if (trapNeeded) {
            for (Character ch : alphabet) {
                transitions.add(new Transition(trap, trap, ch));
            }
            Q.add(trap);
        }
    }
}