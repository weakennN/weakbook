package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.service.DropBoxService;
import com.weakennN.weakbook.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IndexController {

    private PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexView() {
        return "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public PostBinding test() {
        return new PostBinding();
    }

    // can be moved to PostController class
    @PostMapping(value = "/savePost", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PostBinding> savePost(@RequestBody PostBinding postBinding) {
        this.postService.savePost(postBinding);
       /* DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/weakbook").build();
        DbxClientV2 client = new DbxClientV2(config, "sl.BAjv7QUod5F-7vJF7w3aSq-qoLgv0kUr8Z1UoNtM8ZM47T65A0tCOeEnFwm7eVNwGeNpP1fJ5_uakBNo7nKCzyp11Jif265BXQw9IM4XcMgjo3An5LW3d-ejbl_QrzP4rCFKuaU");
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < images.size(); i++) {
            byte[] decodedByte = Base64.decodeBase64(images.get(i));
            InputStream inputStream = new ByteArrayInputStream(decodedByte);
            try {
                System.out.println(client.files().uploadBuilder("/something" + i + ".png").uploadAndFinish(inputStream).getPathDisplay());
            } catch (DbxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        */
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveFile")
    public ResponseEntity<?> saveFile(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getSize());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
