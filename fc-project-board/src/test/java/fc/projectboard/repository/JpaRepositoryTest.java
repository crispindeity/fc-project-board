package fc.projectboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import fc.projectboard.config.JpaConfig;
import fc.projectboard.domain.Article;
import fc.projectboard.domain.UserAccount;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository,
                             @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Test
    @DisplayName("select 테스트")
    void givenTestData_whenSelecting_thenWorksFine() throws Exception {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @Test
    @DisplayName("insert 테스트")
    void givenTestData_whenInserting_thenWorksFine() throws Exception {
        // Given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("newUno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");
        // When
        articleRepository.save(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @Test
    @DisplayName("update 테스트")
    void givenTestData_whenUpdating_thenWorksFine() throws Exception {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";

        // When
        article.setHashtag(updatedHashtag);

        // Then
        assertThat(articleRepository.findById(1L).get().getHashtag()).isEqualTo("#springboot");
    }

    @Test
    @DisplayName("delete 테스트")
    void givenTestData_whenDeleting_thenWorksFine() throws Exception {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(articleRepository.count())
                    .as("게시글이 삭제되면 삭제된 게시글 수 만큼 줄어야 한다.")
                    .isEqualTo(previousArticleCount - 1);
            softAssertions.assertThat(articleCommentRepository.count())
                    .as("게시클이 삭제되면 연관되어 있는 댓글도 함께 삭제 되어야 한다.")
                    .isEqualTo(previousArticleCommentCount - deletedCommentsSize);
        });
    }
}
