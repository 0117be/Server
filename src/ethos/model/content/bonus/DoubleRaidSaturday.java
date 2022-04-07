package ethos.model.content.bonus;

import java.util.Calendar;

public class DoubleRaidSaturday {

	public static boolean isDoubleRaid() {
		//return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}
}
