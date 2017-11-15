package com.example.rosana.booksapp.dummy;

import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.model.NovelBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Content {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Novel> ITEMS = new ArrayList<Novel>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Novel> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 20;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createNovel());
        }
    }

    private static void addItem(Novel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static Novel createNovel() {
        return new NovelBuilder().withDefaults().build();

    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
