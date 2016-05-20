package com.sist.mybook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import com.sist.book.manager.*;
@Controller
public class MainController {
   @Autowired
   private BookManager bm;
   @RequestMapping("main.do")
   /*
    *  [
          <c:forEach var="vo" items="">
          {
            name: 'John',
            data: [5, 3, 4, 7, 2],
            stack: 'male'
          },
          </c:forEach>
        ]
    */
   public String main_page(Model model)
   {
	   List<BookVO> list=bm.bookAllData();
	   String title="[";
	   int i=0;
	   String json="[";
	   for(BookVO vo:list)
	   {
		   if(i<6)
		   {
		     title+="'"+vo.getTitle()+"',";
		     json+="{name:'"+vo.getTitle()
		           +"',data:["+vo.getOprice()+","+vo.getSprice()+"],"
		           +"stack:'"+vo.getPublisher()+"'},";
		   }
		   i++;
	   }
	   title=title.substring(0,title.lastIndexOf(","));
	   title+=']';
	   json=json.substring(0,json.lastIndexOf(","));
	   json+="]";
	   System.out.println(title);
	   System.out.println(json);
	   model.addAttribute("title", title);
	   model.addAttribute("json", json);
	   model.addAttribute("list", list);
	   return "main";
   }
}






