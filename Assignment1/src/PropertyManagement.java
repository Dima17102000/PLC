import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
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

    public List<Apartment> getOldestApartments() {
        List<Apartment> apartments = propertymanagementDAO.getApartmentList();

        if (apartments.isEmpty()) {
            return List.of();  // Пустой список, если нет квартир
        }

        // Находим минимальный (старейший) год постройки
        int oldestYear = apartments.stream()
                .mapToInt(Apartment::getYearOfConstruction)  // .mapToInt((aprt)->{return aprt.getYearOfConstruction;});
                .min()
                .orElseThrow();

        // Возвращаем все квартиры, которые построены в этот старейший год
        return apartments.stream()
                .filter(apartment -> apartment.getYearOfConstruction() == oldestYear)
                .collect(Collectors.toList());
    }
}
