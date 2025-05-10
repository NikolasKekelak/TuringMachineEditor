package Editor.Abacus.Instruction;

import Editor.Abacus.AbacusMachine;

public interface Instruction {
    void execute(AbacusMachine machine);
    boolean isDone(); // for loops
    void reset();     // for reset/play
}