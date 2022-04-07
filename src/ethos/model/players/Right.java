package ethos.model.players;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import ethos.util.Misc;

/**
 * The rights of a player determines their authority. Every right can be viewed with a name and a value. The value is used to separate each right from one another.
 * 
 * @author Jason MacK
 * @date January 22, 2015, 5:23:49 PM
 */

public enum Right implements Comparator<Right> {
	PLAYER(0, "000000","0", ""),
	HELPER(11, "004080","7000", "Helper"),
	MODERATOR(1, "000000","16777215", "Moderator", HELPER),
	ADMINISTRATOR(2, "000000","16056064", "Administrator", MODERATOR),
	OWNER(3, "000000","16056064", "Owner", ADMINISTRATOR),
	UNKNOWN(4, "F5FF0F","7000", ""),
	MANAGER(25, "9400D3","7000", "Manager", OWNER),
	CONTRIBUTOR(5, "0027FF","7000", ""),
	SPONSOR(6, "1FFF00","7000", "", CONTRIBUTOR),
	SUPPORTER(7, "FF0000","7000", "", SPONSOR),
	DONATOR(8, "E0E0E0","7000", "", SUPPORTER),
	SUPER_DONATOR(9, "7605F5","7000", "", DONATOR),
	EXTREME_DONATOR(17, "9E6405","7000", "", SUPER_DONATOR),
	LEGENDARY(18, "F59E05","7000", "", EXTREME_DONATOR),
	UBER_DONATOR(19, "9E6405","7000", "", LEGENDARY),
	MAX_DONATOR(20, "9E6405","7000", "", UBER_DONATOR),
	
	RESPECTED_MEMBER(10, "272727","7000", ""),
	HITBOX(12, "437100","7000", ""),
	IRONMAN(13, "3A3A3A","7000", ""),
	ULTIMATE_IRONMAN(14, "717070","7000", ""),
	YOUTUBER(15, "FFFFFF","15859712", "", SPONSOR),
	GAME_DEVELOPER(16, "544FBB","7000", "Developer", OWNER),
	OSRS(23, "437100", "7000",""),
	HARDCORE(24, "9F2953","7000", ""),
	LAURA(66, "FF9FE9","7000", "", MODERATOR),
	ALPHA(65, "00FBFF","7000", "", MODERATOR),
	SENIST(68, "1300FF","7000", "", OWNER),
	FPM(67, "007CFF","7000", "", MODERATOR),
	BRNZ(38, "437100", "7000","");

	/**
	 * The level of rights that define this
	 */
	private final int right;

	/**
	 * The rights inherited by this right
	 */
	private final List<Right> inherited;

	/**
	 * The color associated with the right
	 */
	private final String color;
	private final String shad;
	private final String title;


	
	/**
	 * Creates a new right with a value to differentiate it between the others
	 * 
	 * @param right the right required
	 * @param color a color thats used to represent the players name when displayed
	 * @param inherited the right or rights inherited with this level of right
	 */
	private Right(int right, String color, String shad, String title, Right... inherited) {
		this.right = right;
		this.inherited = Arrays.asList(inherited);
		this.color = color;
		this.shad = shad;
		this.title = title;

	}

	/**
	 * The rights of this enumeration
	 * 
	 * @return the rights
	 */
	public int getValue() {
		return right;
	}

	/**
	 * Returns a {@link Rights} object for the value.
	 * 
	 * @param value the right level
	 * @return the rights object
	 */
	public static Right get(int value) {
		return RIGHTS.stream().filter(element -> element.right == value).findFirst().orElse(PLAYER);
	}

	public String getTitle() { return title; }

	/**
	 * A {@link Set} of all {@link Rights} elements that cannot be directly modified.
	 */
	private static final Set<Right> RIGHTS = Collections.unmodifiableSet(EnumSet.allOf(Right.class));

	/**
	 * The color associated with the right
	 * 
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	public String getShad() {
		return shad;
	}

	/**
	 * Determines if this level of rights inherited another level of rights
	 * 
	 * @param rights the level of rights we're looking to determine is inherited
	 * @return {@code true} if the rights are inherited, otherwise {@code false}
	 */
	public boolean isOrInherits(Right right) {
		/*if (this == right) 
			return true;
		for (int i = 0; i < inherited.size(); i++) {
			if (this == inherited.get(i))
				return true;
		}
		System.out.println("inherited.size: "+inherited.size());
		return false;*/
		return this == right || inherited.size() > 0 && inherited.stream().anyMatch(r -> r.isOrInherits(right));
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain MODERATOR}
	 * @return	true if they are of type moderator
	 */
	public boolean isModerator() {
		return equals(MODERATOR);
	}
	public boolean isLaura() {
		return equals(LAURA);
	}
	public boolean isFpm() {
		return equals(FPM);
	}
	public boolean isAlpha() {
		return equals(ALPHA);
	}
	public boolean isSenist() {
		return equals(SENIST);
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain HELPER}
	 * @return	true if they are of type moderator
	 */
	public boolean isHelper() {
		return equals(HELPER);
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain ADMINISTRATOR}
	 * @return	true if they are of type administrator
	 */
	public boolean isAdministrator() {
		return equals(ADMINISTRATOR);
	}
	
	/**
	 * Determines if the players rights equal that of {@linkplain OWNER}
	 * @return	true if they are of type owner
	 */
	public boolean isOwner() {
		return equals(OWNER);
	}
	public boolean isManager() {
		return equals(MANAGER);
	}

	/**
	 * Determines if the players right equal that of {@link MODERATOR}, {@link ADMINISTRATOR},
	 * and {@link OWNER}
	 * @return	true if they are any of the predefined types
	 */
	public boolean isStaff() {
		return isHelper() || isModerator() || isLaura()|| isFpm()  || isAlpha() || isSenist()||  isAdministrator() || isOwner() || isManager();
	}
	
	public boolean isManagment() {
		return isAdministrator() || isOwner() || isManager();
	}
	
	/**
	 * An array of {@link Right} objects that represent the order in which some rights should be prioritized over others. The index at which a {@link Right} object exists
	 * determines it's priority. The lower the index the less priority that {@link Right} has over another. The list is ordered from lowest priority to highest priority.
	 * <p>
	 * An example of this would be comparing a {@link #MODERATOR} to a {@link #ADMINISTRATOR}. An {@link #ADMINISTRATOR} can be seen as more 'powerful' when compared to a
	 * {@link #MODERATOR} because they have more power within the community.
	 * </p>
	 */

	public static final Right[] PRIORITY = { PLAYER, OSRS, IRONMAN, ULTIMATE_IRONMAN, HARDCORE, BRNZ,  CONTRIBUTOR, SPONSOR, SUPPORTER, DONATOR, SUPER_DONATOR, EXTREME_DONATOR, LEGENDARY, RESPECTED_MEMBER, HITBOX, YOUTUBER, HELPER,
			GAME_DEVELOPER, MODERATOR, ALPHA, LAURA, FPM, SENIST, ADMINISTRATOR, OWNER, MANAGER, UNKNOWN, };

	@Override
	public String toString() {
		return Misc.capitalizeJustFirst(name().replaceAll("_", " "));
	}

	@Override
	public int compare(Right arg0, Right arg1) {
		return 0;
	}

}
