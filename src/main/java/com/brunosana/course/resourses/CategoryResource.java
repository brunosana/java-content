package com.brunosana.course.resourses;

import com.brunosana.course.domain.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> list(){
        Category category = new Category(1, "Info");
        Category categoryTwo = new Category(2, "Office");
        List<Category> list= new ArrayList<>();
        list.add(category);
        list.add(categoryTwo);
        return list;
    }
}
