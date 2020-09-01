package system_design.singleton;

public class Solution{

    // 1. 私有单例对象，禁止通过 类名.属性 访问
    // 2. 将其声明为静态成员，使得在静态方法中得以访问
    // 3. 使用volatile关键字，消除指令重排序的影响
    private static volatile Solution instance;

    // 1. 私有构造函数
    private Solution(){

    }

    // 静态方法，返回单例对象。双重检查+synchroinzed锁，保证多线程下instance对象唯一
    public static Solution getInstance(){
        if(instance == null){
            synchronized(Solution.class){
                if(instance == null){
                    // 多线程并发访问，只会有一个线程初始化成功
                    instance = new Solution();
                }
            }
        }
        return instance;

    }
}