package dev.jonahm.cellularautomata.data.taskbarcomponent.impl;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.Colors;
import dev.jonahm.cellularautomata.data.taskbarcomponent.TaskbarComponent;
import dev.jonahm.cellularautomata.data.taskbarcomponent.TaskbarComponentType;
import dev.jonahm.cellularautomata.utils.DrawingUtils;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
public class ButtonComponent implements TaskbarComponent {

    @Setter
    private String text;
    private final int x, y, width, height;
    private final Runnable onClick;

    private boolean hovered;
    private long hoverStart = -1;

    public ButtonComponent(String text, int x, int y, int width, int height, Runnable onClick) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.onClick = onClick;
    }

    @Override
    public TaskbarComponentType getTaskbarComponentType() {
        return TaskbarComponentType.BUTTON;
    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(Colors.TASKBAR_BUTTON_BG.get(hoverStart));
        graphics.fillRoundRect(x, y, width, height, 5, 5);
        graphics.setColor(Colors.TASKBAR_BUTTON_FG.get(hoverStart));
        graphics.setFont(CellularAutomata.getInstance().getFont());
        graphics.setFont(graphics.getFont().deriveFont(Font.BOLD).deriveFont(12f));
        int textWidth = DrawingUtils.getMessageWidth(text, graphics), textHeight = DrawingUtils.getMessageHeight(text, graphics);
        graphics.drawString(text, x + width / 2 - textWidth / 2, y + textHeight / 2 + height / 2);
    }

    public void onClick(Point point) {
        int x = point.x, y = point.y;
        if (x >= getX() && x <= getX() + width && y >= getY() && y <= getY() + height) onClick.run();
    }

    public void onHover(Point point) {
        int x = point.x, y = point.y;
        boolean preHovered = hovered;
        hovered = x >= getX() && x <= getX() + width && y >= getY() && y <= getY() + height;
        if (preHovered != hovered) {
            hoverStart = hovered ? System.currentTimeMillis() : -1;
        }
    }

}
