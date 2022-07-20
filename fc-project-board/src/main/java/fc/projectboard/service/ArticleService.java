package fc.projectboard.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fc.projectboard.domain.type.SearchType;
import fc.projectboard.dto.ArticleDto;
import fc.projectboard.dto.ArticleUpdateDto;
import fc.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return Page.empty();
    }

    public ArticleDto searchArticles(long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(long articleId, ArticleUpdateDto dto) {
    }

    public void deleteArticle(long articleId, ArticleUpdateDto dto) {
    }
}
