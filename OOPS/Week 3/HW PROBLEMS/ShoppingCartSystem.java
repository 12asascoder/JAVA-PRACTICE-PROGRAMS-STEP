import java.util.*;

class Product {
    String productId;
    String productName;
    double price;
    String category;
    int stockQuantity;

    static int totalProducts = 0;
    static String[] categories = {"Electronics","Books","Clothing","Home","Grocery","Toys"};
    private static int pidCounter = 0;

    public Product(String name, double price, String category, int stockQuantity) {
        this.productId = generateId();
        this.productName = name;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        totalProducts++;
    }

    private static String generateId() { pidCounter++; return "P" + String.format("%03d", pidCounter); }

    static Product findProductById(Product[] products, String productId) {
        for (Product p : products) if (p != null && p.productId.equals(productId)) return p;
        return null;
    }
    static Product[] getProductsByCategory(Product[] products, String category) {
        // count first
        int count = 0;
        for (Product p : products) if (p != null && p.category.equalsIgnoreCase(category)) count++;
        Product[] result = new Product[count];
        int idx = 0;
        for (Product p : products) if (p != null && p.category.equalsIgnoreCase(category)) result[idx++] = p;
        return result;
    }

    @Override public String toString() {
        return productId + " | " + productName + " | ₹" + String.format("%.2f",price) + " | " + category + " | Stock: " + stockQuantity;
    }
}

class ShoppingCart {
    String cartId;
    String customerName;
    Product[] products;
    int[] quantities;
    double cartTotal;
    private static int cidCounter = 0;
    private int size = 0;

    public ShoppingCart(String customerName) {
        this.cartId = "C" + String.format("%03d", ++cidCounter);
        this.customerName = customerName;
        this.products = new Product[50];
        this.quantities = new int[50];
        this.cartTotal = 0;
    }

    public boolean addProduct(Product product, int qty) {
        if (product == null || qty <= 0) return false;
        if (product.stockQuantity < qty) {
            System.out.println("Insufficient stock for " + product.productName);
            return false;
        }
        // check if already in cart
        for (int i = 0; i < size; i++) {
            if (products[i].productId.equals(product.productId)) {
                products[i].stockQuantity -= qty;
                quantities[i] += qty;
                calculateTotal();
                return true;
            }
        }
        products[size] = product;
        quantities[size] = qty;
        product.stockQuantity -= qty;
        size++;
        calculateTotal();
        return true;
    }

    public boolean removeProduct(String productId) {
        for (int i = 0; i < size; i++) {
            if (products[i].productId.equals(productId)) {
                // restock
                products[i].stockQuantity += quantities[i];
                // shift left
                for (int j = i; j < size-1; j++) {
                    products[j] = products[j+1];
                    quantities[j] = quantities[j+1];
                }
                products[size-1] = null;
                quantities[size-1] = 0;
                size--;
                calculateTotal();
                return true;
            }
        }
        return false;
    }

    public void calculateTotal() {
        cartTotal = 0;
        for (int i = 0; i < size; i++) cartTotal += products[i].price * quantities[i];
    }

    public void displayCart() {
        System.out.println("---- Cart " + cartId + " for " + customerName + " ----");
        for (int i = 0; i < size; i++) {
            System.out.println(products[i].productId + " x " + quantities[i] + " = ₹" + String.format("%.2f", products[i].price * quantities[i]));
        }
        System.out.println("Cart Total: ₹" + String.format("%.2f",cartTotal));
    }

    public void checkout() {
        if (size == 0) {
            System.out.println("Cart is empty.");
            return;
        }
        displayCart();
        System.out.println("Checkout successful. Thank you, " + customerName + "!");
        // clear cart
        for (int i = 0; i < size; i++) { products[i] = null; quantities[i] = 0; }
        size = 0;
        cartTotal = 0;
    }
}

public class ShoppingCartSystem {
    private static void showMenu() {
        System.out.println("\n=== Online Shopping Menu ===");
        System.out.println("1. List all products");
        System.out.println("2. Browse by category");
        System.out.println("3. Add product to cart");
        System.out.println("4. Remove product from cart");
        System.out.println("5. View cart");
        System.out.println("6. Checkout");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    public static void main(String[] args) {
        // Seed products (at least 10)
        Product[] catalog = new Product[20];
        catalog[0]= new Product("Laptop 14\"", 55000, "Electronics", 10);
        catalog[1]= new Product("Smartphone X", 35000, "Electronics", 20);
        catalog[2]= new Product("Coffee Maker", 4500, "Home", 15);
        catalog[3]= new Product("Cotton T-Shirt", 799, "Clothing", 50);
        catalog[4]= new Product("Wireless Mouse", 899, "Electronics", 30);
        catalog[5]= new Product("Fantasy Novel", 499, "Books", 40);
        catalog[6]= new Product("Non-stick Pan", 1299, "Home", 25);
        catalog[7]= new Product("Building Blocks", 999, "Toys", 18);
        catalog[8]= new Product("Organic Rice 5kg", 699, "Grocery", 40);
        catalog[9]= new Product("Denim Jeans", 1499, "Clothing", 35);

        ShoppingCart cart = new ShoppingCart("Customer A");

        // Menu loop (works interactively; also runs a small scripted demo if no input is provided)
        Scanner sc = new Scanner(System.in);
        boolean scripted = false;
        try { sc.hasNextLine(); } catch (Exception e) { scripted = true; }

        if (scripted) {
            // Scripted demo
            cart.addProduct(catalog[0], 1);
            cart.addProduct(catalog[4], 2);
            cart.displayCart();
            cart.removeProduct(catalog[4].productId);
            cart.displayCart();
            cart.checkout();
            return;
        }

        while (true) {
            showMenu();
            int choice = -1;
            try { choice = Integer.parseInt(sc.nextLine()); } catch (Exception e) { choice = -1; }
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    System.out.println("\n--- Product Catalog ---");
                    for (Product p : catalog) if (p != null) System.out.println(p);
                    break;
                case 2:
                    System.out.print("Enter category: ");
                    String cat = sc.nextLine();
                    Product[] list = Product.getProductsByCategory(catalog, cat);
                    if (list.length == 0) System.out.println("No products in " + cat);
                    else for (Product p : list) System.out.println(p);
                    break;
                case 3:
                    System.out.print("Enter Product ID: ");
                    String pid = sc.nextLine();
                    System.out.print("Enter quantity: ");
                    int q = Integer.parseInt(sc.nextLine());
                    Product prod = Product.findProductById(catalog, pid);
                    if (prod == null) System.out.println("Product not found.");
                    else if (cart.addProduct(prod, q)) System.out.println("Added.");
                    break;
                case 4:
                    System.out.print("Enter Product ID to remove: ");
                    String rid = sc.nextLine();
                    if (cart.removeProduct(rid)) System.out.println("Removed.");
                    else System.out.println("Not in cart.");
                    break;
                case 5:
                    cart.displayCart();
                    break;
                case 6:
                    cart.checkout();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        sc.close();
        System.out.println("Goodbye!");
    }
}
