package part1.concurrent.theoretical.basis.chapter01;

/**
 * @version 1.0
 * @Description: 并发编程中的编译优化带来的有序性问题：双重检查锁问题(重点)
 * @author: bingyu
 * @date: 2021/10/20
 */

public class Singleton {
    static volatile Singleton instance;

    static Singleton getInstance() throws InterruptedException {
        //第一次检查(注意：如果只有第一次检查，那么线程1在执行期间，线程2可能已经进入了第一个if里，
        //线程1执行完后，线程2就可以继续创建对象，导致单实例失败,因此里面需要进行第二次检查)
        if (instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) { //第二次检查
                    instance = new Singleton(); //TODO:指令重排序可能导致实例为null
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<100_000;i++) { //模拟多线程高并发
            new Thread(()->{
                try {
                    System.out.println(Singleton.getInstance().hashCode()); //成功打印的都是同样的hashCode
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}