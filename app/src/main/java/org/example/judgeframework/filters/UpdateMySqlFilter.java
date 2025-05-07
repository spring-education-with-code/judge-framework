package org.example.judgeframework.filters;

import org.example.App;
import org.example.judgeframework.JudgeApplication;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class UpdateMySqlFilter extends IntraProcessFilter{

    //mysql 에 채점 결과 업데이트
    @Override
    public void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
        updateSQL(Long.valueOf(requestDTO.get("submit_id")), Integer.valueOf(requestDTO.get("isCorrect")));
    }

    public void updateSQL(long submitId, int isCorrect){
        String updateSQL = "update problem_submit set is_correct = ? where problem_submit_id = ?";

        try(java.sql.Connection conn = DriverManager.getConnection(JudgeApplication.DB_URL, JudgeApplication.DB_USER, JudgeApplication.DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(updateSQL)){
            pstmt.setInt(1, isCorrect);
            pstmt.setLong(2, submitId);

            pstmt.executeUpdate();

        }catch(SQLException e){
            System.err.println("DB 갱신 중 오류 발생: " + e.getMessage());
        }
    }
}
