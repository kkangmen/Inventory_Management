package myProject.toyproject.ultraviolet.controller;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.ultraviolet.service.UltravioletService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ult")
public class UltravioletController {

    private final UltravioletService ultravioletService;

    @GetMapping
    public ResponseEntity<Void> getUltraviolet(){

        ultravioletService.getUltraviolet();

        return ResponseEntity.ok().build();
    }
}
