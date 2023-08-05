package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute(); // 1s
        client.execute(); // 1s
        client.execute(); // 1s
    }

    /**
     * client -> cacheProxy -> realSubject 런타임 객체 의존 관계 형성
     */
    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        // cacheProxy가 realSubject를 참조하는 런타임 객체 의존관계 형성
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute(); // 1s & caching
        client.execute(); // 0s
        client.execute(); // 0s
    }
}
