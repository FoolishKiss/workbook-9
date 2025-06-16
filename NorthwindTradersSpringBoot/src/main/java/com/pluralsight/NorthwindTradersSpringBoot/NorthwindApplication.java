package com.pluralsight.NorthwindTradersSpringBoot;

import com.pluralsight.NorthwindTradersSpringBoot.dao.ProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class NorthwindApplication implements CommandLineRunner {

    //create an instance of our filmDao
    @Autowired
    @Qualifier("jdbcProductDao")
    private ProductDao productDao;

    @Override
    public void run(String... args) throws Exception {


        // We create a Scanner object so we can read user input from the console.
        Scanner scanner = new Scanner(System.in);

        // This is a "loop" that will keep showing the menu until the user chooses to exit.
        while (true) {
            // Print the menu options to the screen.
            System.out.println("\n=== Product Admin Menu ===");
            System.out.println("1. List Products");
            System.out.println("2. Add Product");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            // Read the user's choice as a String.
            String choice = scanner.nextLine();

            // Use a "switch" to handle each possible choice.
            switch (choice) {

                case "1":
                    // The user chose option 1 → List all films.

                    // Call the DAO to get a list of all films.
                    List<Product> products = productDao.getAll();

                    // Print the films to the screen.
                    System.out.println("\nProducts:");
                    for (Product film : products) {
                        System.out.println(film);
                    }

                    break;

                case "2":
                    // The user chose option 2 → Add a new film.

                    // Ask the user for the film's title.
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();

                    // Ask the user for the film's rental rate.
                    System.out.print("Enter product price: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    System.out.println("Enter category ID: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());

                    // Create a new Product object and set its data.
                    Product newProduct = new Product();
                    newProduct.setName(name);
                    newProduct.setPrice(price);
                    newProduct.setCategoryId(categoryId);

                    // Add the new film to the DAO (which stores it in memory).
                    productDao.add(newProduct);

                    // Let the user know that the film was added.
                    System.out.println("Product added successfully.");

                    break;

                case "3":
                    // The user chose option 3 → Exit the program.

                    // Print a goodbye message.
                    System.out.println("Goodbye!");

                    // End the program with a success status (0).
                    System.exit(0);

                default:
                    // The user entered something that is not a valid option.

                    // Tell the user the input was invalid and show the menu again.
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

}
