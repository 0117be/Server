package ethos.model.players.packets.npcoptions;

import ethos.Config;
import ethos.Server;
import ethos.model.content.PotionMixing;
import ethos.model.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import ethos.model.content.achievement_diary.desert.DesertDiaryEntry;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.achievement_diary.fremennik.FremennikDiaryEntry;
import ethos.model.content.achievement_diary.varrock.VarrockDiaryEntry;
import ethos.model.npcs.NPCHandler;
import ethos.model.npcs.pets.PetHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.model.players.skills.agility.AgilityHandler;
import ethos.model.players.skills.thieving.Thieving.Pickpocket;

/*
 * @author Matt
 * Handles all 3rd options on non playable characters.
 */

public class NpcOptionThree {

	public static void handleOption(Player player, int npcType) {
		if (Server.getMultiplayerSessionListener().inAnySession(player)) {
			return;
		}
		player.clickNpcType = 0;
		player.rememberNpcIndex = player.npcClickIndex;
		player.npcClickIndex = 0;
//		if (npcType != 2130 || npcType != 2131 || npcType != 2132) {
//			if (PetHandler.talktoPet(c, npcType))
//				return;
//		}
		if (PetHandler.isPet(npcType)) {
			if (PetHandler.getOptionForNpcId(npcType) == "third") {
				if (PetHandler.pickupPet(player, npcType, true))
					return;
			}
		}
		if (Server.getHolidayController().clickNpc(player, 3, npcType)) {
			return;
		}
		switch (npcType) {
		case 3257:
			if(player.getRights().isOrInherits(Right.IRONMAN)|| player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN)){
				player.getThieving().steal(Pickpocket.FARMER, NPCHandler.npcs[player.rememberNpcIndex]);
			}else{
				player.getShops().openShop(16);
			}
			break;
		case 1428:
			player.getPrestige().openShop();
			break;
		case 1909:
			player.getDH().sendDialogues(903, 1909);
			break;
		case 5215:
			player.getDH().sendDialogues(16999, 5215);
			break;
		case 5567:
			player.getPrestige().openShop();
			break;
		case 4321:
			player.getShops().openShop(119);
			player.sendMessage("You currently have @red@"+player.bloodPoints+" @bla@Blood Money Points!");
			break;
		case 7520:
			player.getShops().openShop(118);
			player.sendMessage("You currently have @red@"+player.getRaidPoints()+" @bla@Raid Points!");
			break;
		case 6773:
			player.isSkulled = true;
			player.skullTimer = Config.EXTENDED_SKULL_TIMER;
			player.headIconPk = 0;
			player.getPA().requestUpdates();
			player.sendMessage("@cr10@@blu@You are now skulled.");
			break;
		case 2200:
			player.getPA().openUpBank();
			break;
		case 311:
			if (player.getRights().isOrInherits(Right.IRONMAN) || player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN)|| player.getRights().isOrInherits(Right.HARDCORE)) {
				player.getShops().openShop(41);
			} else {
				player.sendMessage("You must be an Iron Man to access this shop.");
			}
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
		case 17: //Rug merchant - Nardah
			if (!player.getDiaryManager().getDesertDiary().hasCompleted("EASY")) {
				player.getDH().sendNpcChat1("You must have completed all easy diaries here in the desert \\n to use this location.", 17, "Rug Merchant");
				return;
			}
			player.startAnimation(2262);
			AgilityHandler.delayFade(player, "NONE", 3402, 2916, 0, "You step on the carpet and take off...", "at last you end up in nardah.", 3);
			break;
		
		/*case 3936:
			AgilityHandler.delayFade(player, "NONE", 2310, 3782, 0, "You board the boat...", "And end up in Neitiznot", 3);
			player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TRAVEL_NEITIZNOT);
			break;*/
			
		case 402:
		case 401:
		case 405:
		case 6797:
		case 7663:
		case 8623:
	
			player.getShops().openShop(44);
			player.sendMessage("I currently have @blu@" + player.getSlayer().getPoints() + " @bla@slayer points.");
			break;
		case 3394:
			if(player.playerLevel[18] != 99){
				player.getDH().sendNpcChat1("You need 99 Slayer to talk to me.",3394,"Blood Slayer");
			}
				else 	{		
				player.getShops().openShop(10);
				break;
	}
		case 315:
			player.getDH().sendDialogues(548, 315);
			break;
		case 403:
			player.getDH().sendDialogues(12001, -1);
			break;
		case 1599:
			player.getShops().openShop(10);
			player.sendMessage("You currently have @red@" + player.slayerPoints + " @bla@slayer points.");
			break;
		case 836:
			player.getShops().openShop(103);
			break;
		case 5449:
			PotionMixing.decantInventory(player);

			break;
		case 2580:
			if (Boundary.isIn(player, Boundary.VARROCK_BOUNDARY)) {
				player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.TELEPORT_ESSENCE_VAR);
			}
			if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
				player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.TELEPORT_ESSENCE_ARD);
			}
		
			player.getPA().startTeleport(2929, 4813, 0, "modern", false);
			break;

			case 534:
				if (player.getItems().isWearingItems()) {
					player.sendMessage("You must remove your equipment before changing your appearance.");
					player.canChangeAppearance = false;
				} else {
					player.getPA().showInterface(3559);
					player.canChangeAppearance = true;
				}
				break;

		}
	}

}
