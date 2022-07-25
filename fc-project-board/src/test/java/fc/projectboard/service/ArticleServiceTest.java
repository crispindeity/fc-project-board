package fc.projectboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import fc.projectboard.domain.Article;
import fc.projectboard.domain.UserAccount;
import fc.projectboard.domain.type.SearchType;
import fc.projectboard.dto.ArticleDto;
import fc.projectboard.dto.ArticleWithCommentsDto;
import fc.projectboard.dto.UserAccountDto;
import fc.projectboard.repository.ArticleRepository;
import fc.projectboard.repository.UserAccountRepository;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserAccountRepository userAccountRepository;

    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticles_thenReturnArticlePage() throws Exception {
        //given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() throws Exception {
        //given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        ArticleWithCommentsDto dto = sut.getArticle(articleId);

        //then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticlesPage() throws Exception {
        //given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }

    @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnsEmptyPage() throws Exception {
        //given
        Pageable pageable = Pageable.ofSize(20);

        //when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(null, pageable);

        //then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).shouldHaveNoInteractions();
    }

    @DisplayName("게시글을 해시태크 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenHashtag_whenSearchingArticlesViaHashtag_thenReturnArticlesPage() throws Exception {
        //given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashtag(hashtag, pageable)).willReturn(Page.empty(pageable));

        //when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(hashtag, pageable);

        //then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashtag(hashtag, pageable);
    }

    @DisplayName("게시글 ID로 조회하면, 댓글 달린 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleWithComments_thenReturnsArticleWithComments() throws Exception {
        //given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        ArticleWithCommentsDto dto = sut.getArticleWithComments(articleId);

        //then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 달린 게시글이 없으면, 예외를 던진다.")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() throws Exception {
        //given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        //when
        Throwable t = catchThrowable(() -> {
            sut.getArticle(articleId);
        });

        //then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다 - articleId: " + articleId);
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() throws Exception {
        //given
        ArticleDto dto = createArticleDto();
//        given(userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId())).willReturn(createUserAccount());
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());

        //when
        sut.saveArticle(dto);

        //then
//        then(userAccountRepository).should().getReferenceById(dto.getUserAccountDto().getUserId());
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenModifiedArticleInfo_whenUpdatingArticle_thenUpdatesArticle() throws Exception {
        //given
        Article article = createArticle();
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(dto.getId())).willReturn(article);
//        given(userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId())).willReturn(dto.getUserAccountDto().toEntity());

        //when
        sut.updateArticle(dto.getId(), dto);

        //then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.getTitle())
                .hasFieldOrPropertyWithValue("content", dto.getContent())
                .hasFieldOrPropertyWithValue("hashtag", dto.getHashtag());
        then(articleRepository).should().getReferenceById(dto.getId());
//        then(userAccountRepository).should().getReferenceById(dto.getUserAccountDto().getUserId());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
    @Test
    void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() throws Exception {
        //given
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springvoot");
        given(articleRepository.getReferenceById(dto.getId())).willThrow(EntityNotFoundException.class);

        //when
        sut.updateArticle(dto.getId(), dto);

        //then
        then(articleRepository).should().getReferenceById(dto.getId());
    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() throws Exception {
        //given
        Long articleId = 1L;
        String userId = "geombong";
        willDoNothing().given(articleRepository).deleteByIdAndUserAccount_UserId(articleId, userId);

        //when
        sut.deleteArticle(articleId, userId);

        //then
        then(articleRepository).should().deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    @DisplayName("게시글 수를 조회하면, 게스길 수를 반환한다.")
    @Test
    void givenNothing_whenCountingArticles_thenReturnsArticleCount() throws Exception {
        //given
        long expected = 0L;
        given(articleRepository.count()).willReturn(expected);

        //when
        long actual = sut.getArticleCount();

        //then
        assertThat(actual).isEqualTo(expected);
        then(articleRepository).should().count();
    }

//    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다.")
//    @Test
//    void givenNothing_whenCalling_thenReturnsHashtags() throws Exception {
//        //given
//        List<String> expectedHashtags = List.of("#java", "#spring", "#boot");
//        given(articleRepository.findAllDistinctHashtags()).willReturn(expectedHashtags);
//
//        //when
//        List<String> actualHashtags = sut.getHashtags();
//
//        //then
//        assertThat(actualHashtags).isEqualTo(expectedHashtags);
//        then(articleRepository).should().findAllDistinctHashtags();
//    }


    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content", "#java");
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "geombong",
                LocalDateTime.now(),
                "geombong");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "geombong",
                "pw",
                "qwe123@123.com",
                "geombong",
                "this is memo",
                LocalDateTime.now(),
                "geombong",
                LocalDateTime.now(),
                "geombong"
        );
    }


    private Article createArticle() {
        Article article = Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
        ReflectionTestUtils.setField(article, "id", 1L);

        return article;
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "geombong",
                "pw",
                "qwe123@123.com",
                "geombong",
                null
        );
    }
}
