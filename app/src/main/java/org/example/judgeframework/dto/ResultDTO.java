package org.example.judgeframework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResultDTO {
    int userId;
    int problemId;
    long submitId;
    int solvedTestNum;
    int totalTestNum;
}
