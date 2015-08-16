package Interfaces;

import Entities.User;

public interface IDAO {
    User getUser(String username);
    boolean storeUser(User user);
    void closeDao();
}
