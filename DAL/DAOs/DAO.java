package DAOs;

import Entities.User;
import Interfaces.IDAO;

import java.io.*;
import java.util.HashMap;

public class DAO implements IDAO{
    private static final DAO ourInstance = new DAO();
    private static final String FILE_NAME = "users.bin";
    private HashMap<String, User> users;

    public static IDAO getInstance() {
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

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public boolean storeUser(User user) {
        users.put(user.name, user);
        return true;
    }

    @Override
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
