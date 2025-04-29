/*
 * Created on 2005. 8. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.onlinequiz.dto;

/**
 * @author HerSunJa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OnlineQuizDTO {

    protected String systemCode  ; 
    protected String seqNo       ;
    protected String subject     ;
    protected String quizType    ;
    protected String example1    ;
    protected String example2    ;
    protected String example3    ;
    protected String example4    ;
    protected String example5    ;
    protected String answer      ; 
    protected String quizComment ;
    protected String quizCurriUrl;
    protected String quizLinkUrl ;
    protected String useYn       ;
    protected String regId       ;
    protected String regDate     ;
    protected String modId       ;
    protected String modDate     ;
    protected String[] example = new String[10];

    public String getSystemCode    () { return this.systemCode  ; }     
    public String getSeqNo         () { return this.seqNo       ; }
    public String getSubject       () { return this.subject     ; }
    public String getQuizType      () { return this.quizType    ; }
    public String getExample1      () { return this.example1    ; }
    public String getExample2      () { return this.example2    ; }
    public String getExample3      () { return this.example3    ; }
    public String getExample4      () { return this.example4    ; }
    public String getExample5      () { return this.example5    ; }
    public String getAnswer        () { return this.answer      ; }     
    public String getQuizComment   () { return this.quizComment ; }
    public String getQuizCurriUrl  () { return this.quizCurriUrl; }
    public String getQuizLinkUrl   () { return this.quizLinkUrl ; }
    public String getUseYn         () { return this.useYn       ; }
    public String getRegId         () { return this.regId       ; }
    public String getRegDate       () { return this.regDate     ; }
    public String getModId         () { return this.modId       ; }
    public String getModDate       () { return this.modDate     ; }
	public String[] getExample     () { return example;           }

    public void setSystemCode   ( String systemCode   ) { this.systemCode   = systemCode  ; }     
    public void setSeqNo        ( String seqNo        ) { this.seqNo        = seqNo       ; }
    public void setSubject      ( String subject      ) { this.subject      = subject     ; }
    public void setQuizType     ( String quizType     ) { this.quizType     = quizType    ; }
    public void setExample1     ( String example1     ) { this.example1     = example1    ; }
    public void setExample2     ( String example2     ) { this.example2     = example2    ; }
    public void setExample3     ( String example3     ) { this.example3     = example3    ; }
    public void setExample4     ( String example4     ) { this.example4     = example4    ; }
    public void setExample5     ( String example5     ) { this.example5     = example5    ; }
    public void setAnswer       ( String answer       ) { this.answer       = answer      ; }     
    public void setQuizComment  ( String quizComment  ) { this.quizComment  = quizComment ; }
    public void setQuizCurriUrl ( String quizCurriUrl ) { this.quizCurriUrl = quizCurriUrl; }
    public void setQuizLinkUrl  ( String quizLinkUrl  ) { this.quizLinkUrl  = quizLinkUrl ; }
    public void setUseYn        ( String useYn        ) { this.useYn        = useYn       ; }
    public void setRegId        ( String regId        ) { this.regId        = regId       ; }
    public void setRegDate      ( String regDate      ) { this.regDate      = regDate     ; }
    public void setModId        ( String modId        ) { this.modId        = modId       ; }
    public void setModDate      ( String modDate      ) { this.modDate      = modDate     ; }
	public void setExample(String[] example) { this.example = example;  }	
    
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return   "systemCode  " + systemCode  
               +",seqNo       " + seqNo       
               +",subject     " + subject     
               +",quizType    " + quizType    
               +",example1    " + example1    
               +",example2    " + example2    
               +",example3    " + example3    
               +",example4    " + example4    
               +",example5    " + example5    
               +",answer      " + answer      
               +",quizComment " + quizComment 
               +",quizCurriUrl" + quizCurriUrl
               +",quizLinkUrl " + quizLinkUrl 
               +",useYn       " + useYn       
               +",regId       " + regId       
               +",regDate     " + regDate     
               +",modId       " + modId       
               +",modDate     " + modDate  ;
    }
    
}
