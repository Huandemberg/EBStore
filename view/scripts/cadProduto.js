async function newProduto(){
    let modelo = document.getElementById("modeloImput").value;
    let tamanho = document.getElementById("tamanhoImput").value;
    let cor = document.getElementById("corPImput").value;
    let valorP = document.getElementById("valorImput").value;
    let key = "Authorization";

    const response = await fetch("http://localhost:8080/produto", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),
        }),
        body: JSON.stringify({
            modelo: modelo,
            tamanho: tamanho,
            cor: cor,
            preco: valorP,
        }),
    });

    if (response.ok) {
        showToast("#okToast");
      } else {
        showToast("#errorToast");
      }
    
      window.setTimeout(function () {
        window.location = "/view/produto.html";
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