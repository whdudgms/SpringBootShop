package com.example.shoptry.service;

import com.example.shoptry.entity.ItemImg;
import com.example.shoptry.repository.ItemImgRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    // application.properties 파일에서 itemImgLocation 값을 주입받아 이미지 저장 경로로 사용합니다.
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    // 의존성 주입을 통해 ItemImgRepository와 FileService 객체를 사용합니다.
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    /**
     * 상품 이미지를 저장하는 메서드입니다.
     *
     * @param itemImg 상품 이미지 엔티티
     * @param itemImgFile 업로드된 상품 이미지 파일
     * @throws Exception 파일 업로드 및 DB 저장 시 발생할 수 있는 예외
     */
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        // 업로드된 파일의 원본 파일명을 가져옵니다.
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";  // 서버에 저장된 파일명
        String imgUrl = "";   // 저장된 파일의 접근 URL

        // 파일이 비어있지 않은 경우에만 업로드를 진행합니다.
        if (!StringUtils.isEmpty(oriImgName)) {
            // 파일을 업로드하고, 서버에 저장된 파일명을 반환받습니다.
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());

            // 업로드된 파일에 접근할 수 있는 URL을 생성합니다.
            imgUrl = "/images/item" + imgName;
        }

        // 상품 이미지 정보를 업데이트합니다. 원본 파일명, 저장된 파일명, 이미지 URL이 저장됩니다.
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);

        // DB에 상품 이미지 정보를 저장합니다.
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if (!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityExistsException::new);
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }


}