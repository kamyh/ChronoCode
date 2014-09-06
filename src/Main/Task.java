package Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable
{
	public Task()
	{
		this.setElapsedTime(0);
		this.baseName = "";
		this.name = "";
		this.setWatching(true);
		newEntry();
	}

	public Task(String name2, String baseName2)
	{
		this.setElapsedTime(0);
		this.baseName = baseName2;
		this.name = name2;
		this.setWatching(true);
		newEntry();
	}

	public void newEntry()
	{
		displayAllPeriods();
		this.periods.add(new Period());
	}

	public void displayAllPeriods()
	{
		System.out.println("--------");
		System.out.println("Task Name: " + this.name);

		for (int i = 0; i < this.periods.size(); i++)
		{
			System.out.println(this.periods.get(i).getStartDate() + " -- " + this.periods.get(i).getEndDate() + " --- " + this.periods.get(i).getElapsedTime() / 1000);
		}
		System.out.println("--------");
	}

	private Boolean compareDateFromString(String s, Date d)
	{
		String time = s.split(" ")[3];
		String monthString = s.split(" ")[1];
		int month = 0;

		switch (monthString)
		{
		case "Jan":
			month = 1;
			break;
		case "Fev":
			month = 2;
			break;
		case "Mar":
			month = 3;
			break;
		case "Avr":
			month = 4;
			break;
		case "Mai":
			month = 5;
			break;
		case "Jun":
			month = 6;
			break;
		case "Jul":
			month = 7;
			break;
		case "Aug":
			month = 8;
			break;
		case "Sep":
			month = 9;
			break;
		case "Oct":
			month = 10;
			break;
		case "Nov":
			month = 11;
			break;
		case "Dec":
			month = 12;
			break;

		default:
			break;
		}

		int year = Integer.parseInt(s.split(" ")[5]);
		int day = Integer.parseInt(s.split(" ")[2]);
		int hours = Integer.parseInt(time.split(":")[0]);
		int minutes = Integer.parseInt(time.split(":")[1]);
		int seconds = Integer.parseInt(time.split(":")[2]);

		if ((d.getYear() + 1900) == year && d.getDay() == day && d.getHours() == hours && d.getMinutes() == minutes && d.getSeconds() == seconds)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public Boolean removePeriodFromTo(String start, String end)
	{
		for (Period period : this.periods)
		{

			if (compareDateFromString(start, period.getStartDate()) && compareDateFromString(end, period.getEndDate()))
			{
				this.periods.remove(period);
				System.out.println("FOUND");
				return true;
			}
			else
			{
				System.out.println("NOT FOUND");
			}
		}

		return false;
	}

	public void resetPeriod()
	{
		this.periods = new ArrayList<Period>();
	}

	public int getElapsedTime()
	{
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime)
	{
		this.elapsedTime = elapsedTime;
	}

	public Period getLastPeriod()
	{
		return this.periods.get(this.periods.size() - 1);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBaseName()
	{
		return baseName;
	}

	public void setBaseName(String baseName)
	{
		this.baseName = baseName;
	}

	public ArrayList<Period> getPeriods()
	{
		return periods;
	}

	public void setPeriods(ArrayList<Period> periods)
	{
		this.periods = periods;
	}

	public boolean isWatching()
	{
		return isWatching;
	}

	public void setWatching(boolean isWatching)
	{
		this.isWatching = isWatching;
	}

	private int elapsedTime;
	private String name;
	private String baseName;
	private ArrayList<Period> periods = new ArrayList<Period>();
	private boolean isWatching;

	private static final long serialVersionUID = 1L;

}
