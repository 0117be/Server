package ethos.model.players.packets.npcoptions;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;


import ethos.Config;
import ethos.Server;
import ethos.model.content.PotionMixing;
import ethos.model.content.achievement_diary.desert.DesertDiaryEntry;
import ethos.model.content.achievement_diary.fremennik.FremennikDiaryEntry;
import ethos.model.content.achievement_diary.kandarin.KandarinDiaryEntry;
import ethos.model.content.achievement_diary.varrock.VarrockDiaryEntry;
import ethos.model.content.dailytasks.DailyTasks;
import ethos.model.content.tradingpost.Listing;
import ethos.model.holiday.halloween.HalloweenRandomOrder;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.npcs.pets.PetHandler;
import ethos.model.npcs.pets.Probita;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.Right;
import ethos.model.players.combat.CombatType;
import ethos.model.players.skills.Fishing;
import ethos.model.players.skills.agility.AgilityHandler;
import ethos.model.players.skills.crafting.Tanning;
import ethos.model.players.skills.hunter.impling.Impling;
import ethos.model.players.skills.mining.Mineral;
import ethos.model.players.skills.thieving.Thieving.Pickpocket;
import ethos.util.Location3D;
import ethos.model.players.Boundary;
/*
 * @author Matt
 * Handles all first options on non playable characters.
 */

public class NpcOptionOne {

	public static void handleOption(Player player, int npcType) {
		if (Server.getMultiplayerSessionListener().inAnySession(player)) {
			return;
		}
		player.clickNpcType = 0;
		player.rememberNpcIndex = player.npcClickIndex;
		player.npcClickIndex = 0;

		/*
		 * if(Fishing.fishingNPC(c, npcType)) { Fishing.fishingNPC(c, 1, npcType);
		 * return; }
		 */
		if (PetHandler.isPet(npcType)) {
			if (Objects.equals(PetHandler.getOptionForNpcId(npcType), "first")) {
				if (PetHandler.pickupPet(player, npcType, true))
					return;
			}
		}

		if (Server.getHolidayController().clickNpc(player, 1, npcType)) {
			return;
		}
		switch (npcType) {
		case 2148:
			if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN)|| player.getRights().isOrInherits(Right.BRNZ) ||player.getRights().isOrInherits(Right.HARDCORE))
				player.sendMessage("@red@You can't access this on this game mode.");
				else 
					Listing.openPost(player, false, true);
		break;
		case 1603:
			player.getDH().sendDialogues(150, npcType);
			break;
		case 5452:
			if (player.getRights().isOrInherits(Right.BRNZ)) {
			player.getDH().sendDialogues(6161, npcType);
			}
			else 
				player.sendMessage("@red@Only Bronze Man can access this NPC.");			
			break;
		case 8037:
			player.getShops().openShop(124);
			break;
		case 7923:
			player.getShops().openShop(118);
			break;	
		case 1012:
			player.getShops().openShop(135);
			//player.getDH().sendDialogues(7461, 1012); //nicolas left on here :)
			break;
		case 7952:
			if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) ||player.getRights().isOrInherits(Right.HARDCORE))
				player.sendMessage("@red@You can't access this on this game mode.");
				else 
			player.getShops().openShop(126);
			break;
case 5215:
	if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) ||player.getRights().isOrInherits(Right.HARDCORE))
	player.getShops().openShop(137);
	else 
		player.getShops().openShop(9);
	break;
	
case 505:
DailyTasks.complete(player);
break;

