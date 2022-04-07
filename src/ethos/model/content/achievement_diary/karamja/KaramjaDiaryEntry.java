package ethos.model.content.achievement_diary.karamja;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum KaramjaDiaryEntry {
	//Easy
	PICK_BANANAS("Pick bananas: %totalstage", 32),
	TELEPORT_TO_KARAMJA("Use a glory teleport to Karamja"),
	SAIL_TO_ARDOUGNE("Travel to Ardougne from the port in Brimhaven"),
	FISH_LOBSTER_KAR("Fish some lobsters: %totalstage", 58),
	ENTER_BRIMHAVEN_DUNGEON("Enter the Brimhaven Dungeon"),
	
	//Medium
	DURADEL("Be assigned a slayer task by Duradel"),
	STEEL_DRAGON("Kill steel dragons in Brimhaven dungeon: %totalstage", 50),
	MOSS_GIANT("Kill moss giants on the island west of Brimhaven: %totalstage", 50),
	
	//Hard
	MINE_GOLD_KAR("Mine some gold in Tzhaar city: %totalstage", 150),
	KILL_TZHAAR_XIL("Kill some Tzhaar-xil: %totalstage", 100),
	
	//Elite
	CRAFT_NATURES("Craft more than 50 nature runes in one go: %totalstage", 85),
	ANTI_VENOM("Create some anti-venom potions in Brimhaven: %totalstage", 120),
	EQUIP_FIRE_CAPE("Equip a fire cape in the Tzhaar city");
	
	private final String description;
	
	private final int maximumStages;
	
	public static final Set<KaramjaDiaryEntry> SET = EnumSet.allOf(KaramjaDiaryEntry.class);
	
	KaramjaDiaryEntry(String description) {
		this(description, -1);
	}
	
	KaramjaDiaryEntry(String description, int maximumStages) {
		this.description = description;
		this.maximumStages = maximumStages;
	}
	
	public final String getDescription() {
		return description;
	}
	
	public static final Optional<KaramjaDiaryEntry> fromName(String name) {
		return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
	}

	public int getMaximumStages() {
		return maximumStages;
	}
}
