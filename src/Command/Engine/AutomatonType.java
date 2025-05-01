package Command.Engine;

import Command.Engine.AutomatonEngine;
import Command.Engine.AutomatonRenderer;

public class AutomatonType {
    public final String name;
    public final AutomatonEngine engine;
    public final AutomatonRenderer renderer;
    public final SyntaxHighlighter syntaxHighlighter;
    public AutomatonType(String name, AutomatonEngine engine, AutomatonRenderer renderer, SyntaxHighlighter syntaxHighlighter) {
        this.name = name;
        this.engine = engine;
        this.renderer = renderer;
        this.syntaxHighlighter=syntaxHighlighter;
    }
}
