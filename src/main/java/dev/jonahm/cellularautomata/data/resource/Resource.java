package dev.jonahm.cellularautomata.data.resource;

public class Resource {

    private final String path;
    private final ResourceType resourceType;

    public Resource(String path, ResourceType resourceType) {
        this.path = path;
        this.resourceType = resourceType;
    }

    public String getPath() {
        return path;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
