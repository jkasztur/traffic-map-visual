package cz.muni.fi.pb138.trafficmap.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class containing all statistical data
 *
 * @author jkasztur
 */
public abstract class AbstractStatistics {

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private int totalAccidents;

	@Getter
	@Setter
	private int drunkDriving;

	@Getter
	@Setter
	private int killedPersons;

	@Getter
	@Setter
	private int seriouslyInjured;

	@Getter
	@Setter
	private int slightlyInjured;

	@Getter
	@Setter
	private double propertyDamage;

	public AbstractStatistics(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AbstractStatistics{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", totalAccidents=" + totalAccidents +
				", drunkDriving=" + drunkDriving +
				", killedPersons=" + killedPersons +
				", seriouslyInjured=" + seriouslyInjured +
				", slightlyInjured=" + slightlyInjured +
				", propertyDamage=" + propertyDamage +
				'}';
	}
}
