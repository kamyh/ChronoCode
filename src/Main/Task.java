package Main;

import java.util.ArrayList;

public class Task {

	public Task() {
		this.setElapsedTime(0);
		this.baseName = "";
		this.name = "";
		newEntry();
	}

	public void newEntry() {
		int index = this.periods.size();
		if (index > 0) {
			this.periods.get(index - 1).setEndDate();
		}
		this.periods.add(new Period());
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
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
