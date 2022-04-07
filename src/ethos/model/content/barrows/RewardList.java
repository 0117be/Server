package ethos.model.content.barrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
class RewardList extends ArrayList<RewardItem> {

	RewardList() {
		addAll(Reward.asList());
	}

	public void reset() {
		clear();
		addAll(Reward.asList());
	}

	int getTotalWeight(int killCount) {
		int total = 0;
		for (RewardItem item : this) {
			int rarity = item.getRarityLevel().getRarity();
			if (item.getRarityLevel() == RewardLevel.COMMON) {
				rarity = firstTierRarity(killCount);
			}
			total += rarity;
		}
		return total;
	}

	int firstTierRarity(int killCount) {
		int size = (int) Reward.VALUES.stream().filter(reward -> reward.rarity == RewardLevel.COMMON).count();
		return RewardLevel.COMMON.getRarity() - (RewardLevel.KC_MULTIPLIER * killCount / size);
	}
	private enum Reward {

		DHAROK1(4716, 1, 1, RewardLevel.COMMON), 
		DHAROK2(4718, 1, 1, RewardLevel.COMMON), 
		DHAROK3(4720, 1, 1, RewardLevel.COMMON), 
		DHAROK4(4722, 1, 1, RewardLevel.COMMON), 

		GUTHANS(4724, 1, 1, RewardLevel.COMMON), 
		GUTHANS1(4726, 1, 1, RewardLevel.COMMON), 
		GUTHANS2(4728, 1, 1, RewardLevel.COMMON), 
		GUTHANS3(4730, 1, 1, RewardLevel.COMMON), 
		
		VERACS(4753, 1, 1, RewardLevel.COMMON), 
		VERACS1(4755, 1, 1, RewardLevel.COMMON), 
		VERACS2(4757, 1, 1, RewardLevel.COMMON), 
		VERACS3(4759, 1, 1, RewardLevel.COMMON), 
		
		KARILS(4732, 1, 1, RewardLevel.COMMON), 
		KARILS1(4734, 1, 1, RewardLevel.COMMON), 
		KARILS2(4736, 1, 1, RewardLevel.COMMON), 
		KARILS3(4738, 1, 1, RewardLevel.COMMON), 
		
		TORAGS(4745, 1, 1, RewardLevel.COMMON), 
		TORAGS1(4747, 1, 1, RewardLevel.COMMON), 
		TORAGS2(4749, 1, 1, RewardLevel.COMMON), 
		TORAGS3(4751, 1, 1, RewardLevel.COMMON),
		
		AHRIMS(4708, 1, 1, RewardLevel.COMMON), 
		AHRIMS1(4710, 1, 1, RewardLevel.COMMON),
		AHRIMS2(4712, 1, 1, RewardLevel.COMMON),
		AHRIMS3(4714, 1, 1, RewardLevel.COMMON),
		
		CRYSTAL_KEY(990, 1, 3, RewardLevel.COMMON), 
		BOLTRACK(4740, 100, 300, RewardLevel.COMMON), 
		
		
		SET(12873, 1, 1, RewardLevel.UNCOMMON), 
		SET1(12875, 1, 1, RewardLevel.UNCOMMON), 
		SET2(12877, 1, 1, RewardLevel.UNCOMMON), 
		SET3(12879, 1, 1, RewardLevel.UNCOMMON), 
		SET4(12881, 1, 1, RewardLevel.UNCOMMON), 
		SET5(12883, 1, 1, RewardLevel.UNCOMMON), 
		

		CLUE_HARD(2722, 1, 1, RewardLevel.UNCOMMON);

		private static final List<Reward> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

		private final int itemId;
		private final int minAmount;
		private final int maxAmount;
		private final RewardLevel rarity;

		Reward(int itemId, int minAmount, int maxAmount, RewardLevel rarity) {
			this.itemId = itemId;
			this.minAmount = minAmount;
			this.maxAmount = maxAmount;
			this.rarity = rarity;
		}

		public static List<RewardItem> asList() {
			return VALUES.stream().map(reward -> new RewardItem(reward.itemId, reward.minAmount, reward.maxAmount, reward.rarity)).collect(Collectors.toList());
		}

	}

}
