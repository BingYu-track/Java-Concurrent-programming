package part1.concurrent.theoretical.basis.chapter02;

/**
 * @version 1.0
 * @Description: 测试线程start()规则
 * @author: bingyu
 * @date: 2021/10/25
 */
public class Test {

    public static void main(String[] args) {
        Integer var;
        Thread B = new Thread(()->{
            // 主线程调用B.start()之前
            // 所有对共享变量的修改，此处皆可见
            //System.out.println(var);
        }); // 此处对共享变量var修改
        var = 77; // 主线程启动子线程前修改了var变量
        B.start();
    }
}
