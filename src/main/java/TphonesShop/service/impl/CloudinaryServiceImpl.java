package TphonesShop.service.impl;

import TphonesShop.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map uploadFile(MultipartFile file, String folderName) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folderName,
                        "resource_type", "auto"
                ));
    }

    @Override
    public void deleteFile(String imageUrl, String folder) throws IOException {
        String publicId = extractPublicId(imageUrl);

        var result = cloudinary.uploader().destroy(folder + publicId, ObjectUtils.emptyMap());
        System.out.println("Delete response: " + result);
    }

    private static String extractPublicId(String url) {
        try {
            int from = url.lastIndexOf("/");
            int to = url.lastIndexOf('.');
            return url.substring(from, to);
        } catch (Exception e) {
            throw new RuntimeException("Invalid URL provided!");
        }
    }
}
