package com.sist.book.manager;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.net.*;

@Component
public class BookManager {
	public List<BookVO> bookAllData(){
		List<BookVO> list = new ArrayList<BookVO>();
		try{
			Document doc = Jsoup.connect("http://www.bandinlunis.com/front/display/listBest.do").get();
			Elements posterElem = doc.select("div.prod_thumb_img img");
			Elements reviewElem = doc.select("dl.prod_info dd.txt_bex");
			Elements titleElem = doc.select("dl.prod_info dt a");
			Elements opriceElem = doc.select("dd.mt5 span.txt_reprice");
			Elements spriceElem = doc.select("span.txt_price strong em");
			Elements pubElem = doc.select("dl.prod_info dd.txt_block");
			for(int i=0;i<posterElem.size();i++){
				Element pelem = posterElem.get(i);
				String poster = pelem.attr("src");
				// attr() : 속성값
				// text() : 태그사이의 문자열  <a><b>aaa</b></a> aaa
				// html() : 태그사이의 문자열  <a><b>aaa</b></a> <b>aaa</b>
				Element relem = reviewElem.get(i);
				Element telem = titleElem.get(i);
				Element oelem = opriceElem.get(i);
				Element selem = spriceElem.get(i);
				Element pubelem = pubElem.get(i);
				String temp = pubelem.text();
				StringTokenizer st = new StringTokenizer(temp, "|");
				BookVO vo = new BookVO();
				vo.setPoster(poster);
				vo.setReview(relem.text());
				vo.setTitle(telem.text());
				vo.setSprice(Integer.parseInt(selem.text().replace(",", "")));
				vo.setOprice(Integer.parseInt(oelem.text().substring(0,oelem.text().lastIndexOf("원")).replace(",", "")));
				vo.setAuthor(st.nextToken().trim());
				vo.setPublisher(st.nextToken().trim());
				vo.setRegdate(st.nextToken().trim());
				list.add(vo);
			}
			
		}catch(Exception ex){
			System.out.println("bookAllData() : "+ex.getMessage());
		}
		return list;
	}
	
	public List<BookVO> yes24bookAllData(){
		List<BookVO> list=new ArrayList<BookVO>();
		try{
			Document doc=Jsoup.connect("http://www.yes24.com/24/category/bestseller").get();
			Elements posterElem=doc.select("ol li p.image img");
			Elements titleElem=doc.select("ol li p.image img");
			Elements spriceElem=doc.select("ol li p.price strong");
			Elements pubElem=doc.select("ol li p.aupu");
			for(int i=0;i<posterElem.size();i++){
				Element pelem=posterElem.get(i);
				String poster=pelem.attr("src");
				Element telem=titleElem.get(i);
				String title=telem.attr("alt");
				Element selem=spriceElem.get(i);
				Element pubelem=pubElem.get(i);
				BookVO vo=new BookVO();
				vo.setPoster(poster);
				vo.setTitle(title);
				vo.setSprice(Integer.parseInt(selem.text().substring(0, selem.text().lastIndexOf("원")).replace(",", "")));
				vo.setAuthor(pubelem.text().substring(0, pubelem.text().indexOf("저")).trim());
				vo.setPublisher(pubelem.text().substring(pubelem.text().lastIndexOf("|")+1).trim());
				list.add(vo);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return list;
	}
}
