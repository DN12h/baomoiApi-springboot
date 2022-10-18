package com.dn.baomoiapi.controller;

import com.dn.baomoiapi.dto.accountDto;
import com.dn.baomoiapi.model.account;
import com.dn.baomoiapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class UserControl {
    private final AccountService accountService;

    HashMap<String,Object> data;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody accountDto account, HttpServletResponse response){
        Optional<account> accountInfo =  accountService.findByUsernameAndPassword(account.getUsername(),account.getPassword());
        if(!accountInfo.isEmpty()){
            Cookie cookie = new Cookie("username",account.getUsername());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            data = new HashMap<>();
            data.put("data",account.getUsername());
            return ResponseEntity.ok().body(data);
        }else {
            data = new HashMap<>();
            data.put("message","Login fail");
            return ResponseEntity.status(404).body(data);
        }
    }
}
