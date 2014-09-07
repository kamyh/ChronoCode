package Main;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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
		this.elapsedTime += time;
	}

	public Date getStartDate()
	{
		return startDate.getTime();
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate.getTime();
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
