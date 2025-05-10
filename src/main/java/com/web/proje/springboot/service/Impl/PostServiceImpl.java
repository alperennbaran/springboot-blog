package com.web.proje.springboot.service.Impl;

import com.web.proje.springboot.dto.PostDto;
import com.web.proje.springboot.entity.Post;
import com.web.proje.springboot.mapper.PostMapper;
import com.web.proje.springboot.repository.PostRepository;
import com.web.proje.springboot.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = postList.stream().map(PostMapper::toPostDto) // (post) -> PostMapper.toPostDto(post) ' un yerine PostMapper::toPostDto kullandım çünkü daha kolay
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public void createPost(PostDto postDto) {
        Post post = PostMapper.toPost(postDto);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postID) {
        Post post = postRepository.findById(postID).get();
        return PostMapper.toPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = PostMapper.toPost(postDto);
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
