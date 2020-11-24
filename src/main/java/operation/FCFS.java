package operation;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
* 先来先服务 算法 FCFS
* 非抢占式
*/
public class FCFS {

    static Scanner cin = new Scanner(System.in);

    /** 进程控制块 */
    static class PCB implements Comparable<PCB>{
        int id; // 进程id
        int arriveTime; // 到达时间
       int runTime; // 运行时间

       int turnAroundTime; // 周转时间
      
       PCB(int id, int arriveTime, int runTime){
            this.id = id;
            this.arriveTime = arriveTime;
            this.runTime = runTime;
        }

        @Override
        public int compareTo(PCB o) { // 按照 到达时间 进入就绪队列
           return  this.arriveTime - o.arriveTime;
        }
    }

    static PCB[] pcbs;
    /** 就绪队列 */
    static Queue<PCB> queue = new PriorityQueue<>();

    /** 初始化 PCB 信息 */
    static void initPCB(){
        System.out.print("输入进程数： ");
        int num = cin.nextInt();
        pcbs = new PCB[num+1];
        System.out.println("输入到达时间， 运行时间");
        for(int i = 1; i <= num; i++) {
            System.out.print("进程" + i + ":");
            pcbs[i] = new PCB(i, cin.nextInt(), cin.nextInt());
            queue.offer(pcbs[i]);
        }
    }

    /** 处理及运行 */
    static void run() {
        int currentTime = 0; // 当前时间
       if(!queue.isEmpty()){
            currentTime = queue.peek().arriveTime;
        }
        while (true) {
            if(queue.isEmpty()){
                System.out.println("当前所有进程运行结束");
                break;
            }else{ // 进程进入 处理机运行
               PCB runPCB = queue.poll(); // 1. 出就绪队列
               System.out.println("当前时间：" + currentTime);
                System.out.println("进程" + runPCB.id + "开始运行------");
             
               currentTime += runPCB.runTime; // 2. 进入处理机运行
               System.out.println("当前时间：" + currentTime);
                System.out.println("进程" + runPCB.id + "运行结束------");
                runPCB.turnAroundTime = currentTime - runPCB.arriveTime; // 计算周转时间
           }
        }
    }

    public static void main(String[] args) {
        initPCB();
        System.out.println("-----处理机开始运行-----");
        run();
        System.out.println("-----处理机运行结束-----");
        showTurnAroundTime();
    }

    // 周转时间
   private static void showTurnAroundTime() {
        double averageT = 0;
        double averageWTAT = 0;
      
        System.out.println("进程\t  周转时间\t  带权周转时间\t");
        for (int i = 1; i < pcbs.length; i ++) {
            int turnAroundTime = pcbs[i].turnAroundTime;
            double weightTurnAroundTime = turnAroundTime*1.0/pcbs[i].runTime;
         
            System.out.printf("%d\t  %d\t  %.2f\n" ,i , turnAroundTime,  weightTurnAroundTime);
            averageT += turnAroundTime;
            averageWTAT += weightTurnAroundTime;
          
        }
        averageT /= pcbs.length-1;
        averageWTAT /= pcbs.length-1;
      
        System.out.println("平均周转时间：" + averageT);
        System.out.println("平均带权周转时间：" + averageWTAT);
       
    }

}

