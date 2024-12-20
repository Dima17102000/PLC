public class OwnedApartment extends Apartment {
    private double operatingCostsPerSquareMeter;
    private double reserveFundPerSquareMeter;

    public OwnedApartment(int id, double area, int rooms, int floor, int yearOfConstruction,
                          String postalCode, String street, String houseNumber, String apartmentNumber,
                          double operatingCostsPerSquareMeter, double reserveFundPerSquareMeter) {
        super(id, area, rooms, floor, yearOfConstruction, postalCode, street, houseNumber, apartmentNumber);
        this.operatingCostsPerSquareMeter = operatingCostsPerSquareMeter;
        this.reserveFundPerSquareMeter = reserveFundPerSquareMeter;
    }

    @Override
    public double getTotalCost() {
        double floorSurcharge = (getFloor() > 0) ? getFloor() * 0.02 : 0;  // 2% surcharge per floor above 0
        return getArea() * (operatingCostsPerSquareMeter + reserveFundPerSquareMeter) * (1 + floorSurcharge);
    }
    
    
    @Override
    public String toString() 
    {
        return String.format(
        "Type:              OA\n" +
        super.toString() + "\n" +
        "Operating Costs:   %.2f\n" +
        "Reserve Fund:      %.2f\n",
         operatingCostsPerSquareMeter,reserveFundPerSquareMeter
        );
    }
}
