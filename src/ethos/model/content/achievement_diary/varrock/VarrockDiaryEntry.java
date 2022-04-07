package ethos.model.content.achievement_diary.varrock;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum VarrockDiaryEntry {
	//Easy
	VARROCK_ROOFTOP("Run the Varrock rooftop agility course: %totalstage" , 25),
	SMITH_STEEL_KNIFES("Smith Steel Knives 10 seperate times: %totalstage", 10),
	FILL_VIAL("Fill some vials with water from the center fountain: %totalstage", 10),
	EARTH_RUNES("Craft some earth runes"),
	TELEPORT_ESSENCE_VAR("Ask the Mage of Zamorak to go to the Essence Mine"),
	MINE_ESSENCE("Mine some rune essence: %totalstage", 10),
	TEA_STALL("Steal from the tea stall"),
	BECOME_A_DANCER("Dance, dance, dance: %totalstage", 15),
	MINE_IRON("Mine iron ore at the Varrock Mine: %totalstage", 10),
	
	//Medium
	CHAMPIONS_GUILD("Enter the champions guild"),
	DRESS_FOR_SUCESS("Browse Thessalia's clothes shop"),
	APOTHECARY_STRENGTH("Have the apothecary brew you a strength potion"),
	PURCHASE_KITTEN("Buy a kitten from Gertrude"),
	POTION_DECANT("Have Bob Barter decant your potions"),
	
	//Hard
	PRAY_WITH_SMITE("Pray at the church altar with Smite active"),
	OBSTACLE_PIPE("Squeeze through the pipe in edgeville dungeon"),
	YEWS_AND_BURN("Chop a total of 80 yew logs: %totalstage", 80),
	COOK_LOBSTER("Cook 150 lobsters in the cooking guild: %totalstage", 150),
	
	//Elite
	SUPER_COMBAT("Create some super combat potions: %totalstage", 100),
	SMITH_RUNE_KNIFES("Smith a total of 200 rune knife sets: %totalstage", 200),
	ALOT_OF_EARTH("Craft more than 150 earth runes in one go: %totalstage", 30),
	PRAY_WITH_PIETY("Pray at the chaos altar with piety active");
	
	private final String description;
	
	private final int maximumStages;
	
	public static final Set<VarrockDiaryEntry> SET = EnumSet.allOf(VarrockDiaryEntry.class);
	
	VarrockDiaryEntry(String description) {
		this(description, -1);
	}
	
	VarrockDiaryEntry(String description, int maximumStages) {
		this.description = description;
		this.maximumStages = maximumStages;
	}
	
	public final String getDescription() {
		return description;
	}
	
	public static final Optional<VarrockDiaryEntry> fromName(String name) {
		return SET.stream().filter(entry -> entry.name().equalsIgnoreCase(name)).findAny();
	}

	public int getMaximumStages() {
		return maximumStages;
	}
}
