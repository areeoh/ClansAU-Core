package com.areeoh.core.framework.commands;

public class Usage {

    private String usage;
    private String description;

    public Usage(String usage, String description) {
        this.usage = usage;
        this.description = description;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}