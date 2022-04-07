package ethos.model.content.teleportation;

import ethos.Config;
import ethos.Server;
import ethos.event.impl.WheatPortalEvent;
import ethos.model.content.QuickSets;
import ethos.model.content.achievement_diary.western_provinces.WesternDiaryEntry;
import ethos.model.minigames.rfd.RecipeForDisaster;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.model.players.skills.Skill;

/*
 * Teleport handler for the teleport tab
 * 
 * @author James
 */

public class QuickSetsTab {
	
	private Player c;
	
	public QuickSetsTab(Player player) {
		this.c = player;
	}

	String[] dopeNames = { "Main/Dds", "Main/Ags", "Main/Claws", "Dh Main","Bandos Main", "Vesta/Max", "",
			"@whi@ @or1@ ", "@whi@ @or1@ ", "@whi@ @or1@", "@whi@ @or1@", "@whi@ @or1@ ", "@whi@ @or1@", "@whi@ @or1@  ", "@whi@ @or1@ ",
			"@whi@ @or1@ ", "@whi@ @or1@ ", "@whi@ @red@ ", "@whi@ @red@ ", "@whi@ @red@ ", "@whi@ @red@ ", "", "", ""};
	
	String[] minigameNames = { "@cr9@ @whi@Duel Arena@gre@ - Staking","@cr9@ @whi@RFD@gre@ - Gloves", "@cr9@ @whi@Pest Control@gre@  (E)", "@cr9@ @whi@Shayzien Assault@or1@  (M)", "@cr9@ @whi@Warriors Guild@or1@  (M)", "@cr9@ @whi@Barrows@or1@  (M)", "@cr9@ @whi@Fight Caves@or2@  (H)",
			"@cr9@ @whi@Mage Arena@or2@  (H)", "@cr9@ @whi@COX@red@ - Advanced", "@cr9@ @whi@TOB@red@ - Advanced", "@cr9@ @whi@Inferno@red@ - Advanced", "@gre@@cr11@ Minigame Guides", "", "", "", "", "", "",
			"", "", "", ""};
	
	String[] skillingNames = {  "@cr20@ @gre@Loyal Skilling Zone", "@cr38@@cr40@<img=38> @whi@Agility", "@cr20@ @whi@Crafting/Hunter", "@cr20@ @whi@Farming/Herblore", "@cr20@ @whi@Fishing/Cooking",
			 "@cr20@ @whi@Mining/Smithing", "@cr20@ @whi@Runecrafting", "@cr20@ @whi@Thieving", "@cr20@ @whi@Woodcutting", "@cr20@ @whi@Infernal Skilling", "@cr20@ @whi@Recourse Area @red@(Wild)", "@gre@@cr11@ Skilling Guides", "",
			"", "", "", "", "", "", "", ""}; 
	/*
	 * "Mining", "Smithing", "Fishing", "Woodcutting (low level)", "Woodcutting Guild", "Farming", "Gnome Agility", "Barbarian Agitity", "Wilderness Agility", "Rooftop Agility", "Thieving", "Slayer", "Hunter", "RCing", 
			"", "", "", "", "", ""		
	 */
	
	String[] bossesNames = {"@cr22@ @whi@Abyssal Sire@or1@  (M)", "@cr22@ @whi@Barrelchest@or1@  (E)", "@cr22@ @whi@Corporeal Beast@red@  (H)", "@cr22@ @whi@Cerberus@or2@  (M)",
			"@cr22@ @whi@Dagonnoth Kings@gre@  (E)", "@cr22@ @whi@Giant Mole@gre@  (E)", "@cr22@ @whi@God Wars Dungeon@or2@  (M)",
			"@cr22@ @whi@Kraken@or2@  (M)", "@cr22@ @whi@Lizardman Shaman@gre@  (E)", "@cr22@ @whi@Therm Smoke Devil@gre@  (E)", "@cr22@ @whi@Vorkath@or2@  (M)", "@cr22@ @whi@Zulrah@or2@  (M)",
			"@cr22@ @whi@Demonic Gorillas@gre@  (M)", "@cr22@ @whi@Kalphite Queen@gre@  (M)", "@cr22@ @whi@Alchemical Hydra @red@ (H)", "@gre@@cr11@ Bossing Guides", "", "", "", ""};
			
