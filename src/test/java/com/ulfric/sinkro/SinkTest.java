package com.ulfric.sinkro;

import java.nio.file.FileSystem;

import com.google.common.truth.Truth;
import com.ulfric.acrodb.Bucket;
import com.ulfric.acrodb.DocumentStore;
import com.ulfric.allsystemsgo.AllSystemsTest;

public class SinkTest {

	@AllSystemsTest
	void testAdding(FileSystem fileSystem) {
		Sink<Message> sink = sink(fileSystem);

		Message message = new Message();
		message.message = "hello";
		sink.add(message);

		Truth.assertThat(sink.read().get(0).message).isEqualTo("hello");
	}

	@AllSystemsTest
	void testLoadingSinkWithExistingData(FileSystem fileSystem) {
		Sink<Message> sink = sink(fileSystem);
		Message message = new Message();
		message.message = "hello";
		sink.add(message);
		sink.save();

		sink = sink(fileSystem);

		Truth.assertThat(sink.read().get(0).message).isEqualTo("hello");
	}

	private Sink<Message> sink(FileSystem fileSystem) {
		return new Sink<>(store(fileSystem), Message.class);
	}

	private DocumentStore store(FileSystem fileSystem) {
		return new Bucket(fileSystem.getPath("sinkro"));
	}

	static class Message {
		String message;
	}

}
