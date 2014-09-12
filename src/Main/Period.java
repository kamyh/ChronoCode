package Main;

import java.io.Serializable;
import java.util.Calendar;

public class Period implements Serializable
{

	public Period()
	{
		this.startDate = Calendar.getInstance();
		this.endDate = Calendar.getInstance();
		System.out.println(this.startDate);
	}

	public Period(Calendar sDateStart, Calendar sDateEnd, int elapsedTime2)
	{
		this.startDate = sDateStart;
		this.endDate = sDateEnd;
		this.elapsedTime = elapsedTime2;
	}

	public void setEndDate()
	{
		this.endDate = Calendar.getInstance();
	}

	public void addTime(int time)
	{
		this.elapsedTime = time;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public int getElapsedTime()
	{
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime)
	{
		this.elapsedTime = elapsedTime;
	}

	private Calendar startDate;
	private Calendar endDate;
	private int elapsedTime = 0;
	private static final long serialVersionUID = 1L;

}
