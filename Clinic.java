/**
 * Author: Stavros Karmaniolos
 */

import java.util.*;
import java.io.*;

public class Clinic
{
    private Doctor[] doctorList = new Doctor[3];
    private Pet[] petList = new Pet[3];
    private Scanner console = new Scanner(System.in);
    private int option, noOfDoctors = 0, noOfPets = 0;

    // Pre & Post condition comments are present for every method.
    // An extra comment is present for the methods found in the main menu.
    // The last method found in this file is used to minimise system.out.print repetition.

    // Run Method
    // This method controls the flow of the program
    private void run()
    {
        do
        {
            // display main selection menu to the user, request which method to run
            System.out.println("========================================================");
            System.out.println("|                 **  MAIN MENU  **                    |");
            System.out.println("|------------------------------------------------------|");
            System.out.println("|    Vet Clinic                         [ Logged In ]  |");
            System.out.println("========================================================");
            System.out.println("| Options:                                             |");
            System.out.println("|                                                      |");
            System.out.println("|    +--------------------------------------------+    |");
            System.out.println("|    | 1. Add Doctor        3. View Doctors (...) |    |");
            System.out.println("|    | 2. Delete Doctor     4. Assign Doctor▶Pet  |    |");
            System.out.println("|    +--------------------------------------------+    |");
            System.out.println("|    | 5. Add Pet           8. Edit Pet           |    |");
            System.out.println("|    | 6. Delete Pet        9. Analyse Pet Weight |    |");
            System.out.println("|    | 7. View Pets (...)                         |    |");
            System.out.println("|    +--------------------------------------------+    |");
            System.out.println("|    | 10. Read: File       11. Write: File       |    |");
            System.out.println("|    +--------------------------------------------+    |");
            System.out.println("|                                                      |");
            System.out.println("|      0. Quit                                         |");
            System.out.println("|                                                      |");
            System.out.println("========================================================");
            option = console.nextInt(); console.nextLine(); // Clear new line in buffer
            switch (option)
            {
                case 1: addDoctor();            break;
                case 2: deleteDoctor();         break;
                case 3: printDoctorDetails();   break;
                case 4: assignDoctor();         break;
                case 5: addPet();               break;
                case 6: deletePet();            break;
                case 7: printPetDetails();      break;
                case 8: editPet();              break;
                case 9: analysePet();           break;
                case 10: readFile();            break;
                case 11: writeFile();           break;

                case 0: System.out.println(feedbackString(100));  break;
                default: System.out.println("Invalid selection. Try again.");
            }
        }
        while (option!=0);
    }

    // Main method of the program
    public static void main(String[] args)
    {
        Clinic clinic = new Clinic();
        clinic.run();
    }



    // ************************************ ############### *********************************************** //
    //////////////////////////////////////// DOCTOR METHODS //////////////////////////////////////////////////
    // ************************************ ############### *********************************************** //

    // Menu item 1: Add Doctor
    /**
     * Precondition: Array, doctorList must have been initiated.
     * Postcondition: The data entered by the user on the CLI is stored as variables
     * within the Doctor object in doctorList array. Variable noOfDoctors is incrementally increased by 1.
     */
    private void addDoctor()
    {
        String name, spec;
        noOfDoctors++;

        if (noOfDoctors == doctorList.length)
        {
            resizeDoctorArray();
        }

        for (int i = 0; i < noOfDoctors; i++)
        {
            if (noOfDoctors < doctorList.length && doctorList[i] == null)
            {
                doctorList[i] = new Doctor();
                name = nameEntry(0);
                spec = petTypeEntry(0);
                doctorList[i].setInfo(name, spec);
                System.out.println(feedbackString(21)); // success message to user
            }
        }
    }


    /**
     * Precondition: Array doctorList exists and has been initialised.
     * Postcondition: Array elements have been copied from doctorList to temporary array within the method and then
     * copied back to doctorList but physical size of the doctorList array has increased by one.
     */
    private void resizeDoctorArray()
    {
        Doctor[] tempList;
        tempList = new Doctor[doctorList.length + 1];

        for(int i = 0; i < doctorList.length; i++)
        {
            if (doctorList[i] != null)
            {
                tempList[i] = doctorList[i];
            }
        }
        doctorList = tempList;
    }

    // Menu item 2: Delete Doctor
    /**
     * Precondition: There is more than one Doctor entered in the system.
     * Postcondition: Doctor entered by user is removed from doctorList and array elements have moved to reduce logical size.
     * All pets assigned to the doctor have been reverted to their default value for doctor assigned.
     */
    private void deleteDoctor()
    {
        if (noOfDoctors > 0)
        {
            System.out.println(feedbackString(3)); // Please enter name
            String name = console.nextLine();

            if (isDuplicate(name) == false)
            {
                System.out.println(feedbackString(14)); // Error; no doctors in the system
            }
            else
            {
                for (int i = 0; i < noOfDoctors; i++)
                {
                    if (name.equalsIgnoreCase(doctorList[i].getName()))
                    {
                        for (int j = i; i < noOfDoctors; i++)
                        {
                            resetDoctorAssignment(doctorList[i]);
                            doctorList[i] = doctorList[i+1];
                        }
                        noOfDoctors--;
                        System.out.println(feedbackString(22)); // Successfully removed from the system
                    }
                }
            }
        }
        else
        {
            System.out.println(feedbackString(16)); // Error; no doctors in the system
        }
    }


