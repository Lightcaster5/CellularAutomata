package dev.jonahm.cellularautomata.utils;

import java.awt.*;
import java.awt.font.TextLayout;

public class DrawingUtils {

    public static int getMessageWidth(String str, Graphics2D graphics) {
        return (int) graphics.getFontMetrics().getStringBounds(str, graphics).getWidth();
    }

    public static int getMessageHeight(String str, Graphics2D graphics) {
        if (str.length() == 0) return 0;
        return (int) new TextLayout(str, graphics.getFont(), graphics.getFontRenderContext()).getBounds().getHeight();
    }

}
