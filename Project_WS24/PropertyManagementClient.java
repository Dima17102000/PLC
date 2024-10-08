import java.time.Year; 
import java.text.DecimalFormat;
public class PropertyManagementClient {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Error: Invalid parameter.");
            return;
        }

        String filename = args[0];
        String command = args[1];
        PropertyManagementDAO propertyManagementDAO = new PropertyManagementSerializationDAO(filename);
        PropertyManagement management = new PropertyManagement(propertyManagementDAO);

        try {
            
            switch (command) {
                case "list":
                    if (args.length == 2) {
                        // No ID provided, list all apartments
                        management.getAllApartments().forEach(System.out::println);
                    } else if (args.length == 3) {
                        // ID provided, list the specific apartment
                        try {
                              int id = Integer.parseInt(args[2]);
                              Apartment apartment = management.getApartmentById(id);
                            if (apartment != null) {
                                System.out.println(apartment);
                            } else {
                                System.out.println("Error: Apartment not found. (id=" + id + ")");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Error: Invalid parameter.");
                        }
                        
                    } else {
                        throw new IllegalArgumentException("Error: Invalid parameter.");
                    }
                    break;

                case "add":
                    try 
                    {
                        if (args.length < 13) {
                           throw new IllegalArgumentException("Error: Invalid parameter.");
                        }

                           String type = args[2];
                           int id = Integer.parseInt(args[3]);
                           double area = Double.parseDouble(args[4]);
                  		   int rooms = Integer.parseInt(args[5]);
                  		   int floor = Integer.parseInt(args[6]);
                           int year = Integer.parseInt(args[7]);

                         // Check if the year is valid
                         if (year > Year.now().getValue()) {
                           throw new IllegalArgumentException("Error: Invalid year of construction.");
                         } 

                           String postalCode = args[8];
                           String street = args[9];
                           String houseNumber = args[10];
                           String apartmentNumber = args[11];

                          // Check if apartment already exists
                         if (management.getApartmentById(id) != null) {
                           throw new IllegalArgumentException("Error: Apartment already exists. (id=" + id + ")");
                         }

                         if (type.equals("OA")) 
                         {
                             if (args.length != 14) {
                                  throw new IllegalArgumentException("Error: Invalid parameter.");
                             }

                           double operatingCosts = Double.parseDouble(args[12]);
                           double reserveFund = Double.parseDouble(args[13]);

                           OwnedApartment ownedApartment = new OwnedApartment(id, area, rooms, floor, year, postalCode, street, houseNumber, apartmentNumber, operatingCosts, reserveFund);
                           management.addApartment(ownedApartment);
                           System.out.println("Info: Apartment " + id + " added.");

                        } 
                        else if (type.equals("RA")) 
                        {
                             if (args.length != 14) {
                                  throw new IllegalArgumentException("Error: Invalid parameter.");
                             }

                           double rent = Double.parseDouble(args[12]);
                           int tenants = Integer.parseInt(args[13]);

                           RentedApartment rentedApartment = new RentedApartment(id, area, rooms, floor, year, postalCode, street, houseNumber, apartmentNumber, rent, tenants);
                           management.addApartment(rentedApartment);
                           System.out.println("Info: Apartment " + id + " added.");

                        } 
                        
                        else 
                        {
                                 throw new IllegalArgumentException("Error: Invalid parameter.");
                        }
                    } catch (NumberFormatException e){
                            System.err.println("Error: Invalid parameter.");
                      } 
                      /*catch (IllegalArgumentException e) {
                         System.err.println(e.getMessage());
                      }
                      */
                     break;

                case "delete":
                    if (args.length != 3) {
                        throw new IllegalArgumentException("Error: Invalid parameter.");
                    }

                    try {
                        
                        // Объявляем id локально в этом блоке
                        int id = Integer.parseInt(args[2]);
                        Apartment apartment = management.getApartmentById(id);
                        if (apartment == null) {
                            throw new IllegalArgumentException("Error: Apartment not found. (id=" + id + ")");
                        }


                        management.deleteApartment(id);
                        System.out.println("Info: Apartment " + id + " deleted.");
                    } catch (NumberFormatException e) {
                        System.err.println("Error: Invalid parameter.");
                    }
                    break;

                case "count":
                    if (args.length == 2) {
                        System.out.println(management.getTotalApartments());
                    } else if (args.length == 3) {
                        String counttype = args[2];
                        long count = management.getAllApartments().stream()
                            .filter(apartment -> (counttype.equals("OA") && apartment instanceof OwnedApartment) ||
                                                 (counttype.equals("RA") && apartment instanceof RentedApartment))
                            .count();
                        System.out.println(count);
                    } else {
                        throw new IllegalArgumentException("Error: Invalid parameter.");
                    }
                    break;

                case "meancosts":
                    DecimalFormat df = Apartment.getDecimalFormat();
                    System.out.println(df.format(management.getAverageTotalCost()));
                    break;

                case "oldest":
                    Apartment oldest = management.getOldestApartment();
                    if (oldest != null) {
                        System.out.println("Id: " + oldest.getId());
                    } else {
                        System.out.println("No apartments found.");
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Error: Invalid command.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}