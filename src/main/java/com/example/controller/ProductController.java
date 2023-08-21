package com.example.controller;

import com.example.model.ProductCreateDTO;
import com.example.model.ProductResponseDTO;
import com.example.service.AttachmentService;
import com.example.service.ProductService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final AttachmentService attachmentService;

    @PostMapping("/create")
    public String create(
                    @RequestParam("image") MultipartFile file,
                    @ModelAttribute ProductCreateDTO dto,
                    Model model)
             {
                 productService.create(dto);
                 List<UUID> list = attachmentService.multipleUpload((File) file);
                 dto.setPhotos(list);
                 productService.create(dto);

                 Page<ProductResponseDTO> products = productService.getAll(dto.getSellerId());
        model.addAttribute("products",products);
        return "seller/products";
    }
}
