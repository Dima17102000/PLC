import java.text.DecimalFormat;
public class GiftedApartment extends Apartment {
    private double rentPerSquareMeter;
    private double share;

    public GiftedApartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                           String postalCode, String street, String houseNumber, String apartmentNumber,
                           double rentPerSquareMeter,double share) {
        super(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber);
        this.rentPerSquareMeter = rentPerSquareMeter;
        this.share = share;
    }

    @Override
    public double getTotalCost() {
        double tenantSurcharge = 1/120 * share;
        if(share > 10)
        {
          share = 0;
        }
        return (getArea() * rentPerSquareMeter) * (1+tenantSurcharge);
    }
    
    
   
    @Override
    public String toString() 
    {
        DecimalFormat df = Apartment.getDecimalFormat();
        return String.format(
        "Type:              CA\n" +
        super.toString() + "\n" +
        "Rent/m2:           %s\n" +
        "Cooperative Share: %s\n",
        df.format(rentPerSquareMeter),df.format(share)
        );
    }
}


