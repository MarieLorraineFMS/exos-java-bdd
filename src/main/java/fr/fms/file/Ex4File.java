package fr.fms.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import fr.fms.utils.AppLogger;
import fr.fms.utils.Helpers;

public final class Ex4File {

    private static void pause(int ms) {
        Helpers.pause(ms);
    }

    private static void spacer() {
        Helpers.spacer();
    }

    private static final java.util.Scanner SC = new java.util.Scanner(System.in);

    //////////////////// DELAY ///////////////////////

    private static final int DELAY_MS = 80;

    //////////////////// FILE ////////////////////////

    private static final Path ORDER_FILE = Path.of("order.txt");

    ////////////// INTERFACES /////////////////////////

    private interface Labeled {
        String label();

        double price();

        // "AUCUN" / "AUCUNE" = "no selection"
        default boolean isNone() {
            String up = label().toUpperCase();
            return up.contains("AUCUN") || up.contains("AUCUNE");
        }
    }

    ////////////// ENUMS //////////////////////////////

    /** Entrées */
    private enum Entree implements Labeled {
        SALADE("salade", 4.50),
        SOUPE("soupe", 4.00),
        QUICHE("quiche", 5.00),
        AUCUNE("aucune", 0.00);

        private final String label;
        private final double price;

        Entree(String label, double price) {
            this.label = label;
            this.price = price;
        }

        public String label() {
            return label;
        }

