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

        // find reachable state
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

        // delete non reachable state
        LinkedList<State> non_reachable = new LinkedList<State>();
        for (State s: dfa.getQ()
             ) {
            if (reachable.stream().noneMatch(state -> state.getName() == s.getName())){
                non_reachable.add(s);
            }
        }

        for (State s: non_reachable
             ) {
            Optional<State> non_rech = dfa.getQ().stream().filter(state -> state.getName() == s.getName()).findFirst();

            if (non_rech.isPresent()){
                List<Transition> transition = dfa.getTransitions().stream()
                        .filter(transition1 -> transition1.getStartState().getName() == non_rech.get().getName())
                        .collect(Collectors.toList());

                for (Transition t: transition
                     ) {
                    dfa.getTransitions().remove(t);
                }

                dfa.getQ().remove(non_rech.get());

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



        for (Tuple t: none_mergeable
             ) {
            Optional<Tuple> tupleOptional =all_tuple.stream().filter(tuple -> tuple.equals(t)).findFirst();
            if(tupleOptional.isPresent()){
                all_tuple.remove(tupleOptional.get());
            }
        }

        // second non merge-able tuple
        while (true){
            int size = none_mergeable.size();
            for (Tuple tuple :
                    all_tuple) {

                for (Character character:dfa.getAlphabet()
                     ) {
                    State state1  = dfa.getQ().stream().filter(state -> state.getName() == tuple.getS1Name()).findFirst().get();
                    State state2  = dfa.getQ().stream().filter(state -> state.getName() == tuple.getS2Name()).findFirst().get();

                    Transition transition1 = dfa.getTransitions().stream().filter(
                            transition -> transition.getStartState().getName() == state1.getName() && transition.getCharacter() == character
                    ).collect(Collectors.toList()).get(0);

                    Transition transition2 = dfa.getTransitions().stream().filter(
                            transition -> transition.getStartState().getName() == state2.getName() && transition.getCharacter() == character
                    ).collect(Collectors.toList()).get(0);
                    Tuple new_tuple = new Tuple(transition1.getFinalState(),transition2.getFinalState());

                    if (none_mergeable.stream().anyMatch(tuple1 ->
                            tuple1.equals(new_tuple))){
                            none_mergeable.add(tuple);

                    }

                }
            }

            if (size == none_mergeable.size()){
                break;
            }else {
                size = none_mergeable.size();
            }
        }



        for (Tuple t: none_mergeable
        ) {
            Optional<Tuple> tupleOptional =all_tuple.stream().filter(tuple -> tuple.equals(t)).findFirst();
            if(tupleOptional.isPresent()){
                all_tuple.remove(tupleOptional.get());
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

        //merge states

        for (Tuple t: all_tuple
             ) {
            String s1_name = t.getS1Name();
            String s2_name = t.getS2Name();


            Optional<State> s1 = dfa.getQ().stream().filter(state -> state.getName() == s1_name).findFirst();
            if(s1.isPresent()){
                State state_1 = s1.get();

                Optional<State> s2 = dfa.getQ().stream().filter(state -> state.getName() == s2_name).findFirst();
                if (s2.isPresent()){
                    State state_2 = s2.get();

                    for (Transition transition: dfa.getTransitions()
                         ) {
                        if (transition.getStartState().getName() == state_1.getName()){
                            transition.setStartState(state_2);
                        }
                        if (transition.getFinalState().getName() == state_1.getName()){
                            transition.setFinalState(state_2);
                        }
                    }
                    if(state_1.isFinal()){
                        state_2.setFinal(true);
                    }
                    if (dfa.getStartState().getName() == state_1.getName()){
                        dfa.setStartState(state_2);
                    }
                    dfa.getQ().remove(state_1);
                }
            }



        }



      return dfa;
    }


}
