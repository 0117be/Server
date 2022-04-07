package ethos.model.content.achievement_diary.lumbridge_draynor;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum LumbridgeDraynorDiaryEntry {
	//Easy
	TELEPORT_ESSENCE_LUM("Teleport to the Essence Mine (Wizards Tower / Sedridor)"),
	CRAFT_WATER("Craft some water runes"),
	PICKPOCKET_MAN_LUM("Pickpocket a man"),
	BURN_OAK("Burn some oak logs: %totalstage", 28),
	COOK_SHRIMP("Cook some shrimp within the castle"),
	MINE_IRON_LUM("Mine some iron at the Al-Kharid mine: %totalstage", 50),
	
	//Medium,
	LUMBRIDGE_TELEPORT("Teleport to Lumbridge"),
	RIVER_LUM_SHORTCUT("Grapple the broken raft over the River Lum"),
	ATTRACTOR("Purchase an attractor from Ava"),
	CHOP_WILLOW_DRAY("Chop some willows in Draynor: %totalstage", 150),
	PICKPOCKET_FARMER_DRAY("Pickpocket the Master Farmer in draynor: %totalstage", 150),
	
	//Hard,
	BONES_TO_PEACHES("Cast bones to peaches spell in Al-Kharid"),
	CRAFT_COSMIC("Craft more than 56 cosmic runes in one go: %totalstage", 30),
	HANS("Have Hans tell you how long you've played"),
	
	//Elite
	CHOP_MAGIC_AL("Chop some magic logs in Al-Kharid: %totalstage", 250),
	RUNE_PLATE_LUM("Smith a rune platebody in the Draynor sewers"),
	ACHIEVEMENT_EMOTE("Perform the hunter skillcape emote in Draynor");
	
	private final String description;
	
	private final int maximumStages;
	
	public static final Set<LumbridgeDraynorDiaryEntry> SET = EnumSet.allOf(LumbridgeDraynorDiaryEntry.class);
	
	LumbridgeDraynorDiaryEntry(String description) {
		this(description, -1);
	}
	
	LumbridgeDraynorDiaryEntry(String description, int maximumStages) {
		this.description = description;
		this.maximumStages = maximumStages;
	}
	
	public final String getDescription() {
		return description;
	}
	
	public static final Optional<LumbridgeDraynorDiaryEntry> fromName(String name) {
		return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
	}

	public int getMaximumStages() {
		return maximumStages;
	}
}
