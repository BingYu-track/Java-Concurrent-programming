package part1.concurrent.theoretical.basis.chapter02;

/**
 * @version 1.0
 * @Description: Happens-Before 规则一: 程序顺序性规则
 *                              规则二：volatile变量规则
 *                              规则三：传递性
 * @author: bingyu
 * @date: 2021/10/25
 */
class VolatileExample {
    int x = 0;
    volatile boolean v = false;
    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v == true) {
            // 这里x会是多少呢？
        }
    }

    /**
     * 这条规则是指在一个线程中，按照程序顺序，前面的操作 Happens-Before于后续的任意操作。这还是比较容易理解的，比如刚才那段示例代码，
     * 按照程序的顺序，第 13 行代码 “x = 42;” Happens-Before 于第 14 行代码 “v = true;”，这就是规则 1 的内容，也比较符合单线程
     * 里面的思维：程序前面对某个变量的修改一定是对后续操作可见的
     * @param args
     */
    public static void main(String[] args) {
        VolatileExample ve = new VolatileExample();
        Thread th1 = new Thread(()->{
            ve.writer();
            System.out.println(ve.x);
        });

        Thread th2 = new Thread(()->{
            ve.reader();
            System.out.println(ve.x);
        });

        th1.start();
        th2.start(); //如果在低于 1.5 版本上运行，x 可能是 42，也有可能是 0；如果在 1.5 以上的版本上运行，x 就是等于 42
    }
}
