package operation;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
* �����ȷ��� �㷨 FCFS
* ����ռʽ
*/
public class FCFS {

    static Scanner cin = new Scanner(System.in);

    /** ���̿��ƿ� */
    static class PCB implements Comparable<PCB>{
        int id; // ����id
        int arriveTime; // ����ʱ��
       int runTime; // ����ʱ��

       int turnAroundTime; // ��תʱ��
      
       PCB(int id, int arriveTime, int runTime){
            this.id = id;
            this.arriveTime = arriveTime;
            this.runTime = runTime;
        }

        @Override
        public int compareTo(PCB o) { // ���� ����ʱ�� �����������
           return  this.arriveTime - o.arriveTime;
        }
    }

    static PCB[] pcbs;
    /** �������� */
    static Queue<PCB> queue = new PriorityQueue<>();

    /** ��ʼ�� PCB ��Ϣ */
    static void initPCB(){
        System.out.print("����������� ");
        int num = cin.nextInt();
        pcbs = new PCB[num+1];
        System.out.println("���뵽��ʱ�䣬 ����ʱ��");
        for(int i = 1; i <= num; i++) {
            System.out.print("����" + i + ":");
            pcbs[i] = new PCB(i, cin.nextInt(), cin.nextInt());
            queue.offer(pcbs[i]);
        }
    }

    /** �������� */
    static void run() {
        int currentTime = 0; // ��ǰʱ��
       if(!queue.isEmpty()){
            currentTime = queue.peek().arriveTime;
        }
        while (true) {
            if(queue.isEmpty()){
                System.out.println("��ǰ���н������н���");
                break;
            }else{ // ���̽��� ���������
               PCB runPCB = queue.poll(); // 1. ����������
               System.out.println("��ǰʱ�䣺" + currentTime);
                System.out.println("����" + runPCB.id + "��ʼ����------");
             
               currentTime += runPCB.runTime; // 2. ���봦�������
               System.out.println("��ǰʱ�䣺" + currentTime);
                System.out.println("����" + runPCB.id + "���н���------");
                runPCB.turnAroundTime = currentTime - runPCB.arriveTime; // ������תʱ��
           }
        }
    }

    public static void main(String[] args) {
        initPCB();
        System.out.println("-----�������ʼ����-----");
        run();
        System.out.println("-----��������н���-----");
        showTurnAroundTime();
    }

    // ��תʱ��
   private static void showTurnAroundTime() {
        double averageT = 0;
        double averageWTAT = 0;
      
        System.out.println("����\t  ��תʱ��\t  ��Ȩ��תʱ��\t");
        for (int i = 1; i < pcbs.length; i ++) {
            int turnAroundTime = pcbs[i].turnAroundTime;
            double weightTurnAroundTime = turnAroundTime*1.0/pcbs[i].runTime;
         
            System.out.printf("%d\t  %d\t  %.2f\n" ,i , turnAroundTime,  weightTurnAroundTime);
            averageT += turnAroundTime;
            averageWTAT += weightTurnAroundTime;
          
        }
        averageT /= pcbs.length-1;
        averageWTAT /= pcbs.length-1;
      
        System.out.println("ƽ����תʱ�䣺" + averageT);
        System.out.println("ƽ����Ȩ��תʱ�䣺" + averageWTAT);
       
    }

}

