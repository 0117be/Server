package ethos.model.wogw;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

import ethos.model.items.GameItem;

/**
 * 
 * @author Jason MacKeigan
 * @date Dec 21, 2014, 9:22:02 AM
 */
public enum WellOfGoodWillReward {
	TOP_HAT(2021, Calendar.NOVEMBER, 14, new GameItem(12432, 1)), 
	GILDED_SCIMITAR(2021, Calendar.NOVEMBER, 4, new GameItem(12389, 1)), 
	DRAGON_CANE(2021, Calendar.NOVEMBER, 21, new GameItem(12373, 1)), 
	RUNE_CANE(2021, Calendar.NOVEMBER, 28, new GameItem(12379, 1)), 
	BRONZE_DRAGON_MASK(2021, Calendar.DECEMBER, 5, new GameItem(12363, 1)), 
	IRON_DRAGON_MASK(2021, Calendar.DECEMBER, 12, new GameItem(12365, 1)), 
	STEEL_DRAGON_MASK(2021, Calendar.DECEMBER, 19, new GameItem(12367, 1)), 
	MITH_DRAGON_MASK(2021, Calendar.DECEMBER, 26, new GameItem(12369, 1));

	private int year, month, day;
	private GameItem reward;

	private WellOfGoodWillReward(int year, int month, int day, GameItem reward) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.reward = reward;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public GameItem getReward() {
		return reward;
	}

	public static Optional<WellOfGoodWillReward> getReward(int year, int month, int day) {
		return Arrays.asList(values()).stream().filter(wogw -> wogw.getYear() == year && wogw.getMonth() == month && wogw.getDay() == day).findFirst();
	}
}
