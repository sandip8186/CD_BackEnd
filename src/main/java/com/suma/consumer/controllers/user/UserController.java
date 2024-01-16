package com.suma.consumer.controllers.user;

import com.suma.consumer.constants.field.ConstantFields;
import com.suma.consumer.constants.path.PathContatnt;
import com.suma.consumer.model.dto.ResponseDto;
import com.suma.consumer.model.entities.User;
import com.suma.consumer.repositories.user.UserRepository;
import com.suma.consumer.services.messagesource.MessageSourceService;
import com.suma.consumer.services.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathContatnt.USER)
@Api(tags="User" ,value = "User" )
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    UserService userService;

    @PostMapping(PathContatnt.SAVE_USER)
    public ResponseEntity<ResponseDto> saveUser(@RequestBody User user){
        System.out.println("--------User -----------");

        // return userService saveUser

        return  ResponseEntity.ok().body(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                messageSourceService.getMessage(ConstantFields.SUCCESS),user.getUser()));

//        return  ResponseEntity.ok().body(new ResponseDto(1,
//               "User Saved Succesfully"));
    }

    @GetMapping(PathContatnt.GET_USER)
    public User getUserDatils(@RequestParam String userName){
        System.out.println("--------Get User------------");
        System.out.println("");
        User user =new User();
        user.setId(Long.valueOf(1));
        user.setUser("sandip");
        user.setPassword("Sumasoft@123");
        user.setRole("Admin");
        return  user;
    }

    @PostMapping(PathContatnt.LOGOUT)
    public ResponseEntity<ResponseDto> logoutUser(@RequestParam Long userId){
        System.out.println("--------logOut------------");
        return ResponseEntity.ok().body(userService.logoutUser(userId));
    }

}
