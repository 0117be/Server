package ethos.model.players.mode;

import ethos.model.items.Item;
import ethos.model.minigames.pest_control.PestControlRewards.RewardButton;
import ethos.model.players.Right;
import ethos.util.Misc;

public class UltimateIronmanMode extends Mode {

	public UltimateIronmanMode(ModeType type) {
		super(type);
	}
	
	public boolean isUltimateIronman() {
		return true;
	}

	@Override
	public boolean isTradingPermitted() {
		return false;
	}

	@Override
	public boolean isStakingPermitted() {
		return false;
	}

	@Override
	public boolean isItemScavengingPermitted() {
		return false;
	}

	@Override
	public boolean isPVPCombatExperienceGained() {
		return true;
	}

	@Override
	public boolean isDonatingPermitted() {
		return false;
	}

	@Override
	public boolean isVotingPackageClaimable(String packageName) {
		if (packageName.equals("Vote Ticket")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isShopAccessible(int shopId) {
		switch (shopId) {
		case 77:
		case 41:
		case 6:
		case 151:
		case 85:
		case 40:
		case 150:
		case 9:
		case 44:
		case 78:
		case 10:
		case 83:
		case 152:
		case 22:
		case 20:
		case 26:
		case 16:
		case 137:
		case 13:
		case 121:
		case 14:
		case 136:
			case 120:
		case 17:
		case 18:
		case 19:
		case 29:
		case 79:
		case 80:
		case 23:
		case 115:
		case 82:
		case 116:
		case 81:
		case 12:
		case 118:
		case 119:
		case 124:
		case 128:
			/*
			 * case 78: case 48: case 44: case 40: case 26: case 22: case 20: case 14: case 12: case 2:
			 */
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemPurchasable(int shopId, int itemId) {
		switch (shopId) {
		case 26:
		case 14:
		case 44:
		case 136:
		case 83:
		case 151:
		case 121:
		case 10:
		case 40:
		case 12:
		case 85:
		case 41:
		case 152:
		case 17:
		case 18:
		case 137:
		case 19:
		case 13:
		case 29:
		case 79:
		case 9:
		case 150:
		//case 77:
		case 80:
		case 23:
		case 115:
		case 116:
		case 81:
		case 118:
		case 119:
		case 128:
			return true;
			
		case 6:
			if (Item.getItemName(itemId).contains("rune")) {
				return true;
			}
			switch (itemId) {
			case 1391:
			case 4089:
			case 4091:
			case 4093:
			case 4095:
			case 4097:
				return true;
			}
			break;
			
		case 124:
			switch (itemId) {
			case 22118:
			case 2368:
			case 22103:
				return true;
			}
			break;
		case 82:
			switch (itemId){
			case 10548:
			case 10551:
			case 11898:
			case 11897:
			case 11896:
			case 11899:
			case 11900:
				return true;
			}
			break;

		case 78:
			if (itemId != 6199 && itemId != 2677 && itemId != 2572) {
				return true;
			}
			break;

		case 77:
			if (itemId != 2572 && itemId != 6199) {
				return true;
			}
			break;

		case 48:
			if (itemId == 10499) {
				return true;
			}
			break;

		case 22:
			if (itemId != 314 && itemId != 317 && itemId != 335 && itemId != 377) {
				return true;
			}
			break;

		case 20:
			if (itemId == 1755 || itemId == 1597 || itemId == 1592 || itemId == 1595 || itemId == 1734 || itemId == 1733 || itemId == 1775 || itemId == 1785) {
				return true;
			}
			break;

		case 16:
			if (itemId == 5341 || itemId == 5343 || itemId == 5340 || itemId == 1925) {
				return true;
			}
			break;

	
		case 25:
			if (itemId == 1617 || itemId == 1619 || itemId == 1621 || itemId == 1623 || itemId == 1601 || itemId == 1603 || itemId == 1605 || itemId == 1607 || itemId == 1623 || itemId == 1615 || itemId == 1739) {
				return false;
			}
			break;
		case 2:
			int[] disallowed = { 314, 1437, 1275, 1359, 1778, 221, 235, 1526, 223, 9736, 231, 225, 239, 243, 6049, 3138, 6693, 245, 6051 };
			if (Misc.linearSearch(disallowed, itemId) != -1) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemSellable(int shopId, int itemId) {
		switch (shopId) {
		case 26:
		case 29:
		case 41:
		case 12:
		case 85:
		case 115:
		case 150:
		case 152:
			
		case 121:
		case 151:
		case 116:
		case 83:
			return true;
		case 44:
			if (Item.getItemName(itemId).contains("head"))
				return true;

		}
		return false;
	}

	@Override
	public int getModifiedShopPrice(int shopId, int itemId, int price) {
		switch (shopId) {		
		case 81:
			if (itemId == 2368) {
				price = 5000000;
			}
			break;
		case 41:
			if (itemId == 10499 || itemId == 10498) {
				price = 100000;
			} else if (itemId == 221) {
				price = 325;
			} else if (itemId == 314) {
				price = 12;
			} else if (itemId == 385) {
				price = 4500;
			} else if (itemId == 3026) {
				price = 30000;
			} else if (itemId == 139) {
				price = 15000;
			} else if (itemId == 6687) {
				price = 22500;
			} else if (itemId == 3105) {
				price = 200000;
			} else if (itemId == 9470) {
				price = 1500000;
			} else if (itemId == 430 || itemId == 10394 || itemId == 662 || itemId == 1833 || itemId == 1835 || itemId == 1837) {
				price = 150000;
			}
			break;
		case 22:
			if (itemId == 314) {
				price = 12;
			}
		}
		return price;
	}

	@Override
	public boolean isBankingPermitted() {
		return true;
	}

	@Override
	public boolean isRewardSelectable(RewardButton reward) {
		switch (reward) {
		case FIGHTER_TORSO:
		case FIGHTER_HAT:
		case BARROWS_GLOVES:
		case VOID_KNIGHT_ROBE:
		case VOID_MAGE_HELM:
		case VOID_MELEE_HELM:
		case VOID_KNIGHT_TOP:
		case VOID_KNIGHT_GLOVES:
		case VOID_RANGE_HELM:
			return true;

		default:
			return false;
		}
	}

}
