package com.preventsgm;

import net.runelite.api.widgets.Widget;

public class SpellFacade {
    private final Widget spell;
    private final String text;

    public SpellFacade(Widget spell, String text) {
        this.spell = spell;
        this.text = text;
    }

    public void toggle(boolean check) {
        if (spell == null) {
            return;
        }
        if (check) {
            spell.setOpacity(0);
            spell.setAction(0, text);
        }
        else {
            spell.setOpacity(128);
            spell.setAction(0, "");
        }
    }
}
