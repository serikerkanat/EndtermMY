import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_lesson";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Erkanat2005";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 5) {
            System.out.println("Choose an option:");
            System.out.println("1. Create Task");
            System.out.println("2. Read Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createTask(connection, scanner);
                    break;
                case 2:
                    readTasks(connection);
                    break;
                case 3:
                    updateTask(connection, scanner);
                    break;
                case 4:
                    deleteTask(connection, scanner);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        }
        connection.close();
        scanner.close();
    }

    private static void createTask(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter name:");
        String name = scanner.next();
        System.out.println("Enter lastname:");
        String lastname = scanner.next();
        System.out.println("Enter age:");
        int age = scanner.nextInt();
        String sql = "insert into mein2(name, lastname, age) values(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, lastname);
        statement.setInt(3, age);
        statement.executeUpdate();
        System.out.println("Added successfully");
    }

    private static void readTasks(Connection connection) throws SQLException {
        String sql = "select * from mein2";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("Table:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("lastname");
            int age = resultSet.getInt("age");
            System.out.println("ID: " + id + ", Name:" + name + ", Surname:" + surname + ", Age:" + age);
        }
        resultSet.close();
        statement.close();
    }

    private static void updateTask(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter ID of task to update:");
        int id = scanner.nextInt();
        System.out.println("Enter new name:");
        String name = scanner.next();
        System.out.println("Enter new lastname:");
        String lastname = scanner.next();
        System.out.println("Enter new age:");
        int age = scanner.nextInt();
        String sql = "update mein2 set name = ?, lastname = ?, age = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, lastname);
        statement.setInt(3, age);
        statement.setInt(4, id);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Task updated successfully");
        } else {
            System.out.println("Task not found");
        }
        statement.close();
    }

    private static void deleteTask(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter ID of task to delete:");
        int id = scanner.nextInt();
        String sql = "delete from mein2 where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Task deleted successfully");
        } else {
            System.out.println("Task not found");
        }
        statement.close();
    }
}
