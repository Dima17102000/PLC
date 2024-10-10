import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Year;
import java.util.Locale;

public abstract class Apartment implements Serializable {
    private int id;
    private double area;
    private int rooms;
    private int floor;
    private int yearOfConstruction;
    private String postalCode;
    private String street;
    private String houseNumber;
    private String apartmentNumber;

    // Constructor to initialize the apartment fields
    public Apartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                     String postalCode, String street, String houseNumber, String apartmentNumber) {
        if (yearOfConstruction > Year.now().getValue()) {
            throw new IllegalArgumentException("Error: Invalid year of construction.");
        }
        this.id = id;
        this.area = area;
        this.rooms = rooms;
        this.floor = floor;
        this.yearOfConstruction = yearOfConstruction;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    // Getters for apartment properties
    public int getId() {
        return id;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public double getArea() {
        return area;
    }

    public int getRooms() {
        return rooms;
    }

    public int getFloor() {
        return floor;
    }

    public int getAge() {
        return Year.now().getValue() - yearOfConstruction;
    }

    // Abstract method to be implemented by subclasses for calculating the total cost
    public abstract double getTotalCost();

    // Overriding toString() method to format and display apartment details
    @Override
    public String toString() {
        DecimalFormat df = getDecimalFormat();
        return String.format(
            "Id:                %d\n" +
            "Area:              %s\n" +
            "Rooms:             %d\n" +
            "Floor:             %d\n" +
            "Year Built:        %d\n" +
            "Postal Code:       %s\n" +
            "Street:            %s\n" +
            "House Number:      %s\n" +
            "Apartment Number:  %s",
            id, df.format(area), rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber
        );
    }

    // Static method to get DecimalFormat instance with dot as the decimal separator
    public static DecimalFormat getDecimalFormat() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.US);
        dfs.setDecimalSeparator('.');
        return new DecimalFormat("0.00", dfs);
    }
}
