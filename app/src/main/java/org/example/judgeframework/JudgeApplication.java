package org.example.judgeframework;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.example.judgeframework.proxy.ProcessChainProxy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class JudgeApplication {

    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;

    //redis 관련 변수
    public static RedisClient redisClient;
    public static StatefulRedisConnection<String, String> redisConnection;


    public static void run(){
        // 구 프로젝트의 Main 부분. rabbitMQ를 연결하거나 redis 연결하거나...
        //1) 환경 변수 설정 하기 (DB_URL, DB_USER, DB_PASSWORD 초기화)
        setProperties();

        //2) redis 에 연결
        connectRedis();

        //

        //#) rabbitmq message 받을 시
        // delegatingProcessProxy.createDeliverCallback 가 실행되게 함 (진입점)
        ProcessChainProxy delegatingProcessProxy = new ProcessChainProxy();

        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("spring.education.queue", true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicConsume("spring.education.queue", true, delegatingProcessProxy.createDeliverCallback(), consumerTag -> { });
        }catch(IOException e){
            e.printStackTrace();
        }catch(TimeoutException e){
            e.printStackTrace();
        }
    }


    public static void setProperties(){
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("config.properties 파일을 읽을 수 없습니다: " + e.getMessage());
            return;
        }

        JudgeApplication.DB_URL = props.getProperty("db.url");
        JudgeApplication.DB_USER = props.getProperty("db.user");
        JudgeApplication.DB_PASSWORD = props.getProperty("db.password");
    }

    public static void connectRedis(){
        JudgeApplication.redisClient = redisClient.create("redis://localhost:6379");
        JudgeApplication.redisConnection = redisClient.connect();
    }
}
