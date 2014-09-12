package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.Native;

public class ProcessList
{

	public ProcessList(boolean blacklistActivated)
	{
		init(blacklistActivated);
	}

	public void init(boolean blacklistActivated)
	{
		processList = new ArrayList<String>();

		Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class,
				W32APIOptions.UNICODE_OPTIONS);

		Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
		WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(
				Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));

		Writer w = new Writer();
		w.getAllBlackListedProcess();
		ArrayList<String> blacklist = new ArrayList<String>();

		if (blacklistActivated)
		{
			blacklist = w.getBlacklist();
		}

		try
		{
			while (kernel32.Process32Next(snapshot, processEntry))
			{
				if (!blacklist
						.contains(Native.toString(processEntry.szExeFile)))
				{
					this.processList.add(Native
							.toString(processEntry.szExeFile));
				}
			}

		}

		finally
		{
			kernel32.CloseHandle(snapshot);
		}

		// remove duplicates in arrayList
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(this.processList);
		this.processList.clear();
		this.processList.addAll(hs);

		Collections.sort(this.processList, new Comparator<String>()
		{
			@Override
			public int compare(String s1, String s2)
			{
				return s1.compareToIgnoreCase(s2);
			}
		});

		System.out.println(this.processList);
	}

	public ArrayList<String> getProcessList()
	{
		return processList;
	}

	public void setProcessList(ArrayList<String> processList)
	{
		this.processList = processList;
	}

	private ArrayList<String> processList;

}