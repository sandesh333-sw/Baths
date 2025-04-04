/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wars;

/**
 * Represents a Man-O-War ship, which has decks and marines.
 * Can fight Blockade and Battle encounters, but not Skirmishes.
 */
public class ManOWar extends Ship {
    private int decks;
    private int marines;
    
    /**
     * Constructor for ManOWar.
     * 
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param battleSkill The battle skill rating (0-10)
     * @param decks Number of decks
     * @param marines Number of marines on board
     */
    public ManOWar(String name, String captain, int battleSkill, int decks, int marines){
        // Commission fee is 500 pounds, but only 300 if the ship has only 2 decks
        super(name, captain, (decks == 2) ? 300 : 500, battleSkill);
        this.decks = decks;
        this.marines = marines;
    }
    
    /**
     * Get the number of decks.
     * 
     * @return The number of decks
     */
    public int getDecks() {
        return decks;
    }
    
    /**
     * Get the number of marines.
     * 
     * @return The number of marines
     */
    public int getMarines() {
        return marines;
    }
    
    @Override
    public boolean canFight(EncounterType type) {
        // Man-O-Wars can fight Blockades and Battles but not Skirmishes
        return type == EncounterType.BLOCKADE || type == EncounterType.BATTLE;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" (Man-O-War, Decks: %d, Marines: %d)", decks, marines);
    }
}