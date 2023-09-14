import java.time.DateTimeException;
import java.time.LocalDate;

public class Main {
	public static void main(String[] args) {
		// Provided case:
		printBonusDatesBetween(2010, 2015);
		// Reversed range case:
		// printBonusDatesBetween(2015, 2010);
		// Negative year case:
		// printBonusDatesBetween(-2015, 2010);
		// Bigger than 4 digit range case:
		// printBonusDatesBetween(2115656, 1563152);
		// Less than 4 digit year range case:
		// printBonusDatesBetween(0, 999);
	}

	public static void printBonusDatesBetween(int fromYear, int toYear) {
		// If fromYear is bigger than toYear, then no worries we can just swap the values
		// to make it work in the same range:
		if (fromYear > toYear) {
			int temp = fromYear;
			fromYear = toYear;
			toYear = temp;
		}
		while (fromYear <= toYear) {
			LocalDate date = getSymmetricalDate(fromYear);
			if (date != null) {
				System.out.println(date);
			}
			fromYear++;
		}
	}

	public static LocalDate getSymmetricalDate(int year) {
		int[] pair = new int[2];
		boolean newPair = true;
		int pairAmount = 0;
		// Get each digit of the year in reverse and pair them by two together:
		for (int i = year; i > 0; i /= 10) {
			int digit = i % 10;
			if (newPair) {
				// if there's more than 2 pairs, return null since in that case it couldn't be parsed
				// in terms of more than 2-digit numbers for days and months in gregorian calendar system:
				if (pairAmount == 2) {
					return null;
				}
				// We're multiplying by 10 before we found a pair because we would like to handle the 0xxx year case too
				// So in that case the day of the date would be a reverse of year's first half:
				pair[pairAmount] = digit * 10;

				newPair = false;
			} else {
				// combine two digits into a pair:
				pair[pairAmount] += digit;

				pairAmount++;
				newPair = true;
			}
		}
		// Let's be sane and use LocalDate object to do the dirty work of handling
		// the rest of gregorian calendar system constraints for us :>
		try {
			return LocalDate.of(year, pair[0], pair[1]);
		} catch (DateTimeException ignored) {}
		return null;
	}
}
