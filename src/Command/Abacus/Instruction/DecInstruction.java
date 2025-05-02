package Command.Abacus.Instruction;

import Command.Abacus.AbacusMachine;
import Command.Abacus.Instruction.Instruction;
import Command.Editor.ConsoleLogger;

public class DecInstruction implements Instruction {
    private final int register;

    public DecInstruction(int register) {
        this.register = register;
    }

    @Override
    public void execute(AbacusMachine machine) {
        int before = machine.registers.getOrDefault(register, 0);
        if (before > 0) {
            machine.registers.put(register, before - 1);
            ConsoleLogger.log.accept("DEC r" + register + ": " + before + " → " + (before - 1));
        } else {
            ConsoleLogger.log.accept("DEC r" + register + ": skipped (already 0)");
        }
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
