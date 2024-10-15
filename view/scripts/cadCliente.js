async function newCliente(){
    let nome = document.getElementById("nomeImput").value;
    let email = document.getElementById("emailImput").value;
    let dataNasc = document.getElementById("dataNascPImput").value;
    let cpf = document.getElementById("cpfImput").value;
    let key = "Authorization";

    const response = await fetch("http://localhost:8080/cliente", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),
        }),
        body: JSON.stringify({
            nome: nome,
            email: email,
            dataNasc: dataNasc,
            cpf: cpf,
        }),
    });

    if (response.ok) {
        showToast("#okToast");
      } else {
        showToast("#errorToast");
      }
    
      window.setTimeout(function () {
        window.location = "/view/cliente.html";
      }, 1000);
}

function showToast(id) {
    var toastElList = [].slice.call(document.querySelectorAll(id));
    var toastList = toastElList.map(function (toastEl) {
      return new bootstrap.Toast(toastEl);
    });
    toastList.forEach((toast) => toast.show());
}
  
  document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "/view/login.html";
  });