package app;

import java.util.Comparator;

public class NameComparator implements Comparator<Particulier> {

	@Override
	public int compare(Particulier p1, Particulier p2) {
		if (p1.getName().compareTo(p2.getName()) < 1) {
			return -1;
		}
		if ((p1.getName().compareTo(p2.getName()) > 1)) {
			return 1;
		}
		return 0;
	}

}
