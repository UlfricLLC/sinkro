package com.ulfric.sinkro;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ulfric.acrodb.Document;
import com.ulfric.acrodb.DocumentStore;
import com.ulfric.acrodb.SkeletalConcurrentSaveable;

public final class Sink<T> extends SkeletalConcurrentSaveable {

	private final Document document;
	private final List<T> entries = new ArrayList<>();

	public Sink(DocumentStore documents, Class<T> type) {
		this(Objects.requireNonNull(documents.openDocument("sinkro"), "documents"), type);
	}

	public Sink(Document document, Class<T> type) {
		Objects.requireNonNull(document, "document");

		this.document = document;

		document.onSave(this::persist);

		Entries<T> entries = document.read(entriesOfT(type));
		if (entries.entries != null) {
			this.entries.addAll(entries.entries);
		}
	}

	public void add(T element) {
		Objects.requireNonNull(element, "element");

		writeLocked(() -> entries.add(element));
	}

	public List<T> read() {
		return readLocked(() -> new ArrayList<>(entries));
	}

	@Override
	protected void onConcurrentSave() {
		super.onConcurrentSave();

		persist();
		document.save();
	}

	private void persist() {
		Entries<T> entries = new Entries<>();
		entries.entries = this.entries;
		document.write(entries);
	}

	private Type entriesOfT(Class<T> type) {
		return new TypeWithParameters(Entries.class, type);
	}

	@Override
	public void lockRead() {
		document.lockRead();
	}

	@Override
	public void lockWrite() {
		document.lockWrite();
	}

	@Override
	public void unlockRead() {
		document.unlockRead();
	}

	@Override
	public void unlockWrite() {
		document.unlockWrite();
	}

	private static final class Entries<T> {
		List<T> entries;
	}

}
