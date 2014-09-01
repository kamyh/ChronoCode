package Main;

import java.util.ArrayList;

public class Session {
	public Session() {

		init();
		// initWithoutGUI();
	}

	private void init() {
		ProcessList pL = new ProcessList();
		this.allProccess = pL.getProcessList();
		System.out.println(this.allProccess);
	}

	private void initWithoutGUI() {
		Task task_1 = new Task();
		task_1.setName("Eclipse");
		task_1.setBaseName("eclipse.exe");

		Task task_2 = new Task();
		task_2.setName("Eclipse");
		task_2.setBaseName("javaw.exe");

		Task task_3 = new Task();
		task_3.setName("Chrome");
		task_3.setBaseName("chrome.exe");

		this.tasks.add(task_1);
		this.tasks.add(task_2);
		this.tasks.add(task_3);
	}

	public void addTask(String name, String baseName) {
		this.tasks.add(new Task(name,baseName));
	}

	public Task getTask(String baseName) {
		for (int i = 0; i < this.tasks.size(); i++) {
			if (this.tasks.get(i).getBaseName().equals(baseName)) {
				return this.tasks.get(i);
			}
		}
		return null;
	}

	public boolean isExsitingTask(String string) {
		for (int i = 0; i < this.tasks.size(); i++) {
			if (this.tasks.get(i).getBaseName().equals(string)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public ArrayList<String> getAllProccess() {
		return allProccess;
	}

	public void setAllProccess(ArrayList<String> allProccess) {
		this.allProccess = allProccess;
	}

	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<String> allProccess = new ArrayList<String>();

}
