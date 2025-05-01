package Command.TuringMachine;

import Command.Engine.AutomatonEngine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TuringMachine implements AutomatonEngine {
    private final Map<String, Tape> tapes = new LinkedHashMap<>();
    private Tape currentTape;
    private int currentStateIndex;
    private final List<State> states = new ArrayList<>();
    private final Map<String, Integer> stateMap = new HashMap<>();
    private int stateCounter = 0;
    private boolean halted = false;
    private boolean accepted = false;

    public TuringMachine(int initialState) {
        this.currentStateIndex = initialState;
    }

    @Override
    public void compile(String sourceCode) throws Exception {
        List<List<String>> lines = readFromBuffered(new BufferedReader(new java.io.StringReader(sourceCode)));
        compile(lines);
    }

    public void compile(List<List<String>> lines) {
        Map<String, List<String>> sets = new HashMap<>();
        tapes.clear();
        states.clear();
        stateMap.clear();
        stateCounter = 0;
        halted = false;
        accepted = false;

        for (List<String> line : lines) {
            if (line.isEmpty()) continue;
            String type = line.get(0);

            switch (type) {
                case "tape" -> {
                    String tapeName = line.get(1);
                    Tape tape;
                    if (line.size() == 2) {
                        tape = new Tape(10000);
                    } else {
                        tape = new Tape(10000, splitToList(line.get(2)));
                    }
                    tapes.put(tapeName, tape);
                    if ("Main".equals(tapeName)) currentTape = tape;
                }
                case "f" -> {
                    if (line.size() < 7) throw new IllegalArgumentException("Invalid rule definition.");
                    String fromState = line.get(1);
                    List<String> read = sets.getOrDefault(line.get(2), List.of(line.get(2)));
                    List<String> write = sets.getOrDefault(line.get(4), List.of(line.get(4)));
                    String move = line.get(5);
                    String toTape = line.get(6);
                    String toState = line.get(3);
                    addRule(fromState, read, write, toState, move, toTape);
                }
                default -> {
                    if (type.startsWith("#")) {
                        String setName = type.substring(1);
                        sets.put(setName, line.subList(1, line.size()));
                    } else {
                        throw new IllegalArgumentException("Invalid line: " + line);
                    }
                }
            }
        }
    }

    private void addRule(String from, List<String> read, List<String> write, String to, String move, String nextTape) {
        stateMap.putIfAbsent(from, stateCounter++);
        stateMap.putIfAbsent(to, stateCounter++);
        int fromIdx = stateMap.get(from);
        int toIdx = stateMap.get(to);

        while (states.size() <= Math.max(fromIdx, toIdx)) {
            states.add(new State(states.size()));
        }

        states.get(fromIdx).addRule(new Command(read, write, toIdx, move, nextTape));
    }

    @Override
    public boolean step() {
        if (halted) return false;

        String current = currentTape.getCurrent();
        Command command = states.get(currentStateIndex).getCommand(current);

        int index = command.read.indexOf(current);
        int writeIndex = (command.write.size() > 1) ? index : 0;

        currentTape.setCurrent(command.write.get(writeIndex));
        currentStateIndex = command.nextState;

        switch (command.move) {
            case "L" -> currentTape.moveLeft();
            case "R" -> currentTape.moveRight();
            case "-" -> {}
            case "0" -> {
                halted = true;
                accepted = false;
                return false;
            }
            case "1" -> {
                halted = true;
                accepted = true;
                return false;
            }
            default -> throw new IllegalStateException("Unknown move: " + command.move);
        }

        currentTape = tapes.get(command.nextTape);
        return true;
    }

    @Override
    public void reset() {
        tapes.values().forEach(Tape::reset);
        currentStateIndex = 0;
        currentTape = tapes.get("Main");
        halted = false;
        accepted = false;
    }

    @Override
    public void play() {
        while (!halted) step();
    }

    @Override
    public void stop() {
        halted = true;
    }

    @Override
    public boolean isHalted() {
        return halted;
    }

    @Override
    public boolean isAccepted() {
        return accepted;
    }

    @Override
    public String getCurrentStatus() {
        return getCurrentState();
    }

    @Override
    public String getGuide() {
        return """
        Turing Machine Language Guide:

        % --- Comments start with % or //
        
        # --- Sets (define groups of symbols):
        #digits 0 1 2 3 4 5 6 7 8 9

        tape Main            // Define a tape named 'Main'
        tape Name initialWord // Optional starting contents

        f fromState read -> toState write move tapeName
          - fromState: Current state
          - read: Expected symbol(s) to read (or a set)
          - toState: Next state to move to
          - write: Symbol(s) to write (or a set)
          - move: 
              'R' - move head right
              'L' - move head left
              '-' - don't move
              '0' - halt and reject
              '1' - halt and accept
          - tapeName: Which tape to switch to after move

        Example Programs You Can Run:
             
            % Always declare a Main tape, in this case on tape will be
            % 0110 
            tape Main = {0110}
            
            % in state q0 if you read 0 go to state q1 
            %and change read 0 to 1
            f( q0, 0) = ( q1, 1, R)
            f( q1, 1) = ( q0, 0, R)
            
            f( q1, Blank ) = ( End, Blank, 1) 
            f( q0, Blank )= ( End, Blank, 0) 

        Notes:
          - 'Blank' refers to an empty tape cell.
          - 'Main' is the default active tape.
          - State and Tape names are case-sensitive.
          - Sets (like #digits) can be used inside rules.

        """;
    }


    public String getCurrentState() {
        for (Map.Entry<String, Integer> entry : stateMap.entrySet()) {
            if (entry.getValue() == currentStateIndex) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Map<String, Tape> getTapes() {
        return tapes;
    }

    public String getCurrentTapeName() {
        for (Map.Entry<String, Tape> entry : tapes.entrySet()) {
            if (entry.getValue() == currentTape) return entry.getKey();
        }
        return null;
    }

    public static List<List<String>> readFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return readFromBuffered(reader);
        }
    }

    public static List<List<String>> readFromBuffered(BufferedReader reader) throws IOException {
        List<List<String>> result = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("[(),={}]", " ").replaceAll(" +", " ").trim();
            if (line.isEmpty() || line.startsWith("%")) continue;
            result.add(List.of(line.split(" ")));
        }

        return result;
    }

    private List<String> splitToList(String s) {
        List<String> list = new ArrayList<>();
        for (char c : s.toCharArray()) {
            list.add(String.valueOf(c));
        }
        return list;
    }
}
