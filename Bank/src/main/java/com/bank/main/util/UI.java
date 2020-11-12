package com.bank.main.util;

import org.apache.log4j.Logger;

public class UI {
	
	private static Logger log = Logger.getLogger(UI.class);
	
	public static void printConsoleSeperator() {
		log.info("==============================");
	}
	
	public static void printConsoleMenuItem(String menuInput) {
		printConsoleSeperator();
		log.info("*- " + menuInput + " -*");
		printConsoleSeperator();
	}

}
