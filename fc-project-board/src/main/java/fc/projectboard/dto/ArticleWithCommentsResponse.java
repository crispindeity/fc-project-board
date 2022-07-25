package fc.projectboard.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class ArticleWithCommentsResponse {
    private Long id;
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createAt;
    private String email;
    private String nickname;
    private Set<ArticleCommentResponse> articleCommentsResponse;

    private ArticleWithCommentsResponse(Long id, String title, String content, String hashtag, LocalDateTime createAt, String email, String nickname, Set<ArticleCommentResponse> articleCommentsResponse) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createAt = createAt;
        this.email = email;
        this.nickname = nickname;
        this.articleCommentsResponse = articleCommentsResponse;
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.getUserAccount().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.getUserAccount().getUserId();
        }

        return new ArticleWithCommentsResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtag(),
                dto.getCreatedAt(),
                dto.getUserAccount().getEmail(),
                nickname,
                dto.getArticleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
