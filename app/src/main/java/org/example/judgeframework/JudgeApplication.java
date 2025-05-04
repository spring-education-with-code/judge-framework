package org.example.judgeframework;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.tools.javac.Main;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.example.judgeframework.process.OneJudgeProcess;
import org.example.judgeframework.proxy.ProcessChainProxy;
import org.example.judgeframework.setting.ApplicationContextSetting;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

public class JudgeApplication {

    //간이 빈 팩토리
    public static Map<String, Object> beanFactory = new ConcurrentHashMap<>();

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

        //3) asm 메타데이터 스캔 & bean 을 map에 등록
        applicationContext();

        //4) 등록된 bean 중 OneJudgeProcess 형태 인 것을 processChainProxy 의 List<OneJudgeProcess> judgeProcesses 에 등록
        ProcessChainProxy processChainProxy = new ProcessChainProxy();
        setProcessChainProxy(processChainProxy);

        //5) rabbitmq message 받을 시
        // delegatingProcessProxy.createDeliverCallback 가 실행되게 함 (진입점)
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("spring.education.queue", true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicConsume("spring.education.queue", true, processChainProxy.createDeliverCallback(), consumerTag -> { });
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

    public static void applicationContext(){
        //TODO - 어플리케이션 컨텍스트 초기화 과정 고도화 필요.
        //TODO - spring은 ASM으로 메타데이터만 스캔해 BeanDefinition을 등록하고, 어플리케이션 컨텍스트가 초기화되는 refresh() 시점에
        //TODO - 실제 메서드 호출 -> 인스턴스 생성 -> 싱글턴 맵에 저장

        ApplicationContextSetting applicationContextSetting = new ApplicationContextSetting();

        //1. asm 바이트 코드 파싱 해서
        //2. 대상되는 process 빈을 생성해서
        //3. beanFactory에 등록!!
        String rootPkg = Main.class.getPackage().getName();
        beanFactory = applicationContextSetting.componentScanAndMakeBean(rootPkg);
    }

    public static void setProcessChainProxy(ProcessChainProxy processChainProxy){
        for (Map.Entry<String, Object> entry : beanFactory.entrySet()) {
            Object bean = entry.getValue();
            if (bean instanceof OneJudgeProcess) {
                processChainProxy.judgeProcesses.add((OneJudgeProcess) bean);
            }
        }
    }
}
