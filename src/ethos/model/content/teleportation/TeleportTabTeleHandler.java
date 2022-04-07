package ethos.model.content.teleportation;

import ethos.Config;
import ethos.Server;
import ethos.event.impl.WheatPortalEvent;
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

public class TeleportTabTeleHandler {
	
	private Player c;
	
	public TeleportTabTeleHandler(Player player) {
		this.c = player;
	}

	String[] monsterNames = { "@cr33@ @whi@Cows & Chickens@gre@  (1-2)", "@cr33@ @whi@Al Kharid Warriors@gre@  (9)", "@cr33@ @whi@Barbarian Village@gre@  (10)", "@cr33@ @whi@Rock Crabs@gre@  (12)", "@cr33@ @whi@Sand Crabs@gre@  (15)","@cr33@ @whi@Desert Bandits@or2@  (74)", "@cr33@ @whi@Elf Warriors@or2@  (90)", "@cr33@ @whi@Vampyre Juvinates@or2@  (90)",
			"@whi@ @or1@ ", "@whi@ @or1@ ", "@whi@ @or1@", "@whi@ @or1@", "@whi@ @or1@ ", "@whi@ @or1@", "@whi@ @or1@  ", "@whi@ @or1@ ",
			"@whi@ @or1@ ", "@whi@ @or1@ ", "@whi@ @red@ ", "@whi@ @red@ ", "@whi@ @red@ ", "@whi@ @red@ ", "", "", ""};
	
	String[] minigameNames = { "@cr26@ @whi@Duel Arena@gre@ - Staking","@cr9@ @whi@RFD@gre@ - Gloves", "@cr9@ @whi@Pest Control@gre@  (E)", "@cr9@ @whi@Shayzien Assault@or1@  (M)", "@cr9@ @whi@Warriors Guild@or1@  (M)", "@cr9@ @whi@Barrows@or1@  (M)", "@cr9@ @whi@Fight Caves@or2@  (H)",
			"@cr9@ @whi@Mage Arena@or2@  (H)", "@cr9@ @whi@COX@red@ - Advanced", "@cr9@ @whi@TOB@red@ - Advanced", "@cr9@ @whi@Inferno@red@ - Advanced", "@gre@@cr11@ Minigame Guides", "", "", "", "", "", "",
			"", "", "", ""};
	
	String[] skillingNames = {  "@cr40@ @gre@Loyal Skilling Zone", "@cr49@ @whi@Agility", "@cr63@ @whi@Crafting/Hunter", "@cr61@ @whi@Farming/Herblore", "@cr56@ @whi@Fishing/Cooking",
			 "@cr54@ @whi@Mining/Smithing", "@cr48@ @whi@Runecrafting", "@cr51@ @whi@Thieving", "@cr59@ @whi@Woodcutting", "@cr58@ @whi@Infernal Skilling", "@cr60@ @whi@Recourse Area @red@(Wild)", "@gre@@cr11@ Skilling Guides", "",
			"", "", "", "", "", "", "", ""}; 
	/*
	 * "Mining", "Smithing", "Fishing", "Woodcutting (low level)", "Woodcutting Guild", "Farming", "Gnome Agility", "Barbarian Agitity", "Wilderness Agility", "Rooftop Agility", "Thieving", "Slayer", "Hunter", "RCing", 
			"", "", "", "", "", ""		
	 */
	
	String[] bossesNames = {"@cr22@ @whi@Abyssal Sire@or1@  (M)", "@cr22@ @whi@Barrelchest@or1@  (E)", "@cr22@ @whi@Corporeal Beast@red@  (H)", "@cr22@ @whi@Cerberus@or2@  (M)",
			"@cr22@ @whi@Dagonnoth Kings@gre@  (E)", "@cr22@ @whi@Giant Mole@gre@  (E)", "@cr22@ @whi@God Wars Dungeon@or2@  (M)",
			"@cr22@ @whi@Kraken@or2@  (M)", "@cr22@ @whi@Lizardman Shaman@gre@  (E)", "@cr22@ @whi@Therm Smoke Devil@gre@  (E)", "@cr22@ @whi@Vorkath@or2@  (M)", "@cr22@ @whi@Zulrah@or2@  (M)",
			"@cr22@ @whi@Demonic Gorillas@gre@  (M)", "@cr22@ @whi@Kalphite Queen@gre@  (M)", "@cr22@ @whi@Alchemical Hydra @red@ (H)", "@cr22@ @whi@Galvek @red@ (NEW)", "@gre@@cr11@ Bossing Guides", "", "", ""};
			
