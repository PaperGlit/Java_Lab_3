import item.BoughtItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static item.BoughtItem.*;

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

    public static void writeReceipt(ArrayList<BoughtItem> items) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();
        fileName = extensionCheck(fileName);
        if (createFile(fileName)) {
            try (FileWriter fw = new FileWriter(fileName)) {
                fw.write(print(items));
                fw.write(String.format("\nTotal:\t%.2f", total(items)));
                if (BoughtItem.hasMeatOrFish(items)) {
                    fw.write(String.format("Do not forget to put your %s in the refrigerator!\n", meatAndFish(items)));
                }
            }
            catch (IOException e) {
                System.out.println("An unknown error occurred");
            }
        }
    }
}
