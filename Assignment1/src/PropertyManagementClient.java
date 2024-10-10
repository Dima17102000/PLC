import java.time.Year; 
import java.text.DecimalFormat;
import java.util.List;
public class PropertyManagementClient {
    public static void main(String[] args) {
        args = combineQuotedStrings(args);
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
                        

                           String type = args[2];
                           if (type.equals("OA") && args.length != 14) {
                                throw new IllegalArgumentException("Error: Invalid parameter.");
                           }
                           if (type.equals("RA") && args.length != 14) {
                                throw new IllegalArgumentException("Error: Invalid parameter.");
                           }
                           
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
                             

                           double operatingCosts = Double.parseDouble(args[12]);
                           double reserveFund = Double.parseDouble(args[13]);

                           OwnedApartment ownedApartment = new OwnedApartment(id, area, rooms, floor, year, postalCode, street, houseNumber, apartmentNumber, operatingCosts, reserveFund);
                           management.addApartment(ownedApartment);
                           System.out.println("Info: Apartment " + id + " added.");

                        } 
                        else if (type.equals("RA")) 
                        {
                             

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
                    List<Apartment> oldestApartments = management.getOldestApartments();
                    if (oldestApartments.isEmpty()) 
                    {
                        System.out.println("No apartments found.");
                    } 
                    
                    else 
                    {
                        for (Apartment apartment : oldestApartments)
                        {
                             System.out.println("Id: " + apartment.getId());
                        }
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Error: Invalid command.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String[] combineQuotedStrings(String[] args) {
        List<String> combinedArgs = new ArrayList<>();
        StringBuilder current = null;

        for (String arg : args) {
            if (arg.startsWith("\"")) { // Start of a quoted string
                current = new StringBuilder(arg);
            } else if (arg.endsWith("\"") && current != null) { // End of a quoted string
                current.append(" ").append(arg);
                combinedArgs.add(current.toString().replaceAll("^\"|\"$", ""));  // Remove the surrounding quotes
                current = null;
            } else if (current != null) { // Inside a quoted string
                current.append(" ").append(arg);
            } else { // Regular argument
                combinedArgs.add(arg);
            }
        }

        return combinedArgs.toArray(new String[0]);
    }
}
