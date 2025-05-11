package com.web.proje.springboot.service.Impl;

import com.web.proje.springboot.dto.CommentDto;
import com.web.proje.springboot.entity.Comment;
import com.web.proje.springboot.entity.Post;
import com.web.proje.springboot.entity.User;
import com.web.proje.springboot.mapper.CommentMapper;
import com.web.proje.springboot.repository.CommentRepository;
import com.web.proje.springboot.repository.PostRepository;
import com.web.proje.springboot.repository.UserRepository;
import com.web.proje.springboot.service.CommentService;

import com.web.proje.springboot.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    // Construction Injection --> Autowired yerine yani field injenction yerine kullanıldı.
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {
        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.toComment(commentDto);
        comment.setPost(post);
        // aktif kullanıcıyı al ve yoruma ekle
        String email = SecurityUtils.getCurrentUser().getUsername();
        User user = userRepository.findByEmail(email);
        comment.setUser(user);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findCommentsByPost() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Comment> comments = commentRepository.findCommentsByPost(userId);
        return comments.stream()
                .map((comment) -> CommentMapper.toCommentDto(comment))
                .collect(Collectors.toList());
    }
}
