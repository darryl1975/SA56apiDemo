package sg.edu.nus.iss.demo.model;

import java.time.OffsetDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseModel {
    @Column(columnDefinition = "bit default 'false' not null")
    private boolean deleted;

    @Basic
    private String createdBy;

    @Basic
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime createdTime;

    @Basic
    private String lastUpdatedBy;

    @Basic
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime lastUpdatedTime;
}
