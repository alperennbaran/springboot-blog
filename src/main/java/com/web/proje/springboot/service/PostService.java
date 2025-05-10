package com.web.proje.springboot.service;

import com.web.proje.springboot.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<PostDto> findAllPosts();
    void createPost(PostDto postDto);
}
