package dev.jonahm.cellularautomata.data.rendercomponent.impl;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.rendercomponent.RenderComponent;
import dev.jonahm.cellularautomata.utils.DrawingUtils;

import java.awt.*;

public class SimulationStateText implements RenderComponent {

    @Override
    public int getRenderPriority() {
        return 15;
    }

    @Override
    public void render(Graphics2D graphics) {
        int padding = 10, lastHeight = 0;
        graphics.setFont(CellularAutomata.getInstance().getFont());
        graphics.setColor(Color.WHITE);
        String stateText = CellularAutomata.getSimulationSpace().getSimulationState().name();
        graphics.drawString(stateText, CellularAutomata.WIDTH - DrawingUtils.getMessageWidth(stateText, graphics) - padding, Navbar.HEIGHT + DrawingUtils.getMessageHeight(stateText, graphics) + padding);
        lastHeight += DrawingUtils.getMessageHeight(stateText, graphics);
        String generationsText = "Generation: " + CellularAutomata.getSimulationSpace().getGeneration();
        graphics.drawString(generationsText, CellularAutomata.WIDTH - DrawingUtils.getMessageWidth(generationsText, graphics) - padding, Navbar.HEIGHT + DrawingUtils.getMessageHeight(generationsText, graphics) + lastHeight + padding * 2);
        lastHeight += DrawingUtils.getMessageHeight(generationsText, graphics);
        String simulationSpeedText = "Simulation speed: " + CellularAutomata.getSimulationSpace().getGenerationsInLastSecondAvg() + " gens/s";
        graphics.drawString(simulationSpeedText, CellularAutomata.WIDTH - DrawingUtils.getMessageWidth(simulationSpeedText, graphics) - padding, Navbar.HEIGHT + DrawingUtils.getMessageHeight(simulationSpeedText, graphics) + lastHeight + padding * 3);

    }

}
