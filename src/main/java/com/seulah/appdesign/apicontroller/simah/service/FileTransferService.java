package com.seulah.appdesign.apicontroller.simah.service;


import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileTransferService {
    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.port}")
    private Integer sftpPort;

    @Value("${sftp.username}")
    private String sftpUser;

    @Value("${sftp.password}")
    private String sftpPasword;
    @Value("${sftp.privatekey}")
    private Resource sftpPrivateKey;
    @Value("${sftp.passphrase}")
    private String sftpPrivateKeyPassphrase;

    @Value("${sftp.sessionTimeout}")
    private Integer sessionTimeout;

    @Value("${sftp.channelTimeout}")
    private Integer channelTimeout;


    public void sftpSessionFactory() {
        String host = sftpHost;
        String username = sftpUser;
        String privateKeyPath = "/path/to/private-key"; // Replace with the actual path to your private key file
        String passphrase = "your-passphrase";
        int port = 22;

        String localFilePath = "/path/to/local/file.txt";
        String remoteDirectory = "/path/on/sftp/server/";

        JSch jsch = new JSch();

        try {
            jsch.addIdentity(privateKeyPath, passphrase);
            Session session = jsch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Upload file
            channelSftp.put(localFilePath, remoteDirectory);

            // Disconnect
            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }

    }
}
