package com.web.proje.springboot.service.Impl;

import com.web.proje.springboot.dto.PostDto;
import com.web.proje.springboot.entity.Post;
import com.web.proje.springboot.entity.User;
import com.web.proje.springboot.mapper.PostMapper;
import com.web.proje.springboot.repository.PostRepository;
import com.web.proje.springboot.repository.UserRepository;
import com.web.proje.springboot.service.PostService;
import com.web.proje.springboot.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = postList.stream().map(PostMapper::toPostDto) // (post) -> PostMapper.toPostDto(post) ' un yerine PostMapper::toPostDto kullandım çünkü daha kolay
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> findPostsByUser() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Post> posts = postRepository.findPostsByUser(userId);
        return posts.stream()
                .map(PostMapper::toPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User user = userRepository.findByEmail(email);
        Post post = PostMapper.toPost(postDto);
        post.setCreatedBy(user);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postID) {
        Post post = postRepository.findById(postID).get();
        return PostMapper.toPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Post post = PostMapper.toPost(postDto);
        post.setCreatedBy(createdBy);
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postID) {
        postRepository.deleteById(postID);
    }

    @Override
    public PostDto findPostByUrl(String postUrl) {
        Post post = postRepository.findByUrl(postUrl).get();
        return PostMapper.toPostDto(post);
    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream()
                .map(PostMapper::toPostDto)
                .collect(Collectors.toList());
    }
}
