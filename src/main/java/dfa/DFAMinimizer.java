package dfa;

import java.util.*;
import java.util.stream.Collectors;

public class DFAMinimizer {

    private static LinkedList<Tuple> none_mergeable = new LinkedList<>();
    private  static  LinkedList<Tuple> all_tuple = new LinkedList<>();

    private  static LinkedList<State> reachable = new LinkedList<>();


    public static Dfa minimize(Dfa dfa) {

        State start_state = dfa.getStartState();
        reachable.add(start_state);


        int reachable_size = 1;


        while(true){
            LinkedList<State> toAdd = new LinkedList<>();
            for (State s: reachable
                 ) {
                for (Transition t: dfa.getTransitions()
                ) {
                    try {
                        if (t.getStartState().getName() == s.getName()){
                            toAdd.add(t.getFinalState());
                        }
                        if (t.getStartState().getName() == "Trap" && t.getFinalState().getName() == "Trap"){
                            break;
                        }
                    } catch (NoSuchElementException e){
                        System.out.println(e.getMessage());
                    }

                }
            }
            for (State s: toAdd
                 ) {
                if (reachable.stream().noneMatch(state -> state.getName() == s.getName())){
                    reachable.add(s);
                }
            }



            if (reachable_size == reachable.size()){
                break;
            }else{
                reachable_size = reachable.size();

            }
        }


        System.out.println("reachable state : ");
        for (State s: reachable
             ) {
            if (s != null){
                System.out.println(s.getName());
            }
        }


        //all tuple
        for (State s1: dfa.getQ()
        ) {
            for (State s2: dfa.getQ().stream().filter(state -> state.getName().compareTo(s1.getName())!=0).collect(Collectors.toSet())
            ) {

                if(all_tuple.stream().noneMatch(tuple -> tuple.equals(new Tuple(s1,s2)))){
                    all_tuple.add(new Tuple(s1,s2));
                }


            }

        }

        // first non merge-able tuple
        for (State s1: dfa.getQ()
        ) {

            for (State s2: dfa.getQ().stream().filter(state -> state.getName().compareTo(s1.getName())!=0).collect(Collectors.toSet())
            ) {
                for (Character ch: dfa.getAlphabet()
                ) {
                    Optional<Transition> transition_s1 = dfa.getTransitions().stream().filter(transition -> {
                        return transition.getStartState().getName().compareTo(s1.getName()) == 0 &&
                                transition.getCharacter().compareTo(ch) == 0;
                    }).findFirst();


                    Optional<Transition> transition_s2 = dfa.getTransitions().stream().filter(transition -> {
                        return transition.getStartState().getName().compareTo(s2.getName()) == 0 &&
                                transition.getCharacter().compareTo(ch) == 0;
                    }).findFirst();

                    if(transition_s1.isPresent() && transition_s2.isPresent()){
                        if (transition_s1.get().getFinalState().isFinal() != transition_s2.get().getFinalState().isFinal()){
                            if(none_mergeable.stream().noneMatch(tuple -> tuple.equals(new Tuple(s1,s2)))){
                                none_mergeable.add(new Tuple(s1,s2));

                            }
                        }

                    }

                }

            }

        }

        System.out.println("all tuple: ");
        for (Tuple t: all_tuple
        ) {
            System.out.println(t.str_format);
        }


        System.out.println("none merge-able: ");
        for (Tuple t: none_mergeable
        ) {
            System.out.println(t.str_format);
        }




      return dfa;
    }


}
