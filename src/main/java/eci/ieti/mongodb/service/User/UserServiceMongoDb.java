package eci.ieti.mongodb.service.User;


import eci.ieti.mongodb.repository.User.User;
import eci.ieti.mongodb.repository.User.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMongoDb implements UserService {


    private final UserMongoRepository userMongoRepository;


    @Autowired
    public UserServiceMongoDb(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }


    @Override
    public User save(User user) {

        Optional<User> productO = findById(user.getId());
        if(productO.isEmpty()){
            this.userMongoRepository.save(user);
        }
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return this.userMongoRepository.findById(id);
    }

    @Override
    public List<User> all() {
        return this.userMongoRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        this.userMongoRepository.deleteById(id);

    }

    @Override
    public User update(User user, String userId) {
        Optional<User> userToSearch = findById(userId);
        if(userToSearch.isPresent()){
            userToSearch.get().setUsername(user.getUsername());
            userToSearch.get().setPassword(user.getPassword());
        }
        save(userToSearch.get());
        return userToSearch.get();

    }
}
