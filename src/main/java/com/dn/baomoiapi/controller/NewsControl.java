package com.dn.baomoiapi.controller;

import com.dn.baomoiapi.dto.newsDto;
import com.dn.baomoiapi.model.category;
import com.dn.baomoiapi.model.news;
import com.dn.baomoiapi.service.CategoryService;
import com.dn.baomoiapi.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("api/news")
public class NewsControl {
    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    HashMap<String,Object> data ;
//    @GetMapping("/")
//    public ResponseEntity<Object> getAllNews(){
//        data = new HashMap<>();
//        data.put("data",newsService.findAll());
//        return ResponseEntity.ok().body(data);
//    }

    @PostMapping("/add")
    public ResponseEntity<Object> addNews(@CookieValue(name = "username", defaultValue = "") String usernameCookie , @RequestBody newsDto news){
        log.info("Cookie :{}",usernameCookie);
        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }else {
            data = new HashMap<>();
            news dataNews = new news();
            BeanUtils.copyProperties(news,dataNews);
            category category = new category();
            category.setId(news.getCateId());
            dataNews.setCateId(category);
            dataNews.setCreateAt(Date.valueOf(LocalDate.now()));
            dataNews.setCreateBy(usernameCookie);
            newsService.save(dataNews);
            data.put("data",dataNews);
            return ResponseEntity.ok().body(data);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delNews(@CookieValue(name = "username", defaultValue = "") String usernameCookie ,@PathVariable int id){
        data = new HashMap<>();
        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }
        try {
            newsService.deleteById(id);
            return ResponseEntity.ok().body(data.put("message","delete success"));
        }catch (Exception e){
            return ResponseEntity.ok().body(data.put("message","delete fail"));
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editNews(@CookieValue(name = "username", defaultValue = "") String usernameCookie ,@RequestBody newsDto newsDto){
        data = new HashMap<>();
        if(usernameCookie.length() == 0){
            data = new HashMap<>();
            data.put("message","err not user");
            return ResponseEntity.status(404).body(data);
        }
        Optional<news> newsOpt = newsService.findById(newsDto.getId());

        if (newsOpt.isEmpty()){
            data.put("message","not fond");
            return ResponseEntity.status(404).body(data);
        }else {
            news dataNews = new news();
            category category = new category();
            category.setId(newsDto.getCateId());
            BeanUtils.copyProperties(newsDto,dataNews);
            dataNews.setCateId(category);
            newsService.save(dataNews);
            data.put("data",dataNews);
            return ResponseEntity.ok().body(data);
        }
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<Object> getNews(@PathVariable("id") int id){

            Optional<news> opt = newsService.findById(id) ;
            if(opt.isEmpty()){
                data = new HashMap<>();
                data.put("message","Not found");
                return ResponseEntity.status(404).body(data);
            }else{
                data = new HashMap<>();
                news dataNews = opt.get();
                data.put("data",dataNews);
                return ResponseEntity.ok().body(data);          }

    }

    @GetMapping("/test")
    public ResponseEntity<Object> getHome(){
        data = new HashMap<>();
        List<news> newsList = newsService.find5News();
        data.put("hotNews",newsList);
        List<category> categories = categoryService.findAll();
        Random random = new Random();
        Object[] randCate = new Object[3];

        int i = 0;
       do{
               boolean isDup = false;
        int crrRand = random.nextInt(0,categories.size());
        for (var ind :  randCate) {
            if(ind == null || crrRand == (int)ind ){
                isDup = true;
                break;
            }
        }
        if(!isDup) {
            randCate[i] = crrRand;
            i++;
        }}while(i<3);


        List<Object> dataCate = new ArrayList<>();
        log.info("randCate:{}",randCate);
        for (var id : randCate) {
            log.info("i:{}",id);

            HashMap<String, Object> temp = new HashMap<>();
            temp.put("cateName",categories.get((int) id).getName());
            temp.put("data",newsService.findByCateId(categories.get((int) id).getId()));
            dataCate.add(temp);
        }
        data.put("dataCate",dataCate);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/")
    public ResponseEntity<Object> PageableNews(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        data = new HashMap<>();
        page = page -1;

        try{
            List<news> newsList = new ArrayList<>();
            Pageable pageable = PageRequest.of(page,size);
            Page<news> pageResult = newsService.findAll(pageable);
            newsList = pageResult.getContent();
            data.put("data",newsList);
            data.put("currentPage",pageResult.getNumber() + 1);
            data.put("totalItems",pageResult.getTotalElements());
            data.put("totalPages",pageResult.getTotalPages());

            return ResponseEntity.ok().body(data);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
