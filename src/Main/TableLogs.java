package Main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableLogs extends JFrame
{
	public TableLogs(Session session)
	{
		super();
		this.session = session;

		setTitle("Logs");

		setDatas();

		JScrollPane jsp = new JScrollPane(this.tabLogs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.tabLogs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		this.add(jsp);

		pack();
		this.setSize(900, this.getSize().height);

		this.setVisible(true);
	}

	private String formatTime(int seconds)
	{
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

		return day + ":" + hours + ":" + minute + ":" + second;
	}

	private void setDatas()
	{
		Object[][] data = new Object[getSizeData()][4];

		int cursor = 0;

		String[] titles =
		{ "Process", "Start Time", "End Time", "Elapsed Time" };

		ArrayList<Task> tasks = this.session.getTasks();

		for (int i = 0; i < tasks.size(); i++)
		{
			ArrayList<Period> periods = tasks.get(i).getPeriods();
			for (int j = 0; j < periods.size(); j++)
			{
				data[cursor][0] = tasks.get(i).getBaseName();
				data[cursor][1] = periods.get(j).getStartDate();
				data[cursor][2] = periods.get(j).getEndDate();
				data[cursor][3] = formatTime(periods.get(j).getElapsedTime() / 1000);
				cursor++;
			}
		}

		tabLogs = new JTable(data, titles);
	}

	private int getSizeData()
	{
		ArrayList<Task> tasks = this.session.getTasks();

		int result = 0;

		for (int i = 0; i < tasks.size(); i++)
		{
			ArrayList<Period> periods = tasks.get(i).getPeriods();

			result += periods.size();
		}
		return result;
	}

	private JTable tabLogs;
	private static final long serialVersionUID = 1L;
	private Session session;

	public static void main(String[] args)
	{
		new TableLogs(new Session(true)).setVisible(true);
	}
}