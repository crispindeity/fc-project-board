package fc.projectboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import fc.projectboard.domain.Article;
import fc.projectboard.domain.UserAccount;
import fc.projectboard.domain.constant.SearchType;
import fc.projectboard.dto.ArticleDto;
import fc.projectboard.dto.ArticleWithCommentsDto;
import fc.projectboard.repository.ArticleRepository;
import fc.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;


    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable)
                    .map(ArticleDto::from);
        }

        switch (searchType) {
            case TITLE:
                return articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT:
                return articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID:
                return articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME:
                return articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG:
                return articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        }
        return Page.empty();
    }

    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다. - articleId: " + articleId));
    }

    @Transactional
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    @Transactional
    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId());
            if (article.getUserAccount().equals(userAccount)) {
                if (dto.getTitle() != null) {
                    article.setTitle(dto.getTitle());
                }
                if (dto.getContent() != null) {
                    article.setContent(dto.getContent());
                }
                article.setHashtag(dto.getHashtag());
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    @Transactional
    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }
        return articleRepository
                .findByHashtag(hashtag, pageable)
                .map(ArticleDto::from);
    }

    public long getArticleCount() {
        return 0;
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashTags();
    }
}
