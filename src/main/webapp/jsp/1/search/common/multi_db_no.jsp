<%!
	public int getMultiDBNo(String dbId)
    {
        int dbNo = 0;

        if (dbId.equalsIgnoreCase("lms_ebook"))
        {
            dbNo = 21;
        }
        else if (dbId.equalsIgnoreCase("lms_course"))
        {
            dbNo = 22;
        }
        return dbNo;
    }
/*
    public String getMultiDBName(int dbNo)
    {
        String dbName = "";

        if (dbNo==1)
        {
            dbName = "KTSET 논문DB 1";
        }
        else if (dbNo==2)
        {
            dbName = "KTSET 논문DB 2";
        }
        return dbName;
    }
*/
%>