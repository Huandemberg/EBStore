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
                <th scope="col">ESTOQUE</th>
               </thead>`;
    
    for (let produto of produtos) {
        tab+= `
                <tr>
                    <td scope="row">${produto.id}</td>
                    <td>${produto.modelo}</td>
                    <td>${produto.tamanho}</td>
                    <td>${produto.cor}</td>
                    <td>${produto.preco}</td>
                    <td>${produto.estoque}</td>
                    <td><button type="button" onclick="getProduto(${produto.id})" class="btn btn-primary">Alterar</button></td>
                    <td><button type="button" onclick="deleteProduto(${produto.id})" class="btn btn-danger">Delete</button></td>
                </tr> `;
    }

    document.getElementById("produtos").innerHTML = tab;
}

function alterar(produto) {
    tab = `<form>
                <div class="mb-3">
                    <label class="form-label">Modelo</label>
                    <input type="text" value="${produto.modelo}" class="form-control" id="modelInput" aria-describedby="form-control" autocomplete="off">
                </div>
                <div class="mb-3">
                    <label class="form-label">Tamanho</label>
                    <input type="text" value=${produto.tamanho} class="form-control" id="tamanhoInput">
                </div>
                <div class="mb-3">
                    <label class="form-label">Cor</label>
                    <input type="text" value="${produto.cor}" class="form-control" id="corInput">
                </div>
                <div class="mb-3">
                    <label class="form-label">Preço</label>
                    <input type="number" value=${produto.preco} class="form-control" id="valorInput">
                </div>
                <button type="button" onclick="setProduto(${produto.id})" class="btn btn-primary">Submit</button>
            </form>`;

            document.getElementById("produtos").innerHTML = tab;
}

async function setProduto(id){


    const produtoEndpoint1 = "http://localhost:8080/produto/" + id;
    let modelo = document.getElementById("modelInput").value;
    let cor = document.getElementById("corInput").value;
    let tamanho = document.getElementById("tamanhoInput").value;
    let preco = document.getElementById("valorInput").value; 
    let key = "Authorization";
    const response = await fetch(produtoEndpoint1, {
        method: "PUT",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),

        }),
        body: JSON.stringify({
            modelo: modelo,
            tamanho: tamanho,
            cor: cor,
            preco: preco
        }),
    });
    window.location = "/view/produto.html";

}

async function getProduto(id) {
    const produtoEndpoint1 = "http://localhost:8080/produto/" + id;
    let key = "Authorization";
    const response = await fetch(produtoEndpoint1, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });

    if(response.status === 401 || response.status === 403){
        localStorage.clear();
    }

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    alterar(data);
}

async function deleteProduto(produtoId){
    let key = "Authorization";
    const response = await fetch("http://localhost:8080/produto/" + produtoId , {
      method: "DELETE",
      headers: new Headers({
        Authorization: localStorage.getItem(key),
      }),
    });
    window.location = "/view/produto.html";
}

async function getProdutos() {
    let key = "Authorization";
    const response = await fetch(produtoEndpoint, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });

    if(response.status === 401 || response.status === 403){
        localStorage.clear();
        window.top.location = "/view/login.html";
    }
    
    var data = await response.json();
    console.log(data);
    if(response) hideLoader();
    show(data);
}

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.top.location = "/view/login.html";
  });

  getProdutos();