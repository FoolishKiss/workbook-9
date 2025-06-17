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

    //create an instance of our productDao
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
            System.out.println("3. Delete Product");
            System.out.println("4. Update Product");
            System.out.println("5. Search Product");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            // Read the user's choice as a String.
            String choice = scanner.nextLine();

            // Use a "switch" to handle each possible choice.
            switch (choice) {

                case "1":
                    // The user chose option 1 → List all products.
                    listProducts();
                    break;

                case "2":
                    // The user chose option 2 → Add a new product.
                    addProduct(scanner);
                    break;

                case "3":
                    // The user chose option 3 → delete a product.
                    deleteProduct(scanner);

                case "4":
                    // The user chose option 4 → update a product.
                    updateProduct(scanner);

                case "5":
                    // The user chose option 5 → search a product.
                    searchByKeyword(scanner);

                case "6":
                    // The user chose option 3 → Exit the program.

                    // Print a goodbye message.
                    System.out.println("\nGoodbye!");

                    // End the program with a success status (0).
                    System.exit(0);

                default:
                    // The user entered something that is not a valid option.

                    // Tell the user the input was invalid and show the menu again.
                    System.out.println("\nInvalid choice. Please try again.");
                    break;
            }
        }
    }

    private void listProducts() {
        List<Product> products = productDao.getAll();
        // Print the product's to the screen.
       products.forEach(System.out::println);

    }

    private void addProduct(Scanner scanner) {
        // Ask the user for the product's title.
        System.out.print("\nEnter product name: ");
        String name = scanner.nextLine();

        // Ask the user for the product's rental rate.
        System.out.print("\nEnter product price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("\nEnter category ID: ");
        int categoryId = Integer.parseInt(scanner.nextLine());

        // Create a new Product object and set its data.
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setCategoryId(categoryId);

        // Add the new film to the DAO (which stores it in memory).
        productDao.add(newProduct);

        // Let the user know that the product was added.
        System.out.println("\nProduct added successfully.");
    }

    private void deleteProduct(Scanner scanner) {
        System.out.println("\nEnter product Id to delete: ");
        int productId = Integer.parseInt(scanner.nextLine());
        productDao.delete(productId);
        System.out.println("\nProduct deleted successfully.");
    }

    private void updateProduct(Scanner scanner) {
        System.out.println("\nEnter product ID to update: ");
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.println("\nEnter new product name: ");
        String name = scanner.nextLine();
        System.out.println("\nEnter new category ID: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("\nEnter new product price: ");
        int categoryId = Integer.parseInt(scanner.nextLine());

        Product product = new Product(productId, name, categoryId, price);
        productDao.update(product);
        System.out.println("\nProduct updated successfully.");
    }

    private void searchByKeyword(Scanner scanner) {
        System.out.println("\nEnter search keyword: ");
        String keyword = scanner.nextLine();
        List<Product> results = productDao.searchByKeyword(keyword);
        if (results.isEmpty()) {
            System.out.println("\nNo products found.");
        } else {
            results.forEach(System.out::println);
        }
    }

}
