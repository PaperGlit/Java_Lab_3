package user;

import item.BoughtItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class History implements Serializable {
    private ArrayList<BoughtItem> items;
    final private String date;
    final private int fDate;

    public History(ArrayList<BoughtItem> items, String date) {
        this.items = items;
        this.date = date;
        this.fDate = dateToDays(date);
    }

    public int getFDate() { return  fDate; }

    public ArrayList<BoughtItem> getItems() { return  items; }

    public void setItems(ArrayList<BoughtItem> items) { this.items = items; }

    public void addItems(ArrayList<BoughtItem> items) { this.items.addAll(items); }

    public static boolean dateCheck(String date) {
        int d, m, y;
        if (date.length() != 10) {
            System.out.println("Error: incorrect date format");
            return false;
        }
        try {
            d = parseInt(date.substring(0, 2));
            m = parseInt(date.substring(3, 5));
            y = parseInt(date.substring(6));
        }
        catch (Exception e) {
            System.out.println("Error: incorrect date format");
            return false;
        }
        boolean leapYear = (y % 4 == 0);
        if (m < 1 || m > 12) {
            System.out.println("Error: incorrect month format");
            return false;
        }
        boolean majorMonth = ((m < 8 && (m % 2 == 1)) || (m > 7 && (m % 2 == 0)));
        //noinspection ConstantValue
        if (d < 1 || (d > 30 && !majorMonth) || (d > 31 && majorMonth) || (d > 28 && m == 2 && !leapYear) || (d > 29 && m == 2 && leapYear)) {
            System.out.println("Error: incorrect day format");
            return false;
        }
        return true;
    }

    public static int dateToDays(String date) {
        int days = parseInt(date.substring(0, 2));
        int months = parseInt(date.substring(3, 5));
        int years = parseInt(date.substring(6));
        int ly = years / 4;
        int y = ((years - ly) * 365) + (ly * 366);
        int lm;
        if (years % 4 == 0) {
            lm = 60;
        } else lm = 59;
        return switch (months) {
            case 1 -> days + y;
            case 2 -> days + y + 31;
            case 3 -> days + y + lm;
            case 4 -> days + y + lm + 31;
            case 5 -> days + y + lm + 61;
            case 6 -> days + y + lm + 92;
            case 7 -> days + y + lm + 122;
            case 8 -> days + y + lm + 153;
            case 9 -> days + y + lm + 184;
            case 10 -> days + y + lm + 214;
            case 11 -> days + y + lm + 245;
            case 12 -> days + y + lm + 275;
            default -> throw new IllegalStateException("Unexpected value: " + months);
        };
    }

    public static ArrayList<History> generalizer(List<History> items) {
        List<History> result = new ArrayList<>();
        items.forEach(x-> {
            if (result.stream().anyMatch(y-> x.getFDate() == y.getFDate())) {
                result.stream().filter(y->y.getFDate() == x.getFDate()).forEach(z->z.addItems(x.getItems()));
            }
            else result.add(x);
        });
        return new ArrayList<>(result);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---").append(date).append("---\n");
        items.forEach(x->sb.append(x.toString()).append("\n"));
        return sb.toString();
    }
}