        public double price() {
            return price;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /** Plats */
    private enum Plat implements Labeled {
        POULET("poulet", 12.00),
        BOEUF("boeuf", 13.50),
        POISSON("poisson", 14.00),
        VEGETARIEN("vegetarien", 12.50),
        VEGAN("vegan", 12.50),
        AUCUN("aucun", 0.00);

        private final String label;
        private final double price;

        Plat(String label, double price) {
            this.label = label;
            this.price = price;
        }

        public String label() {
            return label;
        }

        public double price() {
            return price;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /** Accompagnements */
    private enum Accompagnement implements Labeled {
        RIZ("riz", 3.00),
        PATES("pates", 3.00),
        FRITES("frites", 3.50),
        LEGUMES("legumes", 3.50),
        AUCUN("aucun", 0.00);

        private final String label;
        private final double price;

        Accompagnement(String label, double price) {
            this.label = label;
            this.price = price;
        }

        public String label() {
            return label;
        }

        public double price() {
            return price;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /** Boissons */
    private enum Boisson implements Labeled {
        EAU_PLATE("eau plate", 2.00),
        EAU_GAZEUSE("eau gazeuse", 2.50),
        SODA("soda", 3.00),
        VIN("vin", 4.50),
        AUCUNE("aucune", 0.00);

        private final String label;
        private final double price;

        Boisson(String label, double price) {
            this.label = label;
            this.price = price;
        }

        public String label() {
            return label;
        }

        public double price() {
            return price;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /** Desserts */
    private enum Dessert implements Labeled {
        TARTE_MAISON("tarte maison", 5.00),
        MOUSSE_AU_CHOCOLAT("mousse au chocolat", 5.50),
        TIRAMISU("tiramisu", 5.50),
        AUCUN("aucun", 0.00);

        private final String label;
        private final double price;

        Dessert(String label, double price) {
            this.label = label;
            this.price = price;
        }

        public String label() {
            return label;
        }

        public double price() {
            return price;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /////////////// MAIN /////////////////////////////////

    public static void main(String[] args) {
        Helpers.printlnColor(Helpers.CYAN, "Bonjour, bienvenue chez MyResto !");
        pause(DELAY_MS);
        Helpers.printlnColor(Helpers.YELLOW, "Combien de menus souhaitez-vous ? ");

        int menusCount = readInt("Erreur : ce n'est pas un chiffre");
        int counterCommande = 0;

        final List<List<String>> allOrdersForFile = new ArrayList<>();// Store orders
        final List<List<String>> allReceipts = new ArrayList<>(); // Store receipts
        final List<Double> allTotals = new ArrayList<>(); // Store totals per order

        // Store totals
        double grandTotal = 0.0;

        while (menusCount > 0) {
            menusCount--;
            counterCommande++;

            spacer();
            Helpers.printlnColor(Helpers.CYAN, "--------------------");
            Helpers.printlnColor(Helpers.CYAN, "Menu n° " + counterCommande);
            pause(DELAY_MS);

            final List<String> currentOrderForFile = new ArrayList<>();

            // Receipt lines for CLI & total
            final List<String> receiptLines = new ArrayList<>();
            double total = 0.0;

            total += addChoice(receiptLines, currentOrderForFile, "entree",
                    chooseEnum("Choix Entrée :", Entree.values()));
            total += addChoice(receiptLines, currentOrderForFile, "plat", chooseEnum("Choix Plat :", Plat.values()));
            total += addChoice(receiptLines, currentOrderForFile, "accompagnement",
                    chooseEnum("Choix Accompagnement :", Accompagnement.values()));
            total += addChoice(receiptLines, currentOrderForFile, "boisson",
                    chooseEnum("Choix Boisson :", Boisson.values()));
            total += addChoice(receiptLines, currentOrderForFile, "dessert",
                    chooseEnum("Choix Dessert :", Dessert.values()));

            currentOrderForFile.add("TOTAL : " + String.format("%.2f", total) + " €");
            allOrdersForFile.add(currentOrderForFile);
            grandTotal += total;

            allReceipts.add(receiptLines);
            allTotals.add(total);

            String label = counterCommande > 1 ? "Menu " + counterCommande : "Menu";
            Helpers.printlnColor(
                    Helpers.GREEN,
                    label + " enregistré ✅ (" + String.format("%.2f", total) + " €)");

        }
        spacer();
        Helpers.printlnColor(Helpers.YELLOW, "==================== RÉCAPITULATIF ====================");

        for (int i = 0; i < allReceipts.size(); i++) {
            int orderNumber = i + 1;

            spacer();
            Helpers.printlnColor(Helpers.CYAN, "Menu n° " + orderNumber);
            printRecap(allReceipts.get(i));
            Helpers.printlnColor(Helpers.YELLOW, "TOTAL : " + String.format("%.2f", allTotals.get(i)) + " €");
        }

        // Write file
        writeOrdersToFile(allOrdersForFile, ORDER_FILE, grandTotal);
        spacer();
        System.out
                .println(Helpers.YELLOW + "TOTAL GLOBAL : " + String.format("%.2f", grandTotal) + " €" + Helpers.RESET);
        Helpers.printlnColor(Helpers.CYAN, "Au revoir et à bientôt chez MyResto.");

    }

    ///////////////////////// CHOICE //////////////////////////////////////////

    /**
     * Generic choose
     * - Renders values
     * - Returns the selected enum constant
     */
    private static <E extends Enum<E> & Labeled> E chooseEnum(String title, E[] values) {
        Helpers.title(title);
        pause(DELAY_MS);

        for (int i = 0; i < values.length; i++) {
            System.out.printf("  %d) %s%n", i + 1, values[i].label());
            pause(DELAY_MS);
        }

        Helpers.printlnColor(Helpers.YELLOW, "Votre choix : ");
        int idx;
        while (true) {
            idx = readInt("Erreur : ce n'est pas un chiffre");
            if (idx >= 1 && idx <= values.length) {
                break;
            }
            Helpers.printlnColor(Helpers.RED, "Choix inconnu. Réessayez : ");
        }

        E chosen = values[idx - 1];
        Helpers.printlnColor(Helpers.YELLOW, "→ " + chosen.label());
        return chosen;
    }

    /////////////////////// RECEIPT + FILE HELPERS ////////////////////////////

    /**
     * Add one choice :
     * - Adds console receipt line with price
     * - Adds item label to file list
     * - Returns price to accumulate totals
     */
    private static <E extends Enum<E> & Labeled> double addChoice(
            List<String> receiptLines,
            List<String> fileLines,
            String category,
            E chosen) {
        if (chosen == null || chosen.isNone()) {
            return 0.0;
        }

        // CLI line
        receiptLines.add(category + " : " + chosen.label() + " - " + String.format("%.2f", chosen.price()) + " €");

        // File line
        fileLines.add(category + " : " + chosen.label() + " - " + String.format("%.2f", chosen.price()) + " €");

        return chosen.price();
    }

    /**
     * Write "order.txt" with :
     * - One header line per order
     * - One line per item
     * - TOTAL line at the end
     */
    private static void writeOrdersToFile(List<List<String>> orders, Path file, double grandTotal) {
        try (BufferedWriter w = Files.newBufferedWriter(
                file,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (int i = 0; i < orders.size(); i++) {
                int orderNumber = i + 1;

                // Header line
                w.write("******************** Commande N°" + orderNumber + " ********************");
                w.newLine();

                // Items lines
                for (String line : orders.get(i)) {
                    w.write(line);
                    w.newLine();
                }
                // Blank line between orders
                w.newLine();
            }
            // Total line
            w.write("TOTAL : " + String.format("%.2f", grandTotal) + " €");
            spacer();
            AppLogger.ok("Fichier généré : " + file.toAbsolutePath());
        } catch (IOException e) {
            AppLogger.exception("File write failed", e);
        }
    }

    ///////////////////// RECAP RENDER ///////////////////////////////////

    /**
     * Print human-readable recap for 1 menu
     */
    private static void printRecap(List<String> receiptLines) {
        if (receiptLines == null || receiptLines.isEmpty()) {
            Helpers.printlnColor(Helpers.RED, "(aucun élément sélectionné)");
            return;
        }
        for (String line : receiptLines) {
            System.out.println(line);
        }
    }

    /////////////////////////////// HELPERS //////////////////////////////

    /**
     * Read an int, show error if invalid & ask until valid.
     */
    private static int readInt(String errorPrompt) {
        while (!SC.hasNextInt()) {
            Helpers.printlnColor(Helpers.RED, errorPrompt);
            SC.nextLine(); // discard invalid token to avoid infinite loop
            Helpers.printlnColor(Helpers.YELLOW, "Votre choix (chiffre) : ");
        }
        int input = SC.nextInt();
        SC.nextLine(); // consume endline
        return input;
    }

}
