package com.dn.baomoiapi.controller;

import com.dn.baomoiapi.model.category;
import com.dn.baomoiapi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/category")
@Slf4j
 public class CategoryControl {
    @Autowired
    private CategoryService categoryService;

    HashMap<String,Object> data = new HashMap<>();
//    @GetMapping("/")
//    public ResponseEntity<Object> getAllCate(@CookieValue(name = "username", defaultValue = "") String usernameCookie){
//        if(usernameCookie.length() == 0){
//            data = new HashMap<>();
//            data.put("message","err not user");
//            return ResponseEntity.status(404).body(data);
//        }
//        HashMap<String,Object> data = new HashMap<>();
//        data.put("data",categoryService.findAll());
//        return ResponseEntity.ok().body(data);
//    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCategory(@CookieValue(name = "username", defaultValue = "") String usernameCookie ,@RequestBody category categoryData) throws Exception{
        HashMap<String,Object> data = new HashMap<>();
        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }
        try {
            data.put("data", categoryService.save(categoryData));
            data.put("message","success add category");
            return ResponseEntity.ok().body(data);
        }catch(Exception e) {
            data.put("message","fail to save category");
            return ResponseEntity.status(404).body(data);
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Object> delCategory(@CookieValue(name = "username", defaultValue = "") String usernameCookie ,@PathVariable("id") int id) throws Exception{
        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }
        try {
            data = new HashMap<>();
            categoryService.deleteById(id);
            data.put("message","del success");
            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            return ResponseEntity.status(404).body(data.put("message","del success"));
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editCategory(@CookieValue(name = "username", defaultValue = "") String usernameCookie ,@RequestBody category category) throws Exception{
        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }
        try {
            Optional<category> category1 = categoryService.findById(category.getId());
            if(!category1.isPresent()){
                return ResponseEntity.status(404).body(data.put("message","edit err"));
            }else {

                categoryService.save(category);
                data.put("message","edit success");
                return ResponseEntity.ok().body(data);
            }

        }catch (Exception e){
            return ResponseEntity.status(404).body(data.put("message","edit err"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategory(@CookieValue(name = "username", defaultValue = "") String usernameCookie ,@PathVariable int id) throws Exception{
        data = new HashMap<>();

        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }
        Optional<category> category = categoryService.findById(id);
        log.info("data: {}",category);
        try {
            if(!category.isEmpty()){
                category entity = category.get();
                data.put("data",entity);
                return ResponseEntity.ok().body(data);
            }else {
                data.put("message","not found category");
                return ResponseEntity.status(404).body(data);
            }
        }catch (Exception e){
            return ResponseEntity.status(404).body(data.put("message","not found category"));
        }

    }

    @GetMapping("/")
    public ResponseEntity<Object> PageableCategory(@CookieValue(name = "username", defaultValue = "") String usernameCookie,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size)    {
        data = new HashMap<>();
        page = page -1;
        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }
        try{
             List<category> categories = new ArrayList<>();
             Pageable pageable = PageRequest.of(page,size);
             Page<category> pageResult = categoryService.findAll(pageable);
             categories = pageResult.getContent();
            data.put("data",categories);
            data.put("currentPage",pageResult.getNumber() + 1);
            data.put("totalItems",pageResult.getTotalElements());
            data.put("totalPages",pageResult.getTotalPages());

            return ResponseEntity.ok().body(data);
         }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
