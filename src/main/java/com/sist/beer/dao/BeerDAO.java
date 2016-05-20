package com.sist.beer.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.sist.board.dao.BoardVO;

public class BeerDAO {
	private MongoClient mc; // Connection
	private DB db; // ORCL(DataBase)
	private DBCollection dbc; // Table�� 
	public BeerDAO(){
		try{
			// ������ ����
			mc=new MongoClient("localhost");
			// �����ͺ��̽� �б� 
			db=mc.getDB("movie");// use mydb
			// �÷��� ���� 
			dbc=db.getCollection("movie");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	// ������ �߰�  => insert()
	public void beerInsert(BeerVO vo){
		int no=0;
		int group_id=0;
		DBCursor cursor=dbc.find();
		while(cursor.hasNext()){
			BasicDBObject data=(BasicDBObject)cursor.next();
			int n=data.getInt("no");
			if(no<n)
				no=n;
		}
		cursor.close();
		BasicDBObject query=new BasicDBObject();
		query.put("no", vo.getNo());
		query.put("title", vo.getTitle());
		query.put("rating", vo.getRating());
		query.put("poster", vo.getPoster());
		query.put("director", vo.getDirector());
		query.put("actor", vo.getActor());
		query.put("time", vo.getTime());
		query.put("synopsis", vo.getSynopsis());
		query.put("playdate", vo.getPlaydate());
		query.put("genre", vo.getGenre());
		query.put("grade", vo.getGrade());
		dbc.insert(query);
	}
}