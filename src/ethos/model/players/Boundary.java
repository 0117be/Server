package ethos.model.players;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import ethos.model.players.Boundary;
import ethos.model.npcs.NPC;

/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @date Mar 2, 2014
 */
public class Boundary {

	int minX, minY, highX, highY;
	int height;
	
	public Boundary(Rectangle rect) {
		this.minX = rect.x;
		this.minY = rect.y;
		this.highX = rect.x + rect.width;
		this.highY = rect.y + rect.height;
		this.height = -1;
	}
	/**
	 * 
	 * @param minX The south-west x coordinate
	 * @param minY The south-west y coordinate
	 * @param highX The north-east x coordinate
	 * @param highY The north-east y coordinate
	 */
	public Boundary(int minX, int minY, int highX, int highY) {
		this.minX = minX;
		this.minY = minY;
		this.highX = highX;
		this.highY = highY;
		height = -1;
	}

	/**
	 * 
	 * @param minX The south-west x coordinate
	 * @param minY The south-west y coordinate
	 * @param highX The north-east x coordinate
	 * @param highY The north-east y coordinate
	 * @param height The height of the boundary
	 */
	public Boundary(int minX, int minY, int highX, int highY, int height) {
		this.minX = minX;
		this.minY = minY;
		this.highX = highX;
		this.highY = highY;
		this.height = height;
	}

	public int getMinimumX() {
		return minX;
	}

	public int getMinimumY() {
		return minY;
	}

	public int getMaximumX() {
		return highX;
	}

	public int getMaximumY() {
		return highY;
	}

