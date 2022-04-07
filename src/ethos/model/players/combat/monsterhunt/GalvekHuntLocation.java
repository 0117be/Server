package ethos.model.players.combat.monsterhunt;

public class GalvekHuntLocation {

	private int x;
	
	private int y;
	
	private int heightLevel;
	
	private String locationName;
		
	public GalvekHuntLocation(int x, int y, int heightLevel, String locationName) {
		this.x = x;
		this.y = y;
		this.heightLevel = heightLevel;
		this.locationName = locationName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getHeight() {
		return heightLevel;
	}
	
	public void setHeight(int heightLevel) {
		this.heightLevel = heightLevel;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}