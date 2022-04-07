package ethos.model.npcs.pets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.ImmutableSet;

import ethos.Server;
import ethos.clip.Region;
import ethos.model.items.GameItem;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.PlayerSave;
import ethos.util.Misc;

public class PetHandler {

    /**
     * A {@link Set} of {@link Pets} that represent non-playable characters that a
     * player entity can drop and interact with.
     */
    private static final Set<Pets> PETS = Collections.unmodifiableSet(EnumSet.allOf(Pets.class));

    private static final ImmutableSet<Integer> PET_IDS = ImmutableSet.of(12650, 12649, 12651, 12652, 12644, 12645,
            12643, 11995, 15568, 12653, 12655, 13178, 12646, 13179, 13177, 12921, 13181, 12816, 12647, 22752);

    public static boolean ownsAll(Player player) {
        int amount = 0;
        for (int pets2 : PET_IDS) {
            if (player.getItems().getItemCount(pets2, false) > 0 || player.summonId == pets2) {
                amount++;
            }
            if (amount == PET_IDS.size()) {
                return true;
            }
        }
        return false;
    }

    public static enum Pets {
        GRAARDOR(12650, 6632, "General Graardor", 500, "second"),
        HYDRA(22752, 8493, "Ikkle Hydra", 500, "third"), 
        HYDRA2(22752, 8492, "Ikkle Hydra", 500, "third"), 
        HYDRA3(22752, 8494, "Ikkle Hydra", 500, "third"), 
        HYDRA4(22752, 850, "Ikkle Hydra", 500, "second"), 
		WYRM(23040, 5458, "Wyrm", 1000, "first"),
		DRAKE(23041, 5459, "Drake", 1000, "first"),
		WYRM1(23040, 5460, "Wyrm", 1000, "first"),
        KREE(12649, 6643, "Kree Arra", 500, "second"), 
        ZILLY(12651, 6633, "Commander Zilyana", 500, "second"), 
        TSUT(12652, 6634, "Kril Tsutsaroth", 500, "second"), 
        PRIME(12644, 6627, "Dagannoth Prime",500, "second"),
        REX(12645, 6630, "Dagannoth Rex", 500, "second"), 
        SUPREME(12643, 6628,"Dagannoth Supreme", 500,"second"), 
        CHAOS(11995, 5907, "Chaos Elemental", 500, "first"), 
        CHAOS_FANATIC(11995,5907, "Chaos Fanatic", 500,"first"), 
        KBD(12653, 6636, "King Black Dragon", 500, "second"), 
        KRAKEN(12655, 6640, "Kraken", 500,"second"), 
        CALLISTO(13178, 5558, "Callisto", 500, "second"), 
        MOLE(12646,6651, "Giant Mole", 500, "second"), 
        VETION(13179, 5559,"Vetion", 500, "third"), 
        VENENATIS(13177, 5557,"Venenatis", 500, "second"), 
        DEVIL(12648, 6639,"Thermonuclear Smoke Devil", 500,"second"), 
        DEVIL_SLAYER(22663, 8483,"", 500,"second"), 
        ZULRAH(12921, 2130, "Zulrah",600, "second"), 
        TZREK_JAD(13225,5892, "Tztok-Jad", 110,"second"), 
        HELLPUPPY(13247, 3099,"Cerberus", 600,"second"), 
        SKOTOS(21273,425,"Skotizo", 700,"second"), 
        ZULRAH2(12921,2131,"",-1,"second"), 
        ZULRAH3(12921,2132,"",-1,"second"), 
        HELL_CAT(7582,1625,"",-1,"first"), 
        VORKI(21992, 8029, "Vorkath", 500, "second"),
        DEATH_JR_RED(12840, 5568, "Zombie", 800, "first"),
        DEATH_JR_BLUE(12840, 5570, "", -1, "first"),
        DEATH_JR_GREEN(12840,5571, "", -1, "first"),
        DEATH_JR_BLACK(12840, 5569, "", -1, "first"),
        SANTA_JR(9958, 1047, "", -1, "first"),
        ANTI_SANTA_JR(9959, 1048, "Anti-Santa", 500, "first"),
        SCORPIA(13181, 5561, "Scorpia", 500, "second"),
        DARK_CORE(22318, 388, "Corporeal beast", 500, "second"),
        KALPHITE_PRINCESS(12647, 6637,"Kalphite Queen", 500, "third"),
        KALPHITE_PRINCESS_TWO(12647, 6638, "", -1, "third"),
        HERON(13320, 6715,"", -1, "second"),
        ROCK_GOLEM(13321, 7439, "", -1,"second"),
        ROCK_GOLEM_TIN(21187, 7440, "", -1, "second"),
        ROCK_GOLEM_COPPER(21188, 7441, "", -1,"second"),
        ROCK_GOLEM_IRON(21189, 7442, "", -1, "second"),
        ROCK_GOLEM_COAL(21192, 7445, "",  -1, "second"),
        ROCK_GOLEM_GOLD(21193, 7446, "",-1,"second"),
        ROCK_GOLEM_MITHRIL(21194, 7447, "", -1,"second"),
        ROCK_GOLEM_ADAMANT(21196, 7449, "", -1, "second"),
        ROCK_GOLEM_RUNE(21197, 7450, "", -1,  "second"),
        ROCK_GOLEM_AMETHYST(21340, 7711, "", -1, "second"),
        BEAVER(13322, 6717, "", -1, "second"),
        KITTEN(1555, 5591,  "", -1, "first"),
        KITTEN_ONE(1556, 5592, "", -1, "first"),
        KITTEN_TWO(1557, 5593, "", -1, "first"),
        KITTEN_THREE(1558, 5594,  "", -1, "first"),
        KITTEN_FOUR(1559, 5595,"", -1,"first"),
        TZKAL_ZUK(22319, 8011,"", -1,"first"),
        KITTEN_FIVE(1560, 5596, "", -1, "first"),
        RED_CHINCHOMPA(13323, 6718, "", -1, "second"),
        GRAY_CHINCHOMPA(13324, 6719, "", -1, "second"),
        BLACK_CHINCHOMPA(13325, 6720, "", -1, "second"),
        GOLD_CHINCHOMPA(13326, 6721, "", -1, "second"),
        GIANT_SQUIRREL(20659, 7351, "", -1, "second"),
        TANGLEROOT(20661, 7352, "", -1, "second"),
        ROCKY(20663, 7353, "", -1, "second"),
        RIFT_GUARDIAN_FIRE(20665, 7354, "", -1, "second"),
        RIFT_GUARDIAN_AIR(20667, 7355, "", -1, "second"),
        RIFT_GUARDIAN_MIND(20669, 7356, "", -1,"second"),
        RIFT_GUARDIAN_WATER(20671, 7357, "", -1, "second"),
        RIFT_GUARDIAN_EARTH(20673,7358, "", -1, "second"),
        RIFT_GUARDIAN_BODY(20675, 7359, "",  -1, "second"),
        RIFT_GUARDIAN_COSMIC(20677, 7360, "", -1,"second"),
        RIFT_GUARDIAN_CHAOS(20679, 7361, "", -1, "second"),
        RIFT_GUARDIAN_NATURE(20681, 7362,"", -1, "second"),
        RIFT_GUARDIAN_LAW(20683, 7363, "", -1, "second"),
        RIFT_GUARDIAN_DEATH(20685, 7364, "", -1, "second"),
        RIFT_GUARDIAN_SOUL(20687, 7365, "", -1, "second"),
        RIFT_GUARDIAN_ASTRAL(20689, 7366, "", -1, "second"),
        RIFT_GUARDIAN_BLOOD(20691, 7367, "", -1, "second"),
        RIFT_GUARDIAN_WRATH(21990, 8024, "", -1, "second"),
        ABYSSAL_ORPHAN(13262, 5883, "", -1, "second"),
        BLOODHOUND(19730, 6296, "", -1, "second"),
        PHOENIX(20693, 7368, "", -1, "second"),
        OLMLET(20851, 7519, "Great Olm", 255, "second"),
        PUPPADILE(22376, 8201, "", -1, "second"),
        TEKTINY(22378, 8202, "",  -1, "second"),
        VANGUARD(22380, 8203, "", -1, "second"),
        VASA_MINIRO(22382, 8204, "", -1, "second"),
        NIEVE(19558, 6035, "", -1, "first"),
        STEVE(3255, 6798, "", -1, "first"),
        LUNAR(10609, 786, "", -1, "second"),
      //  CRITTER(22318, 8008, "", -1, "second"),
        VESPINA(22384, 8200, "", -1, "second"),
        NOON(21748, 7891, "", -1, "second"),//7892 transformable
        MIDNIGHT(21750, 7893, "", -1, "second"),
		//INFEROJAD(21292, 4888, "", -1, "second"),
		POSTIE_PETIE(964, 3291, "", -1, "first"), 
		XARP(30915, 8005, "", -1, "first"), 
		SOT(30914, 8006, "", -1, "first"), 
		BLOAT(30912, 8007, "", -1, "first"), 
		NYLO(30913, 8008, "", -1, "first"), 
		MAID(30911, 8009, "", -1, "first"), 
		SANTA(20832, 8051, "", -1, "first"), 
		INFERNO(30951, 2988, "", -1, "first"), 
		INFERNO2(30950, 2989, "", -1, "first"), 


/**
 * Start of slayer pets
 */
        CRAWLING_HAND(4133, 449, "", -1, "first"),
        CAVE_BUG(4521, 482, "", -1, "first"),
        CAVE_CRAWLER(4134, 407, "", -1, "first"),
        BANSHEE(4135, 3984, "", -1, "first"),
        CAVE_SLIME(4520, 3985, "", -1, "first"),
         ROCKSLUG(4136, 422, "", -1, "first"),
         COCKATRICE(4137, 420, "", -1, "first"),
         PYREFIEND(4138, 434, "", -1, "first"),
         BASILISK(4139, 418, "", -1, "first"),
         INFERNAL_MAGE(4140, 443, "", -1, "first"),
         BLOODVELD(4141, 486, "", -1, "first"),
         JELLY(4142, 440, "", -1, "first"),
         TUROTH(4143, 426, "", -1, "first"),
         CAVE_HORROR(8900, 504, "", -1, "first"),
         ABERRANT_SPECTRE(4144, 3, "", -1, "first"),
         DUST_DEVIL(4145, 7249, "", -1, "first"),
         KURASK(4146, 410, "", -1, "first"),
         SKELETAL_WYVERN(6811, 466, "", -1, "first"),
         GARGOYLE(4147, 412, "", -1, "first"),
         NECHRYAEL(4148, 8, "", -1, "first"),
         ABYSSAL_DEMON(4149, 416, "", -1, "first"),
         DARK_BEAST(6637, 603, "", -1, "first"),

