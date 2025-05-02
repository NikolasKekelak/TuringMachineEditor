package Command.Editor;

import java.util.function.Consumer;

public class ConsoleLogger {
    public static Consumer<String> log = msg -> {}; // no-op by default
}
