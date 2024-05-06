package dfa;

import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Dfa dfa = new Dfa();

            System.out.print("Enter the number of states: ");
            int numberOfStates = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Enter the start state: ");
            String start_state_name = scanner.nextLine().trim();
            System.out.print("final?(y/n): ");
            String is_final = scanner.nextLine().trim().toLowerCase();

            State start_state = new State(start_state_name);

            if (is_final.compareTo("y") == 0){
                start_state.setFinal(true);
            }


            dfa.setStartState(start_state);

            for (int i = 2; i <=numberOfStates; i++) {
                System.out.print("Enter the start state"+i+": ");
                String state_name = scanner.nextLine().trim();
                if (dfa.getQ().stream().noneMatch(state -> state.getName().compareTo(state_name) == 0)){
                    System.out.print("final?(y/n): ");
                    is_final = scanner.nextLine().trim().toLowerCase();
                    State new_state = new State(state_name);
                    if (is_final.compareTo("y") == 0){
                        new_state.setFinal(true);
                    }
                    dfa.add_state(new_state);
                }else {
                    System.out.println("this state exists already");
                    System.exit(0);

                }
            }


            System.out.print("Enter the size of alphabet: ");
            int alphabetSize = scanner.nextInt();

            scanner.nextLine();


            for (int i = 0; i < alphabetSize; i++) {
                System.out.print("Enter alphabet character " + (i + 1) + ": ");
                char character = scanner.nextLine().charAt(0);
                dfa.setAlphabet(character);
            }


            System.out.print("Enter the number of transitions: ");
            int transitions_size = scanner.nextInt();
            System.out.println("inter your transition in this format : q0,a,q1");
            scanner.nextLine();
            for (int i = 0; i <Math.min(transitions_size,Math.pow(dfa.getAlphabet().size(),dfa.getQ().size())); i++) {

                System.out.println("inter transition"+(i+1)+": ");
                String[] transitions = scanner.nextLine().trim().split(",");

                String start_state_name_t = transitions[0];
                if(dfa.getQ().stream().noneMatch(state -> state.getName().compareTo(start_state_name_t) == 0)){
                    System.out.println("this state does not exists");
                    System.exit(0);
                }

                Character character =  transitions[1].toCharArray()[0];
                if (dfa.getAlphabet().stream().noneMatch(character1 -> character.compareTo(character) == 0)){
                    System.out.println("this character not in dfa alphabet");
                    System.exit(0);
                }

                String final_state_name_t = transitions[2];
                if(dfa.getQ().stream().noneMatch(state -> state.getName().compareTo(final_state_name_t) == 0)){
                    System.out.println("this state does not exists");
                    System.exit(0);
                }




                State start_state_t = dfa.getQ().stream().filter(state -> state.getName().compareTo(start_state_name_t) == 0).findAny().get();
                State final_state_t = dfa.getQ().stream().filter(state -> state.getName().compareTo(final_state_name_t) == 0).findAny().get();

                Transition transition = new Transition(start_state_t,final_state_t,character);

                if (dfa.getTransitions().stream().anyMatch(transition1 -> transition1.equals(transition))){
                    System.out.println("this transition already exists");
                    System.exit(0);
                }
                dfa.setTransition(start_state_t,final_state_t,character);

            }

            dfa.pre_processor();


            DFAMinimizer.minimize(dfa);

            dfa.print_dfa();

        }
}
