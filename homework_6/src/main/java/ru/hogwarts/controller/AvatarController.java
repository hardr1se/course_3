package ru.hogwarts.controller;

import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.model.Avatar;
import ru.hogwarts.service.AvatarService;

import java.util.Collection;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("{id}/fs")
    public ResponseEntity<byte[]> getFromFS(@PathVariable long id) {
        return builder(avatarService.getFromFS(id));
    }

    @GetMapping("{id}/db")
    public ResponseEntity<byte[]> getFromDB(@PathVariable long id) {
        return builder(avatarService.getFromDB(id));
    }

    private ResponseEntity<byte[]> builder(Pair<byte[], String> pair) {
        byte[] data = pair.getFirst();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(pair.getSecond()))
                .contentLength(data.length)
                .body(data);
    }

    @GetMapping
    public Collection<Avatar> getAvatarsByPages(@RequestParam Integer page,
                                                @RequestParam Integer size) {
        return avatarService.getAvatarsByPages(page, size);
    }
}
