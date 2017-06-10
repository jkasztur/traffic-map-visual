package cz.muni.fi.pb138.trafficmap.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Region extends AbstractStatistics {

	@Getter
	private String regionName;

	@Getter
	private List<District> districts;

	public Region(String regionName) {
		this.regionName = regionName;
		districts = new ArrayList<>();
	}

	public void addDistrict(District dist) {
		districts.add(dist);
	}
}
