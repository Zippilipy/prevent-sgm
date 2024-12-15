package com.preventsgm;

import net.runelite.api.widgets.Widget;

public class SuperGlassMakeFacade {
    private final Widget superglassMake;

    public SuperGlassMakeFacade(Widget superglassMake) {
        this.superglassMake = superglassMake;
    }

    public void toggle(boolean check) {
        if (superglassMake == null || superglassMake.isHidden()) return;
        if (check) enable();
        else disable();
    }

    public void shutdown() {
        enable();
    }

    private void enable() {
        superglassMake.setOpacity(0);
        superglassMake.setAction(0, "Superglass Make");
    }

    private void disable() {
        superglassMake.setOpacity(128);
        superglassMake.setAction(0, "");
    }

}
