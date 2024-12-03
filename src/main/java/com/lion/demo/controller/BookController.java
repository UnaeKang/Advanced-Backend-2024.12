package com.lion.demo.controller;


import com.lion.demo.entity.Book;
import com.lion.demo.service.BookService;
import com.lion.demo.service.CsvFileReaderService;
import java.io.IOException;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired private CsvFileReaderService csvFileReaderService;
    @Autowired private BookService bookService;

    @GetMapping("/list")
    public String list(@RequestParam(name = "p",defaultValue = "1") int page, Model model) {
        List<Book> bookList = bookService.getBooksBypage(page);
        model.addAttribute("bookList",bookList);
        return "book/list";
    }

    @GetMapping("/yes24")
    public String yes24() throws IOException {
        csvFileReaderService.csvFileToH2();
        return "redirect:/book/list";
    }
}
