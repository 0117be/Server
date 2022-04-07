package ethos.model.content.trails;

import java.util.Arrays;
import java.util.List;

import ethos.util.Misc;





public enum RewardLevel {
	SHARED, EASY, MEDIUM, HARD, MASTER;
	
	public String getFormattedName() {
		return Misc.formatPlayerName(name().toLowerCase());
	}
	public static final List<RewardLevel> VALUES = Arrays.asList(values());
	
	public static List<RewardLevel> getValues() {
		return VALUES;
}
}
