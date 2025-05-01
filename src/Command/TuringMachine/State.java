package Command.TuringMachine;

import Command.TuringMachine.Command;

import java.util.ArrayList;
import java.util.List;

class State {
    int index;
    List<Command> rules = new ArrayList<>();

    public State(int index) {
        this.index = index;
    }

    public void addRule(Command command) {
        rules.add(command);
    }

    public Command getCommand(String currentSymbol) {
        for (Command cmd : rules) {
            if (cmd.read.contains(currentSymbol)) {
                return cmd;
            }
        }
        throw new RuntimeException("No valid transition found for: " + currentSymbol);
    }
}