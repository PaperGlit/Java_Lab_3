import item.BoughtItem;
import item.StoreItem;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/* TODO :
    * Implement a/an s/es methods
    * File input/output
    * User system
    * and more...
 */

public class Main {
    public static void main(String[] args) {
        String s, t, u, n;
        int i, count;
        StoreItem it;
        BoughtItem bi;
        double d, sum;
        boolean isMeatOrFish;
        ArrayList<StoreItem> storeItems = new ArrayList<>();
        ArrayList<BoughtItem> cart = new ArrayList<>();
        ArrayList<BoughtItem> history = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        while (true) {
            System.out.print("What do you want to do, " + name + "?:\n" +
                    "1 - Add an item\n" +
                    "2 - Edit an item\n" +
                    "3 - Buy an item\n" +
                    "4 - Print your receipt and save it as a file\n" +
                    "5 - Print the history of your purchases\n" +
                    "Your choice: ");
            n = scanner.nextLine();
            switch (n) {
                case "1":
                    System.out.print("Enter the item's name: ");
                    s = scanner.nextLine();
                    while (true) {
                        System.out.print("Enter the item's type (Vegetable, Fruit, Meat, Fish, Other): ");
                        t = scanner.nextLine();
                        if (StoreItem.isInvalidType(t)) {
                            System.out.println("Error: incorrect type, please try again");
                        } else break;
                    }
                    System.out.print("Enter the item's quantity: ");
                    i = parseInt(scanner.nextLine());
                    System.out.print("Enter the item's price: ");
                    d = parseDouble(scanner.nextLine());
                    storeItems.add(new StoreItem(s, t, i, d));
                    System.out.println("New item was created successfully!");
                    break;
                case "2":
                    count = 0;
                    for (StoreItem storeItem : storeItems) {
                        System.out.println("#" + count + " " + storeItem);
                        count++;
                    }
                    System.out.print("Select an item to edit: ");
                    s = scanner.nextLine();
                    it = storeItems.get(parseInt(s));
                    System.out.println("Selected item: " + it);
                    System.out.print("""
                            Select what to edit:
                            1 - Name
                            2 - Type
                            3 - Quantity
                            4 - Price
                            Your choice:\s""");
                    t = scanner.nextLine();
                    switch(t) {
                        case "1":
                            System.out.print("Enter the item's new name (Current name: " + it.getName() + "): ");
                            u = scanner.nextLine();
                            it.setName(u);
                            System.out.println("New name was set successfully!");
                            break;
                        case "2":
                            while (true) {
                                System.out.print("Enter the item's new type (Vegetable, Fruit, Meat, Fish, Other) " +
                                        "(Current type: " + it.getType() + "): ");
                                u = scanner.nextLine();
                                if (StoreItem.isInvalidType(u)) {
                                    System.out.println("Error: incorrect type, please try again");
                                } else break;
                            }
                            it.setType(u);
                            System.out.println("New type was set successfully!");
                            break;
                        case "3":
                            System.out.print("Enter the item's new quantity: ");
                            i = parseInt(scanner.nextLine());
                            it.setQuantity(i);
                            System.out.println("New quantity was set successfully!");
                            break;
                        case "4":
                            System.out.print("Enter the item's new price: ");
                            d = parseDouble(scanner.nextLine());
                            it.setPrice(d);
                            System.out.println("New price was set successfully!");
                            break;
                        default:
                            System.out.println("Error: wrong input detected");
                            break;
                    }
                    break;
                case "3":
                    count = 0;
                    for (StoreItem storeItem : storeItems) {
                        System.out.println("#" + count + " " + storeItem);
                        count++;
                    }
                    System.out.print("Select which item to buy: ");
                    s = scanner.nextLine();
                    it = storeItems.get(parseInt(s));
                    while (true) {
                        System.out.print("How much " + it.getName() + "(s/es) do you want to buy?: ");
                        i = parseInt(scanner.nextLine());
                        if (i <= 0) {
                            System.out.println("Error: the quantity should be above zero");
                        }
                        else if (!it.hasEnoughQuantity(i)) {
                            System.out.println("Error: there is not enough quantity to buy that much");
                        } else break;
                    }
                    bi = it.buy(i);
                    cart.add(bi);
                    System.out.println("The item was bought successfully!");
                    break;
                case "4":
                    sum = 0;
                    isMeatOrFish = false;
                    for (BoughtItem item : cart) {
                        if (item.getType().equals("Meat") || item.getType().equals("Fish")) {
                            isMeatOrFish = true;
                        }
                        sum += item.getPrice();
                        System.out.println(item);
                    }
                    if (isMeatOrFish) {
                        System.out.println("Do not for get to put your meat/fish into the refrigerator!");
                    }
                    System.out.println("Total price:\t" + sum);
                    history.addAll(cart);
                    break;
                case "5":
                    System.out.println("Your history:");
                    for (BoughtItem item : history) {
                        System.out.println(item);
                    }
                    break;
                default:
                    return;
            }
        }
    }
}