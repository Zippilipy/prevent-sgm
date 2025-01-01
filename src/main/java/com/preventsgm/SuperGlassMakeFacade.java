package com.preventsgm;

import net.runelite.api.widgets.Widget;

public class SuperGlassMakeFacade {
    private final Widget superglassMake;

    public SuperGlassMakeFacade(Widget superglassMake) {
        this.superglassMake = superglassMake;
    }

    public void toggle(boolean check) {
        if (superglassMake == null) {
            return;
        }
        if (check) {
            superglassMake.setOpacity(0);
            superglassMake.setAction(0, "Superglass Make");
        }
        else {
            superglassMake.setOpacity(128);
            superglassMake.setAction(0, "");
        }
    }
}
