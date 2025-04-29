package mediopia3.dbm;


public class CMMain
{

 public CMMain(String id)
 {
     cmid = new String();
     GeneralCnt = 0;
     LifecycleCnt = 0;
     MetametadataCnt = 0;
     TechnicalCnt = 0;
     EducationalCnt = 0;
     RightsCnt = 0;
     RelationCnt = 0;
     AnnotationCnt = 0;
     ClassificationCnt = 0;
     cmid = id;
 }

 public void setCount(char ch)
 {
     switch(ch)
     {
     case 65: // 'A'
         GeneralCnt++;
         break;

     case 66: // 'B'
         LifecycleCnt++;
         break;

     case 67: // 'C'
         MetametadataCnt++;
         break;

     case 68: // 'D'
         TechnicalCnt++;
         break;

     case 69: // 'E'
         EducationalCnt++;
         break;

     case 70: // 'F'
         RightsCnt++;
         break;

     case 71: // 'G'
         RelationCnt++;
         break;

     case 72: // 'H'
         AnnotationCnt++;
         break;

     case 73: // 'I'
         ClassificationCnt++;
         break;
     }
 }

 public String getCMID()
 {
     return cmid;
 }

 public int getCount(char ch)
 {
     switch(ch)
     {
     case 65: // 'A'
         return GeneralCnt;

     case 66: // 'B'
         return LifecycleCnt;

     case 67: // 'C'
         return MetametadataCnt;

     case 68: // 'D'
         return TechnicalCnt;

     case 69: // 'E'
         return EducationalCnt;

     case 70: // 'F'
         return RightsCnt;

     case 71: // 'G'
         return RelationCnt;

     case 72: // 'H'
         return AnnotationCnt;

     case 73: // 'I'
         return ClassificationCnt;
     }
     return -1;
 }

 public String getStrSchema()
 {
     return "cmid, GeneralCnt, LifecycleCnt, MetametadataCnt, TechnicalCnt, EducationalCnt, RightsCnt, RelationCnt, AnnotationCnt, ClassificationCnt";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(cmid).append("', ").append(GeneralCnt).append(", ").append(LifecycleCnt).append(", ").append(MetametadataCnt).append(", ").append(TechnicalCnt).append(", ").append(EducationalCnt).append(", ").append(RightsCnt).append(", ").append(RelationCnt).append(", ").append(AnnotationCnt).append(", ").append(ClassificationCnt).append(")")));
 }

 private String cmid;
 private int GeneralCnt;
 private int LifecycleCnt;
 private int MetametadataCnt;
 private int TechnicalCnt;
 private int EducationalCnt;
 private int RightsCnt;
 private int RelationCnt;
 private int AnnotationCnt;
 private int ClassificationCnt;
}
