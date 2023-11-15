import item.BoughtItem;
import item.StoreItem;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/* TODO :
    * Implement a/an s/es methods (optional)
    * File input/output (done!)
    * User system (done!)
    * Sorting
    * Dates
    * Orders
    * and more...
 */

public class Main {
    public static void main(String[] args) {
        String s, t, u, n;
        int i, uIndex;
        StoreItem it;
        double d;
        User user;
        ArrayList<StoreItem> storeItems;
        storeItems = FileService.importFromFile("shelf.ser");
        ArrayList<BoughtItem> cart = new ArrayList<>();
        ArrayList<User> users;
        users = FileService.importFromFile("users.ser");
        Scanner scanner = new Scanner(System.in);
        if (!users.isEmpty()) {

                System.out.print("""
                        User menu:
                        1 - Add a new user
                        2 - Select user
                        3 - Delete user
                        Your choice:\s""");
                n = scanner.nextLine();
                switch (n) {
                    case "1":
                        System.out.print("Enter your name: ");
                        user = new User(scanner.nextLine());
                        users.add(user);
                        return;
                    case "2":
                        while (true) {
                            IntStream.range(0, users.size()).mapToObj(count -> "#" + count + " " + users.get(count)).forEach(System.out::println);
                            System.out.print("Select a user: ");
                            try {
                                uIndex = parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Error: the input should be an integer");
                                continue;
                            }
                            if (uIndex < 0 || uIndex > users.size() - 1) {
                                System.out.println("Error: unreachable value detected");
                                continue;
                            }
                            user = users.get(uIndex);
                            break;
                        }
                        break;
                    case "3":
                        while (true) {
                            IntStream.range(0, users.size()).mapToObj(count -> "#" + count + " " + users.get(count)).forEach(System.out::println);
                            System.out.print("Select a user to delete: ");
                            try {
                                uIndex = parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Error: the input should be an integer");
                                continue;
                            }
                            if (uIndex < 0 || uIndex > users.size() - 1) {
                                System.out.println("Error: unreachable value detected");
                                continue;
                            }
                            users.remove(uIndex);
                            return;
                        }
                    default:
                        return;
                }
            }

        else {
            System.out.print("Enter your name: ");
            user = new User(scanner.nextLine());
            users.add(user);
        }
        while (true) {
            if (user.getName().equals("Admin")) {
                System.out.print("""
                        Admin menu:
                        1 - Add an item
                        2 - Edit an item
                        3 - Export items
                        Your choice:\s""");
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
                        IntStream.range(0, storeItems.size()).mapToObj(count -> "#" + count + " " + storeItems.get(count)).forEach(System.out::println);
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
                        FileService.exportToFile(storeItems, "shelf.ser");
                        System.out.println("The items were exported successfully");
                        break;
                    default:
                        return;
                }
            }

            else {
                System.out.printf("""
                        What do you want to do, %s?:
                        1 - Buy an item
                        2 - Print your receipt and save it as a file
                        3 - Print the history of your purchases
                        Your choice:\s""", user.getName());
                n = scanner.nextLine();
                switch (n) {
                    case "1":
                        while (true) {
                            IntStream.range(0, storeItems.size()).mapToObj(count -> "#" + count + " " + storeItems.get(count)).forEach(System.out::println);
                            System.out.print("Select which item to buy: ");
                            s = scanner.nextLine();
                            try {
                                i = parseInt(s);
                            }
                            catch (Exception e) {
                                System.out.println("Error: the input should be an integer");
                                continue;
                            }
                            if (i < 0 || i > storeItems.size() - 1) {
                                System.out.println("Error: unreachable value detected");
                                continue;
                            }
                            it = storeItems.get(i);
                            break;
                        }
                        while (true) {
                            System.out.print("How much " + it.getName() + "(s/es) do you want to buy?: ");
                            i = parseInt(scanner.nextLine());
                            if (i <= 0) {
                                System.out.println("Error: the quantity should be above zero");
                            } else if (!it.hasEnoughQuantity(i)) {
                                System.out.println("Error: there is not enough quantity to buy that much");
                            } else break;
                        }
                        BoughtItem bi = it.buy(i);
                        if (cart.stream().anyMatch(x -> x.equals(bi))) {
                            cart.stream().filter(x -> x.equals(bi)).forEach(x -> x.setQuantity(x.getQuantity() + bi.getQuantity()));
                        } else cart.add(bi);
                        storeItems.removeIf(x -> x.getQuantity() == 0);
                        System.out.println("The item was bought successfully!");
                        break;
                    case "2":
                        System.out.println(BoughtItem.print(cart));
                        System.out.println("Total:\t" + BoughtItem.total(cart));
                        if (BoughtItem.hasMeatOrFish(cart)) {
                            System.out.println(("Do not forget to put your " + BoughtItem.meatAndFish(cart) + " in the refrigerator!\n"));
                        }
                        ArrayList<BoughtItem> history = user.getHistory();
                        history.addAll(cart);
                        user.setHistory(history);
                        FileService.writeReceipt(cart);
                        FileService.exportToFile(users, "users.ser");
                        break;
                    case "3":
                        System.out.println("Your history:");
                        System.out.print(BoughtItem.print(user.getHistory()));
                        break;
                    default:
                        return;
                }
            }
        }
    }
}