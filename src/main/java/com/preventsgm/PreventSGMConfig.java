package com.preventsgm;

import net.runelite.client.config.*;

@ConfigGroup("preventsgm")
public interface PreventSGMConfig extends Config {
    @ConfigSection(
            name = "Superglass Make",
            description = "Toggles for superglass make. This plugin requires your Withdraw-X to be to set to amount of sand (default 18) and using withdraw-X on sand and withdraw-1 on seaweed.",
            position = 0
    )
    String sgmSection = "Superglass Make";

    @ConfigSection(
            name = "Offerings",
            description = "Toggles for demonic and sinister offering",
            position = 1
    )
    String offeringSection = "Offerings";

    @ConfigSection(
            name = "Sulphurous essence",
            description = "Toggles for behaviour of sulphur essence",
            position = 2
    )
    String sulphurSection = "Sulphur";

    @ConfigItem(
            keyName = "seaweedToggle",
            name = "Disable superglass",
            description = "Disable superglass make given the conditions given below",
            section = sgmSection,
            position = 0

    )
    default boolean seaweedToggle() {
        return true;
    }

    @ConfigItem(
            keyName = "seaweed",
            name = "Amount of seaweed",
            description = "The amount of seaweed needed to allow the casting of superglass make",
            section = sgmSection,
            position = 1
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
            description = "The amount of sand needed to allow the casting of superglass make",
            section = sgmSection,
            position = 2
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
            description = "Disables the withdraw button on sand and seaweed if you have the given amount of sand and seaweed",
            section = sgmSection,
            position = 3
    )
    default boolean disableWithdraw() {
        return true;
    }

    @ConfigItem(
            keyName = "demonicToggle",
            name = "Disable demonic offering",
            description = "Disable demonic offering given the conditions given below",
            section = offeringSection,
            position = 0
    )
    default boolean demonicToggle() {
        return true;
    }

    @ConfigItem(
            keyName = "demonic",
            name = "Amount of ashes",
            description = "Minimum amount of ashes needed to cast demonic offering",
            section = offeringSection,
            position = 1
    )
    @Range(
            min = 1,
            max = 3
    )
    default int demonic() {
        return 3;
    }

    @ConfigItem(
            keyName = "sinisterToggle",
            name = "Disable sinister offering",
            description = "Disable sinister offering given the conditions given below",
            section = offeringSection,
            position = 2
    )
    default boolean sinisterToggle() {
        return true;
    }

    @ConfigItem(
            keyName = "sinister",
            name = "Amount of bones",
            description = "Minimum amount of bones needed to cast sinister offering",
            section = offeringSection,
            position = 3
    )
    @Range(
            min = 1,
            max = 3
    )
    default int sinister() {
        return 3;
    }

    @ConfigItem(
            keyName = "sulphurTeleportToggle",
            name = "Disable teleports",
            description = "Disables teleport items/spells if you have sulphur essence in your inventory",
            section = sulphurSection,
            position = 0

    )
    default boolean sulphurTeleportToggle() {
        return true;
    }

    @ConfigItem(
            keyName = "sulphurAmountToggle",
            name = "Amount of sulphur essence",
            description = "At how many essences should teleports be disabled?",
            section = sulphurSection,
            position = 1

    )
    @Range(
            min = 1
    )
    default int sulphurAmountToggle() {
        return 1;
    }
}
