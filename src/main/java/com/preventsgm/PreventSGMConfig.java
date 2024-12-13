package com.preventsgm;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("preventsgm")
public interface PreventSGMConfig extends Config {
	@ConfigItem(
		keyName = "seaweed",
		name = "Amount of seaweed",
		description = "The amount of seaweed needed to allow the casting of superglassmake"
	)
	default int seaweed() {
        return 3;
	}
	@ConfigItem(
			keyName = "sand",
			name = "Amount of sand",
			description = "The amount of seaweed needed to allow the casting of superglassmake"
	)
	default int sand() {
		return 18;
	}
}
