package com.edutrack.framework.persist;

import java.math.BigDecimal;

/**
 * 시퀀스에 대한 
 * @author $Author: cvs $<p><p>$Id: ISequence.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 */
public interface ISequence
{
	
    /*
     * 시퀀스의 값을 Int형으로 반환해 준다.
     */
	int getIdAsInt(String seqName) throws Exception;
	
    /*
     * 시퀀스의 값을 String형으로 반환해 준다.
     */
	String getIdAsString(String seqName) throws Exception;
	
    /*
     * 시퀀스의 값을 Long형으로 반환해 준다.
     */
	long getIdAsLong(String seqName) throws Exception;
	
    /*
     * 시퀀스의 값을 BigDecimal형으로 반환해 준다.
     */
	BigDecimal getIdAsBigDecimal(String seqName) throws Exception;

}
