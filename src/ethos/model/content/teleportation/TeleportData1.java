package ethos.model.content.teleportation;

/**
 * The teleport data.
 * 
 * @author Adam_#6723
 */
public enum TeleportData1 {

	COWS( 65031, 52119, "Lumbridge cows", "Lumbride Cows", 10, 0, "Melee", "Easy", TeleportType1.MONSTERS, 3259, 3267, 0, 5, false, 239, new int[] {4151, 3140, 4087, 4587, 995, 4151}),
	CRABS( 17032, 52120, "Rock Crabs", "Rock Crabs", 15, 0, "Melee", "Easy", TeleportType1.MONSTERS, 2673, 3710, 0, 10, false, 8095, new int[] {-1, -1, -1, -1, -1}),
	SAND_CRABS( 17033, 52121, "Sand Crabs", "Sand Crabs", 20, 0, "Melee", "Easy", TeleportType1.MONSTERS, 1866, 3552, 1, 0, false, 1701, new int[] {-1, -1, -1, -1}),
	AMMONITE_CRABS(17034, 52122, "Ammonite crabs", "", 25, 0, "Melee", "Medium", TeleportType1.MONSTERS, 3726, 3892, 0, 10, false, 2, new int[] {4151, 3140, 4087, 4587}),
	
	ZULRAH( 17031, 52119, "Zulrah", "", 300, 0, "Range", "@red@Hard", TeleportType1.BOSSES, 1, 10, 0, 10, false, 10, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	SIRE( 17032, 52120, "Abyssal Sire", "", 500, 0, "Range", "@red@Hard AF Boi", TeleportType1.BOSSES, 1, 10, 0, 10, false, 10, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	ARCH( 17033, 52121, "Deranged Arch", "Deranged Archelogist", 250, 2, "Magic", "Extreme", TeleportType1.BOSSES, 1, 10, 0, 10, false, 10, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	VORK( 17034, 52122, "Vorkath", "", 500, 0, "Magic", "Ultimate", TeleportType1.BOSSES, 1, 10, 0, 10, false, 10, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	//Skilling
	NEW_1( 17031, 52119, "Resource Area", "Wildy Resource Area @red@(Level 54)", 0, 0, "", "", TeleportType1.SKILLING, 3184, 3946, 0, 79, true, 260, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_2( 17031, 52120, "Craft Guild", "Crafting Guild", 0, 0, "", "", TeleportType1.SKILLING, 2936, 3281, 0, 79, true, 260
			, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
		    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_3( 17031, 52121, "Gnome Agilty", "Gnome Agility",  0, 0, "", "", TeleportType1.SKILLING, 2477, 3438, 0, -1, true, -1
			, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
		    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_4( 17031, 52122, "Barb Agility", "Barbarian Agility", 0, 0, "", "", TeleportType1.SKILLING, 2552, 3562, 0,  -1, true, -1,
			new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	
	//Wilderness
	NEW16(17031, 52119, "Magebank", "Magebank @gr3@(Bank area)", 0, 0, "", "", TeleportType1.WILDERNESS, 2537, 4714, 0, 79, true, 260, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW17(17031, 52120, "West Dragons", "West Dragons @red@(Level 10)", 0, 0, "", "", TeleportType1.WILDERNESS, 2979, 3594, 0, 79, true, 260, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW18(17031, 52121, "East Dragons", "East Dragons @red@(Level 17)", 0, 0, "", "", TeleportType1.WILDERNESS, 3348, 3647, 0, 79, true, 260, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_20(17031, 52122, "Elder Druids", "Elder Chaos Druids @red@(Level 15)", 0, 0, "", "", TeleportType1.WILDERNESS, 3235, 3635, 0, 129, true, 6607, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	
	//Cities
	NEW_39( 17031, 52119, "Fossil (EAST)", "Fossil Island (east)", 0, 0, "", "", TeleportType1.CITIES, 3817, 3808, 0, -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_40( 17031, 52120, "Fossil (WEST)", "Fossil Island (WEST)", 0, 0, "", "", TeleportType1.CITIES, 3735, 3803, 0,  -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_41( 17031, 52121, "Land's End", "", 0, 0, "", "", TeleportType1.CITIES, 1504, 3423, 0, -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_42( 17031, 52122, "Varrock", "", 0, 0, "", "", TeleportType1.CITIES, 3213, 3424, 0,  -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	
	
	NEW_58( 17031, 52119, "Duel Arena", "", 0, 0, "", "", TeleportType1.MINIGAMES, 3366, 3266, 0, -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_60( 17031, 52120, "Item Doubler", "",  0, 0, "", "", TeleportType1.MINIGAMES, 3091, 3505, 0, -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_61( 17031, 52121, "Dicing", "", 0, 0, "", "", TeleportType1.MINIGAMES, 2459, 3094, 0, -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	NEW_62( 17031, 52122, "Barrows", "", 0, 0, "", "", TeleportType1.MINIGAMES, 3565, 3315, 0, -1, false, -1, new int[] { 12601, 19550, 13227, 11838, 6914, 6889, 19547, 19553, 
    		12002, 6585, 11808, 11804, 12926, 11826, 13271, 11335}),
	;
	

	/** The name of the teleport. */
	private final String name;
	
	private final String fullname;

	/** The type of the teleport. */
	private final TeleportType1 type;

	/** The position of the teleport. */

	public final int buttonId;

	private int cblvl;

	private boolean wildy;

	private int npcId;

	private int index;

	private int clickingid;

	private int x;

	private int y;

	private int z;
	
	private int health;
	
	public int getHealth() {
		return health;
	}

	public int getTeamsize() {
		return teamsize;
	}

	public String getAttackstyles() {
		return attackstyles;
	}

	public String getDifficulty() {
		return difficulty;
	}

	private int teamsize;
	
	private String attackstyles;
	
	private String difficulty;
	
	private int item[];


	public int[] getItem() {
		return item;
	}

	public void setItem(int[] item) {
		this.item = item;
	}

	/** Creates a new <code>Teleport<code>. */
	TeleportData1( int buttonId, int clickingid, String name, String fullname, int health, int teamsize, String attackstyles,
			String difficulty, TeleportType1 type, int x, int y, int z,
			int cblvl, boolean wildy, int npcId,  int[] item) {
		//this.index = (index);
		this.buttonId = (buttonId);
		this.clickingid = (clickingid);
		this.name = (name);
		this.fullname = (fullname);
		this.health = (health);
		this.teamsize = (teamsize);
		this.attackstyles = (attackstyles);
		this.difficulty = (difficulty);
		this.type = (type);
		this.x = (x);
		this.y = (y);
		this.z = (z);
		this.cblvl = (cblvl);
		this.wildy = (wildy);
		this.npcId = (npcId);
		this.item = (item);
	}

	public String getName() {
		return name;
	}
	
	public String getFullName() {
		return fullname;
	}

	public TeleportType1 getType() {
		return type;
	}

	public int getCblvl() {
		return cblvl;
	}

	public int getClickingId() {
		return clickingid;
	}

	public boolean getWildy() {
		return wildy;
	}

	public int getButtonId() {
		return buttonId;
	}

	public int getIndex() {
		return index;
	}

	public int getZ() {
		return z;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getNpcId() {
		return npcId;
	}

}
