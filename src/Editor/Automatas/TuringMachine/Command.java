package Editor.Automatas.TuringMachine;

import java.util.List;

class Command {
    List<String> read;
    List<String> write;
    int nextState;
    String move;
    String nextTape;

    public Command(List<String> read, List<String> write, int nextState, String move, String nextTape) {
        this.read = read;
        this.write = write;
        this.nextState = nextState;
        this.move = move;
        this.nextTape = nextTape;
    }
}