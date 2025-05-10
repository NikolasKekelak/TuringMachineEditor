package Editor.Engine;

public interface AutomatonEngine {
    void compile(String sourceCode) throws Exception;
    boolean step();                    // One computation step
    void reset();                      // Reset to initial state
    void play();                       // Start automatic execution
    void stop();                       // Stop execution

    boolean isHalted();
    boolean isAccepted();
    String getCurrentStatus();         // e.g., state name or grammar derivation step
    String getGuide();
}
