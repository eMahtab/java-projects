package q1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Given a list of numbers, find the duplicate numbers

public class Main {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(0, 1, 2, 3, 4, 1, 2, 3, 4, 6);
        numbers.stream()
                .collect(Collectors.groupingBy(num -> num,
                        Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .forEach(System.out::println);

    }
}