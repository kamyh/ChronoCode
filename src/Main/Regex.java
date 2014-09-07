package Main;

import java.util.regex.*;

class Regex
{
	public static final boolean isDateAndTime(String s)
	{
		String re1 = "((?:Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|Tues|Thur|Thurs|Sun|Mon|Tue|Wed|Thu|Fri|Sat))"; // Day
		// Of
		// Week
		// 1
		String re2 = "(\\s+)"; // White Space 1
		String re3 = "((?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?))"; // Month
		// 1
		String re4 = "(\\s+)"; // White Space 2
		String re5 = "(\\d+)"; // Integer Number 1
		String re6 = "(\\s+)"; // White Space 3
		String re7 = "((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)"; // HourMinuteSec
		// 1
		String re8 = "(\\s+)"; // White Space 4
		String re9 = "(CEST)"; // Word 1
		String re10 = "(\\s+)"; // White Space 5
		String re11 = "((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])"; // Year
		// 1

		Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(s);
		if (m.find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static final boolean isElapsedTime(int i)
	{
		String txt = Integer.toString(i);

		String re1 = "(\\d+)"; // Integer Number 1

		Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(txt);

		if (m.find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void main(String[] args)
	{
		String txt = "Sun Sep 07 14:33:08 CEST 2014";

		String re1 = "((?:Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|Tues|Thur|Thurs|Sun|Mon|Tue|Wed|Thu|Fri|Sat))"; // Day
																																	// Of
																																	// Week
																																	// 1
		String re2 = "(\\s+)"; // White Space 1
		String re3 = "((?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?))"; // Month
																																												// 1
		String re4 = "(\\s+)"; // White Space 2
		String re5 = "(\\d+)"; // Integer Number 1
		String re6 = "(\\s+)"; // White Space 3
		String re7 = "((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)"; // HourMinuteSec
																															// 1
		String re8 = "(\\s+)"; // White Space 4
		String re9 = "(CEST)"; // Word 1
		String re10 = "(\\s+)"; // White Space 5
		String re11 = "((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])"; // Year
																						// 1

		Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(txt);
		if (m.find())
		{
			System.out.println(true);
		}
		else
		{
			System.out.println(false);
		}
	}
}