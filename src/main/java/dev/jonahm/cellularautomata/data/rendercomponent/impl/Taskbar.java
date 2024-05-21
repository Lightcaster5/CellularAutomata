package dev.jonahm.cellularautomata.data.rendercomponent.impl;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.Colors;
import dev.jonahm.cellularautomata.data.SimulationState;
import dev.jonahm.cellularautomata.data.rendercomponent.RenderComponent;
import dev.jonahm.cellularautomata.data.taskbarcomponent.TaskbarComponent;
import dev.jonahm.cellularautomata.data.taskbarcomponent.impl.ButtonComponent;
import dev.jonahm.cellularautomata.data.taskbarcomponent.impl.DividerComponent;
import dev.jonahm.cellularautomata.utils.DrawingUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Taskbar implements RenderComponent {

    public static int HEIGHT = 110;

    private final List<TaskbarComponent> components = new ArrayList<>();

    public Taskbar() {
        components.add(new DividerComponent(CellularAutomata.WIDTH - 190));

        components.add(new ButtonComponent("Step (S)", CellularAutomata.WIDTH - 180, CellularAutomata.HEIGHT - HEIGHT + 10, 75, 20, () -> {
            CellularAutomata.getSimulationSpace().step();
        }));

        components.add(new ButtonComponent("Play (P)", CellularAutomata.WIDTH - 180, CellularAutomata.HEIGHT - HEIGHT + 40, 75, 20, () -> {
            CellularAutomata.getSimulationSpace().interpolateSimulationStateState();
            ButtonComponent buttonComponent = getButtonByName("Play (P)", "Pause (P)");
            buttonComponent.setText(CellularAutomata.getSimulationSpace().getSimulationState() == SimulationState.PAUSED ? "Play (P)" : "Pause (P)");
        }));

        components.add(new ButtonComponent("Grid (G)", CellularAutomata.WIDTH - 180, CellularAutomata.HEIGHT - HEIGHT + 70, 75, 20, () -> {
            CellularAutomata.getSimulationSpace().interpolateGrid();
        }));

        components.add(new DividerComponent(CellularAutomata.WIDTH - 95));

        components.add(new ButtonComponent("Clear (C)", CellularAutomata.WIDTH - 85, CellularAutomata.HEIGHT - HEIGHT + 10, 75, 20, () -> {
            CellularAutomata.getSimulationSpace().clear();
        }));
        /*components.add(new ButtonComponent("Save", CellularAutomata.WIDTH - 85, CellularAutomata.HEIGHT - HEIGHT + 40, 75, 20, () -> {
            System.out.println("Save was clicked");
        }));
        components.add(new ButtonComponent("Load", CellularAutomata.WIDTH - 85, CellularAutomata.HEIGHT - HEIGHT + 70, 75, 20, () -> {
            System.out.println("Load was clicked");
        }));*/

    }

    @Override
    public int getRenderPriority() {
        return 10;
    }

    @Override
    public void render(Graphics2D graphics) {
        int padding = 10;
        graphics.setColor(Colors.NAV_BG.get());
        graphics.fillRect(0, graphics.getClipBounds().height - HEIGHT, graphics.getClipBounds().width, HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.setFont(CellularAutomata.getInstance().getFont());
        String controlsText = "Controls";
        graphics.drawString(controlsText, padding, graphics.getClipBounds().height - HEIGHT + DrawingUtils.getMessageHeight(controlsText, graphics) + padding);

        components.forEach(taskbarComponent -> taskbarComponent.render(graphics));
    }

    public void onClick(Point point) {
        components.forEach(component -> {
            if (component instanceof ButtonComponent) {
                ((ButtonComponent) component).onClick(point);
            }
        });
    }

    public void onHover(Point point) {
        components.forEach(component -> {
            if (component instanceof ButtonComponent) {
                ((ButtonComponent) component).onHover(point);
            }
        });
    }

    public ButtonComponent getButtonByName(String name) {
        return (ButtonComponent) components.stream()
                .filter(c -> c instanceof ButtonComponent && ((ButtonComponent) c).getText().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public ButtonComponent getButtonByName(String... names) {
        for (String name : names) {
            if (getButtonByName(name) != null) return getButtonByName(name);
        }
        return null;
    }

}
