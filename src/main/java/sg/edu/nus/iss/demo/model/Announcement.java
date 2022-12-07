package sg.edu.nus.iss.demo.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Announcement extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String text;

    @Column(columnDefinition = "varchar(max)")
    private String image1;

    @Column(length = 1000)
    private String image1Url;

    @Column(columnDefinition = "bit default 'false'")
    private Boolean broadcasted;

    private String broadcastBy;
    
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime broadcastTime;
}
