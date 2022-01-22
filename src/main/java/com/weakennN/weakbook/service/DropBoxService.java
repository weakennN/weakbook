package com.weakennN.weakbook.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class DropBoxService {

    private final String accessToken = "bhR2c73-4VoAAAAAAAAAAaJcUfr7p6Y-K_XUht3FrLx5y3gNkMBFwOuHiXZ5syYk";
    private DbxClientV2 dropboxClient;
    private DbxRequestConfig config;

    public DropBoxService() {
        this.config = DbxRequestConfig.newBuilder("dropbox/weakbook").build();
        this.dropboxClient = new DbxClientV2(this.config, this.accessToken);
    }

    public void upload(String path, byte[] bytes) {
        try {
            this.dropboxClient.files().uploadBuilder(path).uploadAndFinish(new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFolder(String path) {
        try {
            this.dropboxClient.files().createFolderV2(path);
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    public String getImageUrl(String path) {
        String url = "";
        try {
            url = this.dropboxClient.files().getTemporaryLink(path).getLink();
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return url.replace("get", "inline");
    }
}
