package com.github.rhettcaptain.jarvis.util;

import java.util.Arrays;

public class ExceptionUtil {
	public static String getStackTrace(Exception e) {
		StringBuffer sb = new StringBuffer();
		sb.append(e.getMessage());
		sb.append(System.lineSeparator());
		Arrays.asList(e.getStackTrace()).stream().forEach(trace -> {
			sb.append("  at " + trace);
			sb.append(System.lineSeparator());
		});
		return sb.toString();
	}

}
