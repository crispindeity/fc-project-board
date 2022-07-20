package fc.projectboard.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ArticleCommentDto {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private String content;

    private ArticleCommentDto(LocalDateTime createdAt,
                              String createdBy,
                              LocalDateTime modifiedAt,
                              String modifiedBy,
                              String content) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.content = content;
    }

    public static ArticleCommentDto of(LocalDateTime createdAt,
                                       String createdBy,
                                       LocalDateTime modifiedAt,
                                       String modifiedBy,
                                       String content) {
        return new ArticleCommentDto(createdAt, createdBy, modifiedAt, modifiedBy, content);
    }
}
