package Main;

import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Period() {
		this.startDate = new Date();
		this.endDate = new Date();
		System.out.println(this.startDate);
	}

	public void setEndDate() {
		this.endDate = new Date();
	}

	public void addTime(int time) {
		this.elapsedTime += time;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	private Date startDate;
	private Date endDate;
	private int elapsedTime = 0;
}
