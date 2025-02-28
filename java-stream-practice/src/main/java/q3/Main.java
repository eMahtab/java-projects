package q3;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Given a list of product orders with customer ID, product name, quantity, and price,
// find customers who have bought all products at least once (complete product coverage).
// Return their customer IDs.

class Order {
    private String custId;
    private String productName;
    private int quantity;
    private double price;

    public Order(String custId, String productName, int quantity, double price) {
        this.custId = custId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    public String getCustId(){
        return custId;
    }
    public String getProductName(){
        return productName;
    }

}

public class Main {

    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("C1", "Hoka Swift", 1, 2200.0),
                new Order("C1", "Kreo Webcam", 1, 3000),
                new Order("C1", "Logitech Wireless Mouse", 1, 1200),
                new Order("C2", "Logitech Wireless Mouse", 1, 1200)
        );

        Set<String> allProducts = orders.stream().map(Order::getProductName).collect(Collectors.toSet());

        Map<String,Set<String>> purchases = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustId,
                         Collectors.mapping(Order::getProductName, Collectors.toSet())));

        purchases.entrySet().stream()
                .filter(entry -> entry.getValue().size() == allProducts.size())
                .map(Map.Entry::getKey)
                .forEach(id -> System.out.println("Bought all products :"+id));
    }
}
