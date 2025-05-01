package Command.Abacus;

import Command.Abacus.Instruction.Instruction;
import Command.Engine.AutomatonEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbacusMachine implements AutomatonEngine {
    public Map<Integer, Integer> registers = new HashMap<>();
    public List<Instruction> program = new ArrayList<>();
    public int pc = 0;
    public boolean halted = false;
    public boolean accepted = false;


    @Override
    public void compile(String sourceCode) throws Exception {

    }

    @Override
    public boolean step() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void play() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isHalted() {
        return false;
    }

    @Override
    public boolean isAccepted() {
        return false;
    }

    @Override
    public String getCurrentStatus() {
        return "";
    }

    @Override
    public String getGuide() {
        return "";
    }
}
