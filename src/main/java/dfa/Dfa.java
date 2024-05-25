package dfa;

import java.util.*;

public class Dfa {
    private Set<State> Q = new HashSet<State>();
    private State startState = new State("");

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
            this.add_state(startState);
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
            if (state.getName().compareTo(s.getName()) == 0){
                return true;
            }
        }
        return false;
    }


    public void print_dfa(){
        System.out.println("Start state: " + this.getStartState().getName());

        System.out.print("all state: ");
        for (State s: this.getQ()
             ) {
            System.out.print(s.getName()+" ");
        }
        System.out.println();

        System.out.print("final states: ");
        for (State s: this.getQ()
        ) {
            if (s.isFinal()){
                System.out.print(s.getName()+" ");
            }
        }
        System.out.println();

        System.out.println("Alphabet: " + this.getAlphabet());
        List<String> transitions_to_print_dfa = new ArrayList<String>();
        for (Transition t: this.transitions
             ) {
            String s = "form "+t.getStartState().getName()+" to " + t.getFinalState().getName()+" with character " + t.getCharacter();
            if(transitions_to_print_dfa.contains(s)){
                continue;
            }else {
                transitions_to_print_dfa.add(s);
            }
        }

        for (String s: transitions_to_print_dfa){
            System.out.println(s);
        }
    }

    public void pre_processor(){
        State trap = new State("Trap");

        boolean trap_needed = false;
        for (State s: this.getQ()
        ) {
            for (Character ch: this.alphabet
            ) {
                if (this.transitions.stream().noneMatch(
                        transition -> {
                            return transition.getStartState().getName().compareTo(s.getName()) == 0 &&
                            transition.getCharacter().compareTo(ch) == 0;
                        }
                )){
                    trap_needed = true;
                    Transition trap_transition = new Transition(s,trap,ch);
                    this.transitions.add(trap_transition);

                }


            }

        }
        if (trap_needed) {
            for (Character ch: this.alphabet
                 ) {
                Transition transition = new Transition(trap,trap,ch);

            }
            this.getQ().add(trap);
        }
    }

}
