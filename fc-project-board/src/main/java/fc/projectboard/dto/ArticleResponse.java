package fc.projectboard.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createAt;
    private String email;
    private String nickname;

    private ArticleResponse(Long id, String title, String content, String hashtag, LocalDateTime createAt, String email, String nickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createAt = createAt;
        this.email = email;
        this.nickname = nickname;
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.getUserAccountDto().getUserId();
        }

        return new ArticleResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtag(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname
        );
    }
}