    /**
     * Precondition: Instance variable (name) in method deleteDoctor equals an existing index in array doctorList
     * Postcondition: Pets assigned to the doctor are set to the default value for variable Doctor (assigned to)
     */
    private void resetDoctorAssignment(Doctor doctor)
    {
        for (int i = 0; i < noOfPets; i++)
        {
            if (petList[i] != null && petList[i].getDoctorName().equalsIgnoreCase(doctor.getName()))
            {
                petList[i].setDoctorName("No Doctor Assigned");
            }
        }
    }

    // Menu item 3: View Doctors
    /**
     * Precondition: At least one object Doctor must be entered in the system.
     * Postcondition: Information stored in the array doctorList for each Doctor object is printed on the screen.
     */
    private void printDoctorDetails()
    {
        if (noOfDoctors > 0)
        {
            System.out.println("========================================================");
            System.out.println("|    Would you like to view in alphabetical order?     |");
            System.out.println("|              1. Yes          2. No                   |");
            System.out.println("========================================================");
            int selection = console.nextInt(); console.nextLine(); // to clear new line from buffer

            switch (selection)
            {
                case 1: printDoctorsAlpha(doctorList); break;
                case 2: printDoctorsUnsorted(); break;
                default: System.out.println("Invalid selection. Try again."); break;
            }
        }
        else
        {
            System.out.println(feedbackString(16));
        }
    }


    /**
     * Precondition: Method is called from the printDoctorDetails method.
     * Postcondition: Doctors are printed to the screen unsorted.
     */
    private void printDoctorsUnsorted()
    {
        System.out.println(feedbackString(1));

        for(int i = 0; i < noOfDoctors; i++)
        {
            if (doctorList[i] != null)
            {
                int j = i + 1; // Numbered list look visually pleasant
                System.out.println(j + ". Name: " + doctorList[i].getName());
                System.out.println("   Specialisation: " + doctorList[i].getSpecialisation() + "\n");
            }
        }
    }


    /**
     * Precondition: Method is called from within the printDoctorDetails method.
     * Postcondition: Elements of the array doctorList are sorted alphabetically and displayed on screen.
     */
    private void printDoctorsAlpha(Doctor[] doctorList)
    {
        for (int i = 0; i < noOfDoctors; i++)
        {
            int minIndex = i;

            for (int j = i + 1; j < noOfDoctors; j++)
            {
                if (doctorList[j].getName().compareTo(doctorList[minIndex].getName()) < 0)
                {
                    minIndex = j;
                }
            }
            Doctor temp = doctorList[i];
            doctorList[i] = doctorList[minIndex];
            doctorList[minIndex] = temp;
        }
        printDoctorsUnsorted();
    }


