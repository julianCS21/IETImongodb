package eci.ieti.mongodb.controller.User;


import eci.ieti.mongodb.repository.User.User;
import eci.ieti.mongodb.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }



    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.all());

    }


    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        userService.save(newUser);
        URI newURL = URI.create("http://localhost:35000/v1/api/users/" + newUser.getId());
        return ResponseEntity.created(newURL).body(newUser);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable("id") String id) {
        return ResponseEntity.ok(userService.update(newUser,id));
    }
}
