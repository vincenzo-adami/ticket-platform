package org.lessons.ticketplatform.model;

import jakarta.validation.constraints.NotBlank;

public class PasswordChanger {

  @NotBlank(message = "Actual password cannot be blank")
  private String actualPassword;

  @NotBlank(message = "New Password cannot be blank")
  private String newPassword;

  @NotBlank(message = "Confirm New Password cannot be blank")
  private String confirmNewPassword;

  public String getActualPassword() {
    return actualPassword;
  }

  public void setActualPassword(String actualPassword) {
    this.actualPassword = actualPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmNewPassword() {
    return confirmNewPassword;
  }

  public void setConfirmNewPassword(String confirmNewPassword) {
    this.confirmNewPassword = confirmNewPassword;
  }

}