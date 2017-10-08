package admin;

import model.User;
import sql.DBManager;

import java.util.ArrayList;

public class UserAdmin {

    static void prompt(){
        while(true){
            System.out.println("Choose one: list (list users), add (add user), edit (edit user), delete (delete user), quit (quit): ");
            String action = App.scanner.nextLine();
            if (action.equals("add")) {
                addNewUserAction();
            } else if (action.equals("list")) {
                listUsersAction();
            } else if (action.equals("edit")) {
                editUserAction();
            } else if (action.equals("delete")) {
                removeUserAction();
            } else if (action.equals("quit")) {
                System.out.println("End");
                closeApplication();
            } else {
                System.out.println("Wrong argument");
            }
        }
    }

    static void listUsersAction() {
        ArrayList<User> users = User.loadAll();
        for(User _user: users){
            System.out.println(_user);
        }
    }

    static void addNewUserAction(){
        System.out.println("Add user");

        String username = promptString(Type.USERNAME);
        String email = promptString(Type.EMAIL);
        String password = promptString(Type.PASSWORD);
        int person_group_id = promptInt(Type.PERSON_GROUP_ID);
        App.scanner.nextLine();
        User user = new User(username, email, password, person_group_id);
        user.saveToDB();

        System.out.println("USER ADDED");
    }

    static void editUserAction(){
        System.out.println("Edit");
        int id = promptInt(Type.ID);
        App.scanner.nextLine();
        String username = promptString(Type.USERNAME);
        String email = promptString(Type.EMAIL);
        String password = promptString(Type.PASSWORD);
        int person_group_id = promptInt(Type.PERSON_GROUP_ID);
        App.scanner.nextLine();
        ArrayList<User> user = User.loadById(id);
        if (!email.isEmpty()) { User.(email); }
        if (!password.isEmpty()) User.setPassword(password);
        if (person_group_id!=0) user.setPersonGroupId(person_group_id);
        if (!username.isEmpty()) user.setUserName(username);
        User.saveToDB;
        System.out.println("Edit done");
    }

    static void removeUserAction() {
        System.out.println("Delete");
        int id = promptInt(Type.USER_ID);
        App.scanner.nextLine();
        if (id!=0  && App.areYouSurePrompt()) {
            User user = User.loadById(long id);
            user.delete();
            System.out.println("Usunięto użytkownika");
        }
    }

    public static String promptString(Type type) {
        switch (type) {
            case USERNAME:
                System.out.println("Give user name:");
                break;
            case EMAIL:
                System.out.println("Give email");
                break;
            case PASSWORD:
                System.out.println("Give password");
                break;
            default:
                break;
        }
        return App.scanner.nextLine();
    }
    public static int promptInt(Type type) {
        switch (type) {
            case PERSON_GROUP_ID:
                System.out.println("Give group id:");
                break;
            case USER_ID:
                System.out.println("Give user id:");
                break;
            default:
                break;
        }
        while(!App.scanner.hasNextInt()) {
            App.scanner.next();
            System.out.println("Wrong argument");
        }
        return App.scanner.nextInt();
    }
    public static boolean areYouSurePrompt() {
        boolean areYouSure = false;
        boolean isCorrect = false;
        while(!isCorrect){
            System.out.println("Are you sure (Y/n)?");
            if (App.scanner.hasNextLine()) {
                String action = App.scanner.nextLine();
                if (action.equals("Y")) {
                    isCorrect = true;
                    areYouSure = true;
                } else if (action.equals("n")) {
                    isCorrect = true;
                    areYouSure = false;
                } else {
                    System.out.println("Wrong argument");
                }
            }
        }
        return areYouSure;
    }
    static void closeApplication() {
        DBManager.close();
        System.exit(0);
    }

    private enum Type{
        USERNAME,
        EMAIL,
        PASSWORD,
        SALT,
        PERSON_GROUP_ID,
        ID

    }
}
