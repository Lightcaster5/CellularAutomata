package dev.jonahm.cellularautomata.managers;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.rendercomponent.RenderComponent;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.Navbar;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.SimulationStateText;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.Taskbar;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderManager {

    private final List<RenderComponent> renderComponents = new ArrayList<>();

    @Getter
    private final Taskbar taskbar;

    public RenderManager() {
        taskbar = new Taskbar();
        init();
    }

    private void init() {
        registerRenderComponent(new Navbar());
        registerRenderComponent(taskbar);
        registerRenderComponent(new SimulationStateText());
        registerRenderComponent(CellularAutomata.getSimulationSpace());
    }

    public void renderAll(Graphics2D graphics) {
        renderComponents.forEach(renderComponent -> renderComponent.render(graphics));
    }

    private void registerRenderComponent(RenderComponent renderComponent) {
        renderComponents.add(renderComponent);
        renderComponents.sort(Comparator.comparing(RenderComponent::getRenderPriority));
    }

}
