import java.text.DecimalFormat;
public class GiftedApartment extends Apartment {
    private double rentPerSquareMeter;
    private int numberOfTenants;
    private int revenue;
    public GiftedApartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                           String postalCode, String street, String houseNumber, String apartmentNumber,
                           double rentPerSquareMeter, int numberOfTenants,int revenue) {
        super(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber);
        this.rentPerSquareMeter = rentPerSquareMeter;
        this.numberOfTenants = numberOfTenants;
        this.revenue = revenue;
    }

    @Override
    public double getTotalCost() {
    	double baseRent = getArea() * rentPerSquareMeter;
    
    	// Apply tenant surcharge if more than 1 tenant
    	double tenantSurcharge = (numberOfTenants > 1) ? Math.min((numberOfTenants - 1) * 0.025, 0.10) : 0;
    	double totalRent = baseRent * (1 + tenantSurcharge);
    
   	 // Calculate revenue contribution, capped at 10 units
    	double revenueContribution = revenue;
    	if (revenueContribution > 10) {
        revenueContribution = 10;
    	}
    
    // Add the revenue contribution to the total cost
    	return totalRent + revenueContribution;
    }
    
    
   
    @Override
    public String toString() 
    {
        DecimalFormat df = Apartment.getDecimalFormat();
        return String.format(
        "Type:              GA\n" +
        super.toString() + "\n" +
        "Rent/m2:           %s\n" +
        "Number of Tenants: %d\n" +
        "Revenue:           %d\n",
        df.format(rentPerSquareMeter),numberOfTenants,revenue
        );
    }
}


