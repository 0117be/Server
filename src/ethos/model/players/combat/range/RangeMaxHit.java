package ethos.model.players.combat.range;

import java.util.stream.IntStream;

import ethos.Config;
import ethos.model.items.Item;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Player;
import ethos.util.Misc;

public class RangeMaxHit extends RangeData {

	public static int calculateRangeDefence(Player c) {
		int defenceLevel = c.playerLevel[1];
		if (c.prayerActive[0]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
		} else if (c.prayerActive[5]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
		} else if (c.prayerActive[13]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
		} else if (c.prayerActive[26]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
		} else if (c.prayerActive[27]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
		} else if (c.prayerActive[28]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
		}
		return defenceLevel + c.playerBonus[9] + (c.playerBonus[9] / 2);
	}

	public static int calculateRangeAttack(Player c) {
		/**
		 * All Range Level debugging written by Kid Buu. I know its easy But its something I am proud of at this point for the quality of the game and the sake of my future code.
		 * Always put in this order Set effects > Single item, Non offensive > Weapons/Offensive item > Slayer buffs (Helmet, Pet, Prestige reward)
		 */
		int rangeLevel = c.playerLevel[4];
		if (c.playerIndex > 0) {
			rangeLevel *= c.specAccuracy;
		}
		if (c.npcIndex > 0 && c.getItems().isWearingItem(20997)) {
			rangeLevel += 1.20;
		}
		if (c.fullVoidRange()) {
			rangeLevel += c.getLevelForXP(c.playerXP[c.playerRanged]) * 0.1;
			if (c.debugMessage)
			c.sendMessage("Range Level void: "+ rangeLevel +"");

		}
		if (c.getItems().getItemCount(10942, true) == 1) {	
			if (c.npcIndex > 0)
				rangeLevel *= 1.1;										
			}
		if (c.prayerActive[3]) {
			rangeLevel *= 1.05;
		} else if (c.prayerActive[11]) {
			rangeLevel *= 1.10;
		} else if (c.prayerActive[19]) {
			rangeLevel *= 1.15;
		} else if (c.prayerActive[27]) {
			rangeLevel *= 1.23;
		}
		if (c.fullVoidRange() && c.specAccuracy > 1.15) {
			rangeLevel *= 1.75;
			if (c.debugMessage)
			c.sendMessage("Range Level Void W/ Spec: "+ rangeLevel +"");
		}
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
		if (c.getItems().isWearingItem(12017, c.playerAmulet)) {
			if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1 || npc.npcType == 7413) {
				rangeLevel *= 1.15;
				if (c.debugMessage)
				c.sendMessage("Range Level salve: "+ rangeLevel +"");
			}
		}
		if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
			if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1 || npc.npcType == 7413) {
				rangeLevel *= 1.20;
				if (c.debugMessage)
				c.sendMessage("Range Level salve: "+ rangeLevel +"");
			}
		}
		if (c.npcIndex > 0 && c.inWild()) {
		if (c.playerEquipment[c.playerWeapon] == 22550) {
			rangeLevel *= 2.5;
			if (c.debugMessage)
			c.sendMessage("Multiplier with Craws: "+ rangeLevel +"");
		}
		}
		if (c.getItems().isWearingItem(21012, c.playerWeapon)) {
			if (Misc.linearSearch(Config.DRAGON_IDS, npc.npcType) != -1 || npc.npcType == 7413) {
				rangeLevel *= 1.50;
				if (c.debugMessage)
				c.sendMessage("Range Level dragons: "+ rangeLevel +"");
			}
		}
	}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (!c.getItems().isWearingItem(4081) || !c.getItems().isWearingItem(12018) || !c.getItems().isWearingItem(10588) || !c.getItems().isWearingItem(12017) && SLAYER_HELM) {
					rangeLevel *= 1.1667;
					if (c.debugMessage)
						c.sendMessage("Range Level slayer: "+ rangeLevel +"");
				}
			}

		}
		return (int) (rangeLevel + (c.playerBonus[4] * 1.95));
	}
	
	public static boolean wearingCrystalBow(Player c) {
		return c.playerEquipment[3] != -1 && Item.getItemName(c.playerEquipment[3]).toLowerCase().contains("crystal bow");
	}
	
	public static boolean wearingCrawsBow(Player c) {
		return c.playerEquipment[3] != -1 && Item.getItemName(c.playerEquipment[3]).toLowerCase().contains("craws bow");
	}

	public static int maxHit(Player c) {
		int rangeLevel = c.playerLevel[4];
		int rangedStrength = 
			/**
			 * If a player is NOT using a blowpipe or crystal bow
			 * But is using a bow or ballista, we grab the strength from the arrow slot
			 */
			c.playerEquipment[c.playerWeapon] != 12926 && !wearingCrystalBow(c) && !wearingCrawsBow(c) && c.usingBow || c.usingBallista ? getRangeStr(c.playerEquipment[c.playerArrows]) :
			
			/**
			 * If a player IS using a blowpipe
			 * We grab the strength from the ammo which is saved on players accounts
			 */
			c.playerEquipment[c.playerWeapon] == 12926 ? getRangeStr(c.getToxicBlowpipeAmmo()) : 
				
			/**
			 * If a player IS using a crystal bow
			 * We grab the strength from the weapon slot
			 */
			wearingCrystalBow(c) ? getRangeStr(c.playerEquipment[c.playerWeapon]) :
				
				/**
				 * If a player IS using a craws bow
				 * We grab the strength from the weapon slot
				 */
				wearingCrawsBow(c) ? getRangeStr(c.playerEquipment[c.playerWeapon]) :
			/**
			 * If none of the above is applicable
			 * We grab the strength from the weapon slot and divide it by 2
			 */
			getRangeStr(c.playerEquipment[c.playerWeapon]) / 3;
			
			/**
			 * All Damage Multiplier debugging written by Kid Buu. I know its easy But its something I am proud of at this point for the quality of the game and the sake of my future code.
			 * Always put in this order Set effects > Single item, Non offensive > Weapons/Offensive item > Slayer buffs (Helmet, Pet, Prestige reward)
			 */
		double b = 1.00;
		if (c.prayerActive[3]) {
			b *= 1.05;
		} else if (c.prayerActive[11]) {
			b *= 1.10;
		} else if (c.prayerActive[19]) {
			b *= 1.15;
		} else if (c.prayerActive[27]) {
			b *= 1.23;
		}
		if (c.npcIndex > 0 && c.getItems().isWearingItem(20997)) {
			b*= 2.50;
			if (c.debugMessage)
			c.sendMessage("Multiplier with Twisted Bow: "+ b +"");
		}
		if (c.fullVoidRange()) {
			b *= 1.20;
			if (c.debugMessage)
			c.sendMessage("Multiplier with Void: "+ b +"");
		}
		if (c.npcIndex > 0 && c.inWild()) {
		if (c.playerEquipment[c.playerWeapon] == 22550) {
			b *= 1.50;
			if (c.debugMessage)
			c.sendMessage("Multiplier with Craws: "+ b +"");
		}
		}
		if (c.getItems().isWearingItem(19547, c.playerAmulet)) {
			b *= 1.05;
			if (c.debugMessage)
			c.sendMessage("Damage with anguish: "+ b +"");
		}
		if (c.getItems().isWearingItem(22249, c.playerAmulet)) {
			b *= 1.05;
			if (c.debugMessage)
			c.sendMessage("Damage with anguish: "+ b +"");
		}
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
		if (c.getItems().isWearingItem(12017, c.playerAmulet)) {
			if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1 || npc.npcType == 7413) {
				b *= 1.15;
				if (c.debugMessage)
				c.sendMessage("Multipler on undead: "+ b +"");
			}
		}
		if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
			if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1 || npc.npcType == 7413) {
				b *= 1.20;
				if (c.debugMessage)
				c.sendMessage("Multipler on undead: "+ b +"");
			}
		}
		if (c.getItems().isWearingItem(21012, c.playerWeapon)) {
			if (Misc.linearSearch(Config.DRAGON_IDS, npc.npcType) != -1 || npc.npcType == 7413) {
				b *= 1.50;
				if (c.debugMessage)
				c.sendMessage("Multipler on dragons: "+ b +"");
			}
		}

	
		if (c.getItems().isWearingItem(22557, c.playerAmulet)) {
			if (Misc.linearSearch(Config.REV_NPC, npc.npcType) != -1) {
				b *= 1.5;
				if (c.debugMessage)
					c.sendMessage("Damage on Revs: "+ b +"");
			}
			
		}
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName())) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (!c.getItems().isWearingItem(4081) || !c.getItems().isWearingItem(12018) || !c.getItems().isWearingItem(10588) || !c.getItems().isWearingItem(12017) && SLAYER_HELM) {
					b *= 1.5;
					if (c.debugMessage)
						c.sendMessage("Multipler on slayer: "+ b +"");
				}
			}
		}
		double e = Math.floor(rangeLevel * b);
		if (c.fightMode == 0) {
			e = (e + 3.0);
		}
		double darkbow = 1.0;
		if (c.usingSpecial) {
			if (c.playerEquipment[3] == 11235 || c.playerEquipment[3] == 12765 || c.playerEquipment[3] == 12766 || c.playerEquipment[3] == 12767 || c.playerEquipment[3] == 12768) {
				if (Arrow.matchesMaterial(c.lastArrowUsed, Arrow.DRAGON)) {
					darkbow = 1.05;
				} else {
					darkbow = 1.01;
				}
			}
		}
		double max = (1.3 + e / 10 + rangedStrength / 80 + e * rangedStrength / 640) * darkbow;
		if (c.usingSpecial) {
			max *= c.specDamage;
		}	
		/**
		 * Start of Max Hit Debugging.
		 * Always put in this order Set effects > Single item, Non offensive > Weapons/Offensive item > Slayer buffs (Helmet, Pet, Prestige reward)
		 * Written by Kid Buu
		 */
		if (c.getItems().isWearingItem(20997, c.playerWeapon)) {
			if (c.debugMessage) {
			c.sendMessage("Twisted Bow Damage: "+ max +"");
			}
		}
		if (c.fullVoidRange()) {
			if (c.debugMessage)
			c.sendMessage("Max with Void: "+ b +"");
		}	
		if (c.getItems().isWearingItem(19547, c.playerAmulet)) {
			if (c.debugMessage) {
				c.sendMessage("Max with Anguish: "+ max +"");
				}
		}		
		if (c.getItems().isWearingItem(22247, c.playerAmulet)) {
			if (c.debugMessage) {
				c.sendMessage("Max with Anguish: "+ max +"");
				}
		}
		if (c.getItems().isWearingItem(12017, c.playerAmulet)) {
			if (c.debugMessage) {
			c.sendMessage("Damage on undead: "+ max +"");
			}
		}
		if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
			if (c.debugMessage) {
			c.sendMessage("Max on undead: "+ max +"");
			}
		}
			if (c.inWild()) {
				if (c.playerEquipment[c.playerWeapon] == 22550) {
					if (c.debugMessage) {
						c.sendMessage("Max with Craws in wild: "+ max +"");
						}	
				}
			}
			if (c.getItems().isWearingItem(21012, c.playerWeapon)) {
				if (c.debugMessage) {
				c.sendMessage("Max on dragons: "+ max +"");
				}
			}
			if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
				NPC npc = NPCHandler.npcs[c.npcIndex];
				if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
					boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
					if (!c.getItems().isWearingItem(4081) || !c.getItems().isWearingItem(12018) || !c.getItems().isWearingItem(10588) || !c.getItems().isWearingItem(12017) && SLAYER_HELM) {
						if (c.debugMessage)
							c.sendMessage("Max on Slayer: "+ max +"");
					}
				}
			}
			/**
			 * End of Max hit Debugging
			 * Written by Kid Buu
			 */
		return (int) max;
	}
}