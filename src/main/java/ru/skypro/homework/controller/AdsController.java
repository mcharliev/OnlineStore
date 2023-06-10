package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {
    private final AdsService adsService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> createAds(
            @RequestPart("image") MultipartFile file,
            @RequestPart("properties") CreateAdsDto createAds) {
        return ResponseEntity.ok(adsService.createAds(createAds));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getFullAdsById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteAds(@PathVariable Integer id) {
        adsService.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreateAdsDto> updateAds(@PathVariable Integer id,
                                                  @RequestBody AdsDto ads) {
        return ResponseEntity.ok(adsService.editAds(id,ads));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adsService.getAllMyAds(authentication));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAdsImage(@PathVariable Integer id,
                                                 @RequestParam MultipartFile image) {
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getAdsComments(@PathVariable Integer id) {
        return ResponseEntity.ok(new ResponseWrapperCommentDto());
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createAdsComment(@PathVariable Integer id,
                                                       @RequestBody CommentDto comment) {
        return ResponseEntity.ok(commentService.createComment(id, comment));
    }

    @DeleteMapping("/{adId}/comments/{commentId}/")
    public void deleteAdsComment(@PathVariable("adId") String adId,
                                 @PathVariable("commentId") Integer commentId) {
    }

    @PatchMapping("/{adId}/comments/{commentId}/")
    public ResponseEntity<CommentDto> editAdsComment(@PathVariable("adId") String adId,
                                                     @PathVariable("commentId") Integer commentId,
                                                     @RequestBody CommentDto Comment) {
        return ResponseEntity.ok(new CommentDto());
    }
}