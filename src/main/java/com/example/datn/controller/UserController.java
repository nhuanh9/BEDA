package com.example.datn.controller;

import com.example.datn.model.*;
import com.example.datn.service.*;
import com.example.datn.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private Environment env;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;
    @Autowired
    private LinkDocService linkDocService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private ITUTCService itutcService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (!userService.isCorrectConfirmPassword(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRoles() != null) {
            Role role = roleService.findByName("ROLE_ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            Role role1 = roleService.findByName("ROLE_USER");
            Set<Role> roles1 = new HashSet<>();
            roles1.add(role1);
            user.setRoles(roles1);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        user.setImageUrls("https://firebasestorage.googleapis.com/v0/b/spa-stay.appspot.com/o/img%2F1583085901039?alt=media&token=e396af18-3aa6-49ae-8ffc-22a55124b18a");
        user.setPosts((long) 0);
        user.setLinkDocs((long) 0);
        user.setComments((long) 0);
        userService.save(user);
        VerificationToken token = new VerificationToken(user);
        token.setExpiryDate(10);
        verificationTokenService.save(token);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Void> confirmUserAccount(@RequestParam("token") String verificationToken) {
        VerificationToken token = verificationTokenService.findByToken(verificationToken);
        if (token != null) {
            boolean isExpired = token.isExpired();
            if (!isExpired) {
                User user = userService.findByEmail(token.getUser().getEmail());
                user.setEnabled(true);
                userService.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users/top-posts")
    public ResponseEntity<Iterable<User>> getTopPosts() {
        Iterable<User> users = this.userService.findTopPosts();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/top-linkdocs")
    public ResponseEntity<Iterable<User>> getTopLinkDocs() {
        Iterable<User> users = this.userService.findTopLinkDocs();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/top-comments")
    public ResponseEntity<Iterable<User>> getTopComments() {
        Iterable<User> users = this.userService.findTopComments();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<Iterable<Post>> getUserPosts(@PathVariable Long id) {
        Iterable<Post> posts = this.postService.findAllByUserId(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/users/{id}/linkdocs")
    public ResponseEntity<Iterable<LinkDoc>> getUserLinkDocs(@PathVariable Long id) {
        Iterable<LinkDoc> linkDocs = this.linkDocService.findAllByUserId(id);
        return new ResponseEntity<>(linkDocs, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userOptional.get().getId());
        user.setUsername(userOptional.get().getUsername());
        user.setEmail(userOptional.get().getEmail());
        user.setEnabled(userOptional.get().isEnabled());
        user.setPassword(userOptional.get().getPassword());
        if (user.getRoles() == null || user.getRoles().size() == 1 )
            user.setRoles(userOptional.get().getRoles());
        user.setConfirmPassword(userOptional.get().getConfirmPassword());
        user.setLinkDocs(userOptional.get().getLinkDocs());
        user.setPosts(userOptional.get().getPosts());
        user.setComments(userOptional.get().getComments());
        if (user.getStatus() == 0) user.setStatus(userOptional.get().getStatus());
        userService.save(user);
        //
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "users/{id}/new-password")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userOptional.get().getId());
        user.setUsername(userOptional.get().getUsername());
        user.setEmail(userOptional.get().getEmail());
        user.setEnabled(userOptional.get().isEnabled());
        user.setRoles(userOptional.get().getRoles());
        user.setName(userOptional.get().getName());
        user.setImageUrls(userOptional.get().getImageUrls());
        user.setPhoneNumber(userOptional.get().getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "users/{username}/new-password-username")
    public ResponseEntity<User> updatePasswordByUsername(@PathVariable String username, @RequestBody User user) {
        User userOptional = this.userService.findByUsername(username);
        user.setId(userOptional.getId());
        user.setUsername(userOptional.getUsername());
        user.setEmail(userOptional.getEmail());
        user.setEnabled(userOptional.isEnabled());
        user.setRoles(userOptional.getRoles());
        user.setName(userOptional.getName());
        user.setImageUrls(userOptional.getImageUrls());
        user.setPhoneNumber(userOptional.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAll() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/users/{idUser}/posts")
    public ResponseEntity editPost(@RequestBody Post postModel, @PathVariable Long idUser) {
        Post postEntity = postService.findById(postModel.getId()).get();
        if (postModel.getCategory() != null) {
            postEntity.setCategory(postModel.getCategory());
        }
        if (postModel.getContent() != "") {
            postEntity.setContent(postModel.getContent());
        }
        if (postModel.getDescription() != " ") {
            postEntity.setDescription(postModel.getDescription());
        }

        if (postModel.getCreateAt() != null) {
            postEntity.setCreateAt(postModel.getCreateAt());
        }
        if (postModel.getUser() != null) {
            postEntity.setUser(postModel.getUser());
        }
        if (postModel.getLikes() != null) {
            postEntity.setLikes(postModel.getLikes());
        }
        if (postModel.getListComment() != null) {
            postEntity.setListComment(postModel.getListComment());
        }
        if (postModel.getImgs().size() != 0) {
            postEntity.setImgs(postModel.getImgs());
        }
        postService.save(postEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/users/{idUser}/posts/{idPost}")
    public ResponseEntity deletePost(@PathVariable Long idUser, @PathVariable Long idPost) {
        Post postEntity = postService.findById(idPost).get();
        postEntity.setStatus(0);
        postService.save(postEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/users/{idUser}/linkdocs")
    public ResponseEntity editLinkDoc(@RequestBody LinkDoc linkdoc, @PathVariable Long idUser) {
        LinkDoc linkDoc1 = linkDocService.findById(linkdoc.getId()).get();
        if (linkdoc.getCategory() != null) {
            linkDoc1.setCategory(linkdoc.getCategory());
        }
        if (linkdoc.getCreateAt() != null) {
            linkDoc1.setCreateAt(linkdoc.getCreateAt());
        }
        if (linkdoc.getUser() != null) {
            linkDoc1.setUser(linkdoc.getUser());
        }
        if (linkdoc.getLikes() != null) {
            linkDoc1.setLikes(linkdoc.getLikes());
        }
        if (linkdoc.getLink() != "") {
            linkDoc1.setLink(linkdoc.getLink());
        }
        if (linkdoc.getDescription() != "") {
            linkDoc1.setDescription(linkdoc.getDescription());
        }
        if (linkdoc.getDes() != " ") {
            linkDoc1.setDes(linkdoc.getDes());
        }
        linkDocService.save(linkDoc1);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/users/{idUser}/linkdocs/{idLinkDoc}")
    public ResponseEntity deleteLinkDoc(@PathVariable Long idUser, @PathVariable Long idLinkDoc) {
        LinkDoc linkDoc = linkDocService.findById(idLinkDoc).get();
        linkDoc.setStatus(0);
        linkDocService.save(linkDoc);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/itutcs")
    public ResponseEntity<Iterable<ITUTC>> getAllITUTC() {
        Iterable<ITUTC> itutcs = itutcService.findAll();
        return new ResponseEntity<>(itutcs, HttpStatus.OK);
    }

}