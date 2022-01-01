package com.example.mdbspringboot.utils;

import org.springframework.stereotype.Service;

import com.example.mdbspringboot.model.Book;
import com.google.gson.Gson;
import com.rabbitmq.client.*;

@Service
public class Utils {
	private Gson gson = new Gson();

	public void sendData(Book obj) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (com.rabbitmq.client.Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			channel.queueDeclare("save_book", false, false, false, null);
			String jsonmessage = gson.toJson(obj);
			channel.basicPublish("", "save_book", null, jsonmessage.getBytes());
			System.out.println(" [x] Sent '" + jsonmessage + "'");
		}
	}

	public void deleteData(int id) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (com.rabbitmq.client.Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			channel.queueDeclare("delete_book", false, false, false, null);
			String jsonmessage = id + "";
			channel.basicPublish("", "delete_book", null, jsonmessage.getBytes());
			System.out.println(" [x] Sent '" + jsonmessage + "'");
		}
	}

	public void consumeData() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		com.rabbitmq.client.Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare("save_book", false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("message   " + message);
			Book book = gson.fromJson(message, Book.class);
			System.out.println(book);

		};

		channel.basicConsume("save_book", true, deliverCallback, consumerTag -> {
		});

		Channel channel2 = connection.createChannel();

		channel2.queueDeclare("delete_book", false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("message   " + message); // '5'

		};
		channel2.basicConsume("delete_book", true, deliverCallback2, consumerTag -> {
		});

	}

}
