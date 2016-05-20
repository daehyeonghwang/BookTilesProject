package com.sist.board.dao;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.mongodb.*;
@Repository
public class BoardDAO {
   private MongoClient mc; // Connection
   private DB db; // ORCL(DataBase)
   private DBCollection dbc; // Table명 
   public BoardDAO()
   {
	   try
	   {
		   // 몽고디비 연결 
		   mc=new MongoClient("localhost");
		   // 데이터베이스 읽기 
		   db=mc.getDB("mydb");// use mydb
		   // 컬렉션 연결 
		   dbc=db.getCollection("board");
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
   }
   // {no:1,name:"",subject:""} => BasicDBObject
   // BasicDBObject => 여러개 : DBCursor(ResultSet)
   // 목록 출력 
   public List<BoardVO> boardListData(int page)
   {
	   List<BoardVO> list=new ArrayList<BoardVO>();
	   try
	   {
		   int rowSize=10;
		   int skip=(page*rowSize)-rowSize;
		   DBCursor cursor=dbc.find().
				   sort(new BasicDBObject("group_id",-1)
				   .append("group_step", 1)).skip(skip).limit(rowSize);
		   // SELECT * FROM board  => Order BY group_id DESC,group_step ASC
		   // WHERE rownum BETWEEN skip AND limit
		   // NOSQL {no:1,name:"",subject:""}, ROW
		   // {no:2,name:"",subject:{}}
		   while(cursor.hasNext())
		   {
			   BasicDBObject obj=
					   (BasicDBObject)cursor.next();
			   // rs.next()
			   BoardVO vo=new BoardVO();
			   vo.setNo(obj.getInt("no"));
			   vo.setSubject(obj.getString("subject"));
			   vo.setName(obj.getString("name"));
			   vo.setRegdate(obj.getString("regdate"));
			   vo.setHit(obj.getInt("hit"));
			   vo.setGroup_tab(obj.getInt("group_tab"));
			   list.add(vo);
		   }
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   return list;
   }
   // 데이터 추가  => insert()
   public void boardInsert(BoardVO vo)
   {
	   
	   int no=0;
	   int group_id=0;
	   DBCursor cursor=dbc.find();
	   while(cursor.hasNext())
	   {
		   BasicDBObject data=(BasicDBObject)cursor.next();
		   int n=data.getInt("no");
		   int g=data.getInt("group_id");
		   if(no<n)
			   no=n;
		   if(group_id<g)
			   group_id=g;
	   }
	   cursor.close();
	   BasicDBObject query=
			   new BasicDBObject();
	   
	   query.put("no", no+1);
	   query.put("name", vo.getName());
	   query.put("subject", vo.getSubject());
	   query.put("content", vo.getContent());
	   query.put("pwd", vo.getPwd());
	   query.put("regdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	   query.put("hit", 0);
	   query.put("group_id", group_id+1);
	   query.put("group_step", 0);
	   query.put("group_tab", 0);
	   query.put("root", 0);
	   query.put("depth", 0);
	   // {name:"",}
	   dbc.insert(query);
   }
   // 총페이지 구하기
   public int boardTotalPage()
   {
	   int total=0;
	   DBCursor cursor=dbc.find();
	   int count=cursor.count();
	   // SELECT COUNT(*) FROM board
	   total=(int)(Math.ceil(count/10.0));
	   return total;
   }
   // 데이터 수정  => $set => update
   // 답변 올리기 
   public void boardReply(int pno,BoardVO vo)
   {
	   try
	   {
		   // pno => gi,gs,gt
		   // 조건절 
		   BasicDBObject where=new BasicDBObject();
		   where.put("no", pno);
		   BasicDBObject pObj=(BasicDBObject)dbc.findOne(where);
		   int gi=pObj.getInt("group_id");
		   int gs=pObj.getInt("group_step");
		   int gt=pObj.getInt("group_tab");
		   int depth=pObj.getInt("depth");
		   
		   // WHERE group_id=gi AND group_step>gs
		   // step증가
		   BasicDBObject[] temp={
			  new BasicDBObject("group_id",gi),//group_id=gi
			  new BasicDBObject("group_step",
					   new BasicDBObject("$gt",gs)) //group_step>gs
		   };
		   BasicDBObject where2=new BasicDBObject("$and",temp);
		   DBCursor cursor=dbc.find(where2);
		   while(cursor.hasNext())
		   {
			   // 파싱  => 분석(MapReduce,R(rJava)) => 저장 (MongoDB)
			   // 시각화  => 예측(몬테칼로,퍼지) 
			   BasicDBObject bd=(BasicDBObject)cursor.next();
			   int no=bd.getInt("no");
			   int group_step=bd.getInt("group_step");
			   dbc.update(new BasicDBObject("no",no),
					 new BasicDBObject("$set",new BasicDBObject("group_step",group_step+1)));
			     
		   }
		   cursor.close();
		   // insert
		   int no=0;
		   cursor=dbc.find();
		   while(cursor.hasNext())
		   {
			   BasicDBObject data=(BasicDBObject)cursor.next();
			   int n=data.getInt("no");
			   if(no<n)
				   no=n;
		   }
		   cursor.close();
		   BasicDBObject query=
				   new BasicDBObject();
		   
		   query.put("no", no+1);
		   query.put("name", vo.getName());
		   query.put("subject", vo.getSubject());
		   query.put("content", vo.getContent());
		   query.put("pwd", vo.getPwd());
		   query.put("regdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		   query.put("hit", 0);
		   query.put("group_id", gi);
		   query.put("group_step", gs+1);
		   query.put("group_tab", gt+1);
		   query.put("root", pno);
		   query.put("depth", 0);
		   // {name:"",}
		   dbc.insert(query);
		   // depth증가 
		   BasicDBObject dup=new BasicDBObject();
		   dup.put("depth", depth+1);
		   // depth=depth+1
		   dbc.update(new BasicDBObject("no", pno),
				   new BasicDBObject("$set",dup));
		   // UPDATE SET board depth=depth+1 WHERE no=pno
		   /*
		    *   SELECT ==> find()
		    *   SELECT ~ WHERE ==> find({no:1})
		    *   
		    *   UPDATE => update("$set")
		    *   DELETE => remove({no:1})
		    *   INSERT => insert({})
		    *   
		    *   {}=BasicDBObject
		    */
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
   }
   // 데이터 삭제   => remove()
   public boolean boardDelete(int no,String pwd)
   {
	   boolean bCheck=false;
	   try
	   {
		   BasicDBObject where=
				   new BasicDBObject("no",no);
		   BasicDBObject data=(BasicDBObject)dbc.findOne(where);
		   String db_pwd=data.getString("pwd");
		   int root=data.getInt("root");
		   int depth=data.getInt("depth");
		   if(pwd.equals(db_pwd))
		   {
			   bCheck=true;
			   if(depth==0)
			   {
				   dbc.remove(where);
			   }
			   else
			   {
				   BasicDBObject up=new BasicDBObject();
				   up.put("subject", "관리자가 삭제한 게시물입니다");
				   up.put("content", "관리자가 삭제한 게시물입니다");
				   dbc.update(where, new BasicDBObject("$set",up));
			   }
			   // 상위
			   BasicDBObject p=new BasicDBObject();
			   p.put("no", root);
			   BasicDBObject pup=(BasicDBObject)dbc.findOne(p);
			   int pd=pup.getInt("depth");
			   dbc.update(p, new BasicDBObject("$set",new BasicDBObject("depth",pd-1)));
			   /*
			    *                   root  depth
			    *   1 AAAA            0     1 
			    *   2   => BBBB       1     0
			    *   //3     => CCCC     2     0
			    */
			   
		   }
		   else
		   {
			   bCheck=false;
		   }
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   return bCheck;
   }
   // 데이터 수정 
   // 찾기             => $regex   => 연산처리 
   /*
    *  연산처리 
    *     WHERE col명 연산자 값
    *     <    $lt
    *     >    $gt
    *     <=   $le
    *     >=   $ge
    *     ==   $eq
    *     !=   $ne
    */
   // 내용보기 
   public BoardVO boardContentData(int no,int type)
   {
	   BoardVO vo=new BoardVO();
	   try
	   {
		   BasicDBObject where=new BasicDBObject();
		   where.put("no", no);
		   // WHERE no=1 ==> find({no:1})
		   if(type==1)
		   {
			   BasicDBObject data1=(BasicDBObject)dbc.findOne(where);
			   // {}   find() => selectList
			   int hit=data1.getInt("hit");
			   BasicDBObject up=new BasicDBObject();
			   up.put("hit", hit+1);
			   dbc.update(where, new BasicDBObject("$set",up));
		   }
		   BasicDBObject data=(BasicDBObject)dbc.findOne(where);
		   vo.setNo(data.getInt("no"));
		   vo.setName(data.getString("name"));
		   vo.setSubject(data.getString("subject"));
		   vo.setRegdate(data.getString("regdate"));
		   vo.setContent(data.getString("content"));
		   vo.setHit(data.getInt("hit"));
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   return vo;
   }
   public boolean boardUpdate(BoardVO vo)
   {
	   boolean bCheck=false;
	   try
	   {
		   BasicDBObject where=new BasicDBObject();
		   where.put("no", vo.getNo());
		   // WHERE no=vo.getNo() => {no:1}
		   BasicDBObject data=(BasicDBObject)dbc.findOne(where);
		   String db_pwd=data.getString("pwd");
		   if(db_pwd.equals(vo.getPwd()))
		   {
			   bCheck=true;
			   BasicDBObject up=new BasicDBObject();
			   up.put("name", vo.getName());
			   up.put("subject", vo.getSubject());
			   up.put("content", vo.getContent());
			   dbc.update(where, new BasicDBObject("$set",up));
		   }
		   else
		   {
			   bCheck=false;
		   }
		   
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   return bCheck;
   }
}










