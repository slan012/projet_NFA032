package app;

import java.time.LocalDate;
import java.util.Comparator;

public class DateComparator implements Comparator<Particulier> {

	@Override
	public int compare(Particulier p1, Particulier p2) {
		LocalDate d1 = LocalDate.parse(p1.getAddDate());
		LocalDate d2 = LocalDate.parse(p2.getAddDate());
		if (d1.isBefore(d2)) {
			return 1;
		}
		if (d1.isAfter(d2)) {
			return -1;
		}
		return 0;
	}

}
