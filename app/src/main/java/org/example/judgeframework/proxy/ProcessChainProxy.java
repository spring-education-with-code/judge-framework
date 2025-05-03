package org.example.judgeframework.proxy;

import com.rabbitmq.client.DeliverCallback;
import org.example.judgeframework.process.OneJudgeProcess;

import java.util.List;

//최초 진입점.
//RabbitMQ 메세지를 받아서 어떤 채점 process 한테 넘길 지를 결정한다.
public class ProcessChainProxy {

    private List<OneJudgeProcess> judgeProcesses;

    public DeliverCallback createDeliverCallback(){
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            //processChain 고르고 doFilter 한다
            //* 지금은 무조건 한 개의 process 로 고정되게 함.
            OneJudgeProcess nowProcess = judgeProcesses.get(0);


        };
    }

}
