import fileService.FileService;
import item.BoughtItem;
import item.BoughtItemSorter;
import item.Item;
import item.StoreItem;
import user.History;
import user.HistorySorter;
import user.ProfitSorter;
import user.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static user.User.generalize;

public class Main {
    public static void main(String[] args) {
        String s, t, u, n, date;
        int i, j, uIndex;
        StoreItem it;
        double d;
        User user;
        ArrayList<StoreItem> shelf;
        shelf = FileService.importFromFile("shelf.ser");
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
                        FileService.exportToFile(users, "users.ser");
                        break;
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
                            FileService.exportToFile(users, "users.ser");
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
        do {
            System.out.print("Enter the current date (dd.mm.yyyy): ");
            date = scanner.nextLine();
        } while (!History.dateCheck(date));
        if (user.getOrders().stream().anyMatch(order -> shelf.stream().anyMatch(item -> item.oEquals(order)))) {
            List<BoughtItem> tempList = user.getOrders().stream().filter(order -> shelf.stream().anyMatch(item -> item.oEquals(order))).toList();
            ArrayList<BoughtItem> postOrders = new ArrayList<>(tempList);
            ArrayList<BoughtItem> tempCart = postOrders.stream().map(postOrder -> postOrder.order(user, users, new ArrayList<>(), shelf)).flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));
            cart.addAll(tempCart);
        }
        while (true) {
            if (user.getName().equals("Admin")) {
                System.out.print("""
                        Admin menu:
                        1 - Add an item
                        2 - Edit an item
                        3 - Export items
                        4 - Print items
                        5 - Most profitable day
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
                        StoreItem si = new StoreItem(s, t, i, d);
                        if(shelf.stream().anyMatch(x->x.equals(si))) {
                            shelf.stream().filter(x -> x.equals(si)).forEach(x -> x.setQuantity(x.getQuantity() + si.getQuantity()));
                        }
                        else shelf.add(si);
                        System.out.println("New item was added successfully!");
                        break;
                    case "2":
                        IntStream.range(0, shelf.size()).mapToObj(count -> "#" + count + " " + shelf.get(count)).forEach(System.out::println);
                        System.out.print("Select an item to edit: ");
                        s = scanner.nextLine();
                        it = shelf.get(parseInt(s));
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
                        FileService.exportToFile(shelf, "shelf.ser");
                        System.out.println("The items were exported successfully");
                        break;
                    case "4":
                        StoreItem.print(shelf);
                        break;
                    case "5":
                        ArrayList<History> combinedHistory = History.generalizer(users.stream().flatMap(x->x.getHistory().stream()).toList());
                        if (!combinedHistory.isEmpty()) {
                            combinedHistory.sort(new ProfitSorter().reversed());
                            History mostProfitableDay = combinedHistory.get(0);
                            ArrayList<BoughtItem> result = mostProfitableDay.getItems();
                            result = generalize(result);
                            mostProfitableDay.setItems(result);
                            System.out.println("Most profitable day:\n" + mostProfitableDay);
                        }
                        else System.out.println("Most profitable day: none");
                        break;
                    default:
                        return;
                }
            }

            else {
                System.out.printf("""
                        What do you want to do, %s?:
                        1 - Print the shop shelf
                        2 - Buy an item
                        3 - Place an order to buy an item
                        4 - Print your receipt and save it as a file
                        5 - Print the history of your purchases
                        6 - Most popular item
                        Your choice:\s""", user.getName());
                n = scanner.nextLine();
                switch (n) {
                    case "1":
                        StoreItem.print(shelf);
                        break;
                    case "2":
                        while (true) {
                            IntStream.range(0, shelf.size()).mapToObj(count -> "#" + count + " " + shelf.get(count)).forEach(System.out::println);
                            System.out.print("Select which item to buy: ");
                            s = scanner.nextLine();
                            try {
                                i = parseInt(s);
                            }
                            catch (Exception e) {
                                System.out.println("Error: the input should be an integer");
                                continue;
                            }
                            if (i < 0 || i > shelf.size() - 1) {
                                System.out.println("Error: unreachable value detected");
                                continue;
                            }
                            it = shelf.get(i);
                            break;
                        }
                        while (true) {
                            System.out.print("How many " + it.pluralize() + " do you want to buy?: ");
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
                        } else {
                            cart.add(bi);
                            if (Objects.equals(bi.getType(), "Fruit") || Objects.equals(bi.getType(), "Vegetable")) {
                                cart.add(new BoughtItem("Shopping bag", "Other", 1, 5));
                            }
                        }
                        shelf.removeIf(x -> x.getQuantity() == 0);
                        String wasWere;
                        if (i > 1) {
                            wasWere = " were";
                        } else wasWere = " was";
                        System.out.println(i + " "  + bi.pluralize() + wasWere + " successfully added to your cart!");
                        break;
                    case "3":
                        System.out.print("Enter the name of the item: ");
                        s = scanner.nextLine();
                        while (true) {
                            System.out.print("Enter the item's type (Vegetable, Fruit, Meat, Fish, Other): ");
                            t = scanner.nextLine();
                            if (StoreItem.isInvalidType(t)) {
                                System.out.println("Error: incorrect type, please try again");
                            } else break;
                        }
                        while (true) {
                            System.out.print("How many " + Item.pluralizer(s) + " do you want to buy?: ");
                            try {
                                i = parseInt(scanner.nextLine());
                            }
                            catch (Exception e) {
                                System.out.println("Error: the input value should be an integer");
                                continue;
                            }
                            if (i <= 0) {
                                System.out.println("Error: the quantity should be above zero");
                            }
                            else break;
                        }
                        while (true) {
                            System.out.print("At which maximum price do you want to buy this item (per object): ");
                            try {
                                d = parseDouble(scanner.nextLine());
                            }
                            catch (Exception e) {
                                System.out.println("Error: the input value should be a real number");
                                continue;
                            }
                            if (d <= 0) {
                                System.out.println("Error: the quantity should be above zero");
                            }
                            else break;
                        }
                        BoughtItem order = new BoughtItem(s, t, i, d);
                        if (shelf.stream().anyMatch(x->x.oEquals(order))) {
                            cart = order.order(user, users, cart, shelf);
                        }
                        else {
                            ArrayList<BoughtItem> orders = user.getOrders();
                            orders.add(order);
                            user.setOrders(orders);
                            FileService.exportToFile(users, "users.ser");
                            System.out.println("Your order of " + order.getQuantity() + " " + order.pluralize() + " was set successfully");
                        }
                        break;
                    case "4":
                        System.out.println(BoughtItem.print(cart));
                        System.out.println("Total:\t" + BoughtItem.total(cart));
                        if (BoughtItem.hasMeatOrFish(cart)) {
                            System.out.println(("Do not forget to put your " + BoughtItem.meatAndFish(cart) + " in the refrigerator!\n"));
                        }
                        ArrayList<History> history = user.getHistory();
                        ArrayList<BoughtItem> tempCart = new ArrayList<>(cart);
                        History purchase = new History(tempCart, date);
                        history.add(purchase);
                        user.setHistory(history);
                        FileService.writeReceipt(cart);
                        FileService.exportToFile(users, "users.ser");
                        FileService.exportToFile(shelf, "shelf.ser");
                        cart.clear();
                        break;
                    case "5":
                        System.out.println("Your history:");
                        ArrayList<History> uHistory = user.getHistory();
                        uHistory.sort(new HistorySorter());
                        System.out.print(BoughtItem.print(uHistory));
                        System.out.print("1 - Filter by date, 2 - General history: ");
                        n = scanner.nextLine();
                        switch (n) {
                            case "1":
                                while (true) {
                                    while (true) {
                                        System.out.print("Enter the minimal date (dd.mm.yyyy): ");
                                        s = scanner.nextLine();
                                        if (History.dateCheck(s)) {
                                            i = History.dateToDays(s);
                                            break;
                                        }
                                    }
                                    while (true) {
                                        System.out.print("Enter the maximal date (dd.mm.yyyy): ");
                                        t = scanner.nextLine();
                                        if (History.dateCheck(t)) {
                                            j = History.dateToDays(t);
                                            break;
                                        }
                                    }
                                    if (i <= j) break;
                                    else System.out.println("Error: the minimal date can't be bigger than the maximal one");
                                }
                                int minDate = i;
                                int maxDate = j;
                                List<History> tempHistory = user.getHistory().stream().filter(x->x.getFDate() >= minDate && x.getFDate() <= maxDate).toList();
                                System.out.println("History of purchases between " + s +" - " + t);
                                System.out.print(BoughtItem.print(new ArrayList<>(tempHistory)));
                                double[] total = {0.0};
                                tempHistory.forEach(x -> total[0] += x.getItems().stream().mapToDouble(y->y.getPrice() * y.getQuantity()).sum());
                                System.out.println("Total money spent during this time period: " + total[0]);
                                break;
                            case "2":
                                System.out.println("General history:");
                                ArrayList<BoughtItem> generalHistory = generalize(user.getHistory().stream().flatMap(x->x.getItems().stream()).toList());
                                System.out.print(BoughtItem.print(generalHistory));
                                System.out.println("Total money spent: " + generalHistory.stream().mapToDouble(x->x.getPrice() * x.getQuantity()).sum());
                            default:
                                break;
                        }
                        break;
                    case "6":
                        List<BoughtItem> temp = users.stream().flatMap(x->x.getHistory().stream()).flatMap(x->x.getItems().stream()).toList();
                        ArrayList<BoughtItem> allHistory = generalize(temp);
                        allHistory.sort(new BoughtItemSorter().reversed());
                        System.out.println("Most popular item: " + allHistory.get(0).getName());
                        break;
                    default:
                        return;
                }
            }
        }
    }
}