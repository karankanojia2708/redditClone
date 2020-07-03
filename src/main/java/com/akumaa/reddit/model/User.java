package com.akumaa.reddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username required")
    @Column(unique = true, length = 100)
    private String username;

    @NotBlank(message = "Password required")
    private String password;

    @Email
    @NotEmpty(message = "Email required")
    @Column(unique = true, length = 100)
    private String email;

    private Instant createdDate;
    private boolean enable;

}
