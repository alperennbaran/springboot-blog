package com.web.proje.springboot.controller;

import com.web.proje.springboot.dto.CommentDto;
import com.web.proje.springboot.dto.PostDto;
import com.web.proje.springboot.service.CommentService;
import com.web.proje.springboot.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PostController {
    private PostService postService;
    @Autowired
    private CommentService commentService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/admin/posts")
    public String postList(Model model) {
        List<PostDto> postList = postService.findAllPosts();
        model.addAttribute("postList", postList);
        return "/admin/posts";
    }

    @GetMapping("/admin/posts/comments")
    public String postComments(Model model){
        List<CommentDto> commentList = commentService.findAllComments();
        model.addAttribute("commentList", commentList);
        return "/admin/comments";
    }

    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }

    @GetMapping("admin/posts/newpost")
    public String newPostForm(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "/admin/create_post";
    }

    @PostMapping("admin/posts")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("post", postDto);
            return "/admin/create_post";
        }
        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postID}/update")
    public String editPost(@PathVariable("postID") Long postID, Model model) {
        PostDto postDto = postService.findPostById(postID);
        model.addAttribute("post", postDto);
        return "/admin/update_post";
    }

    @PostMapping("/admin/posts/{postID}")
    public String updatePost(@PathVariable("postID") Long postID,
                             @Valid @ModelAttribute("post") PostDto post,
                             BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "/admin/update_post";
        }

        post.setId(postID);
        postService.updatePost(post);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl,
                           Model model){
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";
    }

    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model){
        List<PostDto> posts = postService.searchPosts(query);
        model.addAttribute("postList", posts);
        return "admin/posts";
    }

    private static String getUrl(String postTitle) {
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+","-");
        url = url.replaceAll("[^A-Za-z0-9]","-");
        return url;
    }
}
