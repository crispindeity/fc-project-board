package fc.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fc.projectboard.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
