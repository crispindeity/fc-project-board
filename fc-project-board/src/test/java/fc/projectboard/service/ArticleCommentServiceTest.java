package fc.projectboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import fc.projectboard.domain.Article;
import fc.projectboard.domain.UserAccount;
import fc.projectboard.dto.ArticleCommentDto;
import fc.projectboard.repository.ArticleCommentRepository;
import fc.projectboard.repository.ArticleRepository;
import fc.projectboard.repository.UserAccountRepository;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleCommentRepository articleCommentRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserAccountRepository userAccountRepository;

    @DisplayName("게시글 Id로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsComments() throws Exception {
        //given
        Long articleId = 1L;
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("uno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(1L);

        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }
}
