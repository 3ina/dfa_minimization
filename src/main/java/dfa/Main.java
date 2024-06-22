package dfa;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dfa dfa = new Dfa();

        System.out.print("Enter the number of states: ");
        int numberOfStates = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter the start state: ");
        String startStateName = scanner.nextLine().trim();
        State startState = createAndAddState(scanner, dfa, startStateName);

        dfa.setStartState(startState);

        for (int i = 1; i < numberOfStates; i++) { // Start from 1 as the first state is already added
            System.out.print("Enter state name " + (i + 1) + ": ");
            String stateName = scanner.nextLine().trim();
            if (dfa.getQ().stream().noneMatch(state -> state.getName().equals(stateName))) {
                createAndAddState(scanner, dfa, stateName);
            } else {
                System.out.println("This state already exists.");
                return;
            }
        }

        System.out.print("Enter the size of the alphabet: ");
        int alphabetSize = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (int i = 0; i < alphabetSize; i++) {
            System.out.print("Enter alphabet character " + (i + 1) + ": ");
            char character = scanner.nextLine().charAt(0);
            dfa.addAlphabet(character);
        }

        System.out.print("Enter the number of transitions: ");
        int transitionsSize = scanner.nextInt();
        System.out.println("Enter your transitions in this format: q0,a,q1");
        scanner.nextLine();  // Consume newline

        for (int i = 0; i < transitionsSize; i++) {
            System.out.print("Enter transition " + (i + 1) + ": ");
            String[] transitionData = scanner.nextLine().trim().split(",");

            if (transitionData.length != 3) {
                System.out.println("Invalid transition format.");
                return;
            }

            String startStateNameT = transitionData[0];
            char character = transitionData[1].charAt(0);
            String finalStateNameT = transitionData[2];

            if (!stateExists(dfa, startStateNameT) || !stateExists(dfa, finalStateNameT)) {
                System.out.println("One of the states does not exist.");
                return;
            }

            if (!alphabetContainsCharacter(dfa, character)) {
                System.out.println("This character is not in the DFA alphabet.");
                return;
            }

            State startStateT = getStateByName(dfa, startStateNameT);
            State finalStateT = getStateByName(dfa, finalStateNameT);
            Transition transition = new Transition(startStateT, finalStateT, character);

            if (dfa.getTransitions().stream().anyMatch(t -> t.equals(transition))) {
                System.out.println("This transition already exists.");
                return;
            }

            dfa.setTransition(startStateT, finalStateT, character);
        }

        dfa.preProcessor();
        DFAMinimizer.minimize(dfa);
        dfa.printDfa();
    }

    private static State createAndAddState(Scanner scanner, Dfa dfa, String stateName) {
        System.out.print("Final state? (y/n): ");
        String isFinal = scanner.nextLine().trim().toLowerCase();
        State state = new State(stateName);
        if (isFinal.equals("y")) {
            state.setFinal(true);
        }
        dfa.addState(state);
        return state;
    }

    private static boolean stateExists(Dfa dfa, String stateName) {
        return dfa.getQ().stream().anyMatch(state -> state.getName().equals(stateName));
    }

    private static boolean alphabetContainsCharacter(Dfa dfa, char character) {
        return dfa.getAlphabet().contains(character);
    }

    private static State getStateByName(Dfa dfa, String stateName) {
        return dfa.getQ().stream().filter(state -> state.getName().equals(stateName)).findFirst().orElse(null);
    }
}