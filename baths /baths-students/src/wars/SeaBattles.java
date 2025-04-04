package wars;

import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @authorTeam CS05 
 * @version 16/02/25
 */

public class SeaBattles implements BATHS , Serializable {
    
     // Game data collections
    private Map<String, Ship> allShips; // All ships in the game
    private Map<Integer, Encounter> encounters; // All available encounters
    private List<Ship> squadron; // Ships in the admiral's squadron
    
    // Game state
    private String admiral;
    private double warChest;


//**************** BATHS ************************** 
    /** Constructor requires the name of the admiral
     * @param adm the name of the admiral
     */  
    public SeaBattles(String adm)
    {
      
       this.admiral = adm;
       this.warChest = 1000.0;
        
       
       // Initialize collections
        this.allShips = new HashMap<>();
        this.encounters = new TreeMap<>();
        this.squadron = new ArrayList<>();
        
       // Setup initial game data
       setupShips();
       setupEncounters();
    }
    
    /** Constructor requires the name of the admiral and the
     * name of the file storing encounters
     * @param admir the name of the admiral
     * @param filename name of file storing encounters
     */  
    public SeaBattles(String admir, String filename)  //Task 3 
    {
      
        this.admiral = admir;
        this.warChest = 1000.0;
        
        // Initialize collections
        this.allShips = new HashMap<>();
        this.encounters = new TreeMap<>();
        this.squadron = new ArrayList<>();
        
        // Setup ships
        setupShips();
       
       // Load encounters from file
       readEncounters(filename);
    }
    
    
    /**Returns a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     * @return a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     **/
    @Override
    public String toString()
    {
        
       StringBuilder sb = new StringBuilder();
        
        sb.append("Admiral: ").append(admiral).append("\n");
        sb.append("War Chest: £").append(String.format("%.2f", warChest)).append("\n");
        sb.append("Defeated: ").append(isDefeated() ? "Yes" : "No").append("\n\n");
        
        sb.append("Squadron Ships:\n");
        String squadronInfo = getSquadron();
        sb.append(squadronInfo.equals("No ships commissioned") ? "No ships" : squadronInfo).append("\n\n");
        
        sb.append("Reserve Fleet:\n").append(getReserveFleet()).append("\n\n");
        
        sb.append("Sunk Ships:\n").append(getSunkShips());
        
        return sb.toString();
    }
    
    
    /** returns true if War Chest <=0 and the admiral's squadron has no ships which 
     * can be retired. 
     * @returns true if War Chest <=0 and the admiral's fleet has no ships 
     * which can be retired. 
     */
    @Override
    public boolean isDefeated() {
        // Defeated if war chest is empty or negative AND no ships can be decommissioned
        if (warChest <= 0) {
            // Check if any ships can be decommissioned
            for (Ship ship : squadron) {
                // Only active or resting ships can be decommissioned
                if (ship.getState() == ShipState.ACTIVE || ship.getState() == ShipState.RESTING) {
                    return false; // Can decommission at least one ship
                }
            }
            return true; // No ships to decommission and no money
        }
        return false; // Still have money
    }
    
