package fc.projectboard.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;

@Getter
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createdAt;
    private String email;
    private String nickname;

    private ArticleResponse(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createdAt = createdAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleResponse that = (ArticleResponse) o;
        return title.equals(that.title) && content.equals(that.content) && hashtag.equals(that.hashtag) && email.equals(that.email) && nickname.equals(that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, hashtag, email, nickname);
    }
}
