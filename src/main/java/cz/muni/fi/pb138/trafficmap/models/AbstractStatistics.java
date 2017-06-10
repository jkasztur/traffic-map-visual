package cz.muni.fi.pb138.trafficmap.models;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractStatistics {

	@Getter @Setter
	private int accidentsTotal;

	@Getter @Setter
	private int drunkDriving;

	@Getter @Setter
	private int killedPersons;

	@Getter @Setter
	private int seriouslyInjured;

	@Getter @Setter
	private int slightlyInjured;

	@Getter @Setter
	private double propertyDamage;

}
