import java.io.Serializable;

public class Product implements Serializable {
    private static final int NAME_LENGTH = 35;
    private static final int DESCRIPTION_LENGTH = 75;
    private static final int ID_LENGTH = 6;
    
    private String name;
    private String description;
    private String id;
    private double cost;

    public Product(String name, String description, String id, double cost) {
        this.name = padString(name, NAME_LENGTH);
        this.description = padString(description, DESCRIPTION_LENGTH);
        this.id = padString(id, ID_LENGTH);
        this.cost = cost;
    }

    // Utility method to pad strings
    private String padString(String str, int length) {
        return String.format("%-" + length + "s", str);
    }

    // Getter methods for Random Access usage
    public String getName() {
        return name.trim();
    }

    public String getDescription() {
        return description.trim();
    }

    public String getId() {
        return id.trim();
    }

    public double getCost() {
        return cost;
    }

    // toString for displaying the product
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name.trim() + '\'' +
                ", description='" + description.trim() + '\'' +
                ", id='" + id.trim() + '\'' +
                ", cost=" + cost +
                '}';
    }
}
