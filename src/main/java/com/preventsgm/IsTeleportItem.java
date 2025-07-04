package com.preventsgm;

import net.runelite.api.gameval.ItemID;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IsTeleportItem {

    public static boolean isWearableParam1 (int param1) {
        switch (param1) {
            case 25362447: //Helmet
            case 25362448: //Cape
            case 25362457: //Pocket slot
            case 25362456: //Ring
            case 25362449: //Necklace
            case 25362455: //Boots
            case 25362452: //Off hand
            case 25362450: //Main hand
            case 25362454: //Gloves
            case 25362451: //Chest plate
            case 25362453: //legs
                return true;
        }
        return false;
    }

    private static final Set<Integer> TELEPORT_ITEM_IDS = new HashSet<>(Arrays.asList(
            //Automatically found
            ItemID.ALUFT_SEED_POD,
            ItemID.AMULET_OF_GLORY_1,
            ItemID.AMULET_OF_GLORY_2,
            ItemID.AMULET_OF_GLORY_3,
            ItemID.AMULET_OF_GLORY_4,
            ItemID.AMULET_OF_GLORY_5,
            ItemID.AMULET_OF_GLORY_6,
            ItemID.AMULET_OF_GLORY_INF,
            ItemID.ARDY_CAPE_EASY,
            ItemID.ARDY_CAPE_ELITE,
            ItemID.ARDY_CAPE_HARD,
            ItemID.ARDY_CAPE_MEDIUM,
            ItemID.ATJUN_GLOVES_ELITE,
            ItemID.ATJUN_GLOVES_HARD,
            ItemID.BOOKOFSCROLLS_CHARGED,
            ItemID.BOOK_OF_THE_DEAD,
            ItemID.BR_AMULET_OF_GLORY,
            ItemID.BURNING_AMULET_1,
            ItemID.BURNING_AMULET_2,
            ItemID.BURNING_AMULET_3,
            ItemID.BURNING_AMULET_4,
            ItemID.BURNING_AMULET_5,
            ItemID.CAMULET,
            ItemID.CA_OFFHAND_EASY,
            ItemID.CA_OFFHAND_ELITE,
            ItemID.CA_OFFHAND_GRANDMASTER,
            ItemID.CA_OFFHAND_HARD,
            ItemID.CA_OFFHAND_MASTER,
            ItemID.CA_OFFHAND_MEDIUM,
            ItemID.CHRONICLE,
            ItemID.DEADMAN_TOURNAMENT_TELETAB,
            ItemID.DESERT_AMULET_ELITE,
            ItemID.DESERT_AMULET_HARD,
            ItemID.DESERT_AMULET_MEDIUM,
            ItemID.DISCOFRETURNING,
            ItemID.DORGESH_TELEPORT_ARTIFACT,
            ItemID.DRAKANS_MEDALLION,
            ItemID.ECTOPHIAL_EMPTY,
            ItemID.ELF_CRYSTAL_TINY,
            ItemID.FOSSIL_TABLET_VOLCANOTELEPORT,
            ItemID.FREMENNIK_BOOTS_EASY,
            ItemID.FREMENNIK_BOOTS_ELITE,
            ItemID.FREMENNIK_BOOTS_HARD,
            ItemID.FREMENNIK_BOOTS_MEDIUM,
            ItemID.GAUNTLET_TELEPORT_CRYSTAL,
            ItemID.GIANTSOUL_AMULET_CHARGED,
            ItemID.HALLOWED_TELEPORT,
            ItemID.HG_QUETZALWHISTLE_BASIC,
            ItemID.HG_QUETZALWHISTLE_ENHANCED,
            ItemID.HG_QUETZALWHISTLE_PERFECTED,
            ItemID.INFERNAL_DEFENDER_GHOMMAL_5,
            ItemID.INFERNAL_DEFENDER_GHOMMAL_6,
            ItemID.JEWL_BRACELET_OF_COMBAT,
            ItemID.JEWL_BRACELET_OF_COMBAT_1,
            ItemID.JEWL_BRACELET_OF_COMBAT_2,
            ItemID.JEWL_BRACELET_OF_COMBAT_3,
            ItemID.JEWL_BRACELET_OF_COMBAT_4,
            ItemID.JEWL_BRACELET_OF_COMBAT_5,
            ItemID.JEWL_BRACELET_OF_COMBAT_6,
            ItemID.JEWL_NECKLACE_OF_SKILLS,
            ItemID.JEWL_NECKLACE_OF_SKILLS_1,
            ItemID.JEWL_NECKLACE_OF_SKILLS_2,
            ItemID.JEWL_NECKLACE_OF_SKILLS_3,
            ItemID.JEWL_NECKLACE_OF_SKILLS_4,
            ItemID.JEWL_NECKLACE_OF_SKILLS_5,
            ItemID.JEWL_NECKLACE_OF_SKILLS_6,
            ItemID.LOTG_TELEPORT_ARTIFACT,
            ItemID.LUMBRIDGE_RING_ELITE,
            ItemID.LUMBRIDGE_RING_HARD,
            ItemID.LUMBRIDGE_RING_MEDIUM,
            ItemID.LUNAR_TABLET_BARBARIAN_TELEPORT,
            ItemID.LUNAR_TABLET_CATHERBY_TELEPORT,
            ItemID.LUNAR_TABLET_FISHING_GUILD_TELEPORT,
            ItemID.LUNAR_TABLET_ICE_PLATEAU_TELEPORT,
            ItemID.LUNAR_TABLET_KHAZARD_TELEPORT,
            ItemID.LUNAR_TABLET_MOONCLAN_TELEPORT,
            ItemID.LUNAR_TABLET_OURANIA_TELEPORT,
            ItemID.LUNAR_TABLET_WATERBIRTH_TELEPORT,
            ItemID.MAGIC_STRUNG_LYRE,
            ItemID.MAGIC_STRUNG_LYRE_2,
            ItemID.MAGIC_STRUNG_LYRE_3,
            ItemID.MAGIC_STRUNG_LYRE_4,
            ItemID.MAGIC_STRUNG_LYRE_5,
            ItemID.MAGIC_STRUNG_LYRE_INFINITE,
            ItemID.MAGIC_WHISTLE,
            ItemID.MM2_ROYAL_SEED_POD,
            ItemID.MM_SIGIL,
            ItemID.MORYTANIA_LEGS_EASY,
            ItemID.MORYTANIA_LEGS_ELITE,
            ItemID.MORYTANIA_LEGS_HARD,
            ItemID.MORYTANIA_LEGS_MEDIUM,
            ItemID.MOURNING_TELEPORT_CRYSTAL_1,
            ItemID.MOURNING_TELEPORT_CRYSTAL_2,
            ItemID.MOURNING_TELEPORT_CRYSTAL_3,
            ItemID.MOURNING_TELEPORT_CRYSTAL_4,
            ItemID.MOURNING_TELEPORT_CRYSTAL_5,
            ItemID.MUSIC_CAPE,
            ItemID.MYTHICAL_CAPE,
            ItemID.NECKLACE_OF_DIGSITE_1,
            ItemID.NECKLACE_OF_DIGSITE_2,
            ItemID.NECKLACE_OF_DIGSITE_3,
            ItemID.NECKLACE_OF_DIGSITE_4,
            ItemID.NECKLACE_OF_DIGSITE_5,
            ItemID.NECKLACE_OF_MINIGAMES_1,
            ItemID.NECKLACE_OF_MINIGAMES_2,
            ItemID.NECKLACE_OF_MINIGAMES_3,
            ItemID.NECKLACE_OF_MINIGAMES_4,
            ItemID.NECKLACE_OF_MINIGAMES_5,
            ItemID.NECKLACE_OF_MINIGAMES_6,
            ItemID.NECKLACE_OF_MINIGAMES_7,
            ItemID.NECKLACE_OF_MINIGAMES_8,
            ItemID.NECKLACE_OF_PASSAGE_1,
            ItemID.NECKLACE_OF_PASSAGE_2,
            ItemID.NECKLACE_OF_PASSAGE_3,
            ItemID.NECKLACE_OF_PASSAGE_4,
            ItemID.NECKLACE_OF_PASSAGE_5,
            ItemID.NZONE_TELETAB_ALDARIN,
            ItemID.NZONE_TELETAB_BRIMHAVEN,
            ItemID.NZONE_TELETAB_KOUREND,
            ItemID.NZONE_TELETAB_POLLNIVNEACH,
            ItemID.NZONE_TELETAB_RELLEKKA,
            ItemID.NZONE_TELETAB_RIMMINGTON,
            ItemID.NZONE_TELETAB_TAVERLEY,
            ItemID.NZONE_TELETAB_TROLLHEIM,
            ItemID.NZONE_TELETAB_YANILLE,
            ItemID.PENDANT_OF_ATES,
            ItemID.PHARAOHS_SCEPTRE_CHARGED_INITIAL,
            ItemID.POH_TABLET_ARDOUGNETELEPORT,
            ItemID.POH_TABLET_CAMELOTTELEPORT,
            ItemID.POH_TABLET_FALADORTELEPORT,
            ItemID.POH_TABLET_FORTISTELEPORT,
            ItemID.POH_TABLET_KOURENDTELEPORT,
            ItemID.POH_TABLET_LUMBRIDGETELEPORT,
            ItemID.POH_TABLET_TELEPORTTOHOUSE,
            ItemID.POH_TABLET_VARROCKTELEPORT,
            ItemID.POH_TABLET_WATCHTOWERTELEPORT,
            ItemID.PRIF_TELEPORT_CRYSTAL,
            ItemID.RATCATCHERS_PARTY_DIRECTIONS,
            ItemID.RING_OF_DUELING_1,
            ItemID.RING_OF_DUELING_2,
            ItemID.RING_OF_DUELING_3,
            ItemID.RING_OF_DUELING_4,
            ItemID.RING_OF_DUELING_5,
            ItemID.RING_OF_DUELING_6,
            ItemID.RING_OF_DUELING_7,
            ItemID.RING_OF_DUELING_8,
            ItemID.RING_OF_ELEMENTS_CHARGED,
            ItemID.RING_OF_LIFE,
            ItemID.RING_OF_RETURNING_1,
            ItemID.RING_OF_RETURNING_2,
            ItemID.RING_OF_RETURNING_3,
            ItemID.RING_OF_RETURNING_4,
            ItemID.RING_OF_RETURNING_5,
            ItemID.RING_OF_WEALTH,
            ItemID.RING_OF_WEALTH_1,
            ItemID.RING_OF_WEALTH_2,
            ItemID.RING_OF_WEALTH_3,
            ItemID.RING_OF_WEALTH_4,
            ItemID.RING_OF_WEALTH_5,
            ItemID.RING_OF_WEALTH_I,
            ItemID.SEERS_HEADBAND_ELITE,
            ItemID.SEERS_HEADBAND_HARD,
            ItemID.SKILLCAPE_AD,
            ItemID.SKILLCAPE_CONSTRUCTION,
            ItemID.SKILLCAPE_CRAFTING,
            ItemID.SKILLCAPE_DEFENCE,
            ItemID.SKILLCAPE_FARMING,
            ItemID.SKILLCAPE_FISHING,
            ItemID.SKILLCAPE_HUNTING,
            ItemID.SKILLCAPE_MAX_ARDY,
            ItemID.SKILLCAPE_MAX_MYTHICAL,
            ItemID.SKILLCAPE_MAX_WORN,
            ItemID.SKILLCAPE_QP,
            ItemID.SKILLCAPE_STRENGTH,
            ItemID.SLAYER_RING_1,
            ItemID.SLAYER_RING_2,
            ItemID.SLAYER_RING_3,
            ItemID.SLAYER_RING_4,
            ItemID.SLAYER_RING_5,
            ItemID.SLAYER_RING_6,
            ItemID.SLAYER_RING_7,
            ItemID.SLAYER_RING_8,
            ItemID.SLAYER_RING_ETERNAL,
            ItemID.SLICE_TELEPORT_ARTIFACT,
            ItemID.SOS_SKULL_SCEPTRE,
            ItemID.SOS_SKULL_SCEPTRE_IMBUED,
            ItemID.STRONGHOLD_TELEPORT_BASALT,
            ItemID.TABLET_ANNAKARL,
            ItemID.TABLET_CARRALLANGAR,
            ItemID.TABLET_DAREEYAK,
            ItemID.TABLET_GHORROCK,
            ItemID.TABLET_KHARYLL,
            ItemID.TABLET_LASSAR,
            ItemID.TABLET_PADDEWA,
            ItemID.TABLET_SENNTISTEN,
            ItemID.TABLET_TARGET,
            ItemID.TABLET_WILDYCRABS,
            ItemID.TELEPORTSCROLL_CERBERUS,
            ItemID.TELEPORTSCROLL_CHASMOFFIRE,
            ItemID.TELEPORTSCROLL_COLOSSAL_WYRM,
            ItemID.TELEPORTSCROLL_DIGSITE,
            ItemID.TELEPORTSCROLL_ELF,
            ItemID.TELEPORTSCROLL_FELDIP,
            ItemID.TELEPORTSCROLL_GUTHIXIAN_TEMPLE,
            ItemID.TELEPORTSCROLL_LUMBERYARD,
            ItemID.TELEPORTSCROLL_LUNARISLE,
            ItemID.TELEPORTSCROLL_MORTTON,
            ItemID.TELEPORTSCROLL_MOSLES,
            ItemID.TELEPORTSCROLL_NARDAH,
            ItemID.TELEPORTSCROLL_PESTCONTROL,
            ItemID.TELEPORTSCROLL_PISCATORIS,
            ItemID.TELEPORTSCROLL_REVENANTS,
            ItemID.TELEPORTSCROLL_SPIDERCAVE,
            ItemID.TELEPORTSCROLL_TAIBWO,
            ItemID.TELEPORTSCROLL_WATSON,
            ItemID.TELEPORTSCROLL_ZULANDRA,
            ItemID.TELETAB_APE,
            ItemID.TELETAB_BARROWS,
            ItemID.TELETAB_BATTLEFRONT,
            ItemID.TELETAB_CEMETERY,
            ItemID.TELETAB_DRAYNOR,
            ItemID.TELETAB_FENK,
            ItemID.TELETAB_HARMONY,
            ItemID.TELETAB_LUMBRIDGE,
            ItemID.TELETAB_MIND_ALTAR,
            ItemID.TELETAB_SALVE,
            ItemID.TELETAB_WESTARDY,
            ItemID.TRAIL_AMULET_OF_GLORY,
            ItemID.VARLAMORE_MINING_TELEPORT,
            ItemID.VEOS_KHAREDSTS_MEMOIRS,
            ItemID.VIKING_ENCHANTED_STRUNG_LYRE,
            ItemID.WEISS_TELEPORT_BASALT,
            ItemID.WESTERN_BANNER_ELITE,
            ItemID.WESTERN_BANNER_HARD,
            ItemID.WILDERNESS_SWORD_ELITE,
            ItemID.WILDERNESS_SWORD_HARD,
            ItemID.XERIC_TALISMAN,
            ItemID.XMAS17_TELETAB,
            ItemID.XMAS19_TABLET_SCAPE_RUNE,
            ItemID.ZEAH_BLESSING_EASY,
            ItemID.ZEAH_BLESSING_ELITE,
            ItemID.ZEAH_BLESSING_HARD,
            ItemID.ZEAH_BLESSING_MEDIUM,
            //Partial matches
            ItemID.SKILLCAPE_AD_TRIMMED,
            ItemID.TRAIL_AMULET_OF_GLORY_1,
            ItemID.TRAIL_AMULET_OF_GLORY_2,
            ItemID.TRAIL_AMULET_OF_GLORY_3,
            ItemID.TRAIL_AMULET_OF_GLORY_4,
            ItemID.TRAIL_AMULET_OF_GLORY_5,
            ItemID.TRAIL_AMULET_OF_GLORY_6,
            ItemID.SKILLCAPE_CONSTRUCTION_TRIMMED,
            ItemID.SKILLCAPE_CRAFTING_TRIMMED,
            ItemID.PRIF_TELEPORT_SEED,
            ItemID.SKILLCAPE_DEFENCE_TRIMMED,
            ItemID.POH_AMULET_DIGSITE,
            ItemID.SKILLCAPE_FARMING_TRIMMED,
            ItemID.SKILLCAPE_FISHING_TRIMMED,
            ItemID.INFERNAL_DEFENDER_GHOMMAL_5_TROUVER,
            ItemID.INFERNAL_DEFENDER_GHOMMAL_6_TROUVER,
            ItemID.SKILLCAPE_HUNTING_TRIMMED,
            ItemID.BR_TABLET_ANCIENTS,
            ItemID.SKILLCAPE_MAX_ANMA,
            ItemID.SKILLCAPE_MAX_ASSEMBLER,
            ItemID.SKILLCAPE_MAX_ASSEMBLER_BROKEN,
            ItemID.SKILLCAPE_MAX_ASSEMBLER_MASORI,
            ItemID.SKILLCAPE_MAX_ASSEMBLER_MASORI_BROKEN,
            ItemID.SKILLCAPE_MAX_ASSEMBLER_MASORI_TROUVER,
            ItemID.SKILLCAPE_MAX_ASSEMBLER_TROUVER,
            ItemID.SKILLCAPE_MAX_DIZANAS,
            ItemID.SKILLCAPE_MAX_DIZANAS_BROKEN,
            ItemID.SKILLCAPE_MAX_DIZANAS_TROUVER,
            ItemID.SKILLCAPE_MAX_FIRECAPE_BROKEN,
            ItemID.SKILLCAPE_MAX_FIRECAPE_DUMMY,
            ItemID.SKILLCAPE_MAX_FIRECAPE_TROUVER,
            ItemID.SKILLCAPE_MAX_GUTHIX,
            ItemID.SKILLCAPE_MAX_GUTHIX2,
            ItemID.SKILLCAPE_MAX_GUTHIX2_BROKEN,
            ItemID.SKILLCAPE_MAX_GUTHIX2_TROUVER,
            ItemID.SKILLCAPE_MAX_INFERNALCAPE,
            ItemID.SKILLCAPE_MAX_INFERNALCAPE_BROKEN,
            ItemID.SKILLCAPE_MAX_INFERNALCAPE_TROUVER,
            ItemID.SKILLCAPE_MAX_SARADOMIN,
            ItemID.SKILLCAPE_MAX_SARADOMIN2,
            ItemID.SKILLCAPE_MAX_SARADOMIN2_BROKEN,
            ItemID.SKILLCAPE_MAX_SARADOMIN2_TROUVER,
            ItemID.SKILLCAPE_MAX_ZAMORAK,
            ItemID.SKILLCAPE_MAX_ZAMORAK2,
            ItemID.SKILLCAPE_MAX_ZAMORAK2_BROKEN,
            ItemID.SKILLCAPE_MAX_ZAMORAK2_TROUVER,
            ItemID.MUSIC_CAPE_TRIMMED,
            ItemID.PHARAOHS_SCEPTRE,
            ItemID.BH_IMBUE_RINGOFWEALTH,
            ItemID.RING_OF_WEALTH_I1,
            ItemID.RING_OF_WEALTH_I2,
            ItemID.RING_OF_WEALTH_I3,
            ItemID.RING_OF_WEALTH_I4,
            ItemID.RING_OF_WEALTH_I5,
            ItemID.SKILLCAPE_STRENGTH_TRIMMED,
            ItemID.BOUNTY_TELEPORT_SCROLL,
            ItemID.GAUNTLET_TELEPORT_CRYSTAL_HM,
            //Added manually
            ItemID.BREW_RED_RUM,
            ItemID.BREW_BLUE_RUM,
            ItemID.PHARAOHS_SCEPTRE_CHARGED,
            ItemID.WILDERNESS_SWORD_EASY,
            ItemID.WILDERNESS_SWORD_MEDIUM,
            ItemID.WESTERN_BANNER_EASY,
            ItemID.WESTERN_BANNER_MEDIUM,
            ItemID.ATJUN_GLOVES_EASY,
            ItemID.ATJUN_GLOVES_MED,
            ItemID.SEERS_HEADBAND_EASY,
            ItemID.SEERS_HEADBAND_MEDIUM,
            ItemID.LUMBRIDGE_RING_EASY,
            ItemID.ECTOPHIAL,
            ItemID.DESERT_AMULET_EASY,
            ItemID.VARLAMORE_MINING_TELEPORT_1,
            ItemID.VARLAMORE_MINING_TELEPORT_2,
            ItemID.VARLAMORE_MINING_TELEPORT_3,
            ItemID.VARLAMORE_MINING_TELEPORT_4,
            ItemID.VARLAMORE_MINING_TELEPORT_5,
            ItemID.VARLAMORE_MINING_TELEPORT_25,
            ItemID.SKILLCAPE_MAX,
            ItemID.SKILLCAPE_AD_HOOD
    ));

    /**
     *
     * Sigh... I have no clue how to otherwise determine the player is trying to teleport if it doesn't say in the item's name or menu action "Teleport"
     * @param itemID the items id
     * @return true if it's an item you use to teleport, otherwise false
     */
    public static boolean itemIsTeleportItem (int itemID) {
        return TELEPORT_ITEM_IDS.contains(itemID);
    }
}
