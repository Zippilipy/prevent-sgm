package com.preventsgm;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.api.widgets.ComponentID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Prevent Superglass Make",
        description = "Prevents casting superglass make if you do not "
                + "have exactly 18 buckets of sand and 3 giant seaweed.")
public class PreventSGMPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private PreventSGMConfig config;

    // These numbers were found with print debugging
    private static final int WITHDRAW = 786445;
    private static final int DEPOSIT_ALL = 786476;
    private static final int DEPOSIT = 983043;
    private static final int MAKE = 14286969;
    // This number was found with the widget inspector
    private static final int SUPERGLASS_MAKE = 14286969;

    private static SuperGlassMakeFacade superglassmake = new SuperGlassMakeFacade(null);

    private int amountOfSeaweed = 0;
    private int amountOfSand = 0;

    @Override
    protected void startUp() throws Exception {
        amountOfSand = 0;
        amountOfSeaweed = 0;
    }

    @Override
    protected void shutDown() throws Exception {
        amountOfSand = 0;
        amountOfSeaweed = 0;
        superglassmake.shutdown();
    }

    @Subscribe
    public void onPlayerSpawned(PlayerSpawned event) {
        if (event.getPlayer().equals(client.getLocalPlayer())) {
            superglassmake = new SuperGlassMakeFacade(client.getWidget(SUPERGLASS_MAKE));
        }
    }

    @Subscribe
    public void onBeforeRender(BeforeRender event) {
        superglassmake.toggle(checkInventory());
    }

    /*
     * This is a pretty silly way of doing this, but
     * onMenuOptionClicked gives the fastest response
     * and so has to be used if you want it to work
     * being tick perfect (that is to say, pressing
     * the superglass make spell before your inventory
     * has updated)
     */
    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event) {
        switch (event.getParam1()) {
            case WITHDRAW:
                updateInventory(event.getItemId());
                break;
            case DEPOSIT:
                if (event.getItemId() == ItemID.GIANT_SEAWEED) {
                    amountOfSeaweed -= 1;
                } else if (event.getItemId() == ItemID.BUCKET_OF_SAND) {
                    amountOfSand = 0;
                }
                break;
            case DEPOSIT_ALL:
            case MAKE:
                amountOfSand = 0;
                amountOfSeaweed = 0;
                break;
            default:
        }
    }

    private void updateInventory(int itemID) {
        if (client.getWidget(ComponentID.BANK_CONTAINER).isHidden()) {
            return;
        }
        if (itemID == ItemID.GIANT_SEAWEED) {
            amountOfSeaweed += 1;
        } else if (itemID == ItemID.BUCKET_OF_SAND) {
            amountOfSand += config.sand();
        }
    }

    private boolean checkInventory() {
        return amountOfSeaweed == config.seaweed() && amountOfSand == config.sand();
    }

    @Provides
    PreventSGMConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PreventSGMConfig.class);
    }
}
