import java.util.List;

public class PropertyManagement {
    private PropertyManagementDAO propertymanagementDAO;

    public PropertyManagement(PropertyManagementDAO propertymanagementDAO) {
        this.propertymanagementDAO = propertymanagementDAO;
    }

    public List<Apartment> getAllApartments() {
        return propertymanagementDAO.getApartmentList();
    }

    public Apartment getApartmentById(int id) {
        return propertymanagementDAO.getApartmentById(id);
    }

    public void addApartment(Apartment apartment) {
        propertymanagementDAO.saveApartment(apartment);
    }

    public void deleteApartment(int id) {
        propertymanagementDAO.deleteApartment(id);
    }

    public int getTotalApartments() {
        return propertymanagementDAO.getApartmentList().size();
    }

    public double getAverageTotalCost() {
        List<Apartment> apartments = propertymanagementDAO.getApartmentList();
        if (apartments.isEmpty()) {
            return 0.0;
        }
        double totalCost = apartments.stream().mapToDouble(Apartment::getTotalCost).sum();
        return totalCost / apartments.size();
    }

    public Apartment getOldestApartment() {
        return propertymanagementDAO.getApartmentList().stream()
                .min((a1, a2) -> Integer.compare(a1.getYearOfConstruction(), a2.getYearOfConstruction()))
                .orElse(null);
    }
}
