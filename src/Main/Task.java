package Main;

public class Task {

	public Task() {
		this.setElapsedTime(0);
		this.baseName = "";
		this.name = "";
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

	private int elapsedTime;
	private String name;
	private String baseName;

}
