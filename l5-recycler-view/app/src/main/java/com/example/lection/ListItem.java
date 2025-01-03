package com.example.lection;

public class ListItem {
    private String name;
    private int size;
    private boolean selected;

    public ListItem(String name, int size, boolean selected) {
        this.name = name;
        this.size = size;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}