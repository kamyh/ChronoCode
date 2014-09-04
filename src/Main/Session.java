package Main;

import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Session(boolean b)
	{

		init(b);
		// initWithoutGUI();
	}

	private void init(boolean b)
	{
		ProcessList pL = new ProcessList(b);
		this.allProccess = pL.getProcessList();
		System.out.println(this.allProccess);
	}

	@SuppressWarnings("unused")
	private void initWithoutGUI()
	{
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

	public void addTask(String name, String baseName)
	{
		this.tasks.add(new Task(name, baseName));
	}

	public Task getTask(String baseName)
	{
		for (int i = 0; i < this.tasks.size(); i++)
		{
			if (this.tasks.get(i).getBaseName().equals(baseName))
			{
				return this.tasks.get(i);
			}
		}
		return null;
	}

	public boolean isExsitingTask(String string)
	{
		for (int i = 0; i < this.tasks.size(); i++)
		{
			if (this.tasks.get(i).getBaseName().equals(string))
			{
				return true;
			}
		}
		return false;
	}

	public void removeTask(String s)
	{
		this.allProccess.remove(s);
		for (int i = 0; i < this.tasks.size(); i++)
		{
			if (this.tasks.get(i).getBaseName().equals(s))
			{
				this.tasks.remove(i);
			}
		}
	}

	public void reset()
	{
		for (int i = 0; i < this.tasks.size(); i++)
		{
			this.tasks.get(i).resetPeriod();
		}
		totTime = 0;
	}

	public ArrayList<Task> getTasks()
	{
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks)
	{
		this.tasks = tasks;
	}

	public ArrayList<String> getAllProccess()
	{
		return allProccess;
	}

	public void setAllProccess(ArrayList<String> allProccess)
	{
		this.allProccess = allProccess;
	}

	public void addProcessToBlacklist(String process)
	{
		Writer w = new Writer("./blacklist.txt", true);

		w.write(process);
	}

	public int getTotTime()
	{
		return totTime;
	}

	public void setTotTime(int totTime)
	{
		this.totTime = totTime;
	}

	public void resetProcessList(boolean b)
	{
		ProcessList pL = new ProcessList(b);
		pL.init(b);
		this.allProccess = pL.getProcessList();
	}

	public synchronized String getSavePath()
	{
		return Session.savePath;
	}

	public void setSavePath(String savePath)
	{
		Session.savePath = new String(savePath);
	}

	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<String> allProccess = new ArrayList<String>();
	private int totTime = 0;
	private static String savePath = "./save.chco";

}
