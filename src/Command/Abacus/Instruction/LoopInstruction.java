package Command.Abacus.Instruction;

import Command.Abacus.AbacusMachine;
import Command.Editor.ConsoleLogger;

import java.util.List;

public class LoopInstruction implements Instruction {
    private final int register;
    private final List<Instruction> body;
    private int loopPc = 0;

    public LoopInstruction(int register, List<Instruction> body) {
        this.register = register;
        this.body = body;
    }

    @Override
    public void execute(AbacusMachine machine) {
        int value = machine.getRegisters().getOrDefault(register, 0);
        ConsoleLogger.info("LOOP r" + register + " = " + value);

        while (value != 0) {
            for (Instruction instr : body) {
                instr.execute(machine);
            }
            value = machine.getRegisters().getOrDefault(register, 0);
            ConsoleLogger.info("LOOP r" + register + " = " + value);
        }

        ConsoleLogger.success("EXIT LOOP r" + register);
    }


    @Override
    public boolean isDone() {
        return false; // a loop is never "done" until its controlling register hits 0
    }

    @Override
    public void reset() {
        for (Instruction instr : body) {
            instr.reset();
        }
    }
}
