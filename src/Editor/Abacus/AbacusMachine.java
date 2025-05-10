package Editor.Abacus;

import Editor.Abacus.Instruction.DecInstruction;
import Editor.Abacus.Instruction.IncInstruction;
import Editor.Abacus.Instruction.Instruction;
import Editor.Abacus.Instruction.LoopInstruction;
import Editor.Editor.ConsoleLogger;
import Editor.Engine.AutomatonEngine;

import java.util.*;

public class AbacusMachine implements AutomatonEngine {
    public Map<Integer, Integer> registers = new HashMap<>();
    public List<Instruction> program = new ArrayList<>();
    private Iterator<Instruction> iterator;
    private Instruction current = null;
    public boolean halted = false;
    public boolean accepted = false;


    private Thread runnerThread;
    private volatile boolean running = false;

    @Override
    public void compile(String sourceCode) throws Exception {
        ConsoleLogger.log.accept("Compiling source...");

        // 1. Remove comments (everything after '%' on each line)
        StringBuilder noComments = new StringBuilder();
        String[] lines = sourceCode.split("\n");
        for (String line : lines) {
            int commentIndex = line.indexOf('%');
            if (commentIndex != -1) {
                line = line.substring(0, commentIndex);
            }
            noComments.append(line);
        }
        // 2. Remove all whitespace characters (spaces, tabs, newlines)
        String code = noComments.toString().replaceAll("\\s+", "");

        // Prepare a stack for nested parsing of loops
        List<Instruction> currentList = new ArrayList<>();  // top-level instructions
        Stack<List<Instruction>> stack = new Stack<>();
        stack.push(currentList);

        // 3. Parse the code string character by character
        int i = 0;
        while (i < code.length()) {
            char c = code.charAt(i);
            if (c == 'a' || c == 's') {
                // Increment or decrement instruction
                boolean isIncrement = (c == 'a');
                i++;  // move past 'a' or 's'
                if (i >= code.length() || !Character.isDigit(code.charAt(i))) {
                    throw new Exception("Missing register number after '" + c + "' at position " + (i - 1));
                }
                // Parse the register number (one or more digits)
                int startNum = i;
                while (i < code.length() && Character.isDigit(code.charAt(i))) {
                    i++;
                }
                String regStr = code.substring(startNum, i);
                int register;
                try {
                    register = Integer.parseInt(regStr);
                } catch (NumberFormatException e) {
                    // This shouldn't normally happen as regStr is digits, but just in case of overflow
                    throw new Exception("Invalid register number: " + regStr);
                }
                Instruction instr = isIncrement
                        ? new IncInstruction(register)
                        : new DecInstruction(register);
                stack.peek().add(instr);
                // continue parsing the next character
            } else if (c == '(') {
                // Start of a loop - push a new instruction list for the loop body
                List<Instruction> loopBody = new ArrayList<>();
                stack.push(loopBody);
                i++;  // skip '(' and parse subsequent instructions inside the loop
            } else if (c == ')') {
                // End of a loop - pop the current loop body and create a LoopInstruction
                if (stack.size() < 2) {
                    // Nothing to pop – no matching '(' for this ')'
                    throw new Exception("Unmatched ')' at position " + i);
                }
                List<Instruction> completedBody = stack.pop();
                i++;  // skip the ')'
                if (i >= code.length() || !Character.isDigit(code.charAt(i))) {
                    throw new Exception("Missing register number after ')' at position " + (i - 1));
                }
                // Parse the loop's register number
                int startNum = i;
                while (i < code.length() && Character.isDigit(code.charAt(i))) {
                    i++;
                }
                String regStr = code.substring(startNum, i);
                int loopRegister;
                try {
                    loopRegister = Integer.parseInt(regStr);
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid register number: " + regStr);
                }
                // Create the LoopInstruction with its body and add to the parent list
                Instruction loopInstr = new LoopInstruction(loopRegister, completedBody);
                stack.peek().add(loopInstr);
            } else {
                // Any other character is unexpected (including digits in an illegal place)
                throw new Exception("Unexpected character '" + c + "' at position " + i);
            }
        }

        // If there's any unmatched '(' remaining, the stack would have more than one list
        if (stack.size() != 1) {
            throw new Exception("Unmatched '(' in source code");
        }

        // Assign the compiled instruction list to the program field
        this.program = stack.pop();  // top-level instructions list

        this.iterator = program.iterator();
        this.halted = false;
        this.accepted = false;

        ConsoleLogger.success("Compiled " + program.size() + " instructions.");
    }

    @Override
    public boolean step() {
        if (halted || iterator == null || !iterator.hasNext()) {
            halted = true;
            ConsoleLogger.success("Machine halted.");
            return false;
        }

        current = iterator.next();
        ConsoleLogger.info("STEP: Executing " + current.getClass().getSimpleName());
        current.execute(this);

        ConsoleLogger.log.accept("State: " + registers);

        if (!iterator.hasNext()) {
            halted = true;
            accepted = true;
            ConsoleLogger.success("Program finished. Accepted.");
        }

        return true;
    }

    @Override
    public void reset() {
        this.iterator = program.iterator();
        this.halted = false;
        this.accepted = false;
        this.registers.clear();

        for (Instruction instr : program) {
            instr.reset();
        }

        ConsoleLogger.info("Machine reset.");
    }

    @Override
    public void play() {
        reset();
        ConsoleLogger.info("Auto-play started.");
        if (running || halted) return;

        running = true;
        runnerThread = new Thread(() -> {
            try {
                while (running && !halted) {
                    boolean stepped = step();
                    if (!stepped) break;
                    Thread.sleep(200);  // delay between steps, tweak as needed
                }
            } catch (InterruptedException e) {
                // Thread was interrupted — do nothing or log
            } finally {
                running = false;
            }
        });
        runnerThread.start();
    }

    @Override
    public void stop() {
        running = false;
        if (runnerThread != null) {
            runnerThread.interrupt();
            runnerThread = null;
        }

        ConsoleLogger.log.accept("Auto-play stopped.");
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
        return "";
    }

    @Override
    public String getGuide() {
        return "";
    }

    public Map<Integer, Integer> getRegisters() {
        return Collections.unmodifiableMap(registers);
    }


}
