package org.mdissjava.api.helpers;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadForm {
    private byte[] filedata;

    public FileUploadForm() {}

    public byte[] getFileData() {
        return filedata;
    }

    @FormParam("filedata")
    @PartType("application/octet-stream")
    public void setFileData(final byte[] filedata) {
        this.filedata = filedata;
    }
}
