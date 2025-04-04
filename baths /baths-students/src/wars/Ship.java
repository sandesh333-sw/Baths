/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

import java.io.Serializable;

/**
 * Abstract class representing a ship in the BATHS system.
 * Ships can be of different types (ManOWar, Frigate, Sloop) with different capabilities.
 */
public abstract class Ship implements Serializable {
    private String name;
    private String captain;
    private int commissionFee;
    private int battleSkill;
    private ShipState state;
    
    /**
     * Constructor for Ship.
     * 
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param commissionFee The commission fee in pounds
     * @param battleSkill The battle skill (0-10)
     */
    public Ship(String name, String captain, int commissionFee, int battleSkill){
        this.name = name;
        this.captain = captain;
        this.commissionFee = commissionFee;
        this.battleSkill = battleSkill;
        this.state = ShipState.RESERVE; // Default state for new ships
    }
    
    /**
     * Determines if this ship can fight a specific type of encounter.
     * Each ship type has different capabilities.
     * 
     * @param type The type of encounter
     * @return true if the ship can fight this encounter type, false otherwise
     */
    public abstract boolean canFight(EncounterType type);
    
    // Getters and Setters
    public String getName(){
        return name;
    }
    
    public String getCaptain() {
        return captain;
    }
    
    public int getCommissionFee() {
        return commissionFee;
    }
    
    public int getBattleSkill() {
        return battleSkill;
    }
    
    public ShipState getState(){
        return state;
    }
    
    public void setState(ShipState state){
        this.state = state;
    }
    
    @Override
    public String toString() {
        return String.format("%s - Captain: %s, Fee: %d, Skill: %d, State: %s",
                name, captain, commissionFee, battleSkill, state);
    }
}