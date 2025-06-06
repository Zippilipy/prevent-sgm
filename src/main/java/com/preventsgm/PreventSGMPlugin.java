package com.preventsgm;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.events.*;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.Arrays;

import static com.preventsgm.IsTeleportItem.isWearableParam1;
import static com.preventsgm.IsTeleportItem.itemIsTeleportItem;

@Slf4j
@PluginDescriptor(
        name = "Prevent Misclicks",
        description = "Prevents certain missclicks in spellbooks, such as superglass make if you don't "
                + "have exactly 18 buckets of sand and 3 giant seaweed. Formerly known as 'Prevent superglass make'")
public class PreventSGMPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private PreventSGMConfig config;

    // These numbers were found with print debugging
    private static final int WITHDRAW = 786445;
    private static final int DEPOSIT_ALL = 786476;
    private static final int DEPOSIT = 983043;
    private static final int SUPERGLASS_MAKE = 14286969;
    private static final int DEMONIC_OFFERING = 14287025;
    private static final int SINISTER_OFFERING = 14287026;
    private static final int WEARABLE_TELEPORT = 25362449;

    private int amountOfSeaweed = 0;
    private int amountOfSand = 0;
    private boolean sinister = false;
    private boolean demonic = false;

    @Override
    protected void startUp() throws Exception {
    }

    @Override
    protected void shutDown() throws Exception {
        if (client == null) {
            return;
        }
        toggle(client.getWidget(SUPERGLASS_MAKE), true);
        toggle(client.getWidget(DEMONIC_OFFERING), true);
        toggle(client.getWidget(SINISTER_OFFERING), true);
    }

    @Subscribe
    public void onPlayerSpawned(PlayerSpawned event) {
        if (event.getPlayer().equals(client.getLocalPlayer())) {
            Widget inventory = client.getWidget(ComponentID.INVENTORY_CONTAINER);
            Widget[] items = inventory.getChildren();
            amountOfSand = (int) Arrays.stream(items).filter(item -> item.getItemId() == ItemID.BUCKET_SAND).count();
            amountOfSeaweed = (int) Arrays.stream(items).filter(item -> item.getItemId() == ItemID.GIANT_SEAWEED).count();
            int amountBones = (int) Arrays.stream(items).filter(item -> isSinisterBone(item.getItemId())).count();
            int amountAshes = (int) Arrays.stream(items).filter(item -> isDemonicAsh(item.getItemId())).count();
            demonic = amountAshes >= config.demonic();
            sinister = amountBones >= config.sinister();
        }
    }

    @Subscribe
    public void onBeforeRender(BeforeRender event) {
        if (client == null) {
            return;
        }
        if (config.seaweedToggle()) {
            toggle(client.getWidget(SUPERGLASS_MAKE), checkSeaweedAndSand());
        }
        if (config.demonicToggle()) {
            toggle(client.getWidget(DEMONIC_OFFERING), demonic);
        }
        if (config.sinisterToggle()) {
            toggle(client.getWidget(SINISTER_OFFERING), sinister);
        }
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event) {
        int inventoryContainerId = 93;
        if (event.getContainerId() == inventoryContainerId) {
            Item[] items = event.getItemContainer().getItems();
            updateBonesAndAshes(items);
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if (client == null) {
            return;
        }
        Widget inventory = client.getWidget(ComponentID.INVENTORY_CONTAINER);
        if (inventory == null) {
            return;
        }
        Widget[] items = inventory.getChildren();
        int amountBones = (int) Arrays.stream(items).filter(item -> isSinisterBone(item.getItemId())).count();
        int amountAshes = (int) Arrays.stream(items).filter(item -> isDemonicAsh(item.getItemId())).count();
        demonic = amountAshes >= config.demonic();
        sinister = amountBones >= config.sinister();
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
        if (config.sulphurTeleportToggle()) {
            if (client == null) {
            } else {
                Widget inventory = client.getWidget(ComponentID.INVENTORY_CONTAINER);
                if (inventory == null) {
                    return;
                }
                Widget[] items = inventory.getChildren();
                if (items == null) {
                    return;
                }
                Widget[] inventoryFiltered = Arrays.stream(items).filter(item -> item.getItemId() == ItemID.SULPHUROUS_ESSENCE).toArray(Widget[]::new);
                int amountSulphurAsh = inventoryFiltered[0].getItemQuantity();
                if (amountSulphurAsh >= config.sulphurAmountToggle()) {
                    //I haven't figured out a good way to detect if someone is trying to teleport, so this will have to do
                    String menu = event.getMenuOption();
                    boolean specialCases = itemIsTeleportItem(event.getItemId()) && !menu.equals("Wear") && !menu.equals("Equip");
                    specialCases = specialCases && !menu.equals("Drop") && !menu.equals("Remove") && !menu.equals("Examine") && !menu.equals("Check");
                    specialCases = specialCases && !menu.equals("Use") && !menu.equals("Take") && !menu.equals("Trim");
                    if (event.getItemId() == -1) {
                        specialCases = specialCases || isWearableParam1(event.getParam1()) && !menu.equals("Remove") && !menu.equals("Examine") && !menu.equals("Check");
                    }
                    if (specialCases || event.getMenuOption().contains("Teleport") || event.getMenuTarget().contains("Teleport")) {
                        event.consume();
                        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Teleporting disabled since you have " + amountSulphurAsh + " sulphurous essence in your inventory!", null);
                    }
                }
            }
        }
        switch (event.getParam1()) {
            case WITHDRAW:
                if (config.disableWithdraw()) {
                    if (amountOfSand == config.sand() && event.getItemId() == ItemID.BUCKET_SAND) {
                        event.consume();
                        return;
                    } else if (amountOfSeaweed == config.seaweed() && event.getItemId() == ItemID.GIANT_SEAWEED) {
                        event.consume();
                        return;
                    }
                }
                updateInventory(event.getItemId());
                break;
            case DEPOSIT:
                if (event.getItemId() == ItemID.GIANT_SEAWEED) {
                    amountOfSeaweed -= 1;
                } else if (event.getItemId() == ItemID.BUCKET_SAND) {
                    amountOfSand = 0;
                }
                break;
            case DEPOSIT_ALL:
            case SUPERGLASS_MAKE:
                amountOfSand = 0;
                amountOfSeaweed = 0;
                break;
            default:
        }
    }

    private void updateInventory(int itemID) {
        Widget bank = client.getWidget(ComponentID.BANK_CONTAINER);
        if (bank == null || bank.isHidden()) {
            return;
        }
        if (itemID == ItemID.GIANT_SEAWEED) {
            amountOfSeaweed += 1;
        } else if (itemID == ItemID.BUCKET_SAND) {
            amountOfSand += config.sand();
        }
    }

    private boolean checkSeaweedAndSand() {
        return amountOfSeaweed == config.seaweed() && amountOfSand == config.sand();
    }

    private boolean isSinisterBone(int id) {
        switch (id) {
            case ItemID.BONES:
            case ItemID.MM_NORMAL_MONKEY_BONES:
            case ItemID.BAT_BONES:
            case ItemID.DRAGON_BONES_SUPERIOR:
            case ItemID.ZOGRE_ANCESTRAL_BONES_OURG:
            case ItemID.DAGANNOTH_KING_BONES:
            case ItemID.HYDRA_BONES:
            case ItemID.ZOGRE_ANCESTRAL_BONES_RAURG:
            case ItemID.LAVA_DRAGON_BONES:
            case ItemID.ZOGRE_ANCESTRAL_BONES_FAYG:
            case ItemID.DRAKE_BONES:
            case ItemID.WYVERN_BONES:
            case ItemID.DRAGON_BONES:
            case ItemID.WYRM_BONES:
            case ItemID.BABYDRAGON_BONES:
            case ItemID.TBWT_BEAST_BONES:
            case ItemID.ZOGRE_BONES:
            case ItemID.BABYWYRM_BONES:
            case ItemID.TBWT_JOGRE_BONES:
            case ItemID.BIG_BONES:
                return true;
        }
        return false;
    }

    private boolean isDemonicAsh(int id) {
        switch (id) {
            case ItemID.FIENDISH_ASHES:
            case ItemID.VILE_ASHES:
            case ItemID.MALICIOUS_ASHES:
            case ItemID.ABYSSAL_ASHES:
            case ItemID.INFERNAL_ASHES:
                return true;
        }
        return false;
    }

    private void updateBonesAndAshes(Item[] items) {
        int amountOfBones = (int) Arrays.stream(items).filter(item -> isSinisterBone(item.getId())).count();
        int amountOfAshes = (int) Arrays.stream(items).filter(item -> isDemonicAsh(item.getId())).count();
        sinister = amountOfBones >= config.sinister();
        demonic = amountOfAshes >= config.demonic();
    }

    private void toggle(Widget spell, boolean check) {
        if (spell == null) {
            return;
        }
        if (check) {
            spell.setOpacity(0);
            spell.setAction(0, "Cast");
        }
        else {
            spell.setOpacity(128);
            spell.setAction(0, "");
        }
    }

    @Provides
    PreventSGMConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PreventSGMConfig.class);
    }
}
