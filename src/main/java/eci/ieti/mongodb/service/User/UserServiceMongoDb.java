package eci.ieti.mongodb.service.User;


import eci.ieti.mongodb.repository.user.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceMongoDb implements UserService {


    private final UserMongoRepository userMongoRepository;


    @Autowired
    public UserServiceMongoDb(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }


}
