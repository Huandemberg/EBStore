document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "/view/login.html";
  });

  function logout(){
    localStorage.clear();
    window.location = "/view/login.html";
  }