	String[] wildernessNames = { "@cr21@ @whi@Western Dragons@red@  (12)","@cr21@ @whi@Eastern Dragons@red@  (21)",  "@cr21@ @whi@Ents@red@  (14)", "@cr21@ @whi@Revenant Caves@red@  (40)", "@cr21@ @whi@Demonic Ruins@red@  (46)",
			"@cr21@ @whi@Mage Bank@red@  (55)",  "@cr21@ @whi@Wildy Agility Course@red@  (50)",
			"@cr21@ @whi@Venenatis@red@  (29)", "@cr21@ @whi@Vet'ion@red@  (35)", "@cr21@ @whi@Callisto@red@  (42)", "@cr21@ @whi@Scorpia@red@  (54)", 
			"@cr21@ @whi@Chaos Elemental@red@  (50)", "@cr21@ @whi@Chaos Fanatic@red@  (41)", "@cr21@ @whi@Crazy Archaeologist@red@  (24)", "@cr21@ @whi@King Black Dragon@red@  (42)","@cr21@ @whi@Dusk/Dawn@red@  (44)", "@gre@@cr11@ Wilderness Guides", "",
			"", "", ""};
	
	String[] citiesNames = { "@cr18@ @whi@Al Kharid", "@cr18@ @whi@Ardougne", "@cr18@ @whi@Brimhaven", "@cr18@ @whi@Burthorpe", "@cr18@ @whi@Camelot", "@cr18@ @whi@Canifis",
			"@cr18@ @whi@Catherby", "@cr18@ @whi@Draynor Village", "@cr18@ @whi@Falador", "@cr18@ @whi@Kourend", "@cr18@ @whi@Lumbridge", "@cr18@ @whi@Lletya", "@cr18@ @whi@Mos Le Harmless", "@cr18@ @whi@Pollnivneach",
			"@cr18@ @whi@Rellekka", "@cr18@ @whi@Shilo Village", "@cr18@ @whi@Sophanem", "@cr18@ @whi@Taverley", "@cr18@ @whi@Varrock", "@cr18@ @whi@Yanille"};
	
	String[] donatorNames = { "@blu@@cr4@ Sapphire Zone", "@red@@cr6@ Ruby Zone", "@whi@@cr7@ Diamond Zone", " @cr16@ @yel@Godly Zone (Coming Soon)", "@cr27@ @bla@Ruby Slayer", "@cr26@ @bla@Safe Revenants",
			"@whi@", "@whi@ ", "@whi@", "@whi@", "@whi@", "@whi@", "@whi@", "@whi@",
			"@whi@", "@whi@ ", "@whi@", "@whi@", "@whi@", "@whi@"};
	
