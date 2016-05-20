package com.sist.data.dao;
import java.util.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
public interface DataBoardMapper {
   @Select("SELECT no,subject,name,regdate,hit,filecount,num "
		  +"FROM (SELECT no,subject,name,regdate,hit,filecount,rownum as num "
		  +"FROM (SELECT no,subject,name,regdate,hit,filecount "
		  +"FROM multiDataBoard ORDER BY no DESC)) "
		  +"WHERE num BETWEEN #{start} AND #{end}")
   public List<DataBoardVO> databoardAllData(Map map);
   // insert
   @SelectKey(keyProperty="no",before=true,resultType=int.class,
		   statement="SELECT NVL(MAX(no)+1,1) as no FROM multiDataboard")
   @Insert("INSERT INTO multiDataboard VALUES(#{no},#{name},#{subject},"
		  +"#{content},#{pwd},SYSDATE,0,#{filename},"
		  +"#{filesize},#{filecount})")
   public void databoardInsert(DataBoardVO vo);
   // update
   // delete
   // content => Transaction
   @Select("SELECT no,name,subject,content,regdate,hit,"
		  +"filename,filesize,filecount "
		  +"FROM multiDataboard "
		  +"WHERE no=#{no}")
   public DataBoardVO databoardContent(int no);
   @Update("UPDATE multiDataBoard SET "
		  +"hit=hit+1 "
		  +"WHERE no=#{no}")
   public void databoardHitIncrement(int no);
   // find 
}