 		NIGHT_BEAST(30196, 5693, "Night Beast", 78, "first"),

 		GREATER_ABYSSAL_DEMON(30197, 5671, "Greater Abyssal Demon", 78, "first"),

 		CRUSHING_HAND(30198, 5672, "Crushing Hand", 78, "first"),

 		CHASM_CRAWLER(30199, 5673, "Chasm Crawler", 78, "first"),

 		SCREAMING_BANSHEE(30200, 5674, "Screaming Banshee", 78, "first"),

 		TWISTED_BANSHEE(30201, 5675, "Twisted Banshee", 78, "first"),

 		GIANT_ROCKSLUG(30202, 5676, "Giant Rockslug", 78, "first"),

 		COCKATHRICE(30203, 5677, "Cockathrice", 78, "first"),

 		FLAMING_PYRELORD(30204, 5678, "Flaming Pyrelord", 78, "first"),

 		MONSTROUS_BASILISK(30205, 5679, "Monstrous Basilisk", 78, "first"),

 		MALEVOLENT_MAGE(30206, 5680, "Night Beast", 78, "first"),

 		INSATIABLE_BLOODVELD(30207, 5681, "Insatiable Bloodveld", 78, "first"),

 		INSATIABLE_MUTATED_BLOODVELD(30208, 5682, "Insatiable Mutated Bloodveld", 78, "first"),

