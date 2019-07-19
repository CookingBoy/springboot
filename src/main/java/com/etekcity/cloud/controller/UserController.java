package com.etekcity.cloud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.etekcity.cloud.domain.request.RegisterAndLoginRequestData;
import com.etekcity.cloud.domain.request.UpdatePasswordRequestData;
import com.etekcity.cloud.domain.request.UpdateUserInfoRequestData;
import com.etekcity.cloud.domain.response.ResponseData;
import com.etekcity.cloud.service.UserService;

/**
 * created by vik on 2019年7月12日
 *
 * @author vik
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/v1/user/register", method = RequestMethod.POST)
    public ResponseData register(@RequestBody RegisterAndLoginRequestData ralData) throws Exception {
        return userService.register(ralData);
    }

    @RequestMapping(value = "/api/v1/user/login", method = RequestMethod.POST)
    public ResponseData login(@RequestBody RegisterAndLoginRequestData ralData) throws Exception {
        return userService.login(ralData);
    }

    @RequestMapping(value = "/api/v1/user/logout", method = RequestMethod.POST)
    public ResponseData logout(@RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.logout(authorization);
    }

    @RequestMapping(value = "/api/v1/user/getUserInfo", method = RequestMethod.POST)
    public ResponseData getUserInfo(@RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.getUserInfo(authorization);
    }

    @RequestMapping(value = "/api/v1/user/updateUserInfo", method = RequestMethod.POST)
    public ResponseData updateUserInfo(@RequestBody UpdateUserInfoRequestData updateUserInfoRequestData,
                                       @RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.updateUserInfo(updateUserInfoRequestData, authorization);
    }

    @RequestMapping(value = "/api/v1/user/updatePassword", method = RequestMethod.POST)
    public ResponseData updatePassword(@RequestBody UpdatePasswordRequestData updatePasswordRequestData,
                                       @RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.updatePassword(updatePasswordRequestData, authorization);
    }
}
