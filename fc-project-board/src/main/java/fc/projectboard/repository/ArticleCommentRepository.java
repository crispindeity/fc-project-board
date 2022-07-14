package fc.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fc.projectboard.domain.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
