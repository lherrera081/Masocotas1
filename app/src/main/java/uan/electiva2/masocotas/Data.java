package uan.electiva2.masocotas;
import java.util.ArrayList;
import java.util.List;

import uan.electiva2.masocotas.entities.LogicException;
import uan.electiva2.masocotas.entities.User;


/**
 * Created by lucho on 3/9/2016.
 */
public class Data {
    private static Data DATA;
    public static Data getDataSingleton(){
        if(DATA==null){
            DATA=new Data();
        }
        return DATA;
    }
    private List<User> users;
    public Data()
    {
        users= new ArrayList<User>();
    }

    public void addUser(User user) throws LogicException {
        for ( User item : users ) {
            if(item.getUserName().equals(user.getUserName())){
                throw new LogicException("El nombre de usuario ya se encuentra registrado");
            }
        }
        users.add(user);
    }

    public User getUserByUsername(String userName){
        for ( User user : users ) {
            if(user.getUserName().equals(userName)){
                return user;
            }
        }
        return null;
    }

}

