package ethos.model.players.packets;

import ethos.model.players.PacketType;
import ethos.model.players.Player;
import ethos.model.players.Right;

public class BankModifiableX implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int slot = player.getInStream().readUnsignedWordA();
		int component = player.getInStream().readUnsignedWord();
		int item = player.getInStream().readUnsignedWordA();
		int amount = player.getInStream().readInteger();
		if (player.debugMessage) {
			player.sendMessage("Bank ModifiableX: removeSlot: "+slot+", item: " + item+", amount: "+amount);
			player.sendMessage("interface: " + component);
		}
			
		if (player.getInterfaceEvent().isActive()) {
			player.sendMessage("Please finish what you're doing.");
			return;
		}
		if (player.getTutorial().isActive()) {
			player.getTutorial().refresh();
			return;
		}
		if (amount <= 0)
			return;
		switch (component) {
		case 5382: //bank (withdraw)
			if (player.getBank().getBankSearch().isSearching()) {
				player.getBank().getBankSearch().removeItem(item, amount);
				return;
			}
			player.getItems().removeFromBank(item, amount, true);
			break;
			
		case 5064: //bank (deposit)

			if (player.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) &&  item != 8839 
					&&  item != 11230 &&  item != 8842 
					&&  item != 11663 	&&  item != 11664 && item != 11665 
					&&  item != 13072 &&  item != 13073 	&&  item != 10551 	
					&&  item != 12954 	&&  item != 19722	&&  item != 7462 ) {
				player.sendMessage("@red@You can only deposit untradable items on this game mode!");
				
				return;
			} else 
				
			player.getItems().addToBank(item, amount, true);
			break;
			
		case 64016: //shop (buying)
			if(player.inWild() || player.inClanWars()) { //Fix wildy resource zone here inwild() && !inwildyzone
				return;
			}
			if (amount > 10000) {
				player.sendMessage("You can only buy 10,000 items at a time.");
				amount = 10000;
			}
			player.getShops().buyItem(item, slot, amount);// buy X
			player.xRemoveSlot = 0;
			player.xInterfaceId = 0;
			player.xRemoveId = 0;
			break;
		
		
		case 3823: //inv (selling)
			if(player.inWild() || player.inClanWars()) {
				return;
			}
			player.getShops().sellItem(item, slot, amount);// sell X
			player.xRemoveSlot = 0;
			player.xInterfaceId = 0;
			player.xRemoveId = 0;
			break;
		}
	}

}
