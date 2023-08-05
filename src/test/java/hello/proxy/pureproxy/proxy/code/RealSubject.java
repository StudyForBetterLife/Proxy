package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealSubject implements Subject {

    /**
     * 여러번 조회해도 변하지 않은 데이터인 "data"를 반환하는 메소드이다.
     * <p>
     * 이 경우 이미 조회 결과의 데이터를 캐싱하는게 성능상 좋다.
     */
    @Override
    public String operation() {
        log.info("실제 객체 호출");
        sleep(1000);
        return "data";
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
