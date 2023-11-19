package item;

import java.util.Comparator;

public class BoughtItemSorter implements Comparator<BoughtItem> {
    @Override
    public int compare(BoughtItem i1, BoughtItem i2) { return Integer.compare(i1.getQuantity(), i2.getQuantity()); }
}
