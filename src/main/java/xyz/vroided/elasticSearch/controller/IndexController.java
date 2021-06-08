package xyz.vroided.elasticSearch.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.vroided.elasticSearch.dto.BookDto;
import xyz.vroided.elasticSearch.dto.PageDto;
import xyz.vroided.elasticSearch.pojo.Book;
import xyz.vroided.elasticSearch.repository.BookRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vroided
 * @date 2021/06/02/7:12 PM
 * @description
 */
@Controller
public class IndexController {

    @Resource
    private BookRepository bookRepository;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/q", method = {RequestMethod.GET, RequestMethod.POST})
    public String findByNameAndInfoContaining(Model model,
                                              @RequestParam(value = "name") String name,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        SearchPage<Book> searchPage = bookRepository.fuzzySearchAll(name, name, PageRequest.of(page - 1, size));
        List<SearchHit<Book>> searchHits = searchPage.getSearchHits().getSearchHits();
        // 搜索结果
        ArrayList<BookDto> books = new ArrayList<>();
        searchHits.forEach(book -> books.add(new BookDto(book.getContent())));
        model.addAttribute("books", books);
        // 搜索到的总条数
        long total = searchPage.getTotalElements();
        model.addAttribute("totalElements", searchPage.getTotalElements());
        // 分页信息
        model.addAttribute("pagination", new PageDto<>((int) total, page, size));
        // 关键字信息
        model.addAttribute("keyword", name);
        // 网页标题
        model.addAttribute("title", name + " - SearchEngine");
        return "result";
    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryByTitleGet(Model model,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        List<Book> books = bookRepository.prefixByName(name.trim(), PageRequest.of(page, size));
        model.addAttribute("books", books);
        model.addAttribute("keyword", name);
        model.addAttribute("title", name + " - SearchEngine");
        return "result";
    }
}
