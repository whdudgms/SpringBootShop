package com.example.shoptry.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    /**
     * 파일을 업로드하는 메서드입니다.
     * @param uploadPath 파일이 저장될 경로
     * @param originalFileName 업로드할 파일의 원본 파일명
     * @param fileData 업로드할 파일의 바이트 데이터
     * @return 저장된 파일의 이름
     * @throws Exception 파일 저장 중 발생할 수 있는 예외
     */
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        // UUID를 이용해 고유한 파일 이름을 생성합니다.
        UUID uuid = UUID.randomUUID();

        // 원본 파일명에서 확장자를 추출합니다.
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 고유한 파일 이름과 확장자를 결합하여 저장될 파일 이름을 생성합니다.
        String savedFileName = uuid.toString() + extension;

        // 파일이 저장될 전체 경로를 생성합니다.
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // 지정된 경로에 파일을 저장하기 위해 FileOutputStream을 생성합니다.
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);

        // 파일 데이터를 작성합니다.
        fos.write(fileData);

        // 저장된 파일 이름을 반환합니다.
        return savedFileName;
    }

    /**
     * 파일을 삭제하는 메서드입니다.
     * @param filePath 삭제할 파일의 전체 경로
     * @throws Exception 파일 삭제 중 발생할 수 있는 예외
     */
    public void deleteFile(String filePath) throws Exception {
        // 삭제할 파일을 File 객체로 생성합니다.
        File deleteFile = new File(filePath);

        // 파일이 존재하는지 확인합니다.
        if (deleteFile.exists()) {
            // 파일이 존재하면 삭제합니다.
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            // 파일이 존재하지 않으면 로그에 메시지를 남깁니다.
            log.info("파일이 존재하지 않습니다.");
        }
    }
}