 		VITREOUS_JELLY(30209, 5683, "Vitreous Jelly", 78, "first"),

 		VITREOUS_WARPED_JELLY(30210, 5684, "Vitreous Warped Jelly", 78, "first"),

 		CAVE_ABOMINATION(30211, 5685, "Cave Abomination", 78, "first"),

 		ABHORRENT_SPECTRE(30212, 5686, "Abhorrent Spectre", 78, "first"),

 		REPUGNANT_SPECTRE(30213, 5687, "Repugnant Spectre", 78, "first"),

 		CHOKE_DEVIL(30214, 5688, "Choke Devil", 78, "first"),

 		KING_KURASK(30215, 5689, "King Kurask", 78, "first"),

 		NUCLEAR_SMOKE_DEVIL(30217, 5690, "Nuclear Smoke Devil", 78, "first"),

 		MARBLE_GARGOYLE(30218, 5691, "Marble Gargoyle", 78, "first"),

 		NECHRYARCH(30219, 5692, "Nechryarch", 78, "first"),
/**
 * End of slayer pets
 */
         AHRIM(1815, 2394, "", -1, "first"),
         KARIL(1816, 2392, "", -1, "first"),
         VERAC(1817, 2393, "", -1, "first"),
         TORAG(1818, 2391, "", -1, "first"),
         GUTHAN(1819, 5846, "", -1, "first"),
         DHAROK(1820, 5845, "", -1, "first"),   
         SPLATTER(1812, 1693, "", -1, "first"),
         SPINNER(1813, 1712, "", -1, "first"),
         SHIFTER(1814, 1703, "", -1, "first"),
         LIL_ZIK(22473, 8337, "", -1, "second");

