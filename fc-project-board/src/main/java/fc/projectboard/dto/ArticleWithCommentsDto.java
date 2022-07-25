package fc.projectboard.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import fc.projectboard.domain.Article;
import lombok.Getter;

@Getter
public class ArticleWithCommentsDto {

    private Long id;
    private UserAccountDto userAccount;
    private Set<ArticleCommentDto> articleCommentDtos;
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

    private ArticleWithCommentsDto(Long id, UserAccountDto userAccount, Set<ArticleCommentDto> articleCommentDtos, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        this.id = id;
        this.userAccount = userAccount;
        this.articleCommentDtos = articleCommentDtos;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public static ArticleWithCommentsDto from(Article article) {
        return new ArticleWithCommentsDto(
                article.getId(),
                UserAccountDto.from(article.getUserAccount()),
                article.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getModifiedAt(),
                article.getModifiedBy()
        );
    }

    public static ArticleWithCommentsDto of(Long id, UserAccountDto userAccount, Set<ArticleCommentDto> articleCommentDtos, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleWithCommentsDto(id,
                userAccount,
                articleCommentDtos,
                title,
                content,
                hashtag,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }
}