	String[] dungeonNames = { "@cr25@ @whi@Edgeville Dungeon@gre@  (E)","@cr25@ @whi@Chasm of Fire@or1@  (M)",
			"@cr25@ @whi@Mithril Dragons@or1@  (M)", "@cr25@ @whi@Brimhaven Dungeon@or1@  (M)", "@cr25@ @whi@Fremennik Dungeon@or1@  (M)", "@cr25@ @whi@Kourend Catacombs@or1@  (M)", "@cr25@ @whi@Karuulm Dungeon@or1@  (M)", "@cr25@ @whi@Lithkren Vault@or1@  (M)", "@cr25@ @whi@Slayer Tower@or1@  (M)", "@cr25@ @whi@Smoke Dungeon@or1@  (M)",
			"@cr25@ @whi@Stronghold Dungeon@or1@  (M)", "@cr25@ @whi@Taverley Dungeon@or1@  (M)", "@cr25@ @whi@Wyvern Cave@red@  (H)", "@cr25@ @whi@Skeletal Wyverns@red@  (H)", "@cr25@ @whi@Cave Horrors@red@  (H)", "@cr25@ @whi@Kalphite Cave@red@  (H)", "@cr25@ @red@Blood Slayer@red@  (@bla@99@red@)", "@gre@@cr11@ Slayer Guides", "", "", "","",""};
	
	
	/*
	 * for(int iD = 47628; iD <= 47648; iD++) {
	 * 20 teleports
	 */
	public void loadMonsterTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(monsterNames[j], ids[j]);
		}
	}
	
	public void loadMinigamesTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(minigameNames[j], ids[j]);
		}
	}
	
	public void loadSkillingTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(skillingNames[j], ids[j]);
		}
	}
	
	public void loadBossesTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(bossesNames[j], ids[j]);
		}
	}
	
	public void loadWildernessTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(wildernessNames[j], ids[j]);
		}
	}
	
	public void loadCitiesTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(citiesNames[j], ids[j]);
		}
	}
	public void loadDungeonsTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(dungeonNames[j], ids[j]);
		}
	}
	public void loadDonatorTab() {
		int[] ids = { 47628, 47629, 47630, 47631, 47632, 47633, 47634, 47635, 47636, 47637, 47638, 47639, 47640, 47641, 47642, 47643, 47644, 47645, 47646, 47647 };
		for (int j = 0; j <= 19; j++) {
			
			c.getPA().sendFrame126(donatorNames[j], ids[j]);
		}
	}
	public void loadTab(Player player, int tab) {
		if (player.teleTabSelected == 0) {
			loadMonsterTab();
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
	
	public boolean teleportCheck(Player player) {
		return true;
	}
	
	public void handleTeleport(Player player, int Id) {
		if (player.inWild() && player.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			player.sendMessage("You can not teleport while above level " + Config.NO_TELEPORT_WILD_LEVEL + " wilderness.");
			return;
		}
		 if (Boundary.isIn(c, Boundary.RAIDS) || (Boundary.isIn(c, Boundary.OLM))) {
			c.getPA().spellTeleport(3306, 5196, c.getRaids().raidLeader.getIndex()*4, true);
			player.sendMessage("@red@You teleport to the Raids lobby, please exit using the stairs.");
			return;
		 }
		 if (Boundary.isIn(c, Boundary.TOB)) {
				c.getPA().spellTeleport(3219, 4460, c.getTob().tobLeader.getIndex()*4, true);
				player.sendMessage("@red@You can't teleport out of Theatre of Blood. Survive or die.");
				return;
			 }
		
		switch(Id) {
			case 185143:
				selection(player, 0); //training
				break;
			case 185144:
				if 	(player.amDonated >=10){
				selection(player, 7); //donator
				} else 
					
					player.sendMessage("@blu@@cr4@ You need to be atleast a Sapphire Donator to view this tab! @cr4@");
				
				break;
			case 185145://dungones
				selection(player, 6);
				break;
			case 185146: //minigames
				selection(player, 1);
				break;
			case 185147:
				selection(player, 2);
				break;
			case 185148:
				selection(player, 3); //bosses
				break;
			case 185149:
				selection(player, 4);
				break;
			case 185150:
				selection(player, 5);
				break;
			
		
			case 185247:
			
				if (!teleportCheck(player))
					return;
			
				if (player.teleTabSelected == 0) { // Training
					player.getPA().startTeleport(3246, 3272, 0, "modern", false);
					
				} else if (player.teleTabSelected == 1) { // Minigames
					player.getPA().startTeleport(3365, 3266, 0, "modern", false); 
					
				} else if (player.teleTabSelected == 2 && player.totalLevel >= 1000) { // Skilling
					player.getPA().startTeleport(3034, 4519, 0, "modern", false);					
				} else if (player.teleTabSelected == 2 && player.totalLevel < 1000) { // Skilling
					player.sendMessage("@red@You must have 1000 total level to teleport here!");
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(3039, 4768, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(2993, 3610, 0, "modern", false);
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3294, 3194, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(3097, 9868, 0, "modern", false);
				} else if (player.teleTabSelected == 7) { // Donator
					player.getPA().startTeleport(2144, 2847, 0, "modern", false);
				}
				break;
			case 185248:
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Training
					player.getPA().startTeleport(3292, 3176, 0, "modern", false);
		
				} else if (player.teleTabSelected == 1) { // Minigames
					RecipeForDisaster rfd = c.createRecipeForDisasterInstance();

					if (c.rfdChat == 1) {
						if (rfd == null) {
							c.sendMessage("We are unable to allow you to start the minigame.");
							c.sendMessage("Too many players.");
							return;
						}

						if (Server.getEventHandler().isRunning(c, "rfd")) {
							c.sendMessage("You're about to fight start the minigame, please wait.");
							return;
						}
						c.getrecipeForDisaster().init();
					} else {
						c.getDH().sendDialogues(58, 4847);
					}
					break;
				} else if (player.teleTabSelected == 2) { // Skilling Agility
					c.getDH().sendDialogues(14401, -1);
					return;
				//	player.getPA().startTeleport(3143, 3442, 0, "modern", false);

					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(1219, 3495, 0, "modern", false);
							
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3349, 3679, 0, "modern", false);
		
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2662, 3306, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(1438, 10096, 3, "modern", false);
					
				} else if (player.teleTabSelected == 7) { // Donator
					
				}
					if (player.amDonated >=100){
					player.getPA().startTeleport(2039, 2847, 0, "modern", false);
				}
		 else 
			player.sendMessage("@red@@cr6@ You need to be atlest a Ruby Donator to access this zone. @cr6@");
				return;
				
			case 185249:	
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Training
					player.getPA().startTeleport(3084, 3417, 0, "modern", false);
			
				} else if (player.teleTabSelected == 1) { // Minigames
					player.getPA().startTeleport(2660, 2648, 0, "modern", false);
			
				} else if (player.teleTabSelected == 2) { // Skilling Crafting
					c.getDH().sendDialogues(14420, -1);
					return;
				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(2968, 4383, 2, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3304, 3657, 0, "modern", false);
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2783, 3183, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(1749, 5330, 0, "modern", false);
				} else if (player.teleTabSelected == 7) { // Donator
					
				}
					if (player.amDonated >=250){
					player.getPA().startTeleport(3116, 3124, 0, "modern", false);
				}
					else 
			player.sendMessage("@whi@@cr7@ You need to be atlest a Diamond Donator to access this zone. @cr7@");
				return;

				
			case 185250:			
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Training
					player.getPA().startTeleport(2675, 3714, 0, "modern", false);
			
				} else if (player.teleTabSelected == 1) { // Minigames
					player.getPA().startTeleport(1461, 3689, 0, "modern", false);
	
				} else if (player.teleTabSelected == 2) { // Skilling farming
					player.getPA().startTeleport(3006, 3376, 0, "modern", false);
				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(1310, 1242, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3119, 3833, 0, "modern", false);
	
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2918, 3545, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(2639, 9520, 0, "modern", false);
					
				} else if (player.teleTabSelected == 7) { // Donator
					player.sendMessage("@red@THIS ZONE IS UNDER CONSTRUCTION!");
				}
				break;
				
			case 185251:
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Training
					player.getPA().startTeleport(1737, 3475, 0, "modern", false);
					
				} else if (player.teleTabSelected == 1) { // Minigames
					player.getPA().startTeleport(2851, 3547, 0, "modern", false);
									
				} else if (player.teleTabSelected == 2) { // Skilling
					c.getDH().sendDialogues(14422, -1);
					return;
				
	
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(1913, 4367, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3288, 3886, 0, "modern", false);
		
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2756, 3478, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(2807, 10002, 0, "modern", false);
					
				} else if (player.teleTabSelected == 7) { // Donator
					if 	(player.amDonated >=100){
					player.getPA().startTeleport(2105, 2848, 0, "modern", false);
					}
				}
				else 
					player.sendMessage("@red@@cr6@ You need to be atlest a Ruby Donator to access this zone. @cr6@");
				break;
				
			case 185252:			
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Training
					player.getPA().startTeleport(3173, 2984, 0, "modern", false);
		
				} else if (player.teleTabSelected == 1) { // Minigames
					player.getPA().startTeleport(3565, 3316, 0, "modern", false);
					player.sendMessage("@or2@<shad=7000>Barrows keys are 1/50 and they appear in inventory, good luck!");
			
				} else if (player.teleTabSelected == 2) { // Skilling
					c.getDH().sendDialogues(14424, -1);
					
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(1761, 5163, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(2539, 4716, 0, "modern", false);
			
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3497, 3492, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(1664, 10049, 0, "modern", false);
					
				} else if (player.teleTabSelected == 7) { // Donator
					if 	(player.amDonated >=50){
						player.getPA().startTeleport(3738, 3962, 0, "modern", false);
					}
					}
					else 
						player.sendMessage("@gre@@cr6@ You must be an Emerald Donator to access this zone. @cr6@");
				
				
				break;
				
			case 185253:
	
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Training
					player.getPA().startTeleport(2344, 3166, 0, "modern", false);
	
				} else if (player.teleTabSelected == 1) { // Minigames
					player.getPA().startTeleport(2444, 5179, 0, "modern", false);
			
					player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.PEST_CONTROL_TELEPORT);
				} else if (player.teleTabSelected == 2) { // Skilling
					player.getPA().startTeleport(3059, 5584, 0, "modern", false);

				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(2881, 5310, 2, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(2998, 3913, 0, "modern", false);
	
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2830, 3438, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(1312, 10225, 0, "modern", false);
				}
				break;
				
			case 185254:
	
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
					player.getPA().startTeleport(3610, 3192, 0, "modern", false);
	
				} else if (player.teleTabSelected == 1) { // Minigames

					player.getPA().startTeleport(2541, 4716, 0, "modern", false);
					
				} else if (player.teleTabSelected == 2) { // Skilling
					c.getDH().sendDialogues(14425, -1);
					return;
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(2278, 10016, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3345, 3754, 0, "modern", false);
				
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3103, 3257, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(1568, 5077, 0, "modern", false);
				}
				break;
			case 185255:
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
					player.getPA().startTeleport(2807, 10002, 0, "modern", false);
		
				} else if (player.teleTabSelected == 1) { // Minigames
					player.getPA().startTeleport(1254, 3568, 0, "modern", false);
								
				} else if (player.teleTabSelected == 2) { // Skilling
					c.getDH().sendDialogues(14426, -1);
					
					return;
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(1550, 3696, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3200, 3794, 0, "modern", false);
			
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2965, 3384, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(3428, 3538, 0, "modern", false);
				}
				break;
				
			case 186000:
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
		
				} else if (player.teleTabSelected == 1) { // Minigames
		
					player.getPA().startTeleport(3668, 3219, 0, "modern", false);
		
				} else if (player.teleTabSelected == 2) { // Skilling
					player.getPA().startTeleport(2534, 5090, 0, "modern", false);

	
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(2381, 9452, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3325, 3845, 0, "modern", false);
	
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(1629, 3646, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(3294, 9374, 0, "modern", false);
	
				}
				break;
				
			case 186001:			
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
					
				} else if (player.teleTabSelected == 1) { // Minigames
					if (player.hasSacrificedFcape = true) {
					}
					player.getPA().startTeleport(2497, 5113, 0, "modern", false);
	
					
				} else if (player.teleTabSelected == 2) { // Skilling
					player.getPA().startTeleport(3186, 3957, 0, "modern", false);
				
				
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(2272, 4050, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3233, 3945, 0, "modern", false);
			
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3235, 3218, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(2453, 9834, 0, "modern", false);
			
				}
				break;
				
			case 186002:
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
				
					
				} else if (player.teleTabSelected == 2) { // Skilling
					player.getPA().sendFrame126("http://revelation-rsps.com/forums/index.php?/forum/12-guides/", 12000);
				} else if (player.teleTabSelected == 1) { // Minigames
	
		
					player.getPA().sendFrame126("http://revelation-rsps.com/forums/index.php?/forum/12-guides/", 12000);
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(2202, 3056, 0, "modern", false);
						
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3300, 3921, 0, "modern", false);
		
					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2342, 3172, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(2884, 9799, 0, "modern", false);

				}
				break;
			case 186003:				
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
			
					
				} else if (player.teleTabSelected == 2) { // Skilling
				//	player.getPA().startTeleport(3060, 5586, 0, "modern", false);
		
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(2131, 5646, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(2978, 3833, 0, "modern", false);
				
		
				}else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3680, 2982, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(3603, 10224, 0, "modern", false);
			
				}
				break;
			case 186004:
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
		
					
				}else if (player.teleTabSelected == 2) { // Skilling
				//	player.getPA().startTeleport(3040, 4969, 1, "modern", false);
				
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(3505, 9494, 0, "modern", false);
					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(2987, 3698, 0, "modern", false);

			
					
				}else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3362, 2987, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(3056, 9562, 0, "modern", false);
				}
				break;
				
			case 186005:
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
		
					
				} else if (player.teleTabSelected == 2) { // Skilling
				
		
			
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(1356, 10259, 0, "modern", false);

					
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(2997, 3854, 0, "modern", false);

					
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2643, 3677, 0, "modern", false);
					
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(3747, 9374, 0, "modern", false);
		
					//player.getPA().startTeleport(3037, 4765, 0, "modern", false);
				}
				break;
			case 186006://16
				//player.sendMessage("teletabsel "  + player.teleTabSelected);
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
				} else if (player.teleTabSelected == 2) { // skilling guides
					
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().startTeleport(3273, 5072, 0, "modern", false);
				
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().startTeleport(3098, 3878, 0, "modern", false);
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2864, 2968, 0, "modern", false);
				} else if (player.teleTabSelected == 6) { // Dungeons
					player.getPA().startTeleport(3505, 9494, 0, "modern", false);
					
				}
				break;
			case 186007://17
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
	
				} else if (player.teleTabSelected == 2) { // Skilling
					//player.getPA().startTeleport(x, y, 0, "modern", false);// change
				} else if (player.teleTabSelected == 3) { // Bosses
					player.getPA().sendFrame126("http://revelation-rsps.com/forums/index.php?/forum/12-guides/", 12000);
				
					//player.getPA().startTeleport(2202, 3056, 0, "modern", false);
				} else if (player.teleTabSelected == 4) { // Wilderness
					player.getPA().sendFrame126("http://revelation-rsps.com/forums/index.php?/forum/12-guides/", 12000);
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3296, 2784, 0, "modern", false);
				} else if (player.teleTabSelected == 6) { // Dungeons
					if (c.playerLevel[Skill.SLAYER.getId()] == 99) {
					player.getPA().startTeleport(3061, 4879, 0, "modern", false);
					}
					else
					{
						player.sendMessage("@red@You need 99 Slayer to use this teleport.");
					}
				}
				break;
			case 186008://18
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 0) { // Monsters
		
				} else if (player.teleTabSelected == 2) { // Skilling
					player.getPA().startTeleport(2873, 9847, 0, "modern", false);
				} else if (player.teleTabSelected == 3) { // Bosses
			

				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(2896, 3440, 0, "modern", false);
				} else if (player.teleTabSelected == 6) { //dungeons
					player.getPA().sendFrame126("http://revelation-rsps.com/forums/index.php?/forum/12-guides/", 12000);
					
				}
				break;
			case 186009://19
				if (!teleportCheck(player))
					return;
				if (player.teleTabSelected == 2) { // Skilling
					//player.getPA().startTeleport(x, y, 0, "modern", false);// change
				} else if (player.teleTabSelected == 3) { // Bosses
					
			
					//player.getPA().startTeleport(3039, 4788, 0, "modern", false);
				} else if (player.teleTabSelected == 5) { // Cities
					player.getPA().startTeleport(3212, 3429, 0, "modern", false);
				
				}
				break;
			case 186010://20 - not used ever
		 if (player.teleTabSelected == 5) { // Cities
			player.getPA().startTeleport(2611, 3093, 0, "modern", false);
		 }
				break;
				
		
		}
	}
}
