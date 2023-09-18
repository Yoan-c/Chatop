package com.example.rentals.controller;

import com.example.rentals.entity.LogIn;
import com.example.rentals.entity.Messages;
import com.example.rentals.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(
            summary = "Post a message",
            description = "A user can post a message about a rental")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
    @PostMapping("")
    public ResponseEntity<HashMap<String, String>> postMessage(@RequestBody HashMap<String, String> hashMsgInfo){
        HashMap<String, String> message = new HashMap<>();
        messageService.postMessage(hashMsgInfo);
        message.put("message", "message send with success");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
