package dev.jonahm.cellularautomata.data.taskbarcomponent;

import java.awt.*;

public interface TaskbarComponent {

    TaskbarComponentType getTaskbarComponentType();

    void render(Graphics2D graphics);

}
