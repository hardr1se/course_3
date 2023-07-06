package ru.hogwarts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.exceptions.AvatarNotFoundException;
import ru.hogwarts.exceptions.AvatarProcessingException;
import ru.hogwarts.model.Avatar;
import ru.hogwarts.model.Student;
import ru.hogwarts.repository.AvatarRepository;
import ru.hogwarts.repository.StudentRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;

@Service
@Transactional
public class AvatarService {
    private final Path filePath;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarService(@Value("${avatar.direction.path}") String filePath,
                         StudentRepository studentRepository,
                         AvatarRepository avatarRepository) {
        this.filePath = Path.of(filePath);
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Avatar uploadAvatar(long id, MultipartFile file) {
        try {
            Student student = studentRepository.findById(id);
            var contentType = file.getContentType();
            var extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            var fileName = UUID.randomUUID() + "." + extension;
            var pathToAvatar = filePath.resolve(fileName);
            byte[] data = file.getBytes();
            Files.write(pathToAvatar, data);

            Avatar avatar = avatarRepository.findByStudent_Id(student.getId())
                    .orElse(new Avatar());
            avatar = Avatar.builder()
                    .filePath(pathToAvatar.toString())
                    .fileSize(data.length)
                    .mediaType(contentType)
                    .data(data)
                    .build();
            return avatarRepository.save(avatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pair<byte[], String> getFromFS(Long id) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new AvatarNotFoundException(id));
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }

    public Pair<byte[], String> getFromDB(Long id) {
        try {
            Avatar avatar = avatarRepository.findById(id)
                    .orElseThrow(() -> new AvatarNotFoundException(id));
            return Pair.of(Files.readAllBytes(Path.of(avatar.getFilePath())), avatar.getMediaType());
        } catch (IOException e) {
            throw new AvatarProcessingException();
        }
    }

    public Collection<Avatar> getAvatarsByPages(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
