package xyz.vroided.elasticSearch.controller;

/**
 * @author Vroided
 * @date 2021/06/02/7:16 PM
 * @description
 */

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.vroided.elasticSearch.pojo.Book;
import xyz.vroided.elasticSearch.repository.BookRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookRepository bookRepository;

    @RequestMapping(value = "/exist-doc", method = RequestMethod.GET)
    public Object existDoc(@RequestParam(value = "id") Long id) {
        return bookRepository.existsById(id);
    }

    @RequestMapping(value = "/query-doc", method = RequestMethod.GET)
    public List<Book> queryByName(@RequestParam(value = "name") String name) {
        return bookRepository.queryBookByName(name);
    }

    @RequestMapping(value = "/prefix-title-doc", method = RequestMethod.POST)
    public Object prefixQueryTitleByName(@RequestParam(value = "name") String name,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        List<Book> books = bookRepository.prefixByName(name.trim(), PageRequest.of(page, size));
        List<String> titles = new ArrayList<>();
        for (Book book : books) {
            titles.add(book.getName());
        }
        return titles;
    }

    @RequestMapping(value = "/prefix-book-doc", method = RequestMethod.GET)
    public Object prefixQueryBookByName(@RequestParam(value = "name") String name,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        return bookRepository.prefixByName(name.trim(), PageRequest.of(page, size));
    }

}