	public static Boundary ofRect(int x, int y, int width, int height) {
		return new Boundary(new Rectangle(x, y, width, height));
	}
	/**
	 * 
	 * @param player The player object
	 * @param boundaries The array of Boundary objects
	 * @return
	 */
	public static boolean isIn(Player player, Boundary[] boundaries) {
		for (Boundary b : boundaries) {
			if (b.height >= 0) {
				if (player.heightLevel != b.height) {
					continue;
				}
			}
			if (player.absX >= b.minX && player.absX <= b.highX && player.absY >= b.minY && player.absY <= b.highY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param player The player object
	 * @param boundaries The boundary object
	 * @return
	 */
	public static boolean isIn(Player player, Boundary boundaries) {
		if (boundaries.height >= 0) {
			if (player.heightLevel != boundaries.height) {
				return false;
			}
		}
		return player.absX >= boundaries.minX && player.absX <= boundaries.highX && player.absY >= boundaries.minY && player.absY <= boundaries.highY;
	}

	/**
	 * 
	 * @param npc The npc object
	 * @param boundaries The boundary object
	 * @return
	 */
	public static boolean isIn(NPC npc, Boundary boundaries) {
		if (boundaries.height >= 0) {
			if (npc.heightLevel != boundaries.height) {
				return false;
			}
		}
		return npc.absX >= boundaries.minX && npc.absX <= boundaries.highX && npc.absY >= boundaries.minY && npc.absY <= boundaries.highY;
	}

	public static boolean isIn(NPC npc, Boundary[] boundaries) {
		for (Boundary boundary : boundaries) {
			if (boundary.height >= 0) {
				if (npc.heightLevel != boundary.height) {
					return false;
				}
			}
			if (npc.absX >= boundary.minX && npc.absX <= boundary.highX && npc.absY >= boundary.minY && npc.absY <= boundary.highY) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInSameBoundary(Player player1, Player player2, Boundary[] boundaries) {
		Optional<Boundary> boundary1 = Arrays.asList(boundaries).stream().filter(b -> isIn(player1, b)).findFirst();
		Optional<Boundary> boundary2 = Arrays.asList(boundaries).stream().filter(b -> isIn(player2, b)).findFirst();
		if (!boundary1.isPresent() || !boundary2.isPresent()) {
			return false;
		}
		return Objects.equals(boundary1.get(), boundary2.get());
	}

	public static int entitiesInArea(Boundary boundary) {
		int i = 0;
		for (Player player : PlayerHandler.players)
			if (player != null)
				if (isIn(player, boundary))
					i++;
		return i;
	}

	/**
	 * Returns the centre point of a boundary as a {@link Coordinate}
	 * 
	 * @param boundary The boundary of which we want the centre.
	 * @return The centre point of the boundary, represented as a {@link Coordinate}.
	 */
	public static Coordinate centre(Boundary boundary) {
		int x = (boundary.minX + boundary.highX) / 2;
		int y = (boundary.minY + boundary.highY) / 2;
		if (boundary.height >= 0) {
			return new Coordinate(x, y, boundary.height);
		} else {
			return new Coordinate(x, y, 0);
		}
	}
	
	/**
	 * Diary locations
	 */
	public static final Boundary VARROCK_BOUNDARY = new Boundary(3136, 3349, 3326, 3519);
	public static final Boundary ARDOUGNE_BOUNDARY = new Boundary(2432, 3259, 2690, 3380);
	public static final Boundary ARDOUGNE_ZOO_BRIDGE_BOUNDARY = new Boundary(2611, 3270, 2614, 3280);
	public static final Boundary FALADOR_BOUNDARY = new Boundary(2935, 3310, 3066, 3394);
	public static final Boundary CRAFTING_GUILD_BOUNDARY = new Boundary(2925, 3274, 2944, 3292);
	public static final Boundary TAVERLY_BOUNDARY = new Boundary(2866, 3388, 2938, 3517);
	public static final Boundary LUMRIDGE_BOUNDARY = new Boundary(3142, 3139, 3265, 3306);
	public static final Boundary DRAYNOR_DUNGEON_BOUNDARY = new Boundary(3084, 9623, 3132, 9700);
	public static final Boundary AL_KHARID_BOUNDARY = new Boundary(3263, 3136, 3388, 3328);
	public static final Boundary DRAYNOR_MANOR_BOUNDARY = new Boundary(3074, 3311, 3131, 3388);
	public static final Boundary DRAYNOR_BOUNDARY = new Boundary(3065, 3216, 3136, 3292);
	public static final Boundary KARAMJA_BOUNDARY = new Boundary(2816, 3139, 2965, 3205);
	public static final Boundary BRIMHAVEN_BOUNDARY = new Boundary(2683, 3138, 2815, 3248);
	public static final Boundary BRIMHAVEN_DUNGEON_BOUNDARY = new Boundary(2627, 9415, 2745, 9600);
	public static final Boundary TZHAAR_CITY_BOUNDARY = new Boundary(2368, 5056, 2495, 5183);
	public static final Boundary FOUNTAIN_OF_RUNE_BOUNDARY = new Boundary(3367, 3888, 3380, 3899);
	public static final Boundary DEMONIC_RUINS_BOUNDARY = new Boundary(3279, 3879, 3294, 3893);
	public static final Boundary WILDERNESS_GOD_WARS_BOUNDARY = new Boundary(3008, 10112, 3071, 10175);
	public static final Boundary RESOURCE_AREA_BOUNDARY = new Boundary(3173, 3923, 3197, 3945);
	public static final Boundary CANIFIS_BOUNDARY = new Boundary(3471, 3462, 3516, 3514);
	public static final Boundary CATHERBY_BOUNDARY = new Boundary(2767, 3392, 2864, 3521);
	public static final Boundary SEERS_BOUNDARY = new Boundary(2574, 3393, 2766, 3517);
	public static final Boundary RELLEKKA_BOUNDARY = new Boundary(2590, 3597, 2815, 3837);
	public static final Boundary GNOME_STRONGHOLD_BOUNDARY = new Boundary(2369, 3398, 2503, 3550);
	public static final Boundary LLETYA_BOUNDARY = new Boundary(2314, 3153, 2358, 3195);
	public static final Boundary BANDIT_CAMP_BOUNDARY = new Boundary(3156, 2965, 3189, 2993);
	public static final Boundary DESERT_BOUNDARY = new Boundary(3136, 2880, 3517, 3122);
	public static final Boundary SLAYER_TOWER_BOUNDARY = new Boundary(3399, 3527, 3454, 3581);
	public static final Boundary APE_ATOLL_BOUNDARY = new Boundary(2691, 2692, 2815, 2812);
	public static final Boundary FELDIP_HILLS_BOUNDARY = new Boundary(2474, 2880, 2672, 3010);
	public static final Boundary YANILLE_BOUNDARY = new Boundary(2531, 3072, 2624, 3126);
	public static final Boundary ZEAH_BOUNDARY = new Boundary(1402, 3446, 1920, 3972);
	public static final Boundary LUNAR_ISLE_BOUNDARY = new Boundary(2049, 3844, 2187, 3959);
	public static final Boundary FREMENNIK_ISLES_BOUNDARY = new Boundary(2300, 3776, 2436, 3902);
	public static final Boundary WATERBIRTH_ISLAND_BOUNDARY = new Boundary(2495, 3711, 2559, 3772);
	public static final Boundary MISCELLANIA_BOUNDARY = new Boundary(2493, 3835, 2628, 3921);
	public static final Boundary WOODCUTTING_GUILD_BOUNDARY = new Boundary(1608,3479,1657,3516);
	public static final Boundary WARRIORS_GUILD = new  Boundary(2838,3537,2875,3555);
	
	public static final Boundary BARROWS = new  Boundary(3541,3264,3589,3315);
	
	public static final Boundary BOSSINGZONES = new  Boundary(1981,2817,2011,2884);
	
	public static final Boundary BOSSINGZONE1 = new  Boundary(1988,2820,2007,2843);
	
	public static final Boundary BOSSINGZONE2 = new  Boundary(1987,2852,2009,2880);
	/**
	 * Halloween
	 */
	public static final Boundary HALLOWEEN_ORDER_MINIGAME = new Boundary(2591, 4764, 2617, 4786);
	/*
	 * Hydra Dungeon
	 */
	public static final Boundary HYDRA_DUNGEON = new Boundary(1297, 10215, 1397, 10283);
	public static final Boundary HYDRA_DUNGEON2 = new Boundary(1248, 10144, 1302, 10210);
	public static final Boundary HYDRA_BOSS_ROOM = new Boundary(1355, 10253, 1380, 10281);
	public static final Boundary[] HYDRA_ROOMS = { HYDRA_DUNGEON, HYDRA_DUNGEON2, HYDRA_BOSS_ROOM };
	
	/**
	 * Hunter
	 */
	public static final Boundary HUNTER_JUNGLE = new Boundary(1486, 3392, 1685, 3520);
	public static final Boundary HUNTER_LOVAK = new Boundary(1468, 3840, 1511, 3890);
	public static final Boundary HUNTER_DONATOR = new Boundary(2124, 4917, 2157, 4946);
	public static final Boundary HUNTER_WILDERNESS = new Boundary(3128, 3755, 3172, 3796);
	public static final Boundary PURO_PURO = new Boundary(2561, 4289, 2623, 4351);
	public static final Boundary[] HUNTER_BOUNDARIES = { HUNTER_JUNGLE, HUNTER_WILDERNESS, HUNTER_LOVAK, HUNTER_DONATOR };
	
	public static final Boundary LAVA_DRAGON_ISLE = new Boundary(3174, 3801, 3233, 3855);
	public static final Boundary RUNE_DRAG = new Boundary(1577, 5063, 1600, 5090);
	public static final Boundary ADDY_DRAG = new Boundary(1538, 5063, 1558, 5089);
	
	public static final Boundary ABYSSAL_SIRE = new Boundary(2942, 4735, 3136, 4863);
	
	public static final Boundary COMBAT_DUMMY = new Boundary(3090, 3508, 3096, 3513);
	
	public static final Boundary SAFEPKMULTI = new Boundary(3090, 3525, 3109, 3536);
	
	public static final Boundary SAFEPKSAFE = new Boundary(3068, 3516, 3109, 3536);
	
	public static final Boundary SAFEPK = new Boundary(3068, 3525, 3109, 3536);
	/**
	 * Raids bosses
	 */
	public static final Boundary RAID_MAIN = new Boundary(3295, 5152, 3359, 5407, 0);
	public static final Boundary RAID_F1 = new Boundary(3295, 5152, 3359, 5407, 1);
	public static final Boundary RAID_F2 = new Boundary(3295, 5152, 3359, 5407, 2);
	public static final Boundary RAID_F3 = new Boundary(3295, 5152, 3359, 5407, 3);
	
	
	/**
	 * 
	 * RAIDS ENTIRE AREA 
	 */
	
	public static final Boundary ENTIRERAIDS = new Boundary(3240, 5210, 3374, 5494);
	
	public static final Boundary OLM = new Boundary(3217, 5728, 3250, 5760);
	public static final Boundary RAIDS = new Boundary(3259, 5145, 3361, 5474);
	public static final Boundary TEKTON = new Boundary(3296, 5281, 3327, 5310);
	public static final Boundary TEKTON_ATTACK_BOUNDARY = new Boundary(3299, 5285, 3321, 5301);
	public static final Boundary SKELETAL_MYSTICS = new Boundary(3298, 5249, 3325, 5275);
	public static final Boundary ICE_DEMON = new Boundary(3297, 5343, 3325, 5374);
	
	public static final Boundary ALTAR = new Boundary(3223, 3603, 3242, 3673);
	public static final Boundary FORTRESS = new Boundary(2993, 3615, 3024, 3648);
	public static final Boundary DEMONIC = new Boundary(3236, 3852, 3275, 3884);
	public static final Boundary ROGUES = new Boundary(3293, 3919, 3320, 3950);
	public static final Boundary DRAGONS = new Boundary(3293, 3655, 3320, 3682);
	
	public static final Boundary TOB = new Boundary(3130, 4247, 3352, 4527);
	
	public static final Boundary GALVEK_1 = new Boundary(2600, 3110, 2653, 3151);//yanille
	public static final Boundary GALVEK_2 = new Boundary(3465, 3243, 3526, 3320);//barrows
	public static final Boundary GALVEK_3 = new Boundary(2615, 3602, 2706, 3650);//relleka
	public static final Boundary HOME = new Boundary(3067, 3520, 3115, 3464);//edge
	public static final Boundary[] PURSUIT_AREAS  = { ALTAR, FORTRESS, DEMONIC, ROGUES, DRAGONS, GALVEK_1, GALVEK_2, GALVEK_3, HOME };
	
	/**
	 * Kalphite Queen
	 */
	public static final Boundary KALPHITE_QUEEN = new Boundary(3457, 9472, 3514, 9527);
	
	public static final Boundary CATACOMBS = new Boundary(1590, 9980, 1731, 10115);
	

	public static final Boundary CLAN_WARS = new Boundary(3272, 4759, 3380, 4852);
	public static final Boundary CLAN_WARS_SAFE = new Boundary(3263, 4735, 3390, 4761);
	/**
	 * Cerberus spawns
	 */
	public static final Boundary BOSS_ROOM_WEST = new Boundary(1224, 1225, 1259, 1274);
	public static final Boundary BOSS_ROOM_NORTH = new Boundary(1290, 1288, 1323, 1338);
	public static final Boundary BOSS_ROOM_EAST = new Boundary(1354, 1224, 1387, 1274);
	public static final Boundary WITHIN_BOUNDARY_CERB = new Boundary(1234, 1246, 1246, 1256);
	public static final Boundary[] CERBERUS_BOSSROOMS  = { BOSS_ROOM_NORTH, BOSS_ROOM_WEST, BOSS_ROOM_EAST };
	
	
	public static final Boundary SKOTIZO_BOSSROOM = new Boundary(1678, 9870, 1714, 9905);
	
	public static final Boundary CHASM_OF_FIRE = new Boundary(1405, 10111, 1475, 10049);
	
	public static final Boundary BANDOS_GODWARS = new Boundary(2864, 5351, 2876, 5369);
	public static final Boundary ARMADYL_GODWARS = new Boundary(2824, 5296, 2842, 5308);
	public static final Boundary ZAMORAK_GODWARS = new Boundary(2918, 5318, 2936, 5331);
	public static final Boundary SARADOMIN_GODWARS = new Boundary(2889, 5258, 2907, 5276);
	public static final Boundary[] GODWARS_BOSSROOMS = { BANDOS_GODWARS, ARMADYL_GODWARS, ZAMORAK_GODWARS, SARADOMIN_GODWARS };
	
	public static final Boundary FIGHT_ROOM = new Boundary(1671,4690,1695,4715);
	
	public static final Boundary WILDERNESS = new Boundary(2941, 3525, 3392, 3968);
	public static final Boundary WILDERNESS_UNDERGROUND = new Boundary(2941, 9918, 3392, 10366);
	public static final Boundary RISKY = new Boundary(3121, 3489, 3128, 3495);
	public static final Boundary[] WILDERNESS_PARAMETERS = { WILDERNESS, WILDERNESS_UNDERGROUND, WILDERNESS_GOD_WARS_BOUNDARY, RISKY };
	public static final Boundary REV_CAVE = new Boundary(3160, 10020, 3263, 10226);
	public static final Boundary REV_CAVE_EMBLEM_TRADER = new Boundary(3238, 10227, 3244, 10231);

	public static final Boundary CHAOS_ALTAR_HUT = new Boundary(2942, 3815, 2958, 3824);

	
	//public static final Boundary WILDERNESS = new Boundary(2941, 3525, 3392, 3968); // (2941, 3525, 3392, 3968);
//public static final Boundary WILDERNESS = new Boundary(1486, 1444, 3767, 3730);
	//public static final Boundary ZEAH_WILDERNESS = new Boundary(1420, 3730, 1600, 4060);
	//public static final Boundary WILDERNESS_UNDERGROUND = new Boundary(2941, 9918, 3392, 10366);
	//public static final Boundary[] WILDERNESS_PARAMETERS = { WILDERNESS, ZEAH_WILDERNESS, WILDERNESS_UNDERGROUND, WILDERNESS_GOD_WARS_BOUNDARY };
	
	public static final Boundary INFERNO = new Boundary(2256, 5328, 2286, 5359);

	public static final Boundary EDGE_BANK = new Boundary(3098, 3487, 3106, 3498);

	public static final Boundary ICE_PATH = new Boundary(2837, 3786, 2870, 3821);
	public static final Boundary ICE_PATH_TOP = new Boundary(2822, 3806, 2837, 3813);
	public static final Boundary SEERS = new Boundary(2689, 3455, 2734, 3500);
	public static final Boundary VARROCK = new Boundary(3178, 3390, 3243, 3423);
	public static final Boundary ARDOUGNE = new Boundary(2644, 3288, 2678, 3323);
	public static final Boundary[] ROOFTOP_COURSES = { SEERS_BOUNDARY, VARROCK_BOUNDARY, ARDOUGNE_BOUNDARY };
	public static final Boundary DONATOR_ZONE = new Boundary(2118, 2808, 2177, 2883);
	public static final Boundary EDONATOR_ZONE = new Boundary(1978, 2814, 2117, 2882);
	public static final Boundary DIAMOND_ZONE = new Boundary(3062, 3059, 3155, 3140);
	public static final Boundary GALVEK = new Boundary(3267, 5110, 3302, 5046);
	public static final Boundary LIZARDMAN_CANYON = new Boundary(1468, 3674, 1567, 3709);
	public static final Boundary CORPOREAL_BEAST_LAIR = new Boundary(2972, 4370, 2999, 4397);
	public static final Boundary DAGANNOTH_KINGS = new Boundary(2891, 4428, 2934, 4469);
	public static final Boundary SCORPIA_LAIR = new Boundary(3216, 10329, 3248, 10354);
	public static final Boundary KRAKEN_CAVE = new Boundary(2240, 9984, 2303, 10047);
	public static final Boundary LIGHTHOUSE = new Boundary(2501, 4627, 2541, 4662);
	public static final Boundary RFD = new Boundary(1888, 5344, 1911, 5367);
	public static final Boundary RESOURCE_AREA = new Boundary(3174, 3924, 3196, 3944);
	public static final Boundary KBD_AREA = new Boundary(2251, 4675, 2296, 4719);
	public static final Boundary PEST_CONTROL_AREA = new Boundary(2622, 2554, 2683, 2675);
	public static final Boundary FIGHT_CAVE = new Boundary(2365, 5052, 2437, 5123);
	public static final Boundary EDGEVILLE_PERIMETER = new Boundary(3073, 3465, 3108, 3518);
	public static final Boundary[] DUEL_ARENA = new Boundary[] { new Boundary(3332, 3244, 3359, 3259), new Boundary(3364, 3244, 3389, 3259) };
	public static final Boundary ZULRAH = new Boundary(2257, 3067, 2277, 3080);
	public static final Boundary VORKATH = new Boundary(2259, 4053, 2286, 4081);
	
	/**
	 * Places where hardcores keep their status on death
	 */
	public static final Boundary[] HARDCORESAFEZONE = { FIGHT_CAVE, RAIDS, INFERNO, OLM, RFD, LIGHTHOUSE, SKOTIZO_BOSSROOM, ZULRAH, VORKATH, WITHIN_BOUNDARY_CERB, TOB};
}