        private final int itemId;

        private final int npcId;

        private final String parent;

        private final int droprate;

        private final String pickupOption;
        
        public int getItemId() {
            return itemId;
        }
        private Pets(int itemId, int npcId, String parent, int droprate, String pickupOption) {
            this.itemId = itemId;
            this.npcId = npcId;
            this.parent = parent;
            this.droprate = droprate;
            this.pickupOption = pickupOption;
        }
    }
    public static Pets getPetForParentId(Pets pet) {
        switch(pet.parent) {
            case "Runecrafting":
                return Pets.RIFT_GUARDIAN_AIR;
            case "Alchemical Hydra":
                return Pets.HYDRA;
            case "Vetion":
                return Pets.VETION;
            case "Zulrah":
                return Pets.ZULRAH;
            case "Zombie":
                return Pets.DEATH_JR_RED;
            case "Mining":
                return Pets.ROCK_GOLEM;
            case "Kalphite Queen":
                return Pets.KALPHITE_PRINCESS;
            default:
                return pet;
        }
    }
    public static ArrayList<GameItem> getPetIds(boolean parent) {
        ArrayList<GameItem> drops = new ArrayList<>();
        //Yeah this could be done better but I don't want to write a contains function override on the GameItem class
        ArrayList<Integer> itemIds = new ArrayList<>();
        for (Pets p : Pets.values()) {
            int itemId = parent ? getPetForParentId(p).itemId : p.getItemId();
            if (!itemIds.contains(itemId)) {
                itemIds.add(itemId);
                drops.add(new GameItem(itemId));
            }
        }

        return drops;
    }

    public static Pets forItem(int id) {
        for (Pets t : Pets.values()) {
            if (t.itemId == id) {
                return t;
            }
        }
        return null;
    }

    public static Pets forNpc(int id) {
        for (Pets t : Pets.values()) {
            if (t.npcId == id) {
                return t;
            }
        }
        return null;
    }

    public static boolean isPet(int npcId) {
        for (Pets t : Pets.values()) {
            if (t.npcId == npcId) {
                return true;
            }
        }
        return false;
    }

    public static String getOptionForNpcId(int npcId) {
        return forNpc(npcId).pickupOption;
    }

    public static int getItemIdForNpcId(int npcId) {
        if (forNpc(npcId) != null) {
            return forNpc(npcId).itemId;
        }
        return 0;
    }

    public static int getNPCIdForItemId(int itemId) {
        return forItem(itemId).npcId;
    }

