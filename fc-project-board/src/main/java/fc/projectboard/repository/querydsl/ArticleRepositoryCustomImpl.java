package fc.projectboard.repository.querydsl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import fc.projectboard.domain.Article;
import fc.projectboard.domain.QArticle;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashTags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();
    }
}
