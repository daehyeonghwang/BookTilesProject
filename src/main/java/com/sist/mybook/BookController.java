package com.sist.mybook;

import org.springframework.stereotype.Controller;
import java.util.*;

import javax.annotation.Resource;

import com.sist.book.manager.*;
@Controller
public class BookController {
   @Resource(name="bookManager")
   private BookManager bm;
}
