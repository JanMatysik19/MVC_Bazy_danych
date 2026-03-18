package Zadanie.Utils;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public class Stylised<T extends JComponent> {
    private final T component;

    public Stylised(T component) {
        this.component = component;
    }

    public T create() {
        return component;
    }

    public Stylised<T> sized(int w, int h) {
        sized(component, w, h);
        return this;
    }
    public static void sized(JComponent component, int w, int h) {
        if(w == -1) w = Integer.MAX_VALUE;
        if(h == -1) h = Integer.MAX_VALUE;
        var size = new Dimension(w, h);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
    }
    public static void sized(JComponent component, Dimension size) {
        sized(component, size.width, size.height);
    }

    public Stylised<T> stylised(String... styleClasses) {
        stylised(component, styleClasses);
        return this;
    }
    public static void stylised(JComponent component, String... styleClasses) {
        var value = String.join(" ", styleClasses);
        component.putClientProperty(FlatClientProperties.STYLE_CLASS, value);
    }
}