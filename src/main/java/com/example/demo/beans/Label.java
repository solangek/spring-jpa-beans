package com.example.demo.beans;

import org.springframework.stereotype.Component;

@Component(value="autowiredLabelDependency")
public class Label {
    private String label = "Arbitrary Label";
    public Label() {
    }
    public String toString() {
        return label;
    }
    public void setLabel(String l) {
        label = l;
    }
}