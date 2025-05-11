package com.web.proje.springboot.mapper;

import com.web.proje.springboot.dto.PostDto;
import com.web.proje.springboot.entity.Post;

import java.util.stream.Collectors;

public class PostMapper {
    public static PostDto toPostDto(Post post){
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .content(post.getContent())
                .shortDescription(post.getShortDescription())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .comments(post.getComments().stream()
                        .map(CommentMapper::toCommentDto).
                        collect(Collectors.toSet())
                )
                .build();
        return postDto;
    }

    // neden burada .comments yok?
    public static Post toPost(PostDto postDto){
        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .url(postDto.getUrl())
                .content(postDto.getContent())
                .shortDescription(postDto.getShortDescription())
                .createdOn(postDto.getCreatedOn())
                .updatedOn(postDto.getUpdatedOn())
                .build();
        return post;
    }
}
