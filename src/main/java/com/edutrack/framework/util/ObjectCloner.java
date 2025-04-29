package com.edutrack.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



/**
 * 	java.io.Serializable 을 구현한 객체의 복사본 생성
 *	
 *	성능 향상을 위해 메모리에 Hashtable 을 운영하는 경우, 특정 객체를 제공할 때
 *	Hashtable 내에 있는 객체의 복사본을 생성한 후 이를 리턴할 수 있도록 서비스 제공
 *
 *	하이텔, 자바동에서 퍼옮
 *
 * 	<pre>
 *    	RetrieveStatement rstmt = (RetrieveStatement)retrieveCache.get("develop", "develop-select");
 *	
 *	if(rstmt != null){
 *		rstmt2 = ObjectCloner.deepCopy(rstmt);
 *	}
 * 	</pre>
 *
 *	@author  saijer
 * 	@version %0%, %9%
 * 	@see     com.epiontech.persist.PersistentBroker
 */
public class ObjectCloner 
{ 

   //ObjectCloner 객체를 생성할 수 없게 하기 위해 private 생성자로 치환합니다.
   private ObjectCloner(){} 

   /**
    * Deep Copy 한 객체를 반환해줍니다..
    * @param oldObj
    * @return
    */
   static public Object deepCopy(Object oldObj) 
   { 

      ObjectOutputStream oos = null; 
      ObjectInputStream ois = null; 

      try 
      { 

         ByteArrayOutputStream bos = 
               new ByteArrayOutputStream(); 
         oos = new ObjectOutputStream(bos); 

         // 복사하려는 객체를 직렬화 한 뒤 넘겨줍니다. 
         oos.writeObject(oldObj);
         oos.flush(); 



         ByteArrayInputStream bin = 
               new ByteArrayInputStream(bos.toByteArray()); 

        ois = new ObjectInputStream(bin); 

         // 다시 객체를 돌려받습니다. 
         return ois.readObject();

      } 
      catch(Exception e) 
      { 
          System.err.println("Exception in ObjectCloner = " + e); 
	return null;
      } 
      finally 
      { 
	try{
         oos.close(); 
         ois.close(); 
	}catch(IOException ex){
		 System.err.println("Can't close in ObjectCloner!!");
		 System.err.println(""+ex);
	}
		
      } 

   } 

} 
