# DFA Minimization Project

## Overview

This project provides a Java implementation for constructing and minimizing a Deterministic Finite Automaton (DFA). The application allows users to define a DFA, specify its states, transitions, and alphabet, and then apply minimization techniques to reduce the DFA to its simplest equivalent form.

## Features

- **DFA Construction**: Users can input the number of states, the alphabet, and the transitions to define a DFA.
- **Trap State Handling**: Automatically adds a trap state for undefined transitions to ensure the DFA is complete.
- **DFA Minimization**: Implements state minimization techniques to reduce the DFA by merging equivalent states.
- **Reachability Check**: Removes non-reachable states from the DFA.
- **Interactive Command-Line Interface**: Users interact with the application via the command line to define and minimize their DFA.

## Requirements

- Java Development Kit (JDK) 8 or higher

## How to Run

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/3ina/dfa_minimization.git
    cd dfa-minimization
    ```

2. **Compile the Code**:
    Navigate to the source directory and compile the Java files:
    ```bash
    javac dfa/*.java
    ```

3. **Run the Application**:
    ```bash
    java dfa.Main
    ```

## Usage

After running the application, you will be prompted to input the details of your DFA:

1. **Enter the number of states**: Specify how many states the DFA will have.
2. **Enter the start state**: Define the starting state of the DFA.
3. **Enter state names**: Enter the names of the states one by one, specifying whether each is a final state.
4. **Enter the alphabet**: Define the alphabet by entering characters that the DFA will recognize.
5. **Enter the transitions**: Specify the transitions in the format `startState,character,endState`.
6. **Minimization and Output**: The application will then preprocess the DFA, apply minimization, and output the minimized DFA.

### Example

```plaintext
Enter the number of states: 3
Enter the start state: q0
Final state? (y/n): n
Enter state name 2: q1
Final state? (y/n): y
Enter state name 3: q2
Final state? (y/n): n

Enter the size of the alphabet: 2
Enter alphabet character 1: a
Enter alphabet character 2: b

Enter the number of transitions: 3
Enter your transitions in this format: q0,a,q1
Enter transition 1: q0,a,q1
Enter transition 2: q1,b,q2
Enter transition 3: q2,a,q0
