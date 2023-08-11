package com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view;

public class StatusOption {
    private String label;
    private Boolean value;

    public StatusOption(String label, Boolean value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }
}
