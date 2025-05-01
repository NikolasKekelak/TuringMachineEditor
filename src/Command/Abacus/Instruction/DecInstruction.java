package Command.Abacus.Instruction;

import Command.Abacus.AbacusMachine;
import Command.Abacus.Instruction.Instruction;

public class DecInstruction implements Instruction {
    private final int register;

    public DecInstruction(int register) {
        this.register = register;
    }

    @Override
    public void execute(AbacusMachine machine) {
        int value = machine.registers.getOrDefault(register, 0);
        if (value > 0) {
            machine.registers.put(register, value - 1);
        }
        machine.pc++;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void reset() {
        // no state to reset
    }
}
