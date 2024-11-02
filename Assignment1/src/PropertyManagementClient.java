import java.time.Year; 
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList; 
public class PropertyManagementClient {
    public static void main(String[] args) {
   
        args = combineQuotedStrings(args);
        if (args.length < 2) {
            System.out.println("Error: Invalid parameter.");
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
                            } /*else {
                                //System.out.println("");//"Error: Apartment not found. (id=" + id + ")");
                            }
                            */
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
                                System.out.println("Error: Invalid parameter.");
                                return;
                           }
                           
                           else if (type.equals("RA") && args.length != 14) {
                                System.out.println("Error: Invalid parameter.");
                                return;
                           }
                           
                           else if (type.equals("GA") && args.length != 15) {
                                System.out.println("Error: Invalid parameter.");
                                return;
                           }
                           
                           // Check if the id is a valid digit
                           if (!args[3].matches("\\d+")) {
                                System.out.println("Error: Invalid parameter.");
                                return;  // Exit early if id is not a valid digit
                           }
                           
                             int id = Integer.parseInt(args[3]);
                             double area = Double.parseDouble(args[4]);
                  		     int rooms = Integer.parseInt(args[5]);
                  		     int floor = Integer.parseInt(args[6]);
                             int year = Integer.parseInt(args[7]);

                         // Check if the year is valid
                         if (year > Year.now().getValue()) {
                           System.out.println("Error: Invalid year of construction.");
                           return;
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
                        else if(type.equals("GA"))
                        {
                          double giftedr = Double.parseDouble(args[12]);
                          int    giftedt = Integer.parseInt(args[13]);
                          int    giftedrev = Integer.parseInt(args[14]);
                          GiftedApartment gifted = new GiftedApartment(id, area, rooms, floor, year, postalCode, street, houseNumber, apartmentNumber, giftedr,giftedt,giftedrev);
                           management.addApartment(gifted);
                           System.out.println("Info: Apartment " + id + " added.");
                        }
                        else 
                        {
                                 System.out.println("Error: Invalid parameter.");
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
                          int id = Integer.parseInt(args[2]);
                          Apartment apartment = management.getApartmentById(id);
                          if (apartment == null) {
                              // Print the error message if the apartment doesn't exist
                             System.out.println("Error: Apartment not found. (id=" + id + ")");
                          } 
                          else 
                          {
                              // Only proceed with deletion if the apartment exists
                              management.deleteApartment(id);
                              System.out.println("Info: Apartment " + id + " deleted.");
                          }
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
                                                 (counttype.equals("RA") && apartment instanceof RentedApartment)||
                                                 (counttype.equals("GA") && apartment instanceof GiftedApartment))
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
                    
								case "pricerange":
                    
                    int min = Integer.parseInt(args[2]);
                    int max = Integer.parseInt(args[3]);
                    
                    List<Apartment> apartments = management.getApartmentsPriceRange(min,max);
                    
                    if(apartments.isEmpty())
                    {
                       System.out.println("No apartments found.");
                    }
                    
                    else
                    {
                        for(Apartment ap: apartments)
                        {
                            System.out.println(ap);
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Error: Invalid parameter.");
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
