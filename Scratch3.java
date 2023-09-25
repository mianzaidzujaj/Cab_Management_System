import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
class Scratch3 {
    static boolean deleteBalance = false;
    static boolean deleteInfo = false;
    static Scanner input = new Scanner(System.in);
    static String userInputString;
    static double  rideType= 1;
    static String rideTypename = "Ac car";
    static int userInputInt;

    /** usernames and passwords file **/
    static File accounts = new File("accounts.txt");

    /** the temp file that data is coped to during editing **/
    static File tempFile = new File("temp.txt");
    static File tempFile2 = new File("temp2.txt");
    /** the file where account balance is stored **/
    static File balance = new File("balance.txt");
    /** traveling history stored in record file **/
    static File history = new File("record.txt");
    static String userNameFinal = "mohammed";
    static int  pricePerKM = 25;
    static int userBalance = 0;

    /** first page containing log in and sign up **/
    public static void main(String[] args) {
        if(firstPage())
            SecondPage();// second page containing traveling and adding amount and edit menu
        if(deleteInfo){
            // delete the previous file and replace it with the temp that has been edited
            deleteFile1(accounts,tempFile);
            deleteInfo = false;
        }
    }

                          /** first page **/
    public static boolean firstPage () {
        String userPassword;
        boolean admin = false;
        boolean passed = false;
        do {
            do{
                try {
                   System.out.println("Press: \n1 to sign in: \n2 to sign up: \n3 Forgot your password: \n4 to exit: ");
                   userInputInt = Integer.parseInt(inputString());
                   if(userInputInt > 4 || userInputInt <= 0)
                       System.out.println("Wrong input.");
                   else
                       break;
               } catch (Exception e) {
                   System.out.println("Wrong input");
               }
           }while (true);
           switch (userInputInt) {
               // sign in portion
               case 1->{
                   System.out.println("Enter your user Name:");
                   userInputString = inputString();
                   System.out.println("Enter your password:");
                   userPassword = inputString();
                   if (userInputString.equals("admin")){ //admin login check
                       admin = true;
                       System.out.println("Welcome admin");
                       adminPage();// method called
                   } else if(!signIn(userInputString, userPassword)||admin) // method called
                       System.out.println("Wrong username or password.");
                   else{
                       System.out.println("Logged in successfully.");
                   passed = true;
                   }
               }
               // sign up portion
               case 2->{
                   try {
                       System.out.println("Enter User Name:");
                       userInputString =  inputString();
                       if(userInputString.length() < 1){
                           System.out.println("Invalid name");
                           continue;
                       }

                       System.out.println("Enter your a password (8 characters with at least two numbers)");
                       userPassword =  inputString();
                       // method called
                       if (signUp(userInputString,userPassword)){
                           passed = true;
                       }
                   }
                   catch (Exception e){
                       System.out.println("Invalid input");
                   }
               }
               case 3->{
                   try {
                       System.out.println("Enter your user Name:");
                       userInputString = inputString();
                       System.out.println("Enter your phone number");
                       userPassword = inputString();
                       if (forgetPassword(userInputString,userPassword)){
                           System.out.println("Account is verified");
                           System.out.println("Now your password will become your phone number \n you can log in and change it");
                           editRecordOfAccount(userInputString,userPassword,accounts,tempFile,"password");
                       }
                       else {
                           System.out.println("user name or number are not correct");
                       }
                   }
                   catch (Exception e){
                       System.out.println("Invalid input");
                   }
               }

               default -> {
                   return false;
               }
           }
            if(deleteInfo){
                // delete the previous file and replace it with the temp that has been edited
                deleteFile1(accounts,tempFile);
                deleteInfo = false;
            }
       }while (!passed);
        return passed;
    }
    /** second page code **/
    public static void SecondPage(){
        // end the loop and exit the program
        boolean exit = false;
        while (!exit){
            getBalance(userNameFinal);//change the balance in each iteration
            //Names of locations
            String [] locations = {"E-11","F-11","G-11","FAST University","Saddar"};
            System.out.println("\t\t\t Welcome to Indrive");
            System.out.println("Hello "+ userNameFinal);
            System.out.println("Your balance is: "+userBalance);
            boolean secondPageTrue = true;
            do{
                try {
                    System.out.println("Type: \n1 to travel: \n2 to add amount in account: \n3 to change your info:\n4 to exit: ");
                    userInputInt = Integer.parseInt(inputString());
                    if(userInputInt > 4 || userInputInt <= 0)
                        System.out.println("Wrong input.");
                    else
                        secondPageTrue = false;
                } catch (Exception e) {
                        System.out.println("Invalid Input");
                }
            }while (secondPageTrue);
            switch (userInputInt) {
                case 1 -> {
                    do {
                        try {
                            System.out.println("Menu: \n1- For Bike \n2- For Ac Car \n3- For Mini car \n4- For normal car");
                            userInputInt = Integer.parseInt(inputString());
                            if (userInputInt > 4 || userInputInt <= 0)
                                System.out.println("Wrong input.");
                            else
                                break;
                        } catch (Exception e) {
                            System.out.println("Invalid Input");
                        }
                    } while (true);
                    switch (userInputInt) {
                        case 1 -> {
                            rideType = 0.5;
                            rideTypename = "bike";
                        }
                        case 2 -> {
                            rideType = 2;
                            rideTypename = "Ac car";
                        }
                        case 3 -> {
                            rideType = .8;
                            rideTypename = "Mini";
                        }
                        case 4 -> {
                            rideType = 1;
                            rideTypename = "Normal Car";
                        }
                        default -> {
                            rideType = 1;
                            rideTypename = "normal car";
                        }
                    }
                    // current place of customer
                    int currentLocation = 0;
                    do {
                        try {
                            System.out.println("Select your current location by entering the number beside it: ");
                            for (int i = 0; i < locations.length; i++) {
                                System.out.println((i + 1) + "- " + locations[i]);
                            }
                            currentLocation = Integer.parseInt(inputString()) - 1;
                            if (currentLocation + 1 > 5 || currentLocation + 1 <= 0)
                                System.out.println("Wrong input.");
                            else
                                break;
                        } catch (Exception e) {
                            System.out.println("Invalid input");
                        }
                    } while (true);
                    // destination of customer
                    int desiredLocation = 0;
                    do {
                        try {
                            System.out.println("Select your desired location by entering the number beside it: ");
                            for (int i = 0; i < locations.length; i++) {
                                if (i == currentLocation) {
                                    System.out.println((i + 1) + "- " + locations[i] + " <=== this is your current location");
                                } else {
                                    System.out.println((i + 1) + "- " + locations[i]);
                                }
                            }
                            desiredLocation = Integer.parseInt(inputString()) - 1;
                            if (desiredLocation + 1 > 5 || desiredLocation + 1 <= 0)
                                System.out.println("Wrong input.");
                            else if (desiredLocation == currentLocation)
                                System.out.println("You cannot travel to the same place.");
                            else
                                break;
                        } catch (Exception e) {
                            System.out.println("Invalid input");
                        }
                    } while (true);
                    randomDriverGenerator(currentLocation, desiredLocation, locations); // method called
                }
                case 2 -> {
                    long visaNumber = 0;
                    boolean validVisa = false;
                    do {
                        try {
                            System.out.print("Enter your visa number: ex( 374245455400126 )");
                            visaNumber = Long.parseLong(inputString());
                            if (checkVisa(visaNumber)) // checks if visa card is valid or not
                                validVisa = true;
                            else
                                System.out.println("Invalid card.");
                        } catch (Exception e) {
                            System.out.println("Wrong input.");
                        }
                    } while (!validVisa);
                    if(checkVisa(visaNumber)) {
                        String userName = userNameFinal;
                        int balance1 = 0;
                        boolean amountCheck = false;
                        do {
                            try {
                                System.out.println("Please enter the amount you want to add to your balance sir: ");
                                balance1 = Integer.parseInt(inputString());
                                amountCheck = true;
                            } catch (Exception e) {
                                System.out.println("Wrong input.");
                            }
                        } while (!amountCheck);
                        String balance2 = Integer.toString(balance1);
                        editBalance(userName, balance2, balance, tempFile2); // method called
                    } else {
                        System.out.println("Your visa is invalid.");
                    }
                }
                case 3 -> {
                    System.out.println("\nChange your info.");
                    System.out.print("Enter your user name: ");
                    userInputString = inputString();
                    System.out.print("Enter your old password: ");
                    String password = inputString();
                    if(checkForPassChange(userInputString,password,accounts,accounts,tempFile)){
                        System.out.print("");
                    }
                    else
                        System.out.println("Wrong name or password.");
                }
                default -> exit = true;
            }
            if(deleteBalance){
                //delete the previous file and replace it with the temp that has been edited
                deleteFile1(balance,tempFile2);
                deleteBalance =false;
            }
        }
    }

