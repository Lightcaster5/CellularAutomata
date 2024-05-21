package dev.jonahm.cellularautomata.data.taskbarcomponent.impl;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.Colors;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.Taskbar;
import dev.jonahm.cellularautomata.data.taskbarcomponent.TaskbarComponent;
import dev.jonahm.cellularautomata.data.taskbarcomponent.TaskbarComponentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
@Getter
public class DividerComponent implements TaskbarComponent {

    private final int x;

    @Override
    public TaskbarComponentType getTaskbarComponentType() {
        return TaskbarComponentType.DIVIDER;
    }

    @Override
    public void render(Graphics2D graphics) {
        int padding = 15;
        graphics.setColor(Colors.TASKBAR_DIVIDER_BG.get());
        graphics.fillRoundRect(x, CellularAutomata.HEIGHT - Taskbar.HEIGHT + padding, 1, Taskbar.HEIGHT - padding * 2, 2, 2);
    }
}
