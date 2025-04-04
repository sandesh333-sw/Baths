/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 * Represents a Sloop ship, which may have a doctor.
 * Can fight Battle and Skirmish encounters, but not Blockades.
 * All Sloops have a battle skill of 5.
 */
public class Sloop extends Ship {
    private boolean hasDoctor;
    
    /**
     * Constructor for Sloop.
     * 
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param commissionFee The fee to commission the ship
     * @param hasDoctor Whether the ship has a doctor
     */
    public Sloop(String name, String captain, int commissionFee, boolean hasDoctor){
        super(name, captain, commissionFee, 5); // All Sloops have battle skill of 5
        this.hasDoctor = hasDoctor;
    }
    
    /**
     * Check if the ship has a doctor.
     * 
     * @return true if the ship has a doctor, false otherwise
     */
    public boolean hasDoctor() {
        return hasDoctor;
    }
    
    @Override
    public boolean canFight(EncounterType type) {
        // Sloops can fight Battles and Skirmishes but not Blockades
        return type == EncounterType.BATTLE || type == EncounterType.SKIRMISH;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" (Sloop, Has Doctor: %s)", hasDoctor ? "Yes" : "No");
    }
}