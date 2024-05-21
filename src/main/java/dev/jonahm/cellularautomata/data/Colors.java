package dev.jonahm.cellularautomata.data;

import java.awt.*;

public enum Colors {

    MAIN_BG(0x121212),
    NAV_BG(0xFFFFFF, 0.05f),
    NAV_CLOSE_GB(0xE92C2C),
    TASKBAR_BUTTON_BG(0xFFFFFF, 0.9f),
    TASKBAR_BUTTON_FG(0x121212),
    TASKBAR_DIVIDER_BG(0xFFFFFF, 0.125f),
    GRID_BG(0xFFFFFF, 0.125f),
    GRID_CENTER_BG(0xFFFFFF, 0.25f),
    GRID_HOVERED_BG(0x0D85FF, 0.5f),
    SELECTION_FG(0xFFA50D, 0.5f),
    SELECTION_BG(0xFFA50D, 0.125f);

    public static final float TRANSITION_SPEED = 0.125f;

    private final int hex;
    private final float opacity;

    Colors(int hex) {
        this(hex, 1f);
    }

    Colors(int hex, float opacity) {
        this.hex = hex;
        this.opacity = opacity;
    }

    public Color get() {
        Color c = new Color(hex);
        return new Color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, opacity);
    }

    public Color get(long hoverStart) {
        if (hoverStart == -1) return get();
        long transition = (long) (TRANSITION_SPEED * 1000L);
        long timeSinceStart = System.currentTimeMillis() - hoverStart;
        float transitionProgress = Math.min(1f, Math.max(0f, (float) timeSinceStart / (float) transition));
        return darken(0.25f * transitionProgress);
    }

    public Color lighten(float factor) {
        float[] from = get().getRGBComponents(null);
        float[] to = Color.WHITE.getRGBComponents(null);
        from[0] += (to[0] - from[0]) * factor;
        from[1] += (to[1] - from[1]) * factor;
        from[2] += (to[2] - from[2]) * factor;
        return new Color(from[0], from[1], from[2], from[3]);
    }

    public Color darken(float factor) {
        float[] from = get().getRGBComponents(null);
        float[] to = Color.BLACK.getRGBComponents(null);
        from[0] += (to[0] - from[0]) * factor;
        from[1] += (to[1] - from[1]) * factor;
        from[2] += (to[2] - from[2]) * factor;
        return new Color(from[0], from[1], from[2], from[3]);
    }

}
