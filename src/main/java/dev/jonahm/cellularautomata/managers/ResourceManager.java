package dev.jonahm.cellularautomata.managers;

import dev.jonahm.cellularautomata.data.resource.ResourceType;
import dev.jonahm.cellularautomata.data.resource.resources.FontResource;
import dev.jonahm.cellularautomata.data.resource.resources.ImageResource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private Font font;
    private final Map<ResourceType, BufferedImage> imgResources = new HashMap<>();

    public ResourceManager() {
        init();
    }

    private void init() {
        imgResources.clear();
        try {
            // registerImgResource(new ImageResource("/img/title.png", ResourceType.IMG_TITLE));
            font = new FontResource().getResource().deriveFont(16f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private void registerImgResource(ImageResource imageResource) throws IOException {
        imgResources.put(imageResource.getResourceType(), imageResource.getResource());
    }

    public BufferedImage getImageResource(ResourceType resourceType) {
        return imgResources.get(resourceType);
    }

    public Font getFont() {
        return font != null ? font : new Font("Arial", Font.PLAIN, 16);
    }
}