	String[] wildernessNames = { "@cr21@ @whi@Western Dragons@red@  (12)","@cr21@ @whi@Eastern Dragons@red@  (21)",  "@cr21@ @whi@Ents@red@  (14)", "@cr21@ @whi@Revenant Caves@red@  (40)", "@cr21@ @whi@Demonic Ruins@red@  (46)",
			"@cr21@ @whi@Mage Bank@red@  (55)",  "@cr21@ @whi@Wildy Agility Course@red@  (50)",
			"@cr21@ @whi@Venenatis@red@  (29)", "@cr21@ @whi@Vet'ion@red@  (35)", "@cr21@ @whi@Callisto@red@  (42)", "@cr21@ @whi@Scorpia@red@  (54)", 
			"@cr21@ @whi@Chaos Elemental@red@  (50)", "@cr21@ @whi@Chaos Fanatic@red@  (41)", "@cr21@ @whi@Crazy Archaeologist@red@  (24)", "@cr21@ @whi@King Black Dragon@red@  (42)","@cr21@ @whi@Dusk/Dawn@red@  (44)", "@gre@@cr11@ Wilderness Guides", "",
			"", "", ""};
	
	String[] citiesNames = { "@cr18@ @whi@Al Kharid", "@cr18@ @whi@Ardougne", "@cr18@ @whi@Brimhaven", "@cr18@ @whi@Burthorpe", "@cr18@ @whi@Camelot", "@cr18@ @whi@Canifis",
			"@cr18@ @whi@Catherby", "@cr18@ @whi@Draynor Village", "@cr18@ @whi@Falador", "@cr18@ @whi@Kourend", "@cr18@ @whi@Lumbridge", "@cr18@ @whi@Lletya", "@cr18@ @whi@Mos Le Harmless", "@cr18@ @whi@Pollnivneach",
			"@cr18@ @whi@Rellekka", "@cr18@ @whi@Shilo Village", "@cr18@ @whi@Sophanem", "@cr18@ @whi@Taverley", "@cr18@ @whi@Varrock", "@cr18@ @whi@Yanille"};
	
	String[] donatorNames = { "@blu@@cr4@ Donator Zone", "@gre@@cr6@ Extreme Zone", "@whi@@cr8@ Executive Zone", " @cr16@ @yel@Godly Zone", "@cr27@ @bla@Donator Slayer", "@cr26@ @bla@Donator Revs",
			"@whi@", "@whi@ ", "@whi@", "@whi@", "@whi@", "@whi@", "@whi@", "@whi@",
			"@whi@", "@whi@ ", "@whi@", "@whi@", "@whi@", "@whi@"};
	
