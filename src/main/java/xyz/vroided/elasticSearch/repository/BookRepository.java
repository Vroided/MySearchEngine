package xyz.vroided.elasticSearch.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import xyz.vroided.elasticSearch.pojo.Book;

import java.util.List;

/**
 * @author Vroided
 * @date 2021/06/02/7:11 PM
 * @description
 */
@Repository
public interface BookRepository extends ElasticsearchRepository<Book, Long> {
    List<Book> queryBookByName(String name);

    @Query("{\"prefix\":{\"name.keyword\":{\"value\":\"?0\"}}}")
    List<Book> prefixByName(String name, Pageable pageable);

    @Query("{\"bool\":{\"should\":[{\"match\":{\"name\":\"?0\"}},{\"match\":{\"info\":\"?1\"}}]}},\"sort\":\"_score\",\"_source\":[\"name\",\"publish\",\"info\"]")
//    @Highlight(fields = {@HighlightField(name = "name"), @HighlightField(name = "info"), @HighlightField(name = "publish")})
    SearchPage<Book> fuzzySearchAll(String name, String info, Pageable pageable);
}
