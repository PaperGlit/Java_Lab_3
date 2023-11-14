import item.BoughtItem;
import item.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileService {
    private static String extensionCheck(String fileName) {
        return fileName.endsWith(".txt") ? fileName : fileName + ".txt";
    }

    private static boolean createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.createNewFile()) {
                System.out.println("Error: this file already exists");
                return false;
            }
        }
        catch (IOException e) {
            System.out.println("An unknown error occurred");
            return false;
        }
        return true;
    }

    private static double total(ArrayList<BoughtItem> items) {
        return items.stream().mapToDouble(x->x.getPrice()*x.getQuantity()).sum();
    }

    private static String meatAndFish(ArrayList<BoughtItem> items) {
        String mAF = items.stream().filter(x -> x.getType().equals("Meat") || x.getType().equals("Fish")).map(Item::getName).reduce("", (ans, i) -> ans + i + ", ");
        return mAF.substring(0, mAF.length() - 2);
    }

    public static void writeReceipt(ArrayList<BoughtItem> items) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();
        fileName = extensionCheck(fileName);
        if (createFile(fileName)) {
            try (FileWriter fw = new FileWriter(fileName)) {
                StringBuilder sb = new StringBuilder();
                items.forEach(item -> sb.append(item.toString()).append("\n"));
                fw.write(sb.toString());
                fw.write(String.format("\nTotal:\t%.2f\n", total(items)));
                if (items.stream().anyMatch(x -> x.getType().equals("Meat") || x.getType().equals("Fish"))) {
                    fw.write(String.format("Do not forget to put your %s in the refrigerator!\n", meatAndFish(items)));
                }
            }
            catch (IOException e) {
                System.out.println("An unknown error occurred");
            }
        }
    }
}
