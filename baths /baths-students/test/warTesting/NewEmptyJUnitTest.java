package warTesting;

import wars.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Extra Tests for BATHS implementation
 * Tests functionality not covered by existing test files
 * 
 * @author Team CS05
 */
public class NewEmptyJUnitTest {
    BATHS game;
    
    @Before
    public void setUp() {
        game = new SeaBattles("TestAdmiral");
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Tests getAllShips method
     * Expected: Should contain all ships in the game regardless of state
     */
    @Test
    public void testGetAllShips() {
        String result = game.getAllShips();
        // Test that ships from all states are included
        assertTrue("Should contain Victory", result.contains("Victory"));
        assertTrue("Should contain Sophie", result.contains("Sophie"));
        assertTrue("Should contain Arrow", result.contains("Arrow"));
    }
    
    /**
     * Tests behavior when trying to commission a ship with insufficient funds
     * Expected: Should return "Not enough money" and not commission the ship
     */
    @Test
    public void testInsufficientFundsCommission() {
        // Commission several expensive ships to deplete war chest
        game.commissionShip("Victory");
        game.commissionShip("Bellerophon");
        
        // Try to commission another expensive ship
        String result = game.commissionShip("Sophie");
        assertEquals("Not enough money", result);
        assertFalse("Ship should not be in squadron", game.isInSquadron("Sophie"));
    }
    
    /**
     * Tests restoring a ship that doesn't exist
     * Expected: Should have no effect
     */
    @Test
    public void testRestoreNonExistentShip() {
        game.restoreShip("NonExistentShip");
        // No exception should be thrown
        assertFalse("NonExistent ship should not be in squadron", 
                   game.isInSquadron("NonExistentShip"));
    }
    
    /**
     * Tests if sunk ships can be restored
     * Expected: Sunk ships cannot be restored
     */
    @Test
    public void testRestoreSunkShip() {
        game.commissionShip("Victory");
        // Victory has skill 3, encounter 4 requires 9, so it will be sunk
        game.fightEncounter(4);
        
        // Try to restore
        game.restoreShip("Victory");
        
        // Ship should still be sunk, not in squadron
        assertFalse("Sunk ship should not be in squadron after restore attempt", 
                   game.isInSquadron("Victory"));
    }
    
    /**
     * Tests fighting a sequence of encounters with the same ship
     * Expected: Ship should become resting after winning an encounter
     */
    @Test
    public void testShipRestingAfterFight() {
        game.commissionShip("Bellerophon"); // Has skill 8
        
        // Fight encounter 3 which requires skill 3
        game.fightEncounter(3);
        
        // Try to use the same ship again
        String result = game.fightEncounter(3);
        assertTrue("Should indicate no suitable ship available", 
                  result.contains("no suitable ship available"));
    }
}