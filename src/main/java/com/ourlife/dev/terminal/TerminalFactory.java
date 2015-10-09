package com.ourlife.dev.terminal;

import com.ourlife.dev.terminal.bz.BZTerminalService;
import com.ourlife.dev.terminal.bz.JTTTerminalService;
import com.ourlife.dev.terminal.pft.PFTTerminalService;
import com.ourlife.dev.terminal.sys.SYSTerminalService;
import com.ourlife.dev.terminal.zyb.ZYBTerminalService;

public class TerminalFactory {


	public static TerminalService createTerminalService(String terminal) {
		if (terminal.equals("0")) {
			return new PFTTerminalService();
		} else if (terminal.equals("1")) {
			return new BZTerminalService();
		} else if (terminal.equals("2")) {
			return new ZYBTerminalService();
		}else if ("3".equals(terminal)) {
			return new JTTTerminalService();
		} else if (terminal.equals("8")) {
			return new SYSTerminalService();
		} else if (terminal.equals("9")) {
			return null;
		}
		return null;
	}

}
