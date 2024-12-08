const toggleActualPassword = document.querySelector('#toggleActualPassword');
const toggleNewPassword = document.querySelector('#toggleNewPassword');
const toggleConfirmNewPassword = document.querySelector('#toggleConfirmNewPassword');

const actualPassword = document.querySelector('#actualPassword');
const newPassword = document.querySelector('#newPassword');
const confirmeNewPassword = document.querySelector('#confirmNewPassword');

toggleActualPassword.addEventListener('click', function (e) {
  const type = actualPassword.getAttribute('type') === 'password' ? 'text' : 'password';
  actualPassword.setAttribute('type', type);
  this.classList.toggle('fa-eye');  
  this.classList.toggle('fa-eye-slash');
});

toggleNewPassword.addEventListener('click', function (e) {
  const type = newPassword.getAttribute('type') === 'password' ? 'text' : 'password';
  newPassword.setAttribute('type', type);
  this.classList.toggle('fa-eye');
  this.classList.toggle('fa-eye-slash');
});

toggleConfirmNewPassword.addEventListener('click', function (e) {
  const type = confirmeNewPassword.getAttribute('type') === 'password' ? 'text' : 'password';
  confirmeNewPassword.setAttribute('type', type);
  this.classList.toggle('fa-eye');
  this.classList.toggle('fa-eye-slash');
});