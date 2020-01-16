package com.rubix.artifacts;

import java.awt.*;

public class Plane {

    private String alias;
    private String color;

    public Plane(String alias, String color){
        setAlias(alias);
        setColor(color);
    }

    public void setAlias(String alais) {
        this.alias = alais;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlias() {
        return alias;
    }

    public String getColor() {
        return color;
    }
}
