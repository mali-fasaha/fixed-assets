package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

import io.github.assets.domain.enumeration.FileMediumTypes;

/**
 * A FileType.
 */
@Entity
@Table(name = "file_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "filetype")
public class FileType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "file_type_name", nullable = false, unique = true)
    private String fileTypeName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_medium_type", nullable = false)
    private FileMediumTypes fileMediumType;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public FileType fileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
        return this;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public FileMediumTypes getFileMediumType() {
        return fileMediumType;
    }

    public FileType fileMediumType(FileMediumTypes fileMediumType) {
        this.fileMediumType = fileMediumType;
        return this;
    }

    public void setFileMediumType(FileMediumTypes fileMediumType) {
        this.fileMediumType = fileMediumType;
    }

    public String getDescription() {
        return description;
    }

    public FileType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileType)) {
            return false;
        }
        return id != null && id.equals(((FileType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FileType{" +
            "id=" + getId() +
            ", fileTypeName='" + getFileTypeName() + "'" +
            ", fileMediumType='" + getFileMediumType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}