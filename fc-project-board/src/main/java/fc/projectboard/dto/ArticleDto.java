package fc.projectboard.dto;

import java.time.LocalDateTime;

import fc.projectboard.domain.Article;
import fc.projectboard.domain.UserAccount;
import lombok.Getter;

@Getter
public class ArticleDto {
    private Long id;
    private UserAccountDto userAccountDto;
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;


    public ArticleDto(Long id,
                      UserAccountDto userAccountDto,
                      String title,
                      String content,
                      String hashtag,
                      LocalDateTime createdAt,
                      String createdBy,
                      LocalDateTime modifiedAt,
                      String modifiedBy) {
        this.id = id;
        this.userAccountDto = userAccountDto;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public static ArticleDto of(UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag) {
        return new ArticleDto(null, userAccountDto, title, content, hashtag, null, null, null, null);
    }

    public static ArticleDto of(Long id,
                                UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag,
                                LocalDateTime createdAt,
                                String createdBy,
                                LocalDateTime modifiedAt,
                                String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article article) {
        return new ArticleDto(
                article.getId(),
                UserAccountDto.from(article.getUserAccount()),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getModifiedAt(),
                article.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(userAccount, title, content, hashtag);
    }
}
