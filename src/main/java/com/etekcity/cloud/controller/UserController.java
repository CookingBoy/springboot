package com.etekcity.cloud.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseData register(@RequestBody @Validated RegisterAndLoginRequestData ralData) throws Exception {
        return userService.register(ralData);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(@RequestBody @Validated RegisterAndLoginRequestData ralData) throws Exception {
        return userService.login(ralData);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseData logout(@RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.logout(authorization);
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ResponseData getUserInfo(@RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.getUserInfo(authorization);
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseData updateUserInfo(@RequestBody UpdateUserInfoRequestData updateUserInfoRequestData,
                                       @RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.updateUserInfo(updateUserInfoRequestData, authorization);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResponseData updatePassword(@RequestBody @Validated UpdatePasswordRequestData updatePasswordRequestData,
                                       @RequestHeader("X-Authorization") String authorization) throws Exception {
        return userService.updatePassword(updatePasswordRequestData, authorization);
    }
}
