package com.empapp.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeApp {

    private static EmployeeDAO employeeDAO = new EmployeeDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database operation failed: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
            System.out.println("\n------------------------------------\n");
        } while (choice != 5);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Employee Database Application");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");
    }

    private static void addEmployee() throws SQLException {
        System.out.println("\n--- Add New Employee ---");
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee position: ");
        String position = scanner.nextLine();
        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        Employee newEmployee = new Employee(name, position, salary);
        employeeDAO.addEmployee(newEmployee);
    }

    private static void viewAllEmployees() throws SQLException {
        System.out.println("\n--- All Employees ---");
        List<Employee> employees = employeeDAO.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    private static void updateEmployee() throws SQLException {
        System.out.println("\n--- Update Employee ---");
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee existingEmployee = employeeDAO.getEmployeeById(id);
        if (existingEmployee == null) {
            System.out.println("Employee with ID " + id + " not found.");
            return;
        }

        System.out.println("Current details: " + existingEmployee);
        System.out.print("Enter new name (leave blank to keep current: '" + existingEmployee.getName() + "'): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            existingEmployee.setName(name);
        }

        System.out.print("Enter new position (leave blank to keep current: '" + existingEmployee.getPosition() + "'): ");
        String position = scanner.nextLine();
        if (!position.isEmpty()) {
            existingEmployee.setPosition(position);
        }

        System.out.print("Enter new salary (0 to keep current: " + String.format("%.2f", existingEmployee.getSalary()) + "): ");
        double salaryInput = scanner.nextDouble();
        if (salaryInput > 0) {
            existingEmployee.setSalary(salaryInput);
        }
        scanner.nextLine();

        employeeDAO.updateEmployee(existingEmployee);
    }

    private static void deleteEmployee() throws SQLException {
        System.out.println("\n--- Delete Employee ---");
        System.out.print("Enter employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        employeeDAO.deleteEmployee(id);
    }
}