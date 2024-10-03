document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "/view/login.html";
  });