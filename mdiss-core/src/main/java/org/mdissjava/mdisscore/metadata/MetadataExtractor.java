package org.mdissjava.mdisscore.metadata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Metadata;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

public interface MetadataExtractor {

	public Metadata obtenerMetadata(byte[] photo) throws MetadataException, ImageProcessingException, IOException;
	public String getOnlyExtension(String type);
	public long bytesToMb(int bytes);
	
}
