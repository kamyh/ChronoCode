package Main;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable{

	public Task() {
		this.setElapsedTime(0);
		this.baseName = "";
		this.name = "";
		newEntry();
	}

	public Task(String name2, String baseName2) {
		this.setElapsedTime(0);
		this.baseName = baseName2;
		this.name = name2;
		newEntry();
	}

	public void newEntry() {

		displayAllPeriods();
		this.periods.add(new Period());
	}

	public void displayAllPeriods() {
		System.out.println("--------");
		System.out.println("Task Name: " + this.name);

		for (int i = 0; i < this.periods.size(); i++) {
			System.out.println(this.periods.get(i).getStartDate() + " -- "
					+ this.periods.get(i).getEndDate() + " --- "
					+ this.periods.get(i).getElapsedTime() / 1000);
		}
		System.out.println("--------");
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Period getLastPeriod() {
		return this.periods.get(this.periods.size() - 1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public ArrayList<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(ArrayList<Period> periods) {
		this.periods = periods;
	}

	private int elapsedTime;
	private String name;
	private String baseName;
	private ArrayList<Period> periods = new ArrayList<Period>();

}
