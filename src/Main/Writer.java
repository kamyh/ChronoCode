package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Writer implements Serializable
{
	public Writer(String path, boolean overrid)
	{
		this.path = path;
		initFile(overrid);
	}

	public Writer()
	{

	}

	private void initFile(boolean overrid)
	{
		try
		{
			File file = new File(this.path);

			// if file doesnt exists, then create it
			if (!file.exists())
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), overrid);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();

			System.out.println("Logs Created");

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void write(String entry)
	{
		try
		{
			File file = new File(this.path);

			// if file doesnt exists, then create it
			if (!file.exists())
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(entry + "\n");
			bw.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void getAllBlackListedProcess()
	{
		BufferedReader br = null;

		try
		{
			String sCurrentLine;

			br = new BufferedReader(new FileReader("./blacklist.txt"));

			while ((sCurrentLine = br.readLine()) != null)
			{
				this.blacklist.add(sCurrentLine);
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (br != null)
					br.close();
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		Writer w = new Writer("./test.txt", true);
		w.getAllBlackListedProcess();
		System.out.println(w.getBlacklist());
	}

	public ArrayList<String> getBlacklist()
	{
		return blacklist;
	}

	public void setBlacklist(ArrayList<String> blacklist)
	{
		this.blacklist = blacklist;
	}

	private String path;
	private ArrayList<String> blacklist = new ArrayList<String>();

	private static final long serialVersionUID = 1L;
}
