package dev.jonahm.cellularautomata.data.rendercomponent;

import java.awt.*;

public interface RenderComponent {

    int getRenderPriority();

    void render(Graphics2D graphics);

}
