package com.sist.mybook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.sist.data.dao.DataBoardDAO;
import com.sist.data.dao.DataBoardVO;
import com.sist.data.dao.FileInfoVO;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.servlet.http.HttpServletResponse;
@Controller
public class DataBoardController {
   @Autowired
   private DataBoardDAO dao;
   @RequestMapping("databoard_list.do")
   public String databoard_list(String page,Model model)
   {
	   if(page==null)
		    page="1";
	   int curpage=Integer.parseInt(page);
	   int rowSize=10;
	   int start=(curpage*rowSize)-rowSize;
	   int end=curpage*rowSize;
	   Map map=new HashMap();
	   map.put("start", start);
	   map.put("end", end);
	   List<DataBoardVO> list=dao.databoardAllData(map);
	   model.addAttribute("list", list);
	   model.addAttribute("curpage", curpage);
	   return "databoard/list";
   }
   @RequestMapping("databoard_insert.do")
   public String databoard_insert()
   {
	   return "databoard/insert";
   }
   @RequestMapping("databoard_insert_ok.do")
   public String databoard_insert_ok(DataBoardVO vo)
   throws Exception
   {
	   List<MultipartFile> list=vo.getFiles();
	   String temp1=""; // filename VARCHAR2 aaa,bbb,ccc
	   String temp2=""; // filesize VARCHAR2
	   if(list!=null && list.size()>0)
	   {
		   for(MultipartFile mf:list)
		   {
			   String fn=mf.getOriginalFilename();
			   File file=new File("c:\\download\\"+fn);
			   mf.transferTo(file);
			   temp1+=fn+",";
			   temp2+=file.length()+",";
		   }
		   vo.setFilename(temp1.substring(0,temp1.lastIndexOf(',')));
		   vo.setFilesize(temp2.substring(0,temp2.lastIndexOf(',')));
		   vo.setFilecount(list.size());
	   }
	   else
	   {
		   vo.setFilename("");
		   vo.setFilesize("");
		   vo.setFilecount(0);
	   }
	   dao.databoardInsert(vo);
	   return "redirect:/databoard_list.do";
   }
   @RequestMapping("databoard_content.do")
   public String databoard_content(int no,int page,Model model)
   {
	   DataBoardVO vo=dao.databoardContent(no);
	   if(vo.getFilecount()!=0)
	   {
		   String[] fn=vo.getFilename().split(",");
		   String[] fs=vo.getFilesize().split(",");
		   for(int i=0;i<fn.length;i++)
		   {
			   FileInfoVO fvo=new FileInfoVO();
			   fvo.setFilename(fn[i]);
			   fvo.setFilesize(fs[i]);
			   vo.getFlist().add(fvo);
		   }
	   }
	   model.addAttribute("dto", vo);
	   model.addAttribute("page", page);
	   return "databoard/content";
   }
   @RequestMapping("databoard_download.do")
   public void databoard_download(String fn,HttpServletResponse response)
   {
	    
	    try
	    {
	    	String path="c://download//";
		    response.setHeader("Content-Disposition", 
		    		"attachment;filename="
		    		+URLEncoder.encode(fn, "UTF-8"));
		    File file=new File(path+fn);
		    response.setContentLength((int)file.length());
		    // 다운로드창을 전송
		    // 데이터 전송 
	    	BufferedInputStream bis=
	    		new BufferedInputStream(new FileInputStream(file));
	    	BufferedOutputStream bos=
	    		new BufferedOutputStream(response.getOutputStream());
	    	int i=0;
	    	byte[] buffer=new byte[1024];
	    	while((i=bis.read(buffer, 0, 1024))!=-1)
	    	{
	    		bos.write(buffer,0, i);
	    	}
	    	bis.close();
	    	bos.close();
	    }catch(Exception ex){}
   }
}