    public static boolean spawnable(Player player, Pets pet, boolean ignore) {
        if (pet == null) {
            return false;
        }

        if (player.hasFollower && !ignore) {
            return false;
        }

        if (Boundary.isIn(player, Boundary.DUEL_ARENA)) {
            player.sendMessage("You cannot drop your pet here.");
            return false;
        }

        if (!player.getItems().playerHasItem(pet.itemId) && !ignore) {
            return false;
        }
        return true;
    }

    public static void spawn(Player player, Pets pet, boolean ignore, boolean ignoreAll) {
        if (!ignoreAll) {
            if (!spawnable(player, pet, ignore)) {
                return;
            }
        }
        int offsetX = 0;
        int offsetY = 0;
        if (Region.getClipping(player.getX() - 1, player.getY(), player.heightLevel, -1, 0)) {
            offsetX = -1;
        } else if (Region.getClipping(player.getX() + 1, player.getY(), player.heightLevel, 1, 0)) {
            offsetX = 1;
        } else if (Region.getClipping(player.getX(), player.getY() - 1, player.heightLevel, 0, -1)) {
            offsetY = -1;
        } else if (Region.getClipping(player.getX(), player.getY() + 1, player.heightLevel, 0, 1)) {
            offsetY = 1;
        }

        if (pet.itemId == 12840 && !ignore) {
            //if (player.getHolidayStages().getStage("Halloween") < 6) {
            //    player.sendMessage("Death JR does not deem you worthy to follow.");
             //   return;
           // }
            player.getItems().deleteItem2(pet.itemId, 1);
            player.hasFollower = true;
            player.summonId = pet.itemId;
    		player.startAnimation(827);
            PlayerSave.saveGame(player);
            int randomDeath = Misc.random(3);
            switch (randomDeath) {
                case 0:
                    Server.npcHandler.spawnNpc3(player, 5568, player.absX + offsetX, player.absY + offsetY,
                            player.heightLevel, 0, 0, 0, 0, 0, true, false, true);
                    break;

                case 1:
                    Server.npcHandler.spawnNpc3(player, 5569, player.absX + offsetX, player.absY + offsetY,
                            player.heightLevel, 0, 0, 0, 0, 0, true, false, true);
                    break;

                case 2:
                    Server.npcHandler.spawnNpc3(player, 5570, player.absX + offsetX, player.absY + offsetY,
                            player.heightLevel, 0, 0, 0, 0, 0, true, false, true);
                    break;

                case 3:
                    Server.npcHandler.spawnNpc3(player, 5571, player.absX + offsetX, player.absY + offsetY,
                            player.heightLevel, 0, 0, 0, 0, 0, true, false, true);
                    break;
            }
        } else {
            if (!ignoreAll) {
                player.getItems().deleteItem2(pet.itemId, 1);
            }
            player.hasFollower = true;
            player.summonId = pet.itemId;
    		player.startAnimation(827);
            PlayerSave.saveGame(player);
            Server.npcHandler.spawnNpc3(player, pet.npcId, player.absX + offsetX, player.absY + offsetY,
                    player.heightLevel, 0, 0, 0, 0, 0, true, false, true);
            if (!ignore) {
            }
        }
    }

    public static boolean pickupPet(Player player, int npcId, boolean item) {
        Pets pets = forNpc(npcId);
        if (pets != null) {
            int itemId = pets.itemId;
            if (!item) {
                NPCHandler.npcs[player.rememberNpcIndex].absX = 0;
                NPCHandler.npcs[player.rememberNpcIndex].absY = 0;
                NPCHandler.npcs[player.rememberNpcIndex] = null;
                player.summonId = -1;
                player.hasFollower = false;
                return true;
            } else {
                if (NPCHandler.npcs[player.rememberNpcIndex].spawnedBy == player.getIndex()) {
                    if (player.getItems().freeSlots() > 0) {
                        NPCHandler.npcs[player.rememberNpcIndex].absX = 0;
                        NPCHandler.npcs[player.rememberNpcIndex].absY = 0;
                        NPCHandler.npcs[player.rememberNpcIndex] = null;
                        player.startAnimation(827);
                        player.getItems().addItem(itemId, 1);
                        player.summonId = -1;
                        player.hasFollower = false;
                        player.sendMessage("You pick up your pet.");
                        return true;
                    } else {
                        player.sendMessage("You do not have enough inventory space to do this.");
                        return false;
                    }
                } else {
                    player.sendMessage("This is not your pet.");
                    return false;
                }
            }
        }
        return false;
    }

