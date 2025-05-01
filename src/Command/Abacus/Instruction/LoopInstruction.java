package Command.Abacus.Instruction;

import Command.Abacus.AbacusMachine;
import Command.Abacus.Instruction.Instruction;

import java.util.List;

public class LoopInstruction implements Instruction {
    private final int register;
    private final List<Instruction> body;
    private int loopPc = 0;  // internal program counter

    public LoopInstruction(int register, List<Instruction> body) {
        this.register = register;
        this.body = body;
    }

    @Override
    public void execute(AbacusMachine machine) {
        while (loopPc < body.size()) {
            Instruction inst = body.get(loopPc);
            inst.execute(machine);

            // Only step forward if that instruction is "done"
            if (inst.isDone()) {
                loopPc++;
            } else {
                break;
            }
            return; // yield control
        }

        // loopPc reached end of body
        if (machine.registers.getOrDefault(register, 0) != 0) {
            loopPc = 0; // repeat loop
        } else {
            machine.pc++;  // exit loop
        }
    }

    @Override
    public boolean isDone() {
        return false; // will be handled through outer machine pc
    }

    @Override
    public void reset() {
        loopPc = 0;
        for (Instruction i : body) i.reset();
    }
}
