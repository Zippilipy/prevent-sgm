package com.preventsgm;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.events.*;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.api.gameval.ObjectID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.Arrays;

@Slf4j
@PluginDescriptor(
        name = "Prevent Misclicks",
        description = "Prevents certain missclicks in spellbooks, such as superglass make if you don't "
                + "have exactly 18 buckets of sand and 3 giant seaweed.")
public class PreventSGMPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private PreventSGMConfig config;

    // These numbers were found with help from coopermor. Thanks!
    private static final int WITHDRAW = InterfaceID.Bankmain.ITEMS;
    private static final int DEPOSIT_ALL = InterfaceID.Bankmain.DEPOSITINV;
    private static final int DEPOSIT = InterfaceID.Bankside.ITEMS;
    private static final int SUPERGLASS_MAKE = InterfaceID.MagicSpellbook.SUPERGLASS;
    private static final int DEMONIC_OFFERING = InterfaceID.MagicSpellbook.DEMONIC_OFFERING;
    private static final int SINISTER_OFFERING = InterfaceID.MagicSpellbook.SINISTER_OFFERING;
    private static final int VARBIT_FOUNTAIN_OF_RUNE = VarbitID.FOUNTAIN_OF_RUNE_ACTIVE;

    private int amountOfSeaweed = 0;
    private int amountOfSand = 0;

    private boolean sinister = false;
    private boolean demonic = false;

    //Some people will download this plugin without understanding what it does / forget I'm sure, so this is for them!
    private boolean haveGivenWarningAboutWithdrawal = false;
    private int maxWarnings = 5;
    private int amountOfWarningsAboutWrongOptions = 0;

    private void countItemsInInventory() {
        Widget inventory = client.getWidget(InterfaceID.Inventory.ITEMS);
        if (inventory == null) {
            return;
        }
        Widget[] items = inventory.getChildren();
        if (items == null) {
            return;
        }
        amountOfSand = (int) Arrays.stream(items).filter(item -> item.getItemId() == ItemID.BUCKET_SAND).count();
        amountOfSeaweed = (int) Arrays.stream(items).filter(item -> item.getItemId() == ItemID.GIANT_SEAWEED).count();
        int amountBones = (int) Arrays.stream(items).filter(item -> isSinisterBone(item.getItemId())).count();
        int amountAshes = (int) Arrays.stream(items).filter(item -> isDemonicAsh(item.getItemId())).count();
        demonic = amountAshes >= config.demonic();
        sinister = amountBones >= config.sinister();
    }

    @Override
    protected void startUp() throws Exception {
        if (client == null || client.getWidget(InterfaceID.Inventory.ITEMS) == null) {
            return;
        }
        countItemsInInventory();
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

            countItemsInInventory();
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
        if (client.getVarbitValue(VARBIT_FOUNTAIN_OF_RUNE) == 0) {
            if (config.demonicToggle()) {
                toggle(client.getWidget(DEMONIC_OFFERING), demonic);
            }
            if (config.sinisterToggle()) {
                toggle(client.getWidget(SINISTER_OFFERING), sinister);
            }
        }
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event) {
        if (event.getContainerId() == InventoryID.INV) {
            Item[] items = event.getItemContainer().getItems();
            updateBonesAndAshes(items);
        }
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
                return;
            }
            Widget inventory = client.getWidget(InterfaceID.Inventory.ITEMS);
            if (inventory == null) {
                return;
            }
            Widget[] items = inventory.getChildren();
            if (items == null) {
                return;
            }
            Widget[] inventoryFiltered = Arrays.stream(items).filter(item -> item.getItemId() == ItemID.SULPHUROUS_ESSENCE).toArray(Widget[]::new);
            int amountSulphurAsh = 0;
            if (inventoryFiltered.length == 1) {
                amountSulphurAsh = inventoryFiltered[0].getItemQuantity();
            }
            if (amountSulphurAsh >= config.sulphurAmountToggle()) {
                String menu = event.getMenuOption();
                if (menu.equals("Pass-through") && event.getId() == ObjectID.PMOON_TELEBOX_3X3) {
                    event.consume();
                    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Leaving the cave disabled" +
                            " since you have " + amountSulphurAsh + " sulphurous essence in your inventory!", null);
                }
                boolean isItem = event.getParam1() == InterfaceID.Inventory.ITEMS; //if the item is worn, this is FALSE.
                boolean isWornItem = event.getParam1() >= InterfaceID.Wornitems.UNIVERSE && event.getParam1() <= InterfaceID.Wornitems.EXTRA_AMMO_SLOT; //check all slots
                boolean shouldConsume = false;
                if (isItem) {
                    shouldConsume = !(menu.equals("Wield") || menu.equals("Wear") || menu.equals("Check") || menu.equals("Drop") ||
                                        menu.equals("Equip") || menu.equals("Trim") || menu.equals("Untrim") ||
                                    menu.equals("Use") || menu.equals("Examine") || menu.equals("Cancel") || menu.equals("Drink") ||
                            menu.equals("Eat") || menu.equals("Cast") || menu.equals("Energy") || menu.equals("Lookup")); //cast is for alchemy spells, hopefully this doesn't break anything
                } else if (isWornItem) {
                    shouldConsume = !(menu.equals("Remove") || menu.equals("Examine") || menu.equals("Cancel") || menu.equals("Check") ||
                                        menu.equals("Trim") || menu.equals("Untrim"));
                } else {
                    shouldConsume = menu.toLowerCase().contains("teleport") || event.getMenuTarget().toLowerCase().contains("teleport");
                }
                if (shouldConsume) {
                    event.consume();
                    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Teleporting disabled" +
                            " since you have " + amountSulphurAsh + " sulphurous essence in your inventory!", null);
                }
            }
            }
        if (client.getVarbitValue(VARBIT_FOUNTAIN_OF_RUNE) == 0) {
            switch (event.getParam1()) {
                case SINISTER_OFFERING:
                    if (config.sinisterToggle()) {
                        if (!sinister) {
                            event.consume();
                            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Offering spell disabled", null);
                        }
                    }
                    break;
                case DEMONIC_OFFERING:
                    if (config.demonicToggle()) {
                        if (!demonic) {
                            event.consume();
                            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Offering spell disabled", null);
                        }
                    }
                    break;
            }
        }
        if (config.seaweedToggle()) {
            switch (event.getParam1()) {
                case WITHDRAW:
                    if (config.disableWithdraw()) {
                        if (amountOfSand == config.sand() && event.getItemId() == ItemID.BUCKET_SAND) {
                            event.consume();
                            if (!haveGivenWarningAboutWithdrawal) {
                                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Withdrawal of sand disabled.", null);
                                haveGivenWarningAboutWithdrawal = true;
                            }
                            return;
                        } else if (amountOfSeaweed == config.seaweed() && event.getItemId() == ItemID.GIANT_SEAWEED) {
                            event.consume();
                            if (!haveGivenWarningAboutWithdrawal) {
                                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Withdrawal of seaweed disabled.", null);
                                haveGivenWarningAboutWithdrawal = true;
                            }
                            return;
                        }
                    }
                    int itemID = event.getItemId();
                    Widget bank = client.getWidget(InterfaceID.Bankmain.UNIVERSE);
                    if (bank == null || bank.isHidden()) {
                        return;
                    }
                    if (itemID == ItemID.GIANT_SEAWEED) {
                        if (!event.getMenuOption().equals("Withdraw-1")) {
                            if (amountOfWarningsAboutWrongOptions < maxWarnings) {
                                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Your withdraw option must be withdraw-1 for this plugin to work!" +
                                        " Please change with Menu Entry Swapper", null);
                                amountOfWarningsAboutWrongOptions+=1;
                            }
                        }
                        amountOfSeaweed += 1;
                    } else if (itemID == ItemID.BUCKET_SAND) {
                        if (!event.getMenuOption().equals("Withdraw-" + config.sand())) {
                            if (amountOfWarningsAboutWrongOptions < maxWarnings) {
                                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Your withdraw option must be withdraw-" + config.sand() + " for this plugin to work!" +
                                        " Please change with Menu Entry Swapper", null);
                                amountOfWarningsAboutWrongOptions+=1;
                            }
                        }
                        amountOfSand += config.sand();
                    }
                    break;
                case DEPOSIT:
                    if (event.getItemId() == ItemID.GIANT_SEAWEED) {
                        amountOfSeaweed -= 1;
                    } else if (event.getItemId() == ItemID.BUCKET_SAND) {
                        amountOfSand = 0;
                    } else if( event.getItemId() == ItemID.MOLTEN_GLASS) { //if someone tries to withdraw seaweed or sand
                        //with full inventory of glass, it's gonna count of these variables which breaks the plugin
                        //this fix is not foolproof but it's gonna work for that case.
                        amountOfSeaweed = 0;
                        amountOfSand = 0;
                    }
                    break;
                case DEPOSIT_ALL:
                    amountOfSand = 0;
                    amountOfSeaweed = 0;
                    break;
                case SUPERGLASS_MAKE:
                    if (checkSeaweedAndSand()) {
                        amountOfSand = 0;
                        amountOfSeaweed = 0;
                    } else {
                        event.consume();
                        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Prevent Misclicks] Superglass make spell disabled.", null);
                    }
                    break;
                default:
            }
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
            case ItemID.BIG_BONES:
            case ItemID.TBWT_JOGRE_BONES:
            case ItemID.BABYWYRM_BONES:
            case ItemID.ZOGRE_BONES:
            case ItemID.TBWT_BEAST_BONES:
            case ItemID.BABYDRAGON_BONES:
            case ItemID.WYRM_BONES:
            case ItemID.DRAGON_BONES:
            case ItemID.WYVERN_BONES:
            case ItemID.DRAKE_BONES:
            case ItemID.ZOGRE_ANCESTRAL_BONES_FAYG:
            case ItemID.LAVA_DRAGON_BONES:
            case ItemID.ZOGRE_ANCESTRAL_BONES_RAURG:
            case ItemID.FROST_DRAGON_BONES:
            case ItemID.HYDRA_BONES:
            case ItemID.DAGANNOTH_KING_BONES:
            case ItemID.ZOGRE_ANCESTRAL_BONES_OURG:
            case ItemID.DRAGON_BONES_SUPERIOR:
            case ItemID.STRYKEWYRM_BONES:
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
        }
        else {
            spell.setOpacity(128);
        }
    }

    @Provides
    PreventSGMConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PreventSGMConfig.class);
    }
}
