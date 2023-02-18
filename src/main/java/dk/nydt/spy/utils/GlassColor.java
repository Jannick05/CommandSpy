package dk.nydt.spy.utils;

public class GlassColor {
    public static Integer getGlassColor(String s) {
        switch (s) {
            case "&f": return 0;
            case "&6": return 1;
            case "&5": return 2;
            case "&b": return 3;
            case "&e": return 4;
            case "&a": return 5;
            case "&d": return 6;
            case "&8": return 7;
            case "&7": return 8;
            case "&3": return 9;
            case "&9": return 11;
            case "&2": return 13;
            case "&c": return 14;
            case "&0": return 15;
            default: return null;
        }
    }
}
