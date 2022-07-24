package fc.projectboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import fc.projectboard.domain.type.SearchType;
import fc.projectboard.dto.ArticleDto;
import fc.projectboard.dto.ArticleWithCommentsDto;
import fc.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword, Pageable pageable) {
        return Page.empty();
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(long articleId, ArticleDto dto) {
    }

    public void deleteArticle(long articleId, String dto) {
    }

    public Page<ArticleDto> searchArticlesViaHashtag(String hashtage, Pageable pageable) {
        return null;
    }

    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return null;
    }

    public void getArticle(Long articleId) {

    }

    public long getArticleCount() {
        return 0;
    }

    public List<String> getHashtags() {
        return List.of();
    }
}
