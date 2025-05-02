package Command.Editor;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConsoleLogger {
    public static Consumer<String> log = msg -> {};
    public static BiConsumer<String, Color> logColor = (msg, color) -> {};// no-op by default

    public static void info(String msg) {
        logColor.accept(msg, Color.WHITE);
    }

    public static void success(String msg) {
        logColor.accept(msg, Color.GREEN);
    }

    public static void warn(String msg) {
        logColor.accept(msg, Color.ORANGE);
    }

    public static void error(String msg) {
        logColor.accept(msg, Color.RED);
    }

}
