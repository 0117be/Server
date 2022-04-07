package ethos.model.players.combat.magic;

import java.util.stream.IntStream;

import ethos.Config;
import ethos.model.npcs.NPC;
import ethos.model.npcs.NPCHandler;
import ethos.model.players.Player;
import ethos.util.Misc;

public class MagicMaxHit {

	public static int mageAttack(Player c) {
		double equipmentBonus = c.playerBonus[3];
		double magicLevel = c.playerLevel[6];
		double magicPrayer = c.prayerActive[4] ? 1.05 : c.prayerActive[12] ? 1.10 : c.prayerActive[20] ? 2.0 : 2.0;
		double accuracy = (((equipmentBonus + magicLevel) * 1.7) * magicPrayer);
		double modifier = 1.2;
		if (c.fullVoidMage()) {
			modifier *= 1.3;
		}
		if (c.inWild() && c.npcIndex < 0) {
			modifier *= 1.2;
			accuracy = 1.2;
		}
		if (c.getItems().getItemCount(10944, true) == 1) {	
			if (c.npcIndex > 0)
				modifier += 1.4;									
			}
		if (c.getItems().isWearingItem(4710, c.playerWeapon)) {
			modifier *= 1.02;
		}
		if (c.getItems().isWearingItem(22647, c.playerWeapon)) {
			modifier *= 1.05;
		}
		if (c.getItems().isWearingItem(22516, c.playerWeapon)) {
			modifier *= 1.05;
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
		//	NPC npc = NPCHandler.npcs[c.npcIndex];
		//	if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				//	boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
			//	if (!c.getItems().isWearingItem(4081) && (c.getItems().isWearingItem(11865) || c.getItems().isWearingItem(11784) || c.getItems().isWearingItem(19641) || c.getItems().isWearingItem(19645) || c.getItems().isWearingItem(19649) || c.getItems().isWearingItem(21266))) {
			//		modifier *= 1.1667;
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName())) {
				//	boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (!c.getItems().isWearingItem(4081) && (c.getItems().isWearingItem(11865) || c.getItems().isWearingItem(11784) || c.getItems().isWearingItem(19641) || c.getItems().isWearingItem(19645) || c.getItems().isWearingItem(19649) || c.getItems().isWearingItem(21890) || c.getItems().isWearingItem(21266))) {
					modifier *= 1.5;
					if (c.debugMessage)
						c.sendMessage("Magic Attack on slayer: "+ modifier +"");
				}
			}
		}
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
				if (c.inWild()) {
					if (c.playerEquipment[c.playerWeapon] == 22555) {
						modifier *= 2.5;
						if (c.debugMessage)
							c.sendMessage("Magic Attack Thammaron's: "+ modifier +"");
					}
			}
		}
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
				if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
					if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
						modifier *= 1.60;
						if (c.debugMessage)
						c.sendMessage("Magic Attack on Undead: "+ modifier +"");
					}
				}
		}
		
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
				if (c.getItems().isWearingItem(12017, c.playerAmulet)) {
					if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
						modifier *= 1.6;
						if (c.debugMessage)
						c.sendMessage("Magic Attack on Unded: "+ modifier +"");
					}
				}			
			
		

		if (c.getItems().isWearingItem(22557, c.playerAmulet)) {
			if (Misc.linearSearch(Config.REV_NPC, npc.npcType) != -1) {
				modifier *= 1.6;
				if (c.debugMessage)
					c.sendMessage("Damage on Revs: "+ modifier +"");
			}
			
		}
		}
	/*	if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
					if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
						if (c.getItems().isWearingItem(11865) || c.getItems().isWearingItem(11784) || c.getItems().isWearingItem(19641) || c.getItems().isWearingItem(19645) || c.getItems().isWearingItem(19649) || c.getItems().isWearingItem(21890) || c.getItems().isWearingItem(21266) || c.getItems().isWearingItem(23075)) {
					modifier *= 1.1667;
					if (c.debugMessage)
						c.sendMessage("Magic Attack on slayer: "+ modifier +"");
				}
			}
		}*/
		return (int) (accuracy * modifier);
	}

	public static int mageDefence(Player c) {
		double prayerDefence = c.prayerActive[0] ? 1.05 : c.prayerActive[5] ? 1.10 : c.prayerActive[13] ? 1.15 : c.prayerActive[24] ? 1.20 : c.prayerActive[25] ? 1.25 : 1.0;
		double defence = Math.floor((c.playerLevel[1] * prayerDefence) * .3);
		double magicDefence = Math.floor(c.playerLevel[6] * .7);
		defence += magicDefence + c.playerBonus[8];
		return (int) defence;
	}

	public static int magiMaxHit(Player c) {
		double damage = MagicData.MAGIC_SPELLS[c.oldSpellId][6];
		double damageMultiplier = 1;
		switch (c.playerEquipment[c.playerWeapon]) {
			case 4710:
				damageMultiplier += .15;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			case 22516:
				damageMultiplier += .40;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			case 12899:
				damageMultiplier += .10;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			
			case 22647:
				damageMultiplier += .50;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			case 22323:
			case 30919:
				damageMultiplier += .30;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			case 6914:
				damageMultiplier += .30;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			case 21006:
				damageMultiplier += .60;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
			case 20604:
				damageMultiplier += .15;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			case 11791:
			case 22296:
				damageMultiplier += 0.30;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
			case 12904:
				damageMultiplier += 0.15;
				if (c.debugMessage)
				c.sendMessage("Multiplier from weapon: "+ damageMultiplier +"");
				break;
		}
		switch (c.playerEquipment[c.playerShield]) {
		case 20714:// Tome of fire
			if(c.spellId == 3 || c.spellId == 7 || c.spellId == 11 || c.spellId == 15 || c.autocastId == 15 || c.autocastId == 3 || c.autocastId == 7 || c.autocastId == 11){
				damageMultiplier += .5;
					if (c.debugMessage)
						c.sendMessage("Multiplier from shield: "+ damageMultiplier +"");
				}
				break;
			case 18346:// Tome of frost
				if(c.spellId == 1 || c.spellId == 5 || c.spellId == 9 || c.spellId == 13 || c.autocastId == 1 || c.autocastId == 5 || c.autocastId == 9 || c.autocastId == 13){
					damageMultiplier += .5;
					if (c.debugMessage)
						c.sendMessage("Multiplier from shield: "+ damageMultiplier +"");
				}
				break;

		}
		if (c.playerEquipment[c.playerAmulet] == 12002 || c.playerEquipment[c.playerAmulet] == 19720) {
			damageMultiplier += .10;
			if (c.debugMessage)
				c.sendMessage("Multiplier from Occult: "+ damageMultiplier +"");
		}
		if(c.fullVoidMage()) {
			damageMultiplier +=0.025;
			if (c.debugMessage)
				c.sendMessage("Multiplier from void: "+ damageMultiplier +"");
		}
		if (c.playerEquipment[c.playerCape] == 21791 || c.playerEquipment[c.playerCape] == 21793 || c.playerEquipment[c.playerCape] == 21795 ||c.playerEquipment[c.playerCape] == 21793 ||c.playerEquipment[c.playerCape] == 21776 || c.playerEquipment[c.playerCape] == 21780 || c.playerEquipment[c.playerCape] == 21784) {
			damageMultiplier +=0.02;
			if (c.debugMessage)
				c.sendMessage("Multiplier from cape: "+ damageMultiplier +"");
		}
		if (c.playerEquipment[c.playerHat] == 21018) {
			damageMultiplier += 0.02;
			if (c.debugMessage)
				c.sendMessage("Multiplier from Ancestral Hat: "+ damageMultiplier +"");
		}
		if (c.playerEquipment[c.playerHands] == 19544) {
			damageMultiplier += 0.05;
			if (c.debugMessage)
				c.sendMessage("Multiplier from Tormented Bracelet: "+ damageMultiplier +"");
		}
		if (c.playerEquipment[c.playerChest] == 21021) {
			damageMultiplier += 0.02;	
			if (c.debugMessage)
				c.sendMessage("Multiplier from Ancestral Body: "+ damageMultiplier +"");
		}
		if (c.playerEquipment[c.playerLegs] == 21024) {
			damageMultiplier += 0.02;
			if (c.debugMessage)
				c.sendMessage("Multiplier from Ancestral Legs: "+ damageMultiplier +"");
		}

	/*	if (c.npcIndex > 0) {
			if (c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (SLAYER_HELM) {
					damageMultiplier += .1667;
					if (c.debugMessage)
						c.sendMessage("Multipler on slayer: "+ damageMultiplier +"");
				}
				}
			}
		}*/
	/*	if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				if (c.getItems().isWearingItem(11865) || c.getItems().isWearingItem(11784) || c.getItems().isWearingItem(19641) || c.getItems().isWearingItem(19645) || c.getItems().isWearingItem(19649) || c.getItems().isWearingItem(21890) || c.getItems().isWearingItem(21266)) {
					damageMultiplier += .15;
					if (c.debugMessage)
						c.sendMessage("Multipler on slayer: "+ damageMultiplier +"");
				}
			}
		}*/
		if (c.npcIndex > 0) {
				if (c.inWild()) {
					if (c.playerEquipment[c.playerWeapon] == 22555) {
						damageMultiplier += 0.25;
						if (c.debugMessage)
							c.sendMessage("Multiplier with Thammaron's: "+ damageMultiplier +"");
					}
			}
		}
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
				if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
					if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
						damageMultiplier += 0.20;
						if (c.debugMessage)
						c.sendMessage("Multiplier on Undead: "+ damageMultiplier +"");
					}
				}
		}
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
				if (c.getItems().isWearingItem(12017, c.playerAmulet)) {
					if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
						damageMultiplier += 0.20;
						if (c.debugMessage)
						c.sendMessage("Multiplier on Unded: "+ damageMultiplier +"");
					}
				}			
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (SLAYER_HELM) {
					damageMultiplier += .1667;
					if (c.debugMessage)
					c.sendMessage("Multiplier on Slayer: "+ damageMultiplier +"");
				}
			}
		}
		switch (MagicData.MAGIC_SPELLS[c.oldSpellId][0]) {
		case 12037:
			if (c.getItems().isWearingAnyItem(21255)) {
				damage += (c.playerLevel[6] / 6) + 5;
			} else {
				damage += c.playerLevel[6] / 10;
			}
			break;
		}

		damage *= damageMultiplier;
		/**
		 * Start of Max Hit Debugging.
		 * Always put in this order Set effects > Single item, Non offensive > Weapons/Offensive item > Slayer buffs (Helmet, Pet, Prestige reward)
		 * Written by Kid Buu
		 */
		if(c.fullVoidMage()) {
			if (c.debugMessage)
				c.sendMessage("Max with void: "+ damage +"");
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (!c.getItems().isWearingItem(4081) || !c.getItems().isWearingItem(12018) || !c.getItems().isWearingItem(10588) || !c.getItems().isWearingItem(12017) && SLAYER_HELM) {
					if (c.debugMessage)
						c.sendMessage("Max on Slayer task: "+ damage +"");
				}
			}
		}
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (c.inWild()) {
				if (c.playerEquipment[c.playerWeapon] == 22555) {
					if (c.debugMessage)
						c.sendMessage("Max with Thammaron's: "+ damage +"");
				}
		}
			if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					if (c.debugMessage)
					c.sendMessage("Max on undead: "+ damage +"");
				}
			}
			if (c.getItems().isWearingItem(12017, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					if (c.debugMessage)
					c.sendMessage("Max on undead: "+ damage +"");
				}
			}			
		}
		
		if (c.playerEquipment[c.playerAmulet] == 12002 || c.playerEquipment[c.playerAmulet] == 19720) {
			if (c.debugMessage)
				c.sendMessage("Max from Occult: "+ damage +"");
		}
		if (c.playerEquipment[c.playerCape] == 21791 || c.playerEquipment[c.playerCape] == 21793 || c.playerEquipment[c.playerCape] == 21795 ||c.playerEquipment[c.playerCape] == 21793 ||c.playerEquipment[c.playerCape] == 21776 || c.playerEquipment[c.playerCape] == 21780 || c.playerEquipment[c.playerCape] == 21784) {
			if (c.debugMessage)
				c.sendMessage("Max from cape: "+ damage +"");
		}
		if (c.playerEquipment[c.playerHat] == 21018) {
			if (c.debugMessage)
				c.sendMessage("Max from Ancestral Hat: "+ damage +"");
		}
		if (c.playerEquipment[c.playerHands] == 19544) {
			if (c.debugMessage)
				c.sendMessage("Max from Tormented Bracelet: "+ damage +"");
		}
		if (c.playerEquipment[c.playerChest] == 21021) {
			if (c.debugMessage)
				c.sendMessage("Max from Ancestral Body: "+ damage +"");
		}
		if (c.playerEquipment[c.playerLegs] == 21024) {
			if (c.debugMessage)
				c.sendMessage("Max from Ancestral legs: "+ damage +"");
		}
		if (c.playerEquipment[c.playerWeapon] == 4710 || c.playerEquipment[c.playerWeapon] == 6914 || c.playerEquipment[c.playerWeapon] == 21006 || c.playerEquipment[c.playerWeapon] == 20604 || c.playerEquipment[c.playerWeapon] == 11791 || c.playerEquipment[c.playerWeapon] == 22296 || c.playerEquipment[c.playerWeapon] == 12904) {
			if (c.debugMessage)
				c.sendMessage("Max from Weapon: "+ damage +"");
		}
		return (int) damage;
		
		
	}
}