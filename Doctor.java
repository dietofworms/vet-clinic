/**
 * Author: Steven Karmaniolos
 */

import java.util.*;

public class Doctor
{
    private String name;
    private String specialisation;

    // Default Constructor
    // Postcondition: Instance variables are initialised
    public Doctor()
    {
        name = "";
        specialisation = "";
    }

    // Second constructor method
    public Doctor(String docName, String docSpecial)
    {
        name = docName;
        specialisation = docSpecial;
    }

    public void setInfo(String docName, String docSpecial)
    {
        name = docName;
        specialisation = docSpecial;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setSpecialisation(String specialisation)
    {
        this.specialisation = specialisation;
    }

    public String getSpecialisation()
    {
        return specialisation;
    }
}
