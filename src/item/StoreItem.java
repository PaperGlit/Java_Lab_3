package item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class StoreItem extends Item implements Serializable {
    public StoreItem(String name, String type, int quantity, double price) {
        super(name, type, quantity, price);
    }

    public void setName(String name) {
        setN(name);
    }

    public void setType(String type) {
        setT(type);
    }

    public void setPrice(double price) {
        setP(price);
    }

    public static boolean isInvalidType(String s) {
        return !(Objects.equals(s, "Vegetable") || Objects.equals(s, "Fruit")
                || Objects.equals(s, "Meat") || Objects.equals(s, "Fish")
                || Objects.equals(s, "Other"));
    }

    public boolean hasEnoughQuantity(int i) {
        return (getQuantity() >= i);
    }

    public static void print(ArrayList<StoreItem> shelf) {
        int i, j;
        Scanner scanner = new Scanner(System.in);
        String n;
        if (!shelf.isEmpty()){
            shelf.forEach(System.out::println);
            System.out.print("1 - Sort by price, 2 - Filter by price: ");
            n = scanner.nextLine();
            switch (n) {
                case "1":
                    System.out.print("1 - Regular order, 2 - Reverse order: ");
                    n = scanner.nextLine();
                    switch (n) {
                        case "1":
                            shelf.sort(new StoreItemSorter());
                            shelf.forEach(System.out::println);
                            break;
                        case "2":
                            shelf.sort(new StoreItemSorter().reversed());
                            shelf.forEach(System.out::println);
                            break;
                        default:
                            break;
                    }
                    break;
                case "2":
                    while (true) {
                        while (true) {
                            System.out.print("Enter the minimum price: ");
                            try {
                                i = parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Error: the value should be an integer");
                                continue;
                            }
                            if (i < 0) {
                                System.out.println("Error : the price should be a non-negative real value");
                            } else break;
                        }
                        while (true) {
                            System.out.print("Enter the maximum price: ");
                            try {
                                j = parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Error: the value should be an integer");
                                continue;
                            }
                            if (j < 0) {
                                System.out.println("Error : the price should be a non-negative real value");
                            } else break;
                        }
                        if (j < i) {
                            System.out.println("Error: the minimum price can't be bigger than the maximum price");
                        } else break;
                    }
                    int min = i;
                    int max = j;
                    shelf.stream().filter(x->x.getPrice() >= min && x.getPrice() <= max).forEach(System.out::println);
                    break;
                default:
                    break;
            }
        }
        else System.out.println("The shelf is empty.");
    }

    public BoughtItem buy(int quantity) {
        return buyItem(quantity);
    }

    @Override
    public String toString() {
        return getName() + " (" + getPrice() + ") - (" + getQuantity() + " left)";
    }
}
