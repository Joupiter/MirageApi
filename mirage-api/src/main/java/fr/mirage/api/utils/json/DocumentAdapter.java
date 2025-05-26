package fr.mirage.api.utils.json;

import org.bson.Document;

import java.util.concurrent.CompletableFuture;

public interface DocumentAdapter<T> {

    Document toDocument(T type);
    T fromDocument(Document document);

    default CompletableFuture<Document> toDocumentAsync(T type) {
        return CompletableFuture.supplyAsync(() -> toDocument(type));
    }

    default CompletableFuture<T> fromDocumentAsync(Document document) {
        return CompletableFuture.supplyAsync(() -> fromDocument(document));
    }

}