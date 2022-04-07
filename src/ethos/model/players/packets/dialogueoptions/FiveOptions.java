package ethos.model.players.packets.dialogueoptions;

import ethos.Server;
import ethos.event.impl.WheatPortalEvent;
import ethos.model.content.DiceHandler;
import ethos.model.content.LootValue;
import ethos.model.content.QuickSets;
import ethos.model.content.RecolourGraceful;
import ethos.model.content.StatReset;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.achievement_diary.kandarin.KandarinDiaryEntry;
import ethos.model.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import ethos.model.content.teleportation.TeleportationDevice;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerAssistant;
import ethos.model.players.combat.Degrade;
import ethos.model.players.combat.Degrade.DegradableItem;

/*
 * @author Matt
 * Five Option Dialogue actions
 */

public class FiveOptions {
	/*
	 * Handles all first options on 'Five option' dialogues.
	 */

	static NPC OLMLET = NPCHandler.getNpc(7520);
	public static void handleOption1(Player c) {

			switch (c.dialogueAction) {
			case 851:
				c.getDH().sendDialogues(854, 7520);
				break;
				
				
			case 6966:
				 if(Boundary.isIn(c, Boundary.BOSSINGZONE1)) {
				Server.npcHandler.spawnNpc(c, 6615, 1997, 2828, 0, 1, 300, 150, 150, 300, true, true);
				//c.getDH().sendStatement("You have spawned @red@Venenatis.");
				 } else  if(Boundary.isIn(c, Boundary.BOSSINGZONE2)) {
				Server.npcHandler.spawnNpc(c, 6615, 1996, 2865, 0, 1, 300, 150, 150, 300, true, true);
				 }
				 break;
				/*
				 * Blood Money Exchange Options
				*/
				/*
				 * Pest Control
				*/
				
				
			case 6902:
					if (c.getItems().playerHasItem(13307, 1000)) {
					c.pcPoints +=50;
					c.getItems().deleteItem(13307, 1000);
					c.getDH().sendStatement("You have exchanged <col=FF0000>1000</col> blood money for <col=0042FF>50</col> Pc Points.");
					c.gfx0(1002);
			    	Achievements.increase(c, AchievementType.BLOODMONEY, 1000);
					}
					else
						c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
					break;
			case 6903:
				if (c.getItems().playerHasItem(13307, 5000)) {
				c.pcPoints +=250;
				c.getItems().deleteItem(13307, 5000);
				c.getDH().sendStatement("You have exchanged <col=FF0000>5000</col> blood money for <col=0042FF>250</col> Pc Points.");
				c.gfx0(1002);
				Achievements.increase(c, AchievementType.BLOODMONEY, 5000);
				}
				else
					c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
				break;
			case 6904:
				if (c.getItems().playerHasItem(13307, 10000)) {
				c.pcPoints +=500;
				c.getItems().deleteItem(13307, 10000);
				c.getDH().sendStatement("You have exchanged <col=FF0000>10000</col> blood money for <col=0042FF>500</col> Pc Points.");
				c.gfx0(1002);
				Achievements.increase(c, AchievementType.BLOODMONEY, 10000);
				}
				else
					c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
				break;
			case 6905:
				if (c.getItems().playerHasItem(13307, 50000)) {
				c.pcPoints +=2500;
				c.getItems().deleteItem(13307, 50000);
				c.getDH().sendStatement("You have exchanged <col=FF0000>50000</col> blood money for <col=0042FF>2500</col> Pc Points.");
				c.gfx0(1002);
				Achievements.increase(c, AchievementType.BLOODMONEY, 50000);
				}
				else
					c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
				break;
			
					
					
					
					
					
				
				//Armadyl
			case 1056:
				
				c.getPA().startTeleport(2839, 5294, 2, "modern", false);
				
				break;
			case 855: //Tekton
				//OLMLET.facePlayer(c.getIndex());
				//HISTORIAN.startAnimation(1818);
				//HISTORIAN.gfx0(343);
				//OLMLET.forceChat("Off to Tekton you go " + Misc.formatPlayerName(c.playerName) + "!");
				//c.getPA().movePlayer(3309, 5277, 1);
				c.getPA().startTeleport(3309, 5277, 1, "modern", false);
				c.getPA().closeAllWindows();
				break;
			case 14403:
				c.getPA().startTeleport(3036, 3340, 0, "modern", false);
				c.sendMessage("You will gain XP after each lap");
				c.getPA().closeAllWindows();
				break;
				
			case 820:
				QuickSets.gearUp(c, 0);
				break;
			case 821:
				QuickSets.gearUp(c, 3);
				break;
		
		case 801:
			c.getDH().sendDialogues(802, 6773);
			break;
			
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getRingOfLifeEffect()) {
				c.setRingOfLifeEffect(true);
				c.sendMessage("You have toggled on the ROL effect.");
			} else {
				c.setRingOfLifeEffect(false);
				c.sendMessage("You have toggled off the ROL effect.");
			}
			break;

			
		case 141314:
			c.getPA().startTeleport(1271, 3556, 0, "modern", false);
			c.sendMessage("You have teleported to Mount Quidamortem");
			c.getPA().closeAllWindows();
			break;
			
		case 193193:
			c.safeBoxSlots = 1;
			c.sendMessage("You have set your destination to [ @red@Piscarlarius House@bla@ ].");
			c.getPA().closeAllWindows();
			break;

		case 14402: //agility
			c.getPA().startTeleport(3105, 3279, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;
			
			
		case 14420: //crafting
			c.getPA().startTeleport(1504, 3420, 0, "modern", false);			
			c.sendMessage("Welcome to Lands End.");
			c.getPA().closeAllWindows();
			break;	
			
		case 14421: //Farming
			c.getPA().startTeleport(3052, 3304, 0, "modern", false);			
			c.sendMessage("Welcome to the Falador Farm.");
			c.getPA().closeAllWindows();
			break;	
			
			case 14422: //Fishing
				c.getPA().startTeleport(3243, 3158, 0, "modern", false);			
				c.sendMessage("Welcome to the Lumbridge Swamp.");
				c.getPA().closeAllWindows();
				break;	
			case 14423: //Hunter
				c.getPA().startTeleport(1580, 3433, 0, "modern", false);			
				c.sendMessage("Welcome to Feldip Hills.");
				c.getPA().closeAllWindows();
				break;	
			case 14424: //Mining Smithing
				c.getPA().startTeleport(3227, 3149, 0, "modern", false);			
				c.sendMessage("Welcome to the Lumbridge Mine.");
				c.getPA().closeAllWindows();
				break;	
			case 14425: //Thieving
				//c.getPA().startTeleport(3096, 3490, 0, "modern", false);					
			//	c.sendMessage("Welcome to the Home Stalls.");
				c.getPA().closeAllWindows();
				break;	
			case 14426: //Woodcutting
				c.getPA().startTeleport(2725, 3485, 0, "modern", false);			
				c.sendMessage("Welcome to Seer's Village.");
				c.getPA().closeAllWindows();
				break;	

		case 1401:
			StatReset.execute(c, 0);
			break;

		case 1404:
			StatReset.execute(c, 5);
			break;

		case 2002:
			c.getPA().spellTeleport(3565, 3308, 0, false);
			break;
		case 2003:
			c.getPA().spellTeleport(3218, 9622, 0, false);
			break;

		case 61: // Resource Area
			TeleportationDevice.startTeleport(c, 0);
			break;
			
		case 76: //Max cape
				c.getPA().closeAllWindows();
				if (c.inWild()) {
					return;
				}
				if (c.playerMagicBook == 0) {
					c.playerMagicBook = 1;
					c.setSidebarInterface(6, 838);
					c.autocasting = false;
					c.sendMessage("An ancient wisdomin fills your mind.");
					c.getPA().resetAutocast();
				} else if (c.playerMagicBook == 1) {
					c.sendMessage("You switch to the lunar spellbook.");
					c.setSidebarInterface(6, 29999);
					c.playerMagicBook = 2;
					c.autocasting = false;
					c.autocastId = -1;
					c.getPA().resetAutocast();
				} else if (c.playerMagicBook == 2) {
					c.setSidebarInterface(6, 938);
					c.playerMagicBook = 0;
					c.autocasting = false;
					c.sendMessage("You feel a drain on your memory.");
					c.autocastId = -1;
					c.getPA().resetAutocast();
				}
			break;
			
		case 1393:
			LootValue.configureValue(c, "setvalue", 100000);
			break;
			
		case 55:
			RecolourGraceful.COLOR = "PURPLE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		case 56:
			RecolourGraceful.COLOR = "GREEN";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		case 711:
			c.getDH().sendDialogues(712, 306);
			break;
			
		case 78: //Max cape crafting guild teleport
			c.getPA().startTeleport(2936, 3283, 0, "modern", false);
			c.getPA().closeAllWindows();
			break;
		}

		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6737, 1) && c.getItems().playerHasItem(995, 20_000_000)) {
				c.getItems().deleteItem(6737, 1);
				c.getItems().deleteItem(995, 20_000_000);
				c.refreshQuestTab(0);
				c.getItems().addItem(11773, 1);
				c.sendMessage("You imbue your berserker ring for the cost of @blu@20m@bla@.");
				c.getPA().removeAllWindows();
				Achievements.increase(c, AchievementType.IMBUERING, 1);
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 20M and a Berserker ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 135) {
			if (c.getItems().playerHasItem(12605, 1) && c.getItems().playerHasItem(995, 20_000_000)) {
				c.getItems().deleteItem(12605, 1);
				c.getItems().deleteItem(995, 20_000_000);
				c.refreshQuestTab(0);
				c.getItems().addItem(12692, 1);
				c.sendMessage("You imbue your treasonous ring for the cost of @blu@20M@bla@.");
				Achievements.increase(c, AchievementType.IMBUERING, 1);
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 20M and a Treasonous ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 14400) {
			c.getPA().startTeleport(2474, 3438, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			}
		if (c.dialogueAction == 131) {
			c.getDH().sendDialogues(659, 306);
			return;
		}
		if (c.dialogueAction == 128) {
			c.getFightCave().create(1);
			return;
		}
		if (c.teleAction == 17) {
			c.getPA().spellTeleport(2808, 10002, 0, false);
			return;
		}
		if (c.dialogueAction == 123) {
			DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length == 0) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[0].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(614, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 14) {
			c.getPA().spellTeleport(1923, 4367, 0, false);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 12) {
			c.getPA().spellTeleport(3302, 9361, 0, false);
		}
		if (c.teleAction == 11) {
			c.getPA().spellTeleport(3228, 9392, 0, false);
		}
		if (c.teleAction == 10) {
			c.getPA().spellTeleport(2705, 9487, 0, false);
		}
		if (c.teleAction == 9) {
			c.getPA().spellTeleport(3226, 3263, 0, false);
		}
		if (c.teleAction == 8) {
			c.getPA().spellTeleport(3293, 3178, 0, false);
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(3118, 9851, 0, false);
		}
		if (c.teleAction == 15) { //Chickens
			c.getPA().spellTeleport(3236, 3295, 0, false);
		}
		if (c.teleAction == 99) { //Crash island
			c.getPA().spellTeleport(2897, 2725, 0, false);
		}
		if (c.teleAction == 1) { //Lava Dragons
			c.getPA().spellTeleport(2977, 3603, 0, false);
		} else if (c.teleAction == 2) { //Brimhaven Dungeon
			c.getPA().spellTeleport(1571, 3656, 0, false);
		} else if (c.teleAction == 200) { //Raids
			 c.getPA().spellTeleport(3301, 5192, 0, false);
		} else if (c.teleAction == 147258) { //Varrock
			 c.getPA().spellTeleport(3213, 3423, 0, false);
		} else if (c.teleAction == 258369) { //Ardy
			 c.getPA().spellTeleport(2653, 3283, 0, false);
			 c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.TELEPORT_ARDOUGNE);
		} else if (c.teleAction == 201) { //Duel Arena
			 c.getPA().spellTeleport(3367, 3266, 0, false);
		} else if (c.teleAction == 3) { //King Black Dragon
			c.getPA().spellTeleport(3005, 3850, 0, false);
		} else if (c.teleAction == 33) { //Dagannoth Kings
			c.getPA().spellTeleport(1912, 4367, 0, false);
		} else if (c.teleAction == 3434) { //Vet'ion
			c.getPA().spellTeleport(3200, 3794, 0, false);
		} else if (c.teleAction == 1337) { //Land's End
			c.getPA().spellTeleport(1504, 3419, 0, false);
		} else if (c.teleAction == 4) {
			// varrock wildy
			c.getPA().spellTeleport(3025, 3379, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(3046, 9779, 0, false);
		} else if (c.teleAction == 2000) {
			// lum
			c.getPA().spellTeleport(3222, 3218, 0, false);// 3222 3218
		} else {
			int actionButtonId = 0;
			DiceHandler.handleClick(c, actionButtonId);
		}
		if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2845, 4832, 0, false);
			c.dialogueAction = -1;

		} else if (c.dialogueAction == 11) {
			c.getPA().spellTeleport(2786, 4839, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			c.getPA().spellTeleport(2398, 4841, 0, false);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 2nd options on 'Five option' dialogues.
	 */
	public static void handleOption2(Player c) {

		switch (c.dialogueAction) {
		
		case 6902:
			if (c.getItems().playerHasItem(13307, 1000)) {
			c.getSlayer().setPoints(c.getSlayer().getPoints() + 50);
			c.getItems().deleteItem(13307, 1000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>1000</col> blood money for <col=0042FF>50</col> Slayer Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6903:
			if (c.getItems().playerHasItem(13307, 5000)) {
				c.getSlayer().setPoints(c.getSlayer().getPoints() + 250);
			c.getItems().deleteItem(13307, 5000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>5000</col> blood money for <col=0042FF>250</col> Slayer Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6904:
			if (c.getItems().playerHasItem(13307, 10000)) {
				c.getSlayer().setPoints(c.getSlayer().getPoints() + 550);
			c.getItems().deleteItem(13307, 10000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>10000</col> blood money for <col=0042FF>550</col> Slayer Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6905:
			if (c.getItems().playerHasItem(13307, 50000)) {
			c.getSlayer().setPoints(c.getSlayer().getPoints() + 3000);
			c.getItems().deleteItem(13307, 50000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>50000</col> blood money for <col=0042FF>3000</col> Slayer Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		
		
		case 6966:
			 if(Boundary.isIn(c, Boundary.BOSSINGZONE1)) {
			Server.npcHandler.spawnNpc(c, 6609, 1997, 2828, 0, 1, 250, 200, 200, 200, true, true);
			c.getDH().sendStatement("You have spawned @red@Callisto.");
			 } else  if(Boundary.isIn(c, Boundary.BOSSINGZONE2)) {
			Server.npcHandler.spawnNpc(c, 6609, 1996, 2865, 0, 1, 250, 200, 200, 200, true, true);
			 }
			 
			break;
		
		
		
		
		//Bandos
	case 1056:
		c.getPA().startTeleport(2862, 5354, 2, "modern", false);
		break;
		
		case 851:
			//c.getDH().sendDialogues(856, 7520);
			c.getShops().openShop(118);
			c.sendMessage("You currently have @red@"+c.getRaidPoints()+" @bla@Raid Points!");
			//c.sendMessage("A raids shop will be available shortly!");
			//c.getPA().closeAllWindows();
			break;
		
		case 855: //Skeletal mystics
			//OLMLET.facePlayer(c.getIndex());
			//HISTORIAN.startAnimation(1818);
			//HISTORIAN.gfx0(343);
			//OLMLET.forceChat("Off to Skeletal Mystics you go " + Misc.formatPlayerName(c.playerName) + "!");
			//c.getPlayerAssistant().movePlayer(3343, 5248, 1);
			c.getPA().startTeleport(3343, 5248, 1, "modern", false);
			c.getPA().closeAllWindows();
			break;
		case 14403:
			c.getPA().startTeleport(2729, 3488, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;
			
		case 820:
			QuickSets.gearUp(c, 1);
			break;
		case 14420: //crafting
			c.getPA().startTeleport(3740, 3811, 0, "modern", false);			
			c.sendMessage("Welcome to Fossil Island.");
			c.getPA().closeAllWindows();
			break;	
		case 14421: //Farming
			c.getPA().startTeleport(2807, 3464, 0, "modern", false);			
			c.sendMessage("Welcome to the Catherby Farm.");
			c.getPA().closeAllWindows();
			break;	
		case 14422: //Fishing
			c.getPA().startTeleport(2902, 3114, 0, "modern", false);			
			c.sendMessage("Welcome to the Karamja Island.");
			c.getPA().closeAllWindows();
			break;	
		case 14423: //Hunter
			c.getPA().startTeleport(2592, 4321, 0, "modern", false);			
			c.sendMessage("Welcome to Puro Puro.");
			c.getPA().closeAllWindows();
			break;	
		case 14424: //Mining Smithing
			c.getPA().startTeleport(2827, 2996, 0, "modern", false);			
			c.sendMessage("Welcome to Shilo Village.");
			c.getPA().closeAllWindows();
			break;	
		case 14425: //Thieving
			c.getPA().startTeleport(2662, 3306, 0, "modern", false);			
			c.sendMessage("Welcome to the Ardougne Stalls.");		
			c.getPA().closeAllWindows();
			break;	
		case 14426: //Woodcutting
			c.getPA().startTeleport(3087, 3238, 0, "modern", false);			
			c.sendMessage("Welcome to Draynor Village.");
			c.getPA().closeAllWindows();
			break;	
		case 821:
			QuickSets.gearUp(c, 4);
			break;
	
		case 801:
			c.getDH().sendDialogues(805, 6773);
			break;
			
		case 141314:
			c.getPA().startTeleport(1310, 3613, 0, "modern", false);
			c.sendMessage("You have teleported to Xeric's Shrine");
			c.getPA().closeAllWindows();
			break;
			
		case 193193:
			c.safeBoxSlots = 2;
			c.sendMessage("You have set your destination to [ @red@Hosidius House@bla@ ].");
			c.getPA().closeAllWindows();
			break;
		
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getFishingEffect()) {
				c.setFishingEffect(true);
				c.sendMessage("You have toggled on the Fishing effect.");
			} else {
				c.setFishingEffect(false);
				c.sendMessage("You have toggled off the Fishing effect.");
			}
			break;
		
	case 711:
		c.getDH().sendDialogues(713, 306);
		break;
		
		

		case 14402:
			c.getPA().startTeleport(3273, 3196, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;

		case 1401:
			StatReset.execute(c, 2);
			break;

		case 1404:
			StatReset.execute(c, 4);
			break;
		case 2002:
			c.getPA().spellTeleport(2847, 3543, 0, false);
			c.sendMessage("@blu@Use the animators to gain tokens, then head upstairs to the cyclops.");
			break;
		case 2003:

			break;

		case 61: // Kbd Lair
			TeleportationDevice.startTeleport(c, 1);
			break;
			
		case 76: //Max cape
			c.getDH().sendDialogues(78, 306);
			break;
			
		case 78: //Max cape teleport
			PlayerAssistant.ringOfCharosTeleport(c);
			c.getPA().closeAllWindows();
			break;
			
		case 1393:
			LootValue.configureValue(c, "setvalue", 500000);
			break;
			
		case 55:
			RecolourGraceful.COLOR = "BLUE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		case 56:
			RecolourGraceful.COLOR = "WHITE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
		}
		if (c.dialogueAction == 14400) {
		c.getPA().startTeleport(2551, 3555, 0, "modern", false);
		c.sendMessage("You will gain XP after each lap");
		c.getPA().closeAllWindows();
	}

		if (c.teleAction == 17) {
			c.getPA().spellTeleport(2444, 9825, 0, false);
			return;
		}
		if (c.teleAction == 1337) { //Skillers Cove
			c.getPA().spellTeleport(1721, 3464, 0, false);
			return;
		}
		if (c.teleAction == 99) { //bobs island
			c.getPA().spellTeleport(2525,4776, 0, false);
		}
		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6733, 1) && c.getItems().playerHasItem(995, 20_000_000)) {
				c.getItems().deleteItem(6733, 1);
				c.getItems().deleteItem(995, 20_000_000);
				c.refreshQuestTab(0);
				c.getItems().addItem(11771, 1);
				c.sendMessage("You imbue your archer ring for the cost of @blu@20M@bla@.");
				c.getPA().removeAllWindows();
				Achievements.increase(c, AchievementType.IMBUERING, 1);
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 20M and an Archer ring to do this.");
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
			}
			return;
		}
		if (c.dialogueAction == 135) {
			if (c.getItems().playerHasItem(12603, 1) && c.getItems().playerHasItem(995, 20_000_000)) {
				c.getItems().deleteItem(12603, 1);
				c.getItems().deleteItem(995, 20_000_000);
				c.refreshQuestTab(0);
				c.getItems().addItem(12691, 1);
				c.sendMessage("You imbue your treasonous ring for the cost of @blu@20M@bla@.");
				c.getPA().removeAllWindows();
				Achievements.increase(c, AchievementType.IMBUERING, 1);
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 20M and a Treasonous ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 131) {
			c.getDH().sendDialogues(652, 306);
			return;
		}
		if (c.teleAction == 16) {
			c.getPA().startTeleport(3077, 3910, 0, "modern", false);
			return;
		}
		if (c.teleAction == 15) {
			c.getPA().startTeleport(3253, 3266, 0, "modern", false);
			return;
		}
		if (c.teleAction == 2) { //Taverly Dungeon
			c.getPA().spellTeleport(1662, 3529, 0, false);
		}
		if (c.teleAction == 33) { //Barrelchest
			c.getPA().spellTeleport(1229, 3497, 0, false);
		}
		if (c.teleAction == 3434) { //Venenatis
			c.getPA().spellTeleport(3339, 3756, 0, false);
		}
		if (c.teleAction == 201) { //Shayzien Assault
			c.getPA().spellTeleport(1461, 3689, 0, false);
		}
		if (c.dialogueAction == 128) {
			c.getFightCave().create(2);
			return;
		}
		if (c.dialogueAction == 123) {
			DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length < 2) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[1].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(616, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 14) {
			c.getPA().spellTeleport(3292, 3648, 0, false);
			c.sendMessage("@blu@The kraken is roughly 20 steps south-west of this location.");
			c.dialogueAction = -1;
			c.teleAction = -1;
			return;
		}
		if (c.teleAction == 12) {
			c.getPA().spellTeleport(2908, 9694, 0, false);
		}
		if (c.teleAction == 11) {
			c.getPA().spellTeleport(3237, 9384, 0, false);
		}
		if (c.teleAction == 10) {
			c.getPA().spellTeleport(3219, 9366, 0, false);
		}
		if (c.teleAction == 9) {
			c.getPA().spellTeleport(2916, 9800, 0, false);
		}
		if (c.teleAction == 8) {
			c.getPA().spellTeleport(2903, 9849, 0, false);
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(2859, 9843, 0, false);
		}
		if (c.teleAction == 3) {
			// kbd
			// c.sendMessage("King Black Dragon has been disabled.");
			c.getPA().spellTeleport(3262, 3929, 0, false);
		}
		// c.getPA().closeAllWindows();
		/*
		 * if (c.teleAction == 1) { //rock crabs c.getPA().spellTeleport(2676, 3715, 0); } else if (c.teleAction == 2) { //taverly dungeon c.getPA().spellTeleport(2884, 9798, 0); }
		 * else if (c.teleAction == 3) { //kbd c.getPA().spellTeleport(3007, 3849, 0); } else if (c.teleAction == 4) { //west lv 10 wild c.getPA().spellTeleport(2979, 3597, 0); }
		 * else if (c.teleAction == 5) { c.getPA().spellTeleport(3079,9502,0); }
		 */
		if (c.teleAction == 1) {
			// West drags
			c.getPA().spellTeleport(3357, 3674, 0, false);
		} else if (c.teleAction == 200) {
			// pest control
			 c.getPA().spellTeleport(2660, 2648, 0, false);
		} else if (c.teleAction == 147258) { //Fally
			 c.getPA().spellTeleport(2965, 3377, 0, false);
			c.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.TELEPORT_TO_FALADOR);
		} else if (c.teleAction == 258369) { //Cammy
			 c.getPA().spellTeleport(2725, 3491, 0, false);
			 c.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CAMELOT_TELEPORT);
		} else if (c.teleAction == 4) {
			// graveyard
			c.getPA().spellTeleport(3043, 9779, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(3079, 9502, 0, false);

		} else if (c.teleAction == 2000) {
			c.getPA().spellTeleport(3210, 3424, 0, false);// 3210 3424
		} else if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2796, 4818, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 11) {
			c.getPA().spellTeleport(2527, 4833, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			c.getPA().spellTeleport(2464, 4834, 0, false);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 3rd options on 'Five option' dialogues.
	 */
	public static void handleOption3(Player c) {

		switch (c.dialogueAction) {
		
		case 6966:
			 if(Boundary.isIn(c, Boundary.BOSSINGZONE1)) {
			Server.npcHandler.spawnNpc(c, 6610, 1997, 2828, 0, 1, 300, 200, 200, 250, true, true);
			//c.getDH().sendStatement("You have spawned @red@Venenatis.");
			 } else  if(Boundary.isIn(c, Boundary.BOSSINGZONE2)) {
			Server.npcHandler.spawnNpc(c, 6610, 1996, 2865, 0, 1, 300, 200, 200, 250, true, true);
			 }
			 
			break;
		
		
		case 6902:
			if (c.getItems().playerHasItem(13307, 1000)) {
			c.bossPoints +=15;
			c.getItems().deleteItem(13307, 1000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>1000</col> blood money for <col=0042FF>15</col> Boss Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6903:
			if (c.getItems().playerHasItem(13307, 5000)) {
			c.bossPoints +=75;
			c.getItems().deleteItem(13307, 5000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>5000</col> blood money for <col=0042FF>75</col> Boss Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6904:
			if (c.getItems().playerHasItem(13307, 10000)) {
			c.bossPoints +=165;
			c.getItems().deleteItem(13307, 10000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>10000</col> blood money for <col=0042FF>165</col> Boss Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6905:
			if (c.getItems().playerHasItem(13307, 50000)) {
			c.bossPoints +=850;
			c.getItems().deleteItem(13307, 50000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>50000</col> blood money for <col=0042FF>850</col> Boss Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		
		
		
		
		
		
		
		
		
		
		//Saradomin
		case 1056:
			c.getPA().startTeleport(2902, 5265, 0, "modern", false);
			break;
			
		case 851:
			c.getDH().sendDialogues(852, 7520);
			break;
		case 14421: //Farming
			c.getPA().startTeleport(3800, 2836, 0, "modern", false);			
			c.sendMessage("Welcome to Harmony.");
			c.getPA().closeAllWindows();
			break;	
		case 14422: //Fishing
			c.getPA().startTeleport(2593, 3415, 0, "modern", false);			
			c.sendMessage("Welcome to the Fishing Guild.");
			c.getPA().closeAllWindows();
			break;	
		case 14424: //Mining Smithing
			c.getPA().startTeleport(3046, 9767, 0, "modern", false);			
			c.sendMessage("Welcome to the Mining Guild.");
			c.getPA().closeAllWindows();
			break;	
		case 14426: //Woodcutting
			c.getPA().startTeleport(1659, 3504, 0, "modern", false);			
			c.sendMessage("Welcome to the Woodcuttin Guild.");
			c.getPA().closeAllWindows();
			break;	
		case 78:
			if (WheatPortalEvent.xLocation > 0 && WheatPortalEvent.yLocation > 0) {
				c.getPA().spellTeleport(WheatPortalEvent.xLocation + 1, WheatPortalEvent.yLocation + 1, 0, false);
			} else {
				c.sendMessage("There is currently no portal available, wait 5 minutes.");
				return;
			}
			break;
		case 14403:
			c.getPA().startTeleport(2625, 3678, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;
		case 14425: //Thieving
			c.getPA().startTeleport(3233, 2909, 0, "modern", false);			
			c.sendMessage("Welcome to Pyramid Plunder.");

			c.getPA().closeAllWindows();
			break;	
		case 820:
			QuickSets.gearUp(c, 2);
			break;

		case 821:
			QuickSets.gearUp(c, 5);
			break;
		case 801:
			c.getDH().sendDialogues(806, 6773);
			break;
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getMiningEffect()) {
				c.setMiningEffect(true);
				c.sendMessage("You have toggled on the Mining effect.");
			} else {
				c.setMiningEffect(false);
				c.sendMessage("You have toggled off the Mining effect.");
			}
			break;
		
	case 711:
		c.getDH().sendDialogues(714, 306);
		break;
		
	case 141314:
		c.getPA().startTeleport(1676, 3561, 0, "modern", false);
		c.sendMessage("You have teleported to the Hosidius House");
		c.getPA().closeAllWindows();
		break;
		
	case 193193:
		c.safeBoxSlots = 3;
		c.sendMessage("You have set your destination to [ @red@Shayzien House@bla@ ].");
		c.getPA().closeAllWindows();
		break;
		
	case 14420: //crafting
		c.getPA().startTeleport(2933, 3290, 0, "modern", false);			
		c.sendMessage("Welcome to the Crafting Guild.");
		c.getPA().closeAllWindows();
		break;	

		case 14402://agility
			c.getPA().startTeleport(3222, 3414, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;

		case 1401:
			StatReset.execute(c, 1);
			break;

		case 1404:
			StatReset.execute(c, 6);
			break;

		case 2002:
			c.getPA().spellTeleport(3106, 3959, 0, false);
			break;
		case 2003:

			break;

		case 61: // GWD + 10 kc
			c.getDH().sendDialogues(1056, -1);
			break;
			
		case 76: //Max cape
			c.getDH().sendDialogues(77, 306);
			break;
			
		case 56:
			RecolourGraceful.COLOR = "DARK_BLUE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
		//}
			
		case 1393:
			LootValue.configureValue(c, "setvalue", 1000000);
			break;
			
		case 55:
			RecolourGraceful.COLOR = "GOLD";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		/*case 56: //recolor graceful
			
			break;*/

		}
		if (c.dialogueAction == 14400) {
			c.getDH().sendDialogues(14402, -1);
		}

		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6731, 1) && c.getItems().playerHasItem(995, 20_000_000)) {
				c.getItems().deleteItem(6731, 1);
				c.getItems().deleteItem(995, 20_000_000);
				c.refreshQuestTab(0);
				c.getItems().addItem(11770, 1);
				c.sendMessage("You imbue your seers ring for the cost of @blu@20M@bla@.");
				Achievements.increase(c, AchievementType.IMBUERING, 1);
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 20M and n Seers ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 131) {
			c.getDH().sendDialogues(661, 306);
			return;
		}
		if (c.dialogueAction == 128) {
			c.getFightCave().create(3);
			return;
		}
		if (c.teleAction == 17) {
			c.getPA().spellTeleport(1746, 5323, 0, false);
			return;
		}
		if (c.dialogueAction == 123) {
			DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length < 3) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[2].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(617, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 15) {
			c.getPA().startTeleport(1777, 3707, 0, "modern", false);
			return;
		}	
		if (c.teleAction == 99) {
				c.getPA().startTeleport(3429, 3537, 0, "modern", false);
				return;
			}
		if (c.teleAction == 99) {
			c.getPA().startTeleport(2525, 4776, 0, "modern", false);
			return;
		}
		if (c.teleAction == 16) {
			c.getPA().spellTeleport(1476, 3687, 0, false);
		}
		if (c.teleAction == 14) {
			c.getPA().spellTeleport(3343, 3735, 0, false);
		}
		if (c.teleAction == 12) {
			c.getPA().spellTeleport(2739, 5088, 0, false);
		}
		if (c.teleAction == 11) {
			c.getPA().spellTeleport(3280, 9372, 0, false);
		}
		if (c.teleAction == 10) {
			c.getPA().spellTeleport(3241, 9364, 0, false);
		}
		if (c.teleAction == 9) {
			c.getPA().spellTeleport(3159, 9895, 0, false);
		}
		if (c.teleAction == 8) {
			c.getPA().spellTeleport(2912, 9831, 0, false);
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(2843, 9555, 0, false);
		}
		/*
		 * if (c.teleAction == 1) { //experiments c.getPA().spellTeleport(3555, 9947, 0); } else if (c.teleAction == 2) { //brimhavem dung c.getPA().spellTeleport(2709, 9564, 0); }
		 * else if (c.teleAction == 3) { //dag kings c.getPA().spellTeleport(2479, 10147, 0); } else if (c.teleAction == 4) { //easts lv 18 c.getPA().spellTeleport(3351, 3659, 0);
		 * } else if (c.teleAction == 5) { c.getPA().spellTeleport(2813,3436,0); }
		 */
		if (c.teleAction == 1) {
			// Hill giants
			c.getPA().spellTeleport(3246, 3794, 0, false);
		} else if (c.teleAction == 2) { //Relleka Dung
			c.getPA().spellTeleport(1262, 3501, 0, false);
		} else if (c.teleAction == 3) { // Godwars
			c.getPA().spellTeleport(2885, 5292, 2, false);
		} else if (c.teleAction == 33) { // Callisto
			c.getPA().spellTeleport(3325, 3845, 0, false);
		} else if (c.teleAction == 201) { //Barrows
			 c.getPA().spellTeleport(3565, 3308, 0, false);
		} else if (c.teleAction == 3434) { //Scorpia
			c.getPA().spellTeleport(3233, 3947, 0, false);
		} else if (c.teleAction == 1337) { // Wc Guild
			c.getPA().spellTeleport(1658, 3505, 0, false);
		} else if (c.teleAction == 200) { //Fight Caves
			c.getPA().spellTeleport(2480, 5175, 0, false);
		} else if (c.teleAction == 147258) { //Lumbridge
			 c.getPA().spellTeleport(3222, 3218, 0, false);
			 c.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.LUMBRIDGE_TELEPORT);
		} else if (c.teleAction == 258369) { //Mortan
			 c.getPA().spellTeleport(3432, 3451, 0, false);
		} else if (c.teleAction == 4) {
			// Hillz
			c.getPA().spellTeleport(2726, 3487, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(2813, 3436, 0, false);
		} else if (c.teleAction == 2000) {
			c.getPA().spellTeleport(3222, 3219, 0, false);
		}
		if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2713, 4836, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 11) {
			c.getPA().spellTeleport(2162, 4833, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			c.getPA().spellTeleport(2207, 4836, 0, false);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 4th options on 'Five option' dialogues.
	 */
	public static void handleOption4(Player c) {

		switch (c.dialogueAction) {		
		
		case 6966:
			 if(Boundary.isIn(c, Boundary.BOSSINGZONE1)) {
			Server.npcHandler.spawnNpc(c, 6611, 1997, 2828, 0, 1, 255, 200, 300, 300, true, true);
			//c.getDH().sendStatement("You have spawned @red@Venenatis.");
			 } else  if(Boundary.isIn(c, Boundary.BOSSINGZONE2)) {
			Server.npcHandler.spawnNpc(c, 6611, 1996, 2865, 0, 1, 255, 200, 300, 300, true, true);
			 }
			 
			break;
		case 6902:
			if (c.getItems().playerHasItem(13307, 1000)) {
			c.pkp +=15;
			c.getItems().deleteItem(13307, 1000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>1000</col> blood money for <col=0042FF>15</col> PK Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6903:
			if (c.getItems().playerHasItem(13307, 5000)) {
			c.pkp +=75;
			c.getItems().deleteItem(13307, 5000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>5000</col> blood money for <col=0042FF>75</col> PK Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 820:
			QuickSets.gearUp(c, 7);
			break;
		case 6904:
			if (c.getItems().playerHasItem(13307, 10000)) {
			c.pkp +=165;
			c.getItems().deleteItem(13307, 10000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>10000</col> blood money for <col=0042FF>165</col> PK Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6905:
			if (c.getItems().playerHasItem(13307, 50000)) {
			c.pkp +=850;
			c.getItems().deleteItem(13307, 50000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>50000</col> blood money for <col=0042FF>850</col> PK Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		
		
		
		
		
		
		
		
		
		
		
		//Zamorak
				case 1056:
					c.getPA().startTeleport(2925, 5333, 2, "modern", false);
					break;
				case 14403:
					c.getPA().spellTeleport(2673, 3297, 0, false);
					c.sendMessage("You will gain XP after each lap");
					c.getPA().closeAllWindows();
					break;	
		case 851:
			c.getDH().sendDialogues(853, 7520);
			break;
		case 801: //Safe-box
			c.getSafeBox().openSafeBox();
			break;
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getWoodcuttingEffect()) {
				c.setWoodcuttingEffect(true);
				c.sendMessage("You have toggled on the Woodcutting effect.");
			} else {
				c.setWoodcuttingEffect(false);
				c.sendMessage("You have toggled off the Woodcutting effect.");
			}
			break;
		case 14420: //Hunter
			c.getPA().startTeleport(1580, 3433, 0, "modern", false);			
			c.sendMessage("Welcome to the Feldip Hills.");
			c.getPA().closeAllWindows();
			break;	
		case 14421: //Farming
			c.getPA().startTeleport(1251, 3755, 0, "modern", false);			
			c.sendMessage("Welcome to the Farming Guild.");
			c.getPA().closeAllWindows();
			break;	
		case 14424: //Mining Smithing
			c.getPA().startTeleport(3775, 3822, 0, "modern", false);			
			c.sendMessage("Welcome to the Fossil Mine.");
			c.getPA().closeAllWindows();
			break;	
		case 711:
			c.getDH().sendDialogues(715, 306);
			break;
		case 141314:
			c.getPA().startTeleport(1495, 3555, 0, "modern", false);
			c.sendMessage("You have teleported to the Graveyard of Heros");
			c.getPA().closeAllWindows();
			break;
		case 14422:
			c.getPA().startTeleport(1833, 3785, 0, "modern", false);			
			c.sendMessage("Welcome to Port Piscarilius.");
			c.getPA().closeAllWindows();
			break;
		case 193193:
			c.safeBoxSlots = 4;
			c.sendMessage("You have set your destination to [ @red@Mount Quidamortem@bla@ ].");
			c.getPA().closeAllWindows();
			break;
		case 14425:
			c.getPA().startTeleport(3040, 4969, 1, "modern", false);			
			c.sendMessage("Welcome to Rogues's Den.");
			break;

		case 14402:
			c.getPA().startTeleport(3508, 3487, 0, "modern", false);			
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;
			


		case 1401:
			c.getDH().sendDialogues(1404, c.npcType);
			break;

		case 1404:
			StatReset.execute(c, 3);
			break;

		case 2002:
			c.getPA().spellTeleport(2520, 3591, 0, false);
			break;
		case 2003:

			break;

		case 61: // Corporeal beast
			TeleportationDevice.startTeleport(c, 3);
			break;
			
		case 76: //Max cape
			c.getPA().closeAllWindows();
			break;
			
		case 1393: //Set value manually
			c.getOutStream().createFrame(27);
			c.settingLootValue = true;
			break;
			
		case 55:
			RecolourGraceful.COLOR = "RED";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		case 56: //recolor graceful
			
			break;
		}
		if (c.dialogueAction == 14400) {
			c.getPA().startTeleport(3549, 9866, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			}

		if (c.dialogueAction == 135) {
			c.getDH().sendDialogues(2400, -1);
			return;
		}
		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6735, 1)  && c.getItems().playerHasItem(995, 20_000_000)) {
				c.getItems().deleteItem(6735, 1);
				c.getItems().deleteItem(995, 20_000_000);
				c.refreshQuestTab(0);
				c.getItems().addItem(11772, 1);
				c.sendMessage("You imbue your warrior ring for the cost of @blu@20M@bla@.");
				c.getPA().removeAllWindows();
				Achievements.increase(c, AchievementType.IMBUERING, 1);
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 20M and n Warrior ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 128) {
			c.getDH().sendDialogues(634, -1);
			return;
		}
		if (c.teleAction == 17) {
			c.getPA().spellTeleport(2352, 3160, 0, false);
			return;
		}
		/*switch (c.teleAction) {
		case 2:
			c.getPA().spellTeleport(2678 + Misc.random(3), 9563 + Misc.random(2), 0);
			break;
		}*/
		if (c.teleAction == 14) {
			c.getPA().startTeleport(3179, 3774, 0, "modern", false);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.dialogueAction == 123) {
			DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length < 4) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[3].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(618, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 12) {
			c.getDH().sendOption5("GarGoyle", "Bloodveld", "Banshee", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 11;
			return;
		}
		if (c.teleAction == 15) {
			c.getPA().startTeleport(2525, 4776, 0, "modern", false);
			return;
		}
		if (c.teleAction == 11) {
			c.getDH().sendOption5("Black Demon", "Dust Devils", "Nechryael", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 10;
			return;
		}
		if (c.teleAction == 10) {
			c.getDH().sendOption5("Goblins", "Baby blue dragon", "Moss Giants", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 9;
			return;
		}
		if (c.teleAction == 9) {
			c.getDH().sendOption5("Al-kharid warrior", "Ghosts", "Giant Bats", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 8;
			return;
		}
		if (c.teleAction == 8) {
			c.getDH().sendOption5("Hill Giants", "Hellhounds", "Lesser Demons", "Chaos Dwarf", "-- Next Page --");
			c.teleAction = 7;
			return;
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(2923, 9759, 0, false);
		}
		if (c.teleAction == 1) { //Lovak Bank
			c.getPA().spellTeleport(3204, 3857, 0, false);
		} else if (c.teleAction == 200) { //Warriors guild
			c.getPA().spellTeleport(2865, 3547, 0, false);
		} else if (c.teleAction == 147258) { //Karamja
			 c.getPA().spellTeleport(2924, 3148, 0, false);
		} else if (c.teleAction == 258369) { //Edge
			 c.getPA().spellTeleport(2809, 3440, 0, false);
			 c.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CATHERBY_TELEPORT);
		} else if (c.teleAction == 33) { //Shaman
			c.getPA().spellTeleport(1469, 3687, 0, false);
		} else if (c.teleAction == 3434) { //Arch
			c.getPA().spellTeleport(2991, 3695, 0, false);
		} else if (c.teleAction == 2) { //Stronghold Slayer
			c.getPA().spellTeleport(1277, 3552, 0, false);
		} else if (c.teleAction == 1337) { //Farming
			c.getPA().spellTeleport(3003, 3376, 0, false);
		} else if (c.teleAction == 3) { //Zulrah
			c.getPA().spellTeleport(2202, 3056, 0, false);
		} else if (c.teleAction == 4) {
			// Fala
			/*
			 * c.getPA().removeAllWindows(); c.teleAction = 0;
			 */
			c.getPA().spellTeleport(2815, 3461, 0, false);
			c.getDH().sendStatement("You need a Rake, Watering can, Seed Dibber and a seed.");
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(2724, 3484, 0, false);
			c.sendMessage("For magic logs, try north of the duel arena.");
		}
		if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2660, 4839, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 11) {
			// c.getPA().spellTeleport(2527, 4833, 0); astrals here
			// c.getRunecrafting().craftRunes(2489);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			// c.getPA().spellTeleport(2464, 4834, 0); bloods here
			// c.getRunecrafting().craftRunes(2489);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 5th options on 'Five option' dialogues.
	 */
	public static void handleOption5(Player c) {

		switch (c.dialogueAction) {
		case 711:
		case 77:
		case 801:
		case 851:
			c.getPA().removeAllWindows();
			break;
			
			
			
			
		case 6902:
			if (c.getItems().playerHasItem(13307, 1000)) {
			c.getBH().bounties +=1000;
			c.getItems().deleteItem(13307, 1000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>1000</col> blood money for <col=0042FF>1000</col> Bounty Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6903:
			if (c.getItems().playerHasItem(13307, 5000)) {
			c.getBH().bounties +=5000;
			c.getItems().deleteItem(13307, 5000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>5000</col> blood money for <col=0042FF>5000</col> Bounty Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6904:
			if (c.getItems().playerHasItem(13307, 10000)) {
			c.getBH().bounties +=15000;
			c.getItems().deleteItem(13307, 10000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>10000</col> blood money for <col=0042FF>15000</col> Bounty Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
		case 6905:
			if (c.getItems().playerHasItem(13307, 50000)) {
			c.getBH().bounties +=100000;
			c.getItems().deleteItem(13307, 50000);
			c.getDH().sendStatement("You have exchanged <col=FF0000>50000</col> blood money for <col=0042FF>100,000</col> Bounty Points.");
			c.gfx0(1002);
			}
			else
				c.getDH().sendStatement("You don't have enough <col=FF000>blood</col> money to exchange.");
			break;
			
			
			
		case 14403:
			c.getDH().sendDialogues(14402, -1);
			break;
		case 14400:
			c.getPA().startTeleport(3004, 3935, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;
		case 14420: //Hunter
			c.getPA().startTeleport(2592, 4321, 0, "modern", false);			
			c.sendMessage("Welcome to Puro Puro.");
			c.getPA().closeAllWindows();
			break;

		case 14402:
			c.getDH().sendDialogues(14403, -1);
			break;

		case 1401:
			c.getPA().removeAllWindows();
			break;
			
		case 141314:
			c.getPA().startTeleport(1821, 3688, 0, "modern", false);
			c.sendMessage("You have teleported to the Piscarilius Docks");
			c.getPA().closeAllWindows();
			break;
		case 14424: //Mining Smithing
			c.getPA().startTeleport(2911, 4832, 0, "modern", false);			
			c.sendMessage("Welcome to the Essence Mine.");
			c.getPA().closeAllWindows();
			break;	
		case 193193:
			c.safeBoxSlots = 5;
			c.sendMessage("You have set your destination to [ @red@Arceuus House@bla@ ].");
			c.getPA().closeAllWindows();
			break;

		case 1404:
			c.getDH().sendDialogues(1401, -1);
			break;

		case 2002:
			c.getDH().sendDialogues(2003, -1);
			break;

		case 2003:

			break;

		case 61: // Teleportation device 5

			break;
			
		case 76: //Max cape
			c.getPA().closeAllWindows();
			break;
			
		case 1393:
			c.getDH().sendDialogues(1394, c.npcType);
			break;
			
		case 55:
			c.getDH().sendDialogues(61, c.npcType);
			break;
			
		case 56:
			c.getDH().sendDialogues(55, c.npcType);
			break;
		}

		if (c.dialogueAction == 114) {
			c.getDH().sendDialogues(2402, -1);
			return;
		}
		if (c.dialogueAction == 128) {
			c.getDH().sendDialogues(636, -1);
			return;
		}
		switch (c.teleAction) {
		case 17:
			c.getDH().sendDialogues(3324, -1);
			return;
		/*case 2:
			c.getDH().sendDialogues(3333, -1);
			return;*/
		case 14:
			c.getDH().sendOption5("Callisto @red@(39 Wild)", "Giant mole @red@(49 wild)", "Lizardman Canyon", "", "Close");
			c.teleAction = 16;
			return;
		}
		if (c.dialogueAction == 121 || c.dialogueAction == 123 || c.teleAction == 16 || c.dialogueAction == 131 || c.dialogueAction == 135) {
			c.getPA().removeAllWindows();
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 8) {
			c.getDH().sendOption5("Goblins", "Baby blue dragon", "Moss Giants", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 9;
			return;
		}
		if (c.teleAction == 9) {
			c.getDH().sendOption5("Black Demon", "Dust Devils", "Nechryael", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 10;
			return;
		}
		if (c.teleAction == 11) {
			c.getDH().sendOption5("Infernal Mage", "Dark Beasts", "Abyssal Demon", "-- Previous Page --", "");
			c.teleAction = 12;
			return;
		}
		if (c.teleAction == 10) {
			c.getDH().sendOption5("GarGoyle", "Bloodveld", "Banshee", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 11;
			return;
		}
		if (c.teleAction == 7) {
			c.getDH().sendOption5("Al-kharid warrior", "Ghosts", "Giant Bats", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 8;
			return;
		}
		if (c.teleAction == 15) { //Terrorbirds
			c.getDH().sendDialogues(399, -1); 
		} else if (c.teleAction == 200) {
			c.getDH().sendDialogues(403, -1); 
		} else if (c.teleAction == 147258) {
			c.getDH().sendDialogues(121313, -1); 
		} else if (c.teleAction == 258369) { //Relleka
			 c.getPA().spellTeleport(2673, 3709, 0, false);
		} else if (c.teleAction == 3434) { //Fnatic
			c.getPA().spellTeleport(2993, 3854, 0, false);
		} else if (c.teleAction == 33) { //Vet'ion
			c.getDH().sendDialogues(406, -1); 
		} else if (c.teleAction == 3) {
			c.getDH().sendDialogues(405, -1); 
		} else if (c.teleAction == 201) {
			c.getDH().sendDialogues(402, -1); 
		} else if (c.teleAction == 2) { // Asgarnian Caves
			c.getPA().spellTeleport(3029, 9582, 0, false);
		}
		if (c.teleAction == 1) {
			c.getPA().spellTeleport(2539, 4716, 0, false);
			return;
		/*} else if (c.teleAction == 200) {
			// last minigame spot
			// c.sendMessage("Suggest something for this spot on the
			// forums!");
			// c.getPA().closeAllWindows();
			 c.getPA().spellTeleport(1461, 3689, 0);*/
		} else if (c.teleAction == 3) {
			c.getDH().sendOption5("Dagannoth Cave", "Kraken @red@(Level 17 & Multi)", "Venenatis @red@(Level 29 & Multi)", "Vet'ion @red@(Level 34 & Multi)", "@blu@Next");
			c.teleAction = 14;
		} else if (c.teleAction == 4) {
			// ardy lever
			/*
			 * c.getPA().removeAllWindows(); c.teleAction = 0;
			 */
			c.getPA().spellTeleport(3039, 4836, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(2812, 3463, 0, false);
		}
		if (c.dialogueAction == 10 || c.dialogueAction == 11) {
			c.dialogueId++;
			c.getDH().sendDialogues(c.dialogueId, 0);
		} else if (c.dialogueAction == 12) {
			c.dialogueId = 17;
			c.getDH().sendDialogues(c.dialogueId, 0);
		}
	}
}
