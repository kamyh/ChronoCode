package Main;

import java.util.ArrayList;

public class Session {
	public Session() {

		init();
		initWithoutGUI();
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

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<String> allProccess = new ArrayList<String>();

}
