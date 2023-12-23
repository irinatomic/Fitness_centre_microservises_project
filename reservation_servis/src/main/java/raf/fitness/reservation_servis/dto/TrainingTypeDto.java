package raf.fitness.reservation_servis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** Represents a training type for listing all training types.
 *  This is a response DTO.
 *  No request DTO because training types are predefined.
 */
public class TrainingTypeDto {

    private Long id;
    private String name;
}
