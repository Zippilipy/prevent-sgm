package com.preventsgm;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("preventsgm")
public interface PreventSGMConfig extends Config {
    @ConfigItem(
            keyName = "seaweed",
            name = "Amount of seaweed",
            description = "The amount of seaweed needed to allow the casting of superglass make"
    )
    @Range(
            min = 1,
            max = 3
    )
    default int seaweed() {
        return 3;
    }

    @ConfigItem(
            keyName = "sand",
            name = "Amount of sand",
            description = "The amount of sand needed to allow the casting of superglass make"
    )
    @Range(
            min = 6,
            max = 18
    )
    default int sand() {
        return 18;
    }

    @ConfigItem(
            keyName = "disableWithdraw",
            name = "Disable withdraw given condition",
            description = "Disables the withdraw button on sand and seaweed if you have the given amount of sand and seaweed"
    )
    default boolean disableWithdraw() {
        return true;
    }
}
