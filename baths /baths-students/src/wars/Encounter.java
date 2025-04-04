package wars;

import java.io.Serializable;

/**
 * Represents an encounter that ships can fight.
 * Encounters have a type, location, required skill level, and prize money.
 */
public class Encounter implements Serializable {
    private int number;
    private EncounterType type;
    private String location;
    private int requiredSkill;
    private int prizeMoney;
    
    /**
     * Constructor for Encounter.
     * 
     * @param number The unique encounter number
     * @param type The type of encounter
     * @param location The location of the encounter
     * @param requiredSkill The skill level required to win
     * @param prizeMoney The prize money for winning
     */
    public Encounter(int number, EncounterType type, String location, int requiredSkill, int prizeMoney) {
        this.number = number;
        this.type = type;
        this.location = location;
        this.requiredSkill = requiredSkill;
        this.prizeMoney = prizeMoney;
    }
    
    /**
     * Get the encounter number.
     * 
     * @return The encounter number
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Get the encounter type.
     * 
     * @return The type of encounter
     */
    public EncounterType getType() {
        return type;
    }
    
    /**
     * Get the location of the encounter.
     * 
     * @return The location name
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * Get the required skill level.
     * 
     * @return The required skill level
     */
    public int getRequiredSkill() {
        return requiredSkill;
    }
    
    /**
     * Get the prize money.
     * 
     * @return The prize money
     */
    public int getPrizeMoney() {
        return prizeMoney;
    }

    @Override
    public String toString() {
        return String.format("Encounter %d: %s at %s (Skill: %d, Prize: %d)",
                number, type, location, requiredSkill, prizeMoney);
    }
}