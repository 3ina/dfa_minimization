package dfa;

import java.util.*;
import java.util.stream.Collectors;

public class DFAMinimizer {

    private static final LinkedList<Tuple> nonMergeable = new LinkedList<>();
    private static final LinkedList<Tuple> allTuples = new LinkedList<>();
    private static final LinkedList<State> reachable = new LinkedList<>();

    public static void minimize(Dfa dfa) {
        findReachableStates(dfa);
        removeNonReachableStates(dfa);
        createAllTuples(dfa);
        findFirstNonMergeableTuples(dfa);
        findSecondNonMergeableTuples(dfa);
        mergeStates(dfa);
    }

    private static void findReachableStates(Dfa dfa) {
        State startState = dfa.getStartState();
        reachable.add(startState);

        int reachableSize = 1;
        while (true) {
            LinkedList<State> toAdd = new LinkedList<>();
            for (State s : reachable) {
                for (Transition t : dfa.getTransitions()) {
                    if (Objects.equals(t.getStartState().getName(), s.getName())) {
                        toAdd.add(t.getFinalState());
                    }
                }
            }
            for (State s : toAdd) {
                if (reachable.stream().noneMatch(state -> Objects.equals(state.getName(), s.getName()))) {
                    reachable.add(s);
                }
            }
            if (reachableSize == reachable.size()) {
                break;
            } else {
                reachableSize = reachable.size();
            }
        }
    }

    private static void removeNonReachableStates(Dfa dfa) {
        LinkedList<State> nonReachable = new LinkedList<>();
        for (State s : dfa.getQ()) {
            if (reachable.stream().noneMatch(state -> Objects.equals(state.getName(), s.getName()))) {
                nonReachable.add(s);
            }
        }

        for (State s : nonReachable) {
            Optional<State> nonRech = dfa.getQ().stream()
                    .filter(state -> Objects.equals(state.getName(), s.getName()))
                    .findFirst();
            if (nonRech.isPresent()) {
                List<Transition> transitions = dfa.getTransitions().stream()
                        .filter(t -> Objects.equals(t.getStartState().getName(), nonRech.get().getName()))
                        .collect(Collectors.toList());
                dfa.getTransitions().removeAll(transitions);
                dfa.getQ().remove(nonRech.get());
            }
        }

        System.out.println("Reachable states: ");
        for (State s : reachable) {
            if (s != null) {
                System.out.println(s.getName());
            }
        }
    }

    private static void createAllTuples(Dfa dfa) {
        for (State s1 : dfa.getQ()) {
            for (State s2 : dfa.getQ().stream().filter(state -> !state.getName().equals(s1.getName())).collect(Collectors.toSet())) {
                Tuple tuple = new Tuple(s1, s2);
                if (allTuples.stream().noneMatch(t -> t.equals(tuple))) {
                    allTuples.add(tuple);
                }
            }
        }
    }

    private static void findFirstNonMergeableTuples(Dfa dfa) {
        for (Tuple t: allTuples
             ) {
            Optional<State> state1 =dfa.getQ().stream()
                    .filter(state -> Objects.equals(state.getName(),t.getS1Name())).findFirst();

            Optional<State> state2 =dfa.getQ().stream()
                    .filter(state -> Objects.equals(state.getName(),t.getS2Name())).findFirst();

            if (state1.isPresent() && state2.isPresent()) {
                if (state1.get().isFinal() != state2.get().isFinal()) {
                    nonMergeable.add(t);
                }
            }
        }


        for (State s1 : dfa.getQ()) {
            for (State s2 : dfa.getQ().stream().filter(state -> !state.getName().equals(s1.getName())).collect(Collectors.toSet())) {
                for (Character ch : dfa.getAlphabet()) {
                    Optional<Transition> transitionS1 = dfa.getTransitions().stream()
                            .filter(t -> Objects.equals(t.getStartState().getName(), s1.getName()) && t.getCharacter().equals(ch))
                            .findFirst();
                    Optional<Transition> transitionS2 = dfa.getTransitions().stream()
                            .filter(t -> Objects.equals(t.getStartState().getName(), s2.getName()) && t.getCharacter().equals(ch))
                            .findFirst();

                    if (transitionS1.isPresent() && transitionS2.isPresent()) {
                        if (transitionS1.get().getFinalState().isFinal() != transitionS2.get().getFinalState().isFinal()) {
                            Tuple tuple = new Tuple(s1, s2);
                            if (nonMergeable.stream().noneMatch(t -> t.equals(tuple))) {
                                nonMergeable.add(tuple);
                            }
                        }
                    }
                }
            }
        }
        allTuples.removeAll(nonMergeable);
    }

    private static void findSecondNonMergeableTuples(Dfa dfa) {
        while (true) {
            int size = nonMergeable.size();
            for (Tuple tuple : allTuples) {
                for (Character ch : dfa.getAlphabet()) {
                    State state1 = getStateByName(dfa, tuple.getS1Name());
                    State state2 = getStateByName(dfa, tuple.getS2Name());

                    Optional<Transition> transition1Opt = dfa.getTransitions().stream()
                            .filter(t -> Objects.equals(t.getStartState().getName(), state1.getName()) && t.getCharacter().equals(ch))
                            .findFirst();
                    Optional<Transition> transition2Opt = dfa.getTransitions().stream()
                            .filter(t -> Objects.equals(t.getStartState().getName(), state2.getName()) && t.getCharacter().equals(ch))
                            .findFirst();

                    if (transition1Opt.isPresent() && transition2Opt.isPresent()) {
                        Transition transition1 = transition1Opt.get();
                        Transition transition2 = transition2Opt.get();
                        Tuple newTuple = new Tuple(transition1.getFinalState(), transition2.getFinalState());

                        if (nonMergeable.stream().anyMatch(t -> t.equals(newTuple))) {
                            if (nonMergeable.stream().noneMatch(t -> t.equals(tuple))) {
                                nonMergeable.add(tuple);
                            }
                        }
                    }
                }
            }
            if (size == nonMergeable.size()) {
                break;
            }
        }
        allTuples.removeAll(nonMergeable);
    }

    private static void mergeStates(Dfa dfa) {
        for (Tuple t : allTuples) {
            State s1 = getStateByName(dfa, t.getS1Name());
            State s2 = getStateByName(dfa, t.getS2Name());

            for (Transition transition : dfa.getTransitions()) {
                if (Objects.equals(transition.getStartState().getName(), s1.getName())) {
                    transition.setStartState(s2);
                }
                if (Objects.equals(transition.getFinalState().getName(), s1.getName())) {
                    transition.setFinalState(s2);
                }
            }
            if (s1.isFinal()) {
                s2.setFinal(true);
            }
            if (Objects.equals(dfa.getStartState().getName(), s1.getName())) {
                dfa.setStartState(s2);
            }
            dfa.getQ().remove(s1);
        }

        Map<String, Long> duplicateCount = dfa.getTransitions().stream()
                .collect(Collectors.groupingBy(Transition::getStringformat, Collectors.counting()));

        for (Map.Entry<String, Long> entry : duplicateCount.entrySet()) {
            if (entry.getValue() > 1) {
                dfa.getTransitions().removeIf(t -> t.getStringformat().equals(entry.getKey()));
            }
        }
    }

    private static State getStateByName(Dfa dfa, String stateName) {
        return dfa.getQ().stream()
                .filter(state -> Objects.equals(state.getName(), stateName))
                .findFirst()
                .orElse(null);
    }
}