    /** admin page **/
    public static void adminPage(){
        do {
            do {
                try {
                    System.out.println("Enter: \n 1 to search for a record \n 2 to delete user account: \n 3 to change info: \n 4 to exit:");
                    userInputInt = Integer.parseInt(inputString());
                    if(userInputInt < 1 || userInputInt > 4)
                        System.out.println("Wrong input.");
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    userInputInt = 0;
                }
            } while (userInputInt < 1 || userInputInt > 4);
            switch (userInputInt) {
                case 1 -> {
                    System.out.println("Write the customer name you want to search for their rides history: \n or Enter \"exit\" to exit: ");
                    String toBeSearch = inputString();
                    // method called
                    if (CheckIfRecordExist(toBeSearch, history, "", "history")) {
                        showRidesHistory(toBeSearch); // method called
                    } else if (toBeSearch.equals("exit")) {
                        break;
                    } else {
                        System.out.println("This user name is not found");
                    }
                }
                case 2 -> {
                    try {
                        System.out.println("Enter user name to be deleted: ");
                        String username = inputString();
                        if(!username.equals("admin")) {
                            if (CheckIfRecordExist(username, accounts, "", "accounts")) {
                                deleteRecord(username, accounts);
                                System.out.println("Record deleted.");
                            } else {
                                System.out.println("This user is not found");
                            }
                        }
                        else
                            System.out.println("You cannot delete yourself.");
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                }
                case 3 ->{
                    try{
                        System.out.println("\nChange your info.");
                        System.out.print("Enter your old password: ");
                        String password = inputString();
                        if(checkForPassChange("admin",password,accounts,accounts,tempFile)){
                            System.out.print("");
                            if(deleteInfo){
                                // delete the previous file and replace it with the temp that has been edited
                                deleteFile1(accounts,tempFile);
                                deleteInfo = false;
                            }
                        }
                        else
                            System.out.println("Wrong name or password.");
                    }catch (Exception e){
                        System.out.println("Error.");
                    }
                }
                default -> System.exit(1);
            }
        } while (true);
    }

    /** Check if username is already in account file **/
    public static boolean CheckIfRecordExist(String userName, File fileToCheck, String phoneNumber, String Searchin) {
        boolean exist = false;
        // search record in account file
        if (Searchin.equals("accounts")){
            try {
                try (Scanner reader = new Scanner(fileToCheck)) {
                    String userName1;
                    String phoneNumber1;
                    String password;
                    String dummy;
                    while (reader.hasNext()) {
                        userName1 = reader.nextLine();
                        password = reader.nextLine();
                        phoneNumber1 = reader.nextLine();
                        dummy = reader.nextLine();
                        if (userName1.equals(userName) || phoneNumber1.equals(phoneNumber)) {
                            exist = true;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                input.nextLine();
            }
            return exist;
        }
        // search record in record file
        else if (Searchin.equals("history")){
            try {
                Scanner reader = new Scanner(fileToCheck);
                while (reader.hasNext()){
                    String userNameHere = reader.nextLine();
                    reader.nextLine();
                    reader.nextLine();
                    reader.nextLine();
                    reader.nextLine();
                    reader.nextLine();
                    reader.nextLine();
                    reader.nextLine();
                    reader.nextLine();
                    if(userNameHere.equals(userName)){
                       return exist = true;
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return exist;
    }

    /** displays the record of one customer using its name as argument **/
    public static void showRidesHistory (String username){
        do {
            try{
            Scanner reader = new Scanner(history);
            while (reader.hasNext()){
                String userNameHere = reader.nextLine();
                if(userNameHere.equals(username)){
                    System.out.println("-------------------------");
                    System.out.println("Time: "+reader.nextLine());
                    System.out.println("Ride type: "+reader.nextLine());
                    System.out.println("Driver Name: "+reader.nextLine());
                    System.out.println("Car plate: "+reader.nextLine());
                    System.out.println("Distance: "+reader.nextLine()+"Km");
                    System.out.println("Price: " + reader.nextLine()+"Rs");
                    System.out.println("From: "+reader.nextLine());
                    System.out.println("To: "+reader.nextLine());
                }
            }
            reader.close();
            break;
            }catch (FileNotFoundException e){
                System.out.println(e.getMessage());
                input.nextLine();
            }
        } while (true);
    }
    public static void deleteRecord (String userName,File fileToCheck){
        try {
            Scanner count = new Scanner(fileToCheck);
            int counter = -4;
            while (count.hasNextLine()){
                count.nextLine();
                counter++;
            }
            count.close();
            Scanner readerAccount = new Scanner(fileToCheck);
            Scanner readerBalance = new Scanner(balance);
            String []arrayAccount = new String[counter];
            String []arrayBalance = new String[counter / 2];
            String readbyScanner;

            //deal with deleting record form accounts file
            for (int i = 0; i < counter ; i++) {
                readbyScanner = readerAccount.nextLine();
                if(readbyScanner.equals(userName)){
                    readerAccount.nextLine();
                    readerAccount.nextLine();
                    readerAccount.nextLine();
                    arrayAccount[i] = readerAccount.nextLine();
                }
                else {
                    arrayAccount[i] = readbyScanner;
                }
            }
            readerAccount.close();

            PrintWriter writerAccounts = new PrintWriter(fileToCheck);
            for (String i:arrayAccount) {
                writerAccounts.println(i);
            }
            writerAccounts.close();

            //deal with deleting record from balance file
            for (int i = 0; i < counter / 2; i++) {
                readbyScanner = readerBalance.nextLine();
                if(readbyScanner.equals(userName)){
                    readerBalance.nextLine();
                    arrayBalance[i] = readerBalance.nextLine();
                }
                else {
                    arrayBalance[i] = readbyScanner;
                }
            }
            readerBalance.close();

            PrintWriter writerBalance = new PrintWriter(balance);
            for (String i:arrayBalance) {
                writerBalance.println(i);
            }
            writerBalance.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /** edit balance record **/
    public static void editBalance(String username, String change, File source , File target) {
        try {
            Scanner reader = new Scanner(source);
            PrintWriter writer = new PrintWriter(target);
            String userName1;
            String toChange;
            while (reader.hasNext()){
                userName1 = reader.nextLine();
                toChange = reader.nextLine();
                if (userName1.equals(username)){
                    writer.println(username);
                    long balance = Integer.parseInt(toChange) + Integer.parseInt(change);
                    writer.println(balance);
                } else {
                writer.println(userName1);
                writer.println(toChange);
                }
            }
            reader.close();
            writer.flush();
            writer.close();
            deleteBalance = true;
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            input.nextLine();
        }
    }

    /** check if the username and password are stored then edit the password **/
    public static boolean checkForPassChange(String userName, String password, File fileToCheck, File source , File target) {
        boolean exist = false;
        try {
            Scanner reader = new Scanner(fileToCheck);
            String userName1;
            String password1;
            while (reader.hasNext()) {
                userName1 = reader.nextLine();
                password1 = reader.nextLine();
                if (userName1.equals(userName) && password1.equals(password)) {
                    System.out.println("Verification successful.");
                    exist = true;
                    do {
                        try {
                            System.out.println("Enter: \n 1 to change password:  \n 2 to change phone number: ");
                            userInputInt = Integer.parseInt(inputString());
                            if(userInputInt > 2 || userInputInt < 1)
                                System.out.println("Wrong input");
                        }catch (Exception e){
                            System.out.println("Input invalid");
                            userInputInt = 0;
                        }
                    }while (userInputInt > 2 || userInputInt < 1);
                    switch (userInputInt) {
                        case 1 -> {
                            do {
                                System.out.println("Enter your new password (8 characters with at least two numbers)");
                                String change = inputString();
                                if (checkPasswordStrength(change)) {
                                    editRecordOfAccount(userName, change, source, target, "password");
                                    System.out.println("Password updated.");
                                    break;
                                }
                            }
                            while (true);
                        }
                        case 2 -> {
                            do {
                                System.out.println("Enter the new phone number: ");
                                String change = checkPhoneNumber();
                                if (!CheckIfRecordExist("", accounts, change, "accounts")) {
                                    editRecordOfAccount(userName, change, source, target, "phone");
                                    System.out.println("Phone no updated.");
                                    break;
                                } else {
                                    System.out.println("This phone number was used before");
                                }
                            } while (true);
                        }
                    }
                    break;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            input.nextLine();
        }
        return exist;
    }

    /** changes password of old customer **/
    public static void editRecordOfAccount(String username, String change, File source , File target, String whatToEdit) {
        try {
            Scanner reader = new Scanner(source);
            PrintWriter writer = new PrintWriter(target);
            String userName1;
            String password1;
            String phoneNumber;
            String dummy;
            while (reader.hasNext()) {
                userName1 = reader.nextLine();
                password1 = reader.nextLine();
                phoneNumber = reader.nextLine();
                dummy = reader.nextLine();
                if (whatToEdit.equals("password")){
                    if (userName1.equals(username)) {
                        writer.println(username);
                        writer.println(change);
                        writer.println(phoneNumber);
                        writer.println(dummy);
                    } else {
                        writer.println(userName1);
                        writer.println(password1);
                        writer.println(phoneNumber);
                        writer.println(dummy);
                    }
                } else if (whatToEdit.equals("phone")){
                    if (userName1.equals(username)) {
                        writer.println(username);
                        writer.println(password1);
                        writer.println(change);
                        writer.println(dummy);
                    } else {
                        writer.println(userName1);
                        writer.println(password1);
                        writer.println(phoneNumber);
                        writer.println(dummy);
                    }
                }
            }
            reader.close();
            writer.flush();
            writer.close();
            deleteInfo = true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            input.nextLine();
        }
    }

    /** checks if user is entering his details correctly using the given name and password **/
    public static boolean signIn(String userName , String password) {
        try {
            try (Scanner reader = new Scanner(accounts)) {
                String userName1;
                String password1;
                while (reader.hasNext()) {
                    userName1 = reader.nextLine();
                    password1 = reader.nextLine();
                    if (userName1.equals(userName) && password1.equals(password)) {
                        userNameFinal = userName;
                        getBalance(userName); // method called
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            input.nextLine();
        }
        return false;
    }

    /** get balance from the txt file **/
    public static void getBalance(String username) {
        // the row balance from txt file
        try {
            try (Scanner reader1 = new Scanner(balance)) {
                while (reader1.hasNext()) {
                    if (reader1.nextLine().equals(username)) {
                        userBalance = Integer.parseInt(reader1.nextLine());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** signing new user and record his account **/
    public static boolean  signUp (String userName , String password) throws IOException {
        // create the communication tanel
        FileOutputStream accountsIn = new FileOutputStream("accounts.txt",true);
        PrintWriter writer = new PrintWriter(accountsIn);
        FileOutputStream balanceIn = new FileOutputStream("balance.txt",true);
        PrintWriter balanceWriter = new PrintWriter(balanceIn);
        System.out.println("Enter your age:");
        do {
            try {
                userInputInt =  Integer.parseInt(inputString());
                if (userInputInt < 18 || userInputInt > 80){
                    System.out.println("To have an account you must be over 18 and less than 80");
                    return false;
                }else
                    break;
            }catch (Exception e){
                System.out.println("please enter age in integers");
            }
        }while (true);
        System.out.println("Enter your phone number");
        String phoneNumber = checkPhoneNumber();

        //this "if" statement checks if the username has been already taken
        if(!CheckIfRecordExist(userName,accounts,phoneNumber,"accounts")) {
            if (checkPasswordStrength(password)) {
                userNameFinal = userName;
                System.out.println("Signed in successfully");
                writer.println(userName);
                writer.println(password);
                writer.println(phoneNumber);
                writer.println("----------------------------");
                balanceWriter.println(userName);
                balanceWriter.println("0");
                writer.close();
                balanceWriter.close();
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("This user name or phone number is already taken.");
            return false;
        }
    }

    /** this function check the password to be at least 8 characters with minimum of two digits and two letters **/
    public static boolean checkPasswordStrength(String password){
        int counter = 0 ;
        int lettersCounter = 0;
        for (int i = 0 ; i < password.length() ; i++){
            if (Character.isDigit(password.charAt(i))){
                counter++;
            }
            else if(Character.isLetter(password.charAt(i))){
                lettersCounter++;
            }
            if (password.length()>= 8 && counter >= 2 && lettersCounter >= 2){
                return true;
            }
        }
        System.out.println("Please choose another password:");
        return false;
    }

    /** generate random driver card details **/
    public static void randomDriverGenerator(int currentLocation , int desiredLocation , String[] locations) {
        // The distance between the locations as a 5 x 5 matrix
        int [][] distances = {
                {0,3,5,7,10},
                {3,0,2,4,7},
                {5,2,0,4,8},
                {7,4,6,0,3},
                {10,7,4,3,0}
        };
        String [] names = {"Ahmed" , "Mark" , "Mohammed", "Salah" , "Kashif" , "Omar" , "Alison", "Samer","Jhon", "Hasan" , "Hael" , "Khaled"};
        int distance = distances[currentLocation][desiredLocation];
        int numberOfAvailableDrivers = 2 + (int)(Math.random() * 5);
        String[][] array = new String[numberOfAvailableDrivers][6];
        for (int i = 0; i < numberOfAvailableDrivers; i++) {

            // this line will generate a random first and second name for the drivers out of the "names" array
            String name = names[(int) (Math.random() * names.length )] + " " +  names[(int) (Math.random() * names.length )];
            System.out.println((i + 1) + "- driver name: " + name );

            // this line will generate a random plate number with 2 characters and 3 numbers
            String carPlate = randomCarPlateGenerator(); // method called
            System.out.println("Car plate: " + carPlate);
            System.out.println("Distance: " + distance + " km");

            // this line will generate the price according to the distance and the price pre KM and add extra charge to simulate the different drivers
            int price = (int)((distance * pricePerKM) * (1.1 + Math.random() * 2) * rideType);
            System.out.println("Price: " + price);
            System.out.println("From: " + locations[currentLocation]);
            System.out.println("To: " + locations[desiredLocation]);
            System.out.println("__________________________________________");
            array[i][0] = name;
            array[i][1] = carPlate;
            array[i][2] = distance + "" ;
            array[i][3] = price+"";
            array[i][4] = locations[currentLocation];
            array[i][5] = locations[desiredLocation];
        }
        int driverNum;
        do {
            try {
                System.out.println("Please select your driver: ");
                driverNum = Integer.parseInt(inputString().trim()) - 1;
                if(driverNum+1 > numberOfAvailableDrivers || driverNum + 1 <= 0)
                    System.out.println("Wrong driver selected. Select again");
                else
                    break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        } while (true);
        rideAccepting(array, driverNum); // method called
    }

    /** check balance and other accepting process (i.e. add ride record and change balance) **/
    public  static void rideAccepting(String[][] array , int driverNum){
        for (int i = 0; i < array.length; i++) {
            FileOutputStream records;
            for (int j = 0; j < 6; j++) {
                try {
                    records = new FileOutputStream("record.txt",true);
                    PrintWriter writer = new PrintWriter(records);
                    if(i == driverNum){
                        //check balance
                        if(userBalance < Integer.parseInt(array[i][3])){
                            System.out.println("Insignificant Balance");
                            break;
                        } else {
                            // store the time of ride
                            long yourMilliSeconds = System.currentTimeMillis();
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                            Date resultDate = new Date(yourMilliSeconds);
                            if (j < 1) {
                                System.out.println("Enjoy Your Ride <3 <3 ^_+ ");
                                editBalance(userNameFinal,"-"+array[i][3],balance,tempFile2);
                                writer.println(userNameFinal);
                                writer.println(sdf.format(resultDate));
                                writer.println(rideTypename);
                            }
                            writer.println(array[i][j]);
                        }
                        writer.close();
                    }
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    input.nextLine();
                }
            }
        }
    }

    /** generates a random number plate of a driver **/
    public static String randomCarPlateGenerator (){
        StringBuilder carPlate = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            carPlate.append((char) (65 + (int) (Math.random() * 20)));
        }
        for (int i = 0; i < 3; i++) {
            carPlate.append((int) (Math.random() * 10));
        }
        return carPlate.toString();
    }

    /** one place for all Strings inputs **/
    public static String inputString(){
        return input.nextLine().trim();
    }
    /** delete the previous file and replace it with the temp that has been edited **/
    public static void deleteFile1(File toDelete,File destination){
        File dummyFile = new File(toDelete.getName());
        toDelete.delete();
        destination.renameTo(dummyFile);
    }

    public static String checkPhoneNumber (){
        boolean valid = false;
        String number = "";
        while (!valid){
         number = inputString();
        if (number.length() == 11 && number.charAt(0) == '0'&& number.charAt(1) == '3'){
            valid = true;
            for (int i = 0; i < number.length(); i++) {
                if (!Character.isDigit(number.charAt(i))){
                    valid = false;
                }
            }
        }
        else
            System.out.println("Number is invalid try again");
        }
     return number;
    }

    public static boolean forgetPassword(String userName , String phonenumber) {
        try {
            try (Scanner reader = new Scanner(accounts)) {
                String userName1;
                String password1;
                String phonenumber1;
                String dummy;
                while (reader.hasNext()) {
                    userName1 = reader.nextLine();
                    password1 = reader.nextLine();
                    phonenumber1 = reader.nextLine();
                    dummy = reader.nextLine();

                    if (userName1.equals(userName) && phonenumber1.equals(phonenumber)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            input.nextLine();
        }
        return false;
    }


    /** __________________________________________________________
             The following is a dummy code to validate visa **/

    // Return true if the card number is valid
    public static boolean checkVisa(long number) {
        return  (getSize(number) >= 13 && getSize(number) <= 16) &&
                (prefixMatched(number, 4) || prefixMatched(number, 5) ||
                prefixMatched(number, 37) || prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) + sumOfOddPlace(number)) % 10 == 0);
    }
    // Get the result from Step 2
    public static int sumOfDoubleEvenPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2) {
            int number2 = Integer.parseInt(num.charAt(i) + "") * 2;
            if(number2 < 9)
                sum += number2;
            else
                sum += number2 / 10 + number2 % 10;
        }
        return sum;
    }

    // Return sum of odd-place digits in number
    public static int sumOfOddPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2) {
            sum += Integer.parseInt(num.charAt(i) + "");
        }
        return sum;
    }
    // Return true if the digit d is a prefix for number
    public static boolean prefixMatched(long number, int d) {

        return getPrefix(number, getSize(d)) == d;
    }
    // Return the number of digits in d
    public static int getSize(long d) {
        String num = d + "";
        return num.length();
    }

    /** Return the first k number of digits from number. If the
      number of digits in number is less than k, return number. */
    public static long getPrefix(long number, int k) {
        if (getSize(number) > k)  {
            String num = number + "";
            return  Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}