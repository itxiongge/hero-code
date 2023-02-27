package com.hero.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 自定义监听器：用于接收消息对象order.A的发送的消息
 * 必须要注入Spring的ioc容器中
 */
@Component
public class CustomAckConsumerListener implements ChannelAwareMessageListener {
    /**
     * 自定义监听的监听方法 ，作用：监听到消息队列发送过来消息，然后执行当前方法
     * 三要素：
     *      监听对象：消息队列
     *      事件源：消息
     *      执行操作：执行的操作onMessage()
     * @param message 消息内容
     * @param channel 频道
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取投递标签
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //1、获取消息队列中消息
        byte[] messageBody = message.getBody();
        String msg = new String(messageBody, "utf-8");
        //2、执行业务逻辑
        System.out.println("模拟一下，执行正常的业务逻辑，消息内容："+msg);
        try {
            /**
             * 模拟异常情况
             */
//            if (msg.contains("苹果")) {
//                throw new RuntimeException("苹果手机不能卖！！！");
//            }
            //当前线程休眠3秒，演示消费端限流的效果
            TimeUnit.SECONDS.sleep(3);
            //4、正常，签收消息
            /**
             * 签收消息
             * 参数1：deliveryTag，投递标签
             * 参数2：multiple，是否批量签收，如果true，将当前队列对应连接中的所有消息一次性签收，如果false，只签收当前投递标签中的消息
             */

            channel.basicAck(deliveryTag,false);
            System.out.println("手动正常签收");
        } catch (Exception ex){
            //5、异常，拒签消息，消息重回队列
            /**
             * 参数1：deliveryTag，投递标签
             * 参数2：multiple，是否批量签收
             * 参数3：re queue，是否重回队列，如果不回队列，消息真的就应该让它消失吗？？？
             */
            channel.basicNack(deliveryTag,false,true);
            System.out.println("手动拒签签收，异常信息："+ex);
        }

    }
}
