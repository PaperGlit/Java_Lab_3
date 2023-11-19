package user;

import java.util.Comparator;

public class ProfitSorter implements Comparator<History> {
    @Override
    public int compare(History h1, History h2) { return Double.compare(h1.getItems().stream().mapToDouble(x->x.getPrice() * x.getQuantity()).sum(), h2.getItems().stream().mapToDouble(x->x.getPrice() * x.getQuantity()).sum()); }
}
