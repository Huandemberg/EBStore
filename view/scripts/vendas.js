const vendaEndpoint = "http://localhost:8080/venda/vendas";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(vendas) {
    let tab = `<thead>
                <th scope="col">#</th>
                <th scope="col">CLIENTE</th>
                <th scope="col">PRODUTO</th>
                <th scope="col">VALOR_CLIENTE</th>
                <th scope="col">FORMPAG</th>
                <th scope="col">VENDEDOR</th>
               </thead>`;

    for (let venda of vendas) {
        tab += `
                <tr>
                    <td scope="row">${venda.id}</td>
                    <td>${venda.cliente.nome}</td>
                    <td>${venda.produto.modelo}</td>
                    <td>${venda.valorCliente}</td>
                    <td>${venda.formPag}</td>
                    <td>${venda.user.nome}</td>
                    <td><button type="button" onclick="getVenda(${venda.id})" class="btn btn-primary">Alterar</button></td>
                    <td><button type="button" onclick="deleteVenda(${venda.id})" class="btn btn-danger">Delete</button></td>
                </tr> `;
    }

    document.getElementById("vendas").innerHTML = tab;
}

function alterar(venda) {
    tab = `<form>
                <div class="mb-3">
                    <label class="form-label">Cliente_Id</label>
                    <input type="number" value=${venda.cliente.id} class="form-control" id="clienteInput" aria-describedby="form-control" autocomplete="off">
                </div>
                <div class="mb-3">
                    <label class="form-label">Produto_Id</label>
                    <input type="number" value=${venda.produto.id} class="form-control" id="produtoInput">
                </div>
                <div class="mb-3">
                    <label class="form-label">Forma de pagamento</label>
                    <input type="text" value="${venda.formPag}" class="form-control" id="formPInput">
                </div>
                <div class="mb-3">
                    <label class="form-label">Valor da compra</label>
                    <input type="number" value=${venda.valorCliente} class="form-control" id="valorInput">
                </div>
                <button type="button" onclick="setVenda(${venda.id}, ${venda.data})" class="btn btn-primary">Submit</button>
            </form>`;

            document.getElementById("vendas").innerHTML = tab;
}

async function getVenda(id) {
    const vendaEndpoint1 = "http://localhost:8080/venda/" + id;
    let key = "Authorization";
    const response = await fetch(vendaEndpoint1, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    alterar(data);
}

async function deleteVenda(vendaId){
    let key = "Authorization";
    const response = await fetch("http://localhost:8080/venda/" + vendaId , {
      method: "DELETE",
      headers: new Headers({
        Authorization: localStorage.getItem(key),
      }),
    });
    window.location = "/view/vendas.html";
}

async function setVenda(id, data){


    const vendaEndpoint1 = "http://localhost:8080/venda/" + id;
    let cliente_Id = document.getElementById("clienteInput").value;
    let produto_Id = document.getElementById("produtoInput").value;
    let formPag = document.getElementById("formPInput").value;
    let valorC = document.getElementById("valorInput").value; 
    let key = "Authorization";
    const response = await fetch(vendaEndpoint1, {
        method: "PUT",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),

        }),
        body: JSON.stringify({
            cliente_Id: cliente_Id,
            produto_Id: produto_Id,
            data: data,
            valorCliente: valorC,
            formPag: formPag,
        }),
    });
    window.location = "/view/vendas.html";

}

async function getVendas() {
    let key = "Authorization";
    const response = await fetch(vendaEndpoint, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    show(data);
}

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
        window.location = "/view/login.html";
});

getVendas();