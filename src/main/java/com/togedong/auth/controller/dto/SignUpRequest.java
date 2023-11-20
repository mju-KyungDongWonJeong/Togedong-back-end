package com.togedong.auth.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(@NotNull @Size(min=2, max=10) String userId,
                            @NotNull @Size(min=2, max=10) String userName,
                            @NotNull @Size(min=4, max=16) String password) {

}
