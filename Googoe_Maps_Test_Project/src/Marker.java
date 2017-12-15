
public class Marker {

	private double lat;
	private double lng;
	private String desc;
	
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return "Marker [lat=" + lat + ", lng=" + lng + ", desc=" + desc + "]";
	}
	
	
	
	
}
