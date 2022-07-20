package fc.projectboard.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ArticleDto {
    private LocalDateTime createdAt;
    private String createdBy;
    private String title;
    private String content;
    private String hashtag;

    private ArticleDto(LocalDateTime createdAt,
                       String createdBy,
                       String title,
                       String content,
                       String hashtag) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static ArticleDto of(LocalDateTime createdAt,
                                String createdBy,
                                String title,
                                String content,
                                String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }
}
