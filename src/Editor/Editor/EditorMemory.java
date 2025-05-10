package Editor.Editor;

import javax.swing.*;
import javax.swing.undo.*;
import java.awt.event.ActionEvent;

public class EditorMemory {
    private final UndoManager undoManager = new UndoManager();

    public void attach(JTextPane textArea) {
        textArea.getDocument().addUndoableEditListener(undoManager);

        textArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        textArea.getActionMap().put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) undoManager.undo();
            }
        });

        textArea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
        textArea.getActionMap().put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) undoManager.redo();
            }
        });
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public void undo() {
        if (canUndo()) undoManager.undo();
    }

    public void redo() {
        if (canRedo()) undoManager.redo();
    }


}
