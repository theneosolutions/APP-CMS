package com.seulah.appdesign.apicontroller.simah.service;


import com.jcraft.jsch.ChannelSftp;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class FileTransferService {


    Resource sftpPrivateKey = new ClassPathResource("697_SEAH_KEY");

//    @Autowired
//    private Session session;
//
//    public void uploadFile(String remoteDirectory, String localFilePath, String remoteFileName) throws SftpException, IOException, JSchException, FileNotFoundException {
//        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
//        channelSftp.connect();
//
//        File localFile = new File(localFilePath);
//        channelSftp.put(new FileInputStream(localFile), remoteFileName);
//
//        channelSftp.cd(remoteDirectory);
//        channelSftp.disconnect();
//    }

    public void test() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("mftuat.simah.com");
        factory.setPort(22);
        factory.setUser("697_SEAH");
        //  String  sftpPrivateKey = "src/main/resources/697_SEAH_KEY.ppk";
        String privateKeyPassPhrase = "697@Seah";

        factory.setPrivateKey(sftpPrivateKey);
        factory.setPrivateKeyPassphrase(privateKeyPassPhrase);

        factory.setAllowUnknownKeys(true);

        uploadTextFile("a","/upload/CONSUMER/","test.txt",new CachingSessionFactory<>(factory));
    }
    public void uploadTextFile(String content, String remoteDirectory, String fileName, SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        try (Session<ChannelSftp.LsEntry> session = sftpSessionFactory.getSession()) {
            String fullPath = remoteDirectory + "/" + fileName;

            InputStream inputStream = new ByteArrayInputStream(content.getBytes());
            session.write(inputStream, fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
