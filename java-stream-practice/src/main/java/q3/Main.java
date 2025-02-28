package q3;

// From a list of employees with name, department, salary, and years of experience,
// find departments where the average experience is above 5 years
// AND the average salary is below the company-wide average salary.

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

enum Department {
    SALES,
    MARKETING,
    TECH
}

class Employee {
    private String name;
    private Department department;
    private double salary;
    private float experience;

    Employee(String name, Department department, double salary, float experience) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.experience = experience;
    }
    public Department getDepartment(){
        return department;
    }
    public float getExperience() {
        return experience;
    }
    public double getSalary() {
        return salary;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Mahtab", Department.TECH, 3000000.00d, 9.75f),
                new Employee("Alex", Department.SALES, 2700000.00d, 10.50f),
                new Employee("Nyugen", Department.MARKETING, 2800000.00d, 7.25f),
                new Employee("Marc", Department.TECH, 2500000.00d, 6.00f),
                new Employee("Svetlana", Department.MARKETING, 2800000.00d, 7.75f)
        );

        double companyWideAvgSalary = employees.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println("Company Avg Salary:"+companyWideAvgSalary);

        Map<Department, Double> departmentAvgExperience = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                            Collectors.averagingDouble(Employee::getExperience)
                        ));
        Map<Department, Double> departmentAvgSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                            Collectors.averagingDouble(Employee::getSalary)
                        ));
        List<Department> departments = employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .filter(department -> departmentAvgExperience.get(department) > 5.0d &&
                                                 departmentAvgSalary.get(department) < companyWideAvgSalary)
                .collect(Collectors.toList());

        departments.forEach(System.out::println);
    }
}
