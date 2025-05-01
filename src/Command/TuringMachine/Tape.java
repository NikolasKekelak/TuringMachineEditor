package Command.TuringMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tape {
    private final List<String> tape;
    private final List<String> initialContent;
    private final int initialSize;
    private int currentIndex;
    private final int initialHeadPosition;

    public Tape(int size) {
        this.tape = new ArrayList<>(Collections.nCopies(size, "Blank"));
        this.initialContent = new ArrayList<>(this.tape);
        this.initialSize = size;
        this.currentIndex = size / 2;
        this.initialHeadPosition = currentIndex;
    }

    public Tape(int size, List<String> initialContent) {
        this(size);
        for (int i = 0; i < initialContent.size(); i++) {
            tape.set(currentIndex + i, initialContent.get(i));
        }
        this.initialContent.clear();
        for (String s : tape) {
            this.initialContent.add(s);
        }
    }

    public String getCurrent() {
        return tape.get(currentIndex);
    }

    public void setCurrent(String value) {
        tape.set(currentIndex, value);
    }

    public void moveLeft() {
        if (currentIndex > 0) {
            currentIndex--;
        }
    }

    public void moveRight() {
        if (currentIndex < tape.size() - 1) {
            currentIndex++;
        }
    }

    public void reset() {
        for (int i = 0; i < tape.size(); i++) {
            tape.set(i, "Blank");
        }
        for (int i = 0; i < initialContent.size(); i++) {
            tape.set(i, initialContent.get(i));
        }
        currentIndex = initialHeadPosition;
    }

    public void display() {
        int start = Math.max(currentIndex - 5, 0);
        int end = Math.min(currentIndex + 5, tape.size());
        System.out.println("-".repeat(60));
        for (int i = start; i < end; i++) {
            if (i == currentIndex) {
                System.out.print(" | [" + tape.get(i) + "]");
            } else {
                System.out.print(" | " + tape.get(i));
            }
        }
        System.out.println(" |");
        System.out.println("-".repeat(60));
    }

    public String visualSnippet() {
        int start = Math.max(currentIndex - 5, 0);
        int end = Math.min(currentIndex + 5, tape.size());

        StringBuilder sb = new StringBuilder("... ");
        for (int i = start; i < end; i++) {
            if (i == currentIndex) {
                sb.append("[").append(tape.get(i)).append("] ");
            } else {
                sb.append(tape.get(i)).append(" ");
            }
        }
        sb.append("...");
        return sb.toString();
    }

    public int getHeadPosition() {
        return currentIndex;
    }

    public String getAt(int i) {
        return (i >= 0 && i < tape.size()) ? tape.get(i) : "Blank";
    }

    public int length() {
        return tape.size();
    }
}
