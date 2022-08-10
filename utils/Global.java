package utils;

public final class Global {
	private static String datePattern = "yyyy/MM/dd";
	private static String dateTimePattern = "yyyy/MM/dd HH:mm";
	private static String timePattern = "HH:mm";

	public static String getDatePattern() {
		return datePattern;
	}

	public static String getDateTimePattern() {
		return dateTimePattern;
	}

	public static String getTimePattern() {
		return timePattern;
	}

	private Global() {

	}
}
