package com.seulah.appdesign;

import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

@Service
public class FileUploadServiceTest {
    String bucketName = "Arham-Bucket";
    /*  you can find namespace in your bucket details page under Bucket information in OCI console */
    String namespaceName = "axor0ymlfntq";

    private final OsClientConfiguration configuration;

    public FileUploadServiceTest(OsClientConfiguration configuration) {
        this.configuration = configuration;
    }

    public void upload(MultipartFile file) throws Exception {
        String objectName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        //build upload request
        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(bucketName)
                        .objectName(objectName)
                        .contentLength(file.getSize())
                        .putObjectBody(inputStream)
                        .build();
        try {
            configuration.getObjectStorage().putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            configuration.getObjectStorage().close();
        }
    }
}
