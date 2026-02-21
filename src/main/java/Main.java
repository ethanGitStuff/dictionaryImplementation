import java.util.*;

public class Main {
    public static void inserter(BinarySearchTreeDict<String, String> dict, Scanner newScanner)  throws NullKeyException, NullValueException{
        String userKey;
        String userValue;
        System.out.println("Enter a key: ");
        userKey = newScanner.next();

        System.out.println("Enter a value: ");
        userValue = newScanner.next();

        dict.insert(userKey, userValue);
    }

    public static void finder(BinarySearchTreeDict<String, String> dict, Scanner newScanner) throws NullKeyException {
        String userKey;

        System.out.println("Enter a key: ");
        userKey = newScanner.next();

        String result = dict.find(userKey);
        if (result == null) {
            System.out.println("Key was not found.");
        }
        else {
            System.out.println("Value = " + result);
        }
    }

    public static void deleter(BinarySearchTreeDict<String, String> dict, Scanner newScanner) throws NullKeyException{
        String userKey;

        System.out.println("Enter a key: ");
        userKey = newScanner.next();

        String resultString = dict.find(userKey);
        boolean resultBool = dict.delete(userKey);

        if (resultBool) {
            System.out.println("Value deleted: " + resultString);
        }
        else {
            System.out.println("Key was not found.");
        }
    }

    public static void main(String[] args) throws NullKeyException, NullValueException{
        Scanner newScanner = new Scanner(System.in);
        BinarySearchTreeDict<String, String> dict = new BinarySearchTreeDict<>();

        int userChoice = 0;

        while (userChoice != 4){
            System.out.println("|1) Insert  |2) Find  |3) Delete  |4) Quit");
            userChoice = newScanner.nextInt();
            switch (userChoice) {
                case 1:
                    inserter(dict, newScanner);
                    break;
                case 2:
                    finder(dict, newScanner);
                    break;
                case 3:
                    deleter(dict, newScanner);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Sorry, not a valid choice.");
            }
            System.out.println("Current Size: " + dict.getSize());
        }

        newScanner.close();
    }
}
