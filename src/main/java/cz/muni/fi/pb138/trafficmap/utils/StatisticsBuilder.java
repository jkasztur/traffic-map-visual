package cz.muni.fi.pb138.trafficmap.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pb138.trafficmap.models.AbstractStatistics;
import cz.muni.fi.pb138.trafficmap.models.District;
import cz.muni.fi.pb138.trafficmap.models.Region;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatisticsBuilder {

	public static String getJSONRegions() {
		List<AbstractStatistics> regions = new ArrayList<>(getRegions());
		return getJSONStatistic(regions);
	}

	public static String getJSONDistricts() {
		List<AbstractStatistics> districts = new ArrayList<>(getDistricts());
		return getJSONStatistic(districts);
	}

	private static String getJSONStatistic(List<AbstractStatistics> list) {
		String result = null;
		ObjectMapper mapper = new ObjectMapper();

		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, list);
			result = sw.toString();
		} catch (IOException ex) {
			log.error("IOException:" + ex.getMessage());
		}
		return result;
	}

	private static List<Region> getRegions() {
		List<Region> regions = new ArrayList<>();
		for (int i = 1; i < 15; i++) {
			final String regionId = "p" + i;
			final Region region = new Region(regionId);
			setStatisticInfo(regionId, region);
			regions.add(region);
		}
		log.info("Found {} regions.", regions.size());
		final String republicId = "p90";
		final Region republic = new Region(republicId);
		setStatisticInfo(republicId, republic);
		regions.add(republic);
		log.info("Added total info.");

		return regions;
	}

	private static List<District> getDistricts() {
		List<District> districts = new ArrayList<>();
		for (int i = 15; i < 90; i++) {
			final String districtId = "p" + i;
			final District district = new District(districtId);
			setStatisticInfo(districtId, district);
			districts.add(district);
		}
		log.info("Found {} regions.", districts.size());

		return districts;
	}

	private static void setStatisticInfo(String id, AbstractStatistics stat) {
		stat.setName(StatisticsUtil.getRegionName(id));
		stat.setTotalAccidents(StatisticsUtil.getTotalAccidents(id));
		stat.setDrunkDriving(StatisticsUtil.getDrunkDriving(id));
		stat.setKilledPersons(StatisticsUtil.getKilledPersons(id));
		stat.setSeriouslyInjured(StatisticsUtil.getSeriouslyInjured(id));
		stat.setSlightlyInjured(StatisticsUtil.getSlightlyInjured(id));
		stat.setPropertyDamage(StatisticsUtil.getPropertyDamage(id));
	}
}
