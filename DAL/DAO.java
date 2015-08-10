import Entities.User;

import java.io.*;
import java.util.HashMap;

public class DAO {
    private static DAO ourInstance = new DAO();
    private static final String FILE_NAME = "users.bin";
    private HashMap<String, User> users;

    public static DAO getInstance() {
        return ourInstance;
    }

    private DAO() {
        try {
            File saveFile = new File(FILE_NAME);
            if (!saveFile.createNewFile()) {
                FileInputStream inputStream = new FileInputStream(saveFile);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                users = (HashMap<String, User>)in.readObject();
                in.close();
            } else {
                users = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (users == null) {
                users = new HashMap<>();
            }
        }

    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean storeUser(User user) {
        users.put(user.name, user);
        return true;
    }

    public void closeDao() {
        try {
            File saveFile = new File(FILE_NAME);
            saveFile.createNewFile();
            FileOutputStream outStream = new FileOutputStream(saveFile, false);
            ObjectOutputStream out = new ObjectOutputStream(outStream);
            out.writeObject(users);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
