package com.preventsgm;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PreventSGMTest {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(PreventSGMPlugin.class);
        RuneLite.main(args);
    }
}
