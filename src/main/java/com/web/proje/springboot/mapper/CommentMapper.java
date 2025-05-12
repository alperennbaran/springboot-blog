package com.web.proje.springboot.mapper;

import com.web.proje.springboot.dto.CommentDto;
import com.web.proje.springboot.entity.Comment;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                //Kullanıcı giriş yaparak yorum yaptığı için getUser() üzerinden name,email ve userID aldık.
                .name(comment.getUser().getName())
                .email(comment.getUser().getEmail())
                .content(comment.getContent())
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())
                .userId(comment.getUser().getId())
                .profileImage(comment.getUser().getProfileImage())
                .build();
    }


    public static Comment toComment(CommentDto commentDto){
        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .createdOn(commentDto.getCreatedOn())
                .updatedOn(commentDto.getUpdatedOn())
                .build();
    }
}
