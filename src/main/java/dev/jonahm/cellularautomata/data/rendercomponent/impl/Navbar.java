package dev.jonahm.cellularautomata.data.rendercomponent.impl;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.Colors;
import dev.jonahm.cellularautomata.data.rendercomponent.RenderComponent;
import dev.jonahm.cellularautomata.utils.DrawingUtils;

import java.awt.*;

public class Navbar implements RenderComponent {

    public static int HEIGHT = 30;

    @Override
    public int getRenderPriority() {
        return 10;
    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(Colors.NAV_BG.get());
        graphics.fillRect(0, 0, graphics.getClipBounds().width, HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.setFont(CellularAutomata.getInstance().getFont());
        String title = "An Idiot's Attempt at Making Cellular Automata";
        graphics.drawString(title, graphics.getClipBounds().width / 2 - DrawingUtils.getMessageWidth(title, graphics) / 2, DrawingUtils.getMessageHeight(title, graphics) / 2 + HEIGHT / 2);
    }

}
