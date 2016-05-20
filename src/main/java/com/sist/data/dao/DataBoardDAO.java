package com.sist.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class DataBoardDAO {
   @Autowired
   private DataBoardMapper mapper;
   public List<DataBoardVO> databoardAllData(Map map)
   {
	   return mapper.databoardAllData(map);
   }
   public DataBoardVO databoardContentData(int no)
   {
	   return null;
   }
   public void databoardInsert(DataBoardVO vo)
   {
	   mapper.databoardInsert(vo);
   }
   public DataBoardVO databoardContent(int no)
   {
	   mapper.databoardHitIncrement(no);
	   return mapper.databoardContent(no);
   }
}



