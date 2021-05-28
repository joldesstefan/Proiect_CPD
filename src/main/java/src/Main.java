package src;

import src.client.Client;
import src.proxy.ParticipantDataBlock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String [] args) {
        List<ParticipantDataBlock> participantDataBlockList = new LinkedList<>();
        ParticipantDataBlock participantDataBlock = new ParticipantDataBlock();
        participantDataBlock.setId(1);
        participantDataBlock.setSocketServerAddress("localhost");
        participantDataBlock.setSocketServerPort(1111);
        participantDataBlockList.add(participantDataBlock);

        participantDataBlock = new ParticipantDataBlock();
        participantDataBlock.setId(2);
        participantDataBlock.setSocketServerAddress("localhost");
        participantDataBlock.setSocketServerPort(2222);
        participantDataBlockList.add(participantDataBlock);


        participantDataBlock = new ParticipantDataBlock();
        participantDataBlock.setId(3);
        participantDataBlock.setSocketServerAddress("localhost");
        participantDataBlock.setSocketServerPort(3333);
        participantDataBlockList.add(participantDataBlock);


        participantDataBlock = new ParticipantDataBlock();
        participantDataBlock.setId(4);
        participantDataBlock.setSocketServerAddress("localhost");
        participantDataBlock.setSocketServerPort(4444);
        participantDataBlockList.add(participantDataBlock);


       List<List<String>> queues = new ArrayList<>();
        List<String> queues1 = new ArrayList<>();
        queues1.add("Q1");
        queues1.add("Q2");
        queues1.add("Q3");
        queues1.add("Q4");
        queues1.add("Q5");

        queues.add(queues1);

        queues1 = new ArrayList<>();
        queues1.add("Q1");
        queues1.add("Q2");
        queues.add(queues1);

        queues1 = new ArrayList<>();
        queues1.add("Q2");
        queues1.add("Q3");
        queues1.add("Q4");
        queues.add(queues1);

        queues1 = new ArrayList<>();
        queues1.add("Q1");
        queues1.add("Q5");
        queues.add(queues1);


        int i = 0;
        int n = participantDataBlockList.size();
        ParticipantDataBlock me , left , right;
        while(i<n)
        {

             me = participantDataBlockList.get(i);
             if(i==0)
             {
                 left = participantDataBlockList.get(n-1);
             }
             else
             {
                 left = participantDataBlockList.get(i-1);

             }
            if(i==n-1)
            {
                right = participantDataBlockList.get(0);
            }
            else
            {
                right = participantDataBlockList.get(i+1);

            }
            if( i==0) {
                Client client = new Client(String.valueOf(participantDataBlockList.get(i).getId()), queues.get(i), me, left, right, true, "ana");
            }
            else
            {
                Client client = new Client(String.valueOf(participantDataBlockList.get(i).getId()), queues.get(i), me, left, right, false, "ana");

            }
            i++;
        }
    }

}
