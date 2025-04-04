package wars; 


/**
 * Details of your team
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Teamwork
{
    private String[] details = new String[12];
    
    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = "CWK1B CS05";
        
        details[1] = "Lamichhane";
        details[2] = "Arogya";
        details[3] = "22059596";

        details[4] = "Bahadur Shahi";
        details[5] = "Rajeev";
        details[6] = "22076926";

        details[7] = "Gurung";
        details[8] = "Sahil";
        details[9] = "22019747";


        details[10] = "Pulami";
        details[11] = "Sandesh";
        details[12] = "22100740";

	
	   // only if applicable
        details[13] = "surname of member5";
        details[14] = "first name of member5";
        details[15] = "SRN of member5";


    }
    
    public String[] getTeamDetails()
    {
        return details;
    }
    
    public void displayDetails()
    {
        for(String temp:details)
        {
            System.out.println(temp.toString());
        }
    }
}
        
