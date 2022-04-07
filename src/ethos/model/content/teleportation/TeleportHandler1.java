package ethos.model.content.teleportation;

import ethos.Config;
import ethos.event.impl.WheatPortalEvent;
import ethos.model.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.achievement_diary.fremennik.FremennikDiaryEntry;
import ethos.model.content.achievement_diary.kandarin.KandarinDiaryEntry;
import ethos.model.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import ethos.model.content.achievement_diary.morytania.MorytaniaDiaryEntry;
import ethos.model.content.achievement_diary.western_provinces.WesternDiaryEntry;
import ethos.model.players.Player;

/**
 * 
 * @author Adam_#6723
 * @Date 08/01/2019
 */

public class TeleportHandler1 {

	protected Player player;

	public TeleportHandler1(Player player) {
		this.player = player;
	}
	//**
	/*  Opens The interface on the default tab, which is bosses e.g. the first
	 /* category/tab.
	 */
	public void open() {
		player.setTeleportType1(TeleportType1.BOSSES);
		switchTab(253243);
		player.getPA().showInterface(65000);
	}

	 /*
	  * Another open method, which can be used to open a specific TeleportType of your choosing.
	  */
	public void open(TeleportType1 type) {
		player.setTeleportType1(type);
		switchTab(253243);
		player.getPA().showInterface(65000);
	}
	 
	 /**
	  * Handles clicking each of the tabs so that it lists the accurate information
	  * @param buttonid
	  */
	public void switchTab(int buttonid) {
		switch (buttonid) {
			/*
			 * case 253243: //bosses btn
		case 253244: //monsters btn
		case 253245: //wildy btn
		case 253246: //skilling btn
		case 253247: //minigames btn
		case 253248: //cities btn
		case 253254: //teleport btn
			 */
			
		case 253243:
			player.sendMessage("Hey you");
			player.setTeleportType1(TeleportType1.BOSSES);
			switchData();
			break;
		case 253244:
			player.setTeleportType1(TeleportType1.MONSTERS);
			switchData();
			break;
		case 253245:
			player.setTeleportType1(TeleportType1.WILDERNESS);
			switchData();
			break;
		case 253246:
			player.setTeleportType1(TeleportType1.SKILLING);
			switchData();
			break;
		case 253247:
			player.setTeleportType1(TeleportType1.MINIGAMES);
			switchData();
			break;
		case 253248:
			player.setTeleportType1(TeleportType1.CITIES);
			switchData();
			break;
		default:
			break;
		}
	}

	//**
	 /*.Te Handles **Switching** of the tabs, so that it lists the correct information
	 /* for each category.
	 */
	public void switchData() {
		int count = 65031;
		for (int i = 65031; i < 65060; i++) {
			player.getPA().sendFrame126("", i); // boom u did it thats it
		}
		for (TeleportData1 data : TeleportData1.values()) {
			if (data.getType() == player.getTeleportType1()) {
				player.getPA().sendFrame126(data.getName(), count++);
				if (count >= 65061) {
					System.err.println("You are placing a teleport, where the interface ID Stops at.");
					System.err.println("Please check again on the teleports you are adding - James");
					return;
				}
			}
		}
	}

	//** Button Clicking for teleports. **//*
	public void button(int buttonId) {
		for (TeleportData1 data : TeleportData1.values()) {
			if (data.getType() == player.getTeleportType1()) {
				if (buttonId == data.getClickingId()) {
					player.setCurrentTeleport1(data);
					player.getPA().sendFrame126("" + data.getName(), 65017);
					player.getPA().sendFrame126("" + data.getHealth(), 65018);
					player.getPA().sendFrame126("" + data.getTeamsize(), 65019);
					player.getPA().sendFrame126("" + data.getAttackstyles(), 65020);
					player.getPA().sendFrame126("" + data.getDifficulty(), 65021);
					ItemGroup(buttonId);
				}
			}
		}
	}

	//** Teleport button itself. **//*
	public void teleport() {
		if (player.getCurrentTeleport1() != null) {
			player.getPA().startTeleport(player.getCurrentTeleport1().getX(), player.getCurrentTeleport1().getY(), player.getCurrentTeleport1().getZ(), "", false);
		} else {
			player.sendMessage("Please select a teleport destination first!");
		}
	}

	public void ResetFrame34() {
		int interfaceId = 65098;
		for (int index = 0; index < 100; index++) {
			player.getPA().sendFrame34(interfaceId, -1, -1, -1);
			player.getPA().sendFrame126("", interfaceId);
		}
	}
	
	public void ItemGroup(int buttonId) {
		for (TeleportData1 data : TeleportData1.values()) {
			for (int i = 0; i < data.getItem().length; i++) {
				if (data.getType() == player.getTeleportType1()) {
					if (buttonId == data.getClickingId()) {
						ResetFrame34();
						player.getPA().sendFrame34(65098, data.getItem()[i], i, 1);
					}
				}
			}
		}
	}
}