package fc.projectboard.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fc.projectboard.dto.ArticleCommentRequest;
import fc.projectboard.dto.UserAccountDto;
import fc.projectboard.dto.security.BoardPrincipal;
import fc.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of("uno", "pw", "123@123.com", null, null)));
        return "redirect:/articles/" + articleCommentRequest.getArticleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long commentId,
            Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
    ) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getUsername());
        return "redirect:/articles/" + articleId;
    }
}
