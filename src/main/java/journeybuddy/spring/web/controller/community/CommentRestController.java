package journeybuddy.spring.web.controller.community;

import journeybuddy.spring.apiPayload.ApiResponse;
import journeybuddy.spring.service.community.comment.CommentCommandService;
import journeybuddy.spring.web.dto.community.comment.CommentResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/my_page")
public class CommentRestController {

    @Autowired
    private final CommentCommandService commentCommandService;


//    //대충댓글저장하기
//    @PostMapping("/comment/save/{postId}")
//    public ApiResponse<CommentRequestDTO> saveComment(@RequestBody CommentRequestDTO commentRequestDTO, @PathVariable Long postId,
//                                                        @AuthenticationPrincipal UserDetails userdetails) {
//        Comment savedComment = CommentConverter.toComment(commentRequestDTO);
//        String userEmail = userdetails.getUsername();
//        Comment savedComments = commentCommandService.commentSave(userEmail,postId, savedComment);
//        CommentRequestDTO savedDTO = CommentConverter.toCommentRequestDTO(savedComments);
//        return ApiResponse.onSuccess(savedDTO);
//    }

    //내가 쓴 댓글 확인하기(페이징처리)

    @GetMapping("/comment/myComment")
    public ApiResponse <Page<CommentResponseDTO>> checkMyComment(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "9") int size) {

        int maxSize = 50;
        size = Math.min(size, maxSize);

        Pageable pageable = PageRequest.of(page, size);

        String userEmail = userDetails.getUsername();
        log.info("나의 모든 댓글 조회");
        Page<CommentResponseDTO> checkMyComment = commentCommandService.checkMyComment(userEmail,pageable);
        return ApiResponse.onSuccess(checkMyComment);
    }


}
