import java.text.DecimalFormat;
public class RentedApartment extends Apartment {
    private double rentPerSquareMeter;
    private int numberOfTenants;

    public RentedApartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                           String postalCode, String street, String houseNumber, String apartmentNumber,
                           double rentPerSquareMeter, int numberOfTenants) {
        super(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber);
        this.rentPerSquareMeter = rentPerSquareMeter;
        this.numberOfTenants = numberOfTenants;
    }

    @Override
    public double getTotalCost() {
        /*
        double surcharge = (numberOfTenants > 1) ? Math.min(0.025 * (numberOfTenants - 1), 0.10) * rentPerSquareMeter * getArea() : 0;
        return rentPerSquareMeter * getArea() + surcharge;
        */
        double tenantSurcharge = (numberOfTenants > 1) ? Math.min((numberOfTenants - 1) * 0.025, 0.10) : 0;
        return getArea() * rentPerSquareMeter * (1 + tenantSurcharge);
    }
    
    
   
    @Override
    public String toString() 
    {
        DecimalFormat df = Apartment.getDecimalFormat();
        return String.format(
        "Type:              RA\n" +
        super.toString() + "\n" +
        "Rent/m2:           %s\n" +
        "Number of Tenants: %d\n",
        df.format(rentPerSquareMeter), numberOfTenants
        );
    }
}


