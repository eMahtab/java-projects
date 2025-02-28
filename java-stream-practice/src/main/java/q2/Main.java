package q2;

// Given a list of transactions with date, amount, and category,
// find the category with the highest total transaction amount in each month of 2024.
// Return a map of month to category.

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

enum Category{
    GROCERY,
    SMARTPHONE,
    FOOTWEAR,
    APPAREL
}

class Transaction {
    private LocalDate date;
    private double amount;
    private Category category;

    Transaction(LocalDate date, double amount, Category category) {
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    public LocalDate getDate(){
        return date;
    }
    public Category getCategory() {
        return category;
    }
    public double getAmount(){
        return amount;
    }
}

public class Main {

    public static void main(String[] args) {
        List<Transaction> transactions = Arrays.asList(
              new Transaction(LocalDate.of(2025, 3, 10), 20000, Category.SMARTPHONE),
              new Transaction(LocalDate.of(2024, 3, 10), 20000, Category.FOOTWEAR),
              new Transaction(LocalDate.of(2024, 4, 20), 10000, Category.GROCERY),
              new Transaction(LocalDate.of(2024, 4, 25), 15000, Category.FOOTWEAR),
              new Transaction(LocalDate.of(2024, 4, 30), 6000, Category.GROCERY),
              new Transaction(LocalDate.of(2024, 2, 1), 20000, Category.APPAREL),
              new Transaction(LocalDate.of(2024, 2, 21), 10000, Category.SMARTPHONE)
        );

        Map<Integer, Map<Category,Double>> monthAndCategoryWise = transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == 2024)
                .collect(Collectors.groupingBy(transaction -> transaction.getDate().getMonthValue(),
                        Collectors.groupingBy(Transaction::getCategory,
                                Collectors.summingDouble(Transaction::getAmount))
                        ));

        Map<Integer, Category> result = monthAndCategoryWise.entrySet().stream()
                                        .collect(
                                                Collectors.toMap(Map.Entry::getKey,
                                                entry -> entry.getValue().entrySet().stream()
                                                                                   .max(Map.Entry.comparingByValue())
                                                                                   .map(Map.Entry::getKey)
                                                                                   .orElse(null)
                                                )
                                             );

        // The max() method returns an Optional<Map.Entry<Category, Double>> object,
        // the map(Map.Entry::getKey) converts this to an Optional<Category>.
        // orElse(null), returns Category if present otherwise returns null.

        result.forEach((k,v) -> System.out.println(k+" =>"+v));
    }
}
