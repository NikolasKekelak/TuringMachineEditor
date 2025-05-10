package Editor.Engine;

public class AutomatonTypeBundle {
    public final String name;
    public final AutomatonEngine engine;
    public final AutomatonRenderer renderer;
    public final SyntaxHighlighter highlighter;

    public AutomatonTypeBundle(String name, AutomatonEngine engine, AutomatonRenderer renderer, SyntaxHighlighter highlighter) {
        this.name = name;
        this.engine = engine;
        this.renderer = renderer;
        this.highlighter = highlighter;
    }
}