    public static void receive(Player player, NPC npc) {
        if (npc == null) {
            return;
        }

        Optional<Pets> pet = PETS.stream().filter(p -> p.parent.equalsIgnoreCase(npc.getDefinition().getNpcName()))
                .findFirst();

        pet.ifPresent(p -> {
            if (player.getItems().getItemCount(p.itemId, false) > 0 || player.summonId == p.itemId) {
                return;
            }

            int rights = player.getRights().getPrimary().getValue() - 1;
            if (RandomUtils.nextInt(0, p.droprate) == 1) {
                player.getItems().addItemUnderAnyCircumstance(p.itemId, 1);
                spawn(player, p, false, false);
                PlayerHandler.executeGlobalMessage("@red@" + Misc.formatPlayerName(player.playerName)
                        + " has received a pet drop from " + p.parent + ".");
            }
        });

    }

    /**
     * Handles metamorphosis of the npc of choice
     *
     * @param player the player performing the metamorphosis
     * @param npcId  the npc to metamorphose
     */
    public static void metamorphosis(Player player, int npcId) {
        Pets pets = forNpc(npcId);
        if (npcId < 1) {
            return;
        }
        if (pets != null) {
            if (NPCHandler.npcs[player.npcClickIndex].spawnedBy != player.getIndex()) {
                player.sendMessage("This is not your pet.");
                return;
            }
            switch (npcId) {
                case 2130:
                case 2131:
                case 2132:
                    NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId == 2132 ? npcId - 2 : npcId + 1);
                    break;
					
				case 8493:
				case 8492:
				case 8494:
					NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId == 8494 ? npcId - 2 : npcId + 1);
                    break;
                    
			     case 6637:
	             case 6638:
	                    NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId == 6638 ? 6637 : 6638);
	                    break;

                case 2988:
                case 2989:
                    NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId == 2989 ? 2988 : 2989);
                    break;

                // case 6718:
                // case 6719:
                // case 6720:
                // case 6721:
                // int randomGold = Misc.random(15_000);
                // NPCHandler.npcs[player.npcClickIndex].requestTransform(randomGold == 15 ?
                // 6721 : npcId == 6721 ? 6718 : npcId == 6720 ? npcId - 2 : npcId + 1);
                // break;
            }
        }
    }

	/*
     * public static void recolor(Player player, int npcId, int itemId) { Pets pets
	 * = forNpc(npcId); if (npcId < 1) { return; } if (pets != null) { if
	 * (NPCHandler.npcs[player.npcClickIndex].spawnedBy != player.getIndex()) {
	 * player.sendMessage("This is not your pet."); return; } switch (npcId) { case
	 * 7439: switch (itemId) { case 438:
	 * NPCHandler.npcs[player.npcClickIndex].requestTransform(7440); break; } break;
	 * 
	 * /*case 6637: NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId ==
	 * 6638 ? 6637 : 6638); break;
	 * 
	 * } } }
	 */

    public static boolean talktoPet(Player c, int npcId) {
        Pets pets = forNpc(npcId);
        if (pets != null) {
            if (NPCHandler.npcs[c.rememberNpcIndex].spawnedBy == c.getIndex()) {
                if (npcId == 4441) {
                    c.getDH().sendDialogues(14000, 3200);
                }
                if (npcId == 4439) {
                    c.getDH().sendDialogues(14003, 3200);
                }
                if (npcId == 4440) {
                    c.getDH().sendDialogues(14006, 3200);
                }
                if (npcId == 4446) {
                    c.getDH().sendDialogues(14009, 3200);
                }
                if (npcId == 4442) {
                    c.getDH().sendDialogues(14011, 3200);
                }
                if (npcId == 4438) {
                    c.getDH().sendDialogues(14014, 3200);
                }
            } else {
                c.sendMessage("This is not your pet.");
            }
            return true;
        } else {
            return false;
        }
    }

}