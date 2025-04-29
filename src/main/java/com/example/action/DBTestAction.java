package com.example.action;

import com.example.db.DbJndiDataSource;
import com.example.form.DBTestForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBTestAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        DBTestForm dbTestForm = (DBTestForm) form; // 폼 객체 캐스팅

        try {
            conn = DbJndiDataSource.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT  user_id id, user_name  name FROM users limit 10");

            List<String[]> results = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                results.add(new String[]{id, name});
            }

            dbTestForm.setResults(results); // 결과를 폼 객체에 저장
            request.setAttribute("result", "DB 연결 및 테스트 성공");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result", "DB 연결 실패: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (conn != null) try { conn.close(); } catch(Exception e) {}
        }

        return mapping.findForward("success");
    }
}