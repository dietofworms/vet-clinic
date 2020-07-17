/**
 * Author: Steven Karmaniolos
 */

import java.util.*;

public class Pet
{
    private String name;    // the name of the pet.
    private String type;    // the type of the pet. It can only be either: dog/cat.
    private String size;    // the size of the pet. It can only be either: small/medium/large.
    private double weight;  //the weight of the pet. Must be > 0.
    private int age;        //the age of the pet. Measured in months. Must be > 0.
    private String doctor;  // the doctor assigned to the pet - it can be unassigned.

    // Constructor method to initialise the pet's information as empty/blank.
    public Pet()
    {
        type = "";
        size = "";
        name = "";
        weight = 0;
        age = 0;
        doctor = "No Doctor Assigned";
    }

    // Second constructor method
    public Pet(String petType, String petSize, String petName, double petWeight, int petAge, String petDoctor)
    {
        type = petType;
        size = petSize;
        name = petName;
        weight = petWeight;
        age = petAge;
        doctor = petDoctor;
    }

    public void setInfo(String petType, String petSize, String petName, double petWeight, int petAge, String petDoctor)
    {
        type = petType;
        size = petSize;
        name = petName;
        weight = petWeight;
        age = petAge;
        doctor = petDoctor;
    }

    public void setName(String petName)
    {
        name = petName;
    }

    public String getName()
    {
        return name;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    // Must be either: Small, Medium, Large.
    public void setSize(String size)
    {
        this.size = size;
    }

    public String getSize()
    {
        return size;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    // Gets and returns the weight of the pet.
    public double getWeight()
    {
        return weight;
    }

    // stored as months
    public void setAge(int age)
    {
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    public void setDoctorName(String doctor)
    {
        this.doctor = doctor;
    }

    public String getDoctorName()
    {
        return doctor;
    }
}
