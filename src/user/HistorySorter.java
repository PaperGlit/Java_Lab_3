package user;

import java.util.Comparator;

public class HistorySorter implements Comparator<History> {
    @Override
    public int compare(History h1, History h2) { return Integer.compare(h1.getFDate(), h2.getFDate()); }
}
