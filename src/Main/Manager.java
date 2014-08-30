package Main;

import javax.swing.JFrame;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

public class Manager extends JFrame {

	public Manager() {
		super("Chrono Code");
		apperence();

		this.isStart = true;
		init();
		start();
	}

	private void apperence() {

		this.setVisible(true);
		this.setSize(800, 600);
	}

	public void start() {
		while (true) {
			if (this.isStart) {
				char[] buffer = new char[MAX_TITLE_LENGTH * 2];
				User32DLL.GetWindowTextW(User32DLL.GetForegroundWindow(),
						buffer, MAX_TITLE_LENGTH);
				// System.out.println("Active window title: "
				// + Native.toString(buffer));

				PointerByReference pointer = new PointerByReference();
				User32DLL.GetWindowThreadProcessId(
						User32DLL.GetForegroundWindow(), pointer);
				Pointer process = Kernel32.OpenProcess(
						Kernel32.PROCESS_QUERY_INFORMATION
								| Kernel32.PROCESS_VM_READ, false,
						pointer.getValue());
				Psapi.GetModuleBaseNameW(process, null, buffer,
						MAX_TITLE_LENGTH);

				System.out.println(Native.toString(buffer) + ":"
						+ this.tasks[0].getBaseName());

				for (int i = 0; i < this.tasks.length; i++) {

					if (this.tasks[i].getBaseName().equals(
							Native.toString(buffer))) {
						this.tasks[i].setElapsedTime(this.tasks[i]
								.getElapsedTime() + refreshTime);
						System.out
								.println(this.tasks[i].getElapsedTime() / 1000);
					}
				}

				try {
					Thread.sleep(refreshTime);
				} catch (InterruptedException e) {
					System.err
							.println("Impossible to sleep. too many noises !");
					e.printStackTrace();
				}
			}
		}
	}

	private void save(String path) {
		// TODO
	}

	private void load(String path) {
		// TODO
	}

	private void listAllAppRunning() {
		// TODO
	}

	private void setWhiteList() {
		// TODO
	}

	private void init() {
		this.tasks = new Task[3];

		for (int i = 0; i < this.tasks.length; i++) {
			this.tasks[i] = new Task();
		}

		this.tasks[0].setName("Eclipse");
		this.tasks[0].setBaseName("eclipse.exe");

		this.tasks[1].setName("Eclipse");
		this.tasks[1].setBaseName("javaw.exe");

		this.tasks[2].setName("Chrome");
		this.tasks[2].setBaseName("chrome.exe");

	}

	public static void main(String[] args) throws Exception {
		Manager m = new Manager();

	}

	private static final int MAX_TITLE_LENGTH = 1024;
	private int refreshTime = 1000;
	private boolean isStart;
	private Task[] tasks;

	static class Psapi {
		static {
			Native.register("psapi");
		}

		public static native int GetModuleBaseNameW(Pointer hProcess,
				Pointer hmodule, char[] lpBaseName, int size);
	}

	static class Kernel32 {
		static {
			Native.register("kernel32");
		}
		public static int PROCESS_QUERY_INFORMATION = 0x0400;
		public static int PROCESS_VM_READ = 0x0010;

		public static native int GetLastError();

		public static native Pointer OpenProcess(int dwDesiredAccess,
				boolean bInheritHandle, Pointer pointer);
	}

	static class User32DLL {
		static {
			Native.register("user32");
		}

		public static native int GetWindowThreadProcessId(HWND hWnd,
				PointerByReference pref);

		public static native HWND GetForegroundWindow();

		public static native int GetWindowTextW(HWND hWnd, char[] lpString,
				int nMaxCount);
	}
}