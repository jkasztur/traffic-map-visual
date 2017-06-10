package cz.muni.fi.pb138.trafficmap.models;

import lombok.Getter;

public class District extends AbstractStatistics {

	@Getter
	private String districtName;

	public District(String districtName) {
		this.districtName = districtName;
	}
}
