package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    AtomicLong counter = new AtomicLong();
    public static final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Long, Post> all() {
        if (posts.isEmpty()) {
            throw new NotFoundException("Empty list");
        }
        return posts;
    }

    public Optional<Post> getById(long id) {
        if (posts.containsKey(id)) {
            return Optional.ofNullable(posts.get(id));
        }
        throw new NotFoundException("Post not found");
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            posts.put(counter.incrementAndGet(), post);
            return post;
        } else {
            if (!posts.containsKey(post.getId())) {
                throw new NotFoundException("Post");
            } else {
                posts.put(post.getId(), post);
            }
        }
        return post;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else throw new NotFoundException("Post doesn't exist");
    }
}
