package com.msg.entities;

public enum ProductStatus {
    NEW("NEW"),
    IN_STOCK("IN_STOCK"),
    DISCONTINUED("DISCONTINUED");

    public final String label;

    private ProductStatus(String label) {
        this.label = label;
    }

}