    /**
     * Precondition: A Doctor object exists in system
     * Postcondition: Return true/false if data stored in the variable (name) of the receiving Doctor object
     * equals criteria in the if statement.
     * */
    private boolean isDuplicate(String name)
    {
        boolean isDuplicate = false;

        for (int i = 0; i < doctorList.length; i++)
        {
            if ((doctorList[i] != null && (name.equalsIgnoreCase(doctorList[i].getName()))))
            {
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }


    /**
     * Precondition: The instance variable of the calling object has no value or is blank ("").
     * Postcondition: String entered by the user is returned to be stored as variable (name).
     * It cannot match the name of another doctor object or pet object depending on the option input.
     */
    private String nameEntry(int option) // Either 0 or 1 to switch between user prompt (doc or pet type message)
    {
        String name = "";
        System.out.println(feedbackString(3)); // Please enter name
        name = console.nextLine().trim();
        while (name.isEmpty())
        {
            System.out.println(feedbackString(13)); // Name already exists in the system
            name = console.nextLine().trim();
        }
        if (option == 0) // Doctor duplicate list check
        {
            while (isDuplicate(name) == true)
            {
                System.out.println(feedbackString(11)); // Name already exists in the system
                name = console.nextLine().trim();
            }
        }
        else if (option == 1) // Pet duplicate list check
        {
            while (isPetDuplicate(name) == true)
            {
                System.out.println(feedbackString(11)); // Name already exists in the system
                name = console.nextLine().trim();
            }
        }
        return name;
    }


    /**
     * Precondition: The instance variable of the calling object has no value or is blank ("").
     * Postcondition: String entered by the user (must be "dog" or "cat")
     * is returned to be stored as a variable (specialisation) in the addDoctor method ... or
     * is returned to be stored as a variable (type) in the addPet method.
     */
    private String petTypeEntry(int option) // Either 0 or 1 to switch between user prompt (doc specialty or pet type message)
    {
        String type = "";

        while (!(type.equalsIgnoreCase("dog") || type.equalsIgnoreCase("cat")))
        {   // Switching between doctor or pet prompt message
            if (option == 0)
            {
                System.out.println(feedbackString(4)); // Please enter doctor specialty
            }
            else if (option == 1)
            {
                System.out.println(feedbackString(5)); // Please enter pet type
            }
            type = console.nextLine();
            // ignoreCase is used to allow for variations of captilisation of the input
            if (!(type.equalsIgnoreCase("dog") || type.equalsIgnoreCase("cat")))
            {
                System.out.println(feedbackString(10));
            }
        }
        return type;
    }



    //-||||||||||||||||||||||||||||||||||||   PET METHODS   |||||||||||||||||||||||||||||||||||||||||||||||-//
    // Menu item 4: Add Pet
    /**
     * Precondition: Array petList has been initiated
     * Postcondition: The data entered by the user on the CLI is stored as variables
     * within the Pet object in petList array. Variable noOfPets is incrementally increased by 1.
     */
    private void addPet()
    {
        String name, type, size;
        String doctor = "No Doctor Assigned";
        int age;
        double weight;
        noOfPets++;
        if (noOfPets == petList.length)
        {
            resizePetArray();
        }
        for (int i = 0; i < noOfPets; i++)
        {
            if (noOfPets < petList.length && petList[i] == null)
            {
                petList[i] = new Pet();
                name = nameEntry(1);
                type = petTypeEntry(1);
                size = sizeEntry();
                age = ageEntry();
                weight = weightEntry();
                petList[i].setInfo(type, size, name, weight, age, doctor);
                System.out.println(feedbackString(21)); // success message to user
            }
        }
    }

    // Menu item 5: Delete Pet
    /**
     * Precondition: A Pet object exists and noOfPets is greater than zero.
     * Postcondition: The String entered by the user matches variable (name) of a Pet object in the petList array and is removed
     * from petList and array elements have moved to reduce logical size.
     */
    private void deletePet()
    {
        if (noOfPets > 0)
        {
            System.out.println(feedbackString(3)); // Please enter name
            String name = console.nextLine();
            if (isPetDuplicate(name) == false)
            {
                System.out.println(feedbackString(14)); // Error; no pet in the system
            }
            else
            {
                for (int i = 0; i < noOfPets; i++)
                {
                    if (name.equalsIgnoreCase(petList[i].getName()))
                    {
                        for (int j = i; j < noOfPets; j++)
                        {
                            petList[j] = petList[j+1];
                        }
                        noOfPets--;
                        System.out.println(feedbackString(22)); // Successfully removed from the system
                    }
                }
            }
        }
        else
        {
            System.out.println(feedbackString(17)); // Pet doesn't exist in the system
        }
    }

    // Menu item 6: View Pets
    /**
     * Precondition: A pet object exists in the system.
     * Postcondition: Depending on the user selection, the desired pet details have been displayed on the screen.
     * Can be either all pets in the system or pets assigned to specific doctor.
     */
    private void printPetDetails()
    {
        if (noOfPets > 0)
        {
            System.out.println("========================================================");
            System.out.println("|          Please select an option from below:         |");
            System.out.println("| Options:                                             |");
            System.out.println("|        1. View all pets                              |");
            System.out.println("|        2. View all pets alphabetically               |");
            System.out.println("|        3. View pets assigned to a specific doctor    |");
            System.out.println("========================================================");
            int selection = console.nextInt(); console.nextLine(); // to clear new line from buffer

            switch (selection)
            {
                case 1: printAllPets(); break;
                case 2: printPetsAlpha(petList); break;
                case 3: printPetsByDoctor(); break;
                default: System.out.println("Invalid selection. Try again."); break;
            }
        }
        else
        {
            System.out.println(feedbackString(17));
        }
    }


    /**
     * Precondition: User has selected option from switch statement within method printPetDetails.
     * Postcondition: Details of pets within system are printed onto the screen if element in the array does not equal null.
     */
    private void printAllPets()
    {
        if (noOfPets > 0)
        {
            System.out.println(feedbackString(2));
            for(int i = 0; i < noOfPets; i++)
            {
                if (petList[i] != null)
                {
                    int j = i + 1; // Numbered list look visually pleasant
                    System.out.println(j + ". Name: " + petList[i].getName());
                    System.out.println("   Type: " + petList[i].getType());
                    System.out.println("   Size: " + petList[i].getSize());
                    System.out.println("   Weight: " + petList[i].getWeight() + "kg");
                    System.out.println("   Age: " + petList[i].getAge() + " months or approximately "
                                                  + (petList[i].getAge() / 12) + " years");
                    System.out.println("   Doctor: " + petList[i].getDoctorName() + "\n");
                }
            }
        }
        else
        {
            System.out.println(feedbackString(17)); // Error; no pets in system
        }
    }


    private void printPetsAlpha(Pet[] petList)
    {
        for (int i = 0; i < noOfPets; i++)
        {
            int minIndex = i;

            for (int j = i + 1; j < noOfPets; j++)
            {
                if (petList[i] != null && petList[j].getName().compareTo(petList[minIndex].getName()) < 0)
                {
                    minIndex = j;
                }
            }

            Pet temp = petList[i];
            petList[i] = petList[minIndex];
            petList[minIndex] = temp;
        }

        printAllPets();
    }


    /**
     * Precondition: User has selected option from switch statement within method printPetDetails.
     * Postcondition: Details of pets assigned to doctor are printed to the screen if element in the array doesn't equal null.
     */
    private void printPetsByDoctor()
    {
        if (noOfDoctors > 0)
        {
            System.out.println("========================================================");
            System.out.println("|             Please enter the doctor's name           |");
            System.out.println("========================================================");
            String docName = console.nextLine();
            if (isDuplicate(docName))
                for (int i = 0; i < noOfPets; i++)
                {
                    if (petList[i] != null && docName.equalsIgnoreCase(petList[i].getDoctorName()))
                    {
                        System.out.println("* Name: " + petList[i].getName());
                        System.out.println("  Type: " + petList[i].getType());
                        System.out.println("  Size: " + petList[i].getSize());
                        System.out.println("  Weight: " + petList[i].getWeight() + "kg");
                        System.out.println("  Age: " + petList[i].getAge() + " months or approximately "
                                                      + (petList[i].getAge() / 12) + " years");
                    }
                }
            else
            {
                System.out.println(feedbackString(14)); // Error; name doesn't exist in system
            }
        }
        else
        {
            System.out.println(feedbackString(16)); // Error; no doctors in system
        }
    }

    // Menu item 7: Edit Pet
    /**
     * Precondition: An object Pet exists within the system.
     * Postcondition: Depending on user selection, details of the Pet object have been edited.
     */
    private void editPet()
    {
        String name;
        if (noOfPets > 0)
        {
            System.out.println("========================================================");
            System.out.println("|   Enter the name of the pet you would like to edit:  |");
            System.out.println("========================================================");
            name = console.nextLine();

            if (isPetDuplicate(name))
                for (int i = 0; i < noOfPets; i++)
                {
                    if (petList[i] != null && name.equalsIgnoreCase(petList[i].getName()))
                    {
                        int j = i + 1; // Numbered list look visually pleasant
                        System.out.println(j + ". Name: " + petList[i].getName());
                        System.out.println("   Type: " + petList[i].getType());
                        System.out.println("   Size: " + petList[i].getSize());
                        System.out.println("   Weight: " + petList[i].getWeight() + "kg");
                        System.out.println("   Age: " + petList[i].getAge() + " months or approximately "
                                                      + (petList[i].getAge() / 12) + " years");
                        System.out.println("   Doctor: " + petList[i].getDoctorName() + "\n");
                        System.out.println("========================================================");
                        System.out.println("|   What field of information would you like to edit?  |");
                        System.out.println("|    +--------------------------------------------+    |");
                        System.out.println("|    |         1. Type        4. Age              |    |");
                        System.out.println("|    |         2. Size        5. Doctor Assigned  |    |");
                        System.out.println("|    |         3. Weight                          |    |");
                        System.out.println("|    +--------------------------------------------+    |");
                        System.out.println("|    0. Back to main menu                              |");
                        System.out.println("========================================================");

                        int selection = console.nextInt(); console.nextLine();

                        switch (selection)
                        {
                            case 1: petList[i].setType(petTypeEntry(1));    break;
                            case 2: petList[i].setSize(sizeEntry());        break;
                            case 3: petList[i].setWeight(weightEntry());    break;
                            case 4: petList[i].setAge(ageEntry());          break;
                            case 5: assignDoctor(); break;
                            case 0: break;
                        }
                    }
                }
            else
            {
                System.out.println(feedbackString(14)); // Error; name doesn't exist in system
            }
        }
        else
        {
            System.out.println(feedbackString(17)); // Error; no pets in system
        }
    }


    /**
     * Precondition: Array petList exists and has been initialised.
     * Postcondition: Array elements have been copied from petList to temporary array within the method and then copied back
     * to petList but physical size of the petList array has increased by one.
     */
    private void resizePetArray()
    {
        Pet[] tempList;
        tempList = new Pet[petList.length + 1];

        for(int i = 0; i < petList.length; i++)
        {
            if (petList[i] != null)
            {
                tempList[i] = petList[i];
            }
        }
        petList = tempList;
    }


    /**
     * Precondition: A Pet object exists (does not equal null)
     * Postcondition: Return true/false if data stored in the variable (name) of the receiving Pet object
     * equals criteria in the if statement.
     */
    private boolean isPetDuplicate(String name)
    {
        boolean isDuplicate = false;
        for (int i = 0; i < petList.length; i++)
        {
            if ((petList[i] != null && (name.equalsIgnoreCase(petList[i].getName()))))
            {
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }


    /**
     * Precondition: A Pet object exists (does not equal null)
     * Postcondition: The integer variable (age) entered is above zero and is returned to be stored as a variable (age)
     * of the receiving Pet object.
     */
    private int ageEntry()
    {
        int age = 0;
        while (age <= 0)
        {
            System.out.println("========================================================");
            System.out.println("|             Please enter the age in months           |");
            System.out.println("========================================================");
            age = console.nextInt();
            if (age <= 0)
            {
                System.out.println(feedbackString(12)); // Error; enter number above zero.
            }
        }
        return age;
    }


    /**
     * Precondition: A Pet object exists (does not equal null)
     * Postcondition: The double variable (weight) is above zero and is returned to be stored as a variable (weight)
     * of the receiving Pet object.
     */
    private double weightEntry()
    {
        double weight = 0;
        while (weight <= 0)
        {
            System.out.println("========================================================");
            System.out.println("|           Please enter the weight in kilograms       |");
            System.out.println("========================================================");
            weight = console.nextDouble();
            if (weight <= 0)
            {
                System.out.println(feedbackString(12)); // Error; enter number above zero.
            }
        }
        return weight;
    }


    /**
     * Precondition: A Pet object exists (does not equal null).
     * Postcondition: The String variable (size) satisfies the criteria in the if statement (small/medium/large)
     * and is returned to be stored as a variable of the receiving Pet object.
     */
    private String sizeEntry()
    {
        String size = "";
        boolean isValid = false;

        while (isValid == false)
        {
            System.out.println("========================================================");
            System.out.println("|            Please enter the animal's size            |");
            System.out.println("|          Enter either: Small / Medium / Large        |");
            System.out.println("========================================================");
            size = console.nextLine();

            if ((size.equalsIgnoreCase("small")) || (size.equalsIgnoreCase("medium")) || (size.equalsIgnoreCase("large")))
            {
                isValid = true;
            }
            else
            {
                System.out.println("========================================================");
                System.out.println("|                        Error!                        |");
                System.out.println("========================================================");
            }
        }
        return size;
    }

    // Menu item 8: Analyse Pet
    /**
     * Precondition: A Pet object exists (does not equal null).
     * Postcondition: Pet information is displayed on screen along with information on if the pet is/isn't overweight.
     */
    private void analysePet()
    {
        String name;

        if (noOfPets > 0)
        {
            System.out.println(feedbackString(3)); // Prompt; enter name
            name = console.nextLine();
            if (isPetDuplicate(name)) // Checking if the pet exists in the system
                for (int i = 0; i < petList.length; i++)
                {
                    if ((petList[i] != null) && (name.equalsIgnoreCase(petList[i].getName())))
                    {
                        System.out.println(feedbackString(2)); // Header; Pet details
                        System.out.println(petList[i].getName());
                        System.out.println("Pet overweight status: " + isOverweight(petList[i]));
                    }
                }
            else
            {
                System.out.println(feedbackString(14)); // Error; Couldn't find that name
            }
        }
        else
        {
            System.out.println(feedbackString(17)); // Error; No pets in system
        }
    }


    /**
     * Precondition: Pet object exists (does not equal null) and method is called within analysePet method.
     * Postcondition: Returns true/false if data stored in variables (weight, size) of the receiving
     * Pet object satisfy the criteria in if statement to determine whether pet is overweight.
     */
    private boolean isOverweight(Pet pet)
    {
        boolean isOverweight = false;
        if (((pet.getType().equalsIgnoreCase("cat") && pet.getSize().equalsIgnoreCase("small") && pet.getWeight() > 4)
            || (pet.getType().equalsIgnoreCase("cat") && pet.getSize().equalsIgnoreCase("medium") && pet.getWeight() > 6)
            || (pet.getType().equalsIgnoreCase("cat") && pet.getSize().equalsIgnoreCase("large") && pet.getWeight() > 8))
            || (pet.getType().equalsIgnoreCase("dog") && pet.getSize().equalsIgnoreCase("small") && pet.getWeight() > 6)
            || (pet.getType().equalsIgnoreCase("dog") && pet.getSize().equalsIgnoreCase("medium") && pet.getWeight() > 9)
            || (pet.getType().equalsIgnoreCase("dog") && pet.getSize().equalsIgnoreCase("large") && pet.getWeight() > 12))
        {
            isOverweight = true;
        }
        return isOverweight;
    }

    // Menu item 9: Assign Doctor to Pet
    /**
     * Precondition: A Pet object exists exists and a Doctor object exists (does not equal null).
     * Postcondition: Parses Doctor and Pet object to method assignDocTo to be stored within Pet object.
     */
    private void assignDoctor()
    {
        String docInput, petInput;

        if (noOfDoctors > 0 && noOfPets > 0)
        {
            System.out.println("========================================================");
            System.out.println("|        Enter the name of the doctor you would        |");
            System.out.println("|                like to assign to a pet               |");
            System.out.println("========================================================");
            docInput = console.nextLine();
            if (isDuplicate(docInput))
            {
                for (int i = 0; i < noOfDoctors; i++)
                {
                    if ((doctorList[i] != null) && docInput.equalsIgnoreCase(doctorList[i].getName()))
                    {
                        System.out.println("========================================================");
                        System.out.println("|          Enter the name of the pet you would         |");
                        System.out.println("|             like to assign to this doctor            |");
                        System.out.println("========================================================");
                        petInput = console.nextLine();

                        if (isPetDuplicate(petInput)) // Checking if pet name exists in the system
                        {
                            for (int j = 0; j < noOfPets; j++)
                            {
                                if (petInput.equalsIgnoreCase(petList[j].getName())
                                    && (isSameType(petList[j], doctorList[i]) == true))
                                {
                                    assignDocTo(petList[j], doctorList[i]);
                                    break;
                                }
                                else if (petInput.equalsIgnoreCase(petList[j].getName())
                                    && (isSameType(petList[j], doctorList[i]) == false))
                                {
                                    System.out.println("========================================================");
                                    System.out.println("|   Sorry! That doctor cannot treat that type of pet   |");
                                    System.out.println("========================================================");
                                    break;
                                }
                            }
                        }
                        else
                        {
                            System.out.println(feedbackString(14)); // Error; Couldn't find that name
                        }
                    }
                }
            }
            else
            {
                System.out.println(feedbackString(14)); // Error; Couldn't find that name
            }
        }
        else
        {
            System.out.println("========================================================");
            System.out.println("|                       Error!                         |");
            System.out.println("|   There are no doctors and/or pets in the system     |");
            System.out.println("========================================================");
        }
    }


    /**
     * Precondition: A Pet object does not equal null and a Doctor object does not equal null
     * and have both been parsed from method assignDoctor.
     * Postcondition: Variable (doctor) is stored as a variable of the receiving Pet object.
     */
    private void assignDocTo(Pet pet, Doctor doctor)
    {
        if (isAssigned(pet) == false)
        {
            pet.setDoctorName(doctor.getName());
            System.out.println(feedbackString(21)); // Success; Info entered successfully
        }
        else
        {
            System.out.println("========================================================");
            System.out.println("|      This pet already has a doctor assigned to it.   |");
            System.out.println("|             Would you like to change it?             |");
            System.out.println("| Options:                                             |");
            System.out.println("|                Enter 1 for Yes                       |");
            System.out.println("|                   or 2 for No                        |");
            System.out.println("========================================================");
            int option = console.nextInt();

            switch (option)
            {
                case 1: System.out.println(feedbackString(21)); // Success; Info entered successfully
                        pet.setDoctorName(doctor.getName());  break;
                case 2: System.out.println("========================================================");
                        System.out.println("| The pet will remain assigned to the original doctor. |");
                        System.out.println("========================================================");
                        break;
                default: System.out.println("Invalid selection. Try again."); break;
            }
        }
    }


    /**
     * Precondition: Pet and Doctor does not equal null and have been parsed into method.
     * Postcondition: Return true/false if Pet type and Doctor specialty are equal/compatible.
     */
    private boolean isSameType(Pet pet, Doctor doctor)
    {
        boolean isSameType = false;
        if (pet.getType().equalsIgnoreCase(doctor.getSpecialisation()))
        {
            isSameType = true;
        }
        return isSameType;
    }


    /**
     * Precondition: Pet and Doctor does not equal null and have been parsed into method.
     * Postcondition: Return true/false if Pet variable (doctor) is default value.
     */
    private boolean isAssigned(Pet pet)
    {
        boolean isAssigned = false;
        if (!(pet.getDoctorName().equalsIgnoreCase("No Doctor Assigned")))
        {
            isAssigned = true;
        }
        return isAssigned;
    }


    // --------------------------------------------------- READ/WRITE ------------------------------------------------ //
    /**
     * Precondition: Method is called from Clinic
     * Postcondition: Data from file is loaded into system
     */
    private void readFile()
    {
        String fileName = "VetManagement.txt";
        Scanner inputStream = null;
        try
        {
            inputStream = new Scanner(new File(fileName));
            readPets();
            readDoctors();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening the file " + fileName);
            System.exit(0);
        }
        inputStream.close();
        System.out.println("(!) Information read successfully");
    }


    /**
     * Precondition: Method is called from within readFile.
     * Postcondition: Pet information from external file is entered into the system.
     */
    private void readPets() throws FileNotFoundException
    {
        String name = "", type = "", size = "", doctor = "", fileName = "VetManagement.txt";
        Scanner inputStream = null; inputStream = new Scanner(new File(fileName));
        double weight = 0;
        int age = 0, tempNoPets = 0;
        Pet[] tempList = new Pet[petList.length];
        String petLine = inputStream.nextLine();

        while (inputStream.hasNextLine() && (!petLine.equalsIgnoreCase("doctors")))
        {
            for (int i = 0; i < tempList.length && (!petLine.equalsIgnoreCase("doctors")); i++)
            {
                petLine = inputStream.nextLine();
                if (petLine.substring(0, 5).equals("type "))
                {
                    type = petLine.substring(5); petLine = inputStream.nextLine();
                }
                if (petLine.substring(0, 5).equals("size "))
                {
                    size = petLine.substring(5); petLine = inputStream.nextLine();
                }
                if (petLine.substring(0, 5).equals("name "))
                {
                    name = petLine.substring(5); petLine = inputStream.nextLine();
                }
                if (petLine.substring(0, 7).equals("weight "))
                {
                    String tempWeight = petLine.substring(7);
                    weight = Double.parseDouble(tempWeight); petLine = inputStream.nextLine();
                }
                if (petLine.substring(0, 4).equals("age "))
                {
                    String tempAge = petLine.substring(4);
                    age = Integer.parseInt(tempAge); petLine = inputStream.nextLine();
                }
                if (petLine.substring(0, 7).equals("doctor "))
                {
                    doctor = petLine.substring(7); petLine = inputStream.nextLine();
                }
                tempList[i] = new Pet(); tempNoPets++;
                tempList[i].setInfo(type, size, name, weight, age, doctor);
                if (tempNoPets == tempList.length) tempList = resizeTempArray(tempList);
            }

            for (int j = 0; j < tempList.length; j++)
            {
                if (tempList[j] != null)
                {
                    String tempName = tempList[j].getName();
                    if (isPetDuplicate(tempName) == true)
                    {
                        System.out.println("========================================================");
                        System.out.println("|   This name already exists, want to override? (Y/N)  |");
                        System.out.println("========================================================");
                        String answer = console.nextLine();
                        if (answer.equalsIgnoreCase("y"))
                        {
                            for (int k = 0; k < petList.length; k++)
                            {
                                if (petList[k] != null && petList[k].getName().equalsIgnoreCase(tempName))
                                {
                                    petList[k] = tempList[j];
                                }
                            }
                        }
                        else
                        {
                            System.out.println("========================================================");
                            System.out.println("|            The original file will remain             |");
                            System.out.println("========================================================");
                        }
                    }
                    else
                    {
                        for (int l = 0; l < petList.length; l++)
                        {
                            if (petList[l] == null)
                            {
                                petList[l] = new Pet(); noOfPets++;
                                petList[l] = tempList[j];
                                break;
                            }
                        }
                    }
                    if (noOfPets == petList.length)
                    {
                        resizePetArray();
                    }
                }
            }
        }
        inputStream.close();
    }


    /**
     * Precondition: Array petList exists and has been initialised.
     * Postcondition: Array elements have been copied from petList to temporary array within the method and then copied back
     * to petList but physical size of the petList array has increased by one.
     */
    private Pet[] resizeTempArray(Pet[] list)
    {
        Pet[] tempList;
        tempList = new Pet[list.length + 1];

        for(int i = 0; i < list.length; i++)
        {
            if (list[i] != null)
            {
                tempList[i] = list[i];
            }
        }
        list = tempList;
        return list;
    }


    /**
     * Precondition: Method is called from readFile method.
     * Postcondition: Doctor information from external file is read into the system.
     */
    private void readDoctors() throws FileNotFoundException
    {
        String name = "", spec = "", fileName = "VetManagement.txt";
        Scanner inputStream = null; inputStream = new Scanner(new File(fileName));
        double weight = 0;
        int age = 0, tempNoDoctors = 0;
        Doctor[] tempList = new Doctor[doctorList.length];
        String doctorLine = inputStream.nextLine();

        while (inputStream.hasNextLine() && (!doctorLine.equalsIgnoreCase("")))
        {
            while (!(doctorLine.equalsIgnoreCase("Doctors")))
            {
                doctorLine = inputStream.nextLine();
            }
            for (int i = 0; i < tempList.length && (!doctorLine.equalsIgnoreCase("")); i++)
            {
                doctorLine = inputStream.nextLine();
                if (doctorLine.equalsIgnoreCase("")) break;
                if (doctorLine.substring(0, 5).equals("name "))
                {
                    name = doctorLine.substring(5); doctorLine = inputStream.nextLine();
                }
                if (doctorLine.substring(0, 15).equals("specialisation "))
                {
                    spec = doctorLine.substring(15); //doctorLine = inputStream.nextLine();
                }
                tempList[i] = new Doctor(); tempNoDoctors++;
                tempList[i].setInfo(name, spec);
                if (tempNoDoctors == tempList.length) tempList = resizeTempDocArray(tempList);
            }

            for (int j = 0; j < tempList.length; j++)
            {
                if (tempList[j] != null)
                {
                    String tempName = tempList[j].getName();
                    if (isDuplicate(tempName) == true)
                    {
                        System.out.println("========================================================");
                        System.out.println("|   This name already exists, want to override? (Y/N)  |");
                        System.out.println("========================================================");
                        String answer = console.nextLine();
                        if (answer.equalsIgnoreCase("y"))
                        {
                            for (int k = 0; k < doctorList.length; k++)
                            {
                                if (doctorList[k] != null && doctorList[k].getName().equalsIgnoreCase(tempName))
                                {
                                    doctorList[k] = tempList[j];
                                }
                            }
                        }
                        else
                        {
                            System.out.println("========================================================");
                            System.out.println("|            The original file will remain             |");
                            System.out.println("========================================================");
                        }
                    }
                    else
                    {
                        for (int l = 0; l < doctorList.length; l++)
                        {
                            if (doctorList[l] == null)
                            {
                                doctorList[l] = new Doctor(); noOfDoctors++;
                                doctorList[l] = tempList[j];
                                break;
                            }
                        }
                    }
                    if (noOfDoctors == doctorList.length)
                    {
                        resizeDoctorArray();
                    }
                }
            }
        }
        inputStream.close();
    }


    /**
     * Precondition: Array petList exists and has been initialised.
     * Postcondition: Array elements have been copied from petList to temporary array within the method and then copied back
     * to petList but physical size of the petList array has increased by one.
     */
    private Doctor[] resizeTempDocArray(Doctor[] list)
    {
        Doctor[] tempList;
        tempList = new Doctor[list.length + 1];

        for(int i = 0; i < list.length; i++)
        {
            if (list[i] != null)
            {
                tempList[i] = list[i];
            }
        }
        list = tempList;
        return list;
    }


    /**
     * Precondition: Method is called from the main menu.
     * Postcondition: Information contained within the system is written to an external file.
     */
    public void writeFile()
    {
        String fileName = "VetManagement.txt";
        PrintWriter outputStream = null;
        try
        {
            System.out.println("========================================================");
            System.out.println("|      Would you like to overwrite existing data?      |");
            System.out.println("| Options:                                             |");
            System.out.println("|                Enter 1 for Yes                       |");
            System.out.println("|                   or 2 for No                        |");
            System.out.println("========================================================");
            int overwrite = console.nextInt();
            if (overwrite == 1)
            {
                outputStream = new PrintWriter (new FileOutputStream("VetManagement.txt", false));
            }
            else if (overwrite == 2)
            {
                outputStream = new PrintWriter (new FileOutputStream("VetManagement.txt", true));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening the file " + fileName + ".");
            System.exit(0);
        }
        outputStream.println("Pets");
        for (int i = 0; i < noOfPets; i++)
        {
            if (petList[i] != null)
            {
                outputStream.println("type "+petList[i].getType().toLowerCase()+"\n"+
                                     "size "+petList[i].getSize().toLowerCase()+"\n"+
                                     "name "+petList[i].getName().toLowerCase()+"\n"+
                                     "weight "+petList[i].getWeight()+"\n"+
                                     "age "+petList[i].getAge()+"\n"+
                                     "doctor "+petList[i].getDoctorName().toLowerCase());
            }
        }
        outputStream.println("Doctors");
        for (int i = 0; i < noOfDoctors; i++)
        {
            if (doctorList[i] != null)
            {
                outputStream.println("name "+doctorList[i].getName().toLowerCase()+"\n"+
                                     "specialisation "+doctorList[i].getSpecialisation().toLowerCase());
            }
        }
        outputStream.println("\n");
        outputStream.close();
        System.out.println("\nInformation written to " + fileName + "\n");
    }


    /**
     * Precondition: A file exists and the user has selected to overwrite it.
     * Postcondition: Information from the system overwrites an existing external file.
     */
    private void overwriteFile()
    {
        String fileName = "VetManagement.txt";
        PrintWriter outputStream = null;
        try
        {
            outputStream = new PrintWriter (new FileOutputStream("VetManagement.txt", false));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening the file " + fileName + ".");
            System.exit(0);
        }
    }

    // ------------------------------------------------------------------------------------------------------------------ //
    // Method used to provide text feeedback to the user to eliminate clutter and repetition.
    // Strings are implemented by this method if they are repeated more than twice or three times in some instances.
    /**
     * Precondition: None.
     * Postcondition: The data stored as cases in the methods switch statement is printed to the screen.
     */
    private String feedbackString(int option)
    {
        String message = ""; // Variable used for eventual output message to the user
        switch (option)
        {
            // HEADERS & PROMPTS -------------------------------------------------------------|
            case 1: message =   ("========================================================\n"
                                + "|                   Doctor Details                     |\n"
                                + "========================================================");
                                break;
            case 2: message =   ("========================================================\n"
                                + "|                    Pet Details                       |\n"
                                + "========================================================");
                                break;
            case 3: message =   ("========================================================\n"
                                + "|                Please enter the name:                |\n"
                                + "========================================================");
                                break;
            case 4: message =   ("========================================================\n"
                                + "|           What is the doctor's specialty?            |\n"
                                + "| Options:                                             |\n"
                                + "|                     Dog or Cat                       |\n"
                                + "========================================================");
                                break;
            case 5: message =   ("========================================================\n"
                                + "|               What is the pet type?                  |\n"
                                + "| Options:                                             |\n"
                                + "|                     Dog or Cat                       |\n"
                                + "========================================================");
                                break;
            // ERRORS ------------------------------------------------------------------------|
            case 10: message =   ("========================================================\n"
                                + "|                       Error!                         |\n"
                                + "|              Enter either Dog or Cat                 |\n"
                                + "========================================================");
                                break;
            case 11: message =   ("========================================================\n"
                                + "|                       Error!                         |\n"
                                + "|       That name already exists in the system.        |\n"
                                + "|                     Try again.                       |\n"
                                + "========================================================");
                                break;
            case 12: message =   ("========================================================\n"
                                + "|                       Error!                         |\n"
                                + "|              Enter a number above zero.              |\n"
                                + "========================================================");
                                break;
            case 13: message =   ("========================================================\n"
                                + "|                       Error!                         |\n"
                                + "|             Please enter a valid name.               |\n"
                                + "========================================================");
                                break;
            case 14: message =   ("========================================================\n"
                                + "|                       Sorry.                         |\n"
                                + "|        Couldn't find that name in the system.        |\n"
                                + "========================================================");
                                break;
            case 16: message =  ("========================================================\n"
                                + "|     Sorry but there are no doctors in the system.    |\n"
                                + "========================================================");
                                break;
            case 17: message =  ("========================================================\n"
                                + "|       Sorry but there are no pets in the system.     |\n"
                                + "========================================================");
                                break;
            // CONFIRMATION / THANK YOU -------------------------------------------------------|
            case 21: message =   ("========================================================\n"
                                + "|                     Thank you.                       |\n"
                                + "|    You have successfully entered the information.    |\n"
                                + "========================================================");
                                break;
            case 22: message =   ("========================================================\n"
                                + "|         Successfully removed from the system         |\n"
                                + "========================================================");
                                break;
            case 100: message =  ("                        _ _                            \n" // Just for fun :)
                                + "                       | | |                           \n"
                                + "   __ _  ___   ___   __| | |__  _   _  ___             \n"
                                + "  / _` |/ _ \\ / _ \\ / _` | '_ \\| | | |/ _ \\            \n"
                                + " | (_| | (_) | (_) | (_| | |_) | |_| |  __/            \n"
                                + "  \\__, |\\___/ \\___/ \\__,_|_.__/ \\__, |\\___|            \n"
                                + "   __/ |                         __/ |                 \n"
                                + "  |___/                         |___/                  \n"
                                + "         © Stavros Karmaniolos (c3160280)          ");

            /*case 0: break;
            default: System.out.println("Invalid selection. Try again."); // Delete this?? */
        }
        return message;
    }
}
