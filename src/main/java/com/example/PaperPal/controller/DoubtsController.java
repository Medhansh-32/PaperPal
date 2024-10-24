package com.example.PaperPal.controller;

import com.example.PaperPal.entity.Doubts;
import com.example.PaperPal.service.DoubtsService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/doubts")
public class DoubtsController {


    private DoubtsService doubtsService;

    @Autowired
    public DoubtsController(DoubtsService doubtsService) {
        this.doubtsService=doubtsService;
    }

    @PostMapping("/addReply")
    public ResponseEntity<?> postDoubtReply(@RequestBody DoubtsService.Reply reply){
        doubtsService.addReply(reply);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getReply/{id}")
    public ResponseEntity<?> getDoubtReply(@PathVariable ObjectId id){
        List<String> replies=doubtsService.getReply(id);
    if(!replies.isEmpty()) {
        return new ResponseEntity<>(replies, HttpStatus.OK);
        }else{
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/postDoubts")
    public ResponseEntity<String> addDoubts(@RequestBody Doubts doubts) {
        if(doubtsService.addDoubts(doubts)){
            return new ResponseEntity<>("Doubt posted successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
