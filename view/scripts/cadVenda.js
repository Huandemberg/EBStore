async function newVenda(){
    let cliente_Id = document.getElementById("clienteImput").value;
    let produto_Id = document.getElementById("produtoImput").value;
    let formPag = document.getElementById("formPImput").value;
    let valorC = document.getElementById("valorImput").value;
    let data = document.getElementById("dataImput").value;
    let key = "Authorization";

    const response = await fetch("http://localhost:8080/venda", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),
        }),
        body: JSON.stringify({
            cliente_Id: cliente_Id,
            produto_Id: produto_Id,
            valorCliente: valorC,
            formPag: formPag,
            data: data
        }),
    });

    if (response.ok) {
        showToast("#okToast");
      } else {
        showToast("#errorToast");
      }
    
      window.setTimeout(function () {
        window.location = "/view/index.html";
      }, 2000);
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