import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyManagementSerializationDAO implements PropertyManagementDAO {
    private String filename;

    public PropertyManagementSerializationDAO(String filename) {
        this.filename = filename;
    }

    @SuppressWarnings("unchecked") // Unterdrückt die Warnung
    @Override
    public List<Apartment> getApartmentList() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Apartment>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();  // Return an empty list if file doesn't exist
        } catch (Exception e) {
            System.err.println("Deserialization error: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public void saveApartment(Apartment apartment) {
        List<Apartment> apartments = getApartmentList();
        apartments.add(apartment);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(apartments);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void deleteApartment(int id) {
        List<Apartment> apartments = getApartmentList();
        apartments.removeIf(apartment -> apartment.getId() == id);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(apartments);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public Apartment getApartmentById(int id) {
        return getApartmentList().stream()
                .filter(apartment -> apartment.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
