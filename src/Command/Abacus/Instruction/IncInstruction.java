package Command.Abacus.Instruction;

import Command.Abacus.AbacusMachine;
import Command.Editor.ConsoleLogger;

import java.awt.*;

public class IncInstruction implements Instruction {
    private final int register;

    public IncInstruction(int register) {
        this.register = register;
    }

    @Override
    public void execute(AbacusMachine machine) {
        int value = machine.registers.getOrDefault(register, 0);
        machine.registers.put(register, value + 1);
        ConsoleLogger.info("INC r" + register + ": " + value + " → " + (value + 1));

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