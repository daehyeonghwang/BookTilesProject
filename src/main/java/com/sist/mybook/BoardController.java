package com.sist.mybook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.board.dao.*;
import java.util.*;
import java.text.*;
@Controller
public class BoardController {
   @Autowired
   private BoardDAO dao;
   @RequestMapping("board_list.do")
   public String board_list(String page,Model model)
   {
	   if(page==null)
		    page="1";
	   int curpage=Integer.parseInt(page);
	   List<BoardVO> list=dao.boardListData(curpage);
	   int totalpage=dao.boardTotalPage();
	   model.addAttribute("list", list);
	   model.addAttribute("curpage", curpage);
	   model.addAttribute("totalpage", totalpage);
	   model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	   model.addAttribute("msg", "관리자가 삭제한 게시물입니다");
	   return "board/list";
   }
   @RequestMapping("board_insert.do")
   public String board_insert()
   {
	   return "board/insert";
   }
   @RequestMapping("board_insert_ok.do")
   public String board_insert_ok(BoardVO vo)
   {
	   dao.boardInsert(vo);
	   return "redirect:/board_list.do";
   }
   @RequestMapping("board_content.do")
   public String board_content(int no,int page,Model model)
   {
	   BoardVO vo=dao.boardContentData(no, 1);
	   model.addAttribute("dto", vo);
	   model.addAttribute("page", page);
	   return "board/content";
   }
   @RequestMapping("board_update.do")
   public String board_update(int no,int page,Model model)
   {
	   BoardVO vo=dao.boardContentData(no, 0);
	   model.addAttribute("vo", vo);
	   model.addAttribute("page", page);
	   return "board/update";
   }
   @RequestMapping("board_update_ok.do")
   @ResponseBody
   public String board_update_ok(BoardVO vo,int page,Model model)
   {
	   String url="";
	   boolean bCheck=dao.boardUpdate(vo);
	   if(bCheck==true)
	   {
		   url="<script>location.href=\"board_content.do?no="+vo.getNo()+"&page="+page+"\";</script>";
	   }
	   else
	   {
		   url="<script>alert(\"Password Fail!!\");history.back();</script>";
	   }
	   return url;
   }
   @RequestMapping("board_reply.do")
   public String board_reply(int no,int page,Model model)
   {
	   model.addAttribute("no", no);
	   model.addAttribute("page", page);
	   return "board/reply";
   }
   @RequestMapping("board_reply_ok.do")
   public String board_reply_ok(int pno,int page,BoardVO vo,Model model)
   {
	   dao.boardReply(pno, vo);
	   return "redirect:/board_list.do?page="+page;
   }
   @RequestMapping("board_delete.do")
   public String board_delete(int no,int page,Model model)
   {
	   
	   model.addAttribute("no", no);
	   model.addAttribute("page", page);
	   return "board/delete";
   }
   @RequestMapping("board_delete_ok.do")
   @ResponseBody
   public String board_delete_ok(int no,int page,String pwd)
   {
	   String url="";
	   boolean bCheck=dao.boardDelete(no, pwd);
	   if(bCheck==true)
	   {
		   url="<script>location.href=\"board_list.do?page="+page+"\";</script>";
	   }
	   else
	   {
		   url="<script>alert(\"Password Fail!!\");history.back();</script>";
	   }
	   return url;
   }
}









