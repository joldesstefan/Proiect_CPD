package src.others_testing;

import src.comm.MessageHandleControlClass;
import src.comm.amqp.QueueClientModule;

public class QM2 {



        public static void main(String[] args) {
            QueueClientModule queueClientModule = new QueueClientModule("amqps://uolhucnk:4WCvaHBFOAdo6-U_wY-rCQIrhprzNYEk@squid.rmq.cloudamqp.com/uolhucnk", new MessageHandleControlClass());
            queueClientModule.initialize();
            queueClientModule.declareQueue("t1");
            //queueClientModule.subscribe("t1");

            QueueClientModule queueClientModule1 = new QueueClientModule("amqps://uolhucnk:4WCvaHBFOAdo6-U_wY-rCQIrhprzNYEk@squid.rmq.cloudamqp.com/uolhucnk", new MessageHandleControlClass());
            queueClientModule1.initialize();
            queueClientModule1.subscribe("t1");

            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");
            queueClientModule.publish("t1","ana");



        }



}
