const produtoEndpoint = "http://localhost:8080/produto/produtos";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(produtos) {
    let tab = `<thead>
                <th scope="col">#</th>
                <th scope="col">MODELO</th>
                <th scope="col">TAMANHO</th>
                <th scope="col">COR</th>
                <th scope="col">PREÇO</th>
               </thead>`;
    
    for (let produto of produtos) {
        tab+= `
                <tr>
                    <td scope="row">${produto.id}</td>
                    <td>${produto.modelo}</td>
                    <td>${produto.tamanho}</td>
                    <td>${produto.cor}</td>
                    <td>${produto.preco}</td>
                </tr> `;
    }

    document.getElementById("produtos").innerHTML = tab;
}

async function getProdutos() {
    let key = "Authorization";
    const response = await fetch(produtoEndpoint, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });
    
    var data = await response.json();
    console.log(data);
    if(response) hideLoader();
    show(data);
}

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "/view/login.html";
  });

  getProdutos();