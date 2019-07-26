package io.github.assets.app.file;

import io.github.assets.domain.MessageToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FileNotification {

    private long fileId;

    private long timeOfUpload;

    private String filename;

    private MessageToken token;
}
