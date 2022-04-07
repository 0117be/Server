package ethos.model.minigames.inferno;

import ethos.Server;
import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.content.achievement_diary.karamja.KaramjaDiaryEntry;
import ethos.model.items.ItemAssistant;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

public class Inferno extends Tzkalzuk {

	public Inferno(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	
	}

	private int killsRemaining;


	public void spawn() {
	    player.getInferno().initiateTzkalzuk();
    }


	public void leaveGame() {
		if (System.currentTimeMillis() - player.infernoLeaveTimer < 15000) {
			player.sendMessage("You cannot leave yet, wait a couple of seconds and try again.");
			return;
		}
		killAllSpawns();
		player.sendMessage("You have left the Inferno minigame.");
		player.getPA().movePlayer(2495, 5110, 0);
		player.infernoWaveType = 0;
		player.infernoWaveId = 0;
	}

	public void create(int type) {
		player.getPA().removeAllWindows();
		player.getPA().movePlayer(2271, 5329, height);
		player.infernoWaveType = type;
		player.sendMessage("Welcome to the Inferno. The boss fight will commence soon.", 255);
		player.infernoWaveId = 0;
		player.infernoLeaveTimer = System.currentTimeMillis();
		spawn();
	}

	public void stop() {
		player.getPA().movePlayer(2495, 5110, 0);
		player.getDH().sendStatement("Congratulations for finishing the Inferno!");
		player.waveInfo[player.infernoWaveType - 1] += 1;
		player.infernoWaveType = 0;
		player.infernoWaveId = 0;
		player.nextChat = 0;
		player.setRunEnergy(100);
		killAllSpawns();
		player.zukDead = false;
	}

	public void handleDeath() {
		player.getPA().movePlayer(2495, 5110, 0);
		player.getDH().sendStatement("Unfortunately you lost the battle better luck next time!");
		player.nextChat = 0;
		player.infernoWaveType = 0;
		player.infernoWaveId = 0;
		killAllSpawns();
	}

	public void killAllSpawns() {
		for (int i = 0; i < NPCHandler.npcs.length; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.isInfernoNpc(i)) {
					if (NPCHandler.isSpawnedBy(player, NPCHandler.npcs[i])) {
						NPCHandler.npcs[i] = null;
					}
				}
			}
		}
	}
	
	public void gamble() {
		if (!player.getItems().playerHasItem(INFERNAL_CAPE)) {
			player.sendMessage("You do not have an Infernal cape to trade.");
			return;
		}
		player.getItems().deleteItem(INFERNAL_CAPE, 1);
		player.hasSacrificedFcape = true;
		
		if (Misc.random(200) == 0) {
			 if (player.getItems().getItemCount(30951, true) == 0 && player.summonId != 30951) {
				 PlayerHandler.executeGlobalMessage("[@red@PET@bla@] @cr20@<col=255> " + player.playerName + "</col> received a pet from <col=255>TzTok-Zek</col>.");
				 player.getItems().addItemUnderAnyCircumstance(30951, 1);
				 player.getDH().sendDialogues(6973, 2180);
			 }
		} else {
			player.getDH().sendDialogues(73, 2180);
			return;
		}
	}

	private static final int[] REWARD_ITEMS = { 6571, 6528, 11128, 6523, 6524, 6525, 6526, 6527, 6568, 6523, 6524, 6525, 6526, 6527, 6568 };

	public static final int INFERNAL_CAPE = 21295;

	public static final int TOKKUL = 6529;

	public static void reward(Player player) {

		player.getItems().addItemUnderAnyCircumstance(INFERNAL_CAPE, 1);
	}

	public int getKillsRemaining() {
		return killsRemaining;
	}

	public void setKillsRemaining(int remaining) {
		this.killsRemaining = remaining;
	}

}
