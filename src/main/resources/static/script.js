function togglePasswordVisibility(toggleButtonSelector, inputSelector) {
  let toggleButton = document.querySelector(toggleButtonSelector);
  let inputField = document.querySelector(inputSelector);

  toggleButton.addEventListener('click', function () {
    let type = inputField.getAttribute('type') === 'password' ? 'text' : 'password';
    inputField.setAttribute('type', type);
    this.classList.toggle('fa-eye');
    this.classList.toggle('fa-eye-slash');
  });
}


if(document.querySelector('#toggleActualPassword')) {
  togglePasswordVisibility('#toggleActualPassword', '#actualPassword');
}
if(document.querySelector('#toggleNewPassword')) {
  togglePasswordVisibility('#toggleNewPassword', '#newPassword');
}
if(document.querySelector('#toggleConfirmNewPassword')) {
  togglePasswordVisibility('#toggleConfirmNewPassword', '#confirmNewPassword');
}
if(document.querySelector('#togglePassword')) {
  togglePasswordVisibility('#togglePassword', '#password');
}