package part1.concurrent.theoretical.basis.chapter01;

/**
 * @version 1.0
 * @Description: 验证多核场景下并发编程中的可见性问题
 * @author: bingyu
 * @date: 2021/10/19
 */

public class Test {

    private long count = 0;

    //count自增一万次
    private void add10K() {
        int idx = 0;
        while(idx++ < 10000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        final Test test = new Test();
        // 创建两个线程，执行add()操作
        Thread th1 = new Thread(()->{
            test.add10K();
        });
        Thread th2 = new Thread(()->{
            test.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
        return test.count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Test.calc()); //12379
    }
    /**
     总结：calc()方法的执行结果是个 10000 到 20000 之间的随机数，为什么？
     我们假设线程 A 和线程 B 同时开始执行，那么第一次都会将 count=0 读到各自的 CPU 缓存里，执行完 count+=1 之后，各自 CPU 缓存里的值都是1,
     同时写入内存后，我们会发现内存中是1，而不是我们期望的2。之后由于各自的 CPU缓存里都有了count 的值，两个线程都是基于 CPU 缓存里的count值
     来计算，所以导致最终 count 的值都是小于 20000 的。这就是缓存的可见性问题！
     */

}