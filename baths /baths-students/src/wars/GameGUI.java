package wars;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Provide a GUI interface for the game
 * 
 * @author A.A.Marczyk
 * @version 20/02/12
 */
public class GameGUI 
{
    private BATHS gp = new SeaBattles("Fred");
    private JFrame myFrame = new JFrame("Game GUI");
    private Container contentPane = myFrame.getContentPane();
    private JTextArea listing = new JTextArea();
    private JLabel codeLabel = new JLabel ();
    private JButton fightBtn = new JButton("Fight Encounter");
    private JButton viewBtn = new JButton("View State");
    private JButton clearBtn = new JButton("Clear");
    private JButton quitBtn = new JButton("Quit");
    private JPanel eastPanel = new JPanel();

    
    public GameGUI()
    {
        makeFrame();
        makeMenuBar(myFrame);
    }
    

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {    
        myFrame.setLayout(new BorderLayout());
        myFrame.add(listing,BorderLayout.CENTER);
        listing.setVisible(false);
        contentPane.add(eastPanel, BorderLayout.EAST);
        // set panel layout and add components
        eastPanel.setLayout(new GridLayout(4,1));

        // Add View State button
        eastPanel.add(viewBtn);
        viewBtn.addActionListener(new ViewStateHandler());
        
        // Add Fight button
        eastPanel.add(fightBtn);
        fightBtn.addActionListener(new FightHandler());
        
        eastPanel.add(clearBtn);
        clearBtn.addActionListener(new ClearHandler());
        
        eastPanel.add(quitBtn);
        quitBtn.addActionListener(new QuitHandler());

        clearBtn.setVisible(true);
        quitBtn.setVisible(true);
        
        // building is done - arrange the components and show        
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar(JFrame frame)
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        // create the File menu
        JMenu fileMenu = new JMenu("Ships");
        menubar.add(fileMenu);
        
        JMenuItem listShipItem = new JMenuItem("List reserve Ships");
        listShipItem.addActionListener(new ListFleetHandler());
        fileMenu.add(listShipItem);
        
        // Add list squadron menu item
        JMenuItem listSquadronItem = new JMenuItem("List Squadron");
        listSquadronItem.addActionListener(new ListSquadronHandler());
        fileMenu.add(listSquadronItem);
        
        // Add view ship menu item
        JMenuItem viewShipItem = new JMenuItem("View a Ship");
        viewShipItem.addActionListener(new ViewShipHandler());
        fileMenu.add(viewShipItem);
        
        // Add commission ship menu item
        JMenuItem commissionItem = new JMenuItem("Commission Ship");
        commissionItem.addActionListener(new CommissionHandler());
        fileMenu.add(commissionItem);
        
        JMenuItem decommission = new JMenuItem("De-ommission Ship");
        decommission.addActionListener(new DecommissionHandler());
        fileMenu.add(decommission);
    }
    
    private class ListFleetHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            listing.setVisible(true);
            String xx = gp.getReserveFleet();
            listing.setText(xx);
        }
    }
    
    // Add new handler for listing squadron
    private class ListSquadronHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            listing.setVisible(true);
            String squadronText = gp.getSquadron();
            listing.setText(squadronText);
        }
    }
    
    // Add new handler for viewing a ship
    private class ViewShipHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            String shipName = JOptionPane.showInputDialog(myFrame, "Enter ship name:");
            if (shipName != null && !shipName.isEmpty()) {
                String shipDetails = gp.getShipDetails(shipName);
                JOptionPane.showMessageDialog(myFrame, shipDetails, "Ship Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Add new handler for commissioning a ship
    private class CommissionHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            String shipName = JOptionPane.showInputDialog(myFrame, "Enter ship name to commission:");
            if (shipName != null && !shipName.isEmpty()) {
                String result = gp.commissionShip(shipName);
                JOptionPane.showMessageDialog(myFrame, result + 
                    (result.equals("Ship commissioned") ? "\nWar Chest: £" + gp.getWarChest() : ""), 
                    "Commission Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Add handler for View State button
    private class ViewStateHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            listing.setVisible(true);
            listing.setText(gp.toString());
        }
    }
    
    // Add handler for Fight button
    private class FightHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            String input = JOptionPane.showInputDialog(myFrame, "Enter encounter number:");
            if (input != null && !input.isEmpty()) {
                try {
                    int encounterNum = Integer.parseInt(input);
                    if (gp.isEncounter(encounterNum)) {
                        String result = gp.fightEncounter(encounterNum);
                        JOptionPane.showMessageDialog(myFrame, result, "Encounter Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(myFrame, "No such encounter", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(myFrame, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private class ClearHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            listing.setText("");
            listing.setVisible(false);            
        }
    }

    private class DecommissionHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            String result = "";
            String inputValue = JOptionPane.showInputDialog("Ship code ?: ");
            
            if(gp.isInSquadron(inputValue)) 
            {
                gp.decommissionShip(inputValue);
                result = inputValue + " is decommissioned";
            }
            else
            {
                result = inputValue + " not in fleet";
            }
            JOptionPane.showMessageDialog(myFrame,result);    
        }
    }
    
    // Add a quit handler
    private class QuitHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {            
            System.exit(0);
        }
    }
    
    public static void main(String[] args)
    {
        new GameGUI();
    }
}