/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 * Represents a Frigate ship, which has cannons and may have a pinnace.
 * Can fight any encounter, but needs a pinnace for Blockades.
 */
public class Frigate extends Ship {
    private int cannons;
    private boolean hasPinnace;
    
    /**
     * Constructor for Frigate.
     * 
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param battleSkill The battle skill rating (0-10)
     * @param cannons Number of cannons
     * @param hasPinnace Whether the ship has a pinnace
     */
    public Frigate(String name, String captain, int battleSkill, int cannons, boolean hasPinnace){
        // Commission fee is 10 pounds per cannon
        super(name, captain, cannons * 10, battleSkill);
        this.cannons = cannons;
        this.hasPinnace = hasPinnace;
    }
    
    /**
     * Get the number of cannons.
     * 
     * @return The number of cannons
     */
    public int getCannons() {
        return cannons;
    }
    
    /**
     * Check if the ship has a pinnace.
     * 
     * @return true if the ship has a pinnace, false otherwise
     */
    public boolean hasPinnace() {
        return hasPinnace;
    }
    
    @Override
    public boolean canFight(EncounterType type) {
        // Frigates can fight Battles and Skirmishes
        // They can also fight Blockades, but only if they have a pinnace
        if (type == EncounterType.BLOCKADE) {
            return hasPinnace;
        }
        return true; // Can fight both BATTLE and SKIRMISH
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" (Frigate, Cannons: %d, Has Pinnace: %s)", 
                                               cannons, hasPinnace ? "Yes" : "No");
    }
}