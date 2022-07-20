package fc.projectboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
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

import fc.projectboard.domain.Article;
import fc.projectboard.domain.type.SearchType;
import fc.projectboard.dto.ArticleDto;
import fc.projectboard.dto.ArticleUpdateDto;
import fc.projectboard.repository.ArticleRepository;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleList() throws Exception {
        //given

        //when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenId_whenSearchingArticle_thenReturnArticle() throws Exception {
        //given

        //when
        ArticleDto articles = sut.searchArticles(1L);

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() throws Exception {
        //given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "geombong", "title", "content", "#hashtag");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.saveArticle(dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() throws Exception {
        //given
        ArticleUpdateDto updateDto = ArticleUpdateDto.of("title", "content", "#hashtag");
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.updateArticle(1L, updateDto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() throws Exception {
        //given
        ArticleUpdateDto updateDto = ArticleUpdateDto.of("title", "content", "#hashtag");
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //when
        sut.deleteArticle(1L, updateDto);

        //then
        then(articleRepository).should().delete(any(Article.class));
    }
}
