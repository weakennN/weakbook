package com.weakennN.weakbook.service;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class DropBoxService {

    private final String accessToken = "sl.BAhCYJpum5_99cfrlGTLyK_vIDviRqO_xNtoKFjmGNvBr9C5yUyf1Nqu2XUAr4iLrdmFRSoBThpsmPU3aG6fppx6u_l62PRizYxcuAmRO9M2iakvi9li7rBcRU8cFWoltzZIB8k";
    private DbxClientV2 dropboxClient;
    private DbxRequestConfig config;

    public DropBoxService() {
        this.config = DbxRequestConfig.newBuilder("dropbox/weakbook").build();
        this.dropboxClient = new DbxClientV2(this.config, this.accessToken);
    }

    public void upload(String path, byte[] bytes) {
        try {
            this.dropboxClient.files().uploadBuilder( path).uploadAndFinish(new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
