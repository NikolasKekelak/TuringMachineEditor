package Command.Abacus.Instruction;

import Command.Abacus.AbacusMachine;

public class IncInstruction implements Instruction {
    private final int register;

    public IncInstruction(int register) {
        this.register = register;
    }

    @Override
    public void execute(AbacusMachine machine) {
        machine.registers.put(register, machine.registers.getOrDefault(register, 0) + 1);
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