	String[] dungeonNames = { "@cr25@ @whi@Edgeville Dungeon@gre@  (E)","@cr25@ @whi@Chasm of Fire@or1@  (M)",
			"@cr25@ @whi@Mithril Dragons@or1@  (M)", "@cr25@ @whi@Brimhaven Dungeon@or1@  (M)", "@cr25@ @whi@Fremennik Dungeon@or1@  (M)", "@cr25@ @whi@Kourend Catacombs@or1@  (M)", "@cr25@ @whi@Karuulm Dungeon@or1@  (M)", "@cr25@ @whi@Lithkren Vault@or1@  (M)", "@cr25@ @whi@Slayer Tower@or1@  (M)", "@cr25@ @whi@Smoke Dungeon@or1@  (M)",
			"@cr25@ @whi@Stronghold Dungeon@or1@  (M)", "@cr25@ @whi@Taverley Dungeon@or1@  (M)", "@cr25@ @whi@Wyvern Cave@red@  (H)", "@cr25@ @whi@Skeletal Wyverns@red@  (H)", "@cr25@ @whi@Cave Horrors@red@  (H)", "@cr25@ @whi@Kalphite Cave@red@  (H)", "@cr25@ @red@Blood Slayer@red@  (@bla@99@red@)", "@gre@@cr11@ Slayer Guides", "", "", "","",""};
	
	
	/*
	 * for(int iD = 57628; iD <= 57648; iD++) {
	 * 20 teleports
	 */
	public void loadMainTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(dopeNames[j], ids[j]);
		}
	}
	
	public void loadMinigamesTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(minigameNames[j], ids[j]);
		}
	}
	
	public void loadSkillingTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(skillingNames[j], ids[j]);
		}
	}
	
	public void loadBossesTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(bossesNames[j], ids[j]);
		}
	}
	
	public void loadWildernessTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(wildernessNames[j], ids[j]);
		}
	}
	
	public void loadCitiesTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(citiesNames[j], ids[j]);
		}
	}
	public void loadDungeonsTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(dungeonNames[j], ids[j]);
		}
	}
	public void loadDonatorTab() {
		int[] ids = { 57628, 57629, 57630, 57631, 57632, 57633, 57634, 57635, 57636, 57637, 57638, 57639, 57640, 57641, 57642, 57643, 57644, 57645, 57646, 57647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(donatorNames[j], ids[j]);
		}
	}
	public void loadTab(Player player, int tab) {
		if (player.teleTabSelected == 0) {
			loadMainTab();
		} else if (player.teleTabSelected == 1) {
			loadMinigamesTab();
		} else if (player.teleTabSelected == 2) {
			loadSkillingTab();
		} else if (player.teleTabSelected == 3) {
			loadBossesTab();
		} else if (player.teleTabSelected == 4) {
			loadWildernessTab();
		} else if (player.teleTabSelected == 5) {
			loadCitiesTab();
		} else if (player.teleTabSelected == 6) {
			loadDungeonsTab();
		} else if (player.teleTabSelected == 7) {
			loadDonatorTab();
		}
	}
	
	public void selection(Player player, int i) {
		player.teleTabSelected = i;
		loadTab(player, i);
	}

	
	public void handleSet(Player player, int Id) {

		switch(Id) {
			case 224159:
				selection(player, 0); //training
				break;
			case 224160:				
				selection(player, 7); //donator
				break;
			case 224161://dungones
				selection(player, 6);
				break;
			case 224162: //minigames
				selection(player, 1);
				break;
			case 224163:
				selection(player, 2);
				break;
			case 224164:
				selection(player, 3); //bosses
				break;
			case 224166:
				selection(player, 4);
				break;
			case 224165:
				selection(player, 5);
				break;
			
		
			case 225007:

			
				if (player.teleTabSelected == 0) { // Training
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 2 ) { // Skilling
					QuickSets.gearUp(c, 0);		
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 7) { // Donator
					QuickSets.gearUp(c, 0);
				}
				break;
			case 225008:
				if (player.teleTabSelected == 0) { // Training
					QuickSets.gearUp(c, 0);
		
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 2) { // Skilling Agility
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 7) { // Donator
					QuickSets.gearUp(c, 0);
				}
					break;
				
			case 225009:	
			
				if (player.teleTabSelected == 0) { // Training
					QuickSets.gearUp(c, 0);
			
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
			
				} else if (player.teleTabSelected == 2) { // Skilling Crafting
					QuickSets.gearUp(c, 0);
				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 7) { // Donator
					QuickSets.gearUp(c, 0);
				}
				break;
				
			case 225010:			
				if (player.teleTabSelected == 0) { // Training
					QuickSets.gearUp(c, 0);
			
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
	
				} else if (player.teleTabSelected == 2) { // Skilling farming
					QuickSets.gearUp(c, 0);
				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
	
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 7) { // Donator
					QuickSets.gearUp(c, 0);
				}
				break;
				
			case 225011:
				if (player.teleTabSelected == 0) { // Training
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
									
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);
	
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
		
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 7) { // Donator
					QuickSets.gearUp(c, 0);
				}
				break;
				
			case 225012:			

				if (player.teleTabSelected == 0) { // Training
					QuickSets.gearUp(c, 0);
		
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);

			
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);
					
					
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
			
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 7) { // Donator
					QuickSets.gearUp(c, 0);
				}
				
				
				break;
				
			case 225013:

				if (player.teleTabSelected == 0) { // Training
					QuickSets.gearUp(c, 0);
	
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
			
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);

				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
	
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
				}
				break;
				
			case 225014:

				if (player.teleTabSelected == 0) { // Monsters
					QuickSets.gearUp(c, 0);
	
				} else if (player.teleTabSelected == 1) { // Minigames

					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);	
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
				}
				break;
			case 225015:

				if (player.teleTabSelected == 0) { // Monsters
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
			
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
				}
				break;
				
			case 225016:
				
				if (player.teleTabSelected == 0) { // Monsters
		
				} else if (player.teleTabSelected == 1) { // Minigames
		
					QuickSets.gearUp(c, 0);
		
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);

	
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
	
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
	
				}
				break;
				
			case 225017:			

				if (player.teleTabSelected == 0) { // Monsters
					
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
	
					
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);
				
				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
			
					
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
			
				}
				break;
				
			case 225018:

				if (player.teleTabSelected == 0) { // Monsters
				
					
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 1) { // Minigames
					QuickSets.gearUp(c, 0);
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
						
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
						
				} else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);

				}
				break;
			case 225019:				
				if (player.teleTabSelected == 0) { // Monsters
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 2) { // Skilling
					QuickSets.gearUp(c, 0);
		
					
				} else if (player.teleTabSelected == 3) { // Bosses
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					QuickSets.gearUp(c, 0);
				
		
				}else if (player.teleTabSelected == 5) { // Cities
					QuickSets.gearUp(c, 0);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					QuickSets.gearUp(c, 0);
			
				}
				break;
			case 225020:
			
				if (player.teleTabSelected == 0) { // Monsters
		
					
				}else if (player.teleTabSelected == 2) { // Skilling
				//	player.getPA().startTeleport(3040, 4969, 1, "modern", false);
				
				} else if (player.teleTabSelected == 3) { // Bosses
				//	player.getPA().startTeleport(3505, 9494, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
				//	player.getPA().startTeleport(2987, 3698, 0, "modern", false);

			
					
				}else if (player.teleTabSelected == 5) { // Cities
				//	player.getPA().startTeleport(3362, 2987, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
				//	player.getPA().startTeleport(3056, 9562, 0, "modern", false);
				}
				break;
				
			case 225021:
				if (player.teleTabSelected == 0) { // Monsters
		
					
				} else if (player.teleTabSelected == 2) { // Skilling
				
		
			
				} else if (player.teleTabSelected == 3) { // Bosses
					

					
				} else if (player.teleTabSelected == 4) { // Wilderness
			

					
				} else if (player.teleTabSelected == 5) { // Cities
			
					
				} else if (player.teleTabSelected == 6) { // Dungeons
				
		
					//player.getPA().startTeleport(3037, 5765, 0, "modern", false);
				}
				break;
			case 225022://16
				if (player.teleTabSelected == 0) { // Monsters
				} else if (player.teleTabSelected == 2) { // skilling guides
					
				} else if (player.teleTabSelected == 3) { // Bosses

				} else if (player.teleTabSelected == 4) { // Wilderness

				} else if (player.teleTabSelected == 5) { // Cities

				} else if (player.teleTabSelected == 6) { // Dungeons
			
					
				}
				break;
			case 225023://17

				if (player.teleTabSelected == 0) { // Monsters
	
				} else if (player.teleTabSelected == 2) { // Skilling
					
				} else if (player.teleTabSelected == 3) { // Bosses
			
				
				} else if (player.teleTabSelected == 4) { // Wilderness

				} else if (player.teleTabSelected == 5) { // Cities
				
				} else if (player.teleTabSelected == 6) { // Dungeons

				}
				break;
			case 225025://18

				if (player.teleTabSelected == 0) { // Monsters
		
				} else if (player.teleTabSelected == 2) { // Skilling

				} else if (player.teleTabSelected == 3) { // Bosses
			

				} else if (player.teleTabSelected == 5) { // Cities

				} else if (player.teleTabSelected == 6) { //dungeons

					
				}
				break;
		
		
				
		
		}
	}
}
