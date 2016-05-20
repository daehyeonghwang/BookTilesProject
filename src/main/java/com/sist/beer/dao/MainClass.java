package com.sist.beer.dao;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainClass {
	public static void main(String[] args){
		BeerDAO dao = new BeerDAO();
		bookAllData();
		
	}
	public static List<BeerVO> bookAllData(){
		List<BeerVO> list = new ArrayList<BeerVO>();
		try{
//			for(int i=1;i<=184;i++){
				Document doc = Jsoup.connect("http://new.cinefox.com/vod/movie/list?page=1").get();
				Elements movieNO = doc.select("div.listBox ul.list li div.postimg");
				for(int j=0;j<movieNO.size();j++){
					Element noelem = movieNO.get(j);
					String no1 = noelem.attr("onclick");
					no1 = no1.substring(no1.lastIndexOf('=')+1,no1.lastIndexOf(")")-1);
					
					Document doc1 = Jsoup.connect("http://new.cinefox.com/vod/view?product_seq="+no1).get();
					Elements titleElem = doc1.select("title");
					Elements posterElem = doc1.select("img#PIMG");
					Elements ratingElem = doc1.select("div.metaInfoWrap div:eq(0)");
					Elements directorElem = doc1.select("div.metaInfoWrap div:eq(1)");
					Elements synopsisElem = doc1.select("div#content");
					String title = titleElem.text();
					title = title.substring(6);   // title
					String poster = posterElem.attr("src");   // poster
					String rating = ratingElem.text();
					rating = rating.substring(0, rating.lastIndexOf("|")-1);
					rating = rating.substring(rating.lastIndexOf("|")+1);    // rating
					String director = directorElem.text();
					if(director.substring(0,director.lastIndexOf("|")-1).length()>6)
						director = director.substring(director.indexOf(":")+2, director.lastIndexOf("|"));  // director
					else
						director = "";
					String actor = directorElem.text();
					if(actor.substring(actor.lastIndexOf(":")).length()>9)
						if(actor.contains("다운로드"))
							actor = actor.substring(actor.lastIndexOf(":")+2,actor.lastIndexOf(" "));  // actor
						else
							actor = actor.substring(actor.lastIndexOf(":")+2);  // actor
					else
						actor = "";
					String time = ratingElem.text();
					time = time.substring(0,time.indexOf("|")); // time
					String synopsis = synopsisElem.text();
					//synopsis = synopsis.replace("?", "" );
					synopsis = synopsis.replace("&nbsp;", "");
					String playdate = ratingElem.text();
					System.out.println(synopsis);
				}
//			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
}