    /** returns the amount of money in the War Chest
     * @returns the amount of money in the War Chest
     */
    public double getWarChest()
    {
        return warChest;
    }
    
    
    /**Returns a String representation of all ships in the reserve fleet
     * @return a String representation of all ships in the reserve fleet
     **/
    @Override
    public String getReserveFleet()
    {   //assumes reserves is a Hashmap
       
        StringBuilder sb = new StringBuilder();
        for (Ship ship : allShips.values()) {
            if (ship.getState() == ShipState.RESERVE) {
                sb.append(ship).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No ships in reserve";
    }
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    @Override
    public String getSquadron() {
        if (squadron.isEmpty()) {
            return "No ships commissioned";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Ship ship : squadron) {
            sb.append(ship).append("\n");
        }
        return sb.toString();
    }
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    @Override
    public String getSunkShips() {
        StringBuilder sb = new StringBuilder();
        for (Ship ship : allShips.values()) {
            if (ship.getState() == ShipState.SUNK) {
                sb.append(ship).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No ships sunk yet";
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
     @Override
    public String getAllShips() {
        StringBuilder sb = new StringBuilder();
        for (Ship ship : allShips.values()) {
            sb.append(ship).append("\n");
        }
        return sb.toString();
    }
    
    
    /** Returns details of any ship with the given name
     * @return details of any ship with the given name
     **/
   @Override
    public String getShipDetails(String nme) {
        Ship ship = allShips.get(nme);
        return ship != null ? ship.toString() : "No such ship";
    }    
 
    // ***************** Fleet Ships ************************   
    /** Allows a ship to be commissioned to the admiral's squadron, if there 
     * is enough money in the War Chest for the commission fee.The ship's 
     * state is set to "active"
     * @param nme represents the name of the ship
     * @return "Ship commissioned" if ship is commissioned, "Not found" if 
     * ship not found, "Not available" if ship is not in the reserve fleet, "Not 
     * enough money" if not enough money in the warChest
     **/        
     @Override
    public String commissionShip(String nme) {
        Ship ship = allShips.get(nme);
        
        // Check if ship exists
        if (ship == null) {
            return "Not found";
        }
        
        // Check if ship is in reserve
        if (ship.getState() != ShipState.RESERVE) {
            return "Not available";
        }
        
        // Check if enough money in war chest
        int commissionFee = ship.getCommissionFee();
        if (warChest < commissionFee) {
            return "Not enough money";
        }
        
        // Commission the ship
        ship.setState(ShipState.ACTIVE);
        warChest -= commissionFee;
        squadron.add(ship);
        
        return "Ship commissioned";
    }
        
    /** Returns true if the ship with the name is in the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name is in the admiral's squadron, false otherwise.
     **/
     @Override
    public boolean isInSquadron(String nme) {
        for (Ship ship : squadron) {
            if (ship.getName().equals(nme)) {
                return true;
            }
        }
        return false;
    }
    
    /** Decommissions a ship from the squadron to the reserve fleet (if they are in the squadron)
     * pre-condition: isInSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    @Override
    public boolean decommissionShip(String nme) {
        Ship ship = getShipByName(nme);
        
        // Check if ship exists and is in squadron
        if (ship == null || !isInSquadron(nme)) {
            return false;
        }
        
        // Check if ship can be decommissioned (not sunk)
        if (ship.getState() == ShipState.SUNK) {
            return false;
        }
        
        // Decommission the ship
        ship.setState(ShipState.RESERVE);
        warChest += ship.getCommissionFee() / 2.0; // Return half the commission fee
        squadron.remove(ship);
        
        return true;
    }
    
  
    /**Restores a ship to the squadron by setting their state to ACTIVE 
     * @param ref the name of the ship to be restored
     */
    @Override
    public void restoreShip(String nme) {
        Ship ship = getShipByName(nme);
        
        if (ship != null && isInSquadron(nme) && ship.getState() == ShipState.RESTING) {
            ship.setState(ShipState.ACTIVE);
        }
    }
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the reference number of the encounter
     * @returns true if the reference number represents a encounter, else false
     **/
    @Override
    public boolean isEncounter(int num) {
        return encounters.containsKey(num);
    }
     
     
/** Retrieves the encounter represented by the encounter 
      * number.Finds a ship from the fleet which can fight the 
      * encounter.The results of fighting an encounter will be 
      * one of the following: 
      * 0-Encounter won by...(ship reference and name)-add prize money to War 
      * Chest and set ship's state to RESTING,  
      * 1-Encounter lost as no ship available - deduct prize from the War Chest,
      * 2-Encounter lost on battle skill and (ship name) sunk" - deduct prize 
      * from War Chest and set ship state to SUNK.
      * If an encounter is lost and admiral is completely defeated because there 
      * are no ships to decommission,add "You have been defeated " to message, 
      * -1 No such encounter
      * Ensure that the state of the war chest is also included in the return message.
      * @param encNo is the number of the encounter
      * @return a String showing the result of fighting the encounter
      */ 
   @Override
    public String fightEncounter(int encNo) {
        // Check if encounter exists
        if (!isEncounter(encNo)) {
            return "No such encounter";
        }
        
        Encounter encounter = encounters.get(encNo);
        
        // Find the first suitable ship in the squadron
        Ship selectedShip = null;
        for (Ship ship : squadron) {
            if (ship.getState() == ShipState.ACTIVE && ship.canFight(encounter.getType())) {
                selectedShip = ship;
                break;
            }
        }
        
        // No suitable ship available
        if (selectedShip == null) {
            // Deduct prize money
            warChest -= encounter.getPrizeMoney();
            
            // Check if defeated
            if (isDefeated()) {
                return String.format("Encounter lost as no suitable ship available. War chest: £%.2f. You have been defeated!", warChest);
            } else {
                return String.format("Encounter lost as no suitable ship available. War chest: £%.2f", warChest);
            }
        }
        // Compare battle skills
        if (selectedShip.getBattleSkill() >= encounter.getRequiredSkill()) {
            // Win
            warChest += encounter.getPrizeMoney();
            selectedShip.setState(ShipState.RESTING);
            return String.format("Encounter won by %s. War chest: £%.2f", selectedShip.getName(), warChest);
        } else {
            // Loss
            warChest -= encounter.getPrizeMoney();
            selectedShip.setState(ShipState.SUNK);
            squadron.remove(selectedShip);
            
            // Check if defeated
            if (isDefeated()) {
                return String.format("Encounter lost on battle skill and %s sunk. War chest: £%.2f. You have been defeated!", 
                        selectedShip.getName(), warChest);
            } else {
                return String.format("Encounter lost on battle skill and %s sunk. War chest: £%.2f", 
                        selectedShip.getName(), warChest);
            }
        }
    }
        

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
     @Override
    public String getEncounter(int num) {
        Encounter encounter = encounters.get(num);
        return encounter != null ? encounter.toString() : "No such encounter";
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
   @Override
    public String getAllEncounters() {
        if (encounters.isEmpty()) {
            return "No encounters";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Encounter encounter : encounters.values()) {
            sb.append(encounter).append("\n");
        }
        return sb.toString();
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
     private void setupShips()
     {
     // Man-O-Wars
        allShips.put("Victory", new ManOWar("Victory", "Alan Aikin", 3, 3, 30));
        allShips.put("Endeavour", new ManOWar("Endeavour", "Col Cannon", 4, 2, 20));
        allShips.put("Bellerophon", new ManOWar("Bellerophon", "Ed Evans", 8, 3, 50));
        
        // Frigates
        allShips.put("Sophie", new Frigate("Sophie", "Ben Baggins", 8, 16, true));
        allShips.put("Surprise", new Frigate("Surprise", "Fred Fox", 6, 10, false));
        allShips.put("Jupiter", new Frigate("Jupiter", "Gil Gamage", 7, 20, false));
        
        // Sloops
        allShips.put("Arrow", new Sloop("Arrow", "Dan Dare", 150, true));
        allShips.put("Paris", new Sloop("Paris", "Hal Henry", 200, true));
        allShips.put("Beast", new Sloop("Beast", "Ian Idle", 400, false));
        allShips.put("Athena", new Sloop("Athena", "John Jones", 100, true));
        
     }
     
    private void setupEncounters()
    {
  
        encounters.put(1, new Encounter(1, EncounterType.BATTLE, "Trafalgar", 3, 300));
        encounters.put(2, new Encounter(2, EncounterType.SKIRMISH, "Belle Isle", 3, 120));
        encounters.put(3, new Encounter(3, EncounterType.BLOCKADE, "Brest", 3, 150));
        encounters.put(4, new Encounter(4, EncounterType.BATTLE, "St Malo", 9, 200));
        encounters.put(5, new Encounter(5, EncounterType.BLOCKADE, "Dieppe", 7, 90));
        encounters.put(6, new Encounter(6, EncounterType.SKIRMISH, "Jersey", 8, 45));
        encounters.put(7, new Encounter(7, EncounterType.BLOCKADE, "Nantes", 6, 130));
        encounters.put(8, new Encounter(8, EncounterType.BATTLE, "Finisterre", 4, 100));
        encounters.put(9, new Encounter(9, EncounterType.SKIRMISH, "Biscay", 5, 200));
        encounters.put(10, new Encounter(10, EncounterType.BATTLE, "Cadiz", 1, 250));
    }
    
    /**
     * Helper method to get a ship by name.
     * 
     * @param name the name of the ship
     * @return the Ship object or null if not found
     */
    private Ship getShipByName(String name) {
        return allShips.get(name);
    }
        
    // Useful private methods to "get" objects from collections/maps

    //*******************************************************************************
    //*******************************************************************************
  
    /************************ Task 3 ************************************************/

    
    //******************************** Task 3.5 **********************************
    /** reads data about encounters from a text file and stores in collection of 
     * encounters.Data in the file is editable
     * @param filename name of the file to be read
     */
    public void readEncounters(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        // Skip header line if it exists
        line = reader.readLine();
        
        while ((line = reader.readLine()) != null) {
            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }
            
            // Parse each line to create an encounter
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                int number = Integer.parseInt(parts[0].trim());
                String typeStr = parts[1].trim();
                String location = parts[2].trim();
                int requiredSkill = Integer.parseInt(parts[3].trim());
                int prizeMoney = Integer.parseInt(parts[4].trim());
                
                // Convert string type to EncounterType enum
                EncounterType type;
                if (typeStr.equalsIgnoreCase("Battle")) {
                    type = EncounterType.BATTLE;
                } else if (typeStr.equalsIgnoreCase("Blockade")) {
                    type = EncounterType.BLOCKADE;
                } else if (typeStr.equalsIgnoreCase("Skirmish")) {
                    type = EncounterType.SKIRMISH;
                } else {
                    type = EncounterType.INVALID;
                }
                
                // Create and add the encounter
                Encounter encounter = new Encounter(number, type, location, requiredSkill, prizeMoney);
                encounters.put(number, encounter);
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading encounters file: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("Error parsing numeric data: " + e.getMessage());
    }
}
                
                
    
    // ***************   file write/read  *********************
    /** Writes whole game to the specified file
     * @param fname name of file storing requests
     */
    public void saveGame(String fname)
    {   
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname))) {
        out.writeObject(this);
    } catch (IOException e) {
        System.err.println("Error saving game: " + e.getMessage());
    }
           
    }
    
    /** reads all information about the game from the specified file 
     * and returns 
     * @param fname name of file storing the game
     * @return the game (as an SeaBattles object)
     */
    public SeaBattles loadGame(String fname)
    {   
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fname))) {
        return (SeaBattles) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Error loading game: " + e.getMessage());
        return null;
    }
    } 
    
 
}



