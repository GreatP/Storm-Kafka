package cn.ac.sict.ljc.kafka_producer_sensor;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SensorProducer extends Thread {

	private static final String partitioner = "cn.ac.sict.kafka_producer_sensor.SimplePartitioner";
	private static final String[] Topic = {"ljc_input_sensor_temper", "ljc_input_sensor_pressure"};
	private static final int[] min = {30, 3}, max = {70, 5};
	private final int NO; // 组号, 用于分区

	private final Producer<String, String> producer;

	public SensorProducer(String kafkaStr, int no) {
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaStr);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("partitioner.class", partitioner);
		this.NO = no;
		this.producer = new KafkaProducer<String, String>(props);
	}

	@Override
	public void run() {
		Random rnd = new Random(System.nanoTime());
		String key = (NO) + "", msg = null; // key 组号, 用于分区
		try {
			double[] randValue = {min[0] + rnd.nextInt((max[0] - min[0]) * 1000) / 1000.0, min[1] + rnd.nextInt((max[1] - min[1]) * 1000) / 1000.0};
			double value;
			int direct = -1;

			// int i = 100;
			// long startTime = System.nanoTime(); // 获取开始时间
			// while ((i--) != 0) {
			while (true) {
				for (int type = 0; type < 2; type++) { // 0 for temper, 1 for pressure
					if (rnd.nextInt() % 2 == 0)
						direct *= -1;
					randValue[type] = randValue[type] + direct * rnd.nextInt(1000) * rnd.nextGaussian() / 10000; // 下一个尽量连续的随机数
					while (randValue[type] > max[type])
						randValue[type] -= rnd.nextDouble() / 1000;
					while (randValue[type] < min[type])
						randValue[type] += rnd.nextDouble() / 1000;
					value = (int) (randValue[type] * 10000) / 10000.0; // 精度为 4 位小数
					// time:type:value
					msg = (new Date().getTime() * 1000 + System.nanoTime() % 1000000 / 1000) + ":" + type + ":" + value;
					this.producer.send(new ProducerRecord<String, String>(Topic[type], key, msg));
					System.out.println(msg);
				}

				// sleep 为便于观察
				try {
					Thread.sleep(999);
				} catch (InterruptedException e) {
				}
			}
			// long endTime = System.nanoTime(); // 获取结束时间
			// System.out.println((endTime - startTime) / 1000000000.0);
		} finally {
			this.producer.close();
		}
	}

}
