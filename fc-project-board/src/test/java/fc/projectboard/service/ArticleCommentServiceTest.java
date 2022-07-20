package fc.projectboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import fc.projectboard.domain.Article;
import fc.projectboard.domain.ArticleComment;
import fc.projectboard.domain.UserAccount;
import fc.projectboard.dto.ArticleCommentDto;
import fc.projectboard.dto.UserAccountDto;
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
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        //when
        Page<ArticleCommentDto> actual = sut.searchArticleComment(articleId);

        //then
        assertThat(actual)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() throws Exception {
        //given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.getArticleId())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        //when
        sut.saveArticleComment(dto);

        //then
        then(articleRepository).should().getReferenceById(dto.getArticleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
    @Test
    void givenNonexistent_whenSavingArticleComment_thenLongsSituationAndDoseNothing() throws Exception {
        //given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.getArticleId())).willThrow(EntityNotFoundException.class);

        //when
        sut.saveArticleComment(dto);

        //then
        then(articleRepository).should().getReferenceById(dto.getArticleId());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() throws Exception {
        //given
        String oldContent = "content";
        String updatedContent = "댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto dto = createArticleCommentDto(updatedContent);
        given(articleCommentRepository.getReferenceById(dto.getId())).willReturn(articleComment);

        //when
        sut.saveArticleComment(dto);

        //then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(articleCommentRepository).should().getReferenceById(dto.getId());
    }

    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안 한다.")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() throws Exception {
        //given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleCommentRepository.getReferenceById(dto.getId())).willThrow(EntityNotFoundException.class);

        //when
        sut.updateArticleComment(dto);

        //then
        then(articleCommentRepository).should().getReferenceById(dto.getId());
    }

    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() throws Exception {
        //given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);

        //when
        sut.deleteArticleComment(articleCommentId);

        //then
        then(articleCommentRepository).should().deleteById(articleCommentId);

    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "geombong",
                LocalDateTime.now(),
                "geombong"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "geombong",
                "password",
                "qwe123@123.com",
                "geombong",
                "This is memo",
                LocalDateTime.now(),
                "geombong",
                LocalDateTime.now(),
                "geombong"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
                createUserAccount(),
                Article.of(
                        createUserAccount(),
                        "title",
                        "content",
                        "hashtag"
                ),
                content
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "geombong",
                "pw",
                "qwe123@123.com",
                "bong",
                null
        );
    }
}