case 4680:
	if (player.dailyEnabled == false) {
	player.getDH().sendDialogues(900, 4680);
	}
	if (player.dailyEnabled == true) {
		player.getDH().sendDialogues(902, 4680);
	}
	break;
		case 7690:
			player.getDH().sendDialogues(1500, -1);
			break;
			case 4407:
				player.getShops().openShop(19);
				break;
			case 1306:
				if (player.getItems().isWearingItems()) {
					player.sendMessage("You must remove your equipment before changing your appearance.");
					player.canChangeAppearance = false;
				} else {
					player.getPA().showInterface(3559);
					player.canChangeAppearance = true;
				}
				break;
			case 9157:
				player.getPA().spellTeleport(Config.START_LOCATION_X, Config.START_LOCATION_Y, 0, true);
				break;
				
		case 5314:
			player.getPA().showInterface(51000);
			player.getTeleport().selection(player, 0);
			// player.getTeleport().loadMonsterTab();
			break;
			
		case 7951:
			player.getShops().openShop(125);
			break;
		
		case 8530:
			player.getShops().openShop(2);
			break;

		case 2995:
			player.getShops().openShop(4);
			break;
		case 3585:
			player.getShops().openShop(6);
			break;
		case 3529:
			player.getShops().openShop(5);
			break;
		case 4929:
			player.getShops().openShop(3);
			break;
		case 5793:
			player.getDH().sendDialogues(6971, 5793);
			//player.getShops().openShop(83);
			break;
		case 7511:
			player.getDH().sendDialogues(6993, 7511);
			break;
		case 4923:
			player.getShops().openShop(8);
			break;
		case 1909:
			player.getDH().sendDialogues(900, 1909);
			break;
		case 5567:
			player.getDH().sendDialogues(1427, 5567);
			break;
		case 3306:
			player.getDH().sendDialogues(1577, -1);
			break;
		case 7520:
			player.getDH().sendDialogues(850, 7520);
			break;
		/**
		 * Doomsayer
		 */
		case 6773:
			if (!player.pkDistrict) {
				player.sendMessage("You cannot do this right now.");
				return;
			}
			player.getDH().sendDialogues(800, 6773);
			break;
		// Zeah Throw Aways
		case 2200:
			player.getDH().sendDialogues(55873, 2200);
			break;
		case 3189:
			player.getDH().sendDialogues(11929, 3189);
			break;
		case 5998:
			if (player.amDonated <= 1) {
				player.getDH().sendDialogues(5998, 5998);
			} else {
				player.getDH().sendDialogues(5999, 5998);
			}
			break;
			case 534:
			if (Boundary.isIn(player, Boundary.VARROCK_BOUNDARY)) {
				player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.DRESS_FOR_SUCESS);
			}
				player.getShops().openShop(114);
				break;
		case 4062:
			player.getDH().sendDialogues(55875, 4062);
			break;
		case 4321:
			player.getDH().sendDialogues(145, 4321);
			break;
		case 7041:
			player.getDH().sendDialogues(500, 7041);
			break;
		case 6877:
			player.getDH().sendDialogues(55877, 6877);
			break;
		case 4409:
			player.getDH().sendDialogues(55876, 4409);
			break;
		case 6982:
			player.getDH().sendDialogues(55868, 6982);
			break;
		case 6947:
		case 6948:
		case 6949:
			player.getDH().sendDialogues(55872, 6947);
			break;
		case 6998:
			player.getDH().sendDialogues(55871, 6998);
			break;
		case 7001:
			player.getDH().sendDialogues(55869, 7001);
			break;
		case 6999:
			player.getDH().sendDialogues(55870, 6999);
			break;
		case 6904:
			player.getDH().sendDialogues(55864, 6904);
			break;
		case 6906:
			player.getDH().sendDialogues(55865, 6904);
			break;
		case 6908:
			player.getDH().sendDialogues(55866, 6904);
			break;
		case 6910:
			player.getDH().sendDialogues(55867, 6904);
			break;
		// End Zeah Throw Aways
		case 1503:
			player.getDH().sendDialogues(88393, 1503);
			break;
		case 1504:
			player.getDH().sendDialogues(88394, 1504);
			break;

		case 6774:
			player.getDH().sendDialogues(800, 6773);
			break;

		case 7440:
			HalloweenRandomOrder.checkOrder(player);
			break;

		case 822:
			boolean hascompleted = player.getDiaryManager().getWildernessDiary().hasDoneEasy();
			
			if (hascompleted) {
				//send a statment asking if you want to proceed
				player.getDH().sendDialogues(702, npcType);			
			} else {
				player.sendMessage("You need to complete the easy wilderness diary for Oziach to assist you.");
			}
			
			break;
		case 306:
			player.getDH().sendDialogues(710, 306);
			break;

		case 7303:
			player.sendMessage("I will trade you my master clue if you bring me a set of Clue Scrolls!");
			player.sendMessage("A set consists of an Easy, Medium, and Hard Clue Scroll");

			break;
			
		case 3936:
			AgilityHandler.delayFade(player, "NONE", 2310, 3782, 0, "You board the boat...", "And end up in Neitiznot", 3);
			player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TRAVEL_NEITIZNOT);
			break;

		case 2914:
			player.getDH().sendNpcChat2("Use a Zamorakian Spear on me to turn", "it into a Hasta! Or Vice Versa.", 2914,
					"Otto Godblessed");
			break;

		case 1635:
		case 1636:
		case 1637:
		case 1638:
		case 1639:
		case 1640:
		case 1641:
		case 1642:
		case 1643:
		case 1654:
		case 7302:
			Impling.catchImpling(player, npcType, player.rememberNpcIndex);
			break;

		case 17: // Rug merchant - Pollnivneach
			player.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.TRAVEL);
			player.startAnimation(2262);
			AgilityHandler.delayFade(player, "NONE", 3351, 3003, 0, "You step on the carpet and take off...",
					"at last you end up in pollnivneach.", 3);
			break;
		case 276:
			if (Config.BONUS_XP_WOGW) {
				player.getDH().sendNpcChat1(
						"Well of Goodwill is currently @red@active@bla@! \\n It is granting 1 hour of @red@Double XP@bla@!",
						276, "Crier");
			} else if (Config.BONUS_MINIGAME_WOGW) {
				player.getDH().sendNpcChat1(
						"Well of Goodwill is currently @red@active@bla@! \\n It is granting 1 hour of @red@Double Pc Points@bla@!",
						276, "Crier");
			} else if (Config.DOUBLE_DROPS) {
				player.getDH().sendNpcChat1(
						"Well of Goodwill is currently @red@active@bla@! \\n It is granting 1 hour of @red@Double Drops@bla@!",
						276, "Crier");
			} else {
				player.getDH().sendNpcChat1("Well of Goodwill is currently @red@inactive@bla@!", 276, "Town Crier");
			}
			break;
		case 5520:
			player.getDiaryManager().getDesertDiary().claimReward();
			break;
		case 5519:
			player.getDiaryManager().getArdougneDiary().claimReward();
			break;
		case 5790:
			player.getDiaryManager().getKaramjaDiary().claimReward();
			break;
		case 5525:
			player.getDiaryManager().getVarrockDiary().claimReward();
			break;
		case 5523:
			player.getDiaryManager().getLumbridgeDraynorDiary().claimReward();
			break;
		case 5524:
			player.getDiaryManager().getFaladorDiary().claimReward();
			break;
		case 5521:
			player.getDiaryManager().getMorytaniaDiary().claimReward();
			break;
		case 6819:
			player.getDiaryManager().getWildernessDiary().claimReward();
			break;
		case 5517:
			player.getDiaryManager().getKandarinDiary().claimReward();
			break;
		case 5526:
			player.getDiaryManager().getFremennikDiary().claimReward();
			break;
		case 5518:
			player.getDiaryManager().getWesternDiary().claimReward();
			break;

		case 1031:
			if (player.getItems().playerHasItem(995, 30)) {
				player.getItems().deleteItem(995, 30);
				player.getItems().addItem(36, 1);
				player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.BUY_CANDLE);
			} else {
				player.sendMessage("You need 30 coins to purchase a candle.");
				return;
			}
			break;

		case 6586:
			player.getDH().sendNpcChat1("No shirt, Sherlock", 6586, "Sherlock");
			player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.SHERLOCK);
			break;

		case 5036:
			if (player.getItems().playerHasItem(225) || player.getItems().playerHasItem(223)) {
				player.sendMessage("The Apothecary takes your ingredients and creates a strength potion.");
				player.getItems().deleteItem(225, 1);
				player.getItems().deleteItem(223, 1);
				player.getItems().addItem(115, 1);
				player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.APOTHECARY_STRENGTH);
			} else {
				player.sendMessage("You must have limpwurt root and red spiders' eggs to do this.");
				return;
			}
			break;

		case 5906:
			Probita.hasInvalidPet(player);
			break;

		case 3500:
			player.getDH().sendDialogues(64, npcType);
			break;

		case 5870:
			if (player.getCerberusLostItems().size() > 0) {
				player.getDH().sendDialogues(640, 5870);
				return;
			}
			player.getDH().sendDialogues(105, npcType);
			break;
		case 8324:
				player.getDH().sendDialogues(6981, 8324);
				return;
			
		case 7283:
			if (player.getSkotizoLostItems().size() > 0) {
				player.getDH().sendDialogues(640, 7283);
				return;
			}
			player.getDH().sendDialogues(105, npcType);
			break;

		case 6481: // Mac
			if (!player.maxRequirements(player)) {
				player.sendMessage("Mac does not even bother speaking to you.");
				return;
			}
			player.getDH().sendDialogues(85, npcType);
			break;
		case 3307: // Combat instructor
			player.getDH().sendDialogues(1390, npcType);
			break;

		case 5513: // Elite void knight
			player.getDH().sendDialogues(79, npcType);
			break;

		case 6071: // Achievement cape
			player.getAchievements().claimCape();
			break;
	
		case 394:
		case 3227:
			player.getDH().sendDialogues(669, npcType);
			break;
		case 311:
			player.getDH().sendDialogues(650, npcType);
			break;

		// Noting Npc At Skill Area
		case 905:
			player.talkingNpc = 905;
			player.getDH().sendNpcChat("Hello there, I can note your resources.",
					"I charge @red@25%@bla@ of the yield, this @red@does not apply to donators@bla@.",
					"Use any resource item obtained in this area on me.");
			player.nextChat = -1;
			break;

		case 2040:
			player.getDH().sendDialogues(637, npcType);
			break;
		case 2184:
			player.getShops().openShop(29);
			break;
		case 1390:
			player.sendMessage("@red@All perks last forever. Perks are also tradeable!");
			player.getShops().openShop(151);
			break;
		case 4066:
			player.getShops().openShop(136);
			player.sendMessage("@red@All perks in the boss point shop grant 10% DR!");
			break;

			case 542:
				player.getShops().openShop(77);
				player.sendMessage("@red@ Please type in ::vote to go to the site. And do ::claimvotes to receive them!");
				player.sendMessage("@red@ Thank you for supporting Revelation!");
				break;

		case 6866:
			player.getShops().openShop(82);
			player.sendMessage("You currently have @red@" + player.getShayPoints() + " @bla@Assault Points!");
			break;

		case 6601:
			NPC golem = NPCHandler.npcs[player.rememberNpcIndex];
			if (golem != null) {
				player.getMining().mine(golem, Mineral.RUNE,
						new Location3D(golem.getX(), golem.getY(), golem.heightLevel));
			}
			break;
		case 1850:
			player.getShops().openShop(112);
			break;
		case 2580:
			player.getDH().sendDialogues(629, npcType);
			break;
		case 3257:
			if(player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || player.getRights().isOrInherits(Right.HARDCORE)){
				player.getThieving().steal(Pickpocket.FARMER, NPCHandler.npcs[player.rememberNpcIndex]);
			}else{
				player.getShops().openShop(16);
			}
			break;
		case 3258:
				player.getThieving().steal(Pickpocket.FARMER_RAIDS, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 6957:
			player.getThieving().steal(Pickpocket.HERBLORE_SECONDARIES, NPCHandler.npcs[player.rememberNpcIndex]);
		break;
		case 3078:
			player.getThieving().steal(Pickpocket.MAN, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 5077:
			player.getThieving().steal(Pickpocket.COOK, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3311:
			player.getThieving().steal(Pickpocket.MINER, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3106:
			player.getThieving().steal(Pickpocket.HERO, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3245:
			player.getThieving().steal(Pickpocket.GUARD, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3104:
			player.getThieving().steal(Pickpocket.PALADIN, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3108:
			player.getThieving().steal(Pickpocket.KING, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 5006:
			player.getThieving().steal(Pickpocket.MAGIC, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3894:
			player.getDH().sendDialogues(628, npcType);
			break;
		case 3220:
			player.getShops().openShop(25);
			break;
		case 637:
			player.getShops().openShop(6);
			break;
			case 6875:
				player.specRestore = 120;
				player.specAmount = 10.0;
				player.setRunEnergy(100);
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.playerLevel[5] = player.getPA().getLevelForXP(player.playerXP[5]);
				player.getHealth().removeAllStatuses();
				player.getHealth().reset();
				player.getPA().refreshSkill(5);
				player.getDH().sendItemStatement("Restored your HP, Prayer, Run Energy, and Spec", 4049);
				player.nextChat =  -1;
				break;
			case 732:
			player.getShops().openShop(16);
			break;
		case 4921:
			if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) ||player.getRights().isOrInherits(Right.HARDCORE))
				player.sendMessage("@red@You can't access this on this game mode.");
				else 
			player.getShops().openShop(113);
			break;
		case 2949:
			player.getPestControlRewards().showInterface();
			break;
		case 2461:
			player.getWarriorsGuild().handleDoor();
			break;
		case 7663:
			player.getDH().sendDialogues(3299, npcType);
			break;
		case 402:// slayer
			if(player.combatLevel<20){
				player.getDH().sendNpcChat2("Do not waste my time peasent.","You need a Combat level of 20.",402,"Mazchna");
				return;
			}
			player.getDH().sendDialogues(3300, npcType);
			break;
		case 401:
			player.getDH().sendDialogues(3300, npcType);
			break;
		case 405:
			if(player.combatLevel<100){
				player.getDH().sendNpcChat1("You need a combat level of 100 to recieve tasks from me.",405,"Duradel");
				return;
			}
			if (player.playerLevel[18] < 50) {
				player.getDH().sendNpcChat1("You must have a slayer level of at least 50 to recieve my tasks.", 405, "Duradel");
				return;
			}
			player.getDH().sendDialogues(3300, npcType);
			break;
		case 8623:
			if(player.combatLevel<60){
				player.getDH().sendNpcChat1("You need a combat level of 60 to recieve tasks from me.",8623,"Konar");
				return;
			}
			if (player.playerLevel[18] < 85) {
				player.getDH().sendNpcChat1("You must have a slayer level of at least 85 to recieve my tasks.", 8623, "Konar");
				return;
			}
			player.getDH().sendDialogues(3300, npcType);
			break;
		case 3394:
			if(player.playerLevel[18] != 99){
				player.getDH().sendNpcChat1("You need 99 Slayer to talk to me.",3394,"Blood Slayer");
				return;
			}
			player.getDH().sendDialogues(3300, npcType);
			break;
		case 6797: // Nieve
			if (player.playerLevel[18] < 90) {
				player.getDH().sendNpcChat1("You must have a slayer level of at least 90 to receive my tasks.", 6797, "Nieve");
				return;
			} else {
				player.getDH().sendDialogues(3300, npcType);
			}
			break;
		case 315:
			player.getDH().sendDialogues(550, npcType);
			break;

		case 1308:
			player.getDH().sendDialogues(538, npcType);
			break;
		case 6771:
			player.getShops().openShop(78);
			player.sendMessage("You currently have " + player.getAchievements().getPoints() + " Achievement points.");
			break;
		case 954:
			player.getDH().sendDialogues(619, npcType);
			break;
		case 4306:
			player.getShops().openSkillCape();
			break;
		case 3341:
			player.getDH().sendDialogues(2603, npcType);
			break;
		case 5919:
			player.getDH().sendDialogues(14400, npcType);
			break;
		case 6599:
			player.getShops().openShop(12);
			break;
		case 2578:
			player.getDH().sendDialogues(2401, npcType);
			break;
		case 3789:
			player.getShops().openShop(75);
			break;
		// FISHING
		case 7676://infernal eel
			Fishing.attemptdata(player, 19);
			break;
		case 3913: // NET + BAIT
			Fishing.attemptdata(player, 1);
			break;
		case 3317:
			Fishing.attemptdata(player, 14);
			break;
		case 4712:
			Fishing.attemptdata(player, 15);
			break;
		case 1524:
			Fishing.attemptdata(player, 11);
			break;
		case 3417: // TROUT
			Fishing.attemptdata(player, 4);
			break;
		case 3657:
			Fishing.attemptdata(player, 8);
			break;
		case 635:
			Fishing.attemptdata(player, 13); // DARK CRAB FISHING
			break;
		case 6825: // Anglerfish
			Fishing.attemptdata(player, 16);
			break;
		case 1520: // LURE
		case 310:
		case 314:
		case 317:
		case 318:
		case 328:
		case 331:
			Fishing.attemptdata(player, 9);
			break;

		case 944:
			player.getDH().sendOption5("Hill Giants", "Hellhounds", "Lesser Demons", "Chaos Dwarf", "-- Next Page --");
			player.teleAction = 7;
			break;

		case 559:
			player.getShops().openShop(16);
			break;
		case 5809:
			Tanning.sendTanningInterface(player);
			break;

		case 1815:
			player.getShops().openShop(22);
			break;
		case 403:
			player.getDH().sendDialogues(2300, npcType);
			break;
		case 1599:
			break;

		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker
		case 1702: // Ghost Banker
		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 567: // Banker
		case 766: // Banker
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // Banker
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
			player.getPA().openUpBank();
			break;
		case 1986:
			player.getDH().sendDialogues(2244, player.npcType);
			break;

		case 5792:
			player.getDH().sendDialogues(4005, player.npcType);
			// player.getShops().openShop(9);
			break;
			
		case 6747:			
			player.getShops().openShop(77);
			break;
			
		case 1578:// magic supplies			
			if (player.getRights().isOrInherits(Right.IRONMAN) && !player.getDiaryManager().getVarrockDiary().hasDoneMedium()|| player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) &&!player.getDiaryManager().getVarrockDiary().hasDoneMedium() ||player.getRights().isOrInherits(Right.HARDCORE) &&!player.getDiaryManager().getVarrockDiary().hasDoneMedium())
				player.sendMessage("@red@You have to complete Varrock Medium Diaries!");
				else 
			player.getShops().openShop(6);
			break;
			
		case 1157:// range supplies
			if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) ||player.getRights().isOrInherits(Right.HARDCORE))
				player.sendMessage("@red@You can't access this on this game mode.");
				else 
			player.getShops().openShop(4);
			break;
			
		case 1785:
			player.getShops().openShop(8);
			break;

		case 1860:
			player.getShops().openShop(47);
			break;

		case 519:
			player.getShops().openShop(48);
			break;

		case 548:
			player.getDH().sendDialogues(69, player.npcType);
			break;

		case 2258:
			player.getDH().sendOption2("Teleport me to Runecrafting Abyss.", "I want to stay here, thanks.");
			player.dialogueAction = 2258;
			break;

		case 532:
			player.getShops().openShop(47);
			break;

		case 3583:// melee supplies
			if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) ||player.getRights().isOrInherits(Right.HARDCORE))
				player.sendMessage("@red@You can't access this on this game mode.");
				else 
			player.getShops().openShop(8);
			break;

		case 506:
			if (player.getRights().isOrInherits(Right.IRONMAN) ||player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN)|| player.getRights().isOrInherits(Right.HARDCORE)) {
				player.getShops().openShop(41);
			} else {
				player.sendMessage("You must be an Iron Man to access this shop.");
			}
			break;
		case 507:
			if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) ||player.getRights().isOrInherits(Right.HARDCORE))
				player.sendMessage("@red@You can't access this on this game mode.");
				else 
			player.getShops().openShop(2);
			break;
		case 1056:
			player.getShops().openShop(7);
			break;
		case 5449:
			PotionMixing.decantInventory(player);
			break;

		/*
		 * case 198: c.getShops().openSkillCape(); break;
		 */

		/**
		 * Make over mage.
		 */

		}
	}

}
