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

        




      return dfa;
    }


}
