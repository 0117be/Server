package ethos.model.players.mode;

import ethos.model.minigames.pest_control.PestControlRewards.RewardButton;


public class BrnzMode extends Mode {
	
	/**
	 * Creates a new default mode
	 * 
	 * @param type the default mode
	 */
	public BrnzMode(ModeType type) {
		
		
		super(type);
	}

	@Override
	public boolean isTradingPermitted() {
		return true;
	}
	public boolean isDroppingPermitted() {
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
		return true;
	}

	@Override
	public boolean isVotingPackageClaimable(String packageName) {
		return true;
	}

	@Override
	public boolean isShopAccessible(int shopId) {
		switch (shopId) {
		case 44:
		case 29:
		case 18:
		case 14:
		case 10:
		case 17:
		case 121:
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemPurchasable(int shopId, int itemId) {
		switch (shopId) {
		case 44:
		case 29:
		case 10:
		case 17:
		case 14:
		case 121:
		case 18:
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
		case 113:
			if (itemId == 385) {
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
		}
		return price;
	}

	@Override
	public boolean isItemSellable(int shopId, int itemId) {
		switch (shopId) {
		case 41:
		case 18:
		case 29:
			return true;
		case 113:
			if (itemId == 385 || itemId == 139 || itemId == 3026 || itemId == 6687) {
				return false;
			}
			break;
		}
		return true;
	}

	@Override
	public boolean isBankingPermitted() {
		return true;
	}

	@Override
	public boolean isRewardSelectable(RewardButton reward) {
		return true;
	